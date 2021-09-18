package my.edu.tarc.mad_assignment

import android.content.DialogInterface
import java.time.LocalDateTime
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.common.api.Api
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

//val context:Context, val vehicles: List<Vehicle>, private val cellClickListener: CellClickListener

class RecyclerAdapter(private val context: android.content.Context, private val vehicles: List<Vehicle>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    var refUsers: DatabaseReference?= null
    var refUsers2: DatabaseReference?= null
    var firebaseUser : FirebaseUser? = null
    var carId: String = ""
    private lateinit var insuranceList: ArrayList<insurance>
    //private val myViewModel: NewApplicationActivity by

    interface CellClickListener {
        fun onCellClickListener(data: Vehicle, mode: Int)
    }

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
            refUsers = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid).child("insurance").child(carID)
            refUsers!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        holder.itemViewRenew.visibility = View.VISIBLE
                        holder.itembtnRenew.setOnClickListener{
                            val intent = Intent(holder.itemView.context, EmptyActivity::class.java)
                            intent.putExtra("carId", currentitem.getcarID().toString())
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            holder.itemView.context.startActivity(intent)
                        }
                        holder.itemLess2.setOnClickListener {
                            holder.itemViewRenew.visibility = View.GONE
                        }
                        holder.itemExpDate.text = "Expired Date: " + snapshot.child("expiredDate").getValue().toString()

                        holder.itembtnClaim.setOnClickListener {
                            refUsers!!.child("ncb").setValue("0")
                            val intent = Intent(holder.itemView.context, NewApplicationActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            holder.itemView.context.startActivity(intent)
                        }

                    } else {
                        holder.itemViewNew.visibility = View.VISIBLE
                        holder.itembtnBuy.setOnClickListener{

                            val intent = Intent(holder.itemView.context, EmptyActivity2::class.java)
                            intent.putExtra("carId", currentitem.getcarID().toString())
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            holder.itemView.context.startActivity(intent)

                        }
                        holder.itemLess1.setOnClickListener {
                            holder.itemViewNew.visibility = View.GONE
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
        var itemViewNew: ConstraintLayout
        var itemViewRenew: ConstraintLayout
        var itemLess1: TextView
        var itemLess2: TextView
        var itemExpDate: TextView
        var itembtnBuy: Button
        var itembtnRenew: Button
        var itembtnClaim: Button

        init{
            itembtnClaim = itemView.findViewById(R.id.btnClaim)
            itembtnRenew = itemView.findViewById(R.id.btnRenew)
            itembtnBuy = itemView.findViewById(R.id.btnBuy)
            itemViewNew = itemView.findViewById(R.id.detail1)
            itemViewRenew = itemView.findViewById(R.id.detail2)
            itemImage = itemView.findViewById(R.id.car_image)
            itemPlatenum = itemView.findViewById(R.id.car_plate_num)
            itemLess1 = itemView.findViewById(R.id.txtshowLess1)
            itemLess2 = itemView.findViewById(R.id.txtshowLess2)
            itemExpDate = itemView.findViewById(R.id.txtExpDate)
        }
    }

}