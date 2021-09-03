package my.edu.tarc.mad_assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import my.edu.tarc.mad_assignment.databinding.ActivityVehicleAddBinding

class Vehicle_add : AppCompatActivity() {
    private lateinit var binding: ActivityVehicleAddBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private var firebaseUserID : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_add)
        supportActionBar?.setTitle("New Vehicle")
        mAuth = FirebaseAuth.getInstance()

        binding.btnBack.setOnClickListener {

        }

        binding.btnFront.setOnClickListener {

        }

        binding.btnLeft.setOnClickListener {

        }

        binding.btnRight.setOnClickListener {

        }

        binding.btnSave.setOnClickListener {
            registerVehicle()
        }

    }

    private fun registerVehicle(){
        val vehiclebrand: String = binding?.etxtBrand?.text.toString()
        val vehiclecc: String = binding?.etxtCC?.text.toString()
        val vehiclemodelName: String = binding?.etxtModel?.text.toString()
        val vehicleplatenum: String = binding?.etxtPlatenum?.text.toString()
        val vehicletype: String = binding?.etxtType?.text.toString()
        val vehicleyear: String = binding?.etxtYear?.text.toString()

        if (vehiclebrand==""){
            Toast.makeText(this, "Please fill in the Vehicle brand.", Toast.LENGTH_LONG).show()
        }else if (vehiclecc==""){
            Toast.makeText(this, "Please fill in the CC.", Toast.LENGTH_LONG).show()
        }else if (vehiclemodelName==""){
            Toast.makeText(this, "Please fill in the Model Name.", Toast.LENGTH_LONG).show()
        }else if (vehicleplatenum==""){
            Toast.makeText(this, "Please fill in the Plate Number.", Toast.LENGTH_LONG).show()
        }else if (vehicletype==""){
            Toast.makeText(this, "Please fill in the Vehicle type.", Toast.LENGTH_LONG).show()
        }else if (vehicleyear==""){
            Toast.makeText(this, "Please fill in the Vehicle year.", Toast.LENGTH_LONG).show()
        }else{
            /*
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
            }*/
        }
    }
}