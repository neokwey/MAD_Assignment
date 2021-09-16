package my.edu.tarc.mad_assignment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import my.edu.tarc.mad_assignment.databinding.ActivityReferralBinding
import my.edu.tarc.mad_assignment.databinding.ActivityRewardBinding

class ReferralActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReferralBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_referral)
        binding =  ActivityReferralBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tnc : Array<String> = resources.getStringArray(R.array.TnC)

        for(items in tnc){
            binding.textViewTerms.text = binding.textViewTerms.text.toString() + "\n\n" + items
        }

        binding.buttonCopy.setOnClickListener {
            val clipboard : ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip : ClipData = ClipData.newPlainText("Referral Code", binding.textViewCode.text.toString())
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this@ReferralActivity, "Copied to Clipboard", Toast.LENGTH_SHORT).show()
        }

        val code = intent.getStringExtra("code")
        binding.textViewCode.text = code

        //change to other activity
        /*val intent = Intent(this, ?Activity::class.java)
        startActivity(intent)*/
    }
}