package com.tcc.alif.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.tcc.alif.R

class ThirdScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third_screen, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val finish = view.findViewById<TextView>(R.id.finish)
        finish.setOnClickListener{
            findNavController().navigate(R.id.action_viewPagerFragment_to_cadastroActivity)
        }
    }

}