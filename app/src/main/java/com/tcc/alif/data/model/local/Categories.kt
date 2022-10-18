package com.tcc.alif.data.model.local

import androidx.annotation.StringRes
import com.tcc.alif.R

enum class Categories(@StringRes val title: Int) {
    BARBER_SHOP(R.string.barberShop),
    RESTAURANT(R.string.restaurant),
    ELECTRICIAN(R.string.electrician),
    FOODS(R.string.foods),
    CLOTHING(R.string.clothing),
    HEALTH(R.string.health),
    DRIVER(R.string.driver),
    TRADE(R.string.trade),
    WHOLESALER(R.string.wholesaler),
    PARKING(R.string.parking)
}
fun getAllCategories() =
    listOf(
        Categories.BARBER_SHOP.title,
        Categories.RESTAURANT.title,
        Categories.ELECTRICIAN.title,
        Categories.FOODS.title,
        Categories.CLOTHING.title,
        Categories.HEALTH.title,
        Categories.DRIVER.title,
        Categories.TRADE.title,
        Categories.WHOLESALER.title,
        Categories.PARKING.title
    )