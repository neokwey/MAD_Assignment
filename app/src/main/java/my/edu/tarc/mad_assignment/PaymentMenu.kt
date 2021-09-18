package my.edu.tarc.mad_assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.edu.tarc.mad_assignment.databinding.ActivityPaymentMenuBinding

class PaymentMenu : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_menu)
        binding = ActivityPaymentMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgMakePayment.setOnClickListener{
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        }
        binding.imgPaymentHistory.setOnClickListener {
            val intent = Intent(this, PaymentHistory::class.java)
            startActivity(intent)
        }
    }
}