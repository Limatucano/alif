package com.tcc.alif.view.ui.administrator.queues

import com.tcc.alif.data.model.CategoryResponse
import com.tcc.alif.data.model.Queues
import com.tcc.alif.data.model.local.Employee


sealed class QueuesState{
    data class Error(val message: String) : QueuesState()
    data class Loading(val loading: Boolean) : QueuesState()
    data class QueuesData(val queues: Queues) : QueuesState()
    data class QueueSaved(val message: String) : QueuesState()
    data class QueueUpdated(val message: String) : QueuesState()
    data class AllCategories(val categories: List<CategoryResponse>) : QueuesState()
    data class MyEmployees(val employees: List<Employee>) : QueuesState()
}
