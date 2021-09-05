package my.edu.tarc.mad_assignment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import my.edu.tarc.mad_assignment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val SPLASH_DISPLAY_LENGTH: Long = 3000
    private lateinit var binding: ActivityMainBinding
    var refUsers: DatabaseReference?= null
    var firebaseUser : FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseUser = FirebaseAuth.getInstance().currentUser
        refUsers = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid)

        Handler(Looper.getMainLooper()).postDelayed({
            refUsers!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists())
                    {
                        val intent = Intent(this@MainActivity, DashBoardActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else {
                        val intent = Intent(this@MainActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }, SPLASH_DISPLAY_LENGTH)
    }
}