package my.edu.tarc.mad_assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import my.edu.tarc.mad_assignment.databinding.ActivityForgetPwactivityBinding

class ForgetPWActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgetPwactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_pwactivity)
        binding =  ActivityForgetPwactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSent.setOnClickListener {
            val email : String =  binding.txtSentEmail.text.toString().trim{it <= ' '}
            if(email.isEmpty()){
                Toast.makeText(this@ForgetPWActivity, "Please enter email address", Toast.LENGTH_LONG).show()
            }
            else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            Toast.makeText(this@ForgetPWActivity, "Email sent successfully to your email", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, LoginActivity::class.java)
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)
                            this.finish()
                        }
                        else{
                            Toast.makeText(this@ForgetPWActivity, task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
            }


        }
    }
}