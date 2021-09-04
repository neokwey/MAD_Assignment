package my.edu.tarc.mad_assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import my.edu.tarc.mad_assignment.databinding.ActivityDashBoardBinding
import my.edu.tarc.mad_assignment.databinding.ActivityPaymentBinding

class DashBoardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashBoardBinding
    //private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        binding =  ActivityDashBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgApplication.setOnClickListener{
//            val intent = Intent(this, Activity::class.java)
//            startActivity(intent)
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
        binding.imgLouout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }

        //change to other activity
        /*val intent = Intent(this, ?Activity::class.java)
        startActivity(intent)*/
    }
}