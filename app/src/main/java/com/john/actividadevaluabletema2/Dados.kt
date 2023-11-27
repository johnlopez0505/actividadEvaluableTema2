package com.john.actividadevaluabletema2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import com.john.actividadevaluabletema2.databinding.ActivityDadosBinding
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class Dados : AppCompatActivity() {
    private lateinit var bindingDados : ActivityDadosBinding
    private var sum : Int = 0
    private val diceImages = arrayOf(
        R.drawable.dado1, R.drawable.dado2, R.drawable.dado3,
        R.drawable.dado4, R.drawable.dado5, R.drawable.dado6
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingDados = ActivityDadosBinding.inflate(layoutInflater)
        setContentView(bindingDados.root)

        initEvent()
    }

    private fun initEvent() {
        bindingDados.txtResultado.visibility = View.INVISIBLE
        bindingDados.imageButton.setOnClickListener{
            bindingDados.txtResultado.visibility = View.VISIBLE
            game()  //comienza el juego

        }
    }
    //Comienza el juego
    private fun game(){

        sheduleRun() //planificamos las tiradas.
        val rotateDuration = 200L
        val delayBetweenDice = 500L
        val totalAnimationDuration = 5 * rotateDuration + delayBetweenDice
        bindingDados.imagviewDado1.animateDice(rotateDuration, totalAnimationDuration)
        bindingDados.imagviewDado2.animateDice(rotateDuration, totalAnimationDuration)
        bindingDados.imagviewDado3.animateDice(rotateDuration, totalAnimationDuration)

        Handler().postDelayed({
            viewResult()
        }, totalAnimationDuration)
    }

    private fun ImageView.animateDice(rotateDuration: Long, totalAnimationDuration: Long) {
        val numDice = Random.nextInt(1, 7)
        val initialRotation = rotation

        animate().rotationBy(360f * 5) // Rotate 5 times
            .setDuration(rotateDuration)
            .withStartAction { selectView(this, 1) }
            .withEndAction { selectView(this, numDice) }
            .start()

        // Reset rotation after the animation completes
        Handler().postDelayed({
            animate().rotation(initialRotation).setDuration(0).start()
        }, totalAnimationDuration)
    }

    /*
    Lanzamos 5 veces los dados y lo programamos con 1sg de diferencia.
    A los 7 seguindos, sacamos la suma de la última tirada.
    Utilizamos un grupo de hilos.
    PARA LAS TIRADAS DE LOS DADOS, UTILIZAMOS UN SÓLO HILO DE EJECUCIÓN. Ese
    hilo lo creamos mediante un Executor. Dentro del for, lanzamos tantas tareas
    como veces queramos. Tener en cuenta, que en este caso, sería conveniente que la
    actualización de la UI estuviera perfectamente sincronizada. Si fueran diferentes hilos
    de ejecución, seguramente de esta forma tendría problemas, porque Android decide que la
    UI se lleve a cabo dentro del hilo principal. Para no ser más pesado, quiero que vosotros
    actualicés el método shdeuleRun, utilizando un Handler de la forma:

    private hander ....
    ...
    handler = Handler(Looper.getMAinLooper())
    ...
    schedulerExecutor = Executors.newSingleThereadSchedulerExecutor()
    schedulerExector.shedule(
        {
            handler.post({
                //tareas que hacer de la UI
            })
        },
        Tiempo_lanzamiento, Unidad_tiempo
    )

    Otra alternativa, es crear tantos executor como hilos quieras. Podéis utilizar la
    solución que prefiráis.
     */
    private fun sheduleRun() {

        val schedulerExecutor = Executors.newSingleThreadScheduledExecutor()
        val msc = 1000
        for (i in 1..5){//lanzamos 5 veces el dado
            schedulerExecutor.schedule(
                {
                    throwDadoInTime()  //Lanzo los tres dados.
                },
                msc * i.toLong(), TimeUnit.MILLISECONDS)
        }

        schedulerExecutor.schedule({//El último hilo, es mostrar el resultado.
            viewResult()
        },
            msc  * 7.toLong(), TimeUnit.MILLISECONDS)

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

        sum = numDados.sum() //me quedo con la suma actual
        for (i in 0..3) //cambio las imagenes, a razón de los aleatorios.
            selectView(imagViews[i], numDados[i])

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
    }

}

