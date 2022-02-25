package com.example.marsappkotlin.data.remoto.firebase

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.marsappkotlin.R
import com.example.marsappkotlin.data.local.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class AuthenticationDataSource(private val context: Context) {

    private var userMutableLiveData: MutableLiveData<User?> = MutableLiveData<User?>()
    private var isUserEmailVerifiedLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    private var firebaseUserLiveData: MutableLiveData<User?> = MutableLiveData<User?>()
    private var isUserDisconnectLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun createUserWithEmailAndPassword(
        name: String,
        email: String,
        password: String
    ) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                FirebaseDatabase.getInstance().getReference("Users")
                    .child(mAuth.currentUser!!.uid)
                    .setValue(User(0, name, email, password))
                    .addOnCompleteListener { task ->
                        if (task.isComplete) {
                            userMutableLiveData.postValue(
                                User(
                                    0,
                                    name,
                                    email,
                                    password
                                )
                            )
                            Toast.makeText(
                                context,
                                context.getString(R.string.register_user),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }.addOnFailureListener { onError ->
                        Toast.makeText(
                            context,
                            onError.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } else {
                Toast.makeText(
                    context,
                    it.exception.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun signInWithEmailAndPassword(name: String, email: String, password: String) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                if (mAuth.currentUser?.isEmailVerified == true) {
                    isUserEmailVerifiedLiveData.postValue(true)
                } else {
                    isUserEmailVerifiedLiveData.postValue(false)
                    mAuth.currentUser?.sendEmailVerification()
                    Toast.makeText(
                        context,
                        it.exception.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun getUserProfile() {

        var userProfile = User(1, "", "", "")

        val user = mAuth.currentUser
        val userId = user?.uid
        val reference = FirebaseDatabase.getInstance().getReference("Users")

        val menuListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                userProfile = dataSnapshot.value as User
                firebaseUserLiveData.postValue(userProfile)
                Toast.makeText(
                    context,
                    userProfile.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(
                    context, databaseError.toException().message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        reference.child(userId!!).addListenerForSingleValueEvent(menuListener)

    }

    fun resetPasswordFromEmail(email: String) {

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(
                    context, it.result.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    context, it.exception.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun signOut() {
        try {
            FirebaseAuth.getInstance().signOut()
            isUserDisconnectLiveData.postValue(true)
        } catch (e: Exception) {
            isUserDisconnectLiveData.postValue(false)
            Toast.makeText(
                context, e.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun getFirebaseUserLiveData(): MutableLiveData<User?> {
        return firebaseUserLiveData
    }

    fun getUserMutableLiveData(): MutableLiveData<User?> {
        return userMutableLiveData
    }

    fun getIsUserEmailVerified(): MutableLiveData<Boolean> {
        return isUserEmailVerifiedLiveData
    }

    fun getIsUserDisconnect(): MutableLiveData<Boolean> {
        return isUserDisconnectLiveData
    }

}