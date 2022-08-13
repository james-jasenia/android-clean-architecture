package com.example.andrdoid_clean_architecture.domain.model

data class Beer(
    val name: String,
    val imageUrl: String,
    val description: String,
    val foodPairing: List<String>
)
