package my.edu.tarc.mad_assignment

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import my.edu.tarc.mad_assignment.databinding.ActivityDashBoardBinding
import my.edu.tarc.mad_assignment.databinding.ActivityEmpty2Binding
import org.w3c.dom.Text
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
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
    var carID: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty2)
        binding =  ActivityEmpty2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        carID = intent.getStringExtra("carId").toString()

        buy()


    }

    private fun buy(){
        val mDialogView = LayoutInflater.from(this@EmptyActivity2).inflate(R.layout.reg_insurance_dialog, null)
        val txtncb = mDialogView.findViewById<TextView>(R.id.txtNcb)
        val ncb: Int = 25
        txtncb.text = ncb.toString() + "%"
        val builder = AlertDialog.Builder(this@EmptyActivity2)

        builder.apply {
            setTitle(carID)
            setView(mDialogView)
            val textValue = mDialogView.findViewById<TextView>(R.id.etxtValue)
            var textInsurance = mDialogView.findViewById<TextView>(R.id.txtInsuranceAmount)
            var btnBuy = mDialogView.findViewById<TextView>(R.id.btnBuy2)
            var btnCancel = mDialogView.findViewById<TextView>(R.id.btnCancel2)

            textValue.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {  }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {  }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (textValue.text.toString().isEmpty()){
                        textInsurance.setText("Please key in a value.\nVehicle value is not acceptable.")
                        btnBuy.isEnabled = false
                    }else{
                        checkCondition(textValue.text.toString().toInt())
                        val condition: Int = textValue.text.toString().toInt()

                        firebaseUser = FirebaseAuth.getInstance().currentUser
                        db = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid)
                        refUsers2 = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid).child("vehicle").child(carID)
                        refUsers2!!.addListenerForSingleValueEvent(object : ValueEventListener {
                            @RequiresApi(Build.VERSION_CODES.O)
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists()) {
                                    val carYear = snapshot.child("carYear").getValue()
                                    val cc = snapshot.child("cc").getValue().toString().toDouble()
                                    val c = Calendar.getInstance()
                                    val yearNow = c.get(Calendar.YEAR)
                                    var yearBetween = yearNow - carYear.toString().toInt()
                                    if (yearBetween <= 5){
                                        //more expensive
                                        if (cc == 1.0 || cc == 1.1 || cc == 1.2 || cc == 1.3){
                                            if (condition >= 11000 && condition < 20000){
                                                InsPrice = 700.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //700
                                            else if(condition >= 20000 && condition < 40000){
                                                InsPrice = 1500.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //1500
                                            else if(condition >= 40000){
                                                InsPrice = 3000.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //3000
                                            else {
                                                textInsurance.setText("Vehicle value is not acceptable.")
                                                btnBuy.isEnabled = false
                                            }
                                        }
                                        else if (cc == 1.3 || cc == 1.5){
                                            if (condition >= 11000 && condition < 20000){
                                                InsPrice = 1500.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //1500
                                            else if(condition >= 20000 && condition < 40000){
                                                InsPrice = 3000.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //3000
                                            else if(condition >= 40000){
                                                InsPrice = 4500.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //4500
                                            else {
                                                textInsurance.setText("Vehicle value is not acceptable.")
                                                btnBuy.isEnabled = false
                                            }
                                        }
                                        else if (cc == 1.6 || cc == 1.7){
                                            if (condition >= 11000 && condition < 20000){
                                                InsPrice = 2500.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //2500
                                            else if(condition >= 20000 && condition < 40000){
                                                InsPrice = 3500.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //3500
                                            else if(condition >= 40000){
                                                InsPrice = 5500.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //5500
                                            else {
                                                textInsurance.setText("Vehicle value is not acceptable.")
                                                btnBuy.isEnabled = false
                                            }
                                        }
                                        else if (cc == 1.8 || cc == 1.9 || cc == 2.0){
                                            if (condition >= 11000 && condition < 20000){
                                                InsPrice = 3000.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //3000
                                            else if(condition >= 20000 && condition < 40000){
                                                InsPrice = 4000.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //4000
                                            else if(condition >= 40000){
                                                InsPrice = 6000.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //6000
                                            else {
                                                textInsurance.setText("Vehicle value is not acceptable.")
                                                btnBuy.isEnabled = false
                                            }
                                        }

                                    }
                                    else if (yearBetween>5&&yearBetween<=10){

                                        if (cc == 1.0 || cc == 1.1 || cc == 1.2 || cc == 1.3){
                                            if (condition >= 11000 && condition < 20000){
                                                InsPrice = 500.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //500
                                            else if(condition >= 20000 && condition < 40000){
                                                InsPrice = 1000.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //1000
                                            else if(condition >= 40000){
                                                InsPrice = 2000.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //2000
                                            else {
                                                textInsurance.setText("Vehicle value is not acceptable.")
                                                btnBuy.isEnabled = false
                                            }
                                        }
                                        else if (cc == 1.3 || cc == 1.5){
                                            if (condition >= 11000 && condition < 20000){
                                                InsPrice = 800.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //800
                                            else if(condition >= 20000 && condition < 40000){
                                                InsPrice = 2000.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //2000
                                            else if(condition >= 40000){
                                                InsPrice = 3000.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //3000
                                            else {
                                                textInsurance.setText("Vehicle value is not acceptable.")
                                                btnBuy.isEnabled = false
                                            }
                                        }
                                        else if (cc == 1.6 || cc == 1.7){
                                            if (condition >= 11000 && condition < 20000){
                                                InsPrice = 1000.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //1000
                                            else if(condition >= 20000 && condition < 40000){
                                                InsPrice = 3000.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //3000
                                            else if(condition >= 40000){
                                                InsPrice = 4000.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //4000
                                            else {
                                                textInsurance.setText("Vehicle value is not acceptable.")
                                                btnBuy.isEnabled = false
                                            }
                                        }
                                        else if (cc == 1.8 || cc == 1.9 || cc == 2.0){
                                            if (condition >= 11000 && condition < 20000){
                                                InsPrice = 2000.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } // 2000
                                            else if(condition >= 20000 && condition < 40000){
                                                InsPrice = 4000.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //4000
                                            else if(condition >= 40000){
                                                InsPrice = 5000.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //5000
                                            else {
                                                textInsurance.setText("Vehicle value is not acceptable.")
                                                btnBuy.isEnabled = false
                                            }
                                        }

                                    }
                                    else {

                                        if (cc == 1.0 || cc == 1.1 || cc == 1.2 || cc == 1.3){

                                            if (condition >= 11000 && condition < 20000){
                                                InsPrice = 350.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //350
                                            else if(condition >= 20000 && condition < 40000){
                                                InsPrice = 500.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //500
                                            else if(condition >= 40000){
                                                InsPrice = 1000.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //1000
                                            else {
                                                textInsurance.setText("Vehicle value is not acceptable.")
                                                btnBuy.isEnabled = false
                                            }

                                        }
                                        else if (cc == 1.4 || cc == 1.5){

                                            if (condition >= 11000 && condition < 20000){
                                                InsPrice = 700.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //700
                                            else if(condition >= 20000 && condition < 40000){
                                                InsPrice = 1000.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //1000
                                            else if(condition >= 40000){
                                                InsPrice = 2000.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //2000
                                            else {
                                                textInsurance.setText("Vehicle value is not acceptable.")
                                                btnBuy.isEnabled = false
                                            }

                                        }
                                        else if (cc == 1.6 || cc == 1.7){

                                            if (condition >= 11000 && condition < 20000){
                                                InsPrice = 1500.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //1500
                                            else if(condition >= 20000 && condition < 40000){
                                                InsPrice = 2000.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //2000
                                            else if(condition >= 40000){
                                                InsPrice = 3000.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //3000
                                            else {
                                                textInsurance.setText("Vehicle value is not acceptable.")
                                                btnBuy.isEnabled = false
                                            }

                                        }
                                        else if (cc == 1.8 || cc == 1.9 || cc == 2.0){

                                            if (condition >= 11000 && condition < 20000){
                                                InsPrice = 2500.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //2500
                                            else if(condition >= 20000 && condition < 40000){
                                                InsPrice = 3000.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //3000
                                            else if(condition >= 40000){
                                                InsPrice = 4000.0
                                                val discountPrice: Double = (100 - ncb.toDouble()) / 100
                                                finalPrice = InsPrice * discountPrice
                                                textInsurance.setText("Premium (WM) is RM ${InsPrice}0,\nPremium Due is RM ${finalPrice}0.")
                                                btnBuy.isEnabled = true
                                            } //4000
                                            else {
                                                textInsurance.setText("Vehicle value is not acceptable.")
                                                btnBuy.isEnabled = false
                                            }
                                        }
                                    }
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {

                            }
                        })


                    }
                }
            })

            btnBuy.setOnClickListener {
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
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
            btnCancel.setOnClickListener {
                val intent = Intent(this@EmptyActivity2, NewApplicationActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
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
        }
    }
}