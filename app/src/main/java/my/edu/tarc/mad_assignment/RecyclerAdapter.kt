package my.edu.tarc.mad_assignment

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
//val context:Context, val vehicles: List<Vehicle>

class RecyclerAdapter internal constructor(): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var vehicles = ArrayList<Vehicle>()

    internal fun setVehicle(vehicle: ArrayList<Vehicle>){
        vehicles = vehicle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.add_vehicle, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        //vehicles.get(position)
        holder.itemPlatenum.text = vehicles[position].plate_num
        val bitmap = BitmapFactory.decodeFile(vehicles[position].b_url)
        holder.itemImage.setImageBitmap(bitmap)
        holder.itemView.setOnClickListener {
            Toast.makeText(it.context, "Car Plate: " + vehicles[position].plate_num, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return vehicles.size
    }

    inner class  ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ShapeableImageView
        var itemPlatenum: TextView

        init{
            itemImage = itemView.findViewById(R.id.car_image)
            itemPlatenum = itemView.findViewById(R.id.car_plate_num)
        }
    }

}