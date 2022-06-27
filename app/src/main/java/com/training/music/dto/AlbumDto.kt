package com.training.music.dto

import java.io.Serializable

data class AlbumDto(
    var name: String,
    var cover: String,
    var year: Int,
    var cancion: MutableList<*>
):Serializable