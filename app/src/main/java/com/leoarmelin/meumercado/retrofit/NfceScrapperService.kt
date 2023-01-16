package com.leoarmelin.meumercado.retrofit

import com.leoarmelin.meumercado.models.Ticket
import com.leoarmelin.meumercado.models.api.CreateNfceRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface NfceScrapperService {
    @POST("/nfe/CreateNfe")
    suspend fun getNfce(@Body nfceUrl: CreateNfceRequest): Response<Ticket>
}