package com.kidd.fitness.network


import com.kidd.fitness.base.entity.BaseListLoadMoreResponse
import com.kidd.fitness.entity.User
import io.reactivex.Single
import retrofit2.http.*

interface ApiInterface {

    @GET("search")
    @Headers("Content-Type: application/json", "lang: vi")
    fun getDataUser(
        @Query("s") keyWord: String,
        @Query("page") page: Int
    ): Single<BaseListLoadMoreResponse<User>>
}
