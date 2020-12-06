package raj.and.dev.customlaunchersdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Raj Aryan on 12/5/2020.
 * Mahiti Infotech
 * raj.aryan@mahiti.org
 */
public class BroadcastAppInstall extends BroadcastReceiver {

    Context context;

    public BroadcastAppInstall() {
    }

    public BroadcastAppInstall(Context context) {
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            Log.e(" BroadcastReceiver ", "onReceive called " + " PACKAGE_REMOVED ");
            Toast.makeText(context, "USER UNINSTALL: " + Utils.getAppName(context), Toast.LENGTH_SHORT).show();


        }
        // when package installed
        else if (intent.getAction().equals(
                "android.intent.action.PACKAGE_ADDED")) {

            Log.e(" BroadcastReceiver ", "onReceive called " + "PACKAGE_ADDED");
            Toast.makeText(context, "USER INSTALL: " + Utils.getAppName(context), Toast.LENGTH_SHORT).show();


        }
    }
}
