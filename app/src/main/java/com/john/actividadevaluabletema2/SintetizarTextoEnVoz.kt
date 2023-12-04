package com.john.actividadevaluabletema2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.john.actividadevaluabletema2.databinding.ActivitySintetizarTextoEnVozBinding
import java.util.Locale

class SintetizarTextoEnVoz : AppCompatActivity() {
    private lateinit var bindingSintetizar : ActivitySintetizarTextoEnVozBinding
    private lateinit var textToSpeech: TextToSpeech  //descriptor de voz
    private val TOUCH_MAX_TIME = 500 // en milisegundos
    private var touchLastTime: Long = 0  //para saber el tiempo entre toque.
    private var touchNumber = 0   //numero de toques dado (por si acaso). De momento no nos hace falta.
    private lateinit var handler: Handler
    private  lateinit var chisteNumero : String
    val MYTAG = "LOGCAT"  //para mirar logs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingSintetizar = ActivitySintetizarTextoEnVozBinding.inflate(layoutInflater)
        setContentView(bindingSintetizar.root)

        configureTextToSpeech()  //configuramos nuestro textToSpeech
        initHander()    //lanzaremos un hilo para el progressBar. No es necesario un hilo.
        initEvent()     //Implementación del botón.
    }

    private fun initHander() {
        handler = Handler(Looper.getMainLooper())  //queremos que el tema de la IU, la llevemos al hilo principal.
        bindingSintetizar.progressBar.visibility = View.VISIBLE  //hacemos visible el progress
        loadGif() // cargamos el gif de fondo.
        bindingSintetizar.btnExample.visibility = View.GONE  //ocultamos el botón.
        bindingSintetizar.btnSalir.visibility = View.INVISIBLE//ocultamos el boton salir
        Thread{
            Thread.sleep(3000)
            handler.post{
                bindingSintetizar.progressBar.visibility = View.GONE  //ocultamos el progress
                hideGif()// ocultamos el gif.
                loadGif2()// cargamos el gif2.
                bindingSintetizar.constraint.setBackgroundColor(ContextCompat.getColor(this,
                    R.color.claro))
                val description = getString(R.string.describe).toString()
                speakMeDescription(description)  //que nos comente de qué va esto...
                Log.i(MYTAG,"Se ejecuta correctamente el hilo")
                bindingSintetizar.btnExample.visibility = View.VISIBLE
                bindingSintetizar.btnSalir.visibility = View.VISIBLE
            }
        }.start()
    }


    private fun configureTextToSpeech() {
        textToSpeech = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {
            if(it != TextToSpeech.ERROR){
                textToSpeech.language = Locale.getDefault()
                // textToSpeech.setSpeechRate(1.0f)
                Log.i(MYTAG,"Sin problemas en la configuración TextToSpeech")
            }else{
                Log.i(MYTAG,"Error en la configuración TextToSpeech")
            }
        })
    }


    private fun initEvent() {
        bindingSintetizar.btnSalir.setOnClickListener{
            Toast.makeText(this, "Regresando a la panatalla principal", Toast.LENGTH_SHORT).show()
            finish()
        }
        bindingSintetizar.btnExample.setOnClickListener{
            //Sacamos el tiempo actual
            val currentTime = System.currentTimeMillis()
            //Comprobamos si el margen entre pulsación, da lugar a una doble pulsación.
            if (currentTime - touchLastTime < TOUCH_MAX_TIME){
                //  touchNumber=0
                chisteNumero = (0..Chistes.chistes.size).random().toString()
                executorDoubleTouch(Chistes.chistes[chisteNumero.toInt()])  //hemos pulsado dos veces, por tanto lanzamos el chiste.
                Log.i(MYTAG,"Escuchamos el chiste")
            }
            else{
                //  touchNumber++
                Log.i(MYTAG,"Hemos pulsado 1 vez.")
                //Describimos el botón, 1 sóla pulsación
                speakMeDescription("Botón para escuchar un chiste")
            }
            touchLastTime = currentTime
            /*  if (touchNumber == 2) {
                  Log.i(MYTAG,"Detectamos 2 pulsaciones.")
                  touchNumber = 0
              }
  */

        }  //fin listener
    }

    //Habla
    private fun speakMeDescription(s: String) {
        Log.i(MYTAG,"Intenta hablar")
        textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    private fun executorDoubleTouch(chiste: String) {
        speakMeDescription(chiste)
        Toast.makeText(this,"doble pulsacion-> Chiste $chisteNumero de ${Chistes.chistes.size}",Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        //Si hemos inicializado la propiedad textToSpeech, es porque existe.
        if (::textToSpeech.isInitialized){
            textToSpeech.stop()
            textToSpeech.shutdown()

        }
        super.onDestroy()

    }

    private fun hideGif(){
        bindingSintetizar.imagenLuces.visibility = View.GONE
    }
    private fun loadGif(){
        val gifImageView = bindingSintetizar.imagenLuces
        gifImageView.visibility = View.VISIBLE
        Glide.with(this).load(R.drawable.luces).centerCrop().into(gifImageView)
    }

    private fun loadGif2(){
        val gifImageView = bindingSintetizar.imagenLuces
        gifImageView.visibility = View.VISIBLE
        Glide.with(this).load(R.drawable.risa).circleCrop().into(gifImageView)
    }
}