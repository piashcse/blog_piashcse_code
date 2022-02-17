package com.piashcse.experiment.mvvm_hilt.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.piashcse.experiment.mvvm_hilt.R
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentBottomNavigationBinding
import com.piashcse.experiment.mvvm_hilt.utils.hide
import com.piashcse.experiment.mvvm_hilt.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomNavigationFragment : Fragment() {
    private var _binding: FragmentBottomNavigationBinding? = null
    private val binding get() = requireNotNull(_binding) // or !!_binding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBottomNavigationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    fun initView() {
        navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment_bottom_navigation) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.detailFragmentBottom, R.id.expandableRecyclerViewFragmentBottom, R.id.imagePickerFragmentBottom -> {
                    binding.bottomNavigation.show()
                }
                else ->
                    binding.bottomNavigation.hide()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}