package com.example.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var tv: TextView
    lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         tv = findViewById(R.id.tv)
         button = findViewById(R.id.button)
        button.setOnClickListener {
           api()
        }
    }

    fun api() {
 CoroutineScope(Dispatchers.IO).launch {
                var data = async {
                    fetchadvice()
                }.await()
     var data2 = async {
                    fetchadvice()
                }.await()
                if (data.isNotEmpty()&& data2.isNotEmpty()) {
                    updatetext(data,data2)

                }
            }
   }
    fun fetchadvice(): String {
        var rsponse = ""

        try {
            rsponse = URL("https://api.adviceslip.com/advice").readText(Charsets.UTF_8)
        } catch (e: Exception) {
            println("error")
        }
        return rsponse
    }

    suspend fun updatetext(data:String,data2:String)
    {


        withContext(Dispatchers.Main)
    {
        val jsonObject= JSONObject(data)
        val slip=jsonObject.getJSONObject("slip")
        val newone=slip.getString("advice")
        val jsonObject1= JSONObject(data2)
        val slip1=jsonObject1.getJSONObject("slip")
        val newone1=slip1.getString("advice")
        tv.text="advice1:$newone \nadvice2:$newone1 "
   }
    }
//suspend fun returnadvice(data: String): String{
//
//
//    return newone
//}
}