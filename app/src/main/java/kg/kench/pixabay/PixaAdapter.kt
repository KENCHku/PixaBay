package kg.kench.pixabay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import kg.kench.pixabay.databinding.ItemImageBinding

class PixaAdapter() : Adapter<PixaAdapter.PixaViewHolder>() {

    val arrayList = arrayListOf<ImageModel>()

    fun addImages(list: ArrayList<ImageModel>){
        arrayList.addAll(list)
        notifyDataSetChanged()
    }

    class PixaViewHolder(private val binding: ItemImageBinding) : ViewHolder(binding.root) {

        fun bind(model: ImageModel) = with(binding) {
            ivItemImage.load(model.largeImageURL)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PixaAdapter.PixaViewHolder {
        return PixaViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PixaAdapter.PixaViewHolder, position: Int) {
        holder.bind(arrayList[position])
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}