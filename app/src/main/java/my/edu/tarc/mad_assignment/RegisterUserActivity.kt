package my.edu.tarc.mad_assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import my.edu.tarc.mad_assignment.databinding.*

class RegisterUserActivity : AppCompatActivity() {
    //private lateinit var binding: ActivityMainBinding
    private lateinit var binding: ActivityRegisterUserBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private var firebaseUserID : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_register_user)
        binding =  ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener{
            registerUser()
        }
    }


    private fun registerUser() {
        val name: String = binding.txtName.text.toString()
        val email: String = binding.txtEmail.text.toString()
        val password: String = binding.txtPass.text.toString()
        val repass: String = binding.txtConPass.text.toString()
        val phone : String = binding.txtPhone.text.toString()
        val state :String = binding.spinState.getSelectedItem().toString()
        val address : String = binding.txtAddress.text.toString()

        if(name==""&&password==""&&email==""&&email==""&&phone==""&&address=="")
        {
            Toast.makeText(this@RegisterUserActivity, "Please fill in the detail", Toast.LENGTH_LONG).show()
        }
        else if(name=="")
        {
            Toast.makeText(this@RegisterUserActivity, "Please insert your name.", Toast.LENGTH_LONG).show()
        }
        else if(password=="")
        {
            Toast.makeText(this@RegisterUserActivity, "Please insert password.", Toast.LENGTH_LONG).show()
        }
        else if(!repass.equals(password))
        {
            Toast.makeText(this@RegisterUserActivity, "Password not match.", Toast.LENGTH_LONG).show()
        }
        else if (email=="")
        {
            Toast.makeText(this@RegisterUserActivity, "Please insert email.", Toast.LENGTH_LONG).show()
        }
        else if(phone=="")
        {
            Toast.makeText(this@RegisterUserActivity, "Please insert Phone Number.", Toast.LENGTH_LONG).show()
        }
        else if(binding.spinState.selectedItemPosition==0)
        {
            Toast.makeText(this@RegisterUserActivity, "Please select state.", Toast.LENGTH_LONG).show()
        }
        else if(address=="")
        {
            Toast.makeText(this@RegisterUserActivity, "Please insert your address", Toast.LENGTH_LONG).show()
        }

        else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                    task ->
                if(task.isSuccessful){
                    firebaseUserID = mAuth.currentUser!!.uid
                    refUsers = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUserID)
                    val userHashMap = HashMap<String, Any>()
                    userHashMap["uid"] = firebaseUserID
                    userHashMap["name"] = name
                    userHashMap["password"] = password
                    userHashMap["email"] = email
                    userHashMap["phone"] = phone
                    userHashMap["state"] = state
                    userHashMap["address"] = address
                    userHashMap["profile"] = "https://firebasestorage.googleapis.com/v0/b/mad-assignment-56b04.appspot.com/o/avatar.jpg?alt=media&token=63ce9acb-32a4-4a0c-80e7-cf410e29f2d3"
                    userHashMap["cover"] = "https://firebasestorage.googleapis.com/v0/b/mad-assignment-56b04.appspot.com/o/cover.jpg?alt=media&token=170a50c3-60a3-4e1b-ab5a-7b74ee997c52"
                    userHashMap["status"] = "offline"

                    refUsers.updateChildren(userHashMap)
                        .addOnCompleteListener {
                                task2 ->
                            if(task2.isSuccessful){
                                val intent = Intent(this, LoginActivity::class.java)
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                startActivity(intent)
                            }

                        }
                }
                else{
                    Toast.makeText(this@RegisterUserActivity, "Error Message:" + task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}