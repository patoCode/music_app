package com.training.music

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.music.databinding.ActivityPlaylistBinding
import com.training.music.dto.PlaylistDto
import com.training.music.dto.SongDto
import com.training.music.generics.GenericAdapter
import com.training.music.generics.GenericBinding
import com.training.music.network.ApiCall
import com.training.music.network.ApiObject
import com.training.music.response.Playlist
import com.training.music.response.Song
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class PlaylistActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPlaylistBinding
    private lateinit var adapter : GenericAdapter<PlaylistDto>
    private val _list = mutableListOf<PlaylistDto>()

    private val bindingInterface = object : GenericBinding<PlaylistDto> {
        override fun bindData(item:PlaylistDto, view: View){
            var _tvName = view.findViewById<TextView>(R.id.tvName)
            _tvName.text = item.name.uppercase()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecycler()
        list()
    }

    private fun initRecycler() {
        adapter = GenericAdapter<PlaylistDto>(_list, R.layout.playlist_item, bindingInterface)
        binding.rvPlaylist.layoutManager = LinearLayoutManager(this)
        binding.rvPlaylist.adapter = adapter
    }

    private fun list() {
        CoroutineScope(Dispatchers.IO).launch {
            val _res: Response<Playlist>
            _res = ApiObject.getRetro().create(ApiCall::class.java).listPlaylist()
            val _response = _res.body()!!
            val _playlist = _response.data
            Log.d("TAG", _playlist.toString())
            runOnUiThread{
                if(_res.isSuccessful){
                    _list.addAll(_playlist)
                    adapter.notifyDataSetChanged()
                }else{
                    Toast.makeText(this@PlaylistActivity, "ERROR DE CONEXION", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}