package com.example.oyunmagazasi.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oyunmagazasi.data.Favoriler
import com.example.oyunmagazasi.databinding.FavorilerTasarimBinding
import com.example.oyunmagazasi.databinding.SahipOlunanTasarimBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class FavorilerAdapter(var mContext:Context,var list:List<Favoriler>):RecyclerView.Adapter<FavorilerAdapter.ViewHolder>() {

    inner class ViewHolder(var binding:FavorilerTasarimBinding):androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FavorilerTasarimBinding.inflate(LayoutInflater.from(mContext),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }
        val db = Firebase.firestore
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fav=list.get(position)
        val t = holder.binding
        t.textViewOyunAd.text=fav.oyunAdi
        t.textViewOyunFiyat.text="${fav.fiyat.toString()} ₺"
        if(fav.gorselUrl !=null){
            Picasso.get().load(fav.gorselUrl).into(t.imageViewOyunResim)
        }
        t.imageViewSilResim.setOnClickListener {
            db.collection("Favoriler").document(fav.oyunAdi)
                .delete()
                .addOnSuccessListener {
                    Log.e("Silindi", "${fav.oyunAdi} Kütüphaneden Kaldırıldı")
                    notifyDataSetChanged()
                    notifyItemRemoved(position)
                }.addOnFailureListener {
                        e -> Log.e("Silme Hata", e.toString())

                }
        }
    }
}