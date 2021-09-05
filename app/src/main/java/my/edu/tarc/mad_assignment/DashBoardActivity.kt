package my.edu.tarc.mad_assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import my.edu.tarc.mad_assignment.databinding.ActivityDashBoardBinding
import my.edu.tarc.mad_assignment.databinding.ActivityPaymentBinding

class DashBoardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashBoardBinding
    var refUsers: DatabaseReference?= null
    var firebaseUser : FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        binding =  ActivityDashBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseUser = FirebaseAuth.getInstance().currentUser
        refUsers = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid)

        refUsers!!.addValueEventListener(object : ValueEventListener {


            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){
                    val user: customer? = snapshot.getValue(customer::class.java)

                    binding.txtName.text = user!!.getname()
                    Picasso.get().load(user.getProfile()).into(binding.imgProfile)
                    refUsers!!.child("status").setValue("Online")

                }else{
                    refUsers!!.child("status").setValue("Offline")
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        binding.imgApplication.setOnClickListener{
            val intent = Intent(this, NewApplicationActivity::class.java)
            startActivity(intent)
        }
        binding.imgReward.setOnClickListener{
            val intent = Intent(this, RewardActivity::class.java)
            startActivity(intent)
        }
        binding.imgReferral.setOnClickListener{
            val intent = Intent(this, ReferralActivity::class.java)
            startActivity(intent)
        }
        binding.imgPayment.setOnClickListener{
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        }
        binding.imgmyProfile.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        binding.imgLogout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            onDestroy()
            refUsers!!.child("status").setValue("Offline")
        }
    }
}