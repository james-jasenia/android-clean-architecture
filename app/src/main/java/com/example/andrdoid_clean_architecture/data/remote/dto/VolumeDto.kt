package com.example.andrdoid_clean_architecture.data.remote.dto


import com.google.gson.annotations.SerializedName

data class VolumeDto(
    val unit: String,
    val value: Int
)