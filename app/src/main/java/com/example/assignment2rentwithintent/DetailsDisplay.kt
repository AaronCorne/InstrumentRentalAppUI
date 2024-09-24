package com.example.assignment2rentwithintent

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailsDisplay:AppCompatActivity() {

    private lateinit var detailsStatus: TextView
    private lateinit var detailsName: TextView
    private lateinit var detailsDesc: TextView
    private lateinit var detailsPrice: TextView
    private lateinit var bookingStatus: TextView
    private lateinit var duration: TextView
    private lateinit var backBtn: Button
    private lateinit var strapTxt: TextView



    private lateinit var item: MusicalEquipment
    private lateinit var selectedRent:String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_activity)

        detailsStatus = findViewById(R.id.details)
        detailsName = findViewById(R.id.detailsName)
        detailsDesc = findViewById(R.id.detailsDesc)
        detailsPrice = findViewById(R.id.detailsPrice)
        duration = findViewById(R.id.duration)
        bookingStatus = findViewById(R.id.bookingStatus)
        backBtn=findViewById(R.id.backBtn)
        strapTxt=findViewById(R.id.strap)

        item = if(Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra("Instrument", MusicalEquipment::class.java)?: return
        }else{
            intent.getParcelableExtra("Instrument")?: return

        }

        selectedRent=intent.getStringExtra("selectedRent")?: "No rent selected"
        showDetail(item)

        backBtn.setOnClickListener{
            finish()
        }





    }

    fun showDetail(music:MusicalEquipment){
        detailsName.text=item.name
        detailsDesc.text=item.desc
        detailsPrice.text=getString(R.string.price,item.price)


        if(item.itemBooked){
            bookingStatus.text=getString(R.string.rentedItem)
            duration.text=selectedRent


        }
        else{
            bookingStatus.text=getString(R.string.notRentedItem)

        }

        if(item.strapOption){
            if(item.strapSelected){
                strapTxt.text=getString(R.string.With_strap)
                strapTxt.visibility= View.VISIBLE

            }
            else{
                strapTxt.text=getString(R.string.Without_strap)
                strapTxt.visibility= View.VISIBLE
            }

        }
        else{
            strapTxt.visibility= View.GONE

        }

    }

}