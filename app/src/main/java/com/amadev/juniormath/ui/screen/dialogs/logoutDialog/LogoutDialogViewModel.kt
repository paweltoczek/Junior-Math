package com.amadev.juniormath.ui.screen.dialogs.logoutDialog

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogoutDialogViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    fun logout() {
        firebaseAuth.signOut()
    }

}