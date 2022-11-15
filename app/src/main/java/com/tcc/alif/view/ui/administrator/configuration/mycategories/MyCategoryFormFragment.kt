package com.tcc.alif.view.ui.administrator.configuration.mycategories

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.tcc.alif.R
import com.tcc.alif.data.model.CategoryResponse
import com.tcc.alif.data.util.ValidateUtil.generateUUID
import com.tcc.alif.data.util.emptyIfNull
import com.tcc.alif.databinding.FragmentMycategoryFormBinding
import com.tcc.alif.view.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyCategoryFormFragment : BaseFragment<FragmentMycategoryFormBinding>(FragmentMycategoryFormBinding::inflate) {

    private val viewModel: MyCategoriesViewModel by viewModels()
    private val args by navArgs<MyCategoryFormFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(
            title = getString(R.string.category_title)
        )
        setListener()
        setObserver()
        setViews()
    }

    private fun setViews() = binding.run{
        if(args.category != null){
            categoryNameEt.setText(args.category?.name)
        }
    }

    private fun setObserver() {
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is MyCategoriesState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    updateLoading(false)
                }
                is MyCategoriesState.Loading -> updateLoading(state.loading)
                is MyCategoriesState.CategoryInserted -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    updateLoading(false)
                }
                is MyCategoriesState.CategoryEdited -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    updateLoading(false)
                }
            }
        }
    }
    private fun updateLoading(loading: Boolean) = binding.run {
        save.isEnabled = !loading
        myCategoryFormSwipe.isRefreshing = loading
    }

    private fun setListener() = binding.run {
        save.setOnClickListener {
            if (args.category == null) {
                viewModel.handleIntent(
                    MyCategoriesIntent.InsertCategory(
                        CategoryResponse(
                            uid = generateUUID(),
                            name = categoryNameEt.text.toString().emptyIfNull(),
                            idCompany = sharedPreferences.companyId.emptyIfNull()
                        )
                    )
                )
                return@setOnClickListener
            }
            viewModel.handleIntent(
                MyCategoriesIntent.EditCategory(
                    CategoryResponse(
                        uid = args.category?.uid ?: "",
                        name = categoryNameEt.text.toString().emptyIfNull(),
                        idCompany = args.category?.idCompany ?: ""
                    )
                )
            )

        }
    }
}