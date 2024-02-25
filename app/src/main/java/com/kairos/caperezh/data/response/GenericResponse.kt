package com.kairos.caperezh.data.response

import com.kairos.caperezh.data.model.CharactersModel
import com.kairos.caperezh.data.model.EpisodesModel
import com.kairos.caperezh.data.model.LocationsModel

data class GenericResponse(
    val info: Info,
    val results: List<Result>
)

internal fun GenericResponse.toCharactersMap() = CharactersModel(
    this.info,
    this.results
)

internal fun GenericResponse.toLocationsMap() = LocationsModel(
    this.info,
    this.results
)


internal fun GenericResponse.toEpisodesMap() = EpisodesModel(
    this.info,
    this.results
)


