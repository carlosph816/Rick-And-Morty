package com.kairos.caperezh.domain

import com.kairos.caperezh.common.DataState
import com.kairos.caperezh.data.model.ItemsModel
import com.kairos.caperezh.data.response.Result
import com.kairos.caperezh.presentation.Pages
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getDataCharacters() : Flow<DataState<ItemsModel?>>
    suspend fun getDataLocations() : Flow<DataState<ItemsModel?>>
    suspend fun getDataEpisodes() : Flow<DataState<ItemsModel?>>

    suspend fun getDataCharactersByPage(page: Int,  type: Pages) : Flow<DataState<ItemsModel?>>

    suspend fun getDataEpisodesById(episodes: List<String>) : Flow<DataState<List<Result>?>>
    suspend fun getDataCharactersById(residents: List<String>) : Flow<DataState<List<Result>?>>
    suspend fun getDataCharactersByType(type: String, value: String) : Flow<DataState<ItemsModel?>>

}