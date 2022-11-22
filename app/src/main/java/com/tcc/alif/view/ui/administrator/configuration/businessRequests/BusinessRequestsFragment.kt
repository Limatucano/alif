package com.tcc.alif.view.ui.administrator.configuration.businessRequests

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tcc.alif.R
import com.tcc.alif.data.local.SharedPreferencesHelper.Companion.EMPTY_STRING
import com.tcc.alif.data.util.setLinearLayout
import com.tcc.alif.databinding.FragmentBusinessRequestsBinding
import com.tcc.alif.view.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BusinessRequestsFragment : BaseFragment<FragmentBusinessRequestsBinding>(FragmentBusinessRequestsBinding::inflate) {

    private val viewModel: BusinessRequestViewModel by viewModels()
    private val adapter by lazy {
        BusinessRequestsAdapter(
            context = requireContext(),
            accept = {},
            refuse = {}
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(
            title = getString(R.string.business_requests)
        )
        setObserver()
        setViews()
        viewModel.handleIntent(BusinessRequestIntent.GetAllBusinessRequests(
            sharedPreferences.userId ?: EMPTY_STRING
        ))
    }

    private fun setViews() = binding.run {
        businessRequestsRv.adapter = adapter
        businessRequestsRv.setLinearLayout(
            context = requireContext(),
            orientation = LinearLayoutManager.VERTICAL,
            reverseLayout = false,
            withItemDecoration = false
        )
    }

    private fun setObserver(){
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is BusinessRequestState.Loading -> updateLoading(state.status)
                is BusinessRequestState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    updateLoading(false)
                }
                is BusinessRequestState.MyBusinessRequests -> {
                    adapter.businessRequests = state.requests
                    updateLoading(false)
                }
            }
        }
    }

    private fun updateLoading(loading: Boolean) = binding.run {
        businessRequestsSwipe.isRefreshing = loading
    }
}