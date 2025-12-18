
package com.example.pokedex

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.pokedex.databinding.FragmentDetailBinding
import com.example.pokedex.services.RetrofitClient
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    companion object {
        fun newInstance(name: String) = DetailFragment().apply {
            arguments = Bundle().apply { putString("name", name) }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentDetailBinding.inflate(inflater, container, false)
        val name = arguments?.getString("name") ?: ""

        lifecycleScope.launch {
            val pokemon = RetrofitClient.api.getPokemonDetail(name)
            binding.txtName.text = pokemon.name
            binding.txtInfo.text = "Altura: ${pokemon.height} | Peso: ${pokemon.weight}"
            Glide.with(this@DetailFragment)
                .load(pokemon.sprites.front_default)
                .into(binding.img)
        }

        return binding.root
    }
}
