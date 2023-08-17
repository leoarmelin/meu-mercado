package com.leoarmelin.meumercado.repository

import com.leoarmelin.scrapper.getTicket
import com.leoarmelin.sharedmodels.Ticket
import com.leoarmelin.sharedmodels.api.BaseDataSource
import com.leoarmelin.sharedmodels.api.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ScrapperRepository @Inject constructor() : BaseDataSource() {
    suspend fun getNfce(url: String): Flow<Result<Ticket>> = flow {
        emit(Result.Loading)

        try {
            val ticket = getTicket(url)
            emit(Result.Success(ticket))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Error during getNfce"))
        }
    }
}