package com.tanriverdi.artbook

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tanriverdi.artbook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var artList: ArrayList<Art>
    private lateinit var artAdapter: ArtAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Accessing views in the layout using ViewBinding
        artList = ArrayList()
        artAdapter = ArtAdapter(artList)

        // Setting up adapter and layout manager for RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = artAdapter

        // Calling the function to load artworks
        loadArtList()
    }

    // Function to load artworks
    private fun loadArtList() {
        try {
            // Opening or creating the SQLite database
            val database = this.openOrCreateDatabase("Arts", MODE_PRIVATE, null)
            // Query to retrieve artworks
            val cursor = database.rawQuery("SELECT * FROM arts", null)

            // Indices of columns returned by the query
            val artNameIx = cursor.getColumnIndex("artname")
            val idIx = cursor.getColumnIndex("id")

            // If there are artworks in the database
            if (cursor.moveToFirst()) {
                // Reading data and adding to artList
                do {
                    val name = cursor.getString(artNameIx)
                    val id = cursor.getInt(idIx)
                    val art = Art(name, id)
                    artList.add(art)
                } while (cursor.moveToNext())
                // Notifying the adapter of the changes
                artAdapter.notifyDataSetChanged()
            } else {
                // If there are no artworks in the database, inform the user
                Toast.makeText(this, "No arts found.", Toast.LENGTH_SHORT).show()
            }

            // Closing the cursor
            cursor.close()

        } catch (e: Exception) {
            // In case of an error, print the error and inform the user
            e.printStackTrace()
            Toast.makeText(this, "Error loading arts.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Creating the menu
        menuInflater.inflate(R.menu.art_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handling click events on menu items
        when (item.itemId) {
            R.id.add_art_item -> {
                // Redirecting to the page to add a new artwork
                val intent = Intent(this, ArtActivity::class.java)
                intent.putExtra("info", "new")
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
