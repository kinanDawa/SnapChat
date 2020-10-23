package kenan.com.snapchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    var emailEditText: EditText? = null
    var passwordEditText: EditText? = null
    val auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        emailEditText = findViewById(R.id.textEmail)
        passwordEditText = findViewById(R.id.textPassword)

        if (auth != null) {
            logIn()
        }
    }

    private fun logIn() {
        val intent = Intent(this, SnapActivity::class.java)
        startActivity(intent)
    }

    fun goClicked(view: View) {
        // Check if we can log in the user
        auth.signInWithEmailAndPassword(
            emailEditText?.text.toString(),
            passwordEditText?.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    logIn()
                } else {
                    // Sign up the user
                    auth.createUserWithEmailAndPassword(
                        emailEditText?.text.toString(),
                        passwordEditText?.text.toString()
                    ).addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            FirebaseDatabase.getInstance().getReference().child("users").child(task.result!!.user!!.uid)
                                .child("email").setValue(emailEditText?.text.toString())
                            logIn()
                        } else {
                            Toast.makeText(this, "Login Failed. Try Again.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
    }
}