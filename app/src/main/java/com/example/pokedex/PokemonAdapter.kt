package com.example.pokedex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.api.PokemonResult
import com.example.pokedex.databinding.ItemPokemonBinding

class PokemonAdapter :
    ListAdapter<PokemonResult, PokemonAdapter.PokemonViewHolder>(DiffCallback()) {

    class PokemonViewHolder(val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = getItem(position)

        holder.binding.pokemonName.text =
            pokemon.name.replaceFirstChar { it.uppercase() }

        val id = pokemon.url
            .split("/")
            .filter { it.isNotEmpty() }
            .last()

        val imageUrl =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"

        Glide.with(holder.itemView)
            .load(imageUrl)
            .into(holder.binding.pokemonImage)
    }


    class DiffCallback : DiffUtil.ItemCallback<PokemonResult>() {
        override fun areItemsTheSame(oldItem: PokemonResult, newItem: PokemonResult) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: PokemonResult, newItem: PokemonResult) =
            oldItem == newItem
    }
}
