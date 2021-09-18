package my.edu.tarc.mad_assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import my.edu.tarc.mad_assignment.databinding.ActivityPaymentHistoryBinding

class PaymentHistory : AppCompatActivity() {
    private lateinit var binding : ActivityPaymentHistoryBinding
    private lateinit var refUsers: DatabaseReference
    private lateinit var firebaseUser : FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_history)
        binding = ActivityPaymentHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*firebaseUser = FirebaseAuth.getInstance().currentUser!!
        refUsers = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid).child("voucherUsed")
        refUsers!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    discount = snapshot.child("amountDiscount").getValue().toString().toDouble()
                    discount = snapshot.child("amountDiscount").getValue().toString().toDouble()
                    binding.textViewTotalAmountPayment.setText(totalAmount.toString())
                    totalPay = totalAmount - discount
                    val total = String.format("%.2f", totalPay)
                    binding.textViewToPayPayment.text =  total
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })*/

    }
}