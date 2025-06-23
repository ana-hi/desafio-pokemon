package com.desafio.pokemon.dto.api;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PokemonDetailDto extends PokemonResponse {
    private String description;
    private Map<String, Object> evolutions;

    public PokemonDetailDto(PokemonResponse base, String description, Map<String, Object> evolutions) {
        super(base.getName(), base.getPhoto(), base.getWeight(), base.getTypes(), base.getAbilities());
        this.description = description;
        this.evolutions = evolutions;
    }
}
