package com.example.assignment2rentwithintent

import android.os.Build
import android.os.Bundle
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

        showDetail(item)

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
        val parentView=findViewById<View>(R.id.rootLayout)

        if (totalCredit < item.price){
            Snackbar.make(
                findViewById(R.id.rootLayout), // Replace with the ID of your root view
                "Looks like you are out of credits, by pressing SAVE you will be able to recharge credits",
                Snackbar.LENGTH_LONG).show()
        }

        saveBtn.setOnClickListener{ //if the save button is clicked and have enough credits...
            if(totalCredit>= item.price){
                if(!termsCheck.isChecked || !privacyCheck.isChecked){
                    Toast.makeText(this, "Please ensure you have checked BOTH the Terms and Privacy checkboxes!", Toast.LENGTH_SHORT).show()
                }else{
                    totalCredit-=item.price
                    item.itemBooked= true
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

                rechargeN.setOnClickListener{
                    recharge.visibility= View.INVISIBLE
                    rechargeN.visibility= View.INVISIBLE
                    rechargeY.visibility= View.INVISIBLE
                }

                rechargeY.setOnClickListener{
                    totalCredit=500
                    itemCredit.text=getString(R.string.credits,totalCredit)
                    recharge.visibility= View.INVISIBLE
                    rechargeN.visibility= View.INVISIBLE
                    rechargeY.visibility= View.INVISIBLE
                }

            }
            showDetail(item)


        }

        cancelBtn.setOnClickListener{
            finish()
        }




    }


    //functions

    fun showDetail(music:MusicalEquipment){
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