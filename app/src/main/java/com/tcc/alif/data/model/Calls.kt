package com.tcc.alif.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class Calls(
    var calls : List<Call>,
    val quantity: Int
): Parcelable

@Parcelize
data class Call(
    val idConsumer : String,
    val employeeName : String,
    val employeeRole : String,
    val consumerName : String,
    val enrollmentTime : Timestamp,
    val cellphone : String,
    val birthDate : String,
    val cpf : String
): Parcelable

enum class CallStatus(val value: String){
    IN_PROGRESS("EM ANDAMENTO"),
    IN_HOLD("EM ESPERA"),
    FINISHED("FINALIZADO"),
    CANCELED("CANCELADO")
}
