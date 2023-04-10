package com.example.oyunmagazasi.ui.fragment

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oyunmagazasi.AuthActivity
import com.example.oyunmagazasi.data.Favoriler
import com.example.oyunmagazasi.data.Sahip
import com.example.oyunmagazasi.databinding.FragmentProfilBinding
import com.example.oyunmagazasi.ui.adapter.FavorilerAdapter
import com.example.oyunmagazasi.data.Profil
import com.example.oyunmagazasi.ui.adapter.SahipAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


class ProfilFragment : Fragment() {
    private lateinit var adapterSahip:SahipAdapter
    private lateinit var adapterFav:FavorilerAdapter
    private lateinit var binding:FragmentProfilBinding
    var list=ArrayList<Sahip>()
    var list2=ArrayList<Favoriler>()
    val db=Firebase.firestore
    private lateinit var auth:FirebaseAuth
    var listeKullanici = ArrayList<Profil>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilBinding.inflate(inflater,container,false)
        auth=Firebase.auth
        binding.rvprofile.layoutManager= LinearLayoutManager(requireContext())
        adapterSahip = SahipAdapter(requireContext(),list)
        binding.rvprofile.adapter = adapterSahip

        veriGetirSahip()
        binding.chipSahip.setOnClickListener {
            veriGetirSahip()
            adapterSahip = SahipAdapter(requireContext(),list)
            binding.rvprofile.adapter = adapterSahip
            binding.textViewBaslik.text="Kütüphanenizdeki Oyunlar"

        }
        binding.chipFav.setOnClickListener {
            veriGetirFav()
            adapterFav = FavorilerAdapter(requireContext(),list2)
            binding.rvprofile.adapter = adapterFav
            binding.textViewBaslik.text="Favori Oyunlar"
        }

        binding.cikis.setOnClickListener {
           auth.signOut()
            val intent=Intent(requireContext(),AuthActivity::class.java)
            startActivity(intent)
        }


            val user = Firebase.auth.currentUser
            user?.let {
                // Name, email address, and profile photo Url

                val email = it.email.toString()
                val db = FirebaseFirestore.getInstance()
                val belgeRef = db.collection("Profil").document(email)

                belgeRef.get().addOnSuccessListener { belgeSnapshot ->
                    if (belgeSnapshot.exists()) {
                        // Belge alındı
                        var gorselUrl = belgeSnapshot.getString("gorselUrl").toString()
                        if(gorselUrl !=null){
                             Picasso.get().load(gorselUrl).into(binding.imageViewKullanici)
                        }
                    } else {
                        // Belge bulunamadı
                    }

                }.addOnFailureListener { exception ->
                    // Hata oldu
                }


                val docRef = db.collection("Profil").document(email.toString())
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {

                            val username = document.getString("username")
                            binding.textViewUsername.text=username.toString()
                        } else {
                            Log.d(TAG, "No such document")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "get failed with ", exception)
                    }
            }



        binding.editProfile.setOnClickListener {
            val gecis =ProfilFragmentDirections.toUpdate()
            Navigation.findNavController(it).navigate(gecis)
        }


        return binding.root
    }

    fun veriGetirSahip(){
        db.collection("Sahip").addSnapshotListener { snapshot, error ->
            if(error !=null){
                Log.e("Veri Çekme Hata",error.toString())
            }else{
                if(snapshot != null){
                    if(!snapshot.isEmpty){
                        list.clear()

                        val documents = snapshot.documents
                        for (document in documents){
                            //null kontrolü
                          var fiyat=0.0
                           if(document.get("fiyat") == null){
                                fiyat =0.0
                           }else{
                               fiyat= document.get("fiyat").toString().toDouble()
                           }
                            var oyunAdi=document.get("oyunAdi").toString()
                            var gorselUrl=document.get("gorselurl").toString()
                            var id=document.get("id").toString().toInt()!!
                            // Log.e("sepet görsel url",gorselUrl)
                            var indirilenOyun =Sahip(id,oyunAdi,fiyat,gorselUrl)
                            list.add(indirilenOyun)

                            adapterSahip.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }
    fun veriGetirFav(){
        db.collection("Favoriler").addSnapshotListener { snapshot, error ->
        if(error !=null){
            Log.e("Veri Çekme Hata",error.toString())
        }else{
            if(snapshot != null){
                if(!snapshot.isEmpty){
                    list2.clear()

                    val documents = snapshot.documents
                    for (document in documents){
                        //null kontrolü
                        var fiyat=0.0
                        if(document.get("fiyat") == null){
                            fiyat =0.0
                        }else{
                            fiyat= document.get("fiyat").toString().toDouble()
                        }
                        var oyunAdi=document.get("oyun").toString()
                        var gorselUrl=document.get("gorselUrl").toString()
                        var id=document.get("id").toString().toInt()!!
                        // Log.e("sepet görsel url",gorselUrl)
                        var indirilenOyun =Favoriler(id,oyunAdi,fiyat,gorselUrl)
                        list2.add(indirilenOyun)

                        adapterFav.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    }
}