package abhinav.com.addresslatlong;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CompressionActivity extends AppCompatActivity implements View.OnClickListener
{
    ImageView imgv_takeImage, imgv_pickImage, imgv_original, imgv_compressed;
    Button btn_compress;
    TextView txtv_originalSize, txtv_compressSize;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_PICK = 2;

    Uri uri = null;
    String strUri ="";
    String mCurrentPhotoPath="";
    String original_path ="";

    private String pictureImagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compression);

        txtv_originalSize = (TextView) findViewById(R.id.txtv_originalSize);
        txtv_compressSize = (TextView) findViewById(R.id.txtv_compressSize);

        imgv_original = (ImageView) findViewById(R.id.imgv_original);
        imgv_compressed = (ImageView) findViewById(R.id.imgv_compressed);

        imgv_takeImage = (ImageView) findViewById(R.id.imgv_takeImage);
        imgv_takeImage.setOnClickListener(this);

        imgv_pickImage = (ImageView) findViewById(R.id.imgv_pickImage);
        imgv_pickImage.setOnClickListener(this);

        btn_compress = (Button) findViewById(R.id.btn_compress);
        btn_compress.setOnClickListener(this);

        RadioButton rdbtn = new RadioButton(CompressionActivity.this);
        rdbtn.setChecked(true);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.imgv_takeImage)
        {
            if (ContextCompat.checkSelfPermission(CompressionActivity.this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(CompressionActivity.this, new String[]{
                        Manifest.permission.CAMERA}, 1);
            }
            else
            {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
                {
                    Toast.makeText(this, "higher api", Toast.LENGTH_SHORT).show();
                    openCameraForHighrApi();
                }
                else
                {
                    Toast.makeText(this, "lowerapi", Toast.LENGTH_SHORT).show();
                    openBackCamera();
                }
            }
        }
        if(view.getId()==R.id.imgv_pickImage)
        {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            Intent.createChooser(intent, "Select Image");
            intent.setType("image/*");
            intent.putExtra("return-data", true);
            startActivityForResult(intent, REQUEST_IMAGE_PICK);
        }
        if(view.getId()==R.id.btn_compress)
        {
            if (ContextCompat.checkSelfPermission(CompressionActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(CompressionActivity.this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
            else
            {
                System.out.println("GALLERY PATH=>"+original_path);

                String compressed_path = compressImage(original_path);
                if(compressed_path!=null)
                {
                    File compressedFile = new File(compressed_path);
                    if(compressedFile.exists())
                    {
                        int fileSize = (Integer.parseInt(String.valueOf(compressedFile.length()/1024)));
                        Bitmap myBitmap = BitmapFactory.decodeFile(compressedFile.getAbsolutePath());
                        imgv_compressed.setImageBitmap(myBitmap);
                        txtv_compressSize.setText(fileSize+" KB");
                        String strimg = BitMapToString(myBitmap);
                    }
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode==REQUEST_IMAGE_PICK)
        {
            if(data!=null)
            {
                try {
                    uri = data.getData();
                    strUri = data.getDataString();
                    original_path = getRealPathFromURI(strUri);
                    //System.out.println("STR_URI=>"+strUri);
                    Bitmap selected_img_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    imgv_original.setImageBitmap(selected_img_bitmap);

                } catch (Exception e) {
                    Toast.makeText(this, "error " + e, Toast.LENGTH_SHORT).show();
                }
            }
        }
        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK)
        {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
            {
                galleryAddPic();
                setPic();
            }
            else
            {
                File imgFile = new  File(original_path);
                if(imgFile.exists())
                {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    imgv_original.setImageBitmap(myBitmap);
                }
            }
        }
    }

    private File createImageFile() throws IOException
    {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        original_path = mCurrentPhotoPath;
        System.out.println("CAPT_PATH=>"+original_path);
        return image;
    }

    private void galleryAddPic() {
        try{
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File f = new File(mCurrentPhotoPath);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            this.sendBroadcast(mediaScanIntent);
        }catch (Exception e)
        {
            Toast.makeText(this, ""+e, Toast.LENGTH_LONG).show();
        }
    }

    private void setPic() {

        try
        {
            // Get the dimensions of the View
            int targetW = imgv_original.getWidth();
            int targetH = imgv_original.getHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            imgv_original.setImageBitmap(bitmap);

        }catch (Exception e)
        {

        }
    }


    /*------------------- Methods to compress -------------------*/

    public String compressImage(String filePath)
    {
        //String filePath = getRealPathFromURI(imageUri);
        //System.out.println("AVAILABLE=> "+filePath);
        Bitmap scaledBitmap = null;

        File original = new File(filePath);
        int fileSize = (Integer.parseInt(String.valueOf(original.length()/1024)));
        txtv_originalSize.setText(fileSize+" KB");

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath,options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

        if (actualHeight > maxHeight || actualWidth > maxWidth)
        {
            if (imgRatio < maxRatio)
            {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            }
            else if (imgRatio > maxRatio)
            {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            }
            else
            {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;
            }
        }

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;

        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }

        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight,Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = 	actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2,
                new Paint(Paint.FILTER_BITMAP_FLAG));

        ExifInterface exif;
        try
        {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try
        {
            out = new FileOutputStream(filename);
//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return filename;
    }

    public String getFilename()
    {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;
    }

    private String getRealPathFromURI(String contentURI)
    {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null,
                null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth)
        {
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap)
        {
            inSampleSize++;
        }

        return inSampleSize;
    }

    /*-------------------------------------------------------------*/

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    private File createTemporaryFile(String part, String ext) throws Exception
    {
        File tempDir= Environment.getExternalStorageDirectory();
        tempDir=new File(tempDir.getAbsolutePath()+"/.temp/");
        if(!tempDir.exists())
        {
            tempDir.mkdirs();
        }
        return File.createTempFile(part, ext, tempDir);
    }

    private void openBackCamera()
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + ".jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        original_path = storageDir.getAbsolutePath() + "/" + imageFileName;
        System.out.println("IMAGE PATH =>"+original_path);
        File file = new File(original_path);
        Uri outputFileUri = Uri.fromFile(file);
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
    }

    private void openCameraForHighrApi()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this, "Error occurred while creating the File", Toast.LENGTH_SHORT).show();
            }
            if (photoFile != null)
            {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "abhinav.com.addresslatlong.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                strUri = photoURI.toString();
                //System.out.println("CAPT_URI"+strUri);

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
}
