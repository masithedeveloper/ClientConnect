package com.zapper.zapperdisplaydata;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by developer on 12/13/2016.
 */
public class ServiceManager extends BroadcastReceiver {
    Context mContext;
    private final String BOOT_ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        String action = intent.getAction();
        if (action.equalsIgnoreCase(BOOT_ACTION)) {
        //check for boot complete event & start your service
            startService();
        }
    }

    public void startService() {
        Intent mServiceIntent = new Intent();
        mServiceIntent.setAction("com.zapper.zapperdisplaydata.RefreshService");
        mContext.startService(mServiceIntent);
    }
}
