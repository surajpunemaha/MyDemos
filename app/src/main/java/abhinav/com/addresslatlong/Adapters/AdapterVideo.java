package abhinav.com.addresslatlong.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import java.util.ArrayList;

import abhinav.com.addresslatlong.BeanYoutubeVideos;
import abhinav.com.addresslatlong.R;

public class AdapterVideo extends RecyclerView.Adapter<AdapterVideo.DataHolder>
{
    private ArrayList<BeanYoutubeVideos> al_videoURL;
    private Activity activity;
    private int recv_id;

    public AdapterVideo(Activity activity, ArrayList<BeanYoutubeVideos> al_videoURL, int recv_id)
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
        View view = LayoutInflater.from(activity).inflate(R.layout.video_view, parent, false);
        return new DataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataHolder holder, final int position)
    {
        holder.videoWebView.loadData(al_videoURL.get(position).getVideoURL(), "text/html", "utf-8");
    }

    @Override
    public int getItemCount()
    {
        return al_videoURL.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder
    {
        WebView videoWebView;

        public DataHolder(View itemView)
        {
            super(itemView);

            videoWebView = (WebView) itemView.findViewById(R.id.videoWebView);
            videoWebView.getSettings().setJavaScriptEnabled(true);
            videoWebView.setWebChromeClient(new WebChromeClient(){

            });
        }
    }

}
