package com.example.pokedex

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.api.PokeApiService
import com.example.pokedex.api.PokemonInfo
import com.example.pokedex.api.PokemonListItem
import com.example.pokedex.api.RetrofitInstance
import com.example.pokedex.databinding.FragmentListBinding
import kotlinx.coroutines.launch

class ListFragment : Fragment(R.layout.fragment_list) {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PokemonAdapter
    private var fullPokemonList: List<PokemonListItem> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentListBinding.bind(view)

        adapter = PokemonAdapter { pokemonItem ->
            // Ao clicar, busca informações detalhadas
            lifecycleScope.launch {
                try {
                    val id = extractId(pokemonItem.url)
                    val info: PokemonInfo = RetrofitInstance.api.getPokemonInfo(id)
                    parentFragmentManager.beginTransaction()
                        .replace(binding.root.id, DetailFragment.newInstance(info))
                        .addToBackStack(null)
                        .commit()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        setupSearch()
        loadPokemon()
    }

    private fun loadPokemon() {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.getPokemonList()
                fullPokemonList = response.results
                adapter.submitList(fullPokemonList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun setupSearch() {
        binding.searchEditText.doAfterTextChanged { editable ->
            val query = editable.toString().lowercase()
            adapter.submitList(fullPokemonList.filter { it.name.contains(query) })
        }
    }

    private fun extractId(url: String): Int {
        return url.trimEnd('/').split("/").last().toInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
