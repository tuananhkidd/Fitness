package com.kidd.fitness.ui.insert_meal

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.kidd.fitness.base.BaseViewModel
import com.kidd.fitness.base.entity.BaseObjectResponse
import com.kidd.fitness.entity.Food
import com.kidd.fitness.entity.UserMeal
import com.kidd.fitness.entity.UserMealDetail
import com.kidd.fitness.extension.format
import com.kidd.fitness.network.repo.UserRepository
import com.kidd.fitness.utils.Define
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class CreateMealViewModel @Inject constructor(var userRepository: UserRepository) :
    BaseViewModel() {
    private val listFood = ArrayList<Food>()
    var userMealDetailsId: String? = null
    lateinit var userMealDoc: String
    lateinit var timeDoc: String
    var userMealDetail: UserMealDetail? = null
    var userMeal: UserMeal? = null

    var spinnerFood =
        MutableLiveData<BaseObjectResponse<Pair<Pair<Int, List<Food>>, UserMealDetail?>>>()

    var createMealFlag = MutableLiveData<BaseObjectResponse<Boolean>>()

    private fun getListFood() {
        val foodCollection = FirebaseFirestore.getInstance()
            .collection(Define.FOODS_COLLECTION)
            .document(userRepository.getUserInfo()?.id!!)
            .collection(Define.FOODS_MEAL_COLLECTION)
            .get()

        foodCollection.addOnSuccessListener {
            it.documents.forEachIndexed { index, snapShot ->
                val food = snapShot.toObject(Food::class.java)!!
                listFood.add(food)
            }

            userMealDetail?.let {
                listFood.forEachIndexed { index, food ->
                    if (food.id == userMealDetail?.foodId) {
                        spinnerFood.value =
                            BaseObjectResponse<Pair<Pair<Int, List<Food>>, UserMealDetail?>>().success(
                                Pair(
                                    Pair<Int, List<Food>>(index, listFood),
                                    userMealDetail
                                )
                            )
                    }
                }
            } ?: kotlin.run {
                spinnerFood.value =
                    BaseObjectResponse<Pair<Pair<Int, List<Food>>, UserMealDetail?>>().success(
                        Pair(
                            Pair(-1, listFood),
                            null
                        )
                    )
            }

        }.addOnFailureListener {
            spinnerFood.value =
                BaseObjectResponse<Pair<Pair<Int, List<Food>>, UserMealDetail?>>().error(it)
        }
    }

    fun getUserMealDetail(userMealDoc: String, timeDoc: String) {
        this.userMealDoc = userMealDoc
        this.timeDoc = timeDoc
        spinnerFood.value =
            BaseObjectResponse<Pair<Pair<Int, List<Food>>, UserMealDetail?>>().loading()
        getUserMealDetailDoc().get().addOnSuccessListener {
            userMealDetail = it.firstOrNull()?.toObject(UserMealDetail::class.java)
            userMealDetailsId = it.firstOrNull()?.id
            getListFood()
        }.addOnFailureListener {
            spinnerFood.value =
                BaseObjectResponse<Pair<Pair<Int, List<Food>>, UserMealDetail?>>().error(it)
        }
    }

    fun createUserMealDetail(foodId: String, calo: Int, foodName: String, foodWeight: Int) {
        val userMealDetail = UserMealDetail()
        userMealDetail.foodId = foodId
        userMealDetail.foodName = foodName
        userMealDetail.foodWeight = foodWeight
        var fieldUpdate =  ""
        when (timeDoc) {
            Define.MORNING -> {
                fieldUpdate = "morning_calo"
                userMeal?.morning_calo = foodWeight * calo
            }
            Define.AFTERNOON -> {
                fieldUpdate = "afternoon_calo"
                userMeal?.afternoon_calo = foodWeight * calo
            }
            Define.EVENING -> {
                fieldUpdate = "evening_calo"
                userMeal?.evening_calo = foodWeight * calo
            }
        }
        createMealFlag.value = BaseObjectResponse<Boolean>().loading()
        userMealDetailsId?.let {
            getUserMealDetailDoc().document(it).set(userMealDetail)
                .addOnSuccessListener {
                    updateUserMealCalo(fieldUpdate,foodWeight * calo)
                }

        } ?: kotlin.run {
            getUserMealDetailDoc().document().set(userMealDetail)
                .addOnSuccessListener {
                    updateUserMealCalo(fieldUpdate,foodWeight * calo)
                }
        }
    }

    private fun updateUserMealCalo(fieldUpdate:String,calo:Int){
        FirebaseFirestore.getInstance()
            .collection(Define.FOODS_COLLECTION)
            .document(userRepository.getUserInfo()?.id!!)
            .collection(Define.USER_MEAL_COLLECTION)
            .document(userMealDoc)
            .update(fieldUpdate, calo)
            .addOnSuccessListener {
                createMealFlag.value = BaseObjectResponse<Boolean>().success(true)
            }
            .addOnFailureListener{
                createMealFlag.value = BaseObjectResponse<Boolean>().error(it)
            }

    }

    private fun getUserMealDetailDoc(): CollectionReference {
        return FirebaseFirestore.getInstance()
            .collection(Define.FOODS_COLLECTION)
            .document(userRepository.getUserInfo()?.id!!)
            .collection(Define.USER_MEAL_COLLECTION)
            .document(userMealDoc)
            .collection(timeDoc)
    }

}
