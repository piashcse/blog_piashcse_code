package com.piashcse.experiment.mvvm_hilt.ui.roomdb.adddata

import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentAddUserBinding
import com.piashcse.experiment.mvvm_hilt.data.datasource.local.room.User
import com.piashcse.experiment.mvvm_hilt.utils.base.BaseBindingFragment
import com.piashcse.experiment.mvvm_hilt.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddUserFragment : BaseBindingFragment<FragmentAddUserBinding>() {
    private val addUserViewModel: AddUserViewModel by viewModels()
    override fun init() {
        initView()
    }

    private fun initView() {
        binding.apply {
            add.setOnClickListener {
                addUserViewModel.insertUser(
                    User(
                        0,
                        name.text.toString().capitalize(),
                        company.text.toString().capitalize()
                    )
                )
                it.findNavController().popBackStack()
                it.hideKeyboard()
            }
            cancel.setOnClickListener {
                it.findNavController().popBackStack()
                it.hideKeyboard()
            }
        }
    }
}