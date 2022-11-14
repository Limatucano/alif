package com.tcc.alif.view.ui.administrator.configuration.mycategories

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tcc.alif.R
import com.tcc.alif.data.local.SharedPreferencesHelper.Companion.EMPTY_STRING
import com.tcc.alif.data.model.CategoryResponse
import com.tcc.alif.data.util.Constants.STATE_NOT_MAPPED
import com.tcc.alif.data.util.setLinearLayout
import com.tcc.alif.databinding.FragmentMycategoriesBinding
import com.tcc.alif.view.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyCategoriesFragment : BaseFragment<FragmentMycategoriesBinding>(FragmentMycategoriesBinding::inflate) {

    private val viewModel: MyCategoriesViewModel by viewModels()
    private val adapter by lazy {
        MyCategoriesAdapter(
            context = requireContext(),
            action = { categorySelected(it) }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(
            title = getString(R.string.mycategories_title)
        )
        setObserver()
        setListener()
        setViews()
        getAllCategories()
    }

    private fun setViews() = binding.run {
        rvCategories.adapter = adapter
        rvCategories.setLinearLayout(
            context = requireContext(),
            orientation = LinearLayoutManager.VERTICAL,
            reverseLayout = false,
            withItemDecoration = true
        )
    }

    private fun setObserver() {
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is MyCategoriesState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    updateLoading(false)
                }
                is MyCategoriesState.Loading -> updateLoading(state.loading)
                is MyCategoriesState.AllCategories -> {
                    adapter.categories = state.categories
                    updateLoading(false)
                }
                is MyCategoriesState.CategoryDeleted -> {
                    getAllCategories()
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    updateLoading(false)
                }
            }
        }
    }

    private fun getAllCategories(){
        viewModel.handleIntent(
            MyCategoriesIntent.GetAllCategories(sharedPreferences.companyId ?: EMPTY_STRING)
        )
    }

    private fun updateLoading(loading: Boolean) = binding.run{
        myCategoriesSwipe.isRefreshing = loading
    }

    private fun categorySelected(category: CategoryResponse){
        MyCategoriesBottomDialog(
            context = requireContext(),
            category = category,
            action = {
                when(it){
                    is MyCategoriesIntent.EditCategory -> editCategory(it.category)
                    is MyCategoriesIntent.DeleteCategory -> deleteCategory(it.category)
                    else -> throw Exception(STATE_NOT_MAPPED)
                }
            }
        ).show()
    }

    private fun deleteCategory(category: CategoryResponse){
        viewModel.handleIntent(MyCategoriesIntent.DeleteCategory(
            category = category
        ))
    }

    private fun editCategory(category: CategoryResponse){
        requireView().findNavController().navigate(
            MyCategoriesFragmentDirections.actionMyCategoriesFragmentToMyCategoryFormFragment(category)
        )
    }

    private fun setListener() = binding.run{
        addCategory.setOnClickListener {
            requireView().findNavController().navigate(
                MyCategoriesFragmentDirections.actionMyCategoriesFragmentToMyCategoryFormFragment()
            )
        }
    }
}