package com.example.noisemachine

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView


class CustomListAdapter(private val context: Activity?,
                        private val nameArray: Array<String>?,
                        private val infoArray: Array<String>?,
                        private val imageIDArray: Array<Int>?): ArrayAdapter<String>(context, R.layout.listview_row, nameArray)
{

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context?.layoutInflater
        val rowView = inflater?.inflate(R.layout.listview_row, parent, false)

        val nameText = rowView?.findViewById(R.id.title) as TextView
        val infoText = rowView.findViewById(R.id.info) as TextView
        val imageView = rowView.findViewById(R.id.icon) as ImageView

        nameText.text = nameArray?.get(position)
        imageView.setImageResource(imageIDArray?.get(position)!!)
        infoText.text = infoArray?.get(position)

        return rowView
    }



}