package com.example.flagquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.children
import com.example.flagquiz.Models.flag
import com.example.flagquiz.databinding.ActivityMainBinding
import java.util.Random


class MainActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding: ActivityMainBinding
    private lateinit var flagArrayList: ArrayList<flag>
    private var count = 0
    private var countryName = ""
    private lateinit var buttonArratList:ArrayList<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonArratList = ArrayList()

        createObject()
        btnJoylaCount()
    }

    private fun createObject() {
        flagArrayList = ArrayList()
        flagArrayList.add(flag("uzbekistan", R.drawable.uzb))
        flagArrayList.add(flag("russian", R.drawable.rus))
        flagArrayList.add(flag("china", R.drawable.china))
        flagArrayList.add(flag("england", R.drawable.uk))
        flagArrayList.add(flag("india", R.drawable.india))
        flagArrayList.add(flag("tajikistan", R.drawable.tajik))
        flagArrayList.add(flag("dubai", R.drawable.dubai))
    }

    private fun btnJoylaCount(){
        binding.image.setImageResource(flagArrayList[count].image!!)
        binding.linear1.removeAllViews()
        binding.linear2.removeAllViews()
        binding.linear3.removeAllViews()
        countryName = ""
        btnJoyla(flagArrayList[count].name)
    }

    private fun btnJoyla(countryName: String?) {
        val btnArray: ArrayList<Button> = randomBtn(countryName)
        for (i in 0..5){
            binding.linear2.addView(btnArray[i])
        }
        for (i in 6..11){
            binding.linear3.addView(btnArray[i])
        }
    }

    private fun randomBtn(countryName: String?): ArrayList<Button> {
        val array = ArrayList<Button>()
        val arrayText = ArrayList<String>()

        for (c in countryName!!){
            arrayText.add(c.toString())
        }
        if (arrayText.size != 12){
            val str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            for (i in arrayText.size until 12){
                val random = Random().nextInt(str.length)
                arrayText.add(str[random].toString())
            }
        }
        arrayText.shuffle()

        for (i in 0 until arrayText.size){
            val button = Button(this)
            button.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f)
            button.text = arrayText[i]
            button.setOnClickListener(this)
            array.add(button)
        }
        return array
    }

    override fun onClick(v: View?) {
        val button1 = v as Button
        if (buttonArratList.contains(button1)){
            binding.linear1.removeView(button1)
            var hasC = false
            binding.linear2.children.forEach { button ->
                if ((button as Button).text.toString() == button1.text.toString()){
                    button.visibility = View.VISIBLE
                    countryName = countryName.substring(0,countryName.length-1)
                    hasC = true
                }
            }
            binding.linear3.children.forEach { button ->
                if ((button as Button).text.toString() == button1.text.toString()){
                    button.visibility = View.VISIBLE
                    if (!hasC){
                        countryName = countryName.substring(0,countryName.length-1)
                    }
                }
            }
        }else{
            button1.visibility = View.INVISIBLE
            countryName += button1.text.toString().toUpperCase()
            val button2 = Button(this)
            button2.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f)
            button2.text = button1.text
            button2.setOnClickListener(this)
            buttonArratList.add(button2)
            binding.linear1.addView(button2)
            matnTogri()
        }
    }

    private fun matnTogri() {
        if (countryName == flagArrayList[count].name?.toUpperCase()){
            Toast.makeText(this, "successful", Toast.LENGTH_SHORT).show()
            if (count == flagArrayList.size-1){
                count = 0
            }else{
                count++
            }
            btnJoylaCount()
        }else{
            if (countryName.length == flagArrayList[count].name?.length){
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                binding.linear1.removeAllViews()
                binding.linear2.removeAllViews()
                binding.linear3.removeAllViews()
                btnJoyla(flagArrayList[count].name)
                countryName = ""
            }
        }
    }
}









