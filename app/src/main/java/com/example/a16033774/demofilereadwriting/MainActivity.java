package com.example.a16033774.demofilereadwriting;

import android.Manifest;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {

    Button btnWrite;
    Button btnRead;
    String folderLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnWrite = (Button) findViewById(R.id.btnWrite);
        btnRead = (Button) findViewById(R.id.btnRead);

        int permissionCheck_Storage = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck_Storage != PermissionChecker.PERMISSION_GRANTED){
            Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            finish();
        }

        folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyFolder";

        File folder = new File(folderLocation);
        if (folder.exists() == false){
            boolean result = folder.mkdir();
            if (result == true){
                Log.d("File Read/Write", "Folder created");
            }
        }

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Code for file writing
                try {
                    String folderLocation_I = getFilesDir().getAbsolutePath() + "/Folder";
                    File targetFile_I = new File(folderLocation_I, "text.txt");
                    FileWriter writer_I = new FileWriter(targetFile_I, true);
                    writer_I.write("test data" + "\n");
                    writer_I.flush();
                    writer_I.close();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Failed to write!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Code for file reading
                String folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Test";
                File targetFile = new File(folderLocation, "data.txt");

                if(targetFile.exists() == true) {
                    String data ="";
                    try {
                        FileReader reader = new FileReader(targetFile);
                        BufferedReader br = new BufferedReader(reader);

                        String line = br.readLine();
                        while (line != null){
                            data += line + "\n";
                            line = br.readLine();
                        }
                        br.close();
                        reader.close();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Failed to read!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                    Log.d("Content", data);
                }

            }
        });

    }
}
