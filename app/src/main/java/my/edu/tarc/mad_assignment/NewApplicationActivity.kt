package my.edu.tarc.mad_assignment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.coroutines.selects.select
import my.edu.tarc.mad_assignment.databinding.ActivityNewApplicationBinding
import java.util.*

class NewApplicationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewApplicationBinding
    private lateinit var vehicleList: ArrayList<Vehicle>
    private lateinit var recyclerView: RecyclerView
    var refUsers: DatabaseReference?= null
    var firebaseUser : FirebaseUser? = null
    lateinit var db: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_application)
        binding =  ActivityNewApplicationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        recyclerView = findViewById(R.id.listViewVehicle)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        vehicleList = arrayListOf<Vehicle>()
        select()

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, Vehicle_add::class.java)
            startActivity(intent)
        }

    }

    private fun select(){
        firebaseUser = FirebaseAuth.getInstance().currentUser
        refUsers = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid)
        refUsers!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user: customer? = snapshot.getValue(customer::class.java)
                    var uid: String = user!!.getUID().toString()
                    db = FirebaseDatabase.getInstance().reference.child("customer").child(uid).child("vehicle")
                    db.addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()){
                                for (vehicleSnapshot in snapshot.children){
                                    val vehicle = vehicleSnapshot.getValue(Vehicle::class.java)
                                    vehicleList.add(vehicle!!)
                                }
                                recyclerView.adapter = RecyclerAdapter(this@NewApplicationActivity, vehicleList)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })


    }


    /*override fun onCellClickListener(data: Vehicle, mode: Int) {
        if(mode == 1){

        }else if(mode == 2){
            val builder = AlertDialog.Builder(context)
            builder.apply {
                setMessage("Delete contact ${data.phone} ?")
                setPositiveButton("Delete", DialogInterface.OnClickListener { dialog, which ->
                    myViewModel.deleteContact(data)
                    Toast.makeText(context, "Contact deleted", Toast.LENGTH_SHORT).show()
                })
                setNegativeButton("Cancel", null)
                show()
            }
        }
    }*/
}