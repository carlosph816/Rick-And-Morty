package com.kairos.caperezh.network

import com.kairos.caperezh.data.response.CharacterResponse
import com.kairos.caperezh.data.response.EpisodeResponse
import com.kairos.caperezh.data.response.GenericResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("character")
    suspend fun getAllCharacters(): Response<GenericResponse>

    @GET("location")
    suspend fun getAllLocations(): Response<GenericResponse>

    @GET("episode")
    suspend fun getAllEpisodes(): Response<GenericResponse>


    @GET("episode/{id}")
    suspend fun getEpisodesById(@Path("id") id: Int): Response<EpisodeResponse>

    @GET("character/{id}")
    suspend fun getCharactersById(@Path("id") id: Int): Response<CharacterResponse>

    @GET("character")
    suspend fun getCharactersByGender(@Query("gender") gender: String): Response<GenericResponse>

    @GET("character")
    suspend fun getCharactersByStatus(@Query("status") status: String): Response<GenericResponse>

}