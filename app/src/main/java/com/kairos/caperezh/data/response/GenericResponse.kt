package com.kairos.caperezh.data.response

import com.kairos.caperezh.data.model.ItemsModel

data class GenericResponse(
    val info: Info,
    val results: List<Result>
)

internal fun GenericResponse.toCharactersMap() = ItemsModel(
    this.info,
    this.results
)


