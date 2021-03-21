package com.tcc.alif

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView

class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val radio_lojista   = findViewById<RadioButton>(R.id.lojista)
        val radio_cliente   = findViewById<RadioButton>(R.id.cliente)
        val cadastropessoa  = findViewById<CardView>(R.id.cadastropessoa)
        val cadastrolojista = findViewById<CardView>(R.id.cadastrolojista)


        radio_lojista.setOnClickListener{
            if(radio_lojista.isChecked){
                cadastrolojista.visibility = View.VISIBLE
                cadastropessoa.visibility = View.INVISIBLE
            }

        }
        radio_cliente.setOnClickListener{
            if(radio_cliente.isChecked){
                cadastropessoa.visibility = View.VISIBLE
                cadastrolojista.visibility = View.INVISIBLE

            }
        }

    }
}