import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.PokemonData
import com.example.pokedex.PokemonsViewModel
import com.example.pokedex.databinding.PokemonItemBinding
class PokemonRecyclerViewAdapter(
    private val viewModel: PokemonsViewModel,
    private val listener: (PokemonData) -> Unit,
    private val context: Context?
): RecyclerView.Adapter<PokemonRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val binding = PokemonItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            PokemonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("RV_item_add", position.toString())
        val pokemonItem: PokemonData = viewModel.filteredList[position]
        holder.binding.apply {
            pokNameTV.text = pokemonItem.name
            pokTypeTV.text = pokemonItem.type.toString()
            updateIsFavouriteImage(favouriteB, position)
            favouriteB.setOnClickListener {  onFavourite(favouriteB, position) }
            if(context != null){
                pokImageIV.setImageResource(context.resources.getIdentifier(pokemonItem.name.toLowerCase(), "drawable", context.packageName))
                pokemonIt.setBackgroundColor(
                    context.resources.getColor(
                        context.resources.getIdentifier(pokemonItem.type.toString().toLowerCase(), "color", context.packageName),
                        context.resources.newTheme()
                    )
                )
            }
        }

        holder.itemView.setOnClickListener{ listener(pokemonItem) }
    }

    private fun onFavourite(button: ImageButton, position: Int){
        viewModel.changeIsFavourite(position)
        updateIsFavouriteImage(button, position)
    }

    private fun updateIsFavouriteImage(button: ImageButton, position: Int){
        if (viewModel.filteredList[position].isFavourite)
            button.setImageResource(android.R.drawable.star_big_on)
        else{
            button.setImageResource(android.R.drawable.star_big_off)
        }
    }

    override fun getItemCount(): Int = viewModel.filteredList.size
}