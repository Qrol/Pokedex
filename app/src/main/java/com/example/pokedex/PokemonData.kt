package com.example.pokedex

enum class PokemonType{
    Poison,
    Fire,
    Ground,
    Normal
}

class PokemonData(
    val name: String,
    val type: PokemonType,
    val description: String
){
    var isFavourite: Boolean = false
    var id: Int = value
    init {
        value++
    }
    companion object {
        var value: Int = 0
    }
}