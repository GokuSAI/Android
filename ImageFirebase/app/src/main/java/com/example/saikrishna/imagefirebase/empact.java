package com.example.saikrishna.imagefirebase;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class empact extends AppCompatActivity {
    TextView urlew,overlay;
    ImageView img;
    Button upload;
    EditText e;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empact);

        urlew = (TextView)findViewById(R.id.URLTextView);
        upload = (Button)findViewById(R.id.UploadButton);
        img=(ImageView) findViewById(R.id.imageView);
        overlay = (TextView)findViewById(R.id.OverlayText);
        e=(EditText)findViewById(R.id.editText);
        overlay.setText("");
        urlew.setVisibility(View.INVISIBLE);





        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e.setText(overlay.getText().toString());
                img.setDrawingCacheEnabled(true);
                img.buildDrawingCache();
                Bitmap bitmap = img.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();


                String path = "Fireimages/"+ UUID.randomUUID() + ".png";
                StorageReference ref = storage.getReference(path);


                StorageMetadata metadata = new StorageMetadata.Builder()
                        .setCustomMetadata("text",overlay.getText().toString())
                        .build();

                upload.setEnabled(false);
                Toast.makeText(empact.this, "Started uploading... ", Toast.LENGTH_SHORT).show();

                UploadTask uploadTask = ref.putBytes(data, metadata);

                uploadTask.addOnSuccessListener(empact.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        upload.setEnabled(true);
                        Uri url = taskSnapshot.getDownloadUrl();
                        urlew.setText(url.toString());
                        urlew.setVisibility(View.VISIBLE);
                    }
                });


            }
        });
    }
}
