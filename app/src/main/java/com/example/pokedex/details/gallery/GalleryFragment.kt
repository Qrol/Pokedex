package com.example.pokedex.details.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.PokemonsViewModel
import com.example.pokedex.databinding.FragmentGalleryBinding

class GalleryFragment(private val pokemonId: Int?) : Fragment() {

    private lateinit var viewModel: PokemonsViewModel
    private lateinit var binding: FragmentGalleryBinding

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("onCreate gallery", pokemonId.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGalleryBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(PokemonsViewModel::class.java)

        val colNum = 3
        viewManager = GridLayoutManager(this.context, colNum)
        viewAdapter = GalleryRecyclerViewAdapter(viewModel, this.context, viewModel.getById(pokemonId!!)!!)
        binding.galleryRV.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        updateView()

        return binding.root
    }

    private fun updateView(){
        if(pokemonId == null) return
        Log.i("UpdateDetails-ID", pokemonId.toString())
        val pokemonData = viewModel.getById(pokemonId!!)!!
        Log.i("UpdateDetails-POK", pokemonData.toString())

        binding.apply {

            nameTV.text = pokemonData.name

        }
    }
}