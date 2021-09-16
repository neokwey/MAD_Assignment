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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import my.edu.tarc.mad_assignment.databinding.ActivityDashBoardBinding
import my.edu.tarc.mad_assignment.databinding.ActivityEmpty2Binding
import org.w3c.dom.Text
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class EmptyActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityEmpty2Binding
    var db: DatabaseReference?= null
    var refUsers: DatabaseReference?= null
    var refUsers2: DatabaseReference?= null
    var firebaseUser : FirebaseUser? = null
    var finalPrice: Double = 0.0
    var InsPrice: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty2)
        binding =  ActivityEmpty2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val carID = intent.getStringExtra("carId")


        val mDialogView = LayoutInflater.from(this@EmptyActivity2).inflate(R.layout.reg_insurance_dialog, null)
        val txtncb = mDialogView.findViewById<TextView>(R.id.txtNcb)
        val ncb: Int = 25
        txtncb.text = ncb.toString() + "%"
        val builder = AlertDialog.Builder(this@EmptyActivity2)

        builder.apply {
            setTitle(carID)
            setView(mDialogView)
            val textValue = mDialogView.findViewById<TextView>(R.id.etxtValue)
            val imgCheck = mDialogView.findViewById<ImageView>(R.id.imgCheck)
            var textInsurance = mDialogView.findViewById<TextView>(R.id.txtInsuranceAmount)
            imgCheck.setOnClickListener {
                if (textValue.text.toString().isEmpty()){
                    Toast.makeText(this@EmptyActivity2, "Please key in a value", Toast.LENGTH_LONG).show()
                    textInsurance.setText("Vehicle value is not acceptable.")
                }else{
                    checkCondition(textValue.text.toString().toInt())
                    val condition: Int = textValue.text.toString().toInt()

                    if (condition >= 11000 && condition < 15000){
                        Toast.makeText(this@EmptyActivity2, "Value is acceptable", Toast.LENGTH_LONG).show()
                        InsPrice = 700.0
                        val discountPrice: Double = (100 - ncb.toDouble()) / 100
                        finalPrice = InsPrice * discountPrice
                        textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                    } else if(condition >= 15000 && condition < 20000){
                        Toast.makeText(this@EmptyActivity2, "Value is acceptable", Toast.LENGTH_LONG).show()
                        InsPrice = 1000.0
                        val discountPrice: Double = (100 - ncb.toDouble()) / 100
                        finalPrice = InsPrice * discountPrice
                        textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                    } else if(condition >= 20000){
                        Toast.makeText(this@EmptyActivity2, "Value is acceptable", Toast.LENGTH_LONG).show()
                        InsPrice = 2000.0
                        val discountPrice: Double = (100 - ncb.toDouble()) / 100
                        finalPrice = InsPrice * discountPrice
                        textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                    } else {
                        textInsurance.setText("Vehicle value is not acceptable.")
                    }
                }
            }

            setPositiveButton("Buy", DialogInterface.OnClickListener { dialog, which ->
                val c = Calendar.getInstance()
                var yearNow = c.get(Calendar.YEAR)
                val monthNow = c.get(Calendar.MONTH)
                val dayNow = c.get(Calendar.DAY_OF_MONTH)
                val year = yearNow + 1
                val month = monthNow + 1
                val day = dayNow - 1
                val expDate = year.toString() + "-" + month.toString() + "-" + day.toString()
                firebaseUser = FirebaseAuth.getInstance().currentUser
                db = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid)
                refUsers = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid)

                val insuranceVehicle = insurance(carID!!, expDate, ncb)
                val insurancePayment = Payment(finalPrice, carID, ncb)
                refUsers!!.child("insurance").child(carID).setValue(insuranceVehicle)
                db!!.child("payment").child(carID).setValue(insurancePayment)

                val intent = Intent(this@EmptyActivity2, NewApplicationActivity::class.java)
                startActivity(intent)
            })
            setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                val intent = Intent(this@EmptyActivity2, NewApplicationActivity::class.java)
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
            Toast.makeText(this@EmptyActivity2, "Value is acceptable", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this@EmptyActivity2, "Min Value is 11000", Toast.LENGTH_LONG).show()
        }
    }


}

/*

            if (textValue.text.toString().toInt() < 11000){
                Toast.makeText(this@EmptyActivity2, "Min Value is 11000", Toast.LENGTH_LONG).show()
                result = false
            } else if(textValue.text.toString().toInt()>= 11000 && textValue.text.toString().toInt() < 15000){
                Toast.makeText(this@EmptyActivity2, "Value is acceptable", Toast.LENGTH_LONG).show()
                val InsPrice: Double = 700.0
                val discountPrice = 100 - ncb / 100
                finalPrice = InsPrice * discountPrice

            } else if(textValue.text.toString().toInt()>= 15000 && textValue.text.toString().toInt() < 20000){
                Toast.makeText(this@EmptyActivity2, "Value is acceptable", Toast.LENGTH_LONG).show()
                val InsPrice: Double = 1000.0
                val discountPrice = 100 - ncb / 100
                finalPrice = InsPrice * discountPrice

            } else {
                Toast.makeText(this@EmptyActivity2, "Value is acceptable", Toast.LENGTH_LONG).show()
                val InsPrice: Double = 2000.0
                val discountPrice = 100 - ncb / 100
                finalPrice = InsPrice * discountPrice
            }

            if (result == true){
                val c = Calendar.getInstance()
                var yearNow = c.get(Calendar.YEAR)
                val monthNow = c.get(Calendar.MONTH)
                val dayNow = c.get(Calendar.DAY_OF_MONTH)
                val year = yearNow + 1
                val month = monthNow + 1
                val day = dayNow - 1
                val expDate = year.toString() + "-" + month.toString() + "-" + day.toString()

                val insuranceVehicle = insurance(carId, 1, expDate, ncb)
                val insurancePayment = Payment(finalPrice, carId, ncb)
                refUsers!!.child(carId).setValue(insuranceVehicle)
                db!!.child(carId).setValue(insurancePayment)
                val intent = Intent(this@EmptyActivity2, DashBoardActivity::class.java)
                startActivity(intent)
            } else {
                return
            }


        })


        var result: Boolean = true
        val positiveBtn: Button = findViewById(R.id.btnBuy)
        positiveBtn!!.setOnClickListener {
            if (textValue.text.toString().toInt() < 11000){
                Toast.makeText(this@EmptyActivity2, "Min Value is 11000", Toast.LENGTH_LONG).show()
                result = false
            } else if(textValue.text.toString().toInt()>= 11000 && textValue.text.toString().toInt() < 15000){
                Toast.makeText(this@EmptyActivity2, "Value is acceptable", Toast.LENGTH_LONG).show()
                val InsPrice: Double = 700.0
                val discountPrice = 100 - ncb / 100
                finalPrice = InsPrice * discountPrice

            } else if(textValue.text.toString().toInt()>= 15000 && textValue.text.toString().toInt() < 20000){
                Toast.makeText(this@EmptyActivity2, "Value is acceptable", Toast.LENGTH_LONG).show()
                val InsPrice: Double = 1000.0
                val discountPrice = 100 - ncb / 100
                finalPrice = InsPrice * discountPrice

            } else {
                Toast.makeText(this@EmptyActivity2, "Value is acceptable", Toast.LENGTH_LONG).show()
                val InsPrice: Double = 2000.0
                val discountPrice = 100 - ncb / 100
                finalPrice = InsPrice * discountPrice
            }

            if (result == true){
                val c = Calendar.getInstance()
                var yearNow = c.get(Calendar.YEAR)
                val monthNow = c.get(Calendar.MONTH)
                val dayNow = c.get(Calendar.DAY_OF_MONTH)
                val year = yearNow + 1
                val month = monthNow + 1
                val day = dayNow - 1
                val expDate = year.toString() + "-" + month.toString() + "-" + day.toString()

                val insuranceVehicle = insurance(carId, 1, expDate, ncb)
                val insurancePayment = Payment(finalPrice, carId, ncb)
                refUsers!!.child(carId).setValue(insuranceVehicle)
                db!!.child(carId).setValue(insurancePayment)
                val intent = Intent(this@EmptyActivity2, DashBoardActivity::class.java)
                startActivity(intent)
            }
        }


    }*/


/*val insuranceVehicle = insurance(carId, 0, "", 25)
refUsers!!.child(carId).setValue(insuranceVehicle)

refUsers2 = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid).child("insurance").child(carId!!)
refUsers2!!.addListenerForSingleValueEvent(object : ValueEventListener {
    override fun onDataChange(snapshot: DataSnapshot) {
        if (snapshot.exists()) {

        }
    }

    override fun onCancelled(error: DatabaseError) {

    }
})*/
/*val insuranceVehicle = insurance(carId, 0, "", 25)
refUsers!!.child(carId).setValue(insuranceVehicle)

firebaseUser = FirebaseAuth.getInstance().currentUser
refUsers = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid).child("insurance").child(carId!!)
refUsers!!.addListenerForSingleValueEvent(object : ValueEventListener {
    override fun onDataChange(snapshot: DataSnapshot) {
        if (snapshot.exists()) {
            val mDialogView = LayoutInflater.from(this@EmptyActivity2).inflate(R.layout.reg_insurance_dialog, null)
            val txtncb = mDialogView.findViewById<TextView>(R.id.txtNcb)
            val ncb: Int = snapshot.child("ncb").getValue().toString().toInt()
            txtncb.text = ncb.toString() + "%"
            val builder = AlertDialog.Builder(this@EmptyActivity2)
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
})*/