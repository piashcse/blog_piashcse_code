package com.piashcse.experiment.mvvm_hilt.ui.roomdb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.piashcse.experiment.mvvm_hilt.R
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentRoomDBBinding
import com.piashcse.experiment.mvvm_hilt.ui.roomdb.adapter.UserAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RoomDBFragment : Fragment() {
    private var _binding: FragmentRoomDBBinding? = null
    private val binding get() = requireNotNull(_binding) // or _binding!!
    private val roomViewModel: RoomViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRoomDBBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
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
        roomViewModel.getAllUser.observe(viewLifecycleOwner) {
            userAdapter.differ.submitList(it.reversed())
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}