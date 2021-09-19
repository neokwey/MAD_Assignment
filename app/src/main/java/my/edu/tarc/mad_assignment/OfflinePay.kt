package my.edu.tarc.mad_assignment

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.zxing.WriterException
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import my.edu.tarc.mad_assignment.databinding.ActivityOfflinePayBinding
import my.edu.tarc.mad_assignment.databinding.ActivityPaymentBinding
import java.lang.Exception
import kotlin.random.Random

class OfflinePay : AppCompatActivity() {


    private lateinit var binding: ActivityOfflinePayBinding
    private lateinit var refUsers: DatabaseReference
    private lateinit var refUsers1: DatabaseReference
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var qrCodeIV: ImageView
    private lateinit var dataEdt: TextView
    var transID : String = ""


    var bitmap: Bitmap? = null
    var qrgEnCoder: QRGEncoder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offline_pay)

        binding = ActivityOfflinePayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //transID = Random.nextInt(1000000000)
        transID = intent.getStringExtra("transID").toString()
        binding.textViewTransactionID.text = "${transID}"

        val toPay = intent.getStringExtra("toPay")
        binding.textViewTotal.text = "RM ${toPay}"
        binding.textViewTotal2.text = "RM ${toPay}"

        binding.btnDonePay.setOnClickListener {
            Toast.makeText(
                this@OfflinePay,
                "Payment Successful.",
                Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(this, DashBoardActivity::class.java)
            startActivity(intent)

            firebaseUser = FirebaseAuth.getInstance().currentUser!!
            refUsers =
                FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid)
            refUsers!!.child("payment").removeValue()
            refUsers!!.child("paymentHistory").child(transID.toString()).child("status")
                .setValue("completed")
            updateVoucherQty()
        }

        //set find ID
        qrCodeIV = findViewById(R.id.imgViewQr)
        dataEdt = findViewById(R.id.textViewTransactionID)


        val manager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = manager.defaultDisplay
        val point = Point()
        display.getSize(point)
        val width = point.x
        val height = point.y
        var dimen = if (width < height) width else height

        dimen = dimen * 3 / 4

        qrgEnCoder = QRGEncoder(dataEdt.text.toString(), null, QRGContents.Type.TEXT, dimen)

        try {
            bitmap = qrgEnCoder!!.encodeAsBitmap()
            qrCodeIV.setImageBitmap(bitmap)
        } catch (e: Exception) {
            Log.e("Tag", e.toString())
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
}