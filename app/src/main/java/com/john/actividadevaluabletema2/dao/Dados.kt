package com.john.actividadevaluabletema2.dao


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.BounceInterpolator
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.john.actividadevaluabletema2.R
import com.john.actividadevaluabletema2.databinding.ActivityDadosBinding
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class Dados : AppCompatActivity() {
    private lateinit var bindingDados : ActivityDadosBinding
    private var sum : Int = 0
    private lateinit var txtName     : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingDados = ActivityDadosBinding.inflate(layoutInflater)
        setContentView(bindingDados.root)


        initEvent()
    }

    private fun initEvent() {
        player()
        loadGif() // cargamos gif de fondo
        bindingDados.txtResultado.visibility = View.INVISIBLE
        bindingDados.buttonJugar.setOnClickListener{
            bindingDados.txtResultado.visibility = View.VISIBLE
            game()  //comienza el juego

        }
        bindingDados.buttonSalir.setOnClickListener{
            Toast.makeText(this, "Regresando a Configuración", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    private fun loadGif() {
        val gifImageView = findViewById<ImageView>(R.id.image_fondo)
        Glide.with(this).load(R.drawable.giphy).centerCrop().into(gifImageView)
    }

    //Comienza el juego
    private fun game(){
        bindingDados.imagviewCarta.setImageDrawable(null)
        bindingDados.txtResultado.text = "0"
        sheduleRun() //planificamos las tiradas.
    }




    private fun sheduleRun() {

        val schedulerExecutor = Executors.newSingleThreadScheduledExecutor()
        val tiradas =  bindingDados.tiradas.text.toString().toLong()
        val msc = 1000
        for (i in 1..tiradas){//lanzamos 5 veces el dado
            schedulerExecutor.schedule(
                {
                    throwDadoInTime()  //Lanzo los tres dados.
                },
                msc * i.toLong(), TimeUnit.MILLISECONDS)
        }
        schedulerExecutor.schedule({//El último hilo, es mostrar el resultado.
            viewResult()
        },

            msc  * (tiradas + 2), TimeUnit.MILLISECONDS)
        schedulerExecutor.shutdown()  //Ya no aceptamos más hilos.
    }


    /*
    Método que lanza los tres dados a partir de 3 aleatorios.
     */
    private fun throwDadoInTime() {
        val numDados = Array(3){ Random.nextInt(1, 6)}
        val imagViews : Array<ImageView> = arrayOf<ImageView>(
            bindingDados.imagviewDado1,
            bindingDados.imagviewDado2,
            bindingDados.imagviewDado3)

        sum = numDados.sum() //me quedo con la suma actual.

        for (i in 0..2) {
            imagViews[i].startAnimation(animation()) // Aplicar la animación a la ImageView.
            animarVaso()// Aplicar animacion al vaso.
            selectView(imagViews[i], numDados[i])
        }
    }


    /*
    Método que dependiendo de la vista, carga una imagen de dado u otro.
     */
    private fun selectView(imgV: ImageView, v: Int) {
        when (v){
            1 -> imgV.setImageResource(R.drawable.dado1);
            2 -> imgV.setImageResource(R.drawable.dado2);
            3 -> imgV.setImageResource(R.drawable.dado3);
            4 -> imgV.setImageResource(R.drawable.dado4);
            5 -> imgV.setImageResource(R.drawable.dado5);
            6 -> imgV.setImageResource(R.drawable.dado6);
        }
    }


    /*
    Muestra los resultados, que es la suma de la última tirada.
     */
    private fun viewResult() {
        bindingDados.txtResultado.text = sum.toString()
        println(sum)
        val imgView = bindingDados.imagviewCarta
        imgView.startAnimation(animationCard())
        selectCard(imgView,sum)


    }

    /*
     Animamos los dados en cada tirada.
     */
    private fun animation():Animation{
        val fadeIn = AlphaAnimation(0f, 1f) // Animación de fade in
        fadeIn.duration = 1000 //Duración de la animación en milisegundos
        val rotate = RotateAnimation(
            0f, 360f,//Ángulo de inicio y fin de la rotación
            //Punto central de rotación (centro horizontal)
            Animation.RELATIVE_TO_SELF, 0.5f,
            //Punto central de rotación (centro vertical)
            Animation.RELATIVE_TO_SELF, 0.5f,
        )
        rotate.duration = 800 //

        val scale = ScaleAnimation(
            0f, 1f,  // Factor de escala de inicio y fin
            0f, 1f,  // Factor de escala de inicio y fin
            Animation.RELATIVE_TO_SELF, 0.5f,//Punto central de escala (centro horizontal)
            Animation.RELATIVE_TO_SELF, 0.5f //Punto central de escala (centro vertical)
        )
        scale.duration = 100 // Duración de la animación en milisegundos


        val translate = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0f,//Desplazamiento inicial (ninguno en el eje X)
            Animation.RELATIVE_TO_SELF, 0f,//Desplazamiento final (ninguno en el eje X)
            Animation.RELATIVE_TO_SELF, -4f,//Desplazamiento inicial (arriba en el eje Y)
            Animation.RELATIVE_TO_SELF, 0f //Desplazamiento final (ninguno en el eje Y)
        )
        translate.duration = 500 // Duración de la animación en milisegundos
        translate.interpolator = BounceInterpolator()
        translate.startOffset = 100

        // El parámetro booleano indica si las animaciones comparten el interpolador
        val animationSet = AnimationSet(true)
        animationSet.addAnimation(rotate)
        animationSet.addAnimation(fadeIn)
        animationSet.addAnimation(translate)
        animationSet.addAnimation(scale)

        return animationSet
    }

    private fun animarVaso(){
        val imgVaso = bindingDados.imageButton
        val rotar = RotateAnimation(
            0f, 180f,  // Ángulo de inicio y fin de la rotación
            //Punto central de rotación (centro horizontal)
            Animation.RELATIVE_TO_SELF, 0.50f,
            // Punto central de rotación (centro vertical)
            Animation.RELATIVE_TO_SELF, 0.50f,
        )
        rotar.duration = 600// Duración de la animación en milisegundos
        rotar.fillAfter = true
        imgVaso.startAnimation(rotar)
    }
    private fun selectCard(imgV: ImageView, v: Int){
        when (v){
            3 -> imgV.setImageResource(R.drawable.carta3);
            4 -> imgV.setImageResource(R.drawable.carta4);
            5 -> imgV.setImageResource(R.drawable.carta5);
            6 -> imgV.setImageResource(R.drawable.carta6);
            7 -> imgV.setImageResource(R.drawable.carta7);
            8 -> imgV.setImageResource(R.drawable.carta8);
            9 -> imgV.setImageResource(R.drawable.carta9);
            10 -> imgV.setImageResource(R.drawable.carta10);
            11 -> imgV.setImageResource(R.drawable.carta11);
            12 -> imgV.setImageResource(R.drawable.carta12);
            13 -> imgV.setImageResource(R.drawable.carta13);
            14 -> imgV.setImageResource(R.drawable.carta14);
            15 -> imgV.setImageResource(R.drawable.carta15);
            16 -> imgV.setImageResource(R.drawable.carta16);
            17 -> imgV.setImageResource(R.drawable.carta17);
            18 -> imgV.setImageResource(R.drawable.cartas18);
        }
    }

    private fun animationCard(): Animation{
        val translate = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 5f,//Desplazamiento inicial (ninguno en el eje X)
            Animation.RELATIVE_TO_SELF, -1f,//Desplazamiento final (ninguno en el eje X)
            Animation.RELATIVE_TO_SELF, 0f,//Desplazamiento inicial (arriba en el eje Y)
            Animation.RELATIVE_TO_SELF, 0f //Desplazamiento final (ninguno en el eje Y)
        )
        translate.duration = 1000 // Duración de la animación en milisegundos
        translate.interpolator = BounceInterpolator()
        translate.startOffset = 100

        return translate
    }

    private fun player() {
        txtName = bindingDados.txtJugador
        val txtNivel = bindingDados.textNivel
        val txtTiradas = bindingDados.tiradas
        val txtEdad = bindingDados.txtEdad
        val txtName1 = bindingDados.txtJugador
        val name = intent.getStringExtra("name")
        val novel = intent.getStringExtra("novel")
        val rolls = intent.getStringExtra("rolls")
        val age = intent.getStringExtra("age")
        val nameEditAuto = intent.getStringExtra("nameEditAuto")
        if (name!= null){
            txtName.text = "$name"
            txtNivel.text = "$novel"
            txtTiradas.text= "$rolls"
            txtEdad.text = "$age"


        }
        if (name == "Selecciona uno" || name != nameEditAuto){
            txtName1.text = "$nameEditAuto"
        }
    }
}

