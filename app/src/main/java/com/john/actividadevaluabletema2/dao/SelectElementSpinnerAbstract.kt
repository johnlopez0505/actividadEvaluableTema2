package com.john.actividadevaluabletema2.dao

import android.content.Context
import android.view.View
import android.widget.AdapterView

abstract class SelectElementSpinnerAbstract (val context : Context) : AdapterView.OnItemSelectedListener{
    lateinit var element : String

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}