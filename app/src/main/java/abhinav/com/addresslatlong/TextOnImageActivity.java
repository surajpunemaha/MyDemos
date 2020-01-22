package abhinav.com.addresslatlong;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class TextOnImageActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    ImageView imgv_pickImage;
    Button btn_pickImage;
    TextView txtv_imgWidth, txtv_imgHeight, txtv_x_cordinate,txtv_Y_cordinate;
    static final int REQUEST_IMAGE_PICK = 2;
    Uri uri = null;
    Bitmap selected_img_bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_on_image);

        imgv_pickImage = (ImageView) findViewById(R.id.imgv_pickImage);
        imgv_pickImage.setOnTouchListener(this);

        btn_pickImage = (Button) findViewById(R.id.btn_pickImage);
        btn_pickImage.setOnClickListener(this);

        txtv_imgWidth = (TextView) findViewById(R.id.txtv_imgWidth);
        txtv_imgHeight = (TextView) findViewById(R.id.txtv_imgHeight);

        txtv_x_cordinate = (TextView) findViewById(R.id.txtv_x_cordinate);
        txtv_Y_cordinate = (TextView) findViewById(R.id.txtv_Y_cordinate);

    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.btn_pickImage)
        {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            Intent.createChooser(intent, "Select Image");
            intent.setType("image/*");
            intent.putExtra("return-data", true);
            startActivityForResult(intent, REQUEST_IMAGE_PICK);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode==REQUEST_IMAGE_PICK)
        {
            if(data!=null)
            {
                try
                {
                    uri = data.getData();
                    selected_img_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                    imgv_pickImage.setImageBitmap(selected_img_bitmap);

                    int width = selected_img_bitmap.getWidth();
                    int height = selected_img_bitmap.getHeight();

                    txtv_imgWidth.setText("Img Width :-"+ width);
                    txtv_imgHeight.setText("Img Height :-"+ height);

                } catch (Exception e) {
                    Toast.makeText(this, "error " + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if(v.getId()==R.id.imgv_pickImage)
        {
            /*PointF p = new PointF(event.getX(), event.getY());
            View root = v.getRootView();
            while (v.getParent() instanceof View && v.getParent() != root) {
                p.y += v.getTop();
                p.x += v.getLeft();
                v = (View) v.getParent();
            }*/

            float x = event.getX();
            float y = event.getY();

            txtv_x_cordinate.setText(""+x);
            txtv_Y_cordinate.setText(""+y);
        }

        return false;
    }
}
