package com.kidd.fitness.base.entity

import com.kidd.fitness.utils.Define
import com.google.gson.annotations.SerializedName

class BaseObjectResponse<T>(
    val type: Int = 0,
    @SerializedName("data")
    val data: T? = null,
    val error: Throwable? = null
) : BaseResponse() {
    fun loading(): BaseObjectResponse<T>? {
        return BaseObjectResponse(Define.ResponseStatus.LOADING,null,null)
    }

    fun success(data: T): BaseObjectResponse<T> {
        return BaseObjectResponse(Define.ResponseStatus.SUCCESS,data,null)
    }

    fun error(throwable: Throwable): BaseObjectResponse<T> {
        return BaseObjectResponse(Define.ResponseStatus.ERROR,null,throwable)
    }
}