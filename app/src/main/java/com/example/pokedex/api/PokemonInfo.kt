package com.example.pokedex.api

import java.io.Serializable

data class PokemonInfo(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int
) : Serializable
