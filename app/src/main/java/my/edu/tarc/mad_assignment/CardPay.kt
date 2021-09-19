package my.edu.tarc.mad_assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import my.edu.tarc.mad_assignment.databinding.ActivityCardPayBinding

class CardPay : AppCompatActivity() {
    private lateinit var binding: ActivityCardPayBinding
    private lateinit var refUsers: DatabaseReference
    private lateinit var refUsers1: DatabaseReference
    private lateinit var firebaseUser : FirebaseUser
    var transID : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_pay)
        binding = ActivityCardPayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cardFormat()
        expiryFormat()

        transID = intent.getStringExtra("transID").toString()
        binding.textViewTransactionIDCard.setText(transID)

        val toPay = intent.getStringExtra("toPay")
        binding.textViewTotal3.text = "RM ${toPay}"

        binding.btnPay.setOnClickListener {
            validation()

        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        refUsers = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid).child("paymentHistory").child(transID!!)
        refUsers.removeValue()
        refUsers1 = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid)
        refUsers1.child("voucherUsed").child("amountDiscount").setValue("0")

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

    private fun expiryFormat(){
        binding.editTextMonthYear.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
                var len: Int = p0.toString().length

                if (before == 0 && len == 2) {
                    binding.editTextMonthYear.append("/")
                }


            }
        })
    }

    private fun cardFormat(){
        binding.editTextCard.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p1: Editable?) {}

            override fun beforeTextChanged(p1: CharSequence?, p2: Int, p3: Int, p4: Int) {}

            override fun onTextChanged(p1: CharSequence?, start: Int, before: Int, count: Int) {
                var len: Int = p1.toString().length


                if (before == 0 && len == 4) {
                    binding.editTextCard.append(" ")
                }
                if (before == 0 && len == 9) {
                    binding.editTextCard.append(" ")
                }
                if (before == 0 && len == 14) {
                    binding.editTextCard.append(" ")
                }
                if(p1!!.isNotEmpty()) {
                    val firstDigit: Char = p1.get(0)
                    val visa: String = "4"
                    val master: String = "5"
                    if (firstDigit.toString().equals(visa)) {
                        binding.imageViewVisa.visibility = View.VISIBLE
                    } else if (firstDigit.toString().equals(master)) {
                        binding.imageViewMaster.visibility = View.VISIBLE
                    } else {

                    }
                }else{
                    binding.imageViewVisa.visibility = View.GONE
                    binding.imageViewMaster.visibility = View.GONE
                }


            }
        })
    }

    private fun validation(){

        var monthYear: String = binding.editTextMonthYear.text.toString()
        val myArray: List<String> = monthYear.split("/")
        var firstDigit : Char? = null
        val visa : String = "4"
        val master : String = "5"
        var card : String = binding.editTextCard.text.toString()


        if(binding.editTextCard.text.isNotEmpty()){
            firstDigit = card.get(0)
        }

        if (binding.editTextCard.text.isBlank() || binding.editTextMonthYear.text.isBlank() || binding.editTextCVV.text.isBlank()) {
            Toast.makeText(
                this@CardPay,
                "Card details cannot be empty.",
                Toast.LENGTH_LONG
            ).show()

            binding.editTextCard.setError("Please enter card number.")
            binding.editTextMonthYear.setError("Please enter expiry date.")
            binding.editTextCVV.setError("Please enter CVV number.")


        } else if(binding.editTextCard.text.length != 19){


            Toast.makeText(
                this@CardPay,
                "Invalid card number.",
                Toast.LENGTH_LONG
            ).show()

            binding.editTextCard.setError("Card number consists of 16 digits of number.")


        } else if(!firstDigit.toString().equals(visa) && !firstDigit.toString().equals(master)) {
            Toast.makeText(
                this@CardPay,
                "Invalid card number.",
                Toast.LENGTH_LONG
            ).show()

            binding.editTextCard.setError("Only VISA and Master card is accepted.")

        } else if (myArray.get(0).toInt() > 12 || myArray.get(1).toInt() < 21) {

            binding.editTextMonthYear.setError("Month must not greater than 12. \nYear must greater than current year.")

        } else if(binding.editTextCVV.text.length != 3){

            binding.editTextCVV.setError("Card CVV consists of 3 digits of number. \nIt available behind the card.")

        }else {
            val transID = intent.getStringExtra("transID")
            Toast.makeText(
                this@CardPay,
                "Payment Successful.",
                Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(this, DashBoardActivity::class.java)
            startActivity(intent)
            updateVoucherQty()
            firebaseUser = FirebaseAuth.getInstance().currentUser!!
            refUsers = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid)
            refUsers!!.child("payment").removeValue()
            refUsers!!.child("paymentHistory").child(transID.toString()).child("status").setValue("completed")

        }
    }

}