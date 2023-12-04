package com.john.actividadevaluabletema2

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.Toast

class SelectElementSpinner(val context: Context) : AdapterView.OnItemSelectedListener {
  lateinit var element : String
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        element = parent?.getItemAtPosition(position).toString()
        Toast.makeText(context as EntradaDatos, element+"!!", Toast.LENGTH_LONG).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(context as EntradaDatos, "No selecciono nada", Toast.LENGTH_LONG).show()

    }
}