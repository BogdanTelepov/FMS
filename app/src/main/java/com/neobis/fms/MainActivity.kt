package com.neobis.fms

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.neobis.fms.network.SessionManager
import com.neobis.fms.ui.activities.AuthActivity
import com.neobis.fms.ui.fragments.addTransaction.AddTransactionFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.navHostFragment)
        setupActionBarWithNavController(navController)

        bottomNavView.setupWithNavController(navHostFragment.findNavController())
        bottomNavView.menu.getItem(2).isEnabled = false
        val radius = resources.getDimension(R.dimen.radius_corner)
        val bottomNavigationViewBackground = bottomNavView.background as MaterialShapeDrawable
        bottomNavigationViewBackground.shapeAppearanceModel =
            bottomNavigationViewBackground.shapeAppearanceModel.toBuilder()
                .setTopRightCorner(CornerFamily.ROUNDED, radius)
                .setTopLeftCorner(CornerFamily.ROUNDED, radius)
                .build()

        sessionManager = SessionManager(this)

        if (sessionManager.fetchUserToken() == null) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }

        fab.setOnClickListener {

            val transactionFragment = AddTransactionFragment()
            transactionFragment.show(supportFragmentManager, "dialog")

        }
    }


    fun authActivityTransition() {

        startActivity(Intent(this, AuthActivity::class.java))
        finish()

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}