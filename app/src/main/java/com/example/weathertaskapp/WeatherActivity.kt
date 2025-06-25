package com.example.weathertaskapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.weathertaskapp.network.RetrofitClient
import kotlinx.coroutines.launch

class WeatherActivity : AppCompatActivity() {

    private val apiKey = "19be54f234f1def327a8dd704d78a609"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        val city = intent.getStringExtra("city") ?: "Delhi"

        val tvCity = findViewById<TextView>(R.id.tvCity)
        val tvTemp = findViewById<TextView>(R.id.tvTemp)
        val tvWeather = findViewById<TextView>(R.id.tvWeather)
        val tvHumidity = findViewById<TextView>(R.id.tvHumidity)
        val tvWindSpeed = findViewById<TextView>(R.id.tvWindSpeed)
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        val btnBack = findViewById<Button>(R.id.btnBack)

        tvCity.text = "City: $city"
        Log.d("WeatherAPI", "Calling weather for $city with $apiKey")

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.weatherService.getWeatherByCity(city, apiKey)
                tvTemp.text = "Temperature: ${response.main.temp}°C"
                tvWeather.text = "Weather: ${response.weather[0].description}"
                tvHumidity.text = "Humidity: ${response.main.humidity}%"
                tvWindSpeed.text = "Wind Speed: ${response.wind.speed} m/s"
            } catch (e: Exception) {
                Log.e("WeatherAPI", "Error: ${e.message}")
                Toast.makeText(this@WeatherActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        btnBack.setOnClickListener {
            finish()
        }
        // Enable back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
