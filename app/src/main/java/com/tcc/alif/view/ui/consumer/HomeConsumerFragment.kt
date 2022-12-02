package com.tcc.alif.view.ui.consumer

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.tcc.alif.R
import com.tcc.alif.data.model.local.HomeConsumerOptions
import com.tcc.alif.data.util.setGridLayout
import com.tcc.alif.data.util.setLinearLayout
import com.tcc.alif.databinding.FragmentHomeConsumerBinding
import com.tcc.alif.view.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeConsumerFragment : BaseFragment<FragmentHomeConsumerBinding>(FragmentHomeConsumerBinding::inflate){

    private val viewModel : HomeConsumerViewModel by viewModels()
    private val args: HomeConsumerFragmentArgs by navArgs()
    private val optionsAdapter by lazy {
        HomeOptionsAdapter(
            context = requireContext(),
            action = {}
        )
    }
    private val historicAdapter by lazy {
        HistoricAdapter(
            context = requireContext(),
            action = {}
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar(
            navigationBack = false,
            visibility = false
        )
        optionsAdapter.options = HomeConsumerOptions.values().toList()
        setView()
        setObserver()
        viewModel.handleIntent(HomeConsumerIntent.LoadHistoric(args.user.uid))
    }

    private fun setObserver() = viewModel.state.observe(viewLifecycleOwner){ state ->
        when(state){
            is HomeConsumerState.Error -> Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            is HomeConsumerState.Loading -> {}
            is HomeConsumerState.Historic -> {
                historicAdapter.historicService = state.historic
            }
        }
    }

    private fun setView() = binding.run {
        userName.text = requireContext().getString(
            R.string.home_consumer_username,
            args.user.name
        )
        historicRv.adapter = historicAdapter
        homeOptionsRv.adapter = optionsAdapter

        historicRv.setLinearLayout(
            context = requireContext(),
            orientation = LinearLayoutManager.VERTICAL,
            withItemDecoration = true
        )

        homeOptionsRv.setGridLayout(
            context = requireContext(),
            spanCount = 3
        )
    }
}