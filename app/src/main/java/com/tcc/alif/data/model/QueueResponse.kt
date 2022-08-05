package com.tcc.alif.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Queues(
    val queues : List<QueueResponse>
) : Parcelable

@Parcelize
data class QueueResponse(
    @SerializedName("id_fila") val idQueue : Int,
    @SerializedName("nome") val name : String,
    val status : String,
    @SerializedName("data_hora_abertura") val openingTime : String,
    @SerializedName("data_hora_fechamento") val closingTime : String,
    @SerializedName("quantidade_vagas") val quantity : Int,
    @SerializedName("descricao") val description : String?,
    @SerializedName("titulo_categoria") val titleCategory : String,
    @SerializedName("descricao_categoria") val descriptionCategory : String,
    @SerializedName("prioridade_categoria") val priorityCategory : Boolean,
    @SerializedName("tempo_medio_categoria") val averageTimeCategory : String,
    @SerializedName("createdby") val employeeCreator : String,
    @SerializedName("primeirosClientes") val firstConsumers : List<ConsumerResume>?
) : Parcelable

@Parcelize
data class ConsumerResume(
    @SerializedName("posicao") val position : Int,
    @SerializedName("nome") val name : String,
    val cpf : String
) : Parcelable
