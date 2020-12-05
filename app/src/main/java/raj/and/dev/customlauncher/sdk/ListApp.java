package raj.and.dev.customlauncher.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.core.Observable;

/**
 * Created by Raj Aryan on 12/4/2020.
 * Mahiti Infotech
 * raj.aryan@mahiti.org
 */
public class ListApp {
    private static List<AppInfo> appsList;

    public static Observable<List<AppInfo>> getAllTheApplications(final Context context, String searchQuery) {

        return Observable.fromCallable(new Callable<List<AppInfo>>() {
            @Override
            public List<AppInfo> call() throws Exception {
                PackageManager pm = context.getPackageManager();
                appsList = new ArrayList<>();
                appsList.clear();

                Intent i = new Intent(Intent.ACTION_MAIN, null);
                i.addCategory(Intent.CATEGORY_LAUNCHER);

                List<ResolveInfo> allApps = pm.queryIntentActivities(i, 0);
                for (ResolveInfo ri : allApps) {
                    AppInfo app = new AppInfo();
                    app.label = ri.loadLabel(pm).toString();
                    app.packageName = ri.activityInfo.packageName;
                    app.icon = ri.activityInfo.loadIcon(pm);
                    app.versionName = Utils.getVersionName(context, app.packageName.toString());
                    app.mainActivityName = Utils.getMainActivityClassName(context, app.packageName.toString());
                    app.versionCode = Utils.getVersionCode(context, app.packageName.toString());
                    app.icon = ri.activityInfo.loadIcon(pm);
                    if (!searchQuery.isEmpty()) {
                        if (app.label.toLowerCase().contains(searchQuery.toLowerCase())) {
                            appsList.add(app);
                        }
                    }else {
                        appsList.add(app);
                    }


                }
                Collections.sort(appsList, new SortByName());
                return appsList;
            }
        });

    }
}
