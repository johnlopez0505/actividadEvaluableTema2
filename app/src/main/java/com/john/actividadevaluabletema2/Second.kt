package com.john.actividadevaluabletema2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.john.actividadevaluabletema2.databinding.ActivitySecondBinding

class Second : AppCompatActivity() {
    private lateinit var bindingSecond: ActivitySecondBinding
    companion object{
        const val PHONE = "623260768"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingSecond = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(bindingSecond.root)

        initEvent()
        showData()
    }

    private fun showData() {
        val txtName = bindingSecond.marcar
        val name = intent.getStringExtra("name")
        txtName.text = name
        Toast.makeText(this, "Datos mostrados con éxito", Toast.LENGTH_LONG).show()
    }

    private fun initEvent() {
        bindingSecond.btnLlamar.setOnClickListener {
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        if (Build.VERSION. SDK_INT >= Build.VERSION_CODES. M){
            if (permissionPhone()){
                call()
            }
            else{
                requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
            }
        }else{
            call()
        }
    }

    private fun call() {
        val intent = Intent(Intent. ACTION_CALL).apply {
            data = Uri.parse( "tel:$PHONE")
        }
        startActivity(intent)
    }


    private fun permissionPhone(): Boolean = ContextCompat.checkSelfPermission( this,
        Manifest.permission.CALL_PHONE) == PackageManager. PERMISSION_GRANTED



    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.
    RequestPermission()) {  isGranted ->
        if (isGranted) {
            call()
        } else {
            Toast.makeText(
                this, "Necesitas habilitar los permisos", Toast.LENGTH_LONG).show()
        }
    }
}

