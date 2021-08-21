package my.edu.tarc.mad_assignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import my.edu.tarc.mad_assignment.databinding.FragmentFirstBinding
import my.edu.tarc.mad_assignment.databinding.FragmentForgetPWBinding

class ForgetPWFragment : Fragment() {
    private var _binding: FragmentForgetPWBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgetPWBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        binding.button4getPW.setOnClickListener {
            findNavController().navigate(R.id.action_forgetPWFragment_to_LoginFragment)
        }

        binding.btnSent.setOnClickListener {
           val email : String =  binding.txtSentEmail.text.toString().trim{it <= ' '}
            if(email.isEmpty()){
                Toast.makeText(activity, "Please enter email address", Toast.LENGTH_LONG).show()

            }
            else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            Toast.makeText(activity, "Email sent successfully to your email", Toast.LENGTH_LONG).show()

                        }
                        else{
                            Toast.makeText(activity, task.exception!!.message.toString(), Toast.LENGTH_LONG).show()


                        }

                    }

            }


        }
    }



    companion object {

    }
}