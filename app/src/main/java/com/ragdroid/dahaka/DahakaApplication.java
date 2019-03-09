package com.ragdroid.dahaka;
import android.app.Activity;
import android.app.Application;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.util.Log;

import com.ragdroid.dahaka.app.AppComponent;
import com.ragdroid.dahaka.app.DaggerAppComponent;
import com.ragdroid.dahaka.app.DaggerAppDataBindingComponent;
import com.ragdroid.dahaka.app.UserManager;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by garimajain on 13/08/17.
 */

public class DahakaApplication extends Application implements HasActivityInjector {
    private static final String TAG = DahakaApplication.class.getSimpleName();

//    @Inject DispatchingAndroidInjector<Activity> activityInjector;
    @Inject UserManager userManager;

    public AppComponent getAppComponent() {
        return appComponent;
    }

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        DataBindingUtil.setDefaultComponent(buildDataBindingComponent());
        createComponent();
    }

    protected void createComponent() {
        Log.e(TAG,"create component");
        appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        Log.e(TAG,"userManager activity="+ userManager.activityInjector());
    }

    private DataBindingComponent buildDataBindingComponent() {
        return DaggerAppDataBindingComponent.builder().application(this).build();
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        Log.e(TAG,"activityInjector >>>>>>");
        return userManager.activityInjector();
    }
}
