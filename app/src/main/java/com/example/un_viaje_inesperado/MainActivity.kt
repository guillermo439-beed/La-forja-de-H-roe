package com.example.un_viaje_inesperado

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val TAG = "RivendellPortal"

    // Variable para llevar la cuenta de los miembros
    private var contador = 0

    // Referencias a las vistas, las inicializamos en onCreate
    private lateinit var tvSaludo: TextView
    private lateinit var tvContador: TextView
    private lateinit var btnForjar: Button
    private lateinit var btnIrCreador: Button

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate: El portal de Rivendell se abre. El concilio se reúne.")

        // Vinculamos las variables con los elementos del XML mediante su id
        tvSaludo = findViewById(R.id.tvSaludo)
        tvContador = findViewById(R.id.tvContador)
        btnForjar = findViewById(R.id.btnForjar)
        btnIrCreador = findViewById(R.id.btnIrCreador)

        // Listener del botón
        btnForjar.setOnClickListener {
            contador++
            tvSaludo.text = "¡La Comunidad del Anillo ha partido hacia Mordor!"
            tvContador.text = "Miembros reunidos: $contador"
        }

        // Navegación hacia "El Creador de Personajes de la Tierra Media"
        btnIrCreador.setOnClickListener {
            startActivity(Intent(this, CharacterCreatorActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: Los miembros se preparan para el viaje.")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: La Comunidad avanza por la Tierra Media.")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Un descanso en Lothlórien.")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: La Comunidad se separa en Amon Hen.")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: El Anillo es destruido en el Monte del Destino.")
    }
}
