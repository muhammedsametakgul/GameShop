package com.example.oyunmagazasi.ui.fragmentauth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.oyunmagazasi.MainActivity
import com.example.oyunmagazasi.R
import com.example.oyunmagazasi.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SignInFragment : Fragment() {
    private lateinit var binding:FragmentSignInBinding
    private lateinit var auth:FirebaseAuth
    val db=Firebase.firestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater,container,false)
        auth =Firebase.auth
        binding.buttonKaydol.setOnClickListener {
            val email= binding.txtEmail.text.toString()
            val sifre=binding.txtSifre.text.toString()
            val sifreYeniden=binding.txtSifreYeniden.text.toString()
            val userName=binding.txtUsername.text.toString()
            if(sifre == sifreYeniden){
                auth.createUserWithEmailAndPassword(email,sifre).addOnCompleteListener {task->
                    if(task.isSuccessful){
                        Toast.makeText(requireContext(),"Başarıyla oluşturuldu",Toast.LENGTH_SHORT).show()


                        val paylasilanMap= hashMapOf<String,Any>()
                        paylasilanMap.put("gorselUrl","a")
                        paylasilanMap.put("username",userName)



                        db.collection("Profil").document(email).set(paylasilanMap).addOnCompleteListener {task->
                            if(task.isSuccessful){
                                //Log.e("Ekleme","Başarıyla Eklendi")
                            }
                        }.addOnFailureListener {exp->
                            //Log.e("Hata",exp.toString())

                        }
                        val intent=Intent(requireContext(),MainActivity::class.java)
                        startActivity(intent)
                    }
                }.addOnFailureListener {er->
                Toast.makeText(requireContext(),er.toString(),Toast.LENGTH_LONG).show()

                }
            }




        }

        return binding.root
    }


}