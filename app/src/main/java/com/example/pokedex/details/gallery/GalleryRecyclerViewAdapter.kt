package com.example.pokedex.details.gallery

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.PokemonData
import com.example.pokedex.PokemonsViewModel
import com.example.pokedex.databinding.GalleryItemBinding
import com.example.pokedex.databinding.PokemonItemBinding
import java.lang.Integer.min

class GalleryRecyclerViewAdapter(
    private val viewModel: PokemonsViewModel,
    private val context: Context?,
    private val pokemonChosen: PokemonData
): RecyclerView.Adapter<GalleryRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val binding = GalleryItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            GalleryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("RV_gallery_item_add", position.toString())
        val pokemonItem: PokemonData = viewModel.pokemonList[(position + pokemonChosen.id + 1)%viewModel.pokemonList.size]
        holder.binding.apply {
            if(context != null){
                enemyIV.setImageResource(context.resources.getIdentifier(pokemonItem.name.toLowerCase(), "drawable", context.packageName))
            }
        }
    }

    override fun getItemCount(): Int = min(viewModel.pokemonList.size, 5)
}