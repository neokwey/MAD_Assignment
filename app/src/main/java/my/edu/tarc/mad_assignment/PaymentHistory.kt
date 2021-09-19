package my.edu.tarc.mad_assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import my.edu.tarc.mad_assignment.databinding.ActivityPaymentHistoryBinding

class PaymentHistory : AppCompatActivity() {
    private lateinit var binding : ActivityPaymentHistoryBinding
    private lateinit var refUsers: DatabaseReference
    private lateinit var firebaseUser : FirebaseUser
    private lateinit var payHistoryList : ArrayList<PaymentHistoryClass>
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_history)
        binding = ActivityPaymentHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recycleViewPayHistory)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        payHistoryList = arrayListOf<PaymentHistoryClass>()


        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        refUsers = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid).child("paymentHistory")
        refUsers!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    for (payHistorySnapshot in snapshot.children){
                        val payHistory = payHistorySnapshot.getValue(PaymentHistoryClass::class.java)
                        payHistoryList.add(payHistory!!)
                    }
                    recyclerView.adapter = PaymentHistoryAdapter(this@PaymentHistory, payHistoryList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}