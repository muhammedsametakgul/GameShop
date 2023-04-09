package com.example.oyunmagazasi.ui.adapter

import android.content.ClipData
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.oyunmagazasi.R
import com.example.oyunmagazasi.data.Oyunlar
import com.example.oyunmagazasi.data.Sepet
import com.example.oyunmagazasi.databinding.SepetimTasarimBinding
import com.example.oyunmagazasi.ui.fragment.AnaSayfaFragmentDirections
import com.example.oyunmagazasi.ui.fragment.SepetFragmentDirections
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class SepetAdapter (var mContext:Context,var sepetList:List<Sepet>):RecyclerView.Adapter<SepetAdapter.HolderClass>(){

    val db=Firebase.firestore
    inner class HolderClass(var binding:SepetimTasarimBinding): RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderClass {
        val binding =SepetimTasarimBinding.inflate(LayoutInflater.from(mContext),parent,false)
        return HolderClass(binding)
    }

    override fun getItemCount(): Int {
        return sepetList.size
    }
        var toplamFiyat=0.0
    override fun onBindViewHolder(holder: HolderClass, position: Int) {
        //var toplam =0.0
        val sepet=sepetList.get(position)
        val t = holder.binding
        t.textViewOyunAd.text=sepet.adi
        t.textViewOyunFiyat.text=sepet.fiyat.toString()
        toplamFiyat += sepet.fiyat
        if(sepet.gorselUrl !=null){
            Picasso.get().load(sepet.gorselUrl).into(t.imageViewOyunResim)
        }
        t.imageViewSilResim.setOnClickListener {
            db.collection("Sepetim").document(sepet.adi)
                .delete()
                .addOnSuccessListener {
                    Log.e("Silindi", "${sepet.adi} Silindi")
                  notifyDataSetChanged()
                    notifyItemRemoved(position)
                }.addOnFailureListener {
                        e -> Log.e("Silme Hata", e.toString())

                }

        }



    }




}