package com.tcc.alif

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_login = findViewById<Button>(R.id.btn_login)
        val btn_cadastrar = findViewById<Button>(R.id.btn_cadastrar)


        btn_login.setOnClickListener {
            val intent = Intent(this, ModoActivity::class.java)
            startActivity(intent)
        }
        btn_cadastrar.setOnClickListener {
            val teste = Intent(this, Slides::class.java)
            startActivity(teste)

        }
    }
}