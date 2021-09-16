package my.edu.tarc.mad_assignment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.edu.tarc.mad_assignment.databinding.ActivityContactUsBinding
import my.edu.tarc.mad_assignment.databinding.ActivityRewardBinding

class ContactUs : AppCompatActivity() {
    private lateinit var binding: ActivityContactUsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)
        binding =  ActivityContactUsBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    fun googleMaps(view: android.view.View) {
        /*val intent = Intent(this, MapsActivity::class.java)
        intent.putExtra("location", "Tunku Abdul Rahman University College")
        startActivity(intent)*/

        try{
            val uri : Uri = Uri.parse("https://www.google.co.in/maps/dir/Your Location/Tarc Kuala Lumpur")
            val intent : Intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }catch(e : ActivityNotFoundException){
            val uri : Uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps")
            val intent : Intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }
    }

    fun phoneCall(view:android.view.View){
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+0341450123"))
        startActivity(intent)
    }

    fun emailIntent(view:android.view.View){
        val email = Intent(Intent.ACTION_SEND)

        email.type = "text/html"
        email.putExtra(Intent.EXTRA_EMAIL, arrayOf("info@taruc.edu.my"))
        email.putExtra(Intent.EXTRA_SUBJECT, "")
        email.putExtra(Intent.EXTRA_TEXT, "")

        email.type = "message/rfc822"

        startActivity(Intent.createChooser(email, "Send Email"))
    }
}