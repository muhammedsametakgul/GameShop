package com.example.oyunmagazasi.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.oyunmagazasi.R
import com.example.oyunmagazasi.data.Favoriler
import com.example.oyunmagazasi.data.Sahip
import com.example.oyunmagazasi.data.Sepet
import com.example.oyunmagazasi.databinding.FragmentProfilBinding
import com.example.oyunmagazasi.ui.adapter.FavorilerAdapter
import com.example.oyunmagazasi.ui.adapter.SahipAdapter
import com.example.oyunmagazasi.ui.adapter.SepetAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ProfilFragment : Fragment() {
    private lateinit var adapterSahip:SahipAdapter
    private lateinit var adapterFav:FavorilerAdapter
    private lateinit var binding:FragmentProfilBinding
    var list=ArrayList<Sahip>()
    var list2=ArrayList<Favoriler>()
    val db=Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilBinding.inflate(inflater,container,false)
        binding.rvprofile.layoutManager= LinearLayoutManager(requireContext())
        adapterSahip = SahipAdapter(requireContext(),list)
        binding.rvprofile.adapter = adapterSahip

        veriGetirSahip()
        binding.chipSahip.setOnClickListener {
            veriGetirSahip()
            adapterSahip = SahipAdapter(requireContext(),list)
            binding.rvprofile.adapter = adapterSahip
        }
        binding.chipFav.setOnClickListener {
            veriGetirFav()
            adapterFav = FavorilerAdapter(requireContext(),list2)
            binding.rvprofile.adapter = adapterFav
        }
        return binding.root
    }

    fun veriGetirSahip(){
        db.collection("Sahip").addSnapshotListener { snapshot, error ->
            if(error !=null){
                Log.e("Veri Çekme Hata",error.toString())
            }else{
                if(snapshot != null){
                    if(!snapshot.isEmpty){
                        list.clear()

                        val documents = snapshot.documents
                        for (document in documents){
                            //null kontrolü
                          var fiyat=0.0
                           if(document.get("fiyat") == null){
                                fiyat =0.0
                           }else{
                               fiyat= document.get("fiyat").toString().toDouble()
                           }
                            var oyunAdi=document.get("oyunAdi").toString()
                            var gorselUrl=document.get("gorselurl").toString()
                            var id=document.get("id").toString().toInt()!!
                            // Log.e("sepet görsel url",gorselUrl)
                            var indirilenOyun =Sahip(id,oyunAdi,fiyat,gorselUrl)
                            list.add(indirilenOyun)

                            adapterSahip.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }
    fun veriGetirFav(){
        db.collection("Favoriler").addSnapshotListener { snapshot, error ->
        if(error !=null){
            Log.e("Veri Çekme Hata",error.toString())
        }else{
            if(snapshot != null){
                if(!snapshot.isEmpty){
                    list2.clear()

                    val documents = snapshot.documents
                    for (document in documents){
                        //null kontrolü
                        var fiyat=0.0
                        if(document.get("fiyat") == null){
                            fiyat =0.0
                        }else{
                            fiyat= document.get("fiyat").toString().toDouble()
                        }
                        var oyunAdi=document.get("oyun").toString()
                        var gorselUrl=document.get("gorselUrl").toString()
                        var id=document.get("id").toString().toInt()!!
                        // Log.e("sepet görsel url",gorselUrl)
                        var indirilenOyun =Favoriler(id,oyunAdi,fiyat,gorselUrl)
                        list2.add(indirilenOyun)

                        adapterFav.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    }
}