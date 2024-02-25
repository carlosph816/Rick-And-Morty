package com.kairos.caperezh.common

enum class Filter{
   ALL
}

enum class Gender (val gender: String){
    MALE("male"),
    FEMALE("female"),
    GENDERLESS("genderless"),
    UNKNOWN("unknown")
}

enum class Status (val status: String){
    ALIVE("alive"),
    DEAD("dead"),
    UNKNOWN("unknown")
}