package my.edu.tarc.mad_assignment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode

class QRCodeScanner : AppCompatActivity() {
    private lateinit var qrCodeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode_scanner)

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 123)
        }
        else{
            scanQR()
        }
    }

    private fun scanQR() {
        val qrScanner : CodeScannerView = findViewById(R.id.QR_Scanner)
        var referralCode : String
        qrCodeScanner = CodeScanner(this, qrScanner)
        qrCodeScanner.camera = CodeScanner.CAMERA_BACK
        qrCodeScanner.formats = CodeScanner.ALL_FORMATS
        qrCodeScanner.autoFocusMode = AutoFocusMode.SAFE
        qrCodeScanner.scanMode = ScanMode.SINGLE
        qrCodeScanner.isFlashEnabled = false
        qrCodeScanner.isAutoFocusEnabled = true

        qrCodeScanner.decodeCallback = DecodeCallback {
          // referralCode = it.text.toString()
            val qrReferral = Intent()
            qrReferral.putExtra("qrReferral", it.text.toString())

            setResult(Activity.RESULT_OK, qrReferral)
            finish()
        }

        qrCodeScanner.errorCallback = ErrorCallback {
            runOnUiThread {
                Toast.makeText(
                    this,
                    "Camera Initialization error : ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        qrScanner.setOnClickListener{
            qrCodeScanner.startPreview()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 123){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show()
                scanQR()
            }else{
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if(::qrCodeScanner.isInitialized){
            qrCodeScanner?.startPreview()
        }
    }

    override fun onPause() {
        if(::qrCodeScanner.isInitialized){
            qrCodeScanner?.releaseResources()
        }
        super.onPause()
    }
}