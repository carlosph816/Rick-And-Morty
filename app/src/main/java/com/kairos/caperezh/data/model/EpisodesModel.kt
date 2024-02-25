package com.kairos.caperezh.data.model

import com.kairos.caperezh.data.response.Info
import com.kairos.caperezh.data.response.Result

data class EpisodesModel (
    val info: Info,
    val character: List<Result>
)