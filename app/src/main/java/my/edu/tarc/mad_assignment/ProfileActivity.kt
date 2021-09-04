package my.edu.tarc.mad_assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.edu.tarc.mad_assignment.databinding.ActivityPaymentBinding
import my.edu.tarc.mad_assignment.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        binding =  ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //change to other activity
        /*val intent = Intent(this, ?Activity::class.java)
        startActivity(intent)*/
    }
}