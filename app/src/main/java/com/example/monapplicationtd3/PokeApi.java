package com.example.monapplicationtd3;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokeApi  {
    @GET("/api/v2/nature")
    Call<RestPokemonResponse> getPokemonResponse();
}
