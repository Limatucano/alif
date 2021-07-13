package com.tcc.alif.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tcc.alif.R
import com.tcc.alif.databinding.ActivityMainBinding
import com.tcc.alif.databinding.ActivityModoBinding
import com.tcc.alif.view.ui.cliente.ClienteActivity

class ModoActivity : AppCompatActivity() {

    private val viewBinding : ActivityModoBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modo)

        viewBinding.btnAcessar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            val b = Bundle()
            if(viewBinding.cliente.isChecked){
                b.putSerializable("modo", "cliente")
            }
            if(viewBinding.lojista.isChecked){
                b.putSerializable("modo", "lojista")
            }
            if(!viewBinding.lojista.isChecked && !viewBinding.cliente.isChecked){
                Toast.makeText(this, "Selecione um modo para prosseguir", Toast.LENGTH_LONG).show()
            }else{
                intent.putExtras(b)
                startActivity(intent)
            }
        }

        viewBinding.btnCadastrar.setOnClickListener {
            val apresentacao = Intent(this, ApresentacaoActivity::class.java)
            startActivity(apresentacao)
        }



    }
}