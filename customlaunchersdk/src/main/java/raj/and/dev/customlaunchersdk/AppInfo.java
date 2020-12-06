package raj.and.dev.customlaunchersdk;

import android.graphics.drawable.Drawable;

import java.util.Comparator;

/**
 * Created by Raj Aryan on 12/4/2020.
 * Mahiti Infotech
 * raj.aryan@mahiti.org
 */
public class AppInfo implements Comparator {
    public String label;
    public CharSequence packageName;
    public Drawable icon;
    public CharSequence versionCode;
    public String versionName;
    public String mainActivityName;

    @Override
    public int compare(Object o1, Object o2) {
        AppInfo appInfo1 = (AppInfo) o1;
        AppInfo appInfo2 = (AppInfo) o2;
        return appInfo1.label.compareTo(appInfo2.label);
    }
}
