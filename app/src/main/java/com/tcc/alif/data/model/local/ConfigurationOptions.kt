package com.tcc.alif.data.model.local

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tcc.alif.R
import com.tcc.alif.view.ui.administrator.configuration.ConfigurationFragment.Companion.openChangePassword
import com.tcc.alif.view.ui.administrator.configuration.ConfigurationFragment.Companion.openMyCategories
import com.tcc.alif.view.ui.administrator.configuration.ConfigurationFragment.Companion.openProfile

enum class ConfigurationOptions(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val action: (() -> Unit)? = null
){
    MY_CATEGORIES(
        title = R.string.categories,
        icon = R.drawable.ic_category,
        action = ::openMyCategories
    ),
    PROFILE(
        title = R.string.profile,
        icon = R.drawable.profile,
        action = ::openProfile
    ),
    CHANGE_PASSWORD(
        title = R.string.change_password,
        icon = R.drawable.ic_key,
        action = ::openChangePassword
    )
}
