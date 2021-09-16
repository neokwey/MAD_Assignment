package my.edu.tarc.mad_assignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class PaymentVoucherAdapter(
    private val voucherList: ArrayList<PaymentVoucher>, private val layout: LinearLayout, txtDiscount : TextView
) : RecyclerView.Adapter<PaymentVoucherAdapter.MyViewHolder>() {
    private lateinit var refUsers: DatabaseReference
    private lateinit var firebaseUser : FirebaseUser
    val txtDiscount = txtDiscount

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PaymentVoucherAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.payment_voucher_layout,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = voucherList[position]


        holder.voucherQuantity.text = currentItem.quantity + " voucher(s) left."
        holder.discountAmount.text = "Discount RM" + currentItem.discountAmount
        holder.btnSelect.setOnClickListener {
            firebaseUser = FirebaseAuth.getInstance().currentUser!!
            refUsers = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid)
            refUsers!!.child("voucherUsed").addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    refUsers.child("voucherUsed").child("amountDiscount").setValue(currentItem.discountAmount)
                    refUsers.child("voucherUsed").child("voucherId").setValue(currentItem.vouncherID)

                    refUsers.child("voucherUsed").child("amountDiscount").get().addOnSuccessListener {
                        txtDiscount.text = it.value.toString()
                    }

                    layout.visibility = View.GONE
                    voucherList.clear()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        }
    }

    override fun getItemCount(): Int {
        return voucherList.size
    }

class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    val voucherQuantity : TextView = itemView.findViewById(R.id.textViewQuantity)
    val discountAmount : TextView = itemView.findViewById(R.id.textViewDiscountAmount)
    val btnSelect : Button = itemView.findViewById(R.id.btnSelectVoucher1)

}
}

