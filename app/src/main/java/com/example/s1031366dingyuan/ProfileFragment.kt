package com.example.s1031366dingyuan

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var textViewName: TextView
    private lateinit var textViewEmail: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
        }

        textViewName = view.findViewById(R.id.textViewName)
        textViewEmail = view.findViewById(R.id.textViewEmail)

        val logoutButton = view.findViewById<Button>(R.id.logoutButton)
        val viewHistoryButton = view.findViewById<Button>(R.id.viewHistoryButton)
        val editProfileButton = view.findViewById<Button>(R.id.editProfileButton)

        logoutButton.setOnClickListener {
            auth.signOut()
            Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        viewHistoryButton.setOnClickListener {
            Toast.makeText(requireContext(), "Viewing points history", Toast.LENGTH_SHORT).show()
        }

        editProfileButton.setOnClickListener {
            Toast.makeText(requireContext(), "Editing profile", Toast.LENGTH_SHORT).show()
        }

        loadUserInfo()

        return view
    }

    private fun loadUserInfo() {
        val uid = auth.currentUser?.uid ?: return
        databaseRef = FirebaseDatabase.getInstance().getReference("users").child(uid)

        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child("name").value?.toString() ?: "Unknown"
                val email = snapshot.child("email").value?.toString() ?: "Unknown"
                textViewName.text = name
                textViewEmail.text = email
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to load user info", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
