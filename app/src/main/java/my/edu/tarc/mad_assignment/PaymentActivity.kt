package my.edu.tarc.mad_assignment

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import my.edu.tarc.mad_assignment.databinding.ActivityPaymentBinding
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.function.DoubleConsumer
import kotlin.random.Random


class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding
    private lateinit var refUsers: DatabaseReference
    private lateinit var refUsers2: DatabaseReference
    private lateinit var refUsers3: DatabaseReference
    private lateinit var firebaseUser : FirebaseUser
    private lateinit var paymentList: ArrayList<Payment>
    private lateinit var voucherList: ArrayList<PaymentVoucher>
    private lateinit var quantityList : ArrayList<String>
    private lateinit var voucherRecycleView : RecyclerView
    private var totalPay : Double = 0.0
    private var totalAmount : Double = 0.0
    private var discount : Double =0.0

    /*private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private var firebaseUserID : String = ""*/


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        binding =  ActivityPaymentBinding.inflate(layoutInflater)
        /*firebaseUserID = mAuth.currentUser!!.uid*/
        setContentView(binding.root)
        retrieveAmount()
        getVoucherData()
        voucherRecycleView = findViewById(R.id.recycleViewVoucher)
        voucherRecycleView.layoutManager = LinearLayoutManager(this)
        voucherRecycleView.setHasFixedSize(true)

        voucherList= arrayListOf<PaymentVoucher>()

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
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
        })


        /*binding.textViewTotalAmountPayment.setText(totalAmount.toString())
        binding.textDiscountPayment.setText(discount.toString())
        totalPay = totalAmount - discount
        val total = String.format("%.2f", totalPay)
        binding.textViewToPayPayment.text =  total*/
        //change to other activity
        /*val intent = Intent(this, ?Activity::class.java)
        startActivity(intent)*/

        paymentList= arrayListOf<Payment>()



        val transactionID = Random.nextInt(1000000000)
        binding.textViewTransactionIDPayment.text = "$transactionID"
        val id = binding.textViewTransactionIDPayment.text.toString()
        val payDate = LocalDate.now()
        val payTime = LocalTime.now().toString()
        val formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formatterTime = DateTimeFormatter.ISO_LOCAL_TIME
        binding.textViewDatePayment.text = payDate.format(formatterDate)
        binding.textViewTimePayment.text = payTime.format(formatterTime)



        binding.btnMakePayment.setOnClickListener {
            val total = String.format("%.2f", totalPay)
            if(binding.textViewToPayPayment.text == "0.00")
            {
                binding.textView29.text = "No overdue payment."
            }
            else{
                if(binding.radioButton1.isChecked){

                    val intent = Intent(this@PaymentActivity, CardPay::class.java)
                    intent.putExtra("transID",id)
                    intent.putExtra("toPay",total)
                    startActivity(intent)
                }else if(binding.radioButton2.isChecked){
                    updateVoucherQty()
                    val intent = Intent(this@PaymentActivity, OfflinePay::class.java)
                    intent.putExtra("transID",id)
                    intent.putExtra("toPay",total)
                    startActivity(intent)
                }else
                {
                    Toast.makeText(this@PaymentActivity, "Please select a payment method.", Toast.LENGTH_SHORT).show()
                }
            }

        }

        binding.btnSelectVoucher.setOnClickListener {

            binding.linearVoucher.visibility = View.VISIBLE
        }

    }

    private fun getVoucherData() {

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        refUsers = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid).child("rewards")
        refUsers2 = FirebaseDatabase.getInstance().reference.child("voucher")
        refUsers!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild("voucher")){

                    for(voucherSnapshot in snapshot.child("voucher").children){
                        val key = voucherSnapshot.key.toString()
                        val quantity = voucherSnapshot.child("quantity").value.toString()

                        //quantityList.add(quantity)//problem caused here

                        refUsers2!!.child(key).addValueEventListener(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if(snapshot.exists()){
                                    val discountAmount = snapshot.child("discountAmount").getValue().toString()
                                    voucherList.add(PaymentVoucher(quantity,discountAmount,key))
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }

                        })
                    }
                    //Toast.makeText(this@PaymentActivity,quantityList.toString(),Toast.LENGTH_SHORT).show()
                    voucherRecycleView.adapter = PaymentVoucherAdapter(voucherList,binding.linearVoucher, binding.textDiscountPayment)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }

    private fun updateVoucherQty(){

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        refUsers = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid)
        var voucherRef = refUsers.child("rewards").child("voucher")

        refUsers.child("voucherUsed").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val key = snapshot.child("voucherId").getValue().toString()

                voucherRef.child(key).child("quantity").get().addOnSuccessListener {
                    var qty = it.value.toString().toInt()

                    qty -= 1

                    if(qty<=0){
                        voucherRef.child(key).removeValue()
                    }
                    else{
                        voucherRef.child(key).child("quantity").setValue(qty.toString())
                    }
                    refUsers.child("voucherUsed").child("amountDiscount").setValue("0")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun retrieveAmount(){

        var totalPayment :Double = 0.0
        var partAmount : Double = 0.0
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        refUsers = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid).child("payment")
        refUsers!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (paymentSnapshot in snapshot.children) {
                        val payment = paymentSnapshot.getValue(Payment::class.java)

                        paymentList.add(payment!!)
                    }
                    for(item in paymentList){

                        partAmount = item.getamount()!!.toDouble()
                        totalPayment += partAmount
                    }
                    totalAmount= totalPayment.toString().toDouble()
                    //getTotalAmount(paymentList)
                    //binding.textViewToPayPayment.text = paymentList[0].getamount().toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


    }






}