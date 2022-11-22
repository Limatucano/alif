package com.tcc.alif.data.model.local

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tcc.alif.R
import com.tcc.alif.view.ui.administrator.configuration.ConfigurationIntent

enum class ConfigurationOptions(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val intent: ConfigurationIntent
){
    MY_CATEGORIES(
        title = R.string.categories,
        icon = R.drawable.ic_category,
        intent = ConfigurationIntent.GoToMyCategories
    ),
    PROFILE(
        title = R.string.profile,
        icon = R.drawable.profile,
        intent = ConfigurationIntent.GoToProfile
    ),
    COMPANY_PROFILE(
        title = R.string.company_profile,
        icon = R.drawable.company,
        intent = ConfigurationIntent.GoToCompanyProfile
    ),
    CHANGE_PASSWORD(
        title = R.string.change_password,
        icon = R.drawable.ic_key,
        intent = ConfigurationIntent.GoToChangePassword
    ),
    BUSINESS_REQUESTS(
        title = R.string.business_requests,
        icon = R.drawable.ic_notification,
        intent = ConfigurationIntent.GoToMyBusinessRequests
    ),
    EXIT(
        title = R.string.exit,
        icon = R.drawable.ic_exit,
        intent = ConfigurationIntent.Exit
    )
}
