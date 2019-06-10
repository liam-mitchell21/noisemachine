package com.example.noisemachine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.os.Build
import androidx.annotation.RequiresApi


class MainActivity : AppCompatActivity() {

    private lateinit var listView : ListView

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.data_list_view)

        val nameArray = arrayOf("Octopus", "Pig")

        val infoArray = arrayOf("8 tentacled monster", "Delicious in rolls")

        val imageArray = arrayOf(R.drawable.accel_icon, R.drawable.mic_icon)

        /*
        //this is the basic adapter
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        listView.adapter = adapter
        //custom adapter
        */
        val adapter = CustomListAdapter(this, nameArray, infoArray , imageArray)
        listView.adapter = adapter

        }


}
