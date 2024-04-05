package com.example.delivibes

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class Fragment_Productos : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment__productos, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val boton1=view.findViewById<Button>(R.id.botonInformacionNutricional)
        val boton2=view.findViewById<Button>(R.id.botonInformacionNutricional2)
        val boton3=view.findViewById<Button>(R.id.botonInformacionNutricional3)
        val boton4=view.findViewById<Button>(R.id.botonInformacionNutricional4)
        val boton5=view.findViewById<Button>(R.id.botonInformacionNutricional5)
        val boton6=view.findViewById<Button>(R.id.botonInformacionNutricional6)
        val boton7=view.findViewById<Button>(R.id.botonInformacionNutricional7)
        val boton8=view.findViewById<Button>(R.id.botonInformacionNutricional8)
        val boton9=view.findViewById<Button>(R.id.botonInformacionNutricional9)
        val boton10=view.findViewById<Button>(R.id.botonInformacionNutricional10)
        val boton11=view.findViewById<Button>(R.id.botonInformacionNutricional11)
        val boton13=view.findViewById<Button>(R.id.botonInformacionNutricional13)


        boton1.setOnClickListener{}
        boton2.setOnClickListener{}
        boton3.setOnClickListener{}
        boton4.setOnClickListener{}
        boton5.setOnClickListener{}
        boton6.setOnClickListener{}
        boton7.setOnClickListener{}
        boton8.setOnClickListener{}
        boton9.setOnClickListener{}
        boton10.setOnClickListener{}
        boton11.setOnClickListener{}
        boton13.setOnClickListener{}
    }

}
