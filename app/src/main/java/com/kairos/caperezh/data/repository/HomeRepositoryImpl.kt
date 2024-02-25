package com.kairos.caperezh.data.repository

import com.kairos.caperezh.common.DataState
import com.kairos.caperezh.data.RemoteDataSource
import com.kairos.caperezh.data.model.CharactersModel
import com.kairos.caperezh.data.model.EpisodesModel
import com.kairos.caperezh.data.model.LocationsModel
import com.kairos.caperezh.data.response.Result
import com.kairos.caperezh.domain.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): HomeRepository {

    override suspend fun getDataCharacters(): Flow<DataState<CharactersModel?>> = remoteDataSource.getDataCharacters()
    override suspend fun getDataLocations(): Flow<DataState<LocationsModel?>> = remoteDataSource.getDataLocations()
    override suspend fun getDataEpisodes(): Flow<DataState<EpisodesModel?>> = remoteDataSource.getDataEpisodes()

    override suspend fun getDataEpisodesById(episodes: List<String>): Flow<DataState<List<Result>?>> = remoteDataSource.getDataEpisodesById(episodes)
    override suspend fun getDataCharactersById(residents: List<String>): Flow<DataState<List<Result>?>> = remoteDataSource.getDataCharactersById(residents)
    override suspend fun getDataCharactersByType(type: String, value: String): Flow<DataState<CharactersModel?>> = remoteDataSource.getDataCharactersByType(type, value)


}