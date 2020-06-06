package com.example.pdfviewer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnLoadCompleteListener;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.joanzapata.pdfview.util.Constants;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileLock;

public class PdfView extends AppCompatActivity {
   PDFView pdfView;
    String LINK;
    TextView textView;
    Uri file;
    String filename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        pdfView = findViewById(R.id.pdf);
        textView=findViewById(R.id.textview);
        Intent intent = getIntent();
        LINK=intent.getStringExtra("LINK");
        filename=intent.getStringExtra("NAME");
    /*    pdfView.loadUrl(LINK);
        pdfView.setWebViewClient(new WebViewClient());
        pdfView.getSettings().setJavaScriptEnabled(true);*/
/*   Intent d = new Intent();

          d.setType("application/pdf");
        d.setAction(Intent.ACTION_GET_CONTENT);
          startActivityForResult(d,12);*/

        File file1=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/"+filename);
        pdfView.fromFile(file1).defaultPage(1).onLoad(new OnLoadCompleteListener() {
            @Override
            public void loadComplete(int nbPages) {
                Toast.makeText(PdfView.this, "Loded", Toast.LENGTH_SHORT).show();

            }
        }).onPageChange(new OnPageChangeListener() {
            @Override
            public void onPageChanged(int page, int pageCount) {

            }
        }).showMinimap(false).enableSwipe(true).load();
       /* FileLoader.with(this).load(LINK).fromDirectory("PDFfiles",FileLoader.DIR_EXTERNAL_PRIVATE).
                asFile(new FileRequestListener<File>() {
                    @Override
                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                        Toast.makeText(PdfView.this, "successfull", Toast.LENGTH_SHORT).show();
                        File file1=response.getBody();


                    }

                    @Override
                    public void onError(FileLoadRequest request, Throwable t) {


                    }
                });

*/




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12 && data != null && data.getData() != null) {
            file=data.getData();

        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}
