package com.example.s1031366dingyuan

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity

class ForgotPasswordActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val editTextEmail = findViewById<EditText>(R.id.textEmailAddress)
        val buttonSendLink = findViewById<Button>(R.id.buttonSendLink)
        val spinnerSecurityQuestion = findViewById<Spinner>(R.id.spinnerSecurityQuestion)
        val editTextAnswer = findViewById<EditText>(R.id.editTextAnswer)
        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)
        val textViewLogin = findViewById<TextView>(R.id.textViewLogin)

        val questions = arrayOf(
            "What is your favorite pet's name?",
            "What is your mother's maiden name?",
            "What was the name of your first school?"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, questions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSecurityQuestion.adapter = adapter

        buttonSendLink.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            } else {

                Toast.makeText(this, "Password reset link sent to $email", Toast.LENGTH_LONG).show()
            }
        }


        buttonSubmit.setOnClickListener {
            val selectedQuestion = spinnerSecurityQuestion.selectedItem.toString()
            val answer = editTextAnswer.text.toString().trim()

            if (answer.isEmpty()) {
                Toast.makeText(this, "Please answer the security question", Toast.LENGTH_SHORT).show()
            } else {

                Toast.makeText(this, "Security question answered: $selectedQuestion\nAnswer: $answer", Toast.LENGTH_LONG).show()


                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }


        textViewLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
