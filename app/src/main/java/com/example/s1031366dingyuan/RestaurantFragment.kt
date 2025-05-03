package com.example.s1031366dingyuan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class RestaurantFragment : Fragment() {

    private lateinit var searchEditText: EditText
    private lateinit var filterButton: Button
    private lateinit var sortButton: Button
    private lateinit var restaurantRecyclerView: RecyclerView
    private lateinit var welcomeTextView: TextView

    private val restaurantList = listOf(
        Restaurant("Delicious Bites", "Italian Cuisine", "4.5 ★"),
        Restaurant("Burger Haven", "Fast Food", "4.8 ★"),
        Restaurant("Taco Fiesta", "Mexican Food", "4.3 ★")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_restaurant, container, false)

        initViews(view)
        setupRecyclerView()
        setupClickListeners()

        return view
    }

    private fun initViews(view: View) {
        searchEditText = view.findViewById(R.id.searchEditText)
        filterButton = view.findViewById(R.id.filterButton)
        sortButton = view.findViewById(R.id.sortButton)
        restaurantRecyclerView = view.findViewById(R.id.restaurantRecyclerView)
        welcomeTextView = view.findViewById(R.id.welcomeTextView)

        // get Firebase user
        val user = FirebaseAuth.getInstance().currentUser
        val username = user?.displayName ?: user?.email ?: "User"
        welcomeTextView.text = "Welcome $username!"
    }

    private fun setupRecyclerView() {
        restaurantRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        restaurantRecyclerView.adapter = RestaurantAdapter(restaurantList)
    }

    private fun setupClickListeners() {
        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(searchEditText.text.toString())
                true
            } else {
                false
            }
        }

        filterButton.setOnClickListener {
            showToast("Filter clicked")
        }

        sortButton.setOnClickListener {
            showToast("Sort clicked")
        }
    }

    private fun performSearch(query: String) {
        val filtered = restaurantList.filter {
            it.name.contains(query, true) || it.cuisine.contains(query, true)
        }
        (restaurantRecyclerView.adapter as? RestaurantAdapter)?.updateList(filtered)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    data class Restaurant(val name: String, val cuisine: String, val rating: String)

    class RestaurantAdapter(private var items: List<Restaurant>) :
        RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val name: TextView = view.findViewById(R.id.restaurantName)
            val cuisine: TextView = view.findViewById(R.id.restaurantCuisine)
            val rating: TextView = view.findViewById(R.id.restaurantRating)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_restaurant, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]
            holder.name.text = item.name
            holder.cuisine.text = item.cuisine
            holder.rating.text = item.rating
        }

        override fun getItemCount() = items.size

        fun updateList(newList: List<Restaurant>) {
            items = newList
            notifyDataSetChanged()
        }
    }
}
