package com.tcc.alif.view.ui.consumer.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.tcc.alif.R
import com.tcc.alif.data.model.SigninResponse
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
            action = { openOption(it) }
        )
    }
    private val historicAdapter by lazy {
        HistoricAdapter(
            context = requireContext()
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        setupToolbar(
            navigationBack = false,
            visibility = false
        )
        setSharedPreferences(args.user)
        optionsAdapter.options = HomeConsumerOptions.values().toList()
        setView()
        setObserver()
        setListener()
        viewModel.handleIntent(
            HomeConsumerIntent.LoadHistoric(
                idUser = args.user.uid
            )
        )
    }

    //TODO: think more about to add a new option to search companies
    //TODO: add new option to configure notifications, app exit,
    private fun openOption(homeConsumer: HomeConsumerOptions){
        when(homeConsumer){
            HomeConsumerOptions.MY_QUEUES -> {
                openMyQueues()
            }
            HomeConsumerOptions.QUEUES -> {
                openQueues()
            }
            HomeConsumerOptions.PROFILE -> {
                openProfile()
            }
        }
    }

    private fun setListener() = binding.run {
        userPicture.setOnClickListener {
            openProfile()
        }

        homeSwipe.setOnRefreshListener {
            viewModel.handleIntent(
                HomeConsumerIntent.LoadHistoric(
                    idUser = args.user.uid
                )
            )
        }
    }

    private fun openQueues(){
        requireView()
            .findNavController()
            .navigate(
                HomeConsumerFragmentDirections.toQueuesConsumer()
            )
    }

    private fun openMyQueues(){
        requireView()
            .findNavController()
            .navigate(
                HomeConsumerFragmentDirections.toMyQueuesConsumer()
            )
    }

    private fun openProfile(){
        requireView()
            .findNavController()
            .navigate(
                HomeConsumerFragmentDirections.toUserProfile()
            )
    }

    private fun setObserver() = viewModel.state.observe(viewLifecycleOwner){ state ->
        when(state){
            is HomeConsumerState.Error -> {
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                updateLoading(false)
            }
            is HomeConsumerState.Loading -> updateLoading(state.loading)
            is HomeConsumerState.Historic -> {
                historicAdapter.historicService = state.historic
                updateLoading(false)
            }
        }
    }

    private fun updateLoading(loading: Boolean) = binding.run{
        homeSwipe.isRefreshing = loading
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

    private fun setSharedPreferences(user: SigninResponse) = sharedPreferences.run {
        userId = user.uid
        userCellphone = user.cellphone
        userEmail = user.email
        userDocument = user.cpf
        userName = user.name
    }
}