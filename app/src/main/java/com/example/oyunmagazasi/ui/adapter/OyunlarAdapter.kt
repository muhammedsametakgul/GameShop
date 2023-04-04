package com.example.oyunmagazasi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.oyunmagazasi.data.Oyunlar
import com.example.oyunmagazasi.databinding.CardTasarimBinding
import com.example.oyunmagazasi.ui.fragment.AnaSayfaFragmentDirections
import com.google.android.material.snackbar.Snackbar

class OyunlarAdapter(var mContext: Context,var oyunlarListesi:List<Oyunlar>)
    :RecyclerView.Adapter<OyunlarAdapter.HolderClass>() {

    inner class HolderClass(var binding:CardTasarimBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderClass {
        val binding =CardTasarimBinding.inflate(LayoutInflater.from(mContext),parent,false)
        return HolderClass(binding)
    }

    override fun getItemCount(): Int {
        return oyunlarListesi.size
    }

    override fun onBindViewHolder(holder: HolderClass, position: Int) {
    val oyun=oyunlarListesi.get(position)
        val t=holder.binding
        t.imageViewOyun.setImageResource(mContext.resources.getIdentifier(oyun.resimAdi,"drawable",mContext.packageName))
        t.textViewAd.text=oyun.adi
        t.textViewFiyat.text="${oyun.fiyat.toString()} TL"
        t.buttonSepeteEkle.setOnClickListener {
            Snackbar.make(it,"${oyun.adi} Sepete Eklendi",Snackbar.LENGTH_LONG).show()
        }
        t.cardViewOyun.setOnClickListener {
            val gecis=AnaSayfaFragmentDirections.toDetay(oyun = oyun)
            Navigation.findNavController(it).navigate(gecis)
        }
    }
}