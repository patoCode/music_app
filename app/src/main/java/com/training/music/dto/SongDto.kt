package com.training.music.dto

import com.training.music.response.Playlist
import java.io.Serializable

data class SongDto(
    var name : String,
    var image : String,
    var track : Int,
    var composer : String,
    var url : String,
    var lyrics : String,
    var playlist: MutableList<*>
):Serializable