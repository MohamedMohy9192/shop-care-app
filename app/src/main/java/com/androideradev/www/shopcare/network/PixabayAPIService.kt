package com.androideradev.www.shopcare.network


import com.androideradev.www.shopcare.BuildConfig
import com.androideradev.www.shopcare.network.model.ImagesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPIService {

    @GET("api")
    suspend fun searchForImage(
        @Query("q") searchInput: String,
        @Query("key") key: String = BuildConfig.API_KEY
    ): Response<ImagesResponse>
}