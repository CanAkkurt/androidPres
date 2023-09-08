package com.example.test

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.provider.WebAuthProvider.logout
import com.auth0.android.result.UserProfile
import com.example.test.databinding.ActivityMainBinding
import com.example.test.screens.login.LoginActivity
import com.example.test.screens.login.LoginFragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

//import kotlin.coroutines.jvm.internal.CompletedContinuation.context


/**
 * Main activity
 *
 * @constructor Create empty Main activity
 */
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var prefs: SharedPreferences? = null
    private var cachedCredentials: com.auth0.android.result.Credentials? = null
    private var cachedUserProfile: UserProfile? = null
    private lateinit var account: Auth0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        account = Auth0(
            getString(R.string.com_auth0_client_id),
            getString(R.string.com_auth0_domain)
        )


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navController = this.findNavController(R.id.nav_host_fragment)
        loginWithBrowser()


        //code for overflow menu

        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.overflow_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                if (menuItem.itemId == R.id.aboutFragment) {
                    return NavigationUI.onNavDestinationSelected(menuItem, navController)
                }
                return false
            }

        })


        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.drawer_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.logOut) {
                    logout()
                    return true
                }
                return false
            }

        })

        setSupportActionBar(toolbar)


        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(binding.navView, navController)


        //setup bottom nav bar
//        binding.bottomNavigationView.setupWithNavController(navController)
//        binding.bottomNavigationView.setOnItemSelectedListener {
//            NavigationUI.onNavDestinationSelected(it, navController)
//            true
//        }

    }


    private fun loginWithBrowser() {
        // Setup the WebAuthProvider, using the custom scheme and scope.
        WebAuthProvider.login(account)
            .withScheme(getString(R.string.com_auth0_scheme))
            .withScope("openid profile email read:current_user update:current_user_metadata")
            .withAudience("https://${getString(R.string.com_auth0_domain)}/api/v2/")

            // Launch the authentication passing the callback where the results will be received
            .start(
                this,
                object : Callback<com.auth0.android.result.Credentials, AuthenticationException> {
                    override fun onFailure(exception: AuthenticationException) {
                        startActivity(Intent(applicationContext, LoginActivity::class.java))
                        showSnackBar("Failure: ${exception.getCode()}")
                    }

                    override fun onSuccess(credentials: com.auth0.android.result.Credentials) {
                        cachedCredentials = credentials
                        showSnackBar("Success: ${credentials.user.name}")

                        showUserProfile()
//                    val intent = Intent(applicationContext, MainActivity::class.java)
//
//                    startActivity(applicationContext, MainActivity::class.java)

                    }
                })
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }


    override fun onStop() {
        super.onStop()
        prefs?.edit()?.putBoolean("hasLoggedIn", false)?.apply()
    }

    private fun showUserProfile() {
        val client = AuthenticationAPIClient(account)

        // Use the access token to call userInfo endpoint.
        // In this sample, we can assume cachedCredentials has been initialized by this point.
        client.userInfo(cachedCredentials!!.accessToken!!)
            .start(object : Callback<UserProfile, AuthenticationException> {
                override fun onFailure(exception: AuthenticationException) {
                    showSnackBar("Failure: ${exception.getCode()}")
                }

                override fun onSuccess(profile: UserProfile) {
                    cachedUserProfile = profile

                }
            })
    }

    private fun showSnackBar(text: String) {
        val snackBarView = Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG)
        val view = snackBarView.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params
//        view.background = ContextCompat.getDrawable(context,R.drawable.custom_drawable) // for custom background
        snackBarView.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        snackBarView.show()
    }

    private fun logout() {
        logout(account)
            .withScheme(getString(R.string.com_auth0_scheme))
            .start(this, object : Callback<Void?, AuthenticationException> {
                override fun onSuccess(payload: Void?) {
                    // The user has been logged out!
                    cachedCredentials = null
                    cachedUserProfile = null
                    startActivity(Intent(applicationContext, LoginActivity::class.java))
                }

                override fun onFailure(exception: AuthenticationException) {
                    showSnackBar("Failure: ${exception.getCode()}")
                    startActivity(Intent(applicationContext, LoginFragment::class.java))
                    System.console().printf("FAILED TO LOGOUT")
                }
            })
    }

}
