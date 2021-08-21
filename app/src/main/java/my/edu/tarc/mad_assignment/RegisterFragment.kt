package my.edu.tarc.mad_assignment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import my.edu.tarc.mad_assignment.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null

    private val binding get() = _binding!!
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private var firebaseUserID : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {




        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()



    }

    private fun registerUser() {
        val username: String = binding?.txtUserName?.text.toString()
        val email: String = binding.txtEmail.text.toString()
        val password: String = binding.txtPass.text.toString()
        val repass: String = binding.txtConPass.text.toString()
        val phone : String = binding.txtPhone.text.toString()
        val state :String = binding.spinState.getSelectedItem().toString()
        val address : String = binding.txtAddress.text.toString()


        if(username .equals("")){

            Toast.makeText(activity, "Please insert username.", Toast.LENGTH_LONG).show()



        }
        else if (email == ""){
            Toast.makeText(activity, "Please insert email.", Toast.LENGTH_LONG).show()


        }
        else if(password == "")

        {
            Toast.makeText(activity, "Please insert password.", Toast.LENGTH_LONG).show()
        }
        else if(!repass.equals(password)){
            Toast.makeText(activity, "Password not match.", Toast.LENGTH_LONG).show()

        }
        else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                task ->
                if(task.isSuccessful){
                    firebaseUserID = mAuth.currentUser!!.uid
                    refUsers = FirebaseDatabase.getInstance().reference.child("users").child(firebaseUserID)
                    val userHashMap = HashMap<String, Any>()
                    userHashMap["uid"] = firebaseUserID
                    userHashMap["username"] = username
                    userHashMap["password"] = password
                    userHashMap["email"] = email
                    userHashMap["phone"] = phone
                    userHashMap["state"] = state
                    userHashMap["address"] = address
                    userHashMap["profile"] = "https://firebasestorage.googleapis.com/v0/b/mad-assignment-56b04.appspot.com/o/avatar.jpg?alt=media&token=63ce9acb-32a4-4a0c-80e7-cf410e29f2d3"
                    userHashMap["cover"] = "https://firebasestorage.googleapis.com/v0/b/mad-assignment-56b04.appspot.com/o/cover.jpg?alt=media&token=170a50c3-60a3-4e1b-ab5a-7b74ee997c52"
                    userHashMap["status"] = "offline"
                    /*userHashMap["facebook"] = "https://m.facebook.com"
                    userHashMap["instagram"] = "https://m.instagram.com"
                    userHashMap["website"] = "https://www.google.com"*/

                    refUsers.updateChildren(userHashMap)
                        .addOnCompleteListener { task ->
                            if(task.isSuccessful){

                                findNavController().navigate(R.id.action_registerFragment_to_LoginFragment)


                            }

                        }
                }
                else{
                    Toast.makeText(activity, "Error Message:" + task.exception!!.message.toString(), Toast.LENGTH_LONG).show()

                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        binding.buttonReg.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_LoginFragment)
        }

        binding.btnRegister.setOnClickListener{

            registerUser()
        }
    }

    companion object {

    }



}