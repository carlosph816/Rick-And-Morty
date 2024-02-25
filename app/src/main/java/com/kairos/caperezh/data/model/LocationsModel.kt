package com.kairos.caperezh.data.model

import com.kairos.caperezh.data.response.Info
import com.kairos.caperezh.data.response.Result

data class LocationsModel (
    val info: Info,
    val results: List<Result>
)