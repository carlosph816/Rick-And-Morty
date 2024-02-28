package com.kairos.caperezh.data.repository

import com.kairos.caperezh.common.DataState
import com.kairos.caperezh.data.RemoteDataSource
import com.kairos.caperezh.data.model.ItemsModel
import com.kairos.caperezh.data.response.Result
import com.kairos.caperezh.domain.HomeRepository
import kotlinx.coroutines.flow.Flow
import com.kairos.caperezh.presentation.Pages
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): HomeRepository {

    override suspend fun getDataCharacters(): Flow<DataState<ItemsModel?>> = remoteDataSource.getDataCharacters()
    override suspend fun getDataLocations(): Flow<DataState<ItemsModel?>> = remoteDataSource.getDataLocations()
    override suspend fun getDataEpisodes(): Flow<DataState<ItemsModel?>> = remoteDataSource.getDataEpisodes()

    override suspend fun getDataCharactersByPage(page: Int, type: Pages): Flow<DataState<ItemsModel?>> = remoteDataSource.getDataCharactersByPage(page, type)

    override suspend fun getDataEpisodesById(episodes: List<String>): Flow<DataState<List<Result>?>> = remoteDataSource.getDataEpisodesById(episodes)
    override suspend fun getDataCharactersById(residents: List<String>): Flow<DataState<List<Result>?>> = remoteDataSource.getDataCharactersById(residents)
    override suspend fun getDataCharactersByType(type: String, value: String): Flow<DataState<ItemsModel?>> = remoteDataSource.getDataCharactersByType(type, value)

}