package com.leoarmelin.meumercado

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.leoarmelin.meumercado.repository.ScrapperRepository
import com.leoarmelin.sharedmodels.api.Result
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScrapperTest {
    private lateinit var scrapperRepository: ScrapperRepository

    @Before
    fun createRepository() {
        scrapperRepository = ScrapperRepository()
    }

    @Test
    fun successResult() = runTest {
        val url = "http://www.fazenda.pr.gov.br/nfce/qrcode?p=TICKET_ID" // Change `TICKET_ID` to test
        val result = scrapperRepository.getNfce(url).last()

        assertTrue(result is Result.Success)
    }

    @Test
    fun wrongUrl() = runTest {
        val url = "wrong_url"
        val result = scrapperRepository.getNfce(url).last()

        assertTrue(result is Result.Error)
    }
    
    @Test
    fun correctData() = runTest {
        val url = "http://www.fazenda.pr.gov.br/nfce/qrcode?p=TICKET_ID" // Change `TICKET_ID` to test
        val result = scrapperRepository.getNfce(url).last() as Result.Success
        val ticket = result.data

        assertTrue(ticket.id.isNotEmpty())
        assertTrue(ticket.priceTotal > 0)
        assertTrue(ticket.url.isNotEmpty())
        assertTrue(ticket.issueAt.isNotEmpty())
        assertTrue(ticket.products.isNotEmpty())
        assertTrue(ticket.store.name.isNotEmpty())
        assertTrue(ticket.store.address.isNotEmpty())
    }
}