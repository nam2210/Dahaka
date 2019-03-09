package com.ragdroid.dahaka.app;



import android.app.Activity;
import android.util.Log;

import com.ragdroid.dahaka.api.entity.Pokemon;
import com.ragdroid.dahaka.user.PokemonService;
import com.ragdroid.dahaka.user.UserComponent;

import java.util.Timer;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by garimajain on 13/08/17.
 */
@Singleton
public class UserManager implements HasActivityInjector {
    private static final String TAG = UserManager.class.getSimpleName();

    private final PokemonService service;
    private final UserComponent.Builder userComponentBuilder;
    @Inject DispatchingAndroidInjector<Activity> activityInjector;

    private Pokemon pokemonCache;

    private UserComponent userComponent;


    @Inject
    public UserManager(PokemonService service, UserComponent.Builder builder) {
        this.service = service;
        this.userComponentBuilder = builder;
        Log.e(TAG,"activityInjector is null" );

    }

    public Flowable<Pokemon> loginWithUserName(String userName) {
        return getPokemonMaybeFromCache()
                .concatWith(service.getPokemon(userName).toMaybe())
                .take(1)
                .doOnNext(this::createUserSession);
    }

    private void createUserSession(Pokemon pokemon) {
        Log.e(TAG, "createUserSession");
        userComponent = userComponentBuilder
                .pokeMon(pokemon)
                .build();
        userComponent.inject(this);

    }


    private Maybe<Pokemon> getPokemonMaybeFromCache() {
        Log.e(TAG, "getPokemonMaybeFromCache");
        if (pokemonCache != null) {
            return Maybe.just(pokemonCache);
        } else {
            return Maybe.empty();
        }
    }

    public boolean isLoggedIn() {
        return userComponent != null;
    }

    public void logOut() {
        pokemonCache = null;
        userComponent = null;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        Log.e(TAG,">>>>> activityInjector=" + activityInjector.toString());
        return activityInjector;
    }
}
