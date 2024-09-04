package zapcom.venkat.assignment

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


class MainActivity : AppCompatActivity() {

    private lateinit var _instance: MainActivity
    private lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _instance = this
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        showCustomActionBar()
        var fragmentManager: FragmentManager = supportFragmentManager
        var fragmentTransaction: FragmentTransaction
        fragmentTransaction = fragmentManager.beginTransaction()
        val loginFragment: Fragment = MainFragment()
        fragmentTransaction.replace(R.id.frame, loginFragment, "MAIN_FRAGMENT")
            .addToBackStack("MAIN_FRAGMENT").commitAllowingStateLoss()
        supportFragmentManager.executePendingTransactions()
    }

    private fun showCustomActionBar() {
        toolbar = findViewById(R.id.toolbar)
//        val textViewHeader: TextView = findViewById(R.id.textViewHeader)
        setSupportActionBar(toolbar)

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeButtonEnabled(true)
        }
    }

}

