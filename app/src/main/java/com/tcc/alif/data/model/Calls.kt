package com.tcc.alif.data.model

import android.os.Parcelable
import androidx.annotation.StringRes
import com.google.firebase.Timestamp
import com.tcc.alif.R
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
    val status : CallStatus,
    val cpf : String
): Parcelable

enum class CallStatus(
    val value: String,
    @StringRes val text: Int
){
    IN_PROGRESS(
        value = "EM ANDAMENTO",
        text = R.string.in_progress
    ),
    IN_HOLD(
        value = "EM ESPERA",
        text = R.string.in_hold
    ),
    FINISHED(
        value = "FINALIZADO",
        text = R.string.finished
    ),
    CANCELED(
        value = "CANCELADO",
        text = R.string.canceled
    )
}
