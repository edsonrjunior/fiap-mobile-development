package br.com.fiap.mycontactlist.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.fiap.mycontactlist.R
import br.com.fiap.mycontactlist.list.ContactListActivity
import br.com.fiap.mycontactlist.signup.SignupActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.etPasswordLogin

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private val newUserRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            goToHome()
        }

        btLogin.setOnClickListener {
            mAuth.signInWithEmailAndPassword(
                etEmailLogin.text.toString(),
                etPasswordLogin.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    goToHome()
                } else {
                    Toast.makeText(
                        this@LoginActivity, it.exception?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        btSignup.setOnClickListener {
            startActivityForResult(Intent(this, SignupActivity::class.java),
                newUserRequestCode)
        }
    }

    private fun goToHome() {
        val intent = Intent(this, ContactListActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == newUserRequestCode && resultCode == Activity.RESULT_OK) {
            etPasswordLogin.setText(data?.getStringExtra("email"))
        }
    }
}
