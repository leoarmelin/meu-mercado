package com.leoarmelin.sharedmodels.api

import android.util.Log
import retrofit2.Response
import java.lang.Exception

abstract class BaseDataSource {
    protected suspend fun <T: Any> ApiCall(call: suspend() -> Response<T>): Result<T> {

        return try {
            val response = call.invoke()
            if(response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!)
            }
            else {
                Result.Error(response.errorBody()?.toString()!!)
            }

        }catch (e: Exception){
            Log.i("ApiCall Error", "" + e.message!!)
            Result.Error(e.message ?: "Internet error.")
        }
    }
}