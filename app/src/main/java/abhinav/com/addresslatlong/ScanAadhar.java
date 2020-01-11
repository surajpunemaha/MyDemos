package abhinav.com.addresslatlong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

public class ScanAadhar extends AppCompatActivity implements View.OnClickListener
{
    Button btn_scan_qr;
    TextView txtv_data, txtv_raw_data;

    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_aadhar);

        qrScan = new IntentIntegrator(this);

        txtv_data = (TextView) findViewById(R.id.txtv_data);
        txtv_raw_data = (TextView) findViewById(R.id.txtv_raw_data);

        btn_scan_qr = (Button) findViewById(R.id.btn_scan_qr);
        btn_scan_qr.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        qrScan.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null)
        {
            //if qrcode has nothing in it
            if (result.getContents() == null)
            {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            }
            else
            {
                try
                {
                    String content = result.getContents();
                    txtv_raw_data.setText(content);
                    processScannedData(content);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected void processScannedData(String scanData)
    {
        XmlPullParserFactory pullParserFactory;

        try
        {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new StringReader(scanData));
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                if(eventType == XmlPullParser.START_DOCUMENT)
                {

                }
                else if(eventType == XmlPullParser.START_TAG
                        && DataAttributes.AADHAAR_DATA_TAG.equals(parser.getName()))
                {
                    String ud_id = parser.getAttributeValue(null,DataAttributes.AADHAR_UID_ATTR);
                    String name =  parser.getAttributeValue(null,DataAttributes.AADHAR_NAME_ATTR);
                    String gender = parser.getAttributeValue(null,DataAttributes.AADHAR_GENDER_ATTR);

                    String yob = parser.getAttributeValue(null,DataAttributes.AADHAR_YOB_ATTR);
                    String co = parser.getAttributeValue(null,DataAttributes.AADHAR_CO_ATTR);
                    String address = parser.getAttributeValue(null,DataAttributes.AADHAR_House_ATTR);
                    String lm = parser.getAttributeValue(null,DataAttributes.AADHAR_LM_ATTR);
                    String dist = parser.getAttributeValue(null,DataAttributes.AADHAR_DIST_ATTR);
                    String state = parser.getAttributeValue(null,DataAttributes.AADHAR_STATE_ATTR);
                    String pin_code = parser.getAttributeValue(null,DataAttributes.AADHAR_PC_ATTR);
                    String taluka = parser.getAttributeValue(null,DataAttributes.AADHAR_TALUKA_ATTR);

                    txtv_data.setText(ud_id+"\n"+name+"\n"+gender+"\n"+yob+"\n"+co+"\n"+address+"\n"
                            +lm+"\n"+dist+"\n"+state+"\n"+pin_code+"\n"+taluka);
                }
                else if(eventType == XmlPullParser.END_TAG)
                {

                }
                else if(eventType == XmlPullParser.TEXT)
                {

                }

                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class DataAttributes {

        public static final String AADHAAR_DATA_TAG = "PrintLetterBarcodeData",
                AADHAR_UID_ATTR = "uid",
                AADHAR_NAME_ATTR = "name",
                AADHAR_GENDER_ATTR = "gender",
                AADHAR_YOB_ATTR = "yob",
                AADHAR_CO_ATTR = "co",
                AADHAR_House_ATTR = "house",
                AADHAR_LM_ATTR = "lm",
                AADHAR_DIST_ATTR = "dist",
                AADHAR_STATE_ATTR = "state",
                AADHAR_TALUKA_ATTR = "subdist",
                AADHAR_PC_ATTR = "pc";

    }
}