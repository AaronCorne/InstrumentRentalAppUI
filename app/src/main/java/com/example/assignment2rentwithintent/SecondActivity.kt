package com.example.assignment2rentwithintent

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)

        val music = intent.getParcelableExtra<MusicalEquipment>("Instrument")
        val borrowImg:ImageView=findViewById(R.id.borrowImg)
        val result: TextView= findViewById<TextView>(R.id.answer1)


        music?.let {

            borrowImg.setImageResource(it.imgID)
            val musicInfo = """
               Name: ${it.name}
               
               Rating: ${it.rating}
               
               Price: $${it.price}
               
              
            """
            result.text = musicInfo
        }

    }

}