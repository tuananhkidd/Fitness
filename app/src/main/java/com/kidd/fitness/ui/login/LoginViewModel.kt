package com.kidd.fitness.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.Transaction
import com.kidd.fitness.BaseApplication
import com.kidd.fitness.base.BaseViewModel
import com.kidd.fitness.base.entity.BaseObjectResponse
import com.kidd.fitness.entity.User
import com.kidd.fitness.extension.toast
import com.kidd.fitness.network.Repository
import com.kidd.fitness.network.repo.UserRepository
import com.kidd.fitness.utils.Define
import java.util.*
import javax.inject.Inject

class LoginViewModel @Inject constructor(var repo: UserRepository) : BaseViewModel() {
    val login = MutableLiveData<BaseObjectResponse<Boolean>>()

    fun login(userName: String, password: String) {
        login.value = BaseObjectResponse<Boolean>().loading()
        FirebaseAuth.getInstance().signInWithEmailAndPassword(userName, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("ahuhu", "signInWithEmail:success ${it.result?.user?.uid}")
                    updateUserData(it.result?.user?.uid!!)
                } else {
                    login.value = BaseObjectResponse<Boolean>().error(it.exception!!)
                    Log.w("ahuhu", "signInWithEmail:failure", it.exception)
                    handleException(it.exception!!)
                }
            }
            .addOnFailureListener {
                Log.w("ahuhu", "createUserWithEmail:exception", it)
                login.value = BaseObjectResponse<Boolean>().error(it)
                handleException(it)
            }
    }

    private fun handleException(e: java.lang.Exception) {
        when (e) {
            is FirebaseAuthInvalidCredentialsException -> {
                BaseApplication.context.toast("Sai tên đăng nhập hoặc mật khẩu")
            }
            else -> {
                BaseApplication.context.toast("Có lỗi xảy ra.Vui lòng thử lại!")
            }
        }
    }

    private fun updateUserData(userID: String) {
        val userRef =
            FirebaseFirestore.getInstance()
                .collection(Define.USERS_COLLECTION)
                .document(userID)
        FirebaseFirestore.getInstance()
            .runTransaction { transaction: Transaction ->
                val user: User = transaction[userRef].toObject(User::class.java)!!
                val onlineStateData: MutableMap<String, Any> = HashMap(1)
                transaction[userRef, onlineStateData] = SetOptions.merge()
                repo.setUserInfo(user)
                user
            }.addOnSuccessListener { user: User? ->
                login.value = BaseObjectResponse<Boolean>().success(true)
            }.addOnFailureListener { e: Exception ->
                login.value = BaseObjectResponse<Boolean>().error(e)
            }
    }
}
