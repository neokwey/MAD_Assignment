package my.edu.tarc.mad_assignment

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.common.api.Api
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import my.edu.tarc.mad_assignment.databinding.ActivityEmpty2Binding
import my.edu.tarc.mad_assignment.databinding.ActivityEmptyBinding
import java.text.SimpleDateFormat
import java.util.*

class EmptyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmptyBinding
    var db: DatabaseReference?= null
    var refUsers: DatabaseReference?= null
    var refUsers2: DatabaseReference?= null
    var firebaseUser : FirebaseUser? = null
    var finalPrice: Double = 0.0
    var InsPrice: Double = 0.0
    var ncb: Int = 0
    var date: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty)
        binding =  ActivityEmptyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val carID = intent.getStringExtra("carId")

        val mDialogView = LayoutInflater.from(this@EmptyActivity).inflate(R.layout.reg_insurance_dialog, null)
        val txtncb = mDialogView.findViewById<TextView>(R.id.txtNcb)
        firebaseUser = FirebaseAuth.getInstance().currentUser
        refUsers = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid).child("insurance").child(carID!!)
        refUsers!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    ncb = snapshot.child("ncb").getValue().toString().toInt() + 5
                    date = snapshot.child("expiredDate").getValue().toString()
                    txtncb.text = ncb.toString() + "%"
                }
            }
            override fun onCancelled(error: DatabaseError) {  }
        })



        val builder = AlertDialog.Builder(this@EmptyActivity)

        builder.apply {
            setTitle(carID)
            setView(mDialogView)
            val textValue = mDialogView.findViewById<TextView>(R.id.etxtValue)
            val imgCheck = mDialogView.findViewById<ImageView>(R.id.imgCheck)
            var textInsurance = mDialogView.findViewById<TextView>(R.id.txtInsuranceAmount)

            imgCheck.setOnClickListener {
                if (textValue.text.toString().isEmpty()){
                    Toast.makeText(this@EmptyActivity, "Please key in a value", Toast.LENGTH_LONG).show()
                    textInsurance.setText("Vehicle value is not acceptable.")
                }else{
                    checkCondition(textValue.text.toString().toInt())
                    val condition: Int = textValue.text.toString().toInt()

                    if (condition >= 11000 && condition < 15000){
                        Toast.makeText(this@EmptyActivity, "Value is acceptable", Toast.LENGTH_LONG).show()
                        InsPrice = 700.0
                        val discountPrice: Double = (100 - ncb.toDouble()) / 100
                        finalPrice = InsPrice * discountPrice
                        val total = String.format("%.2f", finalPrice)
                        textInsurance.setText("Premium (WM) is RM ${InsPrice.toInt()},\nPremium Due is RM ${total}.")
                    } else if(condition >= 15000 && condition < 20000){
                        Toast.makeText(this@EmptyActivity, "Value is acceptable", Toast.LENGTH_LONG).show()
                        InsPrice = 1000.0
                        val discountPrice: Double = (100 - ncb.toDouble()) / 100
                        finalPrice = InsPrice * discountPrice
                        val total = String.format("%.2f", finalPrice)
                        textInsurance.setText("Premium (WM) is RM ${InsPrice.toInt()},\nPremium Due is RM ${total}.")
                    } else if(condition >= 20000){
                        Toast.makeText(this@EmptyActivity, "Value is acceptable", Toast.LENGTH_LONG).show()
                        InsPrice = 2000.0
                        val discountPrice: Double = (100 - ncb.toDouble()) / 100
                        finalPrice = InsPrice * discountPrice
                        val total = String.format("%.2f", finalPrice)
                        textInsurance.setText("Premium (WM) is RM ${InsPrice.toInt()},\nPremium Due is RM ${total}.")
                    } else {
                        textInsurance.setText("Vehicle value is not acceptable.")
                    }
                }
            }

            setPositiveButton("Buy", DialogInterface.OnClickListener { dialog, which ->
                val spliterator = date.split("-")
                val year = spliterator[0]
                val month = spliterator[1]
                val day = spliterator[2]
                val newYear: Int = 1 + year.toInt()
                val expDate = newYear.toString() + "-" + month + "-" + day
                val total = String.format("%.2f", finalPrice)

                firebaseUser = FirebaseAuth.getInstance().currentUser
                db = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid)
                refUsers = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid)

                val insuranceVehicle = insurance(carID!!, expDate, ncb)
                val insurancePayment = Payment(total.toDouble(), carID, ncb)
                refUsers!!.child("insurance").child(carID).setValue(insuranceVehicle)
                db!!.child("payment").child(carID).setValue(insurancePayment)

                val intent = Intent(this@EmptyActivity, NewApplicationActivity::class.java)
                startActivity(intent)
            })
            setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                val intent = Intent(this@EmptyActivity, NewApplicationActivity::class.java)
                startActivity(intent)
            })
            show()
        }
    }

    private fun checkCondition(textValue: Int) {
        var result : Boolean = true
        if (textValue < 11000 || textValue == null){
            result = false
        } else if(textValue >= 11000 && textValue < 15000){
            result = true
        } else if(textValue >= 15000 && textValue < 20000){
            result = true
        } else {
            result = true
        }
        actionForCondition(result)
    }
    private fun actionForCondition(result: Boolean){
        if (result == true){
            Toast.makeText(this@EmptyActivity, "Value is acceptable", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this@EmptyActivity, "Min Value is 11000", Toast.LENGTH_LONG).show()
        }
    }
}

/*
firebaseUser = FirebaseAuth.getInstance().currentUser
        refUsers = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid).child("insurance").child(carID!!)
        refUsers!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val mDialogView = LayoutInflater.from(this@EmptyActivity).inflate(R.layout.reg_insurance_dialog, null)
                    val txtncb = mDialogView.findViewById<TextView>(R.id.txtNcb)
                    val ncb: Int = snapshot.child("ncb").getValue().toString().toInt() + 5
                    txtncb.text = ncb.toString() + "%"
                    val builder = AlertDialog.Builder(this@EmptyActivity)
                    builder.apply {
                        setTitle("Insurance Form")
                        setView(mDialogView)

                        setPositiveButton("Save", DialogInterface.OnClickListener { dialog, which ->

                        })
                        setNegativeButton("Cancel", null)
                        show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
 */