package com.example.assignment2rentwithintent

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private lateinit var musicItems:ArrayList<MusicalEquipment>
    private lateinit var itemName: TextView
    private lateinit var itemImg: ImageView
    private lateinit var switch: Switch
    private lateinit var itemPrice: TextView
    private lateinit var nextBtn: Button
    private lateinit var previousBtn: Button
    private lateinit var borrowBtn: Button
    private lateinit var ratingBar: RatingBar
    private lateinit var strapTxt: TextView

    private var currentIndex= 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemName=findViewById(R.id.itemName)
        itemImg=findViewById(R.id.itemImg)
        itemPrice=findViewById(R.id.itemPrice)
        switch=findViewById(R.id.switch1)
        nextBtn=findViewById(R.id.Next)
        previousBtn=findViewById(R.id.Previous)
        borrowBtn=findViewById(R.id.borrow)
        ratingBar=findViewById(R.id.itemRatingBar)
        strapTxt=findViewById(R.id.switchTxt)


        musicItems= arrayListOf(
            MusicalEquipment("Guitar",4.5f,true, 250, R.drawable.guitar),
            MusicalEquipment("Drums", 3.0f, true, 100,R.drawable.drums),
            MusicalEquipment("Electric Guitar", 5.0f, true, 350, R.drawable.electric_gtar),
            MusicalEquipment("Amp", 3.5f, true, 200, R.drawable.amp)

        )

        showItem(currentIndex)

        switch.setOnCheckedChangeListener{_, isChecked ->
            strapSelect=true

        }

        //Next button
        nextBtn.setOnClickListener{
            currentIndex+=1
            if(currentIndex>=musicItems.size){
                currentIndex=0
            }
            showItem(currentIndex)
        }

        //Previous button
        previousBtn.setOnClickListener{
            currentIndex-=1
            if(currentIndex<0){
                currentIndex= musicItems.size-1
            }
            showItem(currentIndex)
        }

        borrowBtn.setOnClickListener{
            val intent= Intent(this, SecondActivity::class.java)
            intent.putExtra("Instrument",musicItems[currentIndex])
            startActivity(intent)
        }





    }

    //functions
    private var strapSelect=false
    fun showItem(index:Int){
        val currentItem= musicItems[index]
        itemName.text= currentItem.name
        itemImg.setImageResource(currentItem.imgID)
        ratingBar.rating=currentItem.rating

         if(currentItem.name=="Guitar" || currentItem.name=="Electric Guitar"){
             switch.isVisible=true
             strapTxt.isVisible=true
         }
         else{
             switch.isVisible=false
             strapTxt.isVisible=false
             
            
         }
        switch.isChecked=false
        strapSelect=false

    }

}


