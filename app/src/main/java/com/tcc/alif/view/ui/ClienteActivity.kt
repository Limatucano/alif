package com.tcc.alif.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.tcc.alif.R
import com.tcc.alif.databinding.ActivityMainBinding
import com.tcc.alif.model.*
import com.tcc.alif.model.domain.MinhasFilasData
import com.tcc.alif.view.adapter.MinhasFilasAdapter

class ClienteActivity : AppCompatActivity() {
    private var clientSerializable: ClientSerializable? = null
    private val viewBinding : ActivityMainBinding by viewBinding()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente)
        val rvFilas = findViewById<RecyclerView>(R.id.rvFilas)
        val apiService = RestApiService()
        var email = intent.getSerializableExtra("email")
        val arr = MinhasFilasPost(
                email = email.toString(),
        )
        apiService.getMyFilas(arr){ status: Int?, minhasFilas: MinhasFilas? ->

            if (status != 200) {
                Snackbar.make(viewBinding.Layout, R.string.erro_cadastrar, Snackbar.LENGTH_LONG ).show()
            } else {
                minhasFilas?.response?.let {
                    val fila : List<MinhasFilasData> = it.map{ fila ->
                        MinhasFilasData(fila.nome_da_fila)
                    }

                    val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    rvFilas.post{
                        rvFilas.layoutManager = layoutManager
                        rvFilas.adapter = MinhasFilasAdapter(fila)
                    }
                }

            }
        }
    }
}