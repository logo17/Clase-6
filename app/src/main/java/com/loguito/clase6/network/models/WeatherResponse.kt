package com.loguito.clase6.network.models

// TODO 2: Mapeamos el response del api a modelos
data class WeatherResponse(
    val id: Int,
    val name: String,
    val coord: WeatherCoordinate,
    val weather: List<WeatherDetail>
)
