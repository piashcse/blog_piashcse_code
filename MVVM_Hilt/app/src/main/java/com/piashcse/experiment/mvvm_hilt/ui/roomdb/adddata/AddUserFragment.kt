package com.piashcse.experiment.mvvm_hilt.ui.roomdb.adddata

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentAddUserBinding
import com.piashcse.experiment.mvvm_hilt.data.datasource.local.room.User
import com.piashcse.experiment.mvvm_hilt.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddUserFragment : Fragment() {
    private var _binding: FragmentAddUserBinding? = null
    private val binding get() = requireNotNull(_binding) // or _binding!!
    private val addUserViewModel: AddUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

    }

    private fun initView() {
        binding.apply {
            add.setOnClickListener {
                addUserViewModel.insertUser(
                    User(
                        0,
                        binding.name.text.toString().capitalize(),
                        binding.company.text.toString().capitalize()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}