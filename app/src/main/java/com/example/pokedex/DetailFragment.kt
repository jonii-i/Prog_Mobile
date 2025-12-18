package com.example.pokedex

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.pokedex.api.PokemonInfo
import com.example.pokedex.databinding.FragmentDetailBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var pokemon: PokemonInfo

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)

        pokemon = requireArguments().getSerializable("pokemon") as PokemonInfo

        binding.pokemonName.text = pokemon.name.replaceFirstChar { it.uppercase() }
        binding.pokemonHeight.text = "Height: ${pokemon.height}"
        binding.pokemonWeight.text = "Weight: ${pokemon.weight}"

        val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.id}.png"
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.pokemon_placeholder)
            .into(binding.pokemonImage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(pokemon: PokemonInfo): DetailFragment =
            DetailFragment().apply {
                arguments = Bundle().apply { putSerializable("pokemon", pokemon) }
            }
    }
}
