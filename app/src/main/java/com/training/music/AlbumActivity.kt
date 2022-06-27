package com.training.music

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.music.databinding.ActivityAlbumBinding
import com.training.music.dto.AlbumDto
import com.training.music.dto.SongDto
import com.training.music.generics.GenericAdapter
import com.training.music.generics.GenericBinding
import com.training.music.network.ApiCall
import com.training.music.network.ApiObject
import com.training.music.response.Album
import com.training.music.response.Song
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class AlbumActivity : AppCompatActivity() {

    lateinit var binding : ActivityAlbumBinding
    private lateinit var adapter : GenericAdapter<AlbumDto>
    private val _albumList = mutableListOf<AlbumDto>()
    private val bindingInterface = object : GenericBinding<AlbumDto> {
        override fun bindData(item:AlbumDto, view: View){
            var _tvName = view.findViewById<TextView>(R.id.tvName)
            _tvName.text = item.name.uppercase()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecycler()
        list()
    }

    private fun initRecycler() {
        adapter = GenericAdapter<AlbumDto>(_albumList, R.layout.album_item, bindingInterface)
        binding.rvAlbum.layoutManager = LinearLayoutManager(this)
        binding.rvAlbum.adapter = adapter
    }

    private fun list() {
        CoroutineScope(Dispatchers.IO).launch {
            val _res: Response<Album>
            _res = ApiObject.getRetro().create(ApiCall::class.java).listAlbum()
            val _response = _res.body()!!
            val _list = _response.data
            Log.d("TAG", _list.toString())
            runOnUiThread{
                if(_res.isSuccessful){
                    _albumList.addAll(_list)
                    adapter.notifyDataSetChanged()
                }else{
                    Toast.makeText(this@AlbumActivity, "ERROR DE CONEXION", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}