package com.kidd.fitness.ui.meal

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.kidd.fitness.base.BaseViewModel
import com.kidd.fitness.base.entity.BaseObjectResponse
import com.kidd.fitness.entity.UserMeal
import com.kidd.fitness.extension.format
import com.kidd.fitness.network.repo.UserRepository
import com.kidd.fitness.utils.Define
import java.util.*
import javax.inject.Inject

class UserMealViewModel @Inject constructor(var userRepository: UserRepository) : BaseViewModel() {
    val userTargetCalo = MutableLiveData<BaseObjectResponse<UserMeal?>>()
    private var userMealDocumentId = ""
    fun createOrUpdateUserMeal(calo: Int) {
        val document = FirebaseFirestore.getInstance()
            .collection(Define.FOODS_COLLECTION)
            .document(userRepository.getUserInfo()?.id!!)
            .collection(Define.USER_MEAL_COLLECTION)
        val query = document
            .whereEqualTo("createdDate", Date().format())
            .get()
        query.addOnSuccessListener {
            if (it.documents.size == 0) {
                val userMeal = UserMeal(System.currentTimeMillis().toString(), calo, Date().format())
                document.document()
                    .set(userMeal)
                    .addOnSuccessListener {
                        userTargetCalo.value = BaseObjectResponse<UserMeal?>().success(userMeal)
                    }
                    .addOnFailureListener {
                    }
            }else{
                userMealDocumentId = it.documents[0].id
                document.document(it.documents[0].id)
                    .update("target_calo", calo)
                    .addOnSuccessListener {
                        userTargetCalo.value?.data?.target_calo = calo
                        userTargetCalo.value = BaseObjectResponse<UserMeal?>().success(userTargetCalo.value?.data)
                    }
                    .addOnFailureListener {
                    }
            }
        }


    }

    private fun getQuery() : Task<QuerySnapshot>{
        val document = FirebaseFirestore.getInstance()
            .collection(Define.FOODS_COLLECTION)
            .document(userRepository.getUserInfo()?.id!!)
            .collection(Define.USER_MEAL_COLLECTION)
        val query = document
            .whereEqualTo("createdDate", Date().format())
            .get()
        return query
    }

    fun getUserTargetCalo() {
        userTargetCalo.value = BaseObjectResponse<UserMeal?>().loading()

        getQuery().addOnSuccessListener {
            if (it.documents.size > 0) {
                userMealDocumentId = it.documents[0].id
                userTargetCalo.value =
                    BaseObjectResponse<UserMeal?>().success(it.documents[0]!!.toObject(UserMeal::class.java)!!)
            } else {
                userTargetCalo.value =
                    BaseObjectResponse<UserMeal?>().success(null)
            }
        }.addOnFailureListener {
            BaseObjectResponse<UserMeal>().error(it)
        }
    }
}
