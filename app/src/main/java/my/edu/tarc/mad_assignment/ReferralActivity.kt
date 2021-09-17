package my.edu.tarc.mad_assignment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import my.edu.tarc.mad_assignment.databinding.ActivityReferralBinding
import my.edu.tarc.mad_assignment.databinding.ActivityRewardBinding

class ReferralActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReferralBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_referral)
        binding =  ActivityReferralBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tnc : Array<String> = resources.getStringArray(R.array.TnC)

        for(items in tnc){
            binding.textViewTerms.text = binding.textViewTerms.text.toString() + "\n\n" + items
        }

        binding.buttonCopy.setOnClickListener {
            val clipboard : ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip : ClipData = ClipData.newPlainText("Referral Code", binding.textViewCode.text.toString())
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this@ReferralActivity, "Copied to Clipboard", Toast.LENGTH_SHORT).show()
        }

        val code = intent.getStringExtra("code")
        binding.textViewCode.text = code

        val writer = QRCodeWriter()

        try{
            val bitMatrix : BitMatrix = writer.encode(binding.textViewCode.text.toString(), BarcodeFormat.QR_CODE, 350,350)
            val bmp = Bitmap.createBitmap(bitMatrix.width, bitMatrix.height, Bitmap.Config.RGB_565)

            for(x in 0 until bitMatrix.width){
                for(y in 0 until bitMatrix.height){
                    bmp.setPixel(x, y, if(bitMatrix[x,y]) Color.BLACK else Color.WHITE)
                }
            }

            binding.ImageViewQR.setImageBitmap(bmp)
        }catch (e : WriterException){
            e.printStackTrace()
        }

        val qrCode = QRCodeWriter()

        //change to other activity
        /*val intent = Intent(this, ?Activity::class.java)
        startActivity(intent)*/
    }
}