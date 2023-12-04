package com.john.actividadevaluabletema2.models

// Clase Usuario
data class Usuario(
    var name: String,
    var email: String,
    var password: String
)

object Usuarios {
    var listaUsuarios: MutableList<Usuario> = mutableListOf()

    init {
        agregarUsuariosIniciales()
    }

    private fun agregarUsuariosIniciales() {
        // ... (código para agregar usuarios iniciales)
        val u1 = Usuario("john","lopezcon1@hotmail.com","1234")
        val u2 = Usuario("juan","juan@hotmail.com","1234")
        val u3 = Usuario("sonia","sonia@hotmail.com","1234")
        val u4 = Usuario("dario","dario@hotmail.com","1234")
        val u5 = Usuario("francisco","francisco@hotmail.com","1234")
        listaUsuarios.add(u1)
        listaUsuarios.add(u2)
        listaUsuarios.add(u3)
        listaUsuarios.add(u4)
        listaUsuarios.add(u5)
    }
}
