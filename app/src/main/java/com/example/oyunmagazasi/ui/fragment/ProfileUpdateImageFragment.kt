package com.example.oyunmagazasi.ui.fragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.example.oyunmagazasi.data.Oyunlar
import com.example.oyunmagazasi.data.Profil

import com.example.oyunmagazasi.databinding.FragmentProfileimageUpdateBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class ProfileUpdateImageFragment : Fragment() {
    private lateinit var binding:FragmentProfileimageUpdateBinding
    var secilenGorsel: Uri? =null
    var secilenBitmap:Bitmap?=null
    var storage =Firebase.storage
    private lateinit var auth:FirebaseAuth
    val db=Firebase.firestore
    var list=ArrayList<Profil>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= FragmentProfileimageUpdateBinding.inflate(inflater,container,false)
        auth=Firebase.auth
        binding.imageViewResimSec.setOnClickListener {
            izin()
        }



        binding.buttonGuncelle.setOnClickListener {

               val user = Firebase.auth.currentUser
               user?.let {

                   val email = it!!.email.toString()
                   val reference = storage.reference
                   val gorselReferansi = reference.child("User").child(email.toString())
                   gorselReferansi.putFile(secilenGorsel!!).addOnSuccessListener { task ->
                       val yuklenenGorselReferans = reference.child("User").child(email.toString())
                       yuklenenGorselReferans.downloadUrl.addOnSuccessListener { uri ->
                           var downloadurl = uri.toString()
                           val belgeRef = db.collection("Profil").document(email)
                           belgeRef.get().addOnSuccessListener { belge ->
                               if (belge != null && belge.exists()) {
                                   // Belge alındı
                                   var url = downloadurl
                                   belgeRef.update("gorselUrl", url)
                                       .addOnSuccessListener {
                                           Toast.makeText(requireContext(),"Güncellendi",Toast.LENGTH_LONG).show()
                                       }
                                       .addOnFailureListener { hata ->
                                           Toast.makeText(requireContext(),"HATA",Toast.LENGTH_LONG).show()
                                       }
                               } else {
                                   Toast.makeText(requireContext(),"HATA2",Toast.LENGTH_LONG).show()
                               }
                           }
                       }.addOnFailureListener {
                           Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
                       }
                   }.addOnFailureListener {
                       Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
                   }
                   val belgeRef = db.collection("Profil").document(email)
                   belgeRef.get().addOnSuccessListener { belge ->
                       if (belge != null && belge.exists()) {
                           // Belge alındı
                           var yeniKullaniciAdi = binding.etUsernameUpdate.text.toString()
                           belgeRef.update("username", yeniKullaniciAdi)
                               .addOnSuccessListener {
                                   Toast.makeText(requireContext(),"Güncellendi",Toast.LENGTH_LONG).show()
                               }
                               .addOnFailureListener { hata ->
                                   Toast.makeText(requireContext(),"HATA",Toast.LENGTH_LONG).show()
                               }
                       } else {
                           Toast.makeText(requireContext(),"HATA2",Toast.LENGTH_LONG).show()
                       }
                   }

               }
                val gecis=ProfileUpdateImageFragmentDirections.actionProfileUpdateFragmentToProfilFragment2()
            Navigation.findNavController(it).navigate(gecis)

           }




        return binding.root
    }
    fun izin(){
        if(ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)


        }else{
            val galeriIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galeriIntent,2)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode ==1 ){
            if(grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val galeriIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeriIntent,2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode ==2 && resultCode== RESULT_OK && data != null){
        secilenGorsel=data.data
            if(secilenGorsel!=null){
                  if(Build.VERSION.SDK_INT>=28){
                      val source = ImageDecoder.createSource(requireActivity().contentResolver,secilenGorsel!!)
                        secilenBitmap =ImageDecoder.decodeBitmap(source)
                      binding.imageViewResimSec.setImageBitmap(secilenBitmap)
                  }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}