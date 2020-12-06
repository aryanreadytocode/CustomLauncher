package raj.and.dev.customlaunchersdk;

import java.util.Comparator;

/**
 * Created by Raj Aryan on 12/5/2020.
 * Mahiti Infotech
 * raj.aryan@mahiti.org
 */
public class SortByName implements Comparator<AppInfo> {

    @Override
    public int compare(AppInfo appInfo1, AppInfo appInfo2) {
        return appInfo1.label.compareTo(appInfo2.label);
    }
}
