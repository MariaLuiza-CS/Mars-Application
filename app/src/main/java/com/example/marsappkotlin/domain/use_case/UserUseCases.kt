package com.example.marsappkotlin.domain.use_case

data class UserUseCases(
    val getUser: GetUser,
    val deleteUser: DeleteUser,
    val createUserFirebase: CreateUserFirebase,
    val signInWithEmailAndPassword: SignInWithEmailAndPassword,
    val getUserProfileFirebase: GetUserProfileFirebase,
    val resetPasswordFromEmail: ResetPasswordFromEmail,
    val signOutFirebase: SignOutFirebase,
    val checkEditTextEmail: CheckEditTextEmail
)
