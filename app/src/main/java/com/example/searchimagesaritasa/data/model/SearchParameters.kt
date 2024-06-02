package com.example.searchimagesaritasa.data.model

data class SearchParameters(
    val q: String,
    val type: String,
    val engine: String,
    val num: Int
)