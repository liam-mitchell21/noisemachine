package com.example.noisemachine

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.listview_row.view.*

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
        nameTextField.setText(nameArray.get(position))
        infoTextField.setText(infoArray.get(position))
        imageView.setImageResource(imageIDArray.get(position))

        return rowView
    }

    override fun getItem(position: Int): Any {
        return nameArray[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return nameArray.size
    }


}