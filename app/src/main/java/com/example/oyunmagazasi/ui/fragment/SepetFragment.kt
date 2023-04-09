package com.example.oyunmagazasi.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oyunmagazasi.R
import com.example.oyunmagazasi.data.Sepet
import com.example.oyunmagazasi.databinding.FragmentSepetBinding
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
        binding.rv2.layoutManager= LinearLayoutManager( requireContext())
        adapter = SepetAdapter(requireContext(),list)
        binding.rv2.adapter = adapter

        var toplam =0.0
        db.collection("Sepetim").addSnapshotListener { snapshot, error ->
            if(error !=null){
                Log.e("Veri Çekme Hata",error.toString())
            }else{
                    if(snapshot != null){
                        if(!snapshot.isEmpty){
                            list.clear()
                            var toplam =0.0
                            val documents = snapshot.documents
                            for (document in documents){

                                var fiyat= document.get("fiyat").toString().toDouble()!!
                                var oyunAdi=document.get("oyun").toString()
                                var gorselUrl=document.get("gorselUrl").toString()
                                var id=document.get("id").toString().toInt()!!
                               // Log.e("sepet görsel url",gorselUrl)
                                var indirilenOyun =Sepet(id,oyunAdi,fiyat,gorselUrl)
                                list.add(indirilenOyun)
                                toplam += document.get("fiyat").toString().toDouble()
                               binding.toplamFiyat.text="Toplam : ${toplam.toString()}"
                                //Log.e("Fiyat Toplam" ,toplam.toString())

                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
            }
        }

        binding.buttonSatinAl.setOnClickListener {
        try {
            getVeri()

            val collectionRef = db.collection("Sepetim")

            collectionRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        document.reference.delete()
                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.exception)
                }

            }
            if(adapter.itemCount !=0){
                val gecis=SepetFragmentDirections.toAnaSayfa()
                Navigation.findNavController(it).navigate(gecis)
            }
        }catch (exp:java.lang.Exception){
            Toast.makeText(requireContext(),exp.toString(),Toast.LENGTH_LONG).show()
        }
        }


        return binding.root
    }
   fun getVeri(){
        db.collection("Sepetim").addSnapshotListener { snapshot, error ->
            if(error !=null){
                Log.e("Veri Çekme Hata",error.toString())
            }else{
                if(snapshot != null){
                    if(!snapshot.isEmpty){
                        list.clear()
                        var toplam =0.0
                        val documents = snapshot.documents
                        for (document in documents){

                            var fiyat= document.get("fiyat").toString().toDouble()!!
                            var oyunAdi=document.get("oyun") as String
                            var gorselUrl=document.get("gorselUrl").toString()
                            var id=document.get("id").toString().toInt()!!
                            // Log.e("sepet görsel url",gorselUrl)
                            var indirilenOyun =Sepet(id,oyunAdi,fiyat,gorselUrl)
                            val satinAlMap= hashMapOf<String,Any>()
                            satinAlMap.put("id",id)
                            satinAlMap.put("oyunAdi",oyunAdi)
                            satinAlMap.put("fiyat",fiyat)
                            satinAlMap.put("gorselurl",gorselUrl)

                            db.collection("Sahip").document(oyunAdi).set(satinAlMap).addOnCompleteListener {task->
                                if(task.isSuccessful){
                                    Toast.makeText(requireContext(),"Başarılı",Toast.LENGTH_SHORT).show()
                                }
                            }.addOnFailureListener {exp->
                                Toast.makeText(requireContext(),"HATA",Toast.LENGTH_SHORT).show()

                            }

                        }


                    }
                }
            }
        }

    }


}