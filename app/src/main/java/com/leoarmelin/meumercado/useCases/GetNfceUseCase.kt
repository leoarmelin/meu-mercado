package com.leoarmelin.meumercado.useCases

import com.leoarmelin.meumercado.repository.ScrapperRepository
import javax.inject.Inject

class GetNfceUseCase @Inject constructor(
    private val scrapperRepository: ScrapperRepository
) {
    suspend fun execute(url: String) = scrapperRepository.getNfce(url)
}