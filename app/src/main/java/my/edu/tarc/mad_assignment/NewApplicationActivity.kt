package my.edu.tarc.mad_assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import my.edu.tarc.mad_assignment.databinding.ActivityDashBoardBinding
import my.edu.tarc.mad_assignment.databinding.ActivityNewApplicationBinding

class NewApplicationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewApplicationBinding
    //var refUsers: DatabaseReference?= null
    //var firebaseUser : FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_application)
        binding =  ActivityNewApplicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //firebaseUser = FirebaseAuth.getInstance().currentUser
        //refUsers = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid).child("vehicle")

        /*refUsers!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var count: Int = snapshot.childrenCount.toInt()
                var carID: String = ""
                for (i in 0 until count){
                    if (i < 100){
                        carID = "car[$i+1]"
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })*/

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, Vehicle_add::class.java)
            startActivity(intent)
        }
    }
}