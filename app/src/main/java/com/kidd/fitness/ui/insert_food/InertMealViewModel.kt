package com.kidd.fitness.ui.insert_food

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.kidd.fitness.base.BaseViewModel
import com.kidd.fitness.base.entity.BaseObjectResponse
import com.kidd.fitness.entity.Food
import com.kidd.fitness.network.repo.UserRepository
import com.kidd.fitness.utils.Define
import com.kidd.fitness.utils.Define.Companion.FOODS_MEAL_COLLECTION
import javax.inject.Inject

class InertMealViewModel @Inject constructor(var userRepo:UserRepository): BaseViewModel() {
    val insertMeal = MutableLiveData<BaseObjectResponse<Boolean>>()

    fun insertFood(food:Food){
        val document = FirebaseFirestore.getInstance()
            .collection(Define.FOODS_COLLECTION)
            .document(userRepo.getUserInfo()?.id!!)
            .collection(FOODS_MEAL_COLLECTION)
            .document()

        insertMeal.value = BaseObjectResponse<Boolean>().loading()
        document.set(food)
            .addOnSuccessListener {
                insertMeal.value = BaseObjectResponse<Boolean>().success(true)
            }
            .addOnFailureListener{
                insertMeal.value = BaseObjectResponse<Boolean>().error(it)
            }

    }
}
