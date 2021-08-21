package my.edu.tarc.mad_assignment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import my.edu.tarc.mad_assignment.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_MainpageFragment)
        }
        binding.txt4getPW.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_forgetPWFragment)
        }
        binding.txtReg.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener{
            loginuser()

        }

    }

    private fun loginuser() {
        val email: String = binding.txtEmail1.text.toString()
        val password: String = binding.txtPass1.text.toString()

        if (email == ""){
            Toast.makeText(activity, "Please insert email.", Toast.LENGTH_LONG).show()


        }
        else if(password == "")
        {
            Toast.makeText(activity, "Please insert password.", Toast.LENGTH_LONG).show()
        }
        else{
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        findNavController().navigate(R.id.action_LoginFragment_to_MainpageFragment)
                    }
                    else
                    {
                        Toast.makeText(activity, "Error Message:" + task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                    }

                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}