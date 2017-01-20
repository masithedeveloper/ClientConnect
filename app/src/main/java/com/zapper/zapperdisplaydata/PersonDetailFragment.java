package com.zapper.zapperdisplaydata;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zapper.zapperdisplaydata.commons.RESTClient;
import com.zapper.zapperdisplaydata.db.DBPerson;
import com.zapper.zapperdisplaydata.dbcom.DataManager;
import com.zapper.zapperdisplaydata.rest.RESTBase;
import com.zapper.zapperdisplaydata.rest.RESTfGetPersonDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A fragment representing a single Person detail screen.
 * This fragment is either contained in a {@link PersonListActivity}
 * in two-pane mode (on tablets) or a {@link PersonDetailActivity}
 * on handsets.
 */
public class PersonDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public Person person;
    public int id;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PersonDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            // get person locally
            person = DataManager.get_person_details_by_id(getArguments().getInt(ARG_ITEM_ID));
            // check if details have been fully downloaded
            if(person.getIsDetails() == 0){
                // download details from server
                RESTfGetPersonDetails resTfGetPersonDetails = new RESTfGetPersonDetails(Lib.getService(), new RESTClient.RESTClientResponseListener() {
                    @SuppressLint("NewApi")
                    public void HandleResponse(final HashMap<String, Object> response) {
                        final HashMap<String, Object> fresponse = response;
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                String response_data = fresponse.toString();
                                // update person record from the DB
                                try {
                                    person = new Person(new JSONObject(response_data));
                                    DBPerson dbPerson = new DBPerson("serverId = " + person.getId());
                                    dbPerson.values.put("age", person.getAge());
                                    dbPerson.values.put("favouriteColour", person.getFavouriteColour());
                                    dbPerson.values.put("isDetails", 1);
                                    dbPerson.save();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
                resTfGetPersonDetails.call(String.valueOf(person.getId()));
            }

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(person.getFirstName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.person_detail, container, false);

        // Show the dummy content as text in a TextView.
        ((TextView) rootView.findViewById(R.id.txtFirstName)).setText(person.getFirstName());
        ((TextView) rootView.findViewById(R.id.txtLastName)).setText(person.getLastName());
        ((TextView) rootView.findViewById(R.id.txtAge)).setText(person.getAge());
        ((TextView) rootView.findViewById(R.id.txtFavouriteColour)).setText(person.getFavouriteColour());
        return rootView;
    }
}
