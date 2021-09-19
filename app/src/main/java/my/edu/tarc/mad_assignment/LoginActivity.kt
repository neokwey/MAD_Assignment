package my.edu.tarc.mad_assignment

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import my.edu.tarc.mad_assignment.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private var firebaseUserID : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding =  ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()

        binding.txt4getPW.setOnClickListener {

            val intent = Intent(this, ForgetPWActivity::class.java)
            startActivity(intent)

        }
        binding.txtReg.setOnClickListener {

            val intent = Intent(this, RegisterUserActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener{
            loginuser()
        }
        binding.tvLink.setOnClickListener{

        link()

        }

    }

    private fun link(){
        mAuth.currentUser?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if(task.isSuccessful){


                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Link Successfully sent, Please verify your email to login.")
                    builder.setPositiveButton(R.string.ok,
                        DialogInterface.OnClickListener { dialog, i -> dialog.cancel() })
                    builder.show()

                }


            }


    }

    private fun loginuser() {
        val email: String = binding.txtEmail2.text.toString()
        val password: String = binding.txtPass1.text.toString()



        if (email==""){

            binding.txtEmail2.setError("Please fill in email.")
        } else if(password=="") {

            binding.txtPass1.setError("Please enter password.")
        } else{


            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->

                if(mAuth.currentUser?.isEmailVerified==false)
                {
                binding.tvLink.isVisible = true

                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Email not verified.")
                    builder.setPositiveButton(R.string.ok,
                        DialogInterface.OnClickListener { dialog, i -> dialog.cancel() })
                    builder.show()

                }else
                { if (task.isSuccessful) {

                    val intent = Intent(this, DashBoardActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    finish()
                }else if(!task.isSuccessful){

                    val builder = AlertDialog.Builder(this)
                    builder.setTitle(task.exception!!.message.toString())
                    builder.setPositiveButton(R.string.ok,
                        DialogInterface.OnClickListener { dialog, i -> dialog.cancel() })
                    builder.show()
                }
                }
                }


            }

        }

    }
