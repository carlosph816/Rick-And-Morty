package com.kairos.caperezh

import com.kairos.caperezh.common.DataState
import com.kairos.caperezh.data.RemoteDataSourceImpl
import com.kairos.caperezh.data.model.ItemsModel
import com.kairos.caperezh.data.response.GenericResponse
import com.kairos.caperezh.data.response.Info
import com.kairos.caperezh.data.response.Location
import com.kairos.caperezh.data.response.Origin
import com.kairos.caperezh.data.response.Result
import com.kairos.caperezh.network.ApiService
import com.kairos.caperezh.util.MockWebServerObject.getMockRetrofit
import com.kairos.caperezh.util.MockWebServerObject.moshi
import com.kairos.caperezh.util.ResponseUtil.enqueueEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class CharactersUseCaseTest {
    private val mockWebServer = MockWebServer()
    private val mockRetrofit = mockWebServer.getMockRetrofit().create(ApiService::class.java)

    private val rickRepositoryImpl =  RemoteDataSourceImpl(mockRetrofit)

    private var characterInfoAdapter = moshi.adapter(GenericResponse::class.java)
    private var modelInformation = ItemsModel(
        info = Info(
            count = 234,
            pages = 32,
            next ="https://rickandmortyapi.com/api/character/?page=2",
            prev = ""
        ),
        results = listOf(Result(
            created = "",
            episode = "",
            residents = listOf(""),
            characters = listOf(""),
            gender = "",
            id = 1,
            image = "",
            location = Location(
                name = "",
                url = ""
            ),
            name = "",
            origin = Origin(
                name = "",
                url = ""
            ),
            species = "",
            status = "",
            type = "",
            url = "",
            dimension = "",
            air_date = ""
        ))
    )


    private var listEpisodes = listOf(Result(
        created = "",
        episode = "",
        residents = listOf(""),
        characters = listOf(""),
        gender = "",
        id = 1,
        image = "",
        location = Location(
            name = "",
            url = ""
        ),
        name = "",
        origin = Origin(
            name = "",
            url = ""
        ),
        species = "",
        status = "",
        type = "",
        url = "",
        dimension = "",
        air_date = ""
    ))

    var episodes =  listOf(
        ""
    )


    private var responseReferee = characterInfoAdapter.toJson(
        GenericResponse(
            info = Info(
                count = 234,
                pages = 32,
                next ="https://rickandmortyapi.com/api/character/?page=2",
                prev = ""
            ),
            results = listOf(Result(
                created = "",
                episode = "",
                residents = listOf(""),
                characters = listOf(""),
                gender = "",
                id = 1,
                image = "",
                location = Location(
                    name = "",
                    url = ""
                ),
                name = "",
                origin = Origin(
                    name = "",
                    url = ""
                ),
                species = "",
                status = "",
                type = "",
                url = "",
                dimension = "",
                air_date = ""
            ))
        )
    )

    val type: String = "gender"
    val value: String = "male"

    @Test
    fun `get all characters success returned`() {
        mockWebServer.enqueueEntity(responseReferee, 200)
        runBlocking {
            val charactersResponse = rickRepositoryImpl.getDataCharacters().last()
            val characterRequest = mockWebServer.takeRequest(1, TimeUnit.SECONDS)
            Assert.assertEquals(
                "/character",
                characterRequest?.path
            )
            Assert.assertEquals(DataState.Success(modelInformation), charactersResponse)
        }
    }

    @Test
    fun `get all locations success returned`() {
        mockWebServer.enqueueEntity(responseReferee, 200)
        runBlocking {
            val locationsResponse = rickRepositoryImpl.getDataLocations().last()
            val locationRequest = mockWebServer.takeRequest(1, TimeUnit.SECONDS)
            Assert.assertEquals(
                "/location",
                locationRequest?.path
            )
            Assert.assertEquals(DataState.Success(modelInformation), locationsResponse)
        }
    }

    @Test
    fun `get all episodes success returned`() {
        mockWebServer.enqueueEntity(responseReferee, 200)
        runBlocking {
            val episodeResponse = rickRepositoryImpl.getDataEpisodes().last()
            val episodeRequest = mockWebServer.takeRequest(1, TimeUnit.SECONDS)
            Assert.assertEquals(
                "/episode",
                episodeRequest?.path
            )
            Assert.assertEquals(DataState.Success(modelInformation), episodeResponse)
        }
    }


    @Test
    fun `get characters by type `() {
        mockWebServer.enqueueEntity(responseReferee, 200)
        runBlocking {
            val episodeResponse = rickRepositoryImpl.getDataCharactersByType(type, value).last()
            val episodeRequest = mockWebServer.takeRequest(1, TimeUnit.SECONDS)

            val actualPath = episodeRequest?.path?.substringBefore("?") ?: ""

            Assert.assertEquals(
                "/character",
                actualPath
            )
            Assert.assertEquals(DataState.Success(modelInformation), episodeResponse)
        }
    }

    @Test
    fun `get all characters error returned`() {
        mockWebServer.enqueueEntity(responseReferee, 400)
        runBlocking {
            val charactersResponse = rickRepositoryImpl.getDataCharacters().last()
            val characterRequest = mockWebServer.takeRequest(1, TimeUnit.SECONDS)
            Assert.assertEquals(
                "/character",
                characterRequest?.path
            )
            Assert.assertEquals(
                DataState.Error(
                    exception = R.string.error_service,
                    errorCode = 0
                ),charactersResponse
            )
        }
    }


    @Test
    fun `get all locations error returned`() {
        mockWebServer.enqueueEntity(responseReferee, 400)
        runBlocking {
            val locationsResponse = rickRepositoryImpl.getDataLocations().last()
            val characterRequest = mockWebServer.takeRequest(1, TimeUnit.SECONDS)
            Assert.assertEquals(
                "/location",
                characterRequest?.path
            )
            Assert.assertEquals(
                DataState.Error(
                    exception = R.string.error_service,
                    errorCode = 0
                ),locationsResponse
            )
        }
    }

    @Test
    fun `get all episodes error returned`() {
        mockWebServer.enqueueEntity(responseReferee, 400)
        runBlocking {
            val episodesResponse = rickRepositoryImpl.getDataEpisodes().last()
            val characterRequest = mockWebServer.takeRequest(1, TimeUnit.SECONDS)
            Assert.assertEquals(
                "/episode",
                characterRequest?.path
            )
            Assert.assertEquals(
                DataState.Error(
                    exception = R.string.error_service,
                    errorCode = 0
                ),episodesResponse
            )
        }
    }

    @Test
    fun `get characters by type error `() {
        mockWebServer.enqueueEntity(responseReferee, 500)
        runBlocking {
            val episodeResponse = rickRepositoryImpl.getDataCharactersByType(type, value).last()
            val episodeRequest = mockWebServer.takeRequest(1, TimeUnit.SECONDS)

            val actualPath = episodeRequest?.path?.substringBefore("?") ?: ""

            Assert.assertEquals(
                "/character",
                actualPath
            )
            Assert.assertEquals(
                DataState.Error(
                    exception = R.string.error_service,
                    errorCode = 0
                ),episodeResponse
            )
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

}