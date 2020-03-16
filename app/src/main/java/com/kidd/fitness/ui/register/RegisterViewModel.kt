package com.kidd.fitness.ui.register

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.kidd.fitness.base.BaseViewModel
import com.kidd.fitness.base.entity.BaseObjectResponse
import com.kidd.fitness.entity.User
import com.kidd.fitness.network.repo.UserRepository
import com.kidd.fitness.utils.Define
import javax.inject.Inject

class RegisterViewModel @Inject constructor(userRepository: UserRepository) : BaseViewModel() {
    var register = MutableLiveData<BaseObjectResponse<User>>()
    var error = MutableLiveData<Map<String, String>>()
    fun register(
        email: String?,
        password: String?,
        confirmPass: String?,
        name: String?
    ) {
        register.value = BaseObjectResponse<User>().loading()
        if (name.isNullOrEmpty()) {
            register.value =
                BaseObjectResponse<User>().error(java.lang.Exception("Tên không được để trống"))
            return
        }
        if (email.isNullOrEmpty()) {
            register.value =
                BaseObjectResponse<User>().error(java.lang.Exception("Email không được để trống"))
            return
        }
        if (password.isNullOrEmpty()) {
            register.value =
                BaseObjectResponse<User>().error(java.lang.Exception("Mật khẩu không được để trống"))
            return
        }
        if (password!=confirmPass) {
            register.value =
                BaseObjectResponse<User>().error(java.lang.Exception("Xác nhận mật khẩu không đúng!"))
            return
        }
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email!!, password!!)
            .addOnSuccessListener { authResult: AuthResult ->
                saveUserData(authResult.user!!.uid, email, name!!)
            }
            .addOnFailureListener { e: Exception ->
                when (e) {
                    is FirebaseAuthUserCollisionException -> {
                        register.value =
                            BaseObjectResponse<User>().error(java.lang.Exception("Email đã tồn tại!"))
                    }
                    is FirebaseAuthWeakPasswordException -> {
                        register.value =
                            BaseObjectResponse<User>().error(java.lang.Exception("Mật khẩu quá ngắn!"))
                    }
                    else -> {
                        register.value =
                            BaseObjectResponse<User>().error(java.lang.Exception("Có lỗi xảy ra!Vui lòng thử lại!"))
                    }
                }
            }
    }

    private fun saveUserData(
        id: String,
        email: String,
        name: String
    ) {
        val user = User(id, name, null, email, null)
        FirebaseFirestore.getInstance().collection(Define.USERS_COLLECTION)
            .document(id)
            .set(user)
            .addOnSuccessListener {
                register.value = BaseObjectResponse<User>().success(user)
            }
            .addOnFailureListener { e: Exception ->
                register.value =
                    BaseObjectResponse<User>().error(java.lang.Exception("Có lỗi xảy ra!Vui lòng thử lại!"))
            }
    }
}
