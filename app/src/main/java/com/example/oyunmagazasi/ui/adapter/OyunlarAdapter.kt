package com.example.oyunmagazasi.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.oyunmagazasi.data.Favoriler
import com.example.oyunmagazasi.data.Oyunlar
import com.example.oyunmagazasi.databinding.CardTasarimBinding
import com.example.oyunmagazasi.ui.fragment.AnaSayfaFragmentDirections
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class OyunlarAdapter(var mContext: Context,var oyunlarListesi:List<Oyunlar>)
    :RecyclerView.Adapter<OyunlarAdapter.HolderClass>() {
    val db =Firebase.firestore
    val favorilerListesi = ArrayList<Favoriler>()
    inner class HolderClass(var binding:CardTasarimBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderClass {
        val binding =CardTasarimBinding.inflate(LayoutInflater.from(mContext),parent,false)
        return HolderClass(binding)
    }

    override fun getItemCount(): Int {
        return oyunlarListesi.size
    }


    override fun onBindViewHolder(holder: HolderClass, position: Int) {
        var sayac=0
         val oyun=oyunlarListesi.get(position)
        val t=holder.binding
        t.textViewAd.text=oyun.adi
        t.textViewFiyat.text="${oyun.fiyat.toString()} ₺"
        if(oyun.resimAdi !=null){
            Picasso.get().load(oyun.resimAdi).into(t.imageViewOyun)
        }else{
            Toast.makeText(mContext,"Resim Yükle Hata",Toast.LENGTH_LONG).show()
        }

        val belgeRef = db.collection("Favoriler").document(oyun.adi)
        belgeRef.get().addOnSuccessListener { belge ->
            if (belge != null && belge.exists()) {
                // Belge alındı

                t.imageViewFav.setImageResource(mContext.resources.getIdentifier("fav_resim","drawable",mContext.packageName))


            }else{
                t.imageViewFav.setImageResource(mContext.resources.getIdentifier("unfav_resim","drawable",mContext.packageName))

            }

        }.addOnFailureListener {

        }
        t.buttonSepeteEkle.setOnClickListener {
            val gecis=AnaSayfaFragmentDirections.toDetay(oyun = oyun)
            Navigation.findNavController(it).navigate(gecis)
        }
        t.imageViewFav.setOnClickListener {

        if(sayac %2 !=0){
            t.imageViewFav.setImageResource(mContext.resources.getIdentifier("unfav_resim","drawable",mContext.packageName))

            db.collection("Favoriler").document(oyun.adi)
                .delete()
                .addOnSuccessListener {
                    Log.e("Silindi", "${oyun.adi} Silindi")
                    notifyDataSetChanged()
                    notifyItemRemoved(position)
                }.addOnFailureListener {
                        e -> Log.e("Silme Hata", e.toString())

                }
            Toast.makeText(mContext,"Favorilerden Silindi",Toast.LENGTH_SHORT).show()

            sayac++
        }else{
            t.imageViewFav.setImageResource(mContext.resources.getIdentifier("fav_resim","drawable",mContext.packageName))

            val paylasilanMap= hashMapOf<String,Any>()
            paylasilanMap.put("fiyat",oyun.fiyat)
            paylasilanMap.put("oyun",oyun.adi)
            paylasilanMap.put("gorselUrl",oyun.resimAdi)
            paylasilanMap.put("id",oyun.id)
            paylasilanMap.put("yil",oyun.yil)
            paylasilanMap.put("firma",oyun.firma)


            db.collection("Favoriler").document(oyun.adi).set(paylasilanMap).addOnCompleteListener {task->
                if(task.isSuccessful){
                    Log.e("Ekleme","Başarıyla Eklendi")
                }
            }.addOnFailureListener {exp->
                Log.e("Hata",exp.toString())

            }
            Toast.makeText(mContext,"Favorilere Eklendi",Toast.LENGTH_SHORT).show()
            sayac++
        }

        }




    }

}