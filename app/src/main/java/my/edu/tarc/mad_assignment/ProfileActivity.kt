package my.edu.tarc.mad_assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import my.edu.tarc.mad_assignment.databinding.ActivityPaymentBinding
import my.edu.tarc.mad_assignment.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    var refUsers: DatabaseReference?= null
    var firebaseUser : FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        binding =  ActivityProfileBinding.inflate(layoutInflater)
        firebaseUser = FirebaseAuth.getInstance().currentUser
        refUsers = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid)
        setContentView(binding.root)


        refUsers!!.addValueEventListener(object : ValueEventListener {



            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    val user: customer? = snapshot.getValue(customer::class.java)
                    binding.txtID.setText(user!!.getUID())
                    binding.txtName1.setText(user!!.getname())
                    binding.txtEmail1.setText(user!!.getEmail())
                    binding.txtPhone1.setText(user!!.getPhone())
                    binding.txtState1.setText(user!!.getState())
                    binding.txtName1.setText(user!!.getname())
                    binding.txtAddress1.setText(user!!.getAddress())
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        binding.btnSave.setOnClickListener{

            refUsers!!.child("name").setValue(binding.txtName1.text.toString())
            refUsers!!.child("email").setValue(binding.txtEmail1.text.toString())
            refUsers!!.child("phone").setValue(binding.txtPhone1.text.toString())
            refUsers!!.child("state").setValue(binding.txtState1.text.toString())
            refUsers!!.child("address").setValue(binding.txtAddress1.text.toString())
            Toast.makeText(this, "Successfully Updated", Toast.LENGTH_LONG).show()

        }

        //change to other activity
        /*val intent = Intent(this, ?Activity::class.java)
        startActivity(intent)*/
    }
}