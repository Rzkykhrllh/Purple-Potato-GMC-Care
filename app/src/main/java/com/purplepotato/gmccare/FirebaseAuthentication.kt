package com.purplepotato.gmccare


import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthentication {

    private val fbAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun emailAndPasswordSignIn(
        email: String, password: String,
        onSuccess: (Boolean) -> Unit,
        onError: (Exception) -> Unit
    ) {
        fbAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess(true)
            }
            .addOnFailureListener {
                onError(it)
            }
    }
}