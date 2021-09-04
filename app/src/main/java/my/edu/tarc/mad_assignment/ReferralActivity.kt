package my.edu.tarc.mad_assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.edu.tarc.mad_assignment.databinding.ActivityReferralBinding
import my.edu.tarc.mad_assignment.databinding.ActivityRewardBinding

class ReferralActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReferralBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_referral)
        binding =  ActivityReferralBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //change to other activity
        /*val intent = Intent(this, ?Activity::class.java)
        startActivity(intent)*/
    }
}