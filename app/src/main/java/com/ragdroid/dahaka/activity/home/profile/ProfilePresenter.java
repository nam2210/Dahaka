package com.ragdroid.dahaka.activity.home.profile;

import android.util.Log;

import com.ragdroid.dahaka.api.entity.Pokemon;
import com.ragdroid.dahaka.app.UserManager;
import com.ragdroid.dahaka.mvp.BasePresenterImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;

import javax.inject.Inject;

/**
 * Created by garimajain on 17/08/17.
 */

public class ProfilePresenter extends BasePresenterImpl<ProfileContract.View> implements ProfileContract.Presenter {
    private static final String TAG = ProfilePresenter.class.getSimpleName();

    private final Pokemon pokemon;

    @Inject
    public ProfilePresenter(Pokemon pokemon) {
        this.pokemon = pokemon;
        Log.e(TAG, ProfilePresenter.this.toString());
        Log.e(TAG, pokemon.toString());
    }

    @Override
    public void onViewAdded(ProfileContract.View view) {
        super.onViewAdded(view);
        URL url = null;
        try {
            url = new URL(pokemon.getSprites().getFrontDefault());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ProfileModel model = ProfileModel.newBuilder()
                .experience(pokemon.getBaseExperience())
                .height(pokemon.getHeight())
                .imageUrl(url != null ? url.toString() : "")
                .weight(pokemon.getWeight())
                .name(pokemon.getName())
                .build();
        getView().showModel(model);

    }
}
