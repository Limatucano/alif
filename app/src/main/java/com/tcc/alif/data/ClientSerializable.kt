package com.tcc.alif.data
import java.io.Serializable

class ClientSerializable : Serializable {
    private lateinit var email: String
    private lateinit var senha: String
    fun getEmail(): String? {
        return email
    }
    @JvmName("setEmail1")
    fun setEmail(email: String?) {
        this.email = email!!
    }
    fun getSenha(): String? {
        return senha
    }
    @JvmName("setSenha1")
    fun setSenha(senha: String?) {
        this.senha = senha!!
    }
}