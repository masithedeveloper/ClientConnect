package com.zapper.zapperdisplaydata;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zapper.zapperdisplaydata.commons.RESTClient;
import com.zapper.zapperdisplaydata.db.DBPerson;
import com.zapper.zapperdisplaydata.dbcom.DataManager;
import com.zapper.zapperdisplaydata.rest.RESTfGetPersons;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class PersonListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    public ArrayList<Person> persons = new ArrayList<>();
    public ArrayList<String> arrayListPersonFullNames = new ArrayList<>();
    static final int READ_BLOCK_SIZE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);

        Lib.Init("/data/data/com.zapper.zapperdisplaydata/databases/", "zapper.db", getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        View recyclerView = findViewById(R.id.person_list);
        assert recyclerView != null;

        // async task for downloading data from end-point
        RESTfGetPersons restfGetPersonDetails = new RESTfGetPersons(Lib.getService(), new RESTClient.RESTClientResponseListener() {
            @SuppressLint("NewApi")
            public void HandleResponse(final HashMap<String, Object> response) {
                final HashMap<String, Object> fresponse = response;
                runOnUiThread(new Runnable() {
                    public void run() {
                        String response_data = fresponse.toString();
                        // update person record from the DB
                        try {
                            persons = DataManager.jsonToPersons(new JSONArray(response_data));
                            DBPerson dbPerson;
                            for (Person person : persons) {
                                dbPerson = new DBPerson("serverId = " + person.getId());
                                dbPerson.values.put("firstName", person.getAge());
                                dbPerson.values.put("lastName", person.getFavouriteColour());
                                dbPerson.values.put("isDetails", 0);
                                dbPerson.save();
                                arrayListPersonFullNames.add(person.getFirstName()+ " " + person.getLastName());
                            }
                            /*
                            persons = DataManager.get_all_persons();
                            for (int i = 0; i < persons.size(); i++) {
                                arrayListPersonFullNames.add(persons.get(i).getFirstName()+ " " + persons.get(i).getLastName());
                            }
                            */
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        restfGetPersonDetails.call();
        // in case the network call failed, retrieve the data locally
        persons = DataManager.get_all_persons();
        for (int i = 0; i < persons.size(); i++) {
            arrayListPersonFullNames.add(persons.get(i).getFirstName()+ " " + persons.get(i).getLastName());
        }

        /*
        try {
            Scanner input = new Scanner( new File( getApplicationContext().getDatabasePath("zapper.db")  + "/persons.json"));
            InputStream myInput = getApplicationContext().getAssets().open("persons.json");
            StringBuilder builder = new StringBuilder();

            while (input.hasNext()){
                builder.append(input.nextLine());
            }

            FileInputStream fIn = openFileInput(Environment.getExternalStorageDirectory().getPath() + "/persons.json");
            InputStreamReader isr = new InputStreamReader(fIn);
            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String s = "";
            int charRead;
            while ((charRead = isr.read(inputBuffer))>0) {
                //---convert the chars to a String---
                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                s += readString;
                inputBuffer = new char[READ_BLOCK_SIZE];
            }
            isr.close();

            Log.i("builder.toString()", s.toString());
            JSONTokener tokenizer = new JSONTokener(s.toString());
            Log.i("tokenizer.toString()", tokenizer.toString());
            persons = DataManager.jsonToPersons(new JSONArray(tokenizer.toString()));
            DBPerson dbPerson;
            for (Person person : persons) {
                dbPerson = new DBPerson("serverId = " + person.getId());
                dbPerson.values.put("firstName", person.getAge());
                dbPerson.values.put("lastName", person.getFavouriteColour());
                dbPerson.values.put("isDetails", 0);
                dbPerson.save();
                arrayListPersonFullNames.add(person.getFirstName()+ " " + person.getLastName());
            }

        } catch (Exception ex) {
            ex.printStackTrace(); // Masi added this to have our custom response. See HandleError(Exception);
        }
        */
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.person_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        // start service
        // new ServiceManager().startService();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(arrayListPersonFullNames));
    }
    
    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ArrayList<String> mValues;

        public SimpleItemRecyclerViewAdapter(ArrayList<String> arrayListPersons) {
            mValues = arrayListPersons;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.person_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.personId = String.valueOf(persons.get(position).getId());
            holder.mFullNameView.setText(mValues.get(position));
            //holder.mFirstNameView.setText(mValues.get(position).get("firstName"));
            //holder.mLastNameView.setText(mValues.get(position).get("lastName"));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(PersonDetailFragment.ARG_ITEM_ID, holder.personId);
                        PersonDetailFragment fragment = new PersonDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.person_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, PersonDetailActivity.class);
                        intent.putExtra(PersonDetailFragment.ARG_ITEM_ID, holder.personId);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            //public final TextView mFirstNameView;
            //public final TextView mLastNameView;
            public final TextView mFullNameView;
            public String personId;
            public ViewHolder(View view) {
                super(view);
                mView = view;
                //mFirstNameView = (TextView) view.findViewById(R.id.firstName);
                //mLastNameView = (TextView) view.findViewById(R.id.lastName);
                mFullNameView = (TextView) view.findViewById(R.id.fullNameView);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mFullNameView.getText() + "'";
            }
        }
    }
}
