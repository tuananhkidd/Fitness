package com.kidd.fitness.ui.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.kidd.fitness.base.BaseViewModel
import com.kidd.fitness.base.entity.BaseObjectResponse
import com.kidd.fitness.entity.UserMeal
import com.kidd.fitness.entity.UserMealDetail
import com.kidd.fitness.network.repo.UserRepository
import com.kidd.fitness.utils.Define
import javax.inject.Inject

class HistoryViewModel @Inject constructor(var userRepository: UserRepository) : BaseViewModel() {
    var timeVale = ""
    var userMealDocId: String? = null
    var userMeal: MutableLiveData<BaseObjectResponse<UserMeal?>> = MutableLiveData()
    var userMealDetail: MutableLiveData<BaseObjectResponse<UserMealDetail?>> = MutableLiveData()

    fun getHistoryByDay() {
        userMeal.value = BaseObjectResponse<UserMeal?>().loading()
        FirebaseFirestore.getInstance()
            .collection(Define.FOODS_COLLECTION)
            .document(userRepository.getUserInfo()?.id!!)
            .collection(Define.USER_MEAL_COLLECTION)
            .whereEqualTo("createdDate", timeVale)
            .get()
            .addOnSuccessListener {
                if (it.documents.isNotEmpty()) {
                    userMeal.value = BaseObjectResponse<UserMeal?>().success(
                        it.documents.firstOrNull()?.toObject(UserMeal::class.java)
                    )
                    userMealDocId = it.documents.firstOrNull()?.id
                } else {
                    userMeal.value =
                        BaseObjectResponse<UserMeal?>().error(Exception("Không có dữ liệu ngày $timeVale"))
                }
            }
            .addOnFailureListener {
                userMeal.value = BaseObjectResponse<UserMeal?>().error(it)
            }
    }

    fun getDetailMeal(timeDoc: String) {
        userMealDocId?.let {
            userMealDetail.value = BaseObjectResponse<UserMealDetail?>().loading()
            FirebaseFirestore.getInstance()
                .collection(Define.FOODS_COLLECTION)
                .document(userRepository.getUserInfo()?.id!!)
                .collection(Define.USER_MEAL_COLLECTION)
                .document(it)
                .collection(timeDoc)
                .get()
                .addOnSuccessListener { snapshot ->
                    userMealDetail.value =
                        BaseObjectResponse<UserMealDetail?>().success(
                            snapshot.documents.firstOrNull()?.toObject(
                                UserMealDetail::class.java
                            )
                        )
                }
                .addOnFailureListener { e ->
                    userMealDetail.value = BaseObjectResponse<UserMealDetail?>().error(e)
                }
        }
    }
}
