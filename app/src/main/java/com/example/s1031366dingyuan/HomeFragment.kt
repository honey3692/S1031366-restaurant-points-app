package com.example.s1031366dingyuan

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class HomeFragment : Fragment() {

    private lateinit var textViewUserName: TextView
    private lateinit var textViewPointsSummary: TextView
    private lateinit var buttonScanQR: Button
    private lateinit var buttonRedeem: Button
    private lateinit var recentPointsList: LinearLayout
    private var currentPoints = 0
    private lateinit var auth: FirebaseAuth

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // 初始化 Firebase
        auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid
        val database = FirebaseDatabase.getInstance()
        val pointsRef = database.getReference("users").child(userId ?: "").child("points")

        // 初始化视图
        textViewUserName = view.findViewById(R.id.textViewUserName)
        textViewPointsSummary = view.findViewById(R.id.textViewPointsSummary)
        buttonScanQR = view.findViewById(R.id.scanButton)
        buttonRedeem = view.findViewById(R.id.redeemButton)
        recentPointsList = view.findViewById(R.id.linearLayoutRecentPointsList)

        // 设置用户名
        val currentUser = auth.currentUser
        textViewUserName.text = "Welcome, ${currentUser?.displayName ?: currentUser?.email ?: "Guest"}"

        // 获取并显示数据库中的积分
        pointsRef.get().addOnSuccessListener { snapshot ->
            val savedPoints = snapshot.getValue(Int::class.java)
            currentPoints = savedPoints ?: 0
            updatePointsText()
        }

        // 扫码加分逻辑
        buttonScanQR.setOnClickListener {
            val earnedPoints = 50
            currentPoints += earnedPoints
            updatePointsText()
            addRecentPointItem("Earned $earnedPoints points", "+$earnedPoints", true)
            savePointsToDatabase(currentPoints)
        }

        // 兑换减分逻辑
        buttonRedeem.setOnClickListener {
            if (currentPoints >= 100) {
                currentPoints -= 100
                updatePointsText()
                addRecentPointItem("Redeemed 100 points", "-100", false)
                savePointsToDatabase(currentPoints)
            } else {
                textViewPointsSummary.text = "Not enough points"
            }
        }

        return view
    }

    private fun savePointsToDatabase(points: Int) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val database = FirebaseDatabase.getInstance()
        val pointsRef = database.getReference("users").child(userId).child("points")
        pointsRef.setValue(points)
    }

    private fun updatePointsText() {
        textViewPointsSummary.text = "Current total points: $currentPoints"
    }

    private fun addRecentPointItem(description: String, amount: String, isPositive: Boolean) {
        val context = requireContext()

        val itemLayout = LinearLayout(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.HORIZONTAL
            setPadding(8, 8, 8, 8)
        }

        val textDescription = TextView(context).apply {
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            text = description
            textSize = 16f
        }

        val textAmount = TextView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = amount
            textSize = 16f
            setTextColor(if (isPositive) 0xFF4CAF50.toInt() else 0xFFF44336.toInt())
        }

        itemLayout.addView(textDescription)
        itemLayout.addView(textAmount)
        recentPointsList.addView(itemLayout, 0)
    }
}
