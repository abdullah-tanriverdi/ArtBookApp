package com.tanriverdi.artbook

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tanriverdi.artbook.databinding.RecyclerRowBinding

// Adapter class for handling the RecyclerView in MainActivity
class ArtAdapter(private val artList: ArrayList<Art>) : RecyclerView.Adapter<ArtAdapter.ArtHolder>() {

    // ViewHolder class to represent each item in the RecyclerView
    class ArtHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    // Creating ViewHolder instances
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtHolder {
        // Inflating the layout for each item
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtHolder(binding)
    }

    // Getting the total number of items in the list
    override fun getItemCount(): Int {
        return artList.size
    }

    // Binding data to the ViewHolder
    override fun onBindViewHolder(holder: ArtHolder, position: Int) {
        // Setting the text of the TextView in the ViewHolder with the corresponding data
        holder.binding.recyclerViewTextView.text = artList[position].name

        // Setting a click listener for each item in the RecyclerView
        holder.itemView.setOnClickListener {
            // Creating an Intent to start the ArtActivity with necessary information
            val intent = Intent(holder.itemView.context, ArtActivity::class.java).apply {
                putExtra("info", "old")        // indicating that it's an existing item
                putExtra("id", artList[position].id)  // passing the ID of the selected item
            }

            // Starting the ArtActivity
            holder.itemView.context.startActivity(intent)
        }
    }
}
