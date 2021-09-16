package my.edu.tarc.mad_assignment

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import my.edu.tarc.mad_assignment.databinding.ActivityVehicleAddBinding
import my.edu.tarc.mad_assignment.utils.Constants
import my.edu.tarc.mad_assignment.utils.FileUtils
import my.edu.tarc.mad_assignment.utils.SharedPreferencesUtils
import java.io.File
import java.util.*

class Vehicle_add : AppCompatActivity() {
    private val TAG = Vehicle_add::class.java.simpleName
    private lateinit var mProgressDialog: ProgressDialog
    private lateinit var binding: ActivityVehicleAddBinding
    var refUsers: DatabaseReference?= null
    var refUsers2: DatabaseReference?= null
    var firebaseUser : FirebaseUser? = null

    private lateinit var galleryLl: LinearLayout
    private lateinit var cameraLl: LinearLayout

    lateinit var file: File
    lateinit var file2: File
    lateinit var file3: File
    lateinit var file4: File
    lateinit var imageBitmap: Bitmap
    lateinit var imageBitmap2: Bitmap
    lateinit var imageBitmap3: Bitmap
    lateinit var imageBitmap4: Bitmap
    var uri1: Uri? = null
    var uri2: Uri? = null
    var uri3: Uri? = null
    var uri4: Uri? = null

    private lateinit var dowlImg_front: String
    private lateinit var dowlImg_back: String
    private lateinit var dowlImg_left: String
    private lateinit var dowlImg_right: String
    var carID: String = ""
    var uID: String = ""

    private lateinit var requestStoragePermissionLauncher: ActivityResultLauncher<String>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher2: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher3: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher4: ActivityResultLauncher<Intent>

    private lateinit var requestCameraPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var cameraLauncher2: ActivityResultLauncher<Intent>
    private lateinit var cameraLauncher3: ActivityResultLauncher<Intent>
    private lateinit var cameraLauncher4: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_add)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
        binding =  ActivityVehicleAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseUser = FirebaseAuth.getInstance().currentUser
        refUsers2 = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid)
        refUsers2!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user: customer? = snapshot.getValue(customer::class.java)
                    uID = user!!.getname().toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        refUsers = FirebaseDatabase.getInstance().reference.child("customer").child(firebaseUser!!.uid).child("vehicle")
        refUsers!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    var count: Int = snapshot.childrenCount.toInt()
                    var newID: Int = count + 1
                    carID = "car$newID"
                } else {
                    var newID: Int = 1
                    carID = "car$newID"
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        registerStoragePermission()
        registerGalleryLauncher1()
        registerGalleryLauncher2()
        registerGalleryLauncher3()
        registerGalleryLauncher4()
        registerCameraPermission()
        registerCameraLauncher1()
        registerCameraLauncher2()
        registerCameraLauncher3()
        registerCameraLauncher4()

        binding.imgFront.setOnClickListener {
            showImageUploadOptions()
        }

        binding.imgBack.setOnClickListener {
            showImageUploadOptions2()
        }

        binding.imgLeft.setOnClickListener {
            showImageUploadOptions3()
        }

        binding.imgRight.setOnClickListener {
            showImageUploadOptions4()
        }

        binding.btnSave.setOnClickListener {
            registerVehicle()
        }
    }

    private fun registerVehicle(){
        refUsers!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val vehiclebrand: String = binding.etxtBrand.text.toString()
                    val vehiclecc: String = binding.etxtCC.text.toString()
                    val vehiclemodelName: String = binding.etxtModel.text.toString()
                    val vehicleplatenum: String = binding.etxtPlatenum.text.toString()
                    val vehicletype: String = binding.etxtType.text.toString()
                    val vehicleyear: String = binding.etxtYear.text.toString()

                    if (vehiclebrand==""){
                        Toast.makeText(this@Vehicle_add, "Please fill in the Vehicle brand.", Toast.LENGTH_SHORT).show()
                    }else if (vehiclecc==""){
                        Toast.makeText(this@Vehicle_add, "Please fill in the CC.", Toast.LENGTH_LONG).show()
                    }else if (vehiclemodelName==""){
                        Toast.makeText(this@Vehicle_add, "Please fill in the Model Name.", Toast.LENGTH_LONG).show()
                    }else if (vehicleplatenum==""){
                        Toast.makeText(this@Vehicle_add, "Please fill in the Plate Number.", Toast.LENGTH_LONG).show()
                    }else if (vehicletype==""){
                        Toast.makeText(this@Vehicle_add, "Please fill in the Vehicle type.", Toast.LENGTH_LONG).show()
                    }else if (vehicleyear==""){
                        Toast.makeText(this@Vehicle_add, "Please fill in the Vehicle year.", Toast.LENGTH_LONG).show()
                    }else{
                        val carVehicle = Vehicle(dowlImg_back,vehiclebrand,carID,vehicletype,vehicleyear,vehiclemodelName,vehiclecc,dowlImg_front,dowlImg_left,vehicleplatenum,dowlImg_right)
                        refUsers!!.child(carID).setValue(carVehicle).addOnSuccessListener {
                            binding.etxtBrand.text.clear()
                            binding.etxtCC.text.clear()
                            binding.etxtModel.text.clear()
                            binding.etxtPlatenum.text.clear()
                            binding.etxtType.text.clear()
                            binding.etxtYear.text.clear()
                            binding.imgFront.setImageDrawable(ContextCompat.getDrawable(this@Vehicle_add, R.drawable.ic_menu_add))
                            binding.imgBack.setImageDrawable(ContextCompat.getDrawable(this@Vehicle_add, R.drawable.ic_menu_add))
                            binding.imgLeft.setImageDrawable(ContextCompat.getDrawable(this@Vehicle_add, R.drawable.ic_menu_add))
                            binding.imgRight.setImageDrawable(ContextCompat.getDrawable(this@Vehicle_add, R.drawable.ic_menu_add))
                            Toast.makeText(this@Vehicle_add, " Vehicle added successfully.", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {
                            Toast.makeText(this@Vehicle_add, " Failed to add vehicle.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    val vehiclebrand: String = binding.etxtBrand.text.toString()
                    val vehiclecc: String = binding.etxtCC.text.toString()
                    val vehiclemodelName: String = binding.etxtModel.text.toString()
                    val vehicleplatenum: String = binding.etxtPlatenum.text.toString()
                    val vehicletype: String = binding.etxtType.text.toString()
                    val vehicleyear: String = binding.etxtYear.text.toString()

                    if (vehiclebrand==""){
                        Toast.makeText(this@Vehicle_add, "Please fill in the Vehicle brand.", Toast.LENGTH_SHORT).show()
                    }else if (vehiclecc==""){
                        Toast.makeText(this@Vehicle_add, "Please fill in the CC.", Toast.LENGTH_LONG).show()
                    }else if (vehiclemodelName==""){
                        Toast.makeText(this@Vehicle_add, "Please fill in the Model Name.", Toast.LENGTH_LONG).show()
                    }else if (vehicleplatenum==""){
                        Toast.makeText(this@Vehicle_add, "Please fill in the Plate Number.", Toast.LENGTH_LONG).show()
                    }else if (vehicletype==""){
                        Toast.makeText(this@Vehicle_add, "Please fill in the Vehicle type.", Toast.LENGTH_LONG).show()
                    }else if (vehicleyear==""){
                        Toast.makeText(this@Vehicle_add, "Please fill in the Vehicle year.", Toast.LENGTH_LONG).show()
                    }else{
                        val carVehicle = Vehicle(dowlImg_back,vehiclebrand,carID,vehicletype,vehicleyear,vehiclemodelName,vehiclecc,dowlImg_front,dowlImg_left,vehicleplatenum,dowlImg_right)
                        refUsers!!.child(carID).setValue(carVehicle).addOnSuccessListener {
                            binding.etxtBrand.text.clear()
                            binding.etxtCC.text.clear()
                            binding.etxtModel.text.clear()
                            binding.etxtPlatenum.text.clear()
                            binding.etxtType.text.clear()
                            binding.etxtYear.text.clear()
                            binding.imgFront.setImageDrawable(ContextCompat.getDrawable(this@Vehicle_add, R.drawable.ic_menu_add))
                            binding.imgBack.setImageDrawable(ContextCompat.getDrawable(this@Vehicle_add, R.drawable.ic_menu_add))
                            binding.imgLeft.setImageDrawable(ContextCompat.getDrawable(this@Vehicle_add, R.drawable.ic_menu_add))
                            binding.imgRight.setImageDrawable(ContextCompat.getDrawable(this@Vehicle_add, R.drawable.ic_menu_add))
                            Toast.makeText(this@Vehicle_add, " Vehicle added successfully.", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {
                            Toast.makeText(this@Vehicle_add, " Failed to add vehicle.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    fun CallCameraPermission() {
        if (!Status_checkCameraPermission()) {
            requestCameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        } else {
            openCamera()
        }
    }

    fun CallCameraPermission2() {
        if (!Status_checkCameraPermission()) {
            requestCameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        } else {
            openCamera2()
        }
    }

    fun CallCameraPermission3() {
        if (!Status_checkCameraPermission()) {
            requestCameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        } else {
            openCamera3()
        }
    }

    fun CallCameraPermission4() {
        if (!Status_checkCameraPermission()) {
            requestCameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        } else {
            openCamera4()
        }
    }

    fun CallStoragePermission() {

        if (!Status_checkReadExternalStoragePermission()) {
            requestStoragePermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            viewGallery()
        }
    }

    fun CallStoragePermission2() {

        if (!Status_checkReadExternalStoragePermission()) {
            requestStoragePermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            viewGallery2()
        }
    }

    fun CallStoragePermission3() {

        if (!Status_checkReadExternalStoragePermission()) {
            requestStoragePermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            viewGallery3()
        }
    }

    fun CallStoragePermission4() {

        if (!Status_checkReadExternalStoragePermission()) {
            requestStoragePermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            viewGallery4()
        }
    }

    fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent.putExtra(
            Constants.REQUEST_CODE,
            Constants.REQUEST_PERMISSIONS_REQUEST_CODE_CAMERA
        )
        cameraLauncher.launch(takePictureIntent)
    }

    fun openCamera2() {
        val takePictureIntent2 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent2.putExtra(
            Constants.REQUEST_CODE,
            Constants.REQUEST_PERMISSIONS_REQUEST_CODE_CAMERA2
        )
        cameraLauncher2.launch(takePictureIntent2)
    }

    fun openCamera3() {
        val takePictureIntent3 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent3.putExtra(
            Constants.REQUEST_CODE,
            Constants.REQUEST_PERMISSIONS_REQUEST_CODE_CAMERA3
        )
        cameraLauncher3.launch(takePictureIntent3)
    }

    fun openCamera4() {
        val takePictureIntent4 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent4.putExtra(
            Constants.REQUEST_CODE,
            Constants.REQUEST_PERMISSIONS_REQUEST_CODE_CAMERA4
        )
        cameraLauncher4.launch(takePictureIntent4)
    }

    fun viewGallery() {
        val intentDocument = Intent(Intent.ACTION_GET_CONTENT)
        intentDocument.type = "image/*"
        intentDocument.putExtra(
            Constants.REQUEST_CODE,
            Constants.REQUEST_PHOTO_FROM_GALLERY
        )
        galleryLauncher.launch(intentDocument)
    }

    fun viewGallery2() {
        val intentDocument2 = Intent(Intent.ACTION_GET_CONTENT)
        intentDocument2.type = "image/*"
        intentDocument2.putExtra(
            Constants.REQUEST_CODE,
            Constants.REQUEST_PHOTO_FROM_GALLERY2
        )
        galleryLauncher2.launch(intentDocument2)
    }

    fun viewGallery3() {
        val intentDocument3 = Intent(Intent.ACTION_GET_CONTENT)
        intentDocument3.type = "image/*"
        intentDocument3.putExtra(
            Constants.REQUEST_CODE,
            Constants.REQUEST_PHOTO_FROM_GALLERY3
        )
        galleryLauncher3.launch(intentDocument3)
    }

    fun viewGallery4() {
        val intentDocument4 = Intent(Intent.ACTION_GET_CONTENT)
        intentDocument4.type = "image/*"
        intentDocument4.putExtra(
            Constants.REQUEST_CODE,
            Constants.REQUEST_PHOTO_FROM_GALLERY4
        )
        galleryLauncher4.launch(intentDocument4)
    }

    private fun showImageUploadOptions() {
        val mDialog = this.let { Dialog(it) }
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog.setContentView(R.layout.dialog_profile_image_option)
        mDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        galleryLl = mDialog.findViewById<View>(R.id.id_gallery_ll) as LinearLayout
        cameraLl = mDialog.findViewById<View>(R.id.id_camera_ll) as LinearLayout

        galleryLl.setOnClickListener {
            CallStoragePermission()
            mDialog.dismiss()
        }

        cameraLl.setOnClickListener {
            CallCameraPermission()
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

    private fun showImageUploadOptions2() {
        val mDialog = this.let { Dialog(it) }
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog.setContentView(R.layout.dialog_profile_image_option)
        mDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        galleryLl = mDialog.findViewById<View>(R.id.id_gallery_ll) as LinearLayout
        cameraLl = mDialog.findViewById<View>(R.id.id_camera_ll) as LinearLayout

        galleryLl.setOnClickListener {
            CallStoragePermission2()
            mDialog.dismiss()
        }

        cameraLl.setOnClickListener {
            CallCameraPermission2()
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

    private fun showImageUploadOptions3() {
        val mDialog = this.let { Dialog(it) }
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog.setContentView(R.layout.dialog_profile_image_option)
        mDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        galleryLl = mDialog.findViewById<View>(R.id.id_gallery_ll) as LinearLayout
        cameraLl = mDialog.findViewById<View>(R.id.id_camera_ll) as LinearLayout

        galleryLl.setOnClickListener {
            CallStoragePermission3()
            mDialog.dismiss()
        }

        cameraLl.setOnClickListener {
            CallCameraPermission3()
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

    private fun showImageUploadOptions4() {
        val mDialog = this.let { Dialog(it) }
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog.setContentView(R.layout.dialog_profile_image_option)
        mDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        galleryLl = mDialog.findViewById<View>(R.id.id_gallery_ll) as LinearLayout
        cameraLl = mDialog.findViewById<View>(R.id.id_camera_ll) as LinearLayout

        galleryLl.setOnClickListener {
            CallStoragePermission4()
            mDialog.dismiss()
        }

        cameraLl.setOnClickListener {
            CallCameraPermission4()
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

    private fun registerCameraPermission() {
        requestCameraPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                if (granted) {
                    Log.d(TAG, "registerCameraPermission - Camera Permission Granted")
                    openCamera()
                } else {
                    Log.d(TAG, "registerCameraPermission - Camera Permission NOT Granted")
                    requestCameraPermission()
                }
            }
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
        val uploadTask = uploadimgPath.child(uID).child(carID).child("/images_front$uploadTime.png").putFile(imageURI)

        uploadTask.addOnSuccessListener {
            //Log.e(TAG, "Image upload successfully.")
            Toast.makeText(this, " Vehicle photo added successfully.", Toast.LENGTH_SHORT).show()
            val downloadURLTask = uploadimgPath.child(uID).child(carID).child("/images_front$uploadTime.png").downloadUrl
            downloadURLTask.addOnSuccessListener {
                dowlImg_front = it!!.toString()
                mProgressDialog.dismiss()
            }.addOnFailureListener{
                mProgressDialog.dismiss()
            }
        }.addOnCanceledListener {
            Toast.makeText(this, " Failed to add vehicle photo.", Toast.LENGTH_SHORT).show()
            mProgressDialog.dismiss()
        }
    }

    fun uploadBack(mContext: Context, imageURI: Uri){
        mProgressDialog = ProgressDialog(mContext)
        mProgressDialog.setMessage("Please wait, image being uploading...")
        mProgressDialog.show()
        val uploadTime = System.currentTimeMillis()
        val uploadTask = uploadimgPath.child(uID).child(carID).child("/images_back$uploadTime.png").putFile(imageURI)
        uploadTask.addOnSuccessListener {
            //Log.e(TAG, "Image upload successfully.")
            Toast.makeText(this, " Vehicle photo added successfully.", Toast.LENGTH_SHORT).show()
            val downloadURLTask = uploadimgPath.child(uID).child(carID).child("/images_back$uploadTime.png").downloadUrl
            downloadURLTask.addOnSuccessListener {
                dowlImg_back = it!!.toString()
                mProgressDialog.dismiss()
            }.addOnFailureListener{
                mProgressDialog.dismiss()
            }
        }.addOnCanceledListener {
            Toast.makeText(this, " Failed to add vehicle photo.", Toast.LENGTH_SHORT).show()
            mProgressDialog.dismiss()
        }
    }

    fun uploadLeft(mContext: Context, imageURI: Uri){
        mProgressDialog = ProgressDialog(mContext)
        mProgressDialog.setMessage("Please wait, image being uploading...")
        mProgressDialog.show()
        val uploadTime = System.currentTimeMillis()
        val uploadTask = uploadimgPath.child(uID).child(carID).child("/images_left$uploadTime.png").putFile(imageURI)
        uploadTask.addOnSuccessListener {
            //Log.e(TAG, "Image upload successfully.")
            Toast.makeText(this, " Vehicle photo added successfully.", Toast.LENGTH_SHORT).show()
            val downloadURLTask = uploadimgPath.child(uID).child(carID).child("/images_left$uploadTime.png").downloadUrl
            downloadURLTask.addOnSuccessListener {
                dowlImg_left = it!!.toString()
                mProgressDialog.dismiss()
            }.addOnFailureListener{
                mProgressDialog.dismiss()
            }
        }.addOnCanceledListener {
            Toast.makeText(this, " Failed to add vehicle photo.", Toast.LENGTH_SHORT).show()
            mProgressDialog.dismiss()
        }
    }

    fun uploadRight(mContext: Context, imageURI: Uri){
        mProgressDialog = ProgressDialog(mContext)
        mProgressDialog.setMessage("Please wait, image being uploading...")
        mProgressDialog.show()
        val uploadTime = System.currentTimeMillis()
        val uploadTask = uploadimgPath.child(uID).child(carID).child("/images_right$uploadTime.png").putFile(imageURI)
        uploadTask.addOnSuccessListener {
            //Log.e(TAG, "Image upload successfully.")
            Toast.makeText(this, " Vehicle photo added successfully.", Toast.LENGTH_SHORT).show()
            val downloadURLTask = uploadimgPath.child(uID).child(carID).child("/images_right$uploadTime.png").downloadUrl
            downloadURLTask.addOnSuccessListener {
                dowlImg_right = it!!.toString()
                mProgressDialog.dismiss()
            }.addOnFailureListener{
                mProgressDialog.dismiss()
            }
        }.addOnCanceledListener {
            Toast.makeText(this, " Failed to add vehicle photo.", Toast.LENGTH_SHORT).show()
            mProgressDialog.dismiss()
        }
    }

    private fun registerCameraLauncher1() {
        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    if (data == null) {
                        return@registerForActivityResult
                    }
                    val extras = data.extras
                    imageBitmap = extras!!["data"] as Bitmap
                    file = FileUtils.createFile(
                        this,
                        getString(R.string.app_name),
                        "front_car_image.png"
                    )
                    val imageLocalPath = FileUtils.saveImageToInternalStorage(file, imageBitmap)
                    uploadFront(this, data.data!!)

                    SharedPreferencesUtils.setProfilePath(this, imageLocalPath)
                    binding.imgFront.setImageBitmap(imageBitmap)

                }
            }
    }

    private fun registerCameraLauncher2() {
        cameraLauncher2 =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    if (data == null) {
                        return@registerForActivityResult
                    }
                    val extras = data.extras
                    imageBitmap2 = extras!!["data"] as Bitmap
                    file2 = FileUtils.createFile(
                        this,
                        getString(R.string.app_name),
                        "back_car_image.png"
                    )
                    val imageLocalPath = FileUtils.saveImageToInternalStorage(file2, imageBitmap2)
                    uploadBack(this, data.data!!)

                    SharedPreferencesUtils.setProfilePath(this, imageLocalPath)
                    binding.imgBack.setImageBitmap(imageBitmap2)
                }
            }
    }

    private fun registerCameraLauncher3() {
        cameraLauncher3 =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    if (data == null) {
                        return@registerForActivityResult
                    }
                    val extras = data.extras
                    imageBitmap3 = extras!!["data"] as Bitmap
                    file3 = FileUtils.createFile(
                        this,
                        getString(R.string.app_name),
                        "left_car_image.png"
                    )
                    val imageLocalPath = FileUtils.saveImageToInternalStorage(file3, imageBitmap3)
                    uploadLeft(this, data.data!!)

                    SharedPreferencesUtils.setProfilePath(this, imageLocalPath)
                    binding.imgLeft.setImageBitmap(imageBitmap3)
                }
            }
    }

    private fun registerCameraLauncher4() {
        cameraLauncher4 =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    if (data == null) {
                        return@registerForActivityResult
                    }
                    val extras = data.extras
                    imageBitmap4 = extras!!["data"] as Bitmap
                    file4 = FileUtils.createFile(this,
                        getString(R.string.app_name),
                        "right_car_image.png"
                    )
                    val imageLocalPath = FileUtils.saveImageToInternalStorage(file4, imageBitmap4)
                    uploadRight(this, data.data!!)

                    SharedPreferencesUtils.setProfilePath(this, imageLocalPath)
                    /*Glide.with(this).load(imageBitmap4!!)
                        .into(binding.imgRight)*/
                    binding.imgRight.setImageBitmap(imageBitmap4)
                }
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
                        .into(binding.imgFront)
                    //binding.imgFront.setScaleType(ImageView.ScaleType.CENTER_CROP)
                }
            }
    }

    private fun registerGalleryLauncher2() {
        galleryLauncher2 =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    if (data == null) {
                        return@registerForActivityResult
                    }
                    uri2 = data.data!!
                    var imageLocalPath = File(FileUtils.getPathReal(this, uri2!!))

                    file = imageLocalPath.absoluteFile
                    uploadBack(this, uri2!!)

                    SharedPreferencesUtils.setProfilePath(this, imageLocalPath.absolutePath)
                    Glide.with(this).load(uri2)
                        .into(binding.imgBack)
                    //binding.imgFront.setScaleType(ImageView.ScaleType.CENTER_CROP)
                }
            }
    }

    private fun registerGalleryLauncher3() {
        galleryLauncher3 =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    if (data == null) {
                        return@registerForActivityResult
                    }
                    uri3 = data.data!!
                    var imageLocalPath = File(FileUtils.getPathReal(this, uri3!!))

                    file = imageLocalPath.absoluteFile
                    uploadLeft(this, uri3!!)

                    SharedPreferencesUtils.setProfilePath(this, imageLocalPath.absolutePath)
                    Glide.with(this).load(uri3)
                        .into(binding.imgLeft)
                    //binding.imgFront.setScaleType(ImageView.ScaleType.CENTER_CROP)
                }
            }
    }

    private fun registerGalleryLauncher4() {
        galleryLauncher4 =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    if (data == null) {
                        return@registerForActivityResult
                    }
                    uri4 = data.data!!
                    var imageLocalPath = File(FileUtils.getPathReal(this, uri4!!))

                    file = imageLocalPath.absoluteFile
                    uploadRight(this, uri4!!)

                    SharedPreferencesUtils.setProfilePath(this, imageLocalPath.absolutePath)
                    Glide.with(this).load(uri4)
                        .into(binding.imgRight)
                    //binding.imgFront.setScaleType(ImageView.ScaleType.CENTER_CROP)
                }
            }
    }

    private fun requestCameraPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                    Log.d(TAG, "requestCameraPermission - Camera Permission Granted")
                    openCamera()
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) -> {
                    // This case means user previously denied the permission
                    // So here we can display an explanation to the user
                    // That why exactly we need this permission
                    Log.d(TAG, "requestCameraPermission - Camera Permission NOT Granted")
                    showPermissionAlert(
                        getString(R.string.camera_permission),
                        getString(R.string.camera_permission_denied),
                        getString(R.string.ok_caps),
                        getString(R.string.cancel_caps)
                    ) { requestCameraPermissionLauncher.launch(android.Manifest.permission.CAMERA) }
                }
                else -> {
                    // Everything is fine you can simply request the permission

                    showPermissionAlert(
                        getString(R.string.camera_permission),
                        getString(R.string.camera_permission_denied),
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

    private fun Status_checkCameraPermission(): Boolean {
        val camera = ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CAMERA
        )

        return camera == PackageManager.PERMISSION_GRANTED
    }

    private fun Status_checkReadExternalStoragePermission(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

}