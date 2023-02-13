package com.tcc.alif.view.ui.administrator.queues

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.firebase.Timestamp
import com.tcc.alif.R
import com.tcc.alif.data.local.SharedPreferencesHelper.Companion.EMPTY_STRING
import com.tcc.alif.data.model.CategoryResponse
import com.tcc.alif.data.model.CompanyRole
import com.tcc.alif.data.model.QueueRequest
import com.tcc.alif.data.model.QueueResponse
import com.tcc.alif.data.model.local.Employee
import com.tcc.alif.data.model.local.StatusQueue
import com.tcc.alif.data.util.*
import com.tcc.alif.data.util.Constants.STATE_NOT_MAPPED
import com.tcc.alif.data.util.DateFormats.NORMAL_DATE_WITH_HOURS_FORMAT
import com.tcc.alif.data.util.ValidateUtil.generateUUID
import com.tcc.alif.databinding.FragmentQueueFormBinding
import com.tcc.alif.view.base.CustomAdapter
import com.tcc.alif.view.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QueueFormFragment : BaseFragment<FragmentQueueFormBinding>(FragmentQueueFormBinding::inflate) {

    private val viewModel : QueuesViewModel by viewModels()
    private lateinit var queue: QueueResponse
    private var uidCategorySelected: String? = null
    private var uidEmployeeSelected: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments ?: return
        val args = QueueFormFragmentArgs.fromBundle(bundle)
        queue = args.queue
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(
            title = getString(R.string.queue_form_title),
            navigationBack = true
        )
        setViews()
        setListener()
        setObservers()
        viewModel.handleIntent(
            QueuesIntent.GetAllCategories(
                sharedPreferences.companyId.emptyIfNull()
            )
        )
        viewModel.handleIntent(
            QueuesIntent.GetMyEmployees(
                sharedPreferences.companyId.emptyIfNull()
            )
        )
    }

    private fun setViews() = binding.run{
        val statusAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            StatusQueue.values().map { getString(it.text) }
        )
        statusAc.setAdapter(statusAdapter)
        save.setVisible(CompanyRole.getRoleByValue(sharedPreferences.userRole).permissions[Constants.PERMISSION_EDIT_QUEUE] == true)
        fillViews()
    }

    private fun setupCategoryAdapter(categories: List<CategoryResponse>){
        val categoriesMapped = categories.map { Pair(
            it.uid, it.name
        ) }

        val adapter = CustomAdapter(
            context = requireContext(),
            values = categoriesMapped
        )

        binding.categoryAc.setAdapter(adapter)

        if(queue.idQueue.isNotEmpty()){
            val categoryByKey = adapter.getItemByKey(queue.titleCategory)
            categoryByKey?.second?.let {
                binding.categoryAc.setText(it)
            }
        }
        binding.categoryAc.setOnItemClickListener { _, _, position, _ ->
            val itemSelected = adapter.getItem(position)
            uidCategorySelected = itemSelected.first
            binding.categoryAc.setText(itemSelected.second)
        }
    }

    private fun setupEmployeesAdapter(employees: List<Employee>){
        val employeesMapped = employees.map {
            Pair(
                it.uid, "${it.name} - ${it.cpf}"
            )
        }
        val adapter = CustomAdapter(
            context = requireContext(),
            values = employeesMapped
        )

        binding.employeeAc.setAdapter(adapter)

        if(queue.idQueue.isNotEmpty()){
            val employeeByKey = adapter.getItemByKey(queue.employeeResponsible ?: EMPTY_STRING)
            employeeByKey?.second?.let {
                binding.employeeAc.setText(it)
            }
        }
        binding.employeeAc.setOnItemClickListener { _, _, position, _ ->
            val itemSelected = adapter.getItem(position)
            uidEmployeeSelected = itemSelected.first
            binding.employeeAc.setText(itemSelected.second)
        }
    }

    private fun fillViews() = binding.run {
        if(queue.idQueue.isNotEmpty()){
            nameQueueEt.setText(queue.name)
            averageTimeEt.setText(queue.averageTime.toString().emptyIfNull())
            descriptionEt.setText(queue.description)
            closingTimeEt.text = queue.closingTime.toDate().toStringDate(
                NORMAL_DATE_WITH_HOURS_FORMAT
            )
            openingTimeEt.text = queue.openingTime.toDate().toStringDate(
                NORMAL_DATE_WITH_HOURS_FORMAT
            )
            quantityEt.setText(queue.quantity.toString().emptyIfNull())
            statusAc.setText(getString(queue.status ?: R.string.closed_status), false)
        }
    }

    private fun setListener() = binding.run {
        openingTimeEt.setDateSelected{ calendar, _ ->
            openingTimeEt.text = calendar.time.toStringDate(NORMAL_DATE_WITH_HOURS_FORMAT)
        }
        closingTimeEt.setDateSelected { calendar, _ ->
            closingTimeEt.text = calendar.time.toStringDate(NORMAL_DATE_WITH_HOURS_FORMAT)
        }
        save.setOnClickListener {
            buildModel()
        }
    }

    private fun buildModel(){
        val queue = QueueRequest(
            idQueue = this.queue.idQueue.ifEmpty { generateUUID() },
            idCompany = this.queue.idCompany,
            name = binding.nameQueueEt.text.toString().emptyIfNull(),
            status = binding.statusAc.text.toString().emptyIfNull().uppercase(),
            quantity = binding.quantityEt.text.toString().toInt(),
            description = binding.descriptionEt.text.toString().emptyIfNull(),
            titleCategory = uidCategorySelected ?: EMPTY_STRING,
            employeeResponsible = uidEmployeeSelected ?: EMPTY_STRING,
            averageTime = binding.averageTimeEt.text.toString().toInt(),
            employeeCreator = sharedPreferences.userId ?: EMPTY_STRING,
            service = listOf(),
            closingTime = binding.closingTimeEt.text.dateFromString(
                NORMAL_DATE_WITH_HOURS_FORMAT)?.let { Timestamp(it) },
            openingTime = binding.openingTimeEt.text.dateFromString(
                NORMAL_DATE_WITH_HOURS_FORMAT)?.let { Timestamp(it) },
        )

        if(this.queue.idQueue.isEmpty()){
            saveNewQueue(queue)
            return
        }
        updateQueue(queue)

    }

    private fun updateQueue(queueRequest: QueueRequest){
        viewModel.handleIntent(QueuesIntent.UpdateQueue(
            queue = queueRequest
        ))
    }

    private fun saveNewQueue(queueRequest: QueueRequest){
        viewModel.handleIntent(QueuesIntent.SaveNewQueue(
            queue = queueRequest
        ))
    }

    private fun setObservers(){
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is QueuesState.QueueSaved -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    setLoading(false)
                }
                is QueuesState.Loading -> setLoading(state.loading)
                is QueuesState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    setLoading(false)
                }
                is QueuesState.QueueUpdated -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    setLoading(false)
                }
                is QueuesState.AllCategories -> {
                    setupCategoryAdapter(state.categories)
                    setLoading(false)
                }
                is QueuesState.MyEmployees -> {
                    setupEmployeesAdapter(state.employees)
                    setLoading(false)
                }
                else -> throw Exception(STATE_NOT_MAPPED)
            }
        }
    }

    private fun setLoading(loading: Boolean) = binding.run{
        binding.queueFormSwipe.isRefreshing = loading
    }

}