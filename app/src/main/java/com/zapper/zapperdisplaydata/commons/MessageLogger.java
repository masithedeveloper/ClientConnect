package com.zapper.zapperdisplaydata.commons;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by Masi on 2015-07-09.
 */
public class MessageLogger {

    public static void log(Context context ,Object nqabomzi){
        Log.e("","----------------------------------------------------------------------------------------------------------------");
        Log.i(context.getApplicationInfo().loadLabel(context.getPackageManager()).toString(), String.valueOf(nqabomzi).toString());
        Log.e("", "----------------------------------------------------------------------------------------------------------------");
    }
}
