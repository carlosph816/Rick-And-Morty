package com.kairos.caperezh.domain

import com.kairos.caperezh.common.DataState
import com.kairos.caperezh.data.model.CharactersModel
import com.kairos.caperezh.data.response.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersUseCaseImpl @Inject constructor(
    private val homeRepository: HomeRepository
){
    suspend fun getCharacters(): Flow<DataState<CharactersModel?>> = homeRepository.getDataCharacters()
    suspend fun characterById(characters: List<String>): Flow<DataState<List<Result>?>> = homeRepository.getDataCharactersById(characters)
    suspend fun characterByType(type: String, value: String): Flow<DataState<CharactersModel?>> = homeRepository.getDataCharactersByType(type, value)
}