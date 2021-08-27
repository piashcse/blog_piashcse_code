package com.piashcse.experiment.mvvm_hilt.ui.fragment

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.github.dhaval2404.imagepicker.ImagePicker
import com.piashcse.experiment.mvvm_hilt.R
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentImagePickerBinding
import com.piashcse.experiment.mvvm_hilt.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 * Use the [ImagePickerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ImagePickerFragment : Fragment() {
    private lateinit var binding: FragmentImagePickerBinding
    private var mProfileUri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentImagePickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.imagePicker.setOnClickListener {
            ImagePicker.with(this)
                // Crop Square image
                .galleryOnly()
                .cropSquare()
                // Image resolution will be less than 512 x 512
                .maxResultSize(200, 200)
                .createIntent { intent ->
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