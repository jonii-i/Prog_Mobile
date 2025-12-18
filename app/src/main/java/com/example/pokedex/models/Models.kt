
package com.example.pokedex.models

data class PokemonListResponse(val results: List<Pokemon>)
data class Pokemon(val name: String, val url: String)
data class PokemonDetail(val name: String, val height: Int, val weight: Int, val sprites: Sprites)
data class Sprites(val front_default: String)
