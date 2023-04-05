package com.example.oyunmagazasi.ui.fragment

import android.net.Uri
import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.oyunmagazasi.R
import com.example.oyunmagazasi.data.Oyunlar
import com.example.oyunmagazasi.data.Sepet
import com.example.oyunmagazasi.databinding.FragmentSepetBinding
import com.example.oyunmagazasi.databinding.SepetimTasarimBinding
import com.example.oyunmagazasi.ui.adapter.OyunlarAdapter
import com.example.oyunmagazasi.ui.adapter.SepetAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SepetFragment : Fragment() {
        private lateinit var binding :FragmentSepetBinding
        val db=Firebase.firestore
        var list=ArrayList<Sepet>()
    private lateinit var adapter:SepetAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSepetBinding.inflate(inflater,container,false)



        db.collection("Sepetim").addSnapshotListener { snapshot, error ->
            if(error !=null){
                Log.e("Veri Çekme Hata",error.toString())
            }else{
                    if(snapshot != null){
                        if(!snapshot.isEmpty){
                            list.clear()
                            val documents = snapshot.documents
                            for (document in documents){

                                var fiyat= document.get("fiyat").toString().toDouble()!!
                                var oyunAdi=document.get("oyun") as String
                                var gorselUrl=document.get("gorselUrl").toString()
                                var id=document.get("id").toString().toInt()!!
                               // Log.e("sepet görsel url",gorselUrl)
                                var indirilenOyun =Sepet(id,oyunAdi,fiyat,gorselUrl)
                                list.add(indirilenOyun)
                            }
                            adapter.notifyDataSetChanged()
                        }
                    }
            }
        }

        binding.rv2.layoutManager= LinearLayoutManager( requireContext())
        adapter = SepetAdapter(requireContext(),list)
        binding.rv2.adapter = adapter



        return binding.root
    }
}