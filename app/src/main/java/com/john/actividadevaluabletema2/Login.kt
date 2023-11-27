package com.john.actividadevaluabletema2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.john.actividadevaluabletema2.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var bindingLogin : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingLogin = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindingLogin.root)
        initEvents()
    }

    private fun initEvents() {
        bindingLogin.buttonLogin.setOnClickListener {
            validarCredenciales()
        }

        bindingLogin.buttonRegistrar.setOnClickListener{
            registerUser()
        }

    }

    private fun validarCredenciales() {
        val user = bindingLogin.editTextUsername.text.toString()
        val password = bindingLogin.editTextPassword.text.toString()
        val usuarioEncontrado = Usuarios.listaUsuarios.find { it.name==user && it.password ==password }

        if (usuarioEncontrado != null) {
            // El usuario ha iniciado sesión con éxito
            // Credenciales válidas, iniciar Activity principal
            val intent = Intent(this, Principal::class.java)
            intent.putExtra("name", user)  // Pasa el usuario como argumento al Activity principal
            startActivity(intent)
        } else {
            // Las credenciales no son válidas
            Toast.makeText(this, "Credenciales no válidas", Toast.LENGTH_SHORT).show()
        }

    }
    private fun registerUser() {
        val  intent = Intent(this,Register::class.java)
        startActivity(intent)

    }
}
