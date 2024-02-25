package com.kairos.caperezh.data

import com.kairos.caperezh.R
import com.kairos.caperezh.common.DataState
import com.kairos.caperezh.common.Filter
import com.kairos.caperezh.common.Gender
import com.kairos.caperezh.common.Status
import com.kairos.caperezh.data.model.CharactersModel
import com.kairos.caperezh.data.model.EpisodesModel
import com.kairos.caperezh.data.model.LocationsModel
import com.kairos.caperezh.data.response.GenericResponse
import com.kairos.caperezh.data.response.Result
import com.kairos.caperezh.data.response.toCharactersMap
import com.kairos.caperezh.data.response.toEpisodesMap
import com.kairos.caperezh.data.response.toLocationsMap
import com.kairos.caperezh.network.ApiService
import com.kairos.caperezh.network.safeCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    RemoteDataSource {

    override suspend fun getDataCharacters(): Flow<DataState<CharactersModel?>> = safeCall(
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

    override suspend fun getDataLocations(): Flow<DataState<LocationsModel?>> = safeCall(
        call = {
            val response = apiService.getAllLocations()
            if (response.isSuccessful) {
                response.body()?.let {
                    DataState.Success(it.toLocationsMap())
                } ?: DataState.Error(R.string.error_service)
            } else {
                DataState.Error(R.string.error_service)
            }
        },
        errorMessage = R.string.error_service
    )

    override suspend fun getDataEpisodes(): Flow<DataState<EpisodesModel?>> = safeCall(
        call = {
            val response = apiService.getAllEpisodes()
            if (response.isSuccessful) {
                response.body()?.let {
                    DataState.Success(it.toEpisodesMap())
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

    override suspend fun getDataCharactersByType(type: String, value: String): Flow<DataState<CharactersModel?>> = safeCall(
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