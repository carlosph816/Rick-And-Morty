package com.kairos.caperezh.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Parcelize
data class Result(
    val created: String? = null,
    val episode: @RawValue Any?  = null,
    val residents: List<String>?  = null,
    val characters: List<String>? = null,
    val gender: String? = null,
    val id: Int? = null,
    val image: String? = null,
    val location: Location? = null,
    val name: String? = null,
    val origin: Origin? = null,
    val species: String? = null,
    val status: String? = null,
    val type: String? = null,
    val url: String? = null,
    val dimension: String? = null,
    val air_date: String? = null
): Parcelable

fun Result.toDetail() : Result {
    return Result(
        this.created,
        if(this.episode is List<*>){
            val re = this.episode as List<String>
            re.map { tr ->
                URLEncoder.encode(tr, StandardCharsets.UTF_8.toString())
            }
        }else{
            this.episode
        },
        this.residents?.map {
            URLEncoder.encode(it, StandardCharsets.UTF_8.toString())
        },
        this.characters?.map {
            URLEncoder.encode(it, StandardCharsets.UTF_8.toString())
        },
        this.gender,
        this.id,
        URLEncoder.encode(this.image ?: "", StandardCharsets.UTF_8.toString()),
        this.location?.copy(name = location.name, url = URLEncoder.encode(location.url, StandardCharsets.UTF_8.toString())),
        this.name,
        this.origin?.copy(name = origin.name, url = URLEncoder.encode(origin.url, StandardCharsets.UTF_8.toString())),
        this.species,
        this.status,
        this.type,
        URLEncoder.encode(this.url, StandardCharsets.UTF_8.toString()),
        this.dimension,
        this.air_date
    )
}
