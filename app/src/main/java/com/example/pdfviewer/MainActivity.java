package com.example.pdfviewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button viewpdfbtn;
    EditText viewPdf;
    ProgressDialog progressDialog;
    String fileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPdf=findViewById(R.id.pdflink);
        viewpdfbtn=findViewById(R.id.pdfviewbtn);
        progressDialog=new ProgressDialog(MainActivity.this);
        viewpdfbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

          if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                  ==PackageManager.PERMISSION_GRANTED ) {
              if( (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                      == PackageManager.PERMISSION_GRANTED) {
                  String LINK = viewPdf.getText().toString();
                  if (!LINK.isEmpty()) {
                      progressDialog.setTitle("Loading");
                      progressDialog.setCancelable(false);
                      progressDialog.show();
                      Download(LINK);
                      Toast.makeText(MainActivity.this, "if", Toast.LENGTH_SHORT).show();
                      Intent intent = new Intent(getApplicationContext(), PdfView.class);
                      intent.putExtra("LINK", LINK);
                      intent.putExtra("NAME",fileName);
                      startActivity(intent);
                      progressDialog.dismiss();
                  }
                  else {
                      viewPdf.setError("FILL");
                  }
              }
          }
          else {
              Toast.makeText(MainActivity.this, "else", Toast.LENGTH_SHORT).show();
              ActivityCompat.requestPermissions(MainActivity.this,new  String[] {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},12);
          }
            }
        });

    }

    private void Download(String link) {
      fileName = URLUtil.guessFileName(link, null, MimeTypeMap.getFileExtensionFromUrl(link));

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(link));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName);
        DownloadManager downloadManager= (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        request.setMimeType("application/pdf");
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
         downloadManager.enqueue(request);
         viewpdfbtn.setVisibility(View.GONE);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==12)
        {
            if(grantResults[0]+grantResults[1]==PackageManager.PERMISSION_GRANTED)
            {

            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        super.onBackPressed();
    }
}
