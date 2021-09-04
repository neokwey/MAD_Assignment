package my.edu.tarc.mad_assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.edu.tarc.mad_assignment.databinding.ActivityPaymentBinding
import my.edu.tarc.mad_assignment.databinding.ActivityRewardBinding

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        binding =  ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //change to other activity
        /*val intent = Intent(this, ?Activity::class.java)
        startActivity(intent)*/
    }
}