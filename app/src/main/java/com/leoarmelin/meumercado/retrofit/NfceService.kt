package com.leoarmelin.meumercado.retrofit

import com.leoarmelin.meumercado.BuildConfig
import com.leoarmelin.meumercado.models.Ticket
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NfceService {
    @GET("/nfce")
    suspend fun getNfce(@Query("nfce_url") nfceUrl: String): Response<Ticket>

    companion object {
        var nfceService: NfceService? = null
        fun getInstance(): NfceService {
            if (nfceService == null) {
                nfceService = Retrofit.Builder()
                    .baseUrl(BuildConfig.NFCE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(NfceService::class.java)
            }
            return nfceService!!
        }
    }
}