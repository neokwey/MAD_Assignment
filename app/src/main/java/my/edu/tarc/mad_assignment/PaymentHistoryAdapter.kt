package my.edu.tarc.mad_assignment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class PaymentHistoryAdapter(
    private val context: android.content.Context,
    private val payHistory: List<PaymentHistoryClass>

) : RecyclerView.Adapter<PaymentHistoryAdapter.ViewHolder>() {
    var refUsers: DatabaseReference?= null
    var firebaseUser : FirebaseUser? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PaymentHistoryAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.pay_history_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentitem = payHistory[position]
        holder.txtTransId.text = currentitem.transactionID.toString()
        holder.txtTotalPay.setText("RM " + currentitem.totalAmount + "0")
        holder.txtDate.setText("Date: "+currentitem.date)
        holder.txtTime.setText("Time: "+currentitem.time)
        holder.txtDiscount.setText("Discount: RM "+currentitem.discount.toString()+ "0")
        holder.txtPayMethod.setText("Payment Method: "+currentitem.paymentMethod)
        holder.txtTotalAmount.setText("Total Amount: RM "+currentitem.totalAmount.toString()+ "0")
        holder.txtStatus.setText("Status: "+currentitem.status)

        holder.imgBtnExpand.setOnClickListener {


            holder.layoutDetails.visibility = View.VISIBLE
            holder.imgbtnCollapse.visibility = View.VISIBLE
            holder.imgBtnExpand.visibility = View.INVISIBLE

            holder.imgbtnCollapse.setOnClickListener {
                holder.imgbtnCollapse.visibility = View.INVISIBLE
                holder.layoutDetails.visibility = View.GONE
                holder.imgBtnExpand.visibility = View.VISIBLE
            }


        }
    }

    override fun getItemCount(): Int {
        return payHistory.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtTransId: TextView = itemView.findViewById(R.id.textViewTransID)
        var txtTotalPay: TextView = itemView.findViewById(R.id.textViewTotalPay)
        var txtDate: TextView = itemView.findViewById(R.id.textViewDate)
        var txtTime: TextView = itemView.findViewById(R.id.textViewTime)
        var txtTotalAmount: TextView = itemView.findViewById(R.id.textViewTotalAmount)
        var txtDiscount: TextView = itemView.findViewById(R.id.textViewDiscount)
        var txtPayMethod: TextView = itemView.findViewById(R.id.textViewPayMethod)
        var txtStatus: TextView = itemView.findViewById(R.id.textViewSatus)
        var imgBtnExpand: ImageView = itemView.findViewById(R.id.imgbtnExpand)
        var layoutDetails: ConstraintLayout = itemView.findViewById(R.id.layoutDetails)
        var imgbtnCollapse : ImageView = itemView.findViewById(R.id.imgbtnCollapse)

        @SuppressLint("ResourceType")
        var imgCollapse: View? = itemView.findViewById(R.drawable.collapse)


    }
}