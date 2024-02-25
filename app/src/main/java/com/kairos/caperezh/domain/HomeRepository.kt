package com.kairos.caperezh.domain

import com.kairos.caperezh.common.DataState
import com.kairos.caperezh.data.model.CharactersModel
import com.kairos.caperezh.data.model.EpisodesModel
import com.kairos.caperezh.data.model.LocationsModel
import com.kairos.caperezh.data.response.Result
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getDataCharacters() : Flow<DataState<CharactersModel?>>
    suspend fun getDataLocations() : Flow<DataState<LocationsModel?>>
    suspend fun getDataEpisodes() : Flow<DataState<EpisodesModel?>>


    suspend fun getDataEpisodesById(episodes: List<String>) : Flow<DataState<List<Result>?>>
    suspend fun getDataCharactersById(residents: List<String>) : Flow<DataState<List<Result>?>>
    suspend fun getDataCharactersByType(type: String, value: String) : Flow<DataState<CharactersModel?>>

}