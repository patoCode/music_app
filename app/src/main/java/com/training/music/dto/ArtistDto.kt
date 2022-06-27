package com.training.music.dto

import java.io.Serializable

data class ArtistDto(
    var name :String,
    var alias : String
) : Serializable