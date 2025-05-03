package com.example.s1031366dingyuan

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity

class RestaurantDetailsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_details) // layout 文件名必须对应

        val aboutButton = findViewById<Button>(R.id.aboutButton)
        val menuButton = findViewById<Button>(R.id.menuButton)
        val locationButton = findViewById<Button>(R.id.locationButton)

        val bookTableButton = findViewById<Button>(R.id.bookTableButton)
        val orderNowButton = findViewById<Button>(R.id.orderNowButton)
        val writeReviewButton = findViewById<Button>(R.id.writeReviewButton)

        // 设置点击事件
        aboutButton.setOnClickListener {
            Toast.makeText(this, "About clicked", Toast.LENGTH_SHORT).show()
        }

        menuButton.setOnClickListener {
            Toast.makeText(this, "Menu clicked", Toast.LENGTH_SHORT).show()
        }

        locationButton.setOnClickListener {
            Toast.makeText(this, "Location clicked", Toast.LENGTH_SHORT).show()
        }

        bookTableButton.setOnClickListener {
            Toast.makeText(this, "Book a Table clicked", Toast.LENGTH_SHORT).show()
        }

        orderNowButton.setOnClickListener {
            Toast.makeText(this, "Order Now clicked", Toast.LENGTH_SHORT).show()
        }

        writeReviewButton.setOnClickListener {
            Toast.makeText(this, "Write a Review clicked", Toast.LENGTH_SHORT).show()
        }
    }
}