package com.example.assignment2rentwithintent

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.assignment2rentwithintent.ui.theme.Assignment2RentWithIntentTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val image: ImageView = findViewById(R.id.test1)
        image.setImageResource(R.drawable.drums)

        image.setOnClickListener{
            val music= MusicalEquipment("guitar",3.5f, true,55)
            val intent = Intent(this, SecondActivity::class.java)

            intent.putExtra("Instrument",music)

            startActivity(intent)
        }
    }

}


