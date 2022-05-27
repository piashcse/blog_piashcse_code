package com.piashcse.experiment.mvvm_hilt.ui.permission

import android.Manifest
import androidx.activity.result.contract.ActivityResultContracts
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentPermissionBinding
import com.piashcse.experiment.mvvm_hilt.utils.base.BaseBindingFragment
import com.piashcse.experiment.mvvm_hilt.utils.showToast

class PermissionFragment : BaseBindingFragment<FragmentPermissionBinding>() {

    override fun init() {
        binding.apply {
            singlePermission.setOnClickListener {
                readExternalPermissionContract.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            multiplePermission.setOnClickListener {
                multiplePermissionsContract.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA))
            }
        }

    }

    private val readExternalPermissionContract =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isPermissionAccepted ->
            if (isPermissionAccepted) {
                requireContext().showToast("Permission is accepted")
            } else {
                requireContext().showToast("Permission is declined")
            }
        }

    private val multiplePermissionsContract = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionsStatusMap ->
        if (!permissionsStatusMap.containsValue(false)) {
            // all permissions are accepted
            requireContext().showToast("all permissions are accepted")
        } else {
            requireContext().showToast("all permissions are not accepted")
        }
    }
}