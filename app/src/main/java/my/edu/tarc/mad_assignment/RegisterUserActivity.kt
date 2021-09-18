package my.edu.tarc.mad_assignment

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.number.Precision.increment
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import my.edu.tarc.mad_assignment.databinding.*
import my.edu.tarc.mad_assignment.utils.Constants
import my.edu.tarc.mad_assignment.utils.FileUtils
import my.edu.tarc.mad_assignment.utils.SharedPreferencesUtils
import java.io.File
import java.security.AccessController.getContext

class RegisterUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterUserBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private var firebaseUserID : String = ""
    val firebase : DatabaseReference =  FirebaseDatabase.getInstance().getReference("customer")
    private lateinit var requestStoragePermissionLauncher: ActivityResultLauncher<String>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLl: LinearLayout
    private val TAG = Vehicle_add::class.java.simpleName
    private lateinit var mProgressDialog: ProgressDialog
    var uri1: Uri? = null
    lateinit var file: File
    var uID: String = ""
    private var dowlImg_profile: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_register_user)
        binding =  ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        registerStoragePermission()
        registerGalleryLauncher1()
        binding.imagePicture.setOnClickListener {
            showImageUploadOptions()
        }



    }

    fun registerOnclick(view: android.view.View) {
        var referralCode : String = randomCode()
        val query : Query = firebase.orderByChild("referralCode").equalTo(referralCode)

        query.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               if(snapshot.exists()){
                   registerOnclick(binding.btnRegister)
               }
                else{
                   registerUser(referralCode)
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun referralPoints(){
        val query : Query = firebase.orderByChild("referralCode").equalTo(binding.editTextReferral.text.toString())

        query.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for ( ds : DataSnapshot in snapshot.children){
                        val key = ds.key.toString()
                        firebase.child(key).child("rewards").child("points").get().addOnSuccessListener {
                            var currentPoints = it.value.toString().toInt()
                            firebase.child(key).child("rewards").child("points").setValue(currentPoints + 10)
                        }
                    }
                }
                else{
                    Toast.makeText(this@RegisterUserActivity,"Referral user not exist!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun randomCode(): String = List(6) {
        (('A'..'Z') + ('0'..'9')).random()
    }.joinToString("")

    private fun registerUser(referralCode : String) {
        val name: String = binding.txtName.text.toString()
        val email: String = binding.txtEmail.text.toString()
        val password: String = binding.txtPass.text.toString()
        val repass: String = binding.txtConPass.text.toString()
        val phone : String = binding.txtPhone.text.toString()
        val state :String = binding.spinState.getSelectedItem().toString()
        val address : String = binding.txtAddress.text.toString()

        if(name==""&&password==""&&email==""&&email==""&&phone==""&&address=="")
        {
            val builder = AlertDialog.Builder(this)

            builder.setTitle("Please fill in the detail")
            builder.setPositiveButton(R.string.ok,
                DialogInterface.OnClickListener { dialog, i -> dialog.cancel() })
            builder.show()
        }
        else if(name=="")
        {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Please insert your name.")
            builder.setPositiveButton(R.string.ok,
                DialogInterface.OnClickListener { dialog, i -> dialog.cancel() })
            builder.show()
        }
        else if(password=="")
        {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Please insert password.")
            builder.setPositiveButton(R.string.ok,
                DialogInterface.OnClickListener { dialog, i -> dialog.cancel() })
            builder.show()
        }
        else if(!repass.equals(password))
        {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Password not match.")
            builder.setPositiveButton(R.string.ok,
                DialogInterface.OnClickListener { dialog, i -> dialog.cancel() })
            builder.show()
        }
        else if (email=="")
        {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Please insert email.")
            builder.setPositiveButton(R.string.ok,
                DialogInterface.OnClickListener { dialog, i -> dialog.cancel() })
            builder.show()
        }
        else if(phone=="")
        {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Please insert Phone Number.")
            builder.setPositiveButton(R.string.ok,
                DialogInterface.OnClickListener { dialog, i -> dialog.cancel() })
            builder.show()
        }
        else if(binding.spinState.selectedItemPosition==0)
        {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Please select state.")
            builder.setPositiveButton(R.string.ok,
                DialogInterface.OnClickListener { dialog, i -> dialog.cancel() })
            builder.show()
        }
        else if(address=="")
        {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Please insert your address")
            builder.setPositiveButton(R.string.ok,
                DialogInterface.OnClickListener { dialog, i -> dialog.cancel() })
            builder.show()
        }

        else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                    task ->
                if(task.isSuccessful){
                    firebaseUserID = mAuth.currentUser!!.uid
                    refUsers = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUserID)
                    val userHashMap = HashMap<String, Any>()
                    if(dowlImg_profile == null)
                    {
                        //val userHashMap = HashMap<String, Any>()
                        userHashMap["uid"] = firebaseUserID
                        userHashMap["name"] = name
                        userHashMap["password"] = password
                        userHashMap["email"] = email
                        userHashMap["phone"] = phone
                        userHashMap["state"] = state
                        userHashMap["address"] = address
                        userHashMap["profile"] = "https://firebasestorage.googleapis.com/v0/b/mad-assignment-56b04.appspot.com/o/avatar.jpg?alt=media&token=5b961f5d-4450-4b26-a05b-6ac942f3d855"
                        userHashMap["cover"] = "https://firebasestorage.googleapis.com/v0/b/mad-assignment-56b04.appspot.com/o/cover.jpg?alt=media&token=170a50c3-60a3-4e1b-ab5a-7b74ee997c52"
                        userHashMap["status"] = "offline"
                        userHashMap["referralCode"] = referralCode
                    }else{
                        //var userHashMap = HashMap<String, Any>()
                        userHashMap["uid"] = firebaseUserID
                        userHashMap["name"] = name
                        userHashMap["password"] = password
                        userHashMap["email"] = email
                        userHashMap["phone"] = phone
                        userHashMap["state"] = state
                        userHashMap["address"] = address
                        userHashMap["profile"] = dowlImg_profile.toString()
                        userHashMap["cover"] = "https://firebasestorage.googleapis.com/v0/b/mad-assignment-56b04.appspot.com/o/cover.jpg?alt=media&token=170a50c3-60a3-4e1b-ab5a-7b74ee997c52"
                        userHashMap["status"] = "offline"
                        userHashMap["referralCode"] = referralCode
                    }


                    if (binding.editTextReferral.text!=null){
                        referralPoints()
                    }

                    firebase.child(firebaseUserID).child("rewards").child("points").setValue(0)
                    refUsers.updateChildren(userHashMap)
                        .addOnCompleteListener {
                                task2 ->
                            if(task2.isSuccessful){


                                mAuth.currentUser?.sendEmailVerification()
                                    ?.addOnCompleteListener { task ->
                                        if(task.isSuccessful){

                                            Toast.makeText(this@RegisterUserActivity, "Link Successfully sent, Please verify your email to login.", Toast.LENGTH_LONG).show()

                                        }


                                    }
                                val intent = Intent(this, LoginActivity::class.java)
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                startActivity(intent)
                            }

                        }





                }
                else{

                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Error Message:" + task.exception!!.message.toString())
                    builder.setPositiveButton(R.string.ok,
                        DialogInterface.OnClickListener { dialog, i -> dialog.cancel() })
                    builder.show()
                }
            }
        }

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
        val uploadTask = uploadimgPath.child("profile").child("/images_profile$uploadTime.png").putFile(imageURI)

        uploadTask.addOnSuccessListener {
            //Log.e(TAG, "Image upload successfully.")
            Toast.makeText(this, " Profile photo added successfully.", Toast.LENGTH_SHORT).show()
            val downloadURLTask = uploadimgPath.child("profile").child("/images_profile$uploadTime.png").downloadUrl
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
                        .into(binding.imagePicture)
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
}