package com.tcc.alif.view.ui.administrator.configuration.businessRequests

sealed class BusinessRequestIntent{
    data class GetAllBusinessRequests(val idUser:String) : BusinessRequestIntent()
}
