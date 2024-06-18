package com.rayelectronic.pruebaparcial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val boton=findViewById<Button>(R.id.boton_respuesta)
        boton.setOnClickListener {
            Log.d("valor","valor entro")
            textView = findViewById(R.id.textView)
            createRoutes()
        }


    }

    fun retrofitroutes():Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("http://10.0.2.2:80/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun createRoutes() {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("entro", "entro...........")
            val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val call = retrofitroutes().create(ApiService::class.java).getRoutes(currentDate)
            Log.d("fecha",currentDate.toString())
            if (call.isSuccessful) {
                val response = call.body()
                Log.d("valores",call.body().toString())
                if (response != null) {
                    if (response.DATA != null && response.SUCCESS ) {
                        val data = response.DATA
                        val displayText = data.joinToString(separator = "\n") { menu ->
                            "ID: ${menu.ID_MENU}, Fecha: ${menu.FECHA}, Nombre: ${menu.nombre}"
                        }
                        withContext(Dispatchers.Main) {
                            textView.text = displayText
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            textView.text = "No hay data para mostrar"
                        }
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    textView.text = "Error: ${call.errorBody()?.string()}"
                }
            }

        }
    }



}