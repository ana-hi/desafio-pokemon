package com.desafio.pokemon.dto.client;

import lombok.Data;

import java.util.List;

@Data
public class PokemonDetail {
    private int weight;
    private Sprites sprites;
    private List<TypeDetail> types;
    private List<AbilityDetail> abilities;
    private Species species;
}

