package com.example.oyunmagazasi.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.oyunmagazasi.R
import com.example.oyunmagazasi.data.Oyunlar
import com.example.oyunmagazasi.databinding.FragmentDetayBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso


class DetayFragment : Fragment() {
    private lateinit var binding :FragmentDetayBinding
    private lateinit var liste:List<Oyunlar>
    val db = Firebase.firestore
    var secilenGorsel: Uri? = null
    val storage = Firebase.storage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDetayBinding.inflate(inflater,container,false)







        val bundle:DetayFragmentArgs by navArgs()
        val gelenOyun = bundle.oyun
        var ad=gelenOyun.adi
        var id =gelenOyun.id
        var yapimci=gelenOyun.firma
        var yil=gelenOyun.yil
        var oyunResim=gelenOyun.resimAdi
        var fiyat=gelenOyun.fiyat

        binding.toolbar.title=gelenOyun.adi
        Picasso.get().load(gelenOyun.resimAdi).into(binding.ivOyunResim)
        binding.tvYapimci.text=gelenOyun.firma
        binding.tvYil.text=gelenOyun.yil.toString()
        binding.tvFiyat.text=gelenOyun.fiyat.toString()
        binding.btnSepeteEkle.setOnClickListener {
            Snackbar.make(it,"${gelenOyun.adi} sepete eklendi",Snackbar.LENGTH_LONG).show()
          //  val gecis=DetayFragmentDirections.toSepet(sepet = gelenOyun)
           // Navigation.findNavController(it).navigate(gecis)

            //Firebase görsel yükleme
            val reference =storage.reference

            val gorselIsmi="${oyunResimAdi(gelenOyun.adi)}.png"
            val yuklenenGorselReferans=reference.child("gorseller").child(gorselIsmi)
            yuklenenGorselReferans.downloadUrl.addOnSuccessListener { uri->
                val dowloadurl=uri.toString()
                //Log.e("URL",dowloadurl)

                //Firebase deneme
                val paylasilanMap= hashMapOf<String,Any>()
                paylasilanMap.put("fiyat",fiyat)
                paylasilanMap.put("oyun",gelenOyun.adi)
                paylasilanMap.put("gorselUrl",dowloadurl)
                paylasilanMap.put("id",gelenOyun.id)
                paylasilanMap.put("yil",gelenOyun.yil)
                paylasilanMap.put("firma",gelenOyun.firma)


                db.collection("Sepetim").add(paylasilanMap).addOnCompleteListener {task->
                    if(task.isSuccessful){
                        //Log.e("Ekleme","Başarıyla Eklendi")
                    }
                }.addOnFailureListener {exp->
                    //Log.e("Hata",exp.toString())

                }

            }.addOnFailureListener {exp->
            //Log.e("URL çekme hatası",exp.toString())
            }

        }



        return binding.root
    }

    fun oyunResimAdi(oyunAdi:String):String{

        val result = oyunAdi.replace("\\s".toRegex(), "")
        return result.lowercase()
    }
}