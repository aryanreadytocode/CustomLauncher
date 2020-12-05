package raj.and.dev.customlauncher.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import raj.and.dev.customlauncher.R;
import raj.and.dev.customlauncher.databinding.RowLayoutBinding;
import raj.and.dev.customlauncher.sdk.AppInfo;

/**
 * Created by Raj Aryan on 12/4/2020.
 * Mahiti Infotech
 * raj.aryan@mahiti.org
 */
public class RAdapter extends RecyclerView.Adapter<RAdapter.ViewHolder> {
    private List<AppInfo> appsList=new ArrayList<>();
    private List<AppInfo> appsListFinal=new ArrayList<>();


    RowLayoutBinding binding;
    private List<AppInfo> appInfos;

    public RAdapter(List<AppInfo> appInfos) {
        this.appsList = appInfos;
    }

    @Override
    public RAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.row_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(binding);
        return viewHolder;
    }

    public List<AppInfo> getAppsListFinal() {
        return appsListFinal;
    }


    @Override
    public void onBindViewHolder(RAdapter.ViewHolder viewHolder, int i) {

        final AppInfo appInfo = appsList.get(i);

        //Here we use the information in the list we created to define the views

        String appLabel = appsList.get(i).label;
        String appPackage = appsList.get(i).packageName.toString();
        String versionCode = appsList.get(i).versionCode.toString();
        String mainActivityName = appsList.get(i).mainActivityName;
        String versionName = appsList.get(i).versionName;
        Drawable appIcon = appsList.get(i).icon;

        binding.ivIcon.setImageDrawable(appIcon);
        binding.tvAppName.setText(appLabel);
        binding.tvPackageName.setText(appPackage);
        binding.tvMainName.setText("Main Activity: "+mainActivityName);
        binding.tvVersionCode.setText("VersionCode: "+versionCode);
        binding.tvVersionName.setText("VersionName: "+versionName);

        binding.rlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(appInfo.packageName.toString());
                context.startActivity(launchIntent);
                Toast.makeText(v.getContext(), appInfo.label.toString(), Toast.LENGTH_LONG).show();

            }
        });

    }


    @Override
    public int getItemCount() {

        //This method needs to be overridden so that Androids knows how many items
        //will be making it into the list

        return appsList!=null&&!appsList.isEmpty()?appsList.size():0;
    }

    public void setData(List<AppInfo> appInfos) {
        this.appsList = appInfos;
        notifyDataSetChanged();
    }

    public List<AppInfo> getAppsList() {
        return appsList;
    }

    public void resetAdapter() {
        appsList.clear();
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvAppName, tvPackageName, tvMainName, tvVersionCode, tvVersionName;
        public ImageView ivIcon;
        public RelativeLayout rlMain;


        //This is the subclass ViewHolder which simply
        //'holds the views' for us to show on each row
        public ViewHolder(RowLayoutBinding itemView) {
            super(itemView.getRoot());
            ivIcon = binding.ivIcon;
            tvAppName = binding.tvAppName;
            tvPackageName = binding.tvPackageName;
            tvMainName = binding.tvMainName;
            tvVersionCode = binding.tvVersionCode;
            tvVersionName = binding.tvVersionName;
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Context context = v.getContext();

            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(appsList.get(pos).packageName.toString());
            context.startActivity(launchIntent);
            Toast.makeText(v.getContext(), appsList.get(pos).label.toString(), Toast.LENGTH_LONG).show();

        }
    }

}
