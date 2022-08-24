package com.example.king_bob_nae.features.create.detail.data.repository.impl

import com.example.king_bob_nae.features.create.detail.data.DetailKkiLogDto
import com.example.king_bob_nae.features.create.detail.data.repository.DetailKkiLogRepository
import com.example.king_bob_nae.features.create.detail.data.service.DetailKkiLogService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailKkiLogRepositoryImpl @Inject constructor(
    private val service: DetailKkiLogService
) : DetailKkiLogRepository {
    override fun requestDetailKkiLog(): Flow<DetailKkiLogDto> = flow {
        service.requestDetailKkiLog()
    }
}