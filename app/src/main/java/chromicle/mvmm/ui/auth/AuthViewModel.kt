package chromicle.mvmm.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import chromicle.mvmm.data.repositories.UserRepository
import chromicle.mvmm.utils.ApiException
import chromicle.mvmm.utils.Coroutines

/**
 *Created by Chromicle on 12/7/19.
 */

class AuthViewModel : ViewModel() {

    var email: String? = null
    var password: String? = null

    var authListner: AuthListner? = null

    fun OnLoginButtonClick(view: View) {
        authListner?.onStrarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListner?.onFailure("Invalid email or password")
        }

        Coroutines.main {
            try {
                val authResponse = UserRepository().userLogin(email!!, password!!)
                authResponse.user.let {
                    authListner?.onSuccess(it)
                    return@main
                }
                authListner?.onFailure(authResponse.message!!)
            } catch (e: ApiException) {
                authListner?.onFailure(e.message!!)
            }
        }

    }

}