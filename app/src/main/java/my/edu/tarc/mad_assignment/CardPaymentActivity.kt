package my.edu.tarc.mad_assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import my.edu.tarc.mad_assignment.databinding.ActivityCardPaymentBinding



class CardPaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCardPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_payment)
        binding = ActivityCardPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cardFormat()
        expiryFormat()

        binding.btnPay.setOnClickListener {
            validation()
        }
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
        var card : String = binding.editTextCard.text.toString()
        //val firstDigit = Integer.toString(card).substring(0, 1).toInt()
        val firstDigit : Char = card.get(0)
        val visa : String = "4"
        val master : String = "5"
        var monthYear: String = binding.editTextMonthYear.text.toString()
        val myArray: List<String> = monthYear.split("/")
        //val myArray = monthYear.split("/").toTypedArray()
        binding.textViewError.text = ""


        if (binding.editTextCard.text.isEmpty() || binding.editTextMonthYear.text.isEmpty() || binding.editTextCVV.text.isEmpty()) {
            Toast.makeText(
                this@CardPaymentActivity,
                "Card details cannot be empty.",
                Toast.LENGTH_LONG
            ).show()

        } else if(binding.editTextCard.text.length != 19){
            Toast.makeText(
                this@CardPaymentActivity,
                "Invalid card number.",
                Toast.LENGTH_LONG
            ).show()
            binding.textViewError.text = "Card number consists of 16 digits of number."


        } else if(!firstDigit.toString().equals(visa) && !firstDigit.toString().equals(master)) {
            Toast.makeText(
                this@CardPaymentActivity,
                "Invalid card number.",
                Toast.LENGTH_LONG
            ).show()
            binding.textViewError.text = "Only VISA and Master card is accepted."

        } else if (myArray.get(0).toInt() > 12 || myArray.get(1).toInt() < 21) {

            binding.textViewError.text = "Month must not greater than 12. \nYear must greater than current year."

        } else if(binding.editTextCVV.text.length != 3){

            binding.textViewError.text = "Card CVV consists of 3 digits of number. \nIt available behind the card."

        }else {

            Toast.makeText(
                this@CardPaymentActivity,
                "Payment Successful.",
                Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(this, DashBoardActivity::class.java)
            startActivity(intent)

        }
    }



}
