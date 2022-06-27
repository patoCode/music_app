package com.training.music

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.music.databinding.ActivityArtistBinding
import com.training.music.dto.ArtistDto
import com.training.music.generics.GenericAdapter
import com.training.music.generics.GenericBinding
import com.training.music.network.ApiCall
import com.training.music.network.ApiObject
import com.training.music.response.Artist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class ArtistActivity : AppCompatActivity() {
    private lateinit var binding : ActivityArtistBinding
    private lateinit var adapter : GenericAdapter<ArtistDto>
    private val _artistList = mutableListOf<ArtistDto>()

    private val bindingInterface = object : GenericBinding<ArtistDto> {
        override fun bindData(item:ArtistDto, view: View){
            var _tvName = view.findViewById<TextView>(R.id.tvName)
            var _tvAlias = view.findViewById<TextView>(R.id.tvAlias)
            _tvName!!.text = item.name.toString()
            _tvAlias!!.text = item.alias.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecycler()
        list()
    }
    private fun initRecycler(){
        adapter = GenericAdapter<ArtistDto>(_artistList, R.layout.artist_item, bindingInterface)
        binding.rvArtista.layoutManager = LinearLayoutManager(this)
        binding.rvArtista.adapter = adapter
    }

    fun list(){
        CoroutineScope(Dispatchers.IO).launch {
            val _res: Response<Artist>
            _res = ApiObject.getRetro().create(ApiCall::class.java).listArtist()
            val _response = _res.body()!!
            val _list = _response.data
            Log.d("TAG", _list.toString())
            runOnUiThread{
                if(_res.isSuccessful){
                    _artistList.addAll(_list)
                    adapter.notifyDataSetChanged()
                }else{
                    Toast.makeText(this@ArtistActivity, "ERROR DE CONEXION", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}