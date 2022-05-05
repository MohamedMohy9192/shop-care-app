package com.androideradev.www.shopcare.network.model

import com.google.gson.annotations.SerializedName

data class ImagesResponse(
    @SerializedName("hits")
    val images: List<Image>,
    val total: Int,
    val totalHits: Int
)