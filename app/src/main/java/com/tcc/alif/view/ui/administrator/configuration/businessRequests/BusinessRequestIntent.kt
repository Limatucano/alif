package com.tcc.alif.view.ui.administrator.configuration.businessRequests

import com.tcc.alif.data.model.BusinessRequestsResponse

sealed class BusinessRequestIntent{
    data class GetAllBusinessRequests(val idUser:String) : BusinessRequestIntent()
    data class UpdateBusinessRequest(
        val businessRequest: BusinessRequestsResponse,
        val newStatus: String
    ) : BusinessRequestIntent()
}
