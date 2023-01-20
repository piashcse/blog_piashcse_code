package com.piashcse.experiment.mvvm_hilt.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SocialLoginModule {
    @Singleton
    @Provides
    fun provideGoogleSignClient(@ApplicationContext appContext: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken("165959276467-4q6oqs1dt8dikeloe52g7283l42gcome.apps.googleusercontent.com")
            .build()
        return GoogleSignIn.getClient(appContext, gso)
    }
}