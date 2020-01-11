package abhinav.com.addresslatlong;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUploadActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressbar;
    Button btn_brows_files, btn_upload;
    EditText edit_filepath;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload);

        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        progressbar.setVisibility(View.GONE);

        btn_brows_files = (Button) findViewById(R.id.btn_brows_files);
        btn_brows_files.setOnClickListener(this);

        btn_upload = (Button) findViewById(R.id.btn_upload);
        btn_upload.setOnClickListener(this);

        edit_filepath = (EditText) findViewById(R.id.edit_filepath);

    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.btn_brows_files)
        {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(intent, 7);
        }
        if(view.getId()==R.id.btn_upload)
        {
            String path = edit_filepath.getText().toString().trim();

            File file = new File(path);
            String bytearray = convertFileToByteArray(file);
            //uploadFile(path);

            System.out.println("okay");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 7:
                if(resultCode==RESULT_OK)
                {
                    String resume_file_path="";

                    Uri uri = data.getData();
                    String docFilePath = FilePath.getPath(FileUploadActivity.this, uri);

                    if (docFilePath != null)
                    {
                        char first = docFilePath.charAt(0);
                        if(first=='/')
                        {
                            resume_file_path = docFilePath.substring(1, docFilePath.length());
                        }
                        else
                        {
                            resume_file_path = docFilePath;
                        }
                    }

                    edit_filepath.setText(resume_file_path);

                    File file = new File(resume_file_path);
                    int file_size = Integer.parseInt(String.valueOf(file.length()/1024));

                    Toast.makeText(this, "Selected File Size =>\n"+file_size+" MB", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public static String convertFileToByteArray(File f) {
        byte[] byteArray = null;
        try {
            InputStream inputStream = new FileInputStream(f);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024 * 11];
            int bytesRead = 0;

            while ((bytesRead = inputStream.read(b)) != -1) {
                bos.write(b, 0, bytesRead);
            }

            byteArray = bos.toByteArray();

            Log.e("Byte array", ">" + byteArray);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }

    /*public void convertFileToByteArray(File file)
    {
        byte[] b = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(b);
            for (int i = 0; i < b.length; i++) {
                System.out.print((char)b[i]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found.");
            e.printStackTrace();
        }
        catch (IOException e1) {
            System.out.println("Error Reading The File.");
            e1.printStackTrace();
        }
    }*/


    public void uploadFile(final String sourceFileUri)
    {

    }


}