package com.example.gameNews;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class GameReviewFragment extends Fragment {

    //fields
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private EditText mEditTitleName,mCommentsWrite;
    private ImageView mImageView;
    private TextView mShowComments;
    private ProgressBar mprogressBar;
    private Uri mImageuri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Return this View later in this fragment
        View v = inflater.inflate(R.layout.fragment_review, container, false);

        //signed to each view
        mButtonChooseImage = v.findViewById(R.id.ChooseBt);
        mButtonUpload = v.findViewById(R.id.button_upload);
        mEditTitleName= v.findViewById(R.id.WriteTitle);
        mCommentsWrite = v.findViewById(R.id.CommentsWrite);
        mImageView = v.findViewById(R.id.Upload_image);
        mShowComments = v.findViewById(R.id.text_view_show_uploads);
        mprogressBar = v.findViewById(R.id.progress_bar);

        //set the database reference to correct path on firebase
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        //set click listener for choose image button
        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFileChooser();

            }
        });

        //set click listener for upload comments button
        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mUploadTask != null && mUploadTask.isInProgress())
                {
                    Toast.makeText(getActivity(), "Upload in progress", Toast.LENGTH_SHORT).show();
                }else{
                    uploadFile();
                }
            }
        });

        //set click listener for show other people's comments button
        mShowComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCommentFragment();
            }
        });
        return v;
    }

    //void method for open  file
    private  void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    //Override void method for get the image
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                   && data != null && data.getData() != null){
            mImageuri = data.getData();

            Picasso.get().load(mImageuri).into(mImageView);
        }
    }

    //Void method to upload the file
    private  void uploadFile(){

        if(mImageuri != null){
            StorageReference fileRef = mStorageRef.child(System.currentTimeMillis()+"."+"jpg");

            //if upload successful run this
            mUploadTask=fileRef.putFile(mImageuri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mprogressBar.setProgress(0);
                                }
                            },2000);

                            // get the image uri and pass the data from database.
                            final Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();
                            firebaseUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String downloadUri = uri.toString();
                                    uploadModel UPLOAD = new uploadModel(mEditTitleName.getText().toString().trim(),
                                            downloadUri,mCommentsWrite.getText().toString().trim());
                                    String uploadId = mDatabaseRef.push().getKey();
                                    mDatabaseRef.child(uploadId).setValue(UPLOAD);
                                }
                            });

                            Toast.makeText(getActivity(), "Upload successful", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "error"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            //set value to progress bar
                            double progress = (100.0* taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            mprogressBar.setProgress((int) progress);
                        }
                    });
        }else {
            //if image is not selected show this toast
            Toast.makeText(getActivity(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    // open other fragment
    public void OpenCommentFragment(){
        CommentsFragment commentsFragment = new CommentsFragment();
        FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,commentsFragment).addToBackStack(null).commit();
    }
}
