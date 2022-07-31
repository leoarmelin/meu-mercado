package com.leoarmelin.meumercado.retrofit

import com.leoarmelin.meumercado.BuildConfig
import com.leoarmelin.meumercado.models.Ticket
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                val client = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
                nfceService = Retrofit.Builder()
                    .baseUrl(BuildConfig.NFCE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(NfceService::class.java)
            }
            return nfceService!!
        }
    }
}