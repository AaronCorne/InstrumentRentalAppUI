package com.example.assignment2rentwithintent

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class SecondActivity: AppCompatActivity() {

    private lateinit var itemName: TextView
    private lateinit var itemDesc:TextView
    private lateinit var itemImg: ImageView
    private lateinit var itemCredit: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var rentTime: TextView
    private lateinit var rechargeY: Button
    private lateinit var rechargeN: Button
    private lateinit var recharge: TextView
    private lateinit var termsCheck: CheckBox
    private lateinit var privacyCheck: CheckBox

    private var totalCredit=500
    private val rentDuration = listOf("1 month", "3 months", "6 months","9 months", "12 months")

    private lateinit var item: MusicalEquipment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)

        item = if(Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra("Instrument", MusicalEquipment::class.java)?: return
        }else{
            intent.getParcelableExtra("Instrument")?: return

        }

        totalCredit= intent.getIntExtra("totalCredits",500)
        Log.d("SecondActivity", "Instrument received: $item")
        Log.d("SecondActivity", "Total credits: $totalCredit")



        val saveBtn=findViewById<Button>(R.id.save)
        val cancelBtn=findViewById<Button>(R.id.cancel)
        itemImg=findViewById(R.id.borrowImg)
        itemName= findViewById(R.id.name)
        itemDesc=findViewById(R.id.description)
        itemCredit=findViewById(R.id.borrowCredit)
        seekBar=findViewById(R.id.rent)
        rentTime=findViewById(R.id.rentTime)
        rechargeN=findViewById(R.id.rechargeN)
        rechargeY=findViewById(R.id.rechargeY)
        recharge=findViewById(R.id.recharge)
        termsCheck=findViewById(R.id.termsCheck)
        privacyCheck=findViewById(R.id.privacyCheck)


        rentTime.text=rentDuration[0] //so the slider starts on 1 month of rent

        showDetail()

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                rentTime.text=rentDuration[progress]

        }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //Not needed
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //Not needed
            }
        })


        if (totalCredit < item.price){
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    Snackbar.make(
                        findViewById(R.id.rootLayout),
                        "Looks like you are out of credits, by pressing SAVE you will be able to recharge credits",
                        Snackbar.LENGTH_LONG
                    ).setBackgroundTint(getColor(R.color.blue1))
                        .show()
                },1000)
            Log.d("SecondActivity", "Not enough credits to rent: $item")
        }


        saveBtn.setOnClickListener{ //if the save button is clicked and have enough credits...
            if(totalCredit>= item.price){
                if(!termsCheck.isChecked || !privacyCheck.isChecked){
                    Toast.makeText(this, "Please ensure you have checked BOTH the Terms and Privacy checkboxes!", Toast.LENGTH_SHORT).show()
                }else{
                    totalCredit-=item.price
                    item.itemBooked= true
                    Log.d("SecondActivity", "Instrument rented successfully: $item")
                    Toast.makeText(this, "Successfully borrowed!", Toast.LENGTH_SHORT).show()
                    val resultIntent=intent
                    resultIntent.putExtra("updateCredit", totalCredit)
                    //resultIntent.putExtra("rentDuration", rentDuration[seekBar.progress])
                    resultIntent.putExtra("updatedItem", item)
                    resultIntent.putExtra("selectedRent", rentDuration[seekBar.progress])
                    setResult(RESULT_OK, resultIntent)

                    finish()
                }


            }
            else {

                Toast.makeText(this, "Not enough credits to borrow this item!", Toast.LENGTH_SHORT).show()
                recharge.visibility= View.VISIBLE
                rechargeN.visibility= View.VISIBLE
                rechargeY.visibility= View.VISIBLE
                Log.d("SecondActivity", "Recharge for credits to rent $item")

                rechargeN.setOnClickListener{
                    recharge.visibility= View.INVISIBLE
                    rechargeN.visibility= View.INVISIBLE
                    rechargeY.visibility= View.INVISIBLE
                    Log.d("SecondActivity", "Recharge declined for: $item")
                }

                rechargeY.setOnClickListener{
                    totalCredit=500
                    itemCredit.text=getString(R.string.credits,totalCredit)
                    recharge.visibility= View.INVISIBLE
                    rechargeN.visibility= View.INVISIBLE
                    rechargeY.visibility= View.INVISIBLE
                    Toast.makeText(this, "Recharged!", Toast.LENGTH_SHORT).show()
                    Log.d("SecondActivity", "Recharge successful for: $item")
                }

            }
            showDetail()


        }

        cancelBtn.setOnClickListener{
            finish()
            Toast.makeText(this, "Cancelled!", Toast.LENGTH_SHORT).show()
            Log.d("SecondActivity", "Rental cancelled for: $item")
        }




    }


    //functions

    fun showDetail() {
        itemName.text=item.name
        itemImg.setImageResource(item.imgID)
        itemCredit.text=getString(R.string.credits,totalCredit)
        val strapInfo= if(item.strapSelected){
            getString(R.string.With_strap)
        }else{
           ""
        }

        itemDesc.text= getString(R.string.fullDesc,item.desc,strapInfo, item.price)


    }



}