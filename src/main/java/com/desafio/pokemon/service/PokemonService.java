package com.desafio.pokemon.service;

import com.desafio.pokemon.client.PokemonClient;
import com.desafio.pokemon.dto.api.PokemonDetailDto;
import com.desafio.pokemon.dto.api.PokemonResponse;
import com.desafio.pokemon.dto.client.PokemonDetail;
import com.desafio.pokemon.dto.client.PokemonListResult;
import feign.FeignException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PokemonService {

    private final PokemonClient client;

    public PokemonService(PokemonClient client){
        this.client = client;
    }

    private final Map<String, PokemonDetailDto> cache = new ConcurrentHashMap<>();

    @Cacheable("pokemonDetails")
    public PokemonResponse getPokemonResponse(String name) {
        PokemonDetail detail = client.getPokemonByName(name);
        return new PokemonResponse(
                name,
                detail.getSprites().getFrontDefault(),
                detail.getWeight(),
                detail.getTypes().stream().map(t -> t.getType().getName()).toList(),
                detail.getAbilities().stream().map(a -> a.getAbility().getName()).toList()
        );
    }

    public Page<PokemonResponse> getPokemonList(int page, int size){
        int offset = page * size;

        PokemonListResult result = client.getPokemon(size, offset);

        List<PokemonResponse> response = result.getResults().stream()
                .map(summary -> getPokemonResponse(summary.getName())
                ).toList();

        return new PageImpl<>(response, PageRequest.of(page, size), result.getCount());
    }

    public PokemonDetailDto getPokemonDetail(String name){
        if (cache.containsKey(name)) {
            return cache.get(name);
        }

        try {
            PokemonResponse response = getPokemonResponse(name);

            Map<String, Object> species = client.getPokemonSpecies(name);

            List<Map<String, Object>> flavorTexts = (List<Map<String, Object>>) species.get("flavor_text_entries");
            String description = flavorTexts.stream()
                    .filter(e -> ((Map<String, Object>) e.get("language")).get("name").equals("en"))
                    .findFirst()
                    .map(e -> ((String) e.get("flavor_text")).replace("\n", " ").replace("\f", " "))
                    .orElse("");

            String evolutionUrl = ((Map<String, String>) species.get("evolution_chain")).get("url");
            String chainId = evolutionUrl.replaceAll(".*/evolution-chain/(\\d+)/", "$1");

            Map<String, Object> evolutionChain = client.getPokemonEvolutions(chainId);


            Map<String, Object> evolutions = (Map<String, Object>) evolutionChain.get("chain");

            PokemonDetailDto pokemonDetailDto = new PokemonDetailDto(response, description, evolutions);

            cache.put(name, pokemonDetailDto);
            return pokemonDetailDto;

        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving pokemon details");
        }
    }
}
