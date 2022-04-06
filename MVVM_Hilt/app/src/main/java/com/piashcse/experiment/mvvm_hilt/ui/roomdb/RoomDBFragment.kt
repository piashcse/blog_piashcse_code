package com.piashcse.experiment.mvvm_hilt.ui.roomdb

import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.piashcse.experiment.mvvm_hilt.R
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentRoomDBBinding
import com.piashcse.experiment.mvvm_hilt.ui.roomdb.adapter.UserAdapter
import com.piashcse.experiment.mvvm_hilt.utils.base.BaseBindingFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RoomDBFragment : BaseBindingFragment<FragmentRoomDBBinding>() {
    private val roomViewModel: RoomViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter

    override fun init() {
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
}