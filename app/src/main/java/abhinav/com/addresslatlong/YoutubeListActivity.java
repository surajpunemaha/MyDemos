package abhinav.com.addresslatlong;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.youtube.player.YouTubeBaseActivity;

import java.util.ArrayList;

import abhinav.com.addresslatlong.Adapters.AdapterAPIVideo;
import abhinav.com.addresslatlong.Adapters.AdapterVideo;

public class YoutubeListActivity extends YouTubeBaseActivity
{
    RecyclerView recyclerview;
    ArrayList<BeanYoutubeVideos> youtubeVideos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_list);

        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        //setWebViewData();
        setListUsingAPI();
    }

    public void setWebViewData()
    {
        youtubeVideos.clear();
        youtubeVideos.add( new BeanYoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/k80dLDdzkdY\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new BeanYoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/Br19BUAkGJQ\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new BeanYoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/tkOGeNoFD5o\" frameborder=\"0\" allowfullscreen></iframe>") );

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        AdapterVideo videoAdapter = new AdapterVideo(YoutubeListActivity.this, youtubeVideos, recyclerview.getId());
        recyclerview.setAdapter(videoAdapter);
    }

    public void setListUsingAPI()
    {
        youtubeVideos.clear();
        youtubeVideos.add(new BeanYoutubeVideos("https://www.youtube.com/watch?v=tkOGeNoFD5o&t=21s"));
        youtubeVideos.add(new BeanYoutubeVideos("https://www.youtube.com/watch?v=Zq7mN8oi8ds"));
        youtubeVideos.add(new BeanYoutubeVideos("https://www.youtube.com/watch?v=ww68GN-CQMY"));
        youtubeVideos.add(new BeanYoutubeVideos("https://www.youtube.com/watch?v=n-YiJq4Xj_c"));
        youtubeVideos.add(new BeanYoutubeVideos("https://www.youtube.com/watch?v=Br19BUAkGJQ"));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        AdapterAPIVideo adapterAPIVideo = new AdapterAPIVideo(YoutubeListActivity.this, youtubeVideos,
                recyclerview.getId());
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setAdapter(adapterAPIVideo);
    }
}
