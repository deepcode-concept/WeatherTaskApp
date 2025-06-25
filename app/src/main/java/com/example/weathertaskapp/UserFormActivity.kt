package com.example.weathertaskapp


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.weathertaskapp.Database.AppDatabase
import com.example.weathertaskapp.Database.User
import kotlinx.coroutines.launch

class UserFormActivity : AppCompatActivity() {

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etEmail: EditText
    private lateinit var city: EditText
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_form)

        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        etEmail = findViewById(R.id.etEmail)
        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)
        city = findViewById(R.id.city)

        val userDao = AppDatabase.getInstance(this).userDao()

        btnSave.setOnClickListener {
            val firstName = etFirstName.text.toString().trim()
            val lastName = etLastName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val cityS = city.text.toString().trim()

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || cityS.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newUser = User(firstName = firstName, lastName = lastName, email = email,city = cityS)

            lifecycleScope.launch {
                userDao.insertUser(newUser)
                Toast.makeText(this@UserFormActivity, "User Saved!", Toast.LENGTH_SHORT).show()
                finish() // Go back to UserListActivity
            }
        }

        btnCancel.setOnClickListener {
            finish() // Just close the form
        }
    }
}