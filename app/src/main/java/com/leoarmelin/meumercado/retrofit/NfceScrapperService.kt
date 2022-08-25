package com.leoarmelin.meumercado.retrofit

import com.leoarmelin.meumercado.BuildConfig
import com.leoarmelin.meumercado.models.Ticket
import com.leoarmelin.meumercado.models.api.CreateNfceRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NfceScrapperService {
    @POST("/nfe/CreateNfe")
    suspend fun getNfce(@Body nfceUrl: CreateNfceRequest): Response<Ticket>

    companion object {
        private var nfceScrapperService: NfceScrapperService? = null
        fun getInstance(): NfceScrapperService {
            if (nfceScrapperService == null) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                val client = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
                nfceScrapperService = Retrofit.Builder()
                    .baseUrl(BuildConfig.NFCE_SCRAPPER_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(NfceScrapperService::class.java)
            }
            return nfceScrapperService!!
        }
    }
}