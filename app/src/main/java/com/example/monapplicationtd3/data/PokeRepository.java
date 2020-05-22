package com.example.monapplicationtd3.data;

import android.content.SharedPreferences;

import com.example.monapplicationtd3.Constants;
import com.example.monapplicationtd3.presentation.model.Natures;
import com.example.monapplicationtd3.presentation.model.RestPokemonResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokeRepository {
    private final Gson gson;
    private PokeApi pokeApi;
    private SharedPreferences sharedPreferences;

    public PokeRepository(PokeApi pokeApi, SharedPreferences sharedPreferences, Gson gson) {
        this.pokeApi = pokeApi;
        this.sharedPreferences = sharedPreferences;
        this.gson =gson;
    }

    public void getPokemonResponse(final PokeCallback callback) {
        List<Natures> list = getDataFromCache();
        if (list != null) {
            callback.onSuccess(list);
        } else {
            pokeApi.getPokemonResponse().enqueue(new Callback<RestPokemonResponse>() {
                @Override
                public void onResponse(Call<RestPokemonResponse> call, Response<RestPokemonResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body().getResults());
                    } else {
                        callback.onFailed();
                    }
                }

                @Override
                public void onFailure(Call<RestPokemonResponse> call, Throwable t) {
                    callback.onFailed();
                }
            });
        }
    }


    private List<Natures> getDataFromCache() {
        String jsonNature = sharedPreferences.getString(Constants.KEY_POKEMON_LIST, null);
        if (jsonNature == null) {
            return null;
        } else {
            Type listType = new TypeToken<List<Natures>>() {
            }.getType();
            return gson.fromJson(jsonNature, listType);
        }
    }


    private void saveList(List<Natures> naturesList) {

        String jsonString = gson.toJson(naturesList);

        sharedPreferences
                .edit()
                .putString(Constants.KEY_POKEMON_LIST, jsonString)
                .apply();
    }

}

