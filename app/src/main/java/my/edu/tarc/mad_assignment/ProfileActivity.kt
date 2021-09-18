package my.edu.tarc.mad_assignment

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import my.edu.tarc.mad_assignment.databinding.ActivityProfileBinding
import my.edu.tarc.mad_assignment.utils.Constants
import my.edu.tarc.mad_assignment.utils.FileUtils
import my.edu.tarc.mad_assignment.utils.SharedPreferencesUtils
import java.io.File

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val TAG = Vehicle_add::class.java.simpleName
    private lateinit var mProgressDialog: ProgressDialog
    var refUsers: DatabaseReference?= null
    var firebaseUser : FirebaseUser? = null
    private lateinit var galleryLl: LinearLayout
    var uri1: Uri? = null
    lateinit var file: File
    var uID: String = ""
    private var dowlImg_profile: String? = null
    private lateinit var requestStoragePermissionLauncher: ActivityResultLauncher<String>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private val IMAGE_CAPTURE = 100
    private lateinit var  imageUri : Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        binding =  ActivityProfileBinding.inflate(layoutInflater)
        firebaseUser = FirebaseAuth.getInstance().currentUser
        refUsers = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid)
        setContentView(binding.root)
        registerStoragePermission()
        registerGalleryLauncher1()

        binding.imageProfile2.setOnClickListener {
            showImageUploadOptions()
        }


        refUsers!!.addValueEventListener(object : ValueEventListener {



            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    val user: customer? = snapshot.getValue(customer::class.java)
                    uID = user!!.getUID().toString()
                    binding.txtID.setText(user!!.getUID())
                    binding.txtName1.setText(user!!.getname())
                    binding.txtEmail1.setText(user!!.getEmail())
                    binding.txtPhone1.setText(user!!.getPhone())
                    binding.txtState1.setText(user!!.getState())
                    //var existState = user!!.getState().toString().uppercase()
                    binding.txtName1.setText(user!!.getname())
                    binding.txtAddress1.setText(user!!.getAddress())
                    Picasso.get().load(user.getProfile()).into(binding.imageProfile2)




                }


            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        binding.txtState1.setOnClickListener{
            binding.spinnerState1.visibility = View.VISIBLE
            binding.tvTo.isVisible = false
            binding.txtState1.visibility = View.GONE

        }

        //stateOption = binding.spinnerEditState as Spinner

       // var spinPosition = stateOption.

       // val options = arrayOf("KEDAH","JOHOR","KELANTAN","MALACCA","PAHANG","PERAK","PERLIS","SABAH","SARAWAK","SELANGOR","PENANG","TERENGGANU","NEGERI SEMBILAN")
//        val myAdap = stateOption.getAdapter()



        binding.btnSave.setOnClickListener{
            val name = binding.txtName1.text.toString()
            val phone = binding.txtPhone1.text.toString()
            //val state = binding.txtState1.text.toString()
            val state2 = binding.txtState1.text.toString().uppercase()
            val address = binding.txtAddress1.text.toString()
            val state1 = binding.spinnerState1.getSelectedItem().toString()
            //val state :String = binding.spinnerEditState.getSelectedItem().toString()

            if(name==""&&phone==""&&address=="")
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
            else if(state1=="New State")
            {
                Toast.makeText(this@ProfileActivity, "Please select state.", Toast.LENGTH_LONG).show()
            }
            else if(address=="")
            {
                Toast.makeText(this@ProfileActivity, "Please insert address.", Toast.LENGTH_LONG).show()
            }
            else
            {
                if(dowlImg_profile == null)
                {
                    refUsers!!.child("name").setValue(name)
                    refUsers!!.child("phone").setValue(phone)
                    refUsers!!.child("state").setValue(state1)
                    refUsers!!.child("address").setValue(address)

                }
                else
                {
                    refUsers!!.child("name").setValue(name)
                    refUsers!!.child("phone").setValue(phone)
                    refUsers!!.child("state").setValue(state1)
                    refUsers!!.child("address").setValue(address)
                    refUsers!!.child("profile").setValue(dowlImg_profile)

                }

                Toast.makeText(this@ProfileActivity, dowlImg_profile.toString(),Toast.LENGTH_LONG).show()

                Toast.makeText(this, "Successfully Updated", Toast.LENGTH_LONG).show()
                val intent = Intent(this, DashBoardActivity::class.java)
                startActivity(intent)


            }

        }



        /*binding.imageProfile2.setOnClickListener{

            getImage.launch("image/")
        }*/

        //change to other activity
        /*val intent = Intent(this, ?Activity::class.java)
        startActivity(intent)*/
    }

    fun CallStoragePermission() {

        if (!Status_checkReadExternalStoragePermission()) {
            requestStoragePermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            viewGallery()
        }
    }

    fun viewGallery() {
        val intentDocument = Intent(Intent.ACTION_GET_CONTENT)
        intentDocument.type = "image/*"
        intentDocument.putExtra(
            Constants.REQUEST_CODE,
            Constants.REQUEST_PHOTO_FROM_GALLERY5
        )
        galleryLauncher.launch(intentDocument)
    }

    private fun showImageUploadOptions() {
        val mDialog = this.let { Dialog(it) }
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog.setContentView(R.layout.dialog_profile_image_option)
        mDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        galleryLl = mDialog.findViewById<View>(R.id.id_gallery_ll) as LinearLayout


        galleryLl.setOnClickListener {
            CallStoragePermission()
            mDialog.dismiss()
        }



        mDialog.setCancelable(true)
        mDialog.show()
        val metrics = resources.displayMetrics
        val width = metrics.widthPixels
        //val height = metrics.heightPixels
        mDialog.window!!.setLayout(
            width,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

    }

    private fun registerStoragePermission() {
        requestStoragePermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                if (granted) {
                    Log.d(TAG, "registerStoragePermission - Storage Permission Granted")
                    viewGallery()
                } else {
                    Log.d(TAG, "registerStoragePermission - Storage Permission NOT Granted")
                    requestStoragePermission()
                }
            }
    }

    private val uploadimgPath = FirebaseStorage.getInstance().getReference()

    fun uploadFront(mContext: Context, imageURI: Uri){
        mProgressDialog = ProgressDialog(mContext)
        mProgressDialog.setMessage("Please wait, image being uploading...")
        mProgressDialog.show()
        val uploadTime = System.currentTimeMillis()
        val uploadTask = uploadimgPath.child(uID).child("/images_profile$uploadTime.png").putFile(imageURI)

        uploadTask.addOnSuccessListener {
            //Log.e(TAG, "Image upload successfully.")
            Toast.makeText(this, " Profile photo added successfully.", Toast.LENGTH_SHORT).show()
            val downloadURLTask = uploadimgPath.child(uID).child("/images_profile$uploadTime.png").downloadUrl
            downloadURLTask.addOnSuccessListener {
                dowlImg_profile = it!!.toString()
                mProgressDialog.dismiss()
            }.addOnFailureListener{
                mProgressDialog.dismiss()
            }
        }.addOnCanceledListener {
            Toast.makeText(this, " Failed to add profile photo.", Toast.LENGTH_SHORT).show()
            mProgressDialog.dismiss()
        }
    }

    private fun registerGalleryLauncher1() {
        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    if (data == null) {
                        return@registerForActivityResult
                    }
                    uri1 = data.data!!
                    var imageLocalPath = File(FileUtils.getPathReal(this, uri1!!))

                    file = imageLocalPath.absoluteFile
                    uploadFront(this, uri1!!)

                    SharedPreferencesUtils.setProfilePath(this, imageLocalPath.absolutePath)
                    Glide.with(this).load(uri1!!)
                        .into(binding.imageProfile2)
                    //binding.imgFront.setScaleType(ImageView.ScaleType.CENTER_CROP)
                }
            }
    }

    private fun requestStoragePermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {

                    Log.d(TAG, "requestStoragePermission - Storage Permission Granted")
                    viewGallery()
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    // This case means user previously denied the permission
                    // So here we can display an explanation to the user
                    // That why exactly we need this permission
                    Log.d(TAG, "requestStoragePermission - Storage Permission NOT Granted")
                    showPermissionAlert(
                        getString(R.string.read_storage_permission_required),
                        getString(R.string.storage_permission_denied),
                        getString(R.string.ok_caps),
                        getString(R.string.cancel_caps)
                    ) { requestStoragePermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE) }
                }
                else -> {
                    // Everything is fine you can simply request the permission

                    showPermissionAlert(
                        getString(R.string.read_storage_permission_required),
                        getString(R.string.storage_permission_denied),
                        getString(R.string.settings_caps),
                        getString(R.string.cancel_caps)
                    ) {
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts(
                            "package",
                            BuildConfig.APPLICATION_ID, null
                        )
                        intent.data = uri
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }

                }
            }
        }
    }

    private fun showPermissionAlert(title: String, message: String, ok: String, cancel: String, function: () -> Unit) {
        val mDialog = this.let { Dialog(it) }
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog.setContentView(R.layout.dialog_permission_alert)
        mDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val mTitleTv = mDialog.findViewById<View>(R.id.id_title_tv) as AppCompatTextView
        mTitleTv.text = title

        val mMessageTv = mDialog.findViewById<View>(R.id.id_message_tv) as AppCompatTextView
        mMessageTv.text = message

        val mNoBtn = mDialog.findViewById<View>(R.id.no_btn) as AppCompatTextView
        mNoBtn.text = cancel

        val mYesBtn = mDialog.findViewById<View>(R.id.yes_btn) as AppCompatTextView
        mYesBtn.text = ok

        mYesBtn.setOnClickListener {
            function.invoke()
            mDialog.dismiss()
        }

        mNoBtn.setOnClickListener { mDialog.dismiss() }

        mDialog.setCancelable(true)
        mDialog.show()
        val metrics = resources.displayMetrics
        val width = metrics.widthPixels
        //val height = metrics.heightPixels
        mDialog.window!!.setLayout(
            width,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    private fun Status_checkReadExternalStoragePermission(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
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