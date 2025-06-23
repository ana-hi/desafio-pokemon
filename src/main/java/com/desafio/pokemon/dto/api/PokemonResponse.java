package com.desafio.pokemon.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PokemonResponse {
    private String name;
    private String photo;
    private int weight;
    private List<String> types;
    private List<String> abilities;
}
