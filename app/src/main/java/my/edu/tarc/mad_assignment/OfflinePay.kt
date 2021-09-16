package my.edu.tarc.mad_assignment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.zxing.WriterException
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import my.edu.tarc.mad_assignment.databinding.ActivityOfflinePayBinding
import my.edu.tarc.mad_assignment.databinding.ActivityOfflinePaymentBinding
import my.edu.tarc.mad_assignment.databinding.ActivityPaymentBinding
import java.lang.Exception
import kotlin.random.Random

class OfflinePay : AppCompatActivity() {


        private lateinit var binding: ActivityOfflinePayBinding

        private lateinit var qrCodeIV: ImageView
        private lateinit var dataEdt: TextView


        var bitmap : Bitmap? = null
        var qrgEnCoder : QRGEncoder? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_offline_pay)

            binding = ActivityOfflinePayBinding.inflate(layoutInflater)
            setContentView(binding.root)

            //transID = Random.nextInt(1000000000)
            val transID = intent.getStringExtra("transID")
            binding.textViewTransactionID.text = "${transID}"

            val toPay = intent.getStringExtra("toPay")
            binding.textViewTotal.text = "RM ${toPay}"
            binding.textViewTotal2.text = "RM ${toPay}"


            //set find ID
            qrCodeIV = findViewById(R.id.imgViewQr)
            dataEdt = findViewById(R.id.textViewTransactionID)




                 val manager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
                 val display = manager.defaultDisplay
                 val point = Point()
                 display.getSize(point)
                 val width = point.x
                 val height = point.y
                 var dimen = if(width<height) width else height

                 dimen = dimen *3/4

                 qrgEnCoder = QRGEncoder(dataEdt.text.toString(), null,QRGContents.Type.TEXT,dimen)

                 try {
                     bitmap = qrgEnCoder!!.encodeAsBitmap()
                     qrCodeIV.setImageBitmap(bitmap)
                 }catch (e: Exception)
                 {
                     Log.e("Tag",e.toString())
                 }





        }

}