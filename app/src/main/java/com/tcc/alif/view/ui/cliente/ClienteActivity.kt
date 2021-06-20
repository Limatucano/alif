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
import com.tcc.alif.view.ui.cliente.HomeClienteFragment
import com.tcc.alif.view.ui.cliente.PerfilClienteFragment

class ClienteActivity : AppCompatActivity() , MinhasFilasAdapter.OnClickItemListener {
    private val viewBinding : ActivityClienteBinding by viewBinding()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente)


        val HomeClienteFragment = HomeClienteFragment()
        val PerfilClienteFragment = PerfilClienteFragment()
        val PesquisarClienteFragment = PesquisarClienteFragment()
        val FavoritosClienteFragment = FavoritosClienteFragment()
        val ConfigClienteFragment = ConfigClienteFragment()
        setCurrentFragment(HomeClienteFragment)
        viewBinding.bottomNavigationView.menu.getItem(2).isChecked = true
        viewBinding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->setCurrentFragment(HomeClienteFragment)
                R.id.perfil->setCurrentFragment(PerfilClienteFragment)
                R.id.procurar->setCurrentFragment(PesquisarClienteFragment)
                R.id.favoritos->setCurrentFragment(FavoritosClienteFragment)
                R.id.config->setCurrentFragment(ConfigClienteFragment)
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