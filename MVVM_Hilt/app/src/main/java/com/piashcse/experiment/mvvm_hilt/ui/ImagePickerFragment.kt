package com.piashcse.experiment.mvvm_hilt.ui

import android.app.Activity
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import com.github.drjacky.imagepicker.ImagePicker
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentImagePickerBinding
import com.piashcse.experiment.mvvm_hilt.utils.base.BaseBindingFragment
import com.piashcse.experiment.mvvm_hilt.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ImagePickerFragment : BaseBindingFragment<FragmentImagePickerBinding>() {
    private var mProfileUri: Uri? = null

    override fun init() {
        binding.imagePicker.setOnClickListener {
            ImagePicker.with(requireActivity())
                // Crop Square image
                .galleryOnly()
                .cropSquare()
                // Image resolution will be less than 512 x 512
                .maxResultSize(200, 200)
                .createIntentFromDialog { intent ->
                    resultContract.launch(intent)
                }
        }
    }

    private val resultContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val resultCode = it.resultCode
            val data = it.data
            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data

                mProfileUri = fileUri
                binding.imageView.setImageURI(fileUri)
                requireContext().showToast("$mProfileUri")
                Timber.e("Result :$mProfileUri")
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                requireContext().showToast(ImagePicker.getError(data))
                requireContext().showToast(ImagePicker.getError(data))
            } else {
                requireContext().showToast("Task Cancelled")
            }
        }
}