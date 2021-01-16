package com.example.pokedex.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pokedex.databinding.FragmentDetailsSlidePagerBinding
import com.example.pokedex.details.gallery.GalleryFragment

class DetailsSlidePagerFragment : Fragment(){
    private lateinit var binding: FragmentDetailsSlidePagerBinding
    private val NUM_PAGES = 2
    private var pokemonId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pokemonId = it.getInt("pokemonPosition")
        }
        Log.i("onCreate details", pokemonId.toString())

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsSlidePagerBinding.inflate(layoutInflater)
        binding.pager.adapter = DetailsSlidePagerAdapter(this)
        return binding.root
    }

    private inner class DetailsSlidePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment){
        override fun getItemCount(): Int {
            return NUM_PAGES
        }

        override fun createFragment(position: Int): Fragment {

            Log.i("onCreateFragment position: ", position.toString())
            return when(position){
                0 -> DetailsFragment(pokemonId)
                1 -> GalleryFragment(pokemonId)
                else -> createFragment(0)
            }
        }
    }
}