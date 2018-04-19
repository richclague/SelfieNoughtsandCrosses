package com.richardclague.selfienoughtsandcrosses;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    final int MY_PERMISSIONS_ALL = 1;

    ImageView playerOneImageView;
    ImageView playerTwoImageView;
    int imageViewPicker;
    static String playerOneName = "Player One";
    static String playerTwoName = "Player Two";
    static Bitmap playerOneBitmap;
    static Bitmap playerTwoBitmap;
    Uri photoURI;

    String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(

                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void checkPermissions(){

        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            Log.i("Info", "Permission for writing to or reading from external storage is not currently granted, asking now...");


            ActivityCompat.requestPermissions(this,
                    PERMISSIONS,
                    MY_PERMISSIONS_ALL);

        } else {

            Log.i("Info", "Permission for writing to or reading from external has already been granted");

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 0:
                if (resultCode == Activity.RESULT_OK) {

                    try {

                        if (imageViewPicker == 1) {

                            playerOneBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);

                            try {
                                ExifInterface exif = new ExifInterface(mCurrentPhotoPath);
                                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                                Log.i("EXIF", "Exif: " + Integer.toString(orientation));
                                Matrix matrix = new Matrix();
                                if (orientation == 6) {
                                    matrix.postRotate(90);
                                }
                                else if (orientation == 3) {
                                    matrix.postRotate(180);
                                }
                                else if (orientation == 8) {
                                    matrix.postRotate(270);
                                }
                                playerOneBitmap = Bitmap.createBitmap(playerOneBitmap, 0, 0, playerOneBitmap.getWidth(), playerOneBitmap.getHeight(), matrix, true); // rotating bitmap
                            }
                            catch (Exception e) {

                            }

                            playerOneImageView.setImageBitmap(playerOneBitmap);

                        } else {

                            playerTwoBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);

                            try {
                                ExifInterface exif = new ExifInterface(mCurrentPhotoPath);
                                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                                Log.i("EXIF", "Exif: " + Integer.toString(orientation));
                                Matrix matrix = new Matrix();
                                if (orientation == 6) {
                                    matrix.postRotate(90);
                                }
                                else if (orientation == 3) {
                                    matrix.postRotate(180);
                                }
                                else if (orientation == 8) {
                                    matrix.postRotate(270);
                                }
                                playerTwoBitmap = Bitmap.createBitmap(playerTwoBitmap, 0, 0, playerTwoBitmap.getWidth(), playerTwoBitmap.getHeight(), matrix, true); // rotating bitmap
                            }
                            catch (Exception e) {

                            }

                            playerTwoImageView.setImageBitmap(playerTwoBitmap);

                        }

                    } catch (Exception e) {
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }
    }


    private void TakePicturePlayerOne(){

        checkPermissions();
        imageViewPicker = 1;


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i("Error", "Occurred when creating the file");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);

                Log.i("URI", photoURI.toString());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 0);
            }
        }

//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        startActivityForResult(intent, 0);
    }

    public void takePicturePlayerOne(View view){

        TakePicturePlayerOne();

    }

    private void TakePicturePlayerTwo(){
        checkPermissions();
        imageViewPicker = 2;

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i("Error", "Occurred when creating the file");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);

                Log.i("URI", photoURI.toString());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 0);
            }
        }



//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        startActivityForResult(intent, 0);
    }

    public void takePicturePlayerTwo(View view){

        TakePicturePlayerTwo();

    }

    public void playButtonOnClick(View view){

        Intent intent = new Intent(getApplicationContext(), GameActivity.class);

        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneImageView = findViewById(R.id.playerOneImageView);
        playerTwoImageView = findViewById(R.id.playerTwoImageView);

        final EditText playerOneEditText = (EditText) findViewById(R.id.playerOneEditText);
        final EditText playerTwoEditText = (EditText) findViewById(R.id.playerTwoEditText);

        checkPermissions();

        playerOneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                playerOneName = playerOneEditText.getText().toString();
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        playerTwoEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                playerTwoName = playerTwoEditText.getText().toString();
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }
}

