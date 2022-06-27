package com.training.music.network

import com.training.music.dto.SongDto
import com.training.music.response.Album
import com.training.music.response.Artist
import com.training.music.response.Playlist
import com.training.music.response.Song
import retrofit2.Response
import retrofit2.http.GET

interface ApiCall {

    @GET("artista/list")
    suspend fun listArtist():Response<Artist>

    @GET("cancion/list")
    suspend fun listSong():Response<Song>

    @GET("album/list")
    suspend fun listAlbum():Response<Album>

    @GET("playlists/list")
    suspend fun listPlaylist():Response<Playlist>
}