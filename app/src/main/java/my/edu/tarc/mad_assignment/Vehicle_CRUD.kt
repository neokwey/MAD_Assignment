package my.edu.tarc.mad_assignment

import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
//import my.edu.tarc.mad_assignment.VehicleViewModel as vehicleViewModel
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import java.sql.Struct

class Vehicle_CRUD internal constructor() {

    var vehicles = ArrayList<Vehicle>()
    val vehicleView = VehicleViewModel()


    fun select( a: AppCompatActivity?, db: DatabaseReference, rv: RecyclerView, adapter: RecyclerAdapter){

        db.child("customer").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //get username value
                val username: String? = "C1"

                //check match or not
                if (username == snapshot.getValue().toString()){

                    //open db find again
                    db.child(username).addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(datasnapshot: DataSnapshot) {

                            db.child("vehicle").addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(datasnapshot1: DataSnapshot) {
                                    var carid: String = ""
                                    val count: Int = datasnapshot1.childrenCount.toInt()
                                    val startcount: Int = 0
                                    for (startcount in 0 until count) {

                                        if (startcount < 100) {
                                            carid = "car" + startcount + 1
                                        }

                                        db.child(carid).addListenerForSingleValueEvent(object : ValueEventListener {
                                            override fun onDataChange(datasnapshot2: DataSnapshot) {
                                                vehicles.clear()
                                                val f_url: String? = datasnapshot2.child("front url").getValue().toString()
                                                val b_url: String? = datasnapshot2.child("back url").getValue().toString()
                                                val l_url: String? = datasnapshot2.child("left url").getValue().toString()
                                                val r_url: String? = datasnapshot2.child("right url").getValue().toString()
                                                val car_brand: String? = datasnapshot2.child("car brand").getValue().toString()
                                                val car_cc_type: String? = datasnapshot2.child("car cc_type").getValue().toString()
                                                val car_name: String? = datasnapshot2.child("car name").getValue().toString()
                                                val car_plate: String? = datasnapshot2.child("car plate").getValue().toString()
                                                val car_year: String? = datasnapshot2.child("car year").getValue().toString()

                                                vehicleView.retriveVehicle(Vehicle(b_url,f_url,l_url,r_url,car_cc_type,car_brand,car_name,car_year,car_plate))
                                                //vehicles.add(Vehicle(b_url,f_url,l_url,r_url,car_cc_type,car_brand,car_name,car_year,car_plate))


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

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })
                }


            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    internal fun retriveVehicle(vehicle: ArrayList<Vehicle>){
        vehicles = vehicle
    }
}