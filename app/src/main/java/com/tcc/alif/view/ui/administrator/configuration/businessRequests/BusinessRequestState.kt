package com.tcc.alif.view.ui.administrator.configuration.businessRequests

import com.tcc.alif.data.model.BusinessRequestsResponse

sealed class BusinessRequestState{
    data class Loading(val status: Boolean) : BusinessRequestState()
    data class Error(val message: String) : BusinessRequestState()
    data class MyBusinessRequests(val requests: List<BusinessRequestsResponse>) : BusinessRequestState()
    data class BusinessUpdated(val message: String) : BusinessRequestState()
}
