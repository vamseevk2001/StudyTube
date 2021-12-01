package vamsee.application.studytube

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storageMetadata
import kotlinx.android.synthetic.main.activity_uploadvid.*
import vamsee.application.studytube.Models.STvideos
import vamsee.application.studytube.databinding.ActivityUploadvidBinding


class Uploadvid : AppCompatActivity() {
    var uploadv: Button? = null
    var progressDialog: ProgressDialog? = null
    private lateinit var binding: ActivityUploadvidBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadvidBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_uploadvid)

        // initialise layout
        uploadv = findViewById(R.id.Uploadbutton)
        choosevid.setOnClickListener(View.OnClickListener { // Code for showing progressDialog while uploading
            progressDialog = ProgressDialog(this@Uploadvid)
            choosevideo()
        })
    }

    // choose a video from phone storage
    public fun choosevideo() {
        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 5)
    }

    var videouri: Uri? = null
    var simpleVideoView: VideoView? = null
    var mediaControls: MediaController? = null

    // startActivityForResult is used to receive the result, which is the selected video.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 5 && resultCode == RESULT_OK && data != null && data.data != null) {
            videouri = data.data
            simpleVideoView = findViewById<View>(R.id.uploadPrev) as VideoView

            if (mediaControls == null) {
                mediaControls = MediaController(this)
                mediaControls!!.setAnchorView(this.simpleVideoView)
            }
            simpleVideoView!!.setMediaController(mediaControls)
            simpleVideoView!!.setVideoURI(videouri)

            simpleVideoView!!.requestFocus()
            simpleVideoView!!.start()

                Uploadbutton.setOnClickListener {
                    val videoName = binding.vidTitle.toString()
                    uploadvideo()
                }

        }



    }

    private fun uploadvideo() {
        val videoName = binding.vidTitle.toString()
        if (videouri != null || !TextUtils.isEmpty(videoName)) {
            progressDialog!!.setTitle("Uploading...")
            progressDialog!!.show()
            // save the selected video in Firebase storage
            val reference = FirebaseStorage.getInstance().getReference(
                "Files/" + System.currentTimeMillis() + "." + getfiletype(
                    videouri!!
                )
            )
            reference.putFile(videouri!!).addOnSuccessListener { taskSnapshot ->
                val uriTask = taskSnapshot.storage.downloadUrl
                while (!uriTask.isSuccessful);
                // get the link of video

                val downloadUri = uriTask.result.toString()
                val reference1 = FirebaseDatabase.getInstance().getReference("Video")
                val map = HashMap<String, String>()
                map["videolink"] = downloadUri
                reference1.child("" + System.currentTimeMillis()).setValue(map)


                val forestRef = reference.child(downloadUri)

// Create file metadata including the content type
                val metadata = storageMetadata {
                    contentType = "Video/mp4"
                    setCustomMetadata("Title", videoName)
                }

// Update metadata properties
                forestRef.updateMetadata(metadata)
                // Video uploaded successfully
                // Dismiss dialog
                progressDialog!!.dismiss()
                Toast.makeText(this, "Video Uploaded!!", Toast.LENGTH_SHORT).show()
                STvideos(videoName, downloadUri)
            }.addOnFailureListener { e -> // Error, Image not uploaded
                progressDialog!!.dismiss()
                Toast.makeText(this, "Failed " + e.message, Toast.LENGTH_SHORT).show()
            }.addOnProgressListener { taskSnapshot ->
                val progress =
                    100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                progressDialog!!.setMessage("Uploaded " + progress.toInt() + "%")
            }
        }
    }
    fun getfiletype(videouri: Uri): String? {
        val r = contentResolver
        // get the file type ,in this case its mp4
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(r.getType(videouri))
    }
}