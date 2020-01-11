package abhinav.com.addresslatlong;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class ImageCropActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imagv_image;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_crop_image);

        imagv_image = (ImageView) findViewById(R.id.imagv_image);
        imagv_image.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.imagv_image)
        {
            ArrayList<String> arrPerm = new ArrayList<>();

            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED)
            {
                arrPerm.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            else
            {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                Intent.createChooser(intent, "Select Image");
                intent.setType("image/*");
                intent.putExtra("return-data", true);
                startActivityForResult(intent, 2);
            }

            if(!arrPerm.isEmpty())
            {
                String[] permissions = new String[arrPerm.size()];
                permissions = arrPerm.toArray(permissions);
                ActivityCompat.requestPermissions(this, permissions, 178);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 2 && resultCode == RESULT_OK)
        {
            if(data!=null)
            {
                try {
                    Uri uri = data.getData();

                    cropImage(uri);

                } catch (Exception e) {
                    Toast.makeText(this, "error " + e, Toast.LENGTH_SHORT).show();
                }
            }
        }

        if(requestCode==222 && resultCode == RESULT_OK)
        {
            try
            {

                Uri uri = data.getData();
                Bitmap selected_img_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imagv_image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imagv_image.setImageBitmap(selected_img_bitmap);
            }catch (Exception e)
            {

            }
        }
    }

    public void cropImage(Uri uri)
    {
        try
        {
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile("temp_image_file", ".jpg", storageDir);

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(uri, "image/*");
            cropIntent.putExtra("crop", true);
            cropIntent.putExtra("outputX", 350);
            cropIntent.putExtra("outputY", 350);

            cropIntent.putExtra("aspectX", 10);
            cropIntent.putExtra("aspectY", 10);

            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));

            cropIntent.putExtra("scaleUpIfNeeded", true);
            cropIntent.putExtra("return-data", true);

            startActivityForResult(cropIntent, 222);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
