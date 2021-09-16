package my.edu.tarc.mad_assignment

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import my.edu.tarc.mad_assignment.databinding.ActivityPaymentBinding
import my.edu.tarc.mad_assignment.databinding.ActivityProfileBinding
import java.io.ByteArrayOutputStream

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    var refUsers: DatabaseReference?= null
    var firebaseUser : FirebaseUser? = null
    private val IMAGE_CAPTURE = 100
    private lateinit var  imageUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        binding =  ActivityProfileBinding.inflate(layoutInflater)
        firebaseUser = FirebaseAuth.getInstance().currentUser
        refUsers = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid)
        setContentView(binding.root)
        /*val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                binding.imageProfile2.setImageURI(it)
                refUsers!!.child("profile").setValue(it)

            }

        )*/

        refUsers!!.addValueEventListener(object : ValueEventListener {



            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    val user: customer? = snapshot.getValue(customer::class.java)
                    binding.txtID.setText(user!!.getUID())
                    binding.txtName1.setText(user!!.getname())
                    binding.txtEmail1.setText(user!!.getEmail())
                    binding.txtPhone1.setText(user!!.getPhone())
                    binding.txtState1.setText(user!!.getState())
                    binding.txtName1.setText(user!!.getname())
                    binding.txtAddress1.setText(user!!.getAddress())
                    Picasso.get().load(user.getProfile()).into(binding.imageProfile2)





                }


            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        binding.btnSave.setOnClickListener{
            val name = binding.txtName1.text.toString()
            val phone = binding.txtPhone1.text.toString()
            val state = binding.txtState1.text.toString()
            val state2 = binding.txtState1.text.toString().uppercase()
            val address = binding.txtAddress1.text.toString()
            var stateArray = arrayOf("KEDAH","JOHOR","KELANTAN","MALACCA","PAHANG","PERAK","PERLIS","SABAH","SARAWAK","SELANGOR","PENANG","TERENGGANU","NEGERI SEMBILAN")


            if(name==""&&phone==""&&state==""&&address=="")
            {
                Toast.makeText(this@ProfileActivity, "Please fill in the detail", Toast.LENGTH_LONG).show()
            }
            else if(name=="")
            {
                Toast.makeText(this@ProfileActivity, "Please insert your name.", Toast.LENGTH_LONG).show()
            }
            else if(phone=="")
            {
                Toast.makeText(this@ProfileActivity, "Please insert phone number.", Toast.LENGTH_LONG).show()
            }
            else if(state=="")
            {
                Toast.makeText(this@ProfileActivity, "Please insert state.", Toast.LENGTH_LONG).show()
            }
            else if(address=="")
            {
                Toast.makeText(this@ProfileActivity, "Please insert address.", Toast.LENGTH_LONG).show()
            }
            else
            {
               /* for(item in stateArray)
                {*/

                    if(!state2.equals("KEDAH")&&!state2.equals("JOHOR")&&!state2.equals("KELANTAN")&&!state2.equals("MALACCA")&&!state2.equals("PAHANG")
                        &&!state2.equals("PERAK")&&!state2.equals("PERLIS")&&!state2.equals("SABAH")&&!state2.equals("SARAWAK")&&!state2.equals("SELANGOR")
                        &&!state2.equals("PENANG")&&!state2.equals("TERENGGANU")&&!state2.equals("NEGERI SEMBILAN")) {
                        Toast.makeText(this@ProfileActivity, "Please insert valid state.", Toast.LENGTH_LONG).show()
                    }
                    else {

                    refUsers!!.child("name").setValue(name)
                    refUsers!!.child("phone").setValue(phone)
                    refUsers!!.child("state").setValue(state)
                    refUsers!!.child("address").setValue(address)

                    Toast.makeText(this, "Successfully Updated", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, DashBoardActivity::class.java)
                        startActivity(intent)
                    }
                /*}*/
            }

        }

        /*binding.imageProfile2.setOnClickListener{

            getImage.launch("image/")
        }*/

        //change to other activity
        /*val intent = Intent(this, ?Activity::class.java)
        startActivity(intent)*/
    }

    /*private fun getPicture(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { pictureIntent ->
            pictureIntent.resolveActivity(this?.packageManager!!)?.also {
                startActivityForResult(pictureIntent, IMAGE_CAPTURE)

            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == IMAGE_CAPTURE && resultCode == RESULT_OK){
            val imageBitmap = data?.extras?.get("data") as Bitmap

            uploadImageAndSaveURL(imageBitmap)

        }

    }

    private fun uploadImageAndSaveURL(imageBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val storageRef = FirebaseStorage.getInstance()
            .reference
            .child("profile${FirebaseAuth.getInstance().currentUser?.uid}")
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val image = baos.toByteArray()
        val upload = storageRef.putBytes(image)


        upload.addOnCompleteListener{ uploadTask ->
            if(uploadTask.isSuccessful){
                storageRef.downloadUrl.addOnCompleteListener { urlTask ->
                    urlTask.result?.let{
                        imageUri = it
                        binding.imageProfile2.setImageBitmap(imageBitmap)
                    }
                }
            }else{
                uploadTask.exception?.let{
                    Toast.makeText(this,it.message!!, Toast.LENGTH_LONG).show()

                }

            }


        }
    }*/


}