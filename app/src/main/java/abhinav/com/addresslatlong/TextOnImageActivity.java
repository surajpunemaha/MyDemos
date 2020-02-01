package abhinav.com.addresslatlong;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class TextOnImageActivity extends AppCompatActivity implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener
{
    ImageView imgv_pickImage;
    Button btn_pickImage, btn_drawText;
    TextView txtv_imgWidth, txtv_imgHeight, txtv_x_cordinate, txtv_Y_cordinate, txtv_textSize;
    ImageView imgv_y_up, imgv_x_left, imgv_x_right, imgv_y_down;
    EditText edit_input;
    SeekBar seekb_textSize, seekb_red, seekb_green, seekb_blue;

    static final int REQUEST_IMAGE_PICK = 2;
    Uri uri = null;
    Bitmap selected_img_bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_on_image);

        imgv_pickImage = (ImageView) findViewById(R.id.imgv_pickImage);

        btn_pickImage = (Button) findViewById(R.id.btn_pickImage);
        btn_pickImage.setOnClickListener(this);

        txtv_imgWidth = (TextView) findViewById(R.id.txtv_imgWidth);
        txtv_imgHeight = (TextView) findViewById(R.id.txtv_imgHeight);

        txtv_x_cordinate = (TextView) findViewById(R.id.txtv_x_cordinate);
        txtv_Y_cordinate = (TextView) findViewById(R.id.txtv_Y_cordinate);

        imgv_y_up = (ImageView) findViewById(R.id.imgv_y_up);
        imgv_y_up.setOnClickListener(this);

        imgv_x_left = (ImageView) findViewById(R.id.imgv_x_left);
        imgv_x_left.setOnClickListener(this);

        imgv_x_right = (ImageView) findViewById(R.id.imgv_x_right);
        imgv_x_right.setOnClickListener(this);

        imgv_y_down = (ImageView) findViewById(R.id.imgv_y_down);
        imgv_y_down.setOnClickListener(this);

        seekb_textSize = (SeekBar) findViewById(R.id.seekb_textSize);
        seekb_textSize.setOnSeekBarChangeListener(this);

        seekb_red = (SeekBar) findViewById(R.id.seekb_red);
        seekb_red.setOnSeekBarChangeListener(this);
        seekb_green = (SeekBar) findViewById(R.id.seekb_green);
        seekb_green.setOnSeekBarChangeListener(this);
        seekb_blue = (SeekBar) findViewById(R.id.seekb_blue);
        seekb_blue.setOnSeekBarChangeListener(this);

        txtv_textSize = (TextView) findViewById(R.id.txtv_textSize);
        txtv_textSize.setText(""+seekb_textSize.getProgress());

        edit_input = (EditText) findViewById(R.id.edit_input);
        btn_drawText = (Button) findViewById(R.id.btn_drawText);
        btn_drawText.setOnClickListener(this);
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

        if(v.getId()==R.id.btn_drawText)
        {
            getXYAndData();
        }

        if(v.getId()==R.id.imgv_x_right)
        {
            int x = Integer.parseInt(txtv_x_cordinate.getText().toString());
            x = x+10;
            txtv_x_cordinate.setText(""+x);

            getXYAndData();
        }

        if(v.getId()==R.id.imgv_x_left)
        {
            int x = Integer.parseInt(txtv_x_cordinate.getText().toString());
            x = x-10;
            txtv_x_cordinate.setText(""+x);

            getXYAndData();
        }

        if(v.getId()==R.id.imgv_y_up)
        {
            int y = Integer.parseInt(txtv_Y_cordinate.getText().toString());
            y = y-10;
            txtv_Y_cordinate.setText(""+y);

            getXYAndData();
        }

        if(v.getId()==R.id.imgv_y_down)
        {
            int y = Integer.parseInt(txtv_Y_cordinate.getText().toString());
            y = y+10;
            txtv_Y_cordinate.setText(""+y);

            getXYAndData();
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

    public void getXYAndData()
    {
        String str = edit_input.getText().toString().trim();

        int x = Integer.parseInt(txtv_x_cordinate.getText().toString());
        int y = Integer.parseInt(txtv_Y_cordinate.getText().toString());

        float textSize = seekb_textSize.getProgress();

        /*int red = seekb_red.getProgress();
        int green = seekb_green.getProgress();
        int blue = seekb_blue.getProgress();

        String color_string = "#"+Integer.toHexString(red)
                +""+Integer.toHexString(green)
                +""+Integer.toHexString(blue);

        int color = Color.parseColor(color_string);*/

        Bitmap new_bm = drawTextOnImage(selected_img_bitmap, str, x,y, (float) textSize);
        imgv_pickImage.setImageBitmap(new_bm);
    }

    public Bitmap drawTextOnImage(Bitmap bm, String text, int x, int y, float textSize)
    {
        Bitmap newBitmap = null;

        try
        {
            Bitmap.Config config = bm.getConfig();
            if(config == null){
                config = Bitmap.Config.ARGB_8888;
            }

            newBitmap = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), config);
            Canvas newCanvas = new Canvas(newBitmap);

            newCanvas.drawBitmap(bm, 0, 0, null);

            /* ------------------- Draw transperent background --------------- */

            /* --------------------------------------------------------------- */
            Rect rectText1 = new Rect();
            if(text!=null)
            {
                Paint paintText1 = new Paint(Paint.ANTI_ALIAS_FLAG);
                paintText1.setColor(Color.BLACK);
                paintText1.setTextSize(textSize);
                paintText1.setStyle(Paint.Style.FILL);

                paintText1.getTextBounds(text, 0, text.length(), rectText1);

                newCanvas.drawText(text, x, y, paintText1);
            }

        }catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return newBitmap;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        if(seekBar.getId()==R.id.seekb_textSize)
        {
            txtv_textSize.setText(""+progress);
            getXYAndData();
        }

        if((seekBar.getId()==R.id.seekb_red) || (seekBar.getId()==R.id.seekb_green) || (seekBar.getId()==R.id.seekb_blue))
        {
            getXYAndData();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar)
    {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar)
    {

    }
}
