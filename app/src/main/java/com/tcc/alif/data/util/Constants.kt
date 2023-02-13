package com.tcc.alif.data.util

object Constants {
    const val API_BASE_URL = "https://backend-api-alif.herokuapp.com/"
    const val API_CEP_URL = "https://viacep.com.br/ws/"
    const val API_ONE_SIGNAL = "https://onesignal.com/api/v1/"

    const val ADMINISTRATOR_MODE = "A"
    const val CONSUMER_MODE = "C"

    const val PUSH_NOTIFICATION_FEATURE = true

    const val PUSH_NOTIFICATION_TITLE = "Sua hora está chegando!"
    const val PUSH_NOTIFICATION_NAME = "ALIF atualização de atendimento"

    ////// collections
    const val COMPANY_COLLECTION = "company"
    const val USER_COLLECTION = "user"
    const val QUEUE_COLLECTION = "queue"
    const val CATEGORY_COLLECTION = "category"
    const val EMPLOYEE_COLLECTION = "employee"

    ////// columns
    const val ID_COMPANY = "idCompany"
    const val ID_QUEUE = "idQueue"
    const val ID_USER = "idUser"
    const val USER_ID = "userId"
    const val SERVICE = "service"
    const val UID = "uid"
    const val CPF = "cpf"
    const val STATUS = "status"

    ////// message requests
    const val USER_UNAUTHORIZED = "Permissão Negada"
    const val SUCCESSFULLY_UPDATED = "Atualizado com sucesso!"
    const val QUEUE_SUCCESSFULLY_INSERTED = "Fila inserida com sucesso!"
    const val QUEUE_SUCCESSFULLY_UPDATED = "Fila atualizada com sucesso!"
    const val PASSWORD_UPDATED = "Senha atualizada com sucesso!"
    const val SIGNIN_ERROR = "Credenciais inválidas"
    const val PASSWORD_ERROR = "Erro! Verifique as senhas preenchidas"
    const val CATEGORY_DELETED = "Categoria deletada com sucesso!"
    const val CATEGORY_INSERTED = "Categoria inserida com sucesso!"
    const val CATEGORY_UPDATED = "Categoria atualizada com sucesso!"
    const val USER_UPDATED = "Usuário atualizado com sucesso!"
    const val COMPANY_UPDATED = "Empresa atualizada com sucesso!"
    const val EMPLOYEE_DELETED = "Funcionário deletado com sucesso!"
    const val EMPLOYEE_SUCCESSFULLY_INSERTED = "Funcionário adicionado com sucesso!"
    const val EMPLOYEE_ALREADY_EXISTS = "Funcionário já existe!"
    const val BUSINESS_ADJUSTED = "Notificação atualizada com sucesso!"
    const val CANCELLED_SUBSCRIPTION = "Inscrição cancelada com sucesso!"
    const val SUBSCRIPTION_SUCCESSFULLY = "Inscrição realizada com sucesso!"

    const val PERMISSION_EDIT_COMPANY = "PERMISSION_EDIT_COMPANY"
    const val PERMISSION_ADD_COMPANY = "PERMISSION_ADD_COMPANY"
    const val PERMISSION_ADD_RESPONSIBLE_EMPLOYEE_QUEUE = "PERMISSION_ADD_RESPONSIBLE_EMPLOYEE_QUEUE"
    const val PERMISSION_ADD_QUEUE = "PERMISSION_ADD_QUEUE"
    const val PERMISSION_EDIT_QUEUE = "PERMISSION_EDIT_QUEUE"
    const val PERMISSION_ADD_EMPLOYEE = "PERMISSION_ADD_EMPLOYEE"
    const val PERMISSION_REMOVE_EMPLOYEE = "PERMISSION_REMOVE_EMPLOYEE"
    const val PERMISSION_CATEGORY_QUEUE = "PERMISSION_CATEGORY_QUEUE"
    const val PERMISSION_ADMIN_ROLE = "ADMIN"
    const val PERMISSION_EMPLOYEE_ROLE = "EMPLOYEE"

    const val STATE_NOT_MAPPED = "state not mapped"
    const val LANGUAGE_PT = "pt"
    const val COUNTRY_BR = "BR"
}