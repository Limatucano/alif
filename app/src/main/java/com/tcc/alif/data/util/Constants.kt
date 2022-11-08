package com.tcc.alif.data.util

object Constants {
    const val API_BASE_URL = "https://backend-api-alif.herokuapp.com/"
    const val API_CEP_URL = "https://viacep.com.br/ws/"

    const val ADMINISTRATOR_MODE = "A"
    const val CONSUMER_MODE = "C"

    ////// collections
    const val COMPANY_COLLECTION = "company"
    const val USER_COLLECTION = "user"
    const val QUEUE_COLLECTION = "queue"

    ////// message requests
    const val USER_UNAUTHORIZED = "Permissão Negada"
    const val SUCCESSFULLY_UPDATED = "Atualizado com sucesso!"
    const val QUEUE_SUCCESSFULLY_INSERTED = "Fila inserida com sucesso!"
    const val QUEUE_SUCCESSFULLY_UPDATED = "Fila atualizada com sucesso!"


    const val STATE_NOT_MAPPED = "state not mapped"
    const val LANGUAGE_PT = "pt"
    const val COUNTRY_BR = "BR"
}