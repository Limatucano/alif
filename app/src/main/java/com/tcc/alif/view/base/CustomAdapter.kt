package com.tcc.alif.view.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes

class CustomAdapter @JvmOverloads constructor(
    context: Context ,
    @LayoutRes private val layoutRes: Int = android.R.layout.simple_list_item_1,
    private val values: List<Pair<String,String>>
) : ArrayAdapter<Pair<String,String>>(
    context,
    layoutRes,
    values
) {

    /*
    * values is a Pair of String, where the first is the key and the second is what will be shown on screen
    * values.first = key
    * values.second = value
     */
    override fun getCount(): Int = values.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)

        val title = view.findViewById<TextView>(android.R.id.text1)
        title.text = values[position].second
        return view
    }

    override fun getItem(position: Int): Pair<String, String> {
        return values[position]
    }

    fun getItemByKey(key: String) : Pair<String,String>? =
        values.firstOrNull { it.first == key }

    override fun getPosition(item: Pair<String, String>?): Int {
        return values.indexOf(item)
    }
}