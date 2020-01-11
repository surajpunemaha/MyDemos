package abhinav.com.addresslatlong.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import abhinav.com.addresslatlong.BeanYoutubeVideos;
import abhinav.com.addresslatlong.R;

public class AdapterAPIVideo extends RecyclerView.Adapter<AdapterAPIVideo.DataHolder>
{
    private ArrayList<BeanYoutubeVideos> al_videoURL;
    private Activity activity;
    private int recv_id;
    private YouTubePlayer mYouTubePlayer;

    public AdapterAPIVideo(Activity activity, ArrayList<BeanYoutubeVideos> al_videoURL, int recv_id)
    {
        this.activity = activity;
        this.al_videoURL = al_videoURL;
        this.recv_id = recv_id;
    }

    @Override
    public int getItemViewType(int position)
    {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position)
    {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(activity).inflate(R.layout.youtube_video_view, parent, false);
        return new DataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataHolder holder, final int position)
    {
        holder.imgv_play.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                holder.imgv_play.setVisibility(View.GONE);
                String video_url = al_videoURL.get(position).getVideoURL();

                final String video_id = extractVideoIdFromUrl(video_url);

                holder.youtube_player.initialize("AIzaSyAYtzVLsbTj4LtVElRxShJ1ytv6xHEhVdU",
                new YouTubePlayer.OnInitializedListener()
                {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b)
                    {
                        mYouTubePlayer = youTubePlayer;
                        if (mYouTubePlayer != null)
                        {
                            try
                            {
                                mYouTubePlayer.loadVideo(video_id);
                                mYouTubePlayer.play();
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult)
                    {
                        System.out.println("Initialization failed");
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return al_videoURL.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder
    {
        YouTubePlayerView youtube_player;
        ImageView imgv_play;

        public DataHolder(View itemView)
        {
            super(itemView);

            youtube_player = (YouTubePlayerView) itemView.findViewById(R.id.youtube_player);
            imgv_play = (ImageView) itemView.findViewById(R.id.imgv_play);
        }
    }

    public String extractVideoIdFromUrl(String url)
    {
        final String[] videoIdRegex = { "\\?vi?=([^&]*)","watch\\?.*v=([^&]*)", "(?:embed|vi?)/([^/?]*)", "^([A-Za-z0-9\\-]*)"};
        String youTubeLinkWithoutProtocolAndDomain = youTubeLinkWithoutProtocolAndDomain(url);

        for(String regex : videoIdRegex) {
            Pattern compiledPattern = Pattern.compile(regex);
            Matcher matcher = compiledPattern.matcher(youTubeLinkWithoutProtocolAndDomain);

            if(matcher.find()){
                return matcher.group(1);
            }
        }

        return null;
    }

    private String youTubeLinkWithoutProtocolAndDomain(String url)
    {
        String youtubeURLRegEx = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/";
        Pattern compiledPattern = Pattern.compile(youtubeURLRegEx);
        Matcher matcher = compiledPattern.matcher(url);

        if(matcher.find()){
            return url.replace(matcher.group(), "");
        }
        return url;
    }

}
