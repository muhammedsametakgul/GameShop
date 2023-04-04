package com.example.oyunmagazasi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.oyunmagazasi.R
import com.example.oyunmagazasi.databinding.FragmentDetayBinding
import com.google.android.material.snackbar.Snackbar


class DetayFragment : Fragment() {
    private lateinit var binding :FragmentDetayBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDetayBinding.inflate(inflater,container,false)

        val bundle:DetayFragmentArgs by navArgs()
        val gelenOyun = bundle.oyun

        binding.toolbar.title=gelenOyun.adi
        binding.ivOyunResim.setImageResource(resources.getIdentifier(gelenOyun.resimAdi,"drawable",requireContext().packageName))
        binding.tvYapimci.text=gelenOyun.firma
        binding.tvYil.text=gelenOyun.yil.toString()
        binding.tvFiyat.text="${gelenOyun.fiyat.toString()} TL"
        binding.btnSepeteEkle.setOnClickListener {
            Snackbar.make(it,"${gelenOyun.adi} sepete eklendi",Snackbar.LENGTH_LONG).show()
        }

        return binding.root
    }
}