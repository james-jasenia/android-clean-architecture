package com.example.andrdoid_clean_architecture.data.remote.dto


data class IngredientsDto(
    val hops: List<HopDto>,
    val malt: List<MaltDto>,
    val yeast: String
)