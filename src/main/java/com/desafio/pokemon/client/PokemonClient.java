package com.desafio.pokemon.client;

import com.desafio.pokemon.dto.client.PokemonDetail;
import com.desafio.pokemon.dto.client.PokemonListResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "PokemonClient", url = "https://pokeapi.co/api/v2")
public interface PokemonClient {

    @GetMapping("/pokemon")
    PokemonListResult getPokemon(
            @RequestParam int limit,
            @RequestParam int offset
    );

    @GetMapping("/pokemon/{name}")
    PokemonDetail getPokemonByName(
            @PathVariable("name") String name
    );

    @GetMapping("/pokemon-species/{name}")
    Map<String, Object> getPokemonSpecies(
            @PathVariable("name") String name
    );

    @GetMapping("/evolution-chain/{id}")
    Map<String, Object> getPokemonEvolutions(
            @PathVariable("id") String id
    );
}
