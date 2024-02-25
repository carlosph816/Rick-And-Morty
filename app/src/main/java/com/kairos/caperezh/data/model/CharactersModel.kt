package com.kairos.caperezh.data.model

import android.os.Parcelable
import com.kairos.caperezh.data.response.Info
import com.kairos.caperezh.data.response.Result
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharactersModel (
    val info: Info,
    val character: List<Result>
): Parcelable