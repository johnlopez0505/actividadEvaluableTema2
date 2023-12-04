package com.john.actividadevaluabletema2.dao

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.AlarmClock
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.john.actividadevaluabletema2.R
import com.john.actividadevaluabletema2.databinding.ActivityPrincipalBinding

class Principal : AppCompatActivity() {
    private lateinit var bindingPrincipal : ActivityPrincipalBinding
    private lateinit var txtName     : TextView
    private lateinit var intent      : Intent
    companion object{
        const val url = "https://www.google.com/"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingPrincipal = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(bindingPrincipal.root)

        initEvent()
        initHander()
        login()
    }
    private fun initHander() {
        val handler = Handler(Looper.getMainLooper()) //queremos que el tema de la IU, la llevemos al hilo principal.
        bindingPrincipal.progressCircular.visibility = View.VISIBLE  //hacemos visible el progress
        bindingPrincipal.constraint.background = null
        loadGif()
        bindingPrincipal.layouPrincipal.visibility =   View.GONE //ocultamos el cardview.
        Thread{
            Thread.sleep( 1500)
            handler.post{
                bindingPrincipal.progressCircular.visibility = View.GONE //ocultamos el progress
                hideGif()// ocultamos el gif.
                bindingPrincipal.constraint.background = ContextCompat.getDrawable(this,
                    R.drawable.fondo_terror
                )
                bindingPrincipal.layouPrincipal.visibility = View.VISIBLE
                Toast.makeText(this, "Estamos en la pantalla principal",
                    Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun login() {
        txtName = bindingPrincipal.txtInputLogin
        val name = getIntent().getStringExtra("name")
        if (name!= null){
            txtName.text = "$name"
        }
    }

    private fun initEvent() {
        val message = "despertar"
        val hour = 7
        val minutes = 30

        bindingPrincipal.btnCall.setOnClickListener {
            intent = Intent(this, Second::class.java).apply {
                putExtra("name", "LLamada de Emergencia")
            }
            startActivity(intent)
        }

        bindingPrincipal.btnDados.setOnClickListener{
            intent = Intent(this, EntradaDatos::class.java)
            Toast.makeText(this, "Entrada de datos", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }


        bindingPrincipal.btnChiste.setOnClickListener{
            intent = Intent(this, SintetizarTextoEnVoz::class.java)
            Toast.makeText(this, "Abrindo cuenta chistes", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }


        bindingPrincipal.btnUrl.setOnClickListener{
            intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            }
            startActivity(intent)
        }

        bindingPrincipal.btnAlarma.setOnClickListener{
            intent = Intent(AlarmClock.ACTION_SET_ALARM).apply{
                putExtra(AlarmClock.EXTRA_MESSAGE,message)
                putExtra(AlarmClock.EXTRA_HOUR,hour)
                putExtra(AlarmClock.EXTRA_MINUTES,minutes)
            }
            startActivity(intent)
        }


        val subject = "saludo"
        val content = "Hola clase PMP 23/24 "
        bindingPrincipal.btnEmail.setOnClickListener{
            intent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts("mailto","clase2Dam@educand.es",
                null)).apply {
                putExtra(Intent.EXTRA_SUBJECT,subject)
                putExtra(Intent.EXTRA_TEXT,content)
            }
            startActivity(intent)
        }

    }
    private fun loadGif(){
        val gifImageView = bindingPrincipal.imageLuces
        gifImageView.visibility = View.VISIBLE
        Glide.with(this).load(R.drawable.luces).centerCrop().into(gifImageView)
    }
    private fun hideGif(){
        bindingPrincipal.imageLuces.visibility = View.GONE
    }

}