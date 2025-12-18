package com.example.pokedex.api

import retrofit2.http.GET
import retrofit2.http.Query

data class PokemonResponse(
    val results: List<PokemonResult>
)

data class PokemonResult(
    val name: String,
    val url: String
)

interface PokeApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 2000,
        @Query("offset") offset: Int = 0
    ): PokemonResponse
}
