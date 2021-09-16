package my.edu.tarc.mad_assignment

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import org.w3c.dom.Text
import kotlin.properties.Delegates


class VoucherAdapter (private val voucherList : ArrayList<Voucher>, userUID : String, txtPoints : TextView) : RecyclerView.Adapter<VoucherAdapter.MyViewHolder>() {
    val userUID : String = userUID
    val txtPoints : TextView = txtPoints
    val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference("customer")
    // var userPoints : Int = txtPoints.text.toString().toInt()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.voucher_layout, parent, false)
    return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = voucherList[position]
        val index = position
        holder.description.setText(currentItem.voucherDesc)
        holder.points.setText(currentItem.requiredPoints)

        holder.btnClaim.setOnClickListener {
            val voucherPoints : Int = voucherList[position].requiredPoints!!.toInt()
            var userPoints : Int
            firebase.child(userUID).child("rewards").child("points").get().addOnSuccessListener {
                userPoints = it.value.toString().toInt()
                Log.d("user points", userPoints.toString())

                if(voucherPoints <= userPoints){
                   Toast.makeText(holder.itemView.context, "You have successfully redeem your voucher.", Toast.LENGTH_SHORT).show()

                    var voucherRef = firebase.child(userUID).child("rewards").child("voucher")
                    voucherRef.addListenerForSingleValueEvent(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.hasChild(voucherList[index].voucherID!!)){
                                voucherRef.child(voucherList[index].voucherID!!).child("quantity").get().addOnSuccessListener {
                                    firebase.child(userUID).child("rewards").child("points").get().addOnSuccessListener {
                                        var updatePoint = it.value.toString().toInt()
                                        updatePoint-=voucherPoints
                                        firebase.child(userUID).child("rewards").child("points").setValue(updatePoint.toString())
                                        txtPoints.text = "$updatePoint"
                                    }

                                    var qty = it.value.toString().toInt()

                                    qty++

                                    voucherRef.child(voucherList[index].voucherID!!).child("quantity").setValue(qty.toString())
                                }
                            }
                            else{
                                firebase.child(userUID).child("rewards").child("points").get().addOnSuccessListener {
                                    var updatePoint = it.value.toString().toInt()
                                    updatePoint-=voucherPoints
                                    firebase.child(userUID).child("rewards").child("points").setValue(updatePoint.toString())
                                    txtPoints.text = "$updatePoint"
                                }

                                voucherRef.child(voucherList[index].voucherID!!).child("quantity").setValue("1")
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })

                }
                else{
                    Toast.makeText(holder.itemView.context, "You have not enough points to redeem", Toast.LENGTH_SHORT).show()
                }
            }
            Log.d("voucher points", voucherPoints.toString())


        }
    }

    override fun getItemCount(): Int {
        return voucherList.size
    }

    class MyViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        val description : TextView = itemView.findViewById(R.id.textViewTitle)
        val points : TextView = itemView.findViewById(R.id.textViewPoints)
        val btnClaim : Button = itemView.findViewById(R.id.buttonClaim)
    }
}