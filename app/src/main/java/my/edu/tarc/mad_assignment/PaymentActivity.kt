package my.edu.tarc.mad_assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import my.edu.tarc.mad_assignment.databinding.ActivityPaymentBinding


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

        binding.btnMakePayment.setOnClickListener {
            if(binding.radioButton1.isChecked){
                val intent = Intent(this, CardPaymentActivity::class.java)
                startActivity(intent)

            }else{
                val intent = Intent(this, OfflinePaymentActivity::class.java)
                startActivity(intent)
            }
        }
    }
}