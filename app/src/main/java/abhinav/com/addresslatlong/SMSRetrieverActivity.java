package abhinav.com.addresslatlong;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import abhinav.com.addresslatlong.Interfaces.OtpReceivedInterface;
import abhinav.com.addresslatlong.Other.AppSignatureHelper;
import abhinav.com.addresslatlong.Receivers.SmsBroadcastReceiver;

public class SMSRetrieverActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OtpReceivedInterface {

    GoogleApiClient mGoogleAPIClient;
    EditText edit_mobile, edit_otp;
    SmsBroadcastReceiver mSmsBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsretriever);
        initUI();

        mGoogleAPIClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Auth.CREDENTIALS_API)
                .build();

        if(mGoogleAPIClient!=null) {
            mGoogleAPIClient.connect();

            //getHintRequest();

            startSMSListener();
        }
    }

    public void initUI()
    {
        edit_mobile = (EditText) findViewById(R.id.edit_mobile);
        edit_otp = (EditText) findViewById(R.id.edit_otp);

        mSmsBroadcastReceiver = new SmsBroadcastReceiver();
        mSmsBroadcastReceiver.setOnOTPListener(this);

        AppSignatureHelper appSignatureHelper = new AppSignatureHelper(this);
        appSignatureHelper.getAppSignatures();
    }

    @SuppressLint("RestrictedApi")
    public void getHintRequest()
    {
        // this displays the popup with sim mobile numbers inserted in your device.
        HintRequest hintRequest = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build();

        PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(mGoogleAPIClient, hintRequest);
        try {
            startIntentSenderForResult(intent.getIntentSender(), 1008, null,
                    0, 0, 0, null);
        } catch (IntentSender.SendIntentException e) {
            Log.e("", "Could not start hint picker Intent", e);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1008)
        {
            if(resultCode== RESULT_OK)
            {
                // retrieve the selected phone number from hint popup
                Credential cred = data.getParcelableExtra(Credential.EXTRA_KEY);
                edit_mobile.setText(cred.getId().substring(3));
                edit_mobile.setSelection(edit_mobile.getText().toString().trim().length());
            }
        }
    }

    public void startSMSListener()
    {
        SmsRetrieverClient mClient = SmsRetriever.getClient(this);
        Task<Void> mTask = mClient.startSmsRetriever();
        mTask.addOnSuccessListener(new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void aVoid)
            {
                Toast.makeText(SMSRetrieverActivity.this, "SMS Retriever Starts", Toast.LENGTH_SHORT).show();
            }
        });

        mTask.addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Toast.makeText(SMSRetrieverActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onOtpReceived(String otp)
    {
        edit_otp.setText(otp);
    }

    @Override
    public void onOtpTimeout()
    {

    }
}