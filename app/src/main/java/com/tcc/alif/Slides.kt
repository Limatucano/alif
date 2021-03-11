package com.tcc.alif

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

class Slides : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slides)
        val view_pager = findViewById<ViewPager>(R.id.view_pager)
        val tips:Array<Tip> = arrayOf(
            Tip("Tip1", R.color.pink),
            Tip("Tip2", R.color.blue),
            Tip("Tip3", R.color.black)
        )
        view_pager.adapter =  OnBoardingAdapter(tips)
    }
    private inner class OnBoardingAdapter(val tips: Array<Tip>) : PagerAdapter(){
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = layoutInflater.inflate(R.layout.presentation, container, false)
            val tip_title = findViewById<TextView>(R.id.tip_title)
            tip_title.text = tips[position].title
            tip_title.setTextColor(ContextCompat.getColor(this@Slides, tips[position].color))
            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int = tips.size





    }
}