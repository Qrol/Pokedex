package com.example.pokedex.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.PokemonsViewModel
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentDetailsBinding
import com.example.pokedex.databinding.PokemonRvFragmentBinding

class DetailsFragment(private val pokemonId: Int?) : Fragment() {

    private lateinit var viewModel: PokemonsViewModel
    private lateinit var binding: FragmentDetailsBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(PokemonsViewModel::class.java)

        updateView()

        return binding.root
    }

    private fun updateView(){
        if(pokemonId == null) return
        val context = this.context ?: return
        Log.i("UpdateDetails-ID", pokemonId.toString())
        val pokemonData = viewModel.getById(pokemonId!!)!!
        Log.i("UpdateDetails-POK", pokemonData.toString())
        binding.apply {
            detPokNameTV.text = pokemonData.name
            detPokDesc.text = pokemonData.description
            detPokTypeTV.text = pokemonData.type.toString()
            detPokTypeTV.setBackgroundColor(
                context.resources.getColor(
                    context.resources.getIdentifier(pokemonData.type.toString().toLowerCase(), "color", context.packageName),
                    context.resources.newTheme()
                )
            )
            detPokImage.setImageResource(resources.getIdentifier(pokemonData.name.toLowerCase(), "drawable", context.packageName))
        }
    }
}