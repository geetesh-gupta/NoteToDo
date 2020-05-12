package com.gg.notetodo.views

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.gg.notetodo.BuildConfig
import com.gg.notetodo.R
import com.gg.notetodo.util.AppConstant
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddNotesActivity : AppCompatActivity() {
    private lateinit var addNotesTitle: TextInputLayout
    private lateinit var addNotesDescription: TextInputLayout
    private lateinit var addNotesCreateButton: MaterialButton
    private lateinit var addNotesImageView: ImageView
    private lateinit var imageLocation: File
    private val MY_PERMISSION_CODE = 124
    private val REQUEST_CODE_CAMERA = 1
    private val REQUEST_CODE_GALLERY = 2
    private var picturePath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
        bindViews()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(Activity.RESULT_CANCELED)
    }

    private fun bindViews() {
        addNotesTitle = findViewById(R.id.addNotesTitle)
        addNotesImageView = findViewById(R.id.addNotesImageView)
        addNotesDescription = findViewById(R.id.addNotesDescription)
        addNotesCreateButton = findViewById(R.id.addNotesCreateButton)
        addNotesCreateButton.setOnClickListener {
            val intent = Intent()
            intent.putExtra(AppConstant.TITLE, addNotesTitle.editText?.text.toString())
            intent.putExtra(AppConstant.DESCRIPTION, addNotesDescription.editText?.text.toString())
            intent.putExtra(AppConstant.IMAGE_PATH, picturePath)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        addNotesImageView.setOnClickListener {
            if (checkAndRequestPermissions()) {
                setupDialog()
            }
        }
    }


    private fun setupDialog() {
        val view =
            LayoutInflater.from(this@AddNotesActivity).inflate(R.layout.dialog_selector, null)
        val buttonCamera = view.findViewById<MaterialButton>(R.id.buttonCamera)
        val buttonGallery = view.findViewById<MaterialButton>(R.id.buttonGallery)
        val addImageDialog = AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(true)
            .create()
        buttonCamera.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(packageManager) != null) {
                // Create the File where the photo should go
                var photoFile: File? = null
                try {
                    photoFile = createImageFile()
                } catch (ex: IOException) {
                    ex.printStackTrace()
                }
                if (photoFile != null) {
                    val photoURI = FileProvider.getUriForFile(
                        this@AddNotesActivity,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile
                    )
                    imageLocation = photoFile
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA)
                }
            }
//            addImageDialog.hide()
            addImageDialog.dismiss()
        }
        buttonGallery.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(intent, REQUEST_CODE_GALLERY)
//            addImageDialog.hide()
            addImageDialog.dismiss()
        }
        addImageDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) when (requestCode) {
            REQUEST_CODE_CAMERA -> Glide.with(this).load(imageLocation.absoluteFile)
                .into(addNotesImageView)
            REQUEST_CODE_GALLERY -> {
                val selectedImage = data?.data
                val filePath = arrayOf(MediaStore.Images.Media.DATA)
                val c = contentResolver.query(selectedImage!!, filePath, null, null, null)
                c!!.moveToFirst()
                val columnIndex = c.getColumnIndex(filePath[0])
                picturePath = c.getString(columnIndex)
                c.close()
                Glide.with(this).load(picturePath).into(addNotesImageView)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        val mFileName = "JPEG_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(mFileName, ".jpg", storageDir)
    }

    private fun checkAndRequestPermissions(): Boolean {
        val permissionCAMERA = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        )
        val storagePermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        val listPermissionsNeeded = ArrayList<String>()
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (listPermissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray<String>(), MY_PERMISSION_CODE
            )
            return false
        }

        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted Successfully. Write working code here.
                    setupDialog()
                }
            }
        }
    }

}

