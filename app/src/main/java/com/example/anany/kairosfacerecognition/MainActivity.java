package com.example.anany.kairosfacerecognition;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kairos.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    //IMPORTANT ------------------------------------------------------------------------------
    //Replace the following with the ID and Key provided to you at the Kairos API Dashboard - https://developer.kairos.com/admin
    //For more information about getting the free trial and API Key, you can read my repository's GitHub README at the following link -
    //https://github.com/ishaanjav/Kairos_Face_Recognition/blob/master/README.md
    String app_id = "<YOUR APP ID COMES HERE>";
    String api_key = "<YOUR API KEY COMES HERE>";
    //IMPORTANT ------------------------------------------------------------------------------

    KairosListener detlistener, identlistener;

    Button enroll, identify;
    ImageView imageView;
    Bitmap image;
    Kairos myKairos;
    String galleryId = "people";

    TextView info;
    EditText name;
    String minHeadScale = "0.1";
    String multipleFaces = "false";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // instantiate a new kairos instance
        myKairos = new Kairos();
        MainActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        imageView = findViewById(R.id.imageview);
        enroll = findViewById(R.id.enroll);
        identify = findViewById(R.id.identify);
        info = findViewById(R.id.info);

        // set authentication
        myKairos.setAuthentication(this, app_id, api_key);
        name = findViewById(R.id.name);

        // Create an instance of the KairosListener
        detlistener = new KairosListener() {
            @Override
            public void onSuccess(String response) {
                makeToast("Success! Registered the face");
                info.setText(("SUCCESS!"));
            }

            @Override
            public void onFail(String response) {
                makeToast("Fail: " + response);
                info.setText("Fail: " + response);
            }
        };

        identlistener = new KairosListener() {
            @Override
            public void onSuccess(String response) {
                makeToast("Success! You are: " + readJSONForName(response));
                info.setText(readJSONForName(response));
            }

            @Override
            public void onFail(String response) {
                makeToast("Fail: " + response);
                info.setText("Fail: " + response);
            }
        };

        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 100);
                    } else {
                        if (name.getText().toString().isEmpty() || name.getText().toString() == null) {
                            longToast("Please enter a name before enrolling the face.");
                        } else {
                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, 2);
                        }
                    }
                } catch (Exception e) {
                    makeToast("Error Message: " + e.getMessage() + ". Cause: " + e.getCause());
                }
            }
        });

        identify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 100);
                    } else {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, 1);
                    }
                    //       identifyImage();
                } catch (Exception e) {
                    makeToast("Error Message: " + e.getMessage() + ". Cause: " + e.getCause());
                    info.setText("Error Message: " + e.getMessage() + ". Cause: " + e.getCause());
                }
            }
        });


    }

    private String readJSONForName(String response) {
        String match = "";
        int location = response.indexOf("subject_id");
        match = response.substring(location + 13);
        String name = "";
        for (int i = 0; i < match.length(); i++) {
            if (match.charAt(i) == '"') {
                break;
            } else {
                name += Character.toString(match.charAt(i));
            }
        }
        return name;
    }

    private void identifyImage() throws UnsupportedEncodingException, JSONException {
        String selector = "FULL";
        myKairos.recognize(image,
                galleryId,
                selector,
                null,
                minHeadScale,
                null,
                identlistener);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.info) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("About This App")
                    .setMessage("This app makes use of the Kairos SDK for Android to implement facial recognition. First, a user must take a picture of a person. Once they have, they can enter a name and click the enroll button to register that person's face. Finally, they can take another picture and use the identify button for recognizing the face.")
                    .setCancelable(true)
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();

        }

        return super.onOptionsItemSelected(item);
    }

    private void enrollImage() throws UnsupportedEncodingException, JSONException {
        if (name.getText().toString() == null) {
            makeToast("No name entered");
        } else {
            String subjectId = name.getText().toString();
            String selector = "FULL";
            myKairos.enroll(image,
                    subjectId,
                    galleryId,
                    selector,
                    multipleFaces,
                    minHeadScale,
                    detlistener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            image = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(image);
            try {
                enrollImage();
            } catch (Exception e) {
                longToast("ERROR: " + e.toString());
            }
        } else if (requestCode == 1 && resultCode == RESULT_OK) {
            image = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(image);
            try {
                identifyImage();
            } catch (Exception e) {
                longToast("ERROR: " + e.toString());
            }
        }
    }

    private void makeToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    private void longToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }
}
