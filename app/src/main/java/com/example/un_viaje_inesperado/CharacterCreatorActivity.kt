package com.example.un_viaje_inesperado

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * Estructura de datos que representa al personaje configurado
 * en "El Creador de Personajes de la Tierra Media".
 */
data class PersonajeTierraMedia(
    val nombre: String,
    val raza: String,
    val faccion: String,
    val sigilo: Boolean,
    val combateEspada: Boolean
)

class CharacterCreatorActivity : AppCompatActivity() {

    private val TAG = "ForjaDelHeroe"

    // Referencias a las vistas
    private lateinit var etNombre: EditText
    private lateinit var spinnerRaza: Spinner
    private lateinit var rgFaccion: RadioGroup
    private lateinit var cbSigilo: CheckBox
    private lateinit var cbEspada: CheckBox
    private lateinit var btnRegistrar: ImageButton

    /**
     * Función lambda encargada de registrar en el Logcat la estructura
     * (data class) del personaje que el usuario ha configurado.
     */
    private val registrarPersonajeEnLogcat: (PersonajeTierraMedia) -> Unit = { personaje ->
        Log.d(TAG, "Nuevo personaje registrado -> $personaje")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_creator)
        Log.d(TAG, "onCreate: Se abre la Forja del Héroe.")

        // Vinculación de vistas
        etNombre = findViewById(R.id.etNombre)
        spinnerRaza = findViewById(R.id.spinnerRaza)
        rgFaccion = findViewById(R.id.rgFaccion)
        cbSigilo = findViewById(R.id.cbSigilo)
        cbEspada = findViewById(R.id.cbEspada)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        configurarSpinnerRaza()
        solicitarFocoEnNombre()

        // 5. Al pulsar el botón de registro: Toast resumen + Log mediante lambda
        btnRegistrar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()

            if (nombre.isEmpty()) {
                Toast.makeText(this, getString(R.string.toast_nombre_vacio), Toast.LENGTH_SHORT).show()
                etNombre.requestFocus()
                return@setOnClickListener
            }

            if (rgFaccion.checkedRadioButtonId == -1) {
                Toast.makeText(this, getString(R.string.toast_faccion_vacia), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val raza = spinnerRaza.selectedItem.toString()
            val faccion = findViewById<android.widget.RadioButton>(rgFaccion.checkedRadioButtonId).text.toString()

            val personaje = PersonajeTierraMedia(
                nombre = nombre,
                raza = raza,
                faccion = faccion,
                sigilo = cbSigilo.isChecked,
                combateEspada = cbEspada.isChecked
            )

            // Registro en Logcat a través de la función lambda
            registrarPersonajeEnLogcat(personaje)

            // Toast resumen con la configuración del personaje
            val habilidades = buildString {
                if (personaje.sigilo) append(getString(R.string.habilidad_sigilo))
                if (personaje.sigilo && personaje.combateEspada) append(", ")
                if (personaje.combateEspada) append(getString(R.string.habilidad_espada))
                if (!personaje.sigilo && !personaje.combateEspada) append("ninguna")
            }

            val resumen = "${personaje.nombre} · ${personaje.raza} · ${personaje.faccion}\n" +
                    "Habilidades: $habilidades"

            Toast.makeText(this, resumen, Toast.LENGTH_LONG).show()
        }
    }

    /** 2. Carga el Spinner de razas a partir del array definido en strings.xml */
    private fun configurarSpinnerRaza() {
        ArrayAdapter.createFromResource(
            this,
            R.array.razas_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerRaza.adapter = adapter
        }
    }

    /** 1. El EditText del nombre debe ganar el foco automáticamente al iniciar */
    private fun solicitarFocoEnNombre() {
        etNombre.requestFocus()
        etNombre.post {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(etNombre, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}
