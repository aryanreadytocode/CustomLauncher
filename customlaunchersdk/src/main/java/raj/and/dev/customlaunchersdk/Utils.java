package raj.and.dev.customlaunchersdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Objects;

/**
 * Created by Raj Aryan on 12/4/2020.
 * Mahiti Infotech
 * raj.aryan@mahiti.org
 */
public class Utils {

    private static final String TAG = "Utils";

    public static Drawable getActivityIcon(Context context, String packageName, String activityName) {
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, activityName));
        ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);
        assert resolveInfo != null;
        return resolveInfo.loadIcon(pm);
    }

    public static String  getVersionCode(Context mContext, String packageName) {
        int versionCode = 0;
        try {
            PackageInfo pInfo = mContext.getPackageManager().getPackageInfo(packageName, 0);
            versionCode = pInfo.versionCode;
            Log.i(TAG, "getVersionCode: " + versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return ""+versionCode;
    }

    public static String getVersionName(Context mContext, String packageName) {
        String versionName = "";
        try {
            PackageInfo pInfo = mContext.getPackageManager().getPackageInfo(packageName, 0);
            versionName = pInfo.versionName;
            Log.i(TAG, "getVersionName: " + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static String getMainActivityClassName(Context context, String packageName) {
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        assert launchIntent != null;
        return Objects.requireNonNull(launchIntent.getComponent()).getClassName();
    }

    public static String getPackageName(Context context) {
        return context.getApplicationContext().getPackageName();
    }

    public static int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (screenWidthDp / columnWidthDp + 0.5); // +0.5 for correct rounding to int.
        return noOfColumns;
    }

    public static String getAppName(Context context) {
        final PackageManager pm = context.getApplicationContext().getPackageManager();
        ApplicationInfo ai;
        try {
            ai = pm.getApplicationInfo( context.getPackageName(), 0);
        } catch (final PackageManager.NameNotFoundException e) {
            ai = null;
        }
       return (String) (ai != null ? pm.getApplicationLabel(ai) : "(unknown)");
    }
}
