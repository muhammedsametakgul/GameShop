package com.example.oyunmagazasi.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oyunmagazasi.data.Sahip
import com.example.oyunmagazasi.databinding.FragmentProfilBinding
import com.example.oyunmagazasi.databinding.SahipOlunanTasarimBinding
import com.example.oyunmagazasi.databinding.SepetimTasarimBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import java.math.MathContext

class SahipAdapter(var mContext:Context,var list:List<Sahip>):RecyclerView.Adapter<SahipAdapter.ViewHolder>() {
    val db= Firebase.firestore
    inner class ViewHolder(var binding: SahipOlunanTasarimBinding):androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SahipOlunanTasarimBinding.inflate(LayoutInflater.from(mContext),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val gelen=list.get(position)
        val t =holder.binding
        t.textViewOyunAd.text=gelen.oyunAdi
        t.textViewOyunFiyat.text="${gelen.fiyat.toString()} ₺"
        if(gelen.gorselUrl !=null){
            Picasso.get().load(gelen.gorselUrl).into(t.imageViewOyunResim)
        }
        t.imageViewSilResim.setOnClickListener {
            db.collection("Sahip").document(gelen.oyunAdi)
                .delete()
                .addOnSuccessListener {
                    Log.e("Silindi", "${gelen.oyunAdi} Kütüphaneden Kaldırıldı")
                    notifyDataSetChanged()
                    notifyItemRemoved(position)
                }.addOnFailureListener {
                        e -> Log.e("Silme Hata", e.toString())

                }

        }

    }
}