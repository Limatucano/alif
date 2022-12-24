package com.tcc.alif.data.model.local

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tcc.alif.R

enum class HomeConsumerOptions(
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    MY_QUEUES(
        title = R.string.home_consumer_options_my_queues,
        icon = R.drawable.ic_my_queues
    ),
    QUEUES(
        title = R.string.home_consumer_options_queues,
        icon = R.drawable.ic_queues
    ),
    PROFILE(
        title = R.string.home_consumer_profile,
        icon = R.drawable.ic_profile
    ),
    COMPANIES(
        title = R.string.home_consumer_companies,
        icon = R.drawable.ic_store
    )
}