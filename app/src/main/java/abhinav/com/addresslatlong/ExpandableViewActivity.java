package abhinav.com.addresslatlong;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;

public class ExpandableViewActivity extends AppCompatActivity implements View.OnClickListener
{
    LinearLayout expandable_layout;
    Button btn_clickme;
    boolean is_expanded = false;
    int h1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandalbe_view);

        btn_clickme = (Button) findViewById(R.id.btn_clickme);
        btn_clickme.setOnClickListener(this);

        expandable_layout = (LinearLayout) findViewById(R.id.expandable_layout);

        expandable_layout.getViewTreeObserver().addOnGlobalLayoutListener(
        new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                h1 = expandable_layout.getHeight();
                expandable_layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.btn_clickme)
        {
            if(is_expanded)
            {
                is_expanded = false;
                expand(expandable_layout, h1);
            }
            else
            {
                is_expanded = true;
                collapse(expandable_layout);
            }
        }
    }

    private void expand(View summary,int height)
    {
        summary.setVisibility(View.VISIBLE);
        final int widthSpec = View.MeasureSpec.makeMeasureSpec(1,View.MeasureSpec.UNSPECIFIED);
        summary.measure(widthSpec, 300);
        ValueAnimator mAnimator;
        mAnimator = slideAnimator(0, height, summary);
        mAnimator.start();
    }

    private void collapse(final View summary)
    {
        int finalHeight = summary.getHeight();
        ValueAnimator mAnimator = slideAnimator(finalHeight, 0, summary);
        mAnimator.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationEnd(Animator animator){
                summary.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animator){
            }

            @Override
            public void onAnimationCancel(Animator animator){
            }

            @Override
            public void onAnimationRepeat(Animator animator){
            }
        });
        mAnimator.start();
    }

    private ValueAnimator slideAnimator(int start, int end, final View summary)
    {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator)
            {
                // Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = summary.getLayoutParams();
                layoutParams.height = value;
                summary.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }
}
