package com.example.monapplicationtd3.presentation.model.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.monapplicationtd3.Constants;
import com.example.monapplicationtd3.Singletons;
import com.example.monapplicationtd3.data.PokeApi;
import com.example.monapplicationtd3.data.PokeCallback;
import com.example.monapplicationtd3.data.PokeRepository;
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

    private final PokeRepository pokeRepository;
    private MainActivity view;

    public MainController(MainActivity mainActivity,PokeRepository pokeRepository){
        this.view = mainActivity;
        this.pokeRepository = pokeRepository;
    }

    public void onStart(){
        pokeRepository.getPokemonResponse(new PokeCallback() {
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




    public void onItemClick(Natures natures){
        view.navigateToDetails(natures);
    }

    public void onButtonAClick(){

    }

    public void onButtonBClick(){

    }
}
