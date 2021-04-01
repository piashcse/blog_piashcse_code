package com.piashcse.experiment.mvvm_hilt.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentAddUserBinding
import com.piashcse.experiment.mvvm_hilt.datasource.local.User
import com.piashcse.experiment.mvvm_hilt.ui.viewmodel.MainViewModel
import com.piashcse.experiment.mvvm_hilt.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 * Use the [AddUserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class AddUserFragment : Fragment() {
    private lateinit var binding: FragmentAddUserBinding
    private val vm: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddUserBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.add.setOnClickListener {
            vm.insertUser(User(0, binding.name.text.toString().capitalize(), binding.company.text.toString().capitalize()))
            it.findNavController().popBackStack()
            it.hideKeyboard()
        }
        binding.cancel.setOnClickListener {
            it.findNavController().popBackStack()
            it.hideKeyboard()
        }
    }

}