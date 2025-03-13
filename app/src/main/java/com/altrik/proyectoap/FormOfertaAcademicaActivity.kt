package com.altrik.proyectoap

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import java.util.*

class FormOfertaAcademicaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_oferta_academica_layout)

        val inputDuracion = findViewById<EditText>(R.id.inputDuracion)

        inputDuracion.setOnClickListener {
            val calendar = Calendar.getInstance()
            val timePicker = TimePickerDialog(this, { _, hour, minute ->
                inputDuracion.setText("$hour horas, $minute minutos")
            }, 0, 0, true) // true para formato de 24 horas
            timePicker.show()
        }

        val inputInicio = findViewById<EditText>(R.id.inputInicio)
        val inputFinal = findViewById<EditText>(R.id.inputFinal)

        inputInicio.setOnClickListener { showTimePicker(inputInicio) }
        inputFinal.setOnClickListener { showTimePicker(inputFinal) }
    }

    private fun showTimePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            editText.setText(String.format("%02d:%02d", selectedHour, selectedMinute))
        }, hour, minute, true) // true para formato 24 horas

        timePickerDialog.show()
    }
}