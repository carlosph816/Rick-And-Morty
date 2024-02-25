package com.kairos.caperezh.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Info(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: @RawValue Any
): Parcelable