package com.leoarmelin.meumercado.repository

import com.leoarmelin.meumercado.models.Ticket
import com.leoarmelin.meumercado.models.api.BaseDataSource
import com.leoarmelin.meumercado.models.api.Result
import com.leoarmelin.meumercado.retrofit.NfceService

class NfceRepository(
    private val nfceService: NfceService = NfceService.getInstance()
) : BaseDataSource() {
    suspend fun getNfce(url: String): Result<Ticket> {
        return ApiCall { nfceService.getNfce(url) }
    }
}