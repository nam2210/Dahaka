package com.ragdroid.dahaka.app;

import android.app.Application;

import com.ragdroid.dahaka.DahakaApplication;
import com.ragdroid.dahaka.activity.AppBindingModule;
import com.ragdroid.dahaka.api.ApiModule;
import com.ragdroid.dahaka.user.UserComponent;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by garimajain on 13/08/17.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        ApiModule.class,
        AppBindingModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<DaggerApplication> {

    //create user component
    //UserComponent.Builder userBuilder();

    //expose user manager
    //UserManager getUserManager();

    void inject(DahakaApplication instance);

    @Component.Builder
    interface Builder {

        AppComponent build();

        @BindsInstance
        Builder application(Application application);
        //Builder apiModule(ApiModule apiModule);
    }

}
