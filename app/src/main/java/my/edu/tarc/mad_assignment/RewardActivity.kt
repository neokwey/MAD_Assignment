package my.edu.tarc.mad_assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import my.edu.tarc.mad_assignment.databinding.ActivityLoginBinding
import my.edu.tarc.mad_assignment.databinding.ActivityRewardBinding
import java.util.*
import kotlin.collections.ArrayList

class RewardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRewardBinding
    private lateinit var voucherRecyclerView : RecyclerView
    private lateinit var voucherArrayList: ArrayList<Voucher>

    val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference("voucher")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reward)
        binding =  ActivityRewardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val points = intent.getStringExtra("points")
        val userUID = intent.getStringExtra("uid")
        val txtPoints = binding.textViewTotalPoint

        voucherRecyclerView = findViewById(R.id.recyclerVoucher)
        voucherRecyclerView.layoutManager = LinearLayoutManager(this)
        voucherRecyclerView.setHasFixedSize(true)

        voucherArrayList= arrayListOf<Voucher>()

        firebase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(voucherSnapshot in snapshot.children){
                        val vouchers = voucherSnapshot.getValue(Voucher::class.java)
                        voucherArrayList.add(vouchers!!)
                    }
                }
                voucherRecyclerView.adapter = VoucherAdapter(voucherArrayList, userUID.toString(), txtPoints)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        txtPoints.text = points.toString()

        //Log.d("voucher array", voucherArrayList.toString())


//        voucherID = arrayOf(
//            "v1",
//            "v2",
//            "v3"
//        )
//
//        description = arrayOf(
//            "RM 10 rebate on total payment",
//            "RM 20 rebate on total payment",
//            "RM 50 rebate on total payment"
//        )
//
//        voucherPoints = arrayOf(
//            "100",
//            "200",
//            "300"
//        )


        //change to other activity
        /*val intent = Intent(this, ?Activity::class.java)
        startActivity(intent)*/
    }

}