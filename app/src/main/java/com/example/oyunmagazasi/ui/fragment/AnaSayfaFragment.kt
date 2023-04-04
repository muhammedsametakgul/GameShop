package com.example.oyunmagazasi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.oyunmagazasi.R
import com.example.oyunmagazasi.data.Oyunlar
import com.example.oyunmagazasi.databinding.FragmentAnaSayfaBinding
import com.example.oyunmagazasi.ui.adapter.OyunlarAdapter


class AnaSayfaFragment : Fragment() {
    private  lateinit var binding:FragmentAnaSayfaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= FragmentAnaSayfaBinding.inflate(inflater, container, false)
        binding.rv.layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        val oyunlarListesi = ArrayList<Oyunlar>()
        val o1=Oyunlar(1,"CS GO","csgo",2012,123.0,"Valve")
        val o2=Oyunlar(2,"God Of War","godofwar",2018,329.0,"Santa Monica Studio")
        val o3=Oyunlar(3,"GTA4","gta4",2008,109.0,"Rockstar Games")
        val o4=Oyunlar(4,"GTA5","gta5",2013,551.0,"Rockstar Games")
        val o5=Oyunlar(5,"Portal2","portal2",2011,105.0,"Valve")
        val o6=Oyunlar(6,"Stardew Valley","stardewvaley",2016,24.0,"ConcernedApe")
        val o7=Oyunlar(7,"The Witcher3","thewitcher3",2015,249.99,"CD Project Red")
        oyunlarListesi.add(o1)
        oyunlarListesi.add(o2)
        oyunlarListesi.add(o3)
        oyunlarListesi.add(o4)
        oyunlarListesi.add(o5)
        oyunlarListesi.add(o6)
        oyunlarListesi.add(o7)

        val adapter = OyunlarAdapter(requireContext(),oyunlarListesi)
        binding.rv.adapter = adapter




        return  binding.root
    }

}