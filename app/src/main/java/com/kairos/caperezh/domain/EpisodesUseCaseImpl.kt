package com.kairos.caperezh.domain

import com.kairos.caperezh.common.DataState
import com.kairos.caperezh.data.model.EpisodesModel
import com.kairos.caperezh.data.response.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EpisodesUseCaseImpl @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend fun getEpisodes(): Flow<DataState<EpisodesModel?>> = homeRepository.getDataEpisodes()
    suspend fun episodeById(episodes: List<String>): Flow<DataState<List<Result>?>> = homeRepository.getDataEpisodesById(episodes)

}