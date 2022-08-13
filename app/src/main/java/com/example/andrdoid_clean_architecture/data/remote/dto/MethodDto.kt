package com.example.andrdoid_clean_architecture.data.remote.dto


import com.google.gson.annotations.SerializedName

data class MethodDto(
    val fermentation: FermentationDto,
    @SerializedName("mash_temp")
    val mashTemp: List<MashTempDto>,
    val twist: Any
)