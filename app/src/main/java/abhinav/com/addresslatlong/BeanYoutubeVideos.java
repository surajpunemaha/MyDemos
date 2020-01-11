package abhinav.com.addresslatlong;

/**
 * Created by abhinav on 11/1/20.
 */

public class BeanYoutubeVideos
{
    String videoURL;

    public BeanYoutubeVideos()
    {

    }

    public BeanYoutubeVideos(String videoURL)
    {
        this.videoURL = videoURL;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }
}
