/*
package my.edu.tarc.mad_assignment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*

abstract class VehicleViewModel: ViewModel() {
    var vehicles: ArrayList<Vehicle>
    lateinit var db: DatabaseReference
    lateinit var db1: DatabaseReference

    init {
        vehicles = ArrayList<Vehicle>()
    }

    */
/*fun retriveVehicle(vehicle: Vehicle){
        vehicles.add(vehicle)qeertyuioop[
    }*//*


    fun select(username: String){

        db = FirebaseDatabase.getInstance().reference.child("customer").child(username).child("vehicle")
        db.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val count: Int = snapshot.childrenCount.toInt()
                    var carID: String = ""
                    for (start in 0 until count){
                        if (start < 10000) {
                            carID = "car" + start + 1
                        }
                        db1 = FirebaseDatabase.getInstance().reference.child("customer").child(username).child("vehicle").child(carID)
                        db1.addListenerForSingleValueEvent(object: ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {

                                val f_url: String? = snapshot.child("front url").getValue().toString()
                                val b_url: String? = snapshot.child("back url").getValue().toString()
                                val l_url: String? = snapshot.child("left url").getValue().toString()
                                val r_url: String? = snapshot.child("right url").getValue().toString()
                                val car_brand: String? = snapshot.child("car brand").getValue().toString()
                                val car_cc_type: String? = snapshot.child("car cc_type").getValue().toString()
                                val car_name: String? = snapshot.child("car name").getValue().toString()
                                val car_plate: String? = snapshot.child("car plate").getValue().toString()
                                val car_year: String? = snapshot.child("car year").getValue().toString()

                                vehicles.add(Vehicle(b_url,f_url,l_url,r_url,car_cc_type,car_brand,car_name,car_year,car_plate))

                                //return
                            }
                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }
                        })
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

}*/
