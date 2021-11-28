package vamsee.application.studytube

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import vamsee.application.studytube.databinding.FragmentProfileFragBinding

class Profile_frag : Fragment(), GoogleApiClient.OnConnectionFailedListener {
    private var _binding: FragmentProfileFragBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAuth:FirebaseAuth
    private var mGoogleApiClient: GoogleApiClient? = null
    //private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileFragBinding.inflate(inflater, container, false)
        val view = binding.root
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mAuth= FirebaseAuth.getInstance()
        val currentUser=mAuth.currentUser
        binding.profilename.text = currentUser?.displayName
        binding.emailtext.text= currentUser?.email

        mGoogleApiClient = GoogleApiClient.Builder(requireContext())
            .addApi(Auth.GOOGLE_SIGN_IN_API)
            .build()

        Glide.with(this).load(currentUser?.photoUrl).circleCrop().into(binding.disppic);

        binding.logoutbtn.setOnClickListener {
            //mAuth.signOut()
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback {
                FirebaseAuth.getInstance().signOut()
                val i1 = Intent(activity, SignInAct::class.java)
                startActivity(i1)
                Toast.makeText(activity, "Logout Successfully!", Toast.LENGTH_SHORT).show()
            }
            val intent = Intent(activity, SignInAct::class.java)
            startActivity(intent)

//            val intent = Intent(activity, SignInAct::class.java)
//            startActivity(intent)

        }
        binding.Settingstxt.setOnClickListener {
            val uri = Uri.parse("https://myaccount.google.com/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        binding.Helptxt.setOnClickListener {
            val uri = Uri.parse("https://support.google.com/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Toast.makeText(activity, "Not Able to log you out !!!", Toast.LENGTH_SHORT).show()
    }
}