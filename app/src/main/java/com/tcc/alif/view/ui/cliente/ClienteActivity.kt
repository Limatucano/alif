package com.tcc.alif.view.ui.cliente

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tcc.alif.R
import com.tcc.alif.databinding.ActivityClienteBinding
import com.tcc.alif.model.domain.MinhasFilasData
import com.tcc.alif.view.adapter.MinhasFilasAdapter
import com.tcc.alif.view.ui.cliente.FirstClienteFragment
import com.tcc.alif.view.ui.cliente.SecondClienteFragment

class ClienteActivity : AppCompatActivity() , MinhasFilasAdapter.OnClickItemListener {
    private val viewBinding : ActivityClienteBinding by viewBinding()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente)


        val firstClienteFragment = FirstClienteFragment()
        val secondClienteFragment = SecondClienteFragment()

        setCurrentFragment(firstClienteFragment)

        viewBinding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->setCurrentFragment(firstClienteFragment)
                R.id.perfil->setCurrentFragment(secondClienteFragment)
            }
            true
        }
    }

    override fun onItemClick(items: MinhasFilasData, position: Int) {
        //TODO: Mandar todas informações da fila para a  activity de detalhamento

        Toast.makeText(this, items.nome_da_fila, Toast.LENGTH_LONG).show()
    }
    private fun setCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.flFragment, fragment)
        commit()
    }
}