package com.sidaryan33.application;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Logger {
        public static void m(String message) {
            Log.d("SUNNY", "" + message);
        }

        public static void t(Context context, String message) {
            Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show();
        }
        public static void T(Context context, String message) {
            Toast.makeText(context, message + "", Toast.LENGTH_LONG).show();
        }
    }
