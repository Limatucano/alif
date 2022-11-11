package com.tcc.alif.view.ui

import com.tcc.alif.data.local.SharedPreferencesHelper
import com.tcc.alif.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    @Inject
    lateinit var sharedPreferences: SharedPreferencesHelper
}