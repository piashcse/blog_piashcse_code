package com.piashcse.experiment.mvvm_hilt.ui

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentGoogleLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.piashcse.experiment.mvvm_hilt.utils.base.BaseBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class GoogleLoginFragment : BaseBindingFragment<FragmentGoogleLoginBinding>() {
    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    override fun init() {
        binding.googleLogin.setOnClickListener {
            googleLogin()
        }
    }

    private fun googleLogin() {
        val account = GoogleSignIn.getLastSignedInAccount(requireActivity())
        if (account == null) {
            Timber.e("login again")
            resultContract.launch(googleSignInClient.signInIntent)
        } else {
            Timber.e("token ${account.idToken}")
        }
    }

    private val resultContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                handleSignInResult(task)
            }
        }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
            Timber.e("token ${account.idToken}")
            // Signed in successfully, show authenticated UI.
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Timber.e("signInResult:failed code=" + e.statusCode)
        }
    }
}