package com.example.oyunmagazasi.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.oyunmagazasi.data.Oyunlar
import com.example.oyunmagazasi.data.Sepet
import com.example.oyunmagazasi.databinding.FragmentAnaSayfaBinding
import com.example.oyunmagazasi.ui.adapter.OyunlarAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AnaSayfaFragment : Fragment() {
    private  lateinit var binding:FragmentAnaSayfaBinding
    val db = Firebase.firestore
    val oyunlarListesi = ArrayList<Oyunlar>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= FragmentAnaSayfaBinding.inflate(inflater, container, false)
        binding.rv.layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)


        val adapter = OyunlarAdapter(requireContext(),oyunlarListesi)
        binding.rv.adapter = adapter
        db.collection("Oyunlar").addSnapshotListener { snapshot, error ->
            if(error !=null){
                Log.e("Veri Çekme Hata",error.toString())
            }else{
                if(snapshot != null){
                    if(!snapshot.isEmpty){
                        oyunlarListesi.clear()
                        val documents = snapshot.documents
                        for (document in documents){
                            var fiyat= document.get("fiyat").toString().toDouble()!!
                            var oyunAdi=document.get("adi") as String
                            var gorselUrl=document.get("resimAdi").toString()
                            var id=document.get("id").toString().toInt()!!
                            var firma = document.get("firma").toString()
                            var yil = document.get("yil").toString().toInt()

                            var indirilenOyun = Oyunlar(id,oyunAdi,gorselUrl,yil,fiyat,firma)
                            oyunlarListesi.add(indirilenOyun)



                        }
                        adapter.notifyDataSetChanged()

                    }

                }
            }
        }







        return  binding.root
    }

  }

