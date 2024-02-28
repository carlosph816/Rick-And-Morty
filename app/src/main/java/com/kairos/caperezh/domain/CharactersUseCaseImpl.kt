package com.kairos.caperezh.domain

import com.kairos.caperezh.common.DataState
import com.kairos.caperezh.data.model.ItemsModel
import com.kairos.caperezh.data.response.Result
import com.kairos.caperezh.presentation.Pages
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersUseCaseImpl @Inject constructor(
    private val homeRepository: HomeRepository
){
    suspend fun getCharacters(): Flow<DataState<ItemsModel?>> = homeRepository.getDataCharacters()
    suspend fun getCharactersByPage(page: Int, type: Pages): Flow<DataState<ItemsModel?>> = homeRepository.getDataCharactersByPage(page, type)
    suspend fun characterById(characters: List<String>): Flow<DataState<List<Result>?>> = homeRepository.getDataCharactersById(characters)
    suspend fun characterByType(type: String, value: String): Flow<DataState<ItemsModel?>> = homeRepository.getDataCharactersByType(type, value)
}