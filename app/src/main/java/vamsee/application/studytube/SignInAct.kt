package vamsee.application.studytube

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import vamsee.application.studytube.Daos.UserDao
import vamsee.application.studytube.Models.Firebase.User

class SignInAct:AppCompatActivity() {

    companion object{
        private const val RC_SIGN_IN=120
    }

    private lateinit var mAuth:FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        //User already signed in condition:
//        mAuth = FirebaseAuth.getInstance()
//        val user = mAuth.currentUser
//        if (user != null) {
//            val intent = Intent(this, Dashboard::class.java)
//            startActivity(intent)
//        }
            // Configure Google Sign In
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            googleSignInClient = GoogleSignIn.getClient(this, gso)

            //Firebase Auth instance

            google_button.setOnClickListener {
                signIn()
            }

        mAuth = Firebase.auth
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInTask(task)

        }
    }

    private fun handleSignInTask(task: Task<GoogleSignInAccount>?) {
        val exception = task?.exception
        if (task != null) {
            if (task.isSuccessful) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SignInActivity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("SignInActivity", "Google sign in failed", e)
                }
            }else{
                Log.w("SignInActivity", exception.toString())
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        google_button.visibility = View.GONE
        app_logo.visibility = View.GONE
        SHOW_PROGRESS.visibility = View.VISIBLE
        app_name.visibility = View.GONE

        GlobalScope.launch(Dispatchers.IO) {
            val auth = mAuth.signInWithCredential(credential).await()
            val firebaseUser = auth.user
            kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Main){
                updateUI(firebaseUser)
            }
        }

//        mAuth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d("SignInActivity", "signInWithCredential:success")
//                    val intent=Intent(this,Dashboard::class.java)
//                    startActivity(intent)
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w("SignInActivity", "signInWithCredential:failure", task.exception)
//                }
//            }
    }


    private fun updateUI(firebaseUser: FirebaseUser?) {
        if(firebaseUser != null){
            val user = User(firebaseUser.uid,
                firebaseUser.photoUrl.toString(),
                firebaseUser.displayName)
            val userDao = UserDao()
            userDao.addUser(user)
            val mainActivityIntent = Intent(this, Dashboard::class.java)
            startActivity(mainActivityIntent)
            finish()
        }
        else{
            google_button.visibility = View.VISIBLE
            app_logo.visibility = View.VISIBLE
            SHOW_PROGRESS.visibility = View.GONE
            app_name.visibility = View.VISIBLE
        }
    }
}