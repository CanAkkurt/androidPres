package com.example.test.screens.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import com.example.test.MainActivity
import com.example.test.R
import com.example.test.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.view.*


/**
 * Login activity
 *
 * @constructor Create empty Login activity
 */
class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding
    private var cachedCredentials: Credentials? = null
    private var cachedUserProfile: UserProfile? = null
    private var prefs: SharedPreferences? = null
    private lateinit var account: Auth0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        account = Auth0(
            getString(R.string.com_auth0_client_id),
            getString(R.string.com_auth0_domain)
        )

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
//        logout();
//        binding.root.log_out.setOnClickListener { logout() }
        binding.root.loginButton.setOnClickListener { loginWithBrowser() }
    }


    private fun loginWithBrowser() {
        // Setup the WebAuthProvider, using the custom scheme and scope.
        WebAuthProvider.login(account)
            .withScheme(getString(R.string.com_auth0_scheme))
            .withScope("openid profile email read:current_user update:current_user_metadata")
            .withAudience("https://${getString(R.string.com_auth0_domain)}/api/v2/")

            // Launch the authentication passing the callback where the results will be received
            .start(this, object : Callback<Credentials, AuthenticationException> {
                override fun onFailure(exception: AuthenticationException) {
                    showSnackBar("Failure: ${exception.getCode()}")
                }

                override fun onSuccess(credentials: Credentials) {
                    cachedCredentials = credentials
                    showSnackBar("Success: ${credentials.accessToken}")

                    showUserProfile()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
//                    val intent = Intent(applicationContext, MainActivity::class.java)
//
//                    startActivity(applicationContext, MainActivity::class.java)

                }
            })
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
                    cachedUserProfile = profile;

                }
            })
    }


    private fun logout() {
        WebAuthProvider.logout(account)
            .withScheme(getString(R.string.com_auth0_scheme))
            .start(this, object : Callback<Void?, AuthenticationException> {
                override fun onSuccess(payload: Void?) {
                    // The user has been logged out!
                    cachedCredentials = null
                    cachedUserProfile = null
                    startActivity(Intent(getApplicationContext(), LoginFragment::class.java))

                }

                override fun onFailure(exception: AuthenticationException) {
                    showSnackBar("Failure: ${exception.getCode()}")
                    startActivity(Intent(getApplicationContext(), LoginFragment::class.java))
                    System.console().printf("FIAL TO LOGOUT")
                }
            })
    }

    private fun showSnackBar(text: String) {
        Snackbar.make(
            binding.root,
            text,
            Snackbar.LENGTH_LONG
        ).show()
    }
}

