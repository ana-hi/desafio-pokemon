package com.desafio.pokemon.controller;

import com.desafio.pokemon.dto.api.PokemonDetailDto;
import com.desafio.pokemon.dto.api.PokemonResponse;
import com.desafio.pokemon.service.PokemonService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping
    public Page<PokemonResponse> getPokemon(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return pokemonService.getPokemonList(page, size);
    }

    @GetMapping("/{name}")
    public PokemonDetailDto getPokemonDetail(@PathVariable("name") String name){
        return pokemonService.getPokemonDetail(name);
    }
}
