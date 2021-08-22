package my.edu.tarc.mad_assignment

import androidx.lifecycle.ViewModel

class VehicleViewModel: ViewModel() {
    var vehicles: ArrayList<Vehicle>
    init {
        vehicles = ArrayList<Vehicle>()
    }

    fun retriveVehicle(vehicle: Vehicle){
        vehicles.add(vehicle)
    }

}