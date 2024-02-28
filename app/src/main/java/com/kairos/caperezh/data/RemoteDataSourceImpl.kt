package com.kairos.caperezh.data

import com.kairos.caperezh.R
import com.kairos.caperezh.common.DataState
import com.kairos.caperezh.data.model.ItemsModel
import com.kairos.caperezh.data.response.GenericResponse
import com.kairos.caperezh.data.response.Result
import com.kairos.caperezh.data.response.toCharactersMap
import com.kairos.caperezh.network.ApiService
import com.kairos.caperezh.network.safeCall
import com.kairos.caperezh.presentation.Pages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    RemoteDataSource {

    override suspend fun getDataCharacters(): Flow<DataState<ItemsModel?>> = safeCall(
        call = {
            val response = apiService.getAllCharacters()
            if (response.isSuccessful) {
                response.body()?.let {
                    DataState.Success(it.toCharactersMap())
                } ?: DataState.Error(R.string.error_service)
            } else {
                DataState.Error(R.string.error_service)
            }
        },
        errorMessage = R.string.error_service
    )

    override suspend fun getDataLocations(): Flow<DataState<ItemsModel?>> = safeCall(
        call = {
            val response = apiService.getAllLocations()
            if (response.isSuccessful) {
                response.body()?.let {
                    DataState.Success(it.toCharactersMap())
                } ?: DataState.Error(R.string.error_service)
            } else {
                DataState.Error(R.string.error_service)
            }
        },
        errorMessage = R.string.error_service
    )

    override suspend fun getDataEpisodes(): Flow<DataState<ItemsModel?>> = safeCall(
        call = {
            val response = apiService.getAllEpisodes()
            if (response.isSuccessful) {
                response.body()?.let {
                    DataState.Success(it.toCharactersMap())
                } ?: DataState.Error(R.string.error_service)
            } else {
                DataState.Error(R.string.error_service)
            }
        },
        errorMessage = R.string.error_service
    )

    override suspend fun getDataCharactersByPage(page: Int, type: Pages): Flow<DataState<ItemsModel?>> = safeCall(
        call = {
            val response: Response<GenericResponse> = when(type){
                Pages.CHARACTERS -> {
                    apiService.getCharactersByPage(page)
                }

                Pages.LOCATIONS -> {
                    apiService.getLocationsByPage(page)
                }

                Pages.EPISODES -> {
                    apiService.getEpisodesByPage(page)
                }
            }
            if (response.isSuccessful) {
                response.body()?.let {
                    DataState.Success(it.toCharactersMap())
                } ?: DataState.Error(R.string.error_service)
            } else {
                DataState.Error(R.string.error_service)
            }
        },
        errorMessage = R.string.error_service
    )


    override suspend fun getDataEpisodesById(episodes: List<String>): Flow<DataState<List<Result>?>> = flow {
        emit(DataState.Loading(true))
        val mylist =  mutableListOf<Result>()
        for (ep in  episodes){
            val id = ep.split("/")
            val episodeNumber = id.last()
            val response =  apiService.getEpisodesById(episodeNumber.toInt())
            if (response.isSuccessful) {
                mylist.add(
                    Result(
                        name = response.body()?.name,
                        episode = response.body()?.episode,
                        air_date = response.body()?.air_date
                    ))
            } else {
                mylist.add(
                    Result()
                )
            }
        }
        emit(DataState.Success(mylist))
    }

    override suspend fun getDataCharactersById(residents: List<String>): Flow<DataState<List<Result>?>> = flow{
        emit(DataState.Loading(true))
        val mylist =  mutableListOf<Result>()
        for (ep in  residents){
            val id = ep.split("/")
            val episodeNumber = id.last()
            val response =  apiService.getCharactersById(episodeNumber.toInt())
            if (response.isSuccessful) {
                mylist.add(
                    Result(
                        status = response.body()?.status,
                        name = response.body()?.name,
                        image = response.body()?.image
                    ))
            } else {
                mylist.add(
                    Result()
                )
            }
        }
        emit(DataState.Success(mylist))
    }

    override suspend fun getDataCharactersByType(type: String, value: String): Flow<DataState<ItemsModel?>> = safeCall(
        call = {
            var response: Response<GenericResponse>? = null
            response = if(type == "gender"){
                apiService.getCharactersByGender(value)
            }else{
                apiService.getCharactersByStatus(value)
            }

            if (response.isSuccessful) {
                response.body()?.let {
                    DataState.Success(it.toCharactersMap())
                } ?: DataState.Error(R.string.error_service)
            } else {
                DataState.Error(R.string.error_service)
            }
        },
        errorMessage = R.string.error_service
    )

}