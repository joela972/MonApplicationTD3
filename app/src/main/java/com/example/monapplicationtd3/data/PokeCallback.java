package com.example.monapplicationtd3.data;

import com.example.monapplicationtd3.presentation.model.Natures;
import com.example.monapplicationtd3.presentation.model.RestPokemonResponse;

import java.util.List;

public interface PokeCallback {
    public void onSuccess(List <Natures> response);
    public void onFailed();

}
