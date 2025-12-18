package com.example.pokedex


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.api.RetrofitInstance
import com.example.pokedex.api.PokemonResult
import com.example.pokedex.databinding.FragmentListBinding
import kotlinx.coroutines.launch
import android.text.Editable
import android.text.TextWatcher

class ListFragment : Fragment(R.layout.fragment_list) {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PokemonAdapter
    private var fullPokemonList: List<PokemonResult> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentListBinding.bind(view)

        adapter = PokemonAdapter()

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        setupSearch()
        loadPokemon()
    }

    private fun loadPokemon() {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.getPokemonList(limit = 2000)
                fullPokemonList = response.results
                adapter.submitList(fullPokemonList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun setupSearch() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // não usado
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                val query = s.toString().lowercase()

                val filteredList = fullPokemonList.filter {
                    it.name.contains(query)
                }

                adapter.submitList(filteredList)
            }

            override fun afterTextChanged(s: Editable?) {
                // não usado
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
