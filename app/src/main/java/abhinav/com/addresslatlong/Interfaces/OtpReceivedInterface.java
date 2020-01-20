package abhinav.com.addresslatlong.Interfaces;

/**
 * Created by abhinav on 20/1/20.
 */

public interface OtpReceivedInterface
{
    void onOtpReceived(String otp);
    void onOtpTimeout();
}
