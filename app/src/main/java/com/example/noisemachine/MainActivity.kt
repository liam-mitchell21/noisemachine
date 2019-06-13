package com.example.noisemachine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val nameArray = arrayOf<String>("Accelerometer", "Microphone")

    val infoArray = arrayOf<String>("this is where the accelerometer readout goes", "this is where the mic readout goes")

    val imageArray = arrayOf<Int>(R.drawable.accel_icon, R.drawable.mic_icon)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = CustomListAdapter(this, nameArray, infoArray, imageArray)

        data_list_view.adapter = adapter

        data_list_view.setOnItemClickListener { adapterView, view, position, id ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            Toast.makeText(this, "Click on item at $itemAtPos : item id $itemIdAtPos", Toast.LENGTH_LONG).show()
        }
    }
}
