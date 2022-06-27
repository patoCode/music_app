package com.training.music.dto

import java.io.Serializable

data class PlaylistDto(
    var name :String,
    var priority: Int,
    var cancion: MutableList<*>
): Serializable