package com.example.pokedex

import android.util.Log
import androidx.lifecycle.ViewModel

class PokemonsViewModel : ViewModel() {
    lateinit var pokemonList: MutableList<PokemonData>
    lateinit var filteredList: MutableList<PokemonData>

    init {
        Log.i("PokemonsViewModel", "CreatePokemons")
        createPokemons()
        sortByName()
    }

    private fun createPokemons(){
        PokemonData.value = 0
        pokemonList = mutableListOf(
            PokemonData("Diglett", PokemonType.Ground,
                "If a Diglett digs through a field, it leaves the soil perfectly tilled and ideal for planting crops."),
            PokemonData("Charmander", PokemonType.Fire,
                "It has a preference for hot things. When it rains, steam is said to spout from the tip of its tail."),
            PokemonData("Ditto", PokemonType.Normal,
                "It can reconstitute its entire cellular structure to change into what it sees, but it returns to normal when it relaxes."),
            PokemonData("Ekans", PokemonType.Poison,
                "The older it gets, the longer it grows. At night, it wraps its long body around tree branches to rest."),
            PokemonData("Eevee", PokemonType.Normal,
                "It has the ability to alter the composition of its body to suit its surrounding environment."),
            PokemonData("Cubone", PokemonType.Ground,
                "When the memory of its departed mother brings it to tears, its cries echo mournfully within the skull it wears on its head."),
            PokemonData("Jigglypuff", PokemonType.Normal,
                "Jigglypuff has top-notch lung capacity, even by comparison to other Pokémon. It won’t stop singing its lullabies until its foes fall asleep."),
            PokemonData("Zubat", PokemonType.Poison,
                "It emits ultrasonic waves from its mouth to check its surroundings. Even in tight caves, Zubat flies around with skill.")
        )
        filteredList = pokemonList
    }

    fun sortByName(){
        pokemonList.sortBy { it.name }
    }

    fun setFilterToFavourites() {
        filteredList = pokemonList.filter { it.isFavourite }.toMutableList()
    }

    fun setFilterToType(typeStr: String) {
        filteredList = pokemonList.filter { it.type.toString() == typeStr }.toMutableList()
    }

    fun setRemoveFilter() {
        filteredList = pokemonList
    }

    fun getById(id: Int): PokemonData?{
        for (it in pokemonList) {
            Log.i("id", it.id.toString())
            if(it.id == id) {
                Log.i("id", "found")
                return it
            }
        }
        Log.i("id", "returning null")
        return null
    }

    fun changeIsFavourite(position: Int){
        val isFav = filteredList[position].isFavourite
        filteredList[position].isFavourite = !isFav
    }

}