package org.sld.htmle;

import android.content.Context;
import android.content.SharedPreferences;

public class IDESettings {

    public static void saveString(Context c, String key, String value) {
        SharedPreferences sharedPref =
                c.getSharedPreferences(
                        "Save", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key , value);
        editor.commit();
    }
    
    public static String getString(Context c, String key) {
        SharedPreferences sharedPref =
                c.getSharedPreferences(
                        "Save", Context.MODE_PRIVATE);
        return sharedPref.getString(key, "");
    }

    
}
