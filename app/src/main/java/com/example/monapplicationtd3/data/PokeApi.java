package com.example.monapplicationtd3.data;

import com.example.monapplicationtd3.presentation.model.RestPokemonResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokeApi  {
    @GET("/api/v2/nature")
    Call<RestPokemonResponse> getPokemonResponse();
}
