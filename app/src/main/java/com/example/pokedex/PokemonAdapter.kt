package com.example.pokedex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.api.PokemonListItem
import com.example.pokedex.databinding.ItemPokemonBinding

class PokemonAdapter(
    private val onItemClick: (PokemonListItem) -> Unit
) : ListAdapter<PokemonListItem, PokemonAdapter.PokemonViewHolder>(DiffCallback()) {

    class PokemonViewHolder(val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.binding.pokemonName.text = pokemon.name.replaceFirstChar { it.uppercase() }

        holder.itemView.setOnClickListener { onItemClick(pokemon) }
    }

    class DiffCallback : DiffUtil.ItemCallback<PokemonListItem>() {
        override fun areItemsTheSame(oldItem: PokemonListItem, newItem: PokemonListItem) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: PokemonListItem, newItem: PokemonListItem) =
            oldItem == newItem
    }
}
