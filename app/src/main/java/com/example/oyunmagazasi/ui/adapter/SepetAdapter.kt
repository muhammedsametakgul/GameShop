package com.example.oyunmagazasi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.oyunmagazasi.data.Oyunlar
import com.example.oyunmagazasi.data.Sepet
import com.example.oyunmagazasi.databinding.SepetimTasarimBinding
import com.squareup.picasso.Picasso

class SepetAdapter (var mContext:Context,var sepetList:List<Sepet>):RecyclerView.Adapter<SepetAdapter.HolderClass>(){

    inner class HolderClass(var binding:SepetimTasarimBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderClass {
        val binding =SepetimTasarimBinding.inflate(LayoutInflater.from(mContext),parent,false)
        return HolderClass(binding)
    }

    override fun getItemCount(): Int {
        return sepetList.size
    }

    override fun onBindViewHolder(holder: HolderClass, position: Int) {
        val sepet=sepetList.get(position)
        val t = holder.binding
        t.textViewOyunAd.text=sepet.adi
        t.textViewOyunFiyat.text=sepet.fiyat.toString()
        if(sepet.gorselUrl !=null){
            Picasso.get().load(sepet.gorselUrl).into(t.imageViewOyunResim)
        }
    }
}