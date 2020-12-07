package com.example.pokedex.list

import PokemonRecyclerViewAdapter
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.contains
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.PokemonData
import com.example.pokedex.PokemonsViewModel
import com.example.pokedex.R
import com.example.pokedex.databinding.PokemonRvFragmentBinding

class PokemonRecyclerViewFragment : Fragment() {

    private lateinit var viewModel: PokemonsViewModel
    private lateinit var binding: PokemonRvFragmentBinding

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    private lateinit var menu: Filter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("PokemonRecyclerViewFragment", "onCreateView()")
        binding = PokemonRvFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(PokemonsViewModel::class.java)

        viewManager = LinearLayoutManager(this.context)
        viewAdapter = PokemonRecyclerViewAdapter(viewModel, ::onDetails, this.context)
        binding.list.apply {
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        val swipeToDeleteCallback = object : SwipeToDeleteCallback(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                viewModel.pokemonList.remove(viewModel.filteredList.removeAt(pos))
                viewAdapter.notifyItemRemoved(pos)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.list)


        return binding.root
    }

    private fun onDetails(pokemon: PokemonData){
        val action = PokemonRecyclerViewFragmentDirections.actionPokemonListFragmentToDetailsFragment(pokemon.id)
        NavHostFragment.findNavController(this).navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.filter_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
        val types = mutableListOf<String>()
        viewModel.pokemonList.forEach {
            val typeS = it.type.toString()
            var contains = false
            for(t in types){
                if(t.equals(typeS)){
                    contains = true
                }
            }
            if(!contains){
                types.add(typeS)
            }
        }
        Log.i("types", types.size.toString())

        var id: Int = 101
        types.forEach{
            val typeItem: MenuItem = menu.add(
                Menu.NONE,
                id,
                id,
                it
            )

            val typeStr:String = it

            typeItem.setOnMenuItemClickListener {
                onTypeFilter(typeStr)
                return@setOnMenuItemClickListener true
            }

            id++
        }

        return super.onPrepareOptionsMenu(menu)
    }

    private fun onTypeFilter(typeStr:String){
        viewModel.setFilterToType(typeStr)
    }
}