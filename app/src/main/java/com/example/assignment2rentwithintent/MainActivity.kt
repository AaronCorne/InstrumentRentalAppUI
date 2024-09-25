package com.example.assignment2rentwithintent

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar

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
    private lateinit var credits: TextView


    private var currentIndex= 0
    var currentCredit=500
    private var selectedRent:String="" //initialise as an empty string


    private val activityLauncher=registerForActivityResult( //for when we are sending data and receiving data back
        ActivityResultContracts.StartActivityForResult()

    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            currentCredit = result.data!!.getIntExtra("updateCredit", 500)
            credits.text = getString(R.string.credits, currentCredit)
            val updatedItem= result.data?.getParcelableExtra<MusicalEquipment>("updatedItem")
            selectedRent= result.data?.getStringExtra("selectedRent")?:"Rent not selected"

            Log.d("MainActivity", "Received updated credit: $currentCredit")
            Log.d("MainActivity", "Received updated item: $updatedItem")
            Log.d("MainActivity", "Received selected rent duration: $selectedRent")

            if(updatedItem!=null){
                musicItems[currentIndex]=updatedItem
                showItem(currentIndex)
            }

        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemName=findViewById(R.id.itemName)
        itemImg=findViewById(R.id.itemImg)
        itemPrice=findViewById(R.id.detailsPrice)
        switch=findViewById(R.id.switch1)
        nextBtn=findViewById(R.id.Next)
        previousBtn=findViewById(R.id.Previous)
        borrowBtn=findViewById(R.id.borrow)
        ratingBar=findViewById(R.id.itemRatingBar)
        strapTxt=findViewById(R.id.switchTxt)
        credits=findViewById(R.id.credits)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                Snackbar.make(itemImg, "Click the image to view details!", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(ContextCompat.getColor(this,R.color.blue1)).show()
            }, 2000) // Delay in milliseconds




        musicItems= arrayListOf(
            MusicalEquipment("Guitar",true,4.5f, 250, R.drawable.guitar, R.drawable.bookedgtar, "A beautiful hand crafted guitar",),
            MusicalEquipment("Drums",false, 3.0f,  100,R.drawable.drums, R.drawable.bookeddrum,"Drums to make you march to the beat"),
            MusicalEquipment("Electric Guitar", true, 5.0f,  350, R.drawable.electric_gtar,R.drawable.bookedelectric,"An all time rock and roller"),
            MusicalEquipment("Amp",false, 3.5f, 200, R.drawable.amp, R.drawable.bookedamp, "When you need to turn it up, you get one of these",)

        )
        Log.d("MainActivity", "Music items initialised: $musicItems")
        showItem(currentIndex)


        itemImg.setOnClickListener{
            val intent= Intent(this, DetailsDisplay::class.java)
            intent.putExtra("Instrument",musicItems[currentIndex])
            intent.putExtra("selectedRent",selectedRent)
            Log.d("MainActivity", "Starting DetailsDisplay activity for:${musicItems[currentIndex]}")
            activityLauncher.launch(intent)
        }

        switch.setOnCheckedChangeListener{_, isChecked ->
            if(musicItems[currentIndex].strapOption){
                musicItems[currentIndex].strapSelected=isChecked

            }
            else{
                musicItems[currentIndex].strapSelected=false
            }


        }

        //Next button
        nextBtn.setOnClickListener{
            currentIndex+=1
            if(currentIndex>=musicItems.size){
                currentIndex=0
            }
            Log.d("MainActivity", "Showing next item: ${musicItems[currentIndex]}")
            showItem(currentIndex)
        }

        //Previous button
        previousBtn.setOnClickListener{
            currentIndex-=1
            if(currentIndex<0){
                currentIndex= musicItems.size-1
            }
            Log.d("MainActivity", "Showing previous item: ${musicItems[currentIndex]}")
            showItem(currentIndex)
        }

        borrowBtn.setOnClickListener{
            if(borrowBtn.isEnabled){
                val intent= Intent(this, SecondActivity::class.java)
                intent.putExtra("Instrument",musicItems[currentIndex])
                intent.putExtra("totalCredits",currentCredit)
                Log.d("MainActivity", "Starting SecondActivity for: ${musicItems[currentIndex]}")
                activityLauncher.launch(intent)
            }
            else{
                Toast.makeText(this,"Looks like this has already been rented!", Toast.LENGTH_SHORT).show()
            }



        }


    }

    //functions

    fun showItem(index:Int){
        val currentItem= musicItems[index]
        itemName.text= currentItem.name
        itemImg.setImageResource(currentItem.imgID)
        ratingBar.rating=currentItem.rating
        itemPrice.text=getString(R.string.price,currentItem.price)
        credits.text =getString(R.string.credits,currentCredit)

        if(currentItem.itemBooked){
            itemImg.setImageResource(currentItem.bookedImgId)
            borrowBtn.isEnabled=false
            borrowBtn.visibility=View.INVISIBLE



        }
        else{
            itemImg.setImageResource(currentItem.imgID)
            borrowBtn.isEnabled=true
            borrowBtn.visibility=View.VISIBLE
        }

         if(currentItem.strapOption){
             switch.isVisible=true
             strapTxt.isVisible=true
             switch.isChecked=currentItem.strapSelected
         }
         else{
             switch.isVisible=false
             strapTxt.isVisible=false
             switch.isChecked=false

            
         }
    }

}


