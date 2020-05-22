package com.example.monapplicationtd3.presentation.model.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.monapplicationtd3.Constants;
import com.example.monapplicationtd3.Singletons;
import com.example.monapplicationtd3.data.PokeApi;
import com.example.monapplicationtd3.presentation.model.Natures;
import com.example.monapplicationtd3.presentation.model.RestPokemonResponse;
import com.example.monapplicationtd3.presentation.model.view.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainController {

    private SharedPreferences sharedPreferences;
    private Gson gson;
    private MainActivity view;

    public MainController(MainActivity mainActivity, Gson gson, SharedPreferences sharedPreferences){
        this.view = mainActivity;
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
    }

    public void onStart(){

        List<Natures> naturesList = getDataFromCache();
        if(naturesList !=null) {
            view.showList(naturesList);
        }else{
            makeApiCall();
        }
    }

    private void makeApiCall(){


        Call<RestPokemonResponse> call = Singletons.getPokeApi().getPokemonResponse();
        call.enqueue(new Callback<RestPokemonResponse>() {
            @Override
            public void onResponse(Call<RestPokemonResponse> call, Response<RestPokemonResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Natures> naturesList = response.body().getResults();
                    saveList(naturesList);
                    view.showList(naturesList);
                }else{
                   view.showError();
                }
            }

            @Override
            public void onFailure(Call<RestPokemonResponse> call, Throwable t) {
                view.showError();
            }
        });
    }

    private void saveList(List<Natures> naturesList) {

        String jsonString = gson.toJson(naturesList);

        sharedPreferences
                .edit()
                .putString(Constants.KEY_POKEMON_LIST, jsonString)
                .apply();
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

    public void onItemClick(Natures natures){

    }

    public void onButtonAClick(){

    }

    public void onButtonBClick(){

    }
}
