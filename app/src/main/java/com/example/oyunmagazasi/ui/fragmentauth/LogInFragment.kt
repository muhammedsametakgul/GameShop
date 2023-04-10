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
import com.example.oyunmagazasi.databinding.FragmentLogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInFragment : Fragment() {
    private lateinit var  binding:FragmentLogInBinding
    private lateinit var auth:FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth= Firebase.auth
       binding =FragmentLogInBinding.inflate(inflater,container,false)



        binding.btnGirisYap.setOnClickListener {
            val email=binding.txtmail.text.toString()
            val sifre=binding.txtSifre.text.toString()
            auth.signInWithEmailAndPassword(email,sifre).addOnCompleteListener {task->
            if(task.isSuccessful){
                val guncel=auth.currentUser?.email.toString()
                Toast.makeText(requireContext(),"Hoşgeldin : ${guncel}",Toast.LENGTH_SHORT).show()
                val intent= Intent(requireContext(),MainActivity::class.java)
                startActivity(intent)
            }

            }
        }
        val guncelKullanici =auth.currentUser
        if(guncelKullanici !=null){
            val intent =Intent(requireContext(),MainActivity::class.java)
            startActivity(intent)
        }
        binding.textViewOlustur.setOnClickListener {
            val gecis= LogInFragmentDirections.toSign()
            Navigation.findNavController(it).navigate(gecis)
        }
        return binding.root
    }

}