package com.kairos.caperezh.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kairos.caperezh.common.DataState
import com.kairos.caperezh.common.Filter
import com.kairos.caperezh.common.Gender
import com.kairos.caperezh.common.Status
import com.kairos.caperezh.common.TypeInformation
import com.kairos.caperezh.data.response.Info
import com.kairos.caperezh.data.response.Result
import com.kairos.caperezh.domain.CharactersUseCaseImpl
import com.kairos.caperezh.domain.EpisodesUseCaseImpl
import com.kairos.caperezh.domain.LocationsUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InformationViewModel @Inject constructor(
    private val charactersUserCaseImpl: CharactersUseCaseImpl,
    private val locationsUserCaseImpl: LocationsUseCaseImpl,
    private val episodesUserCaseImpl: EpisodesUseCaseImpl,
) : ViewModel() {


    private var typeInformation: TypeInformation = TypeInformation.CHARACTERS

    var myGlobalList: MutableList<Result> = mutableListOf()
        private set

    var myViewList: MutableList<Result> = mutableStateListOf()
        private set

    var myDetailEpisodesList: MutableList<Result> = mutableStateListOf()
        private set

    var myDetailCharactersList: MutableList<Result> = mutableStateListOf()
        private set

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Success)
    val viewState: StateFlow<ViewState> = _viewState

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    var showLoader by mutableStateOf(false)
        private set

    var info: Info? = null

    fun setTypeInformation(type: TypeInformation) {
        typeInformation = type
        when (type) {
            TypeInformation.CHARACTERS -> {
                getCharactersInformation()
            }

            TypeInformation.LOCATIONS -> {
                getLocationsInformation()
            }

            TypeInformation.EPISODES -> {
                getEpisodesInformation()
            }
        }
    }

    private fun getCharactersInformation() = viewModelScope.launch {
        charactersUserCaseImpl.getCharacters().collect { response ->
            showLoader = response is DataState.Loading
            when (response) {
                is DataState.Success -> {
                    clearLists()
                    myGlobalList.addAll(response.data?.results ?: emptyList())
                    myViewList.addAll(response.data?.results ?: emptyList())
                    info = response.data?.info
                    getPaginationNumber()
                }

                is DataState.Error -> {
                    _viewState.value = ViewState.Error(response.errorCode)
                }

                else -> {}
            }
        }
    }

    internal fun getInformationByPage(page: Int, type: Pages) = viewModelScope.launch {
        charactersUserCaseImpl.getCharactersByPage(page, type).collect { response ->
            showLoader = response is DataState.Loading
            when (response) {
                is DataState.Success -> {
                    clearLists()
                    myGlobalList.addAll(response.data?.results ?: emptyList())
                    myViewList.addAll(response.data?.results ?: emptyList())
                    info = response.data?.info
                }

                is DataState.Error -> {
                    _viewState.value = ViewState.Error(response.errorCode)
                }

                else -> {}
            }
        }
    }

    fun getCharactersFilter(filter: Any) = viewModelScope.launch {
        when (filter) {
            is Filter -> {
                getCharactersInformation()
            }

            is Gender -> {
                getInformationFilter("gender", filter.gender)
            }

            is Status -> {
                getInformationFilter("status", filter.status)
            }
        }
    }

    private fun getInformationFilter(type: String, value: String) = viewModelScope.launch {
        charactersUserCaseImpl.characterByType(type, value).collect { response ->
            showLoader = response is DataState.Loading
            when (response) {
                is DataState.Success -> {
                    clearLists()
                    myGlobalList.addAll(response.data?.results ?: emptyList())
                    myViewList.addAll(response.data?.results ?: emptyList())
                }

                is DataState.Error -> {
                    _viewState.value = ViewState.Error(response.errorCode)
                }

                else -> {}
            }
        }
    }

    private fun getLocationsInformation() = viewModelScope.launch {
        locationsUserCaseImpl.getLocations().collect { response ->
            showLoader = response is DataState.Loading
            when (response) {
                is DataState.Success -> {
                    clearLists()
                    myGlobalList.addAll(response.data?.results ?: emptyList())
                    myViewList.addAll(response.data?.results ?: emptyList())
                    info = response.data?.info
                }

                is DataState.Error -> {
                    _viewState.value = ViewState.Error(response.errorCode)
                }

                else -> {}
            }
        }
    }

    private fun getEpisodesInformation() = viewModelScope.launch {
        episodesUserCaseImpl.getEpisodes().collect { response ->
            showLoader = response is DataState.Loading
            when (response) {
                is DataState.Success -> {
                    clearLists()
                    myGlobalList.addAll(response.data?.results ?: emptyList())
                    myViewList.addAll(response.data?.results ?: emptyList())
                    info = response.data?.info
                }

                is DataState.Error -> {
                    _viewState.value = ViewState.Error(response.errorCode)
                }

                else -> {}
            }
        }
    }

    fun getEpisodesById(episodes: List<String>) = viewModelScope.launch {
        episodesUserCaseImpl.episodeById(episodes).collect { response ->
            showLoader = response is DataState.Loading
            when (response) {
                is DataState.Success -> {
                    clearLists()
                    myDetailEpisodesList.addAll(response.data ?: emptyList())
                }

                is DataState.Error -> {
                    _viewState.value = ViewState.Error(response.errorCode)
                }

                else -> {}
            }
        }
    }

    fun getCharactersById(characters: List<String>) = viewModelScope.launch {
        charactersUserCaseImpl.characterById(characters).collect { response ->
            showLoader = response is DataState.Loading
            when (response) {
                is DataState.Success -> {
                    clearLists()
                    myDetailCharactersList.addAll(response.data ?: emptyList())
                }

                is DataState.Error -> {
                    _viewState.value = ViewState.Error(response.errorCode)
                }

                else -> {}
            }
        }
    }

    private fun clearLists() {
        myGlobalList.clear()
        myViewList.clear()
    }

    fun setSearchQuery(query: String) {
        myViewList.clear()
        _searchQuery.value = query
        val list = filterItems(myGlobalList, searchQuery.value)
        myViewList.addAll(list)
    }

    private fun filterItems(
        items: List<Result>,
        query: String,
    ): List<Result> {
        return items.filter { item ->
            if (item.episode is String) {
                val episode = item.episode
                item.name?.contains(query, ignoreCase = true) == true || episode.contains(
                    query,
                    ignoreCase = true
                ) || item.created?.contains(query, ignoreCase = true) == true ||
                        item.air_date?.contains(query, ignoreCase = true) == true
            } else {
                item.name?.contains(query, ignoreCase = true) == true || item.dimension?.contains(
                    query,
                    ignoreCase = true
                ) == true || item.air_date?.contains(query, ignoreCase = true) == true
            }
        }
    }



    fun getPaginationNumber(): Pair<Int?, Int?>{
        val before: Int? = if(info?.prev == null){
            1
        }else{
            val result = info?.prev?.split("=")
            result?.get(1)?.toInt()
        }

        val after: Int? = if(info?.next == null){
            1
        }else{
            val result = info?.next?.split("=")
            result?.get(1)?.toInt()
        }
        return Pair(before, after)
    }
}

sealed class ViewState {
    object Success : ViewState()
    data class Error(val message: Int) : ViewState()
}

enum class Pages{
    CHARACTERS,
    LOCATIONS,
    EPISODES
}