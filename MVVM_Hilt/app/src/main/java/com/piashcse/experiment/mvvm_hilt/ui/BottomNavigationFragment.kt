package com.piashcse.experiment.mvvm_hilt.ui

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.piashcse.experiment.mvvm_hilt.R
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentBottomNavigationBinding
import com.piashcse.experiment.mvvm_hilt.utils.base.BaseBindingFragment
import com.piashcse.experiment.mvvm_hilt.utils.hide
import com.piashcse.experiment.mvvm_hilt.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomNavigationFragment : BaseBindingFragment<FragmentBottomNavigationBinding>() {
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun init() {
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
}