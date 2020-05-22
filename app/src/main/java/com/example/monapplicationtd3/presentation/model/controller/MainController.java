package com.example.monapplicationtd3.presentation.model.controller;

import android.content.SharedPreferences;

import com.example.monapplicationtd3.Constants;
import com.example.monapplicationtd3.Singletons;
import com.example.monapplicationtd3.presentation.model.Natures;
import com.example.monapplicationtd3.presentation.model.RestPokemonResponse;
import com.example.monapplicationtd3.presentation.model.view.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainController {

    //private final PokeRepository pokeRepository;
    private MainActivity view;
    private Gson gson;
    private SharedPreferences sharedPreferences;

    public MainController(MainActivity mainActivity, Gson gson, SharedPreferences sharedPreferences){
        this.view = mainActivity;
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
        //this.pokeRepository = pokeRepository;
    }

    /*public void onStart(){
        //pokeRepository.getPokemonResponse(new PokeCallback() {
            @Override
            public void onSuccess(List<Natures> response) {
                view.showList(response);
            }

            @Override
            public void onFailed() {
                view.showError();
            }
        });


    }

*/

    public void onStart(){
        List<Natures> naturesList = getDataFromCache();
        if(naturesList != null) {
            view.showList(naturesList);
        }else{
            makeApiCall();
        }
    }
    private void makeApiCall(){
        Call<RestPokemonResponse> call = Singletons.getPokeApi().getPokemonResponse();
        call.enqueue(new Callback<RestPokemonResponse>(){
            public void onResponse(Call<RestPokemonResponse> call, Response<RestPokemonResponse> response){
                if(response.isSuccessful() && response.body() != null) {
                    List<Natures> naturesList = response.body().getResults();
                    saveList(naturesList);
                    view.showList(naturesList);
                }else{
                    view.showError();
                }
            }
            public void onFailure(Call<RestPokemonResponse> call, Throwable t) {
                view.showError();
            }
        });
    }

    private void saveList(List <Natures> naturesList) {
        String jsonString = gson.toJson(naturesList);
        sharedPreferences
                .edit()
                .putString(Constants.KEY_POKEMON_LIST, jsonString)
                .apply();
    }

    private List<Natures> getDataFromCache(){
        String jsonNatures = sharedPreferences.getString(Constants.KEY_POKEMON_LIST, null);
        if(jsonNatures == null){
            return null;
        } else{
            Type listType = new TypeToken<List<Natures>>(){}.getType();
            return gson.fromJson(jsonNatures, listType);
        }
    }

    public void onItemClick(Natures natures){
        view.navigateToDetails(natures);
    }

    public void onButtonAClick(){

    }

    public void onButtonBClick(){

    }
}
