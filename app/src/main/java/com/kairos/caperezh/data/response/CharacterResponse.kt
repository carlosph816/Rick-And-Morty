package com.kairos.caperezh.data.response

data class CharacterResponse(
    val gender: String,
    val id: Int,
    val name: String,
    val image: String,
    val species: String,
    val status: String,
    val type: String
)