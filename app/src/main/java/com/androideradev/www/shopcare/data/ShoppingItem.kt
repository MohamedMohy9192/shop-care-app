package com.androideradev.www.shopcare.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_items")
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String,
    var amount: Int,
    var price: Float,
    var imageUrl: String
)