package com.leoarmelin.meumercado.repository

import com.leoarmelin.meumercado.models.Ticket
import com.leoarmelin.meumercado.models.api.BaseDataSource
import com.leoarmelin.meumercado.models.api.CreateNfceRequest
import com.leoarmelin.meumercado.models.api.Result
import com.leoarmelin.meumercado.retrofit.NfceScrapperService
import javax.inject.Inject

class NfceScrapperRepository @Inject constructor(
    private val nfceService: NfceScrapperService
) : BaseDataSource() {
    suspend fun getNfce(request: CreateNfceRequest): Result<Ticket> {
        return ApiCall { nfceService.getNfce(request) }
    }
}