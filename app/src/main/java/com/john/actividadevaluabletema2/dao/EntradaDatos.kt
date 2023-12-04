package com.john.actividadevaluabletema2.dao

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.john.actividadevaluabletema2.databinding.ActivityEntradaDatosBinding
import com.john.actividadevaluabletema2.object_models.Jugadores

class EntradaDatos : AppCompatActivity() {
    private lateinit var bindingEntradaDatos : ActivityEntradaDatosBinding
    private var alumns : MutableList<String> = Jugadores.namesPlayer.toMutableList()
    private lateinit var spiner: String
    private lateinit var nivel : String
    private lateinit var tiradas : String
    private lateinit var adapter : ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingEntradaDatos = ActivityEntradaDatosBinding.inflate(layoutInflater)
        setContentView(bindingEntradaDatos.root)

        initEvent()
        initAdapter()
    }

    private fun initAdapter() {
        adapter = ArrayAdapter (
            this,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            alumns
        )
        bindingEntradaDatos.editAuto.setAdapter(adapter)
        bindingEntradaDatos.spinner.adapter = adapter
    }


    private fun initEvent() {
        proveBtnFloat()
        proveCheck()
        proveCheck()
        proveRadio()
        proveSwitch()
        proveSpinner()
        proveToggle()
        proveButtonActionEditor()
        proveButtonExit()
    }

    private fun proveButtonActionEditor() {
        //  Anonima con lambda
        bindingEntradaDatos.editText.setOnEditorActionListener{
                v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND){
                showMsg("Clase, capturo el evento del Action del teclado")
                true
            }else
                false
        }
    }

    private fun proveRadio(){
        bindingEntradaDatos.radioGroup.setOnCheckedChangeListener{
                group, chekedId ->
            when (chekedId){
                bindingEntradaDatos.btr1.id->{
                    showMsg("1 Tirada, Seleccionada")
                    tiradas = "1"
                }

                bindingEntradaDatos.btr2.id->{
                    showMsg("2 Tiradas, Seleccionadas")
                    tiradas = "2"

                }
                bindingEntradaDatos.btr3.id->{
                    showMsg("3 Tiradas, Seleccionadas")
                    tiradas = "3"
                }
                bindingEntradaDatos.btr4.id ->{
                    showMsg("4 Tiradas, seleccionadas")
                    tiradas = "4"
                }
                bindingEntradaDatos.btr5.id ->{
                    showMsg("5Tiradas, seleccionadas")
                    tiradas = "5"
                }
            }//fin when
        }//fin función lambda
    }



    private fun proveCheck(){

        bindingEntradaDatos.chk1.setOnCheckedChangeListener{
                btnView, isCheked ->
            if (isCheked){
                showMsg("Nivel Principiante, seleccionado")
                nivel = "Nivel: Principiante"
                // Desmarcar otros CheckBox
                bindingEntradaDatos.chk2.isChecked = false
                bindingEntradaDatos.chk3.isChecked = false

            }else{
                showMsg("Nivel principiante , Deseleccionado")
            }
        }

        bindingEntradaDatos.chk2.setOnCheckedChangeListener{
                btnView, isCheked ->
            if (isCheked){
                showMsg("Nivel intermedio, seleccionado")
                nivel = "Nivel: Intermedio"
                // Desmarcar otros CheckBox
                bindingEntradaDatos.chk1.isChecked = false
                bindingEntradaDatos.chk3.isChecked = false
            }else{
                showMsg("Nivel intermedio, Deseleccionado")
            }
        }

        bindingEntradaDatos.chk3.setOnCheckedChangeListener{
                btnView, isCheked ->
            if (isCheked){
                showMsg("Nivel Experto, seleccionado")
                nivel = "Nivel: Experto"
                // Desmarcar otros CheckBox
                bindingEntradaDatos.chk1.isChecked = false
                bindingEntradaDatos.chk2.isChecked = false
            }else{
                showMsg("Nivel Experto, Deseleccionado")
            }
        }
    }

    private fun proveSwitch(){
        bindingEntradaDatos.switchBtn.setOnCheckedChangeListener {
                buttonView, isChecked ->
            if (isChecked){
                showMsg("Boton Switch activado")
            }else{
                showMsg("Boton Switch Desactivado")
            }
        }
    }



    private fun proveSpinner(){
        /*
        Con clase Abstracta y sobreescribiendo los métodos abstractos de la interfaz
        De todas formas, tengo que crear objeto de la clase Abstracta, ya que tengo que
        implementar sus métodos.
         */
        bindingEntradaDatos.spinner.onItemSelectedListener =
            object : SelectElementSpinnerAbstract(this){
                @SuppressLint("SuspiciousIndentation")
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val select = parent?.getItemAtPosition(position).toString()
                    spiner = select

                    if (select != "Selecciona uno")
                        bindingEntradaDatos.editAuto.setText(spiner)
                        showMsg(parent?.getItemAtPosition(position).toString()+"--")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    showMsg("No selecciono nada")
                }
            }
    }


    private fun proveToggle(){
        bindingEntradaDatos.toggBtn.setOnCheckedChangeListener {
                buttonView, isChecked ->
            if (isChecked){
                showMsg("Boton Toggle activado")
            }else{
                showMsg("Boton Toggle Desactivado")
            }
        }
    }


    private fun proveBtnFloat(){

        bindingEntradaDatos.btnFloat.setOnClickListener {
            showMsg(bindingEntradaDatos.editText.text.toString())
        }

       //Ejemplo de como implementar el listener con clase anónima
        val event : View.OnClickListener
        event = object : View.OnClickListener{
            override fun onClick(v: View?) {
                if (comprobarEstado()){
                    abrirJuegoDeDados()
                    showMsg("Juego de los dados")
                }

            }
        }

        /*
        botón, espera a recibir un objeto event.
        Cuando lo recibas, llama implícitamente al método onClick del objeto event.
         */
        bindingEntradaDatos.btnFloat.setOnClickListener(event)

    }

    private fun showMsg(cad : String){
        Toast.makeText(this, cad, Toast.LENGTH_LONG).show()
    }

    private fun abrirJuegoDeDados() {
        val intent = Intent(this, Dados::class.java)
        intent.putExtra("name", spiner)
        intent.putExtra("novel", nivel)
        intent.putExtra("rolls",tiradas)
        intent.putExtra("nameEditAuto",bindingEntradaDatos.editAuto.text.toString())
        intent.putExtra("age",bindingEntradaDatos.editText.text.toString())
        startActivity(intent)
    }


    private fun comprobarEstado() : Boolean{
        val edad = bindingEntradaDatos.editText .text.toString()
        val name = bindingEntradaDatos.editAuto.text.toString()
        val check1 = bindingEntradaDatos.chk1
        val check2 = bindingEntradaDatos.chk2
        val check3 = bindingEntradaDatos.chk3
        val t1 = bindingEntradaDatos.btr1
        val t2 = bindingEntradaDatos.btr2
        val t3 = bindingEntradaDatos.btr3
        val t4 = bindingEntradaDatos.btr4
        val t5 = bindingEntradaDatos.btr5
        // Verificar que ningún campo esté vacío
        if (name.isEmpty() || edad.isEmpty()){
            // Mostrar un mensaje de error si algún campo está vacío
            val campo = "Campo no puede estar vacio"
            bindingEntradaDatos.editText.error = campo
            bindingEntradaDatos.editAuto.error = campo
            Toast.makeText(
                this, "Todos los campos deben ser completados",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        else if (!check1.isChecked && !check2.isChecked && !check3.isChecked ){
            // Mostrar un mensaje de error si algún campo está vacío
            Toast.makeText(
                this, "Elige un Nivel",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        else if (!t1.isChecked && !t2.isChecked && !t3.isChecked && !t4.isChecked && !t5.isChecked){
            // Mostrar un mensaje de error si algún campo está vacío
            Toast.makeText(
                this, "Elige Cuantas Tiradas Quieres",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true
    }

    private fun proveButtonExit(){
        bindingEntradaDatos.btnSalir.setOnClickListener{
            Toast.makeText(this, "Regresando a la panatalla principal",
                Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
