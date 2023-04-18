package com.nomargin.gptextapp.ui.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import com.nomargin.gptextapp.R
import com.nomargin.gptextapp.databinding.ActivityMainBinding
import com.nomargin.gptextapp.databinding.NavigationHeaderBinding
import com.nomargin.gptextapp.ui.model.FirebaseInstance
import com.nomargin.gptextapp.ui.view.fragments.ChatFragment
import com.nomargin.gptextapp.ui.view.fragments.HelpFragment
import com.nomargin.gptextapp.ui.view.fragments.SettingsFragment
import com.nomargin.gptextapp.util.constants.ApplicationConstants

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navigationDrawerBinding: NavigationHeaderBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        navigationDrawerBinding = NavigationHeaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        toggle.drawerArrowDrawable.color = getColor(R.color.low_white)
        binding.drawerLayout.addDrawerListener(toggle)

        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        replaceFragment(ChatFragment(), getString(R.string.chat))

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_chat -> {
                    replaceFragment(ChatFragment(), getString(R.string.chat))
                }
                R.id.nav_help -> {
                    replaceFragment(HelpFragment(), getString(R.string.help))
                }
                R.id.nav_logout -> {
                    FirebaseInstance.getFirebaseAuthentication().signOut()
                    startActivity(Intent(this, SplashActivity::class.java))
                }
            }
            true
        }

        getUserData()

    }

    override fun onResume() {
        super.onResume()
        navigationDrawerBinding.userName.text = intent.getStringExtra(ApplicationConstants.Companion.UserData.USER_NAME)
        navigationDrawerBinding.userLogo.text = intent.getStringExtra(ApplicationConstants.Companion.UserData.USER_EMAIL)?.uppercase()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment(fragment: Fragment, title: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.frameLayout.id, fragment)
        fragmentTransaction.commit()
        binding.drawerLayout.closeDrawers()
        setTitle(title)
    }

    private fun getUserData(){
        val textDisplayed = when{
            FirebaseInstance.getFirebaseAuthentication().currentUser?.displayName.isNullOrEmpty() -> {
                FirebaseInstance.getFirebaseAuthentication().currentUser?.email.toString()
            }
            else -> {
                FirebaseInstance.getFirebaseAuthentication().currentUser?.displayName.toString()
            }
        }
        binding.navigationView.getHeaderView(0).findViewById<TextView>(navigationDrawerBinding.userName.id).text = textDisplayed
        binding.navigationView.getHeaderView(0).findViewById<TextView>(navigationDrawerBinding.userLogo.id).text = textDisplayed.uppercase()
    }
}