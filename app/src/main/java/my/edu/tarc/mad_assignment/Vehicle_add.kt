package my.edu.tarc.mad_assignment

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import my.edu.tarc.mad_assignment.databinding.ActivityNewApplicationBinding
import my.edu.tarc.mad_assignment.databinding.ActivityVehicleAddBinding
import java.util.jar.Manifest

class Vehicle_add : AppCompatActivity() {
    private lateinit var binding: ActivityVehicleAddBinding
    var refUsers: DatabaseReference?= null
    var database: DatabaseReference?= null
    var firebaseUser : FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_add)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
        binding =  ActivityVehicleAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseUser = FirebaseAuth.getInstance().currentUser
        refUsers = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid).child("vehicle")



        /*mAuth = FirebaseAuth.getInstance()

        binding.btnBack.setOnClickListener {

        }

        binding.btnFront.setOnClickListener {

        }

        binding.btnLeft.setOnClickListener {

        }

        binding.btnRight.setOnClickListener {

        }*/

        binding.btnSave.setOnClickListener {
            registerVehicle()
        }

        binding.imgFront.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this@Vehicle_add, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                selectImage()
            }else {
                //ActivityCompat.requestPermissions(this@Vehicle_add, String{android.Manifest.permission.READ_EXTERNAL_STORAGE})
            }
        }

    }

    private fun selectImage() {

    }

    private fun registerVehicle(){
        refUsers!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    var count: Int = snapshot.childrenCount.toInt()
                    var newID: Int = count + 1
                    val carID: String = "car$newID"
                    val vehiclebrand: String = binding.etxtBrand.text.toString()
                    val vehiclecc: String = binding.etxtCC.text.toString()
                    val vehiclemodelName: String = binding.etxtModel.text.toString()
                    val vehicleplatenum: String = binding.etxtPlatenum.text.toString()
                    val vehicletype: String = binding.etxtType.text.toString()
                    val vehicleyear: String = binding.etxtYear.text.toString()
                    if (vehiclebrand==""){
                        Toast.makeText(this@Vehicle_add, "Please fill in the Vehicle brand.", Toast.LENGTH_SHORT).show()
                    }else if (vehiclecc==""){
                        Toast.makeText(this@Vehicle_add, "Please fill in the CC.", Toast.LENGTH_LONG).show()
                    }else if (vehiclemodelName==""){
                        Toast.makeText(this@Vehicle_add, "Please fill in the Model Name.", Toast.LENGTH_LONG).show()
                    }else if (vehicleplatenum==""){
                        Toast.makeText(this@Vehicle_add, "Please fill in the Plate Number.", Toast.LENGTH_LONG).show()
                    }else if (vehicletype==""){
                        Toast.makeText(this@Vehicle_add, "Please fill in the Vehicle type.", Toast.LENGTH_LONG).show()
                    }else if (vehicleyear==""){
                        Toast.makeText(this@Vehicle_add, "Please fill in the Vehicle year.", Toast.LENGTH_LONG).show()
                    }else{
                        val carVehicle = Vehicle("https://firebasestorage.googleapis.com/v0/b/mad-assignment-56b04.appspot.com/o/car_back.jpg?alt=media&token=7c6d6aed-5498-4fce-8870-71726fc91ffb",vehiclebrand,vehiclecc,vehiclemodelName,vehicleplatenum,vehicletype,vehicleyear,"https://firebasestorage.googleapis.com/v0/b/mad-assignment-56b04.appspot.com/o/car_back.jpg?alt=media&token=7c6d6aed-5498-4fce-8870-71726fc91ffb","https://firebasestorage.googleapis.com/v0/b/mad-assignment-56b04.appspot.com/o/car_back.jpg?alt=media&token=7c6d6aed-5498-4fce-8870-71726fc91ffb","https://firebasestorage.googleapis.com/v0/b/mad-assignment-56b04.appspot.com/o/car_back.jpg?alt=media&token=7c6d6aed-5498-4fce-8870-71726fc91ffb")
                        refUsers!!.child(carID).setValue(carVehicle).addOnSuccessListener {
                            binding.etxtBrand.text.clear()
                            binding.etxtCC.text.clear()
                            binding.etxtModel.text.clear()
                            binding.etxtPlatenum.text.clear()
                            binding.etxtType.text.clear()
                            binding.etxtYear.text.clear()
                            Toast.makeText(this@Vehicle_add, " Vehicle added successfully.", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {
                            Toast.makeText(this@Vehicle_add, " Failed to add vehicle.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
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