package vamsee.application.studytube

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import vamsee.application.studytube.databinding.FragmentDashboardFragBinding
import vamsee.application.studytube.databinding.FragmentProfileFragBinding
import android.net.Uri




// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [Profile_frag.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile_frag : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentProfileFragBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAuth:FirebaseAuth
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

        Glide.with(this).load(currentUser?.photoUrl).circleCrop().into(binding.disppic);

        binding.logoutbtn.setOnClickListener {
            mAuth.signOut()

            val intent = Intent(activity, SignInAct::class.java)
            startActivity(intent)

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
}