package com.desafio.pokemon.dto.client;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class PokemonListResult {
    private List<PokemonResult> results;
    private int count;
}
