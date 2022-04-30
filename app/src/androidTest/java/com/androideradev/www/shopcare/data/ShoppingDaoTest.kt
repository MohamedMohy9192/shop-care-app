package com.androideradev.www.shopcare.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.androideradev.www.shopcare.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

    // This is a JUnit test rule that swaps the background executor used
    // by the architecture components with a different one that executes each task synchronously.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.shoppingDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertShoppingItem() = runTest {
        val shoppingItem = ShoppingItem(
            id = 1,
            name = "Phone",
            amount = 1,
            price = 1000f,
            imageUrl = "www.example.com"
        )
        dao.insertShoppingItem(shoppingItem)

        val shoppingItems = dao.getAllShoppingItems().getOrAwaitValue()
        assertThat(shoppingItems).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItem() = runTest {
        val shoppingItem = ShoppingItem(
            id = 1,
            name = "Phone",
            amount = 1,
            price = 1000f,
            imageUrl = "www.example.com"
        )
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)

        val shoppingItems = dao.getAllShoppingItems().getOrAwaitValue()
        assertThat(shoppingItems).doesNotContain(shoppingItem)
    }

    @Test
    fun checkItemsTotalPriceSum() = runTest {
        val shoppingItem1 = ShoppingItem(
            id = 1, name = "Phone", amount = 1, price = 10f, imageUrl = "www.example.com"
        )
        val shoppingItem2 = ShoppingItem(
            id = 2, name = "Car", amount = 3, price = 5.5f, imageUrl = "www.example.com"
        )
        val shoppingItem3 = ShoppingItem(
            id = 3, name = "Cat", amount = 0, price = 25f, imageUrl = "www.example.com"
        )

        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)

        val totalItemPriceSum = dao.getTotalPrice().getOrAwaitValue()
        assertThat(totalItemPriceSum).isEqualTo(1 * 10f + 3 * 5.5f + 0 * 25f)
    }

}