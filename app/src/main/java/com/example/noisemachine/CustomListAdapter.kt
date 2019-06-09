package com.example.noisemachine

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.R


class CustomListAdapter(private val context: Context,
                        private val nameArray: Array<String>,
                        private val infoArray: Array<String>,
                        private val imageIDArray: Array<Int>): BaseAdapter()
{

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {

        val rowView = inflater.inflate(R.layout.listview_row, parent, false)

        //this code gets references to objects in the listview_row.xml file
        val nameTextField = rowView.nameTextViewID as TextView
        val infoTextField = rowView.infoTextViewID as TextView
        val imageView = rowView.imageViewID as ImageView

        //this code sets the values of the objects to values from the arrays
        nameTextField.text = nameArray[position]
        infoTextField.text = infoArray[position]
        imageView.setImageResource(imageIDArray[position])

        return rowView

    }

    override fun getItem(position: Int): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemId(position: Int): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}