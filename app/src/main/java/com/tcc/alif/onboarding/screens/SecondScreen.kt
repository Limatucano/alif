package com.tcc.alif.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.tcc.alif.R

class SecondScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second_screen, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val next2 = view.findViewById<TextView>(R.id.next2)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        next2.setOnClickListener{
            viewPager?.currentItem = 2
        }
    }

}