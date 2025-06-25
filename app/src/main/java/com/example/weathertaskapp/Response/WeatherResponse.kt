package com.example.weathertaskapp.response

data class WeatherResponse(
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
    val name: String
)

data class Weather(
    val main: String,
    val description: String,
    val icon: String
)

data class Main(
    val temp: Float,
    val humidity: Int
)

data class Wind(
    val speed: Float
)
