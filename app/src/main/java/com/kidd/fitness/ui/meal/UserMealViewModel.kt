package com.kidd.fitness.ui.meal

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
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
    val createOrUpdate = MutableLiveData<BaseObjectResponse<Boolean>>()
    var userMealDocumentId = ""
    fun createOrUpdateUserMeal(calo: Int) {
        createOrUpdate.value = BaseObjectResponse<Boolean>().loading()
        val document = FirebaseFirestore.getInstance()
            .collection(Define.FOODS_COLLECTION)
            .document(userRepository.getUserInfo()?.id!!)
            .collection(Define.USER_MEAL_COLLECTION)
        document.whereEqualTo("createdDate", Date().format())
            .addSnapshotListener { querySnapshot, e ->
                Log.v("ahuhu","er ${e?.message}  + ${querySnapshot.toString()}")
                for (dc in querySnapshot!!.documentChanges) {
                    when (dc.type) {
                        DocumentChange.Type.ADDED -> {
                            val userMeal =
                                UserMeal(
                                    System.currentTimeMillis().toString(),
                                    calo,
                                    Date().format()
                                )
                            document.document()
                                .set(userMeal)
                                .addOnSuccessListener {
                                    userMealDocumentId = dc.document.id
                                    userTargetCalo.value =
                                        BaseObjectResponse<UserMeal?>().success(userMeal)
                                    createOrUpdate.value =
                                        BaseObjectResponse<Boolean>().success(true)
                                }
                                .addOnFailureListener { e ->
                                    createOrUpdate.value = BaseObjectResponse<Boolean>().error(e)
                                }
                        }
                        DocumentChange.Type.MODIFIED -> {
                            document.document(querySnapshot.first().id)
                                .update("target_calo", calo)
                                .addOnSuccessListener {
                                    userMealDocumentId = dc.document.id
                                    userTargetCalo.value?.data?.target_calo = calo
                                    userTargetCalo.value =
                                        BaseObjectResponse<UserMeal?>().success(userTargetCalo.value?.data)
                                    createOrUpdate.value =
                                        BaseObjectResponse<Boolean>().success(true)
                                }
                                .addOnFailureListener { e ->
                                    createOrUpdate.value = BaseObjectResponse<Boolean>().error(e)
                                }
                        }
                    }
                }
            }

    }

    private fun getQuery(): Task<QuerySnapshot> {
        val document = FirebaseFirestore.getInstance()
            .collection(Define.FOODS_COLLECTION)
            .document(userRepository.getUserInfo()?.id!!)
            .collection(Define.USER_MEAL_COLLECTION)
        return document
            .whereEqualTo("createdDate", Date().format())
            .get()
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
