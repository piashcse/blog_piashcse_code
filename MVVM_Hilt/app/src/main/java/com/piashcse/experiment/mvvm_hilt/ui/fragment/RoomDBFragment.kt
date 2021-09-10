package com.piashcse.experiment.mvvm_hilt.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.piashcse.experiment.mvvm_hilt.R
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentRoomDBBinding
import com.piashcse.experiment.mvvm_hilt.ui.adapter.UserAdapter
import com.piashcse.experiment.mvvm_hilt.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*


@AndroidEntryPoint
class RoomDBFragment : Fragment() {
    private var _binding: FragmentRoomDBBinding? = null
    private val binding get() = requireNotNull(_binding) // or _binding!!
    private val vm: MainViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRoomDBBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        userAdapter = UserAdapter()
        binding.apply {
            add.setOnClickListener {
                it.findNavController().navigate(R.id.addUserFragment)
            }
            recycler.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = userAdapter
                setHasFixedSize(true)
            }
        }
        vm.getAllUser.observe(viewLifecycleOwner, {
            userAdapter.differ.submitList(it.reversed())
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}