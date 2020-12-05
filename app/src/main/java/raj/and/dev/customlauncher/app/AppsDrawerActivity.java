package raj.and.dev.customlauncher.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telecom.Call;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.operators.observable.ObservableFromCallable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import raj.and.dev.customlauncher.R;
import raj.and.dev.customlauncher.databinding.ActivityAppsDrawerBinding;
import raj.and.dev.customlauncher.sdk.AppInfo;
import raj.and.dev.customlauncher.sdk.BroadcastAppInstall;
import raj.and.dev.customlauncher.sdk.ListApp;
import raj.and.dev.customlauncher.sdk.Utils;

public class AppsDrawerActivity extends AppCompatActivity {

    RecyclerView gridView;
    EditText edtLSearchApp;
    private RAdapter adapter;
    CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAppsDrawerBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_apps_drawer);
        setupUIForHideKey(binding.rlMain);
        gridView = binding.gridView;
        edtLSearchApp = binding.edtLSearchApp;
        int mNoOfColumns = Utils.calculateNoOfColumns(this, 100.0f);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        gridView.setLayoutManager(manager);
        gridView.setHasFixedSize(true);
        gridView.setFocusable(false);

        observeAllApp("");

        edtLSearchApp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.resetAdapter();
                observeAllApp(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void observeAllApp(String searchQuery) {
        disposable.add(ListApp.getAllTheApplications(AppsDrawerActivity.this, searchQuery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AppInfo>>() {
                    @Override
                    public void accept(List<AppInfo> appInfos) throws Throwable {
                        if (appInfos != null && !appInfos.isEmpty()) {
                            adapter = new RAdapter(appInfos);
                            gridView.setAdapter(adapter);
//                            adapter.setData(appInfos);
//                            adapter.setAppsListFinal(appInfos);
                        }
                    }
                }));
    }

    public void setupUIForHideKey(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideKeyboard();
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUIForHideKey(innerView);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadCast();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisteredBroadcast();
    }

    private void unregisteredBroadcast() {
        unregisterReceiver(br);
    }

    BroadcastAppInstall br = new BroadcastAppInstall(this);

    private void registerBroadCast() {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_INSTALL);
        intentFilter.addDataScheme("package");
        registerReceiver(br, intentFilter);
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
