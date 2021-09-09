package my.edu.tarc.mad_assignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

//val context:Context, val vehicles: List<Vehicle>

class RecyclerAdapter(private val context: android.content.Context, private val vehicles: List<Vehicle>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    var refUsers: DatabaseReference?= null
    var refUsers2: DatabaseReference?= null
    var firebaseUser : FirebaseUser? = null
    private lateinit var insuranceList: ArrayList<insurance>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.vehicle_show, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val currentitem = vehicles[position]
        //holder.itemImage.setImageBitmap(BitmapFactory.decodeFile(currentitem.getfUrl()))
        Glide.with(context)
            .load(currentitem.getfUrl())
            .into(holder.itemImage)
        holder.itemPlatenum.text = currentitem.getplate_num()
        val carID = currentitem.getcarID().toString()
        holder.itemView.setOnClickListener {
            firebaseUser = FirebaseAuth.getInstance().currentUser
            /*refUsers2 = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid)
            refUsers2!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {

                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })*/
            refUsers = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid).child("insurance").child(carID)
            refUsers!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        holder.itembtnRenew.visibility = View.VISIBLE
                        holder.itemLess2.setOnClickListener {
                            holder.itembtnRenew.visibility = View.GONE
                        }
                        holder.itemExpDate.text = snapshot.child("expiredDate").getValue().toString()
                        //val count = snapshot.child("count").getValue()


                    } else {
                        holder.itembtnNew.visibility = View.VISIBLE
                        holder.itemLess1.setOnClickListener {
                            holder.itembtnNew.visibility = View.GONE
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
            Toast.makeText(it.context, "Car Plate: " + carID, Toast.LENGTH_SHORT).show()
        }


    }

    override fun getItemCount(): Int {
        return vehicles.size
    }

    inner class  ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ShapeableImageView
        var itemPlatenum: TextView
        var itembtnNew: ConstraintLayout
        var itembtnRenew: ConstraintLayout
        var itemLess1: TextView
        var itemLess2: TextView
        var itemExpDate: TextView

        init{
            itembtnNew = itemView.findViewById(R.id.detail1)
            itembtnRenew = itemView.findViewById(R.id.detail2)
            itemImage = itemView.findViewById(R.id.car_image)
            itemPlatenum = itemView.findViewById(R.id.car_plate_num)
            itemLess1 = itemView.findViewById(R.id.txtshowLess1)
            itemLess2 = itemView.findViewById(R.id.txtshowLess2)
            itemExpDate = itemView.findViewById(R.id.txtExpDate)
        }
    }

}