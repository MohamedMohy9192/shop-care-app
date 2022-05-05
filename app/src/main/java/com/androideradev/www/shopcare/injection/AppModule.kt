package com.androideradev.www.shopcare.injection

import android.content.Context
import androidx.room.Room
import com.androideradev.www.shopcare.BASE_URL
import com.androideradev.www.shopcare.DATABASE_NAME
import com.androideradev.www.shopcare.data.ShoppingItemDatabase
import com.androideradev.www.shopcare.network.PixabayAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        ShoppingItemDatabase::class.java,
        DATABASE_NAME
    )
        .build()

    @Singleton
    @Provides
    fun provideShoppingDao(
        shoppingItemDatabase: ShoppingItemDatabase
    ) = shoppingItemDatabase.shoppingDao()

    @Singleton
    @Provides
    fun providePixbayApi(): PixabayAPIService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabayAPIService::class.java)
    }
}