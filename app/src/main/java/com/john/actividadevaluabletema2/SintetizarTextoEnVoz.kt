package com.john.actividadevaluabletema2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.john.actividadevaluabletema2.databinding.ActivitySintetizarTextoEnVozBinding
import java.util.Locale

class SintetizarTextoEnVoz : AppCompatActivity() {
    private lateinit var bindingSintetizar : ActivitySintetizarTextoEnVozBinding
    private lateinit var textToSpeech: TextToSpeech  //descriptor de voz
    private val TOUCH_MAX_TIME = 500 // en milisegundos
    private var touchLastTime: Long = 0  //para saber el tiempo entre toque.
    private var touchNumber = 0   //numero de toques dado (por si acaso)
    private lateinit var handler: Handler
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
        val img = bindingSintetizar.imagenLuces
        img.visibility = View.VISIBLE
        //val imageView = findViewById<ImageView>(R.id.imagenLuces)
        Glide.with(this).load(R.drawable.luces).into(img)
        bindingSintetizar.btnExample.visibility = View.GONE  //ocultamos el botón.

        Thread{
            Thread.sleep(3000)
            handler.post{
                bindingSintetizar.progressBar.visibility = View.GONE  //ocultamos el progress
                img.visibility = View.GONE
                val description = getString(R.string.describe).toString()
                speakMeDescription(description)  //que nos comente de qué va esto...
                Thread.sleep(4000)
                Log.i(MYTAG,"Se ejecuta correctamente el hilo")
                bindingSintetizar.btnExample.visibility = View.VISIBLE

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

    /*
    Como he dicho esta mañana, System.currentTimeMillis() devuelve el tiempo actual
    en milisegundos. Por cada click el touchLastTime se vuelve a actualizar, para hacer
    la diferencia de tiempos y comprobar si es menor de 500 msg.
     */
    private fun initEvent() {
        val chiste = resources.getString(R.string.chiste)
        bindingSintetizar.btnExample.setOnClickListener{
            //Sacamos el tiempo actual
            val currentTime = System.currentTimeMillis()
            //Comprobamos si el margen entre pulsación, da lugar a una doble pulsación.
            if (currentTime - touchLastTime < TOUCH_MAX_TIME){
                //  touchNumber=0
                executorDoubleTouch(chiste)  //hemos pulsado dos veces, por tanto lanzamos el chiste.
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
        // Toast.makeText(this,"doble pulsacion-> Ejecuto la acción",Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        //Si hemos inicializado la propiedad textToSpeech, es porque existe.
        if (::textToSpeech.isInitialized){
            textToSpeech.stop()
            textToSpeech.shutdown()

        }
        super.onDestroy()
    }
}