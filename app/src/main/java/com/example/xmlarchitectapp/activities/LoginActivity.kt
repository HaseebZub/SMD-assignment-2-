package com.example.xmlarchitectapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.xmlarchitectapp.R

/**
 * LoginActivity - Entry point of the application (F1).
 * Collects user credentials and navigates to DashboardActivity
 * using Intent Extras for data passing.
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvForgotPassword: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize views
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)
        tvForgotPassword = findViewById(R.id.tv_forgot_password)

        // F1: Navigate from Login to Dashboard using Intent Extras
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (validateInput(email, password)) {
                navigateToDashboard(email)
            }
        }

        tvForgotPassword.setOnClickListener {
            Toast.makeText(this, "Password reset link sent!", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Validates user input before navigation.
     */
    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            etEmail.error = "Email is required"
            etEmail.requestFocus()
            return false
        }
        if (password.isEmpty()) {
            etPassword.error = "Password is required"
            etPassword.requestFocus()
            return false
        }
        if (password.length < 4) {
            etPassword.error = "Password must be at least 4 characters"
            etPassword.requestFocus()
            return false
        }
        return true
    }

    /**
     * F1: Creates Intent with Extras to pass data to DashboardActivity.
     * Data is passed using Intent.putExtra() as required by assignment constraints.
     */
    private fun navigateToDashboard(email: String) {
        val intent = Intent(this, DashboardActivity::class.java).apply {
            // F1: Passing data via Intent Extras (NOT static variables or SharedPreferences)
            putExtra("USER_EMAIL", email)
            putExtra("USER_NAME", email.substringBefore("@"))
            putExtra("LOGIN_TIME", System.currentTimeMillis())
        }
        startActivity(intent)
        finish() // Close login so user can't go back
    }
}
