package com.xiaoshihua.thinkpad.democnode.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.xiaoshihua.thinkpad.democnode.R;
import com.xiaoshihua.thinkpad.democnode.ui.base.StatusBarActivity;
import com.xiaoshihua.thinkpad.democnode.ui.listener.NavigationFinishClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

public class ImagePreviewActivity extends StatusBarActivity {

    private static final String EXTRA_IMAGE_URL = "imageUrl";
    @BindView(R.id.photo_view)
    PhotoView photoView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress_wheel)
    ProgressWheel progressWheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

        loadImageAsyncTask();

    }

    private void loadImageAsyncTask() {

        progressWheel.spin();

        Glide.with(this).load(getIntent().getStringExtra(EXTRA_IMAGE_URL))
                .error(R.drawable.image_error)
                .dontAnimate()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressWheel.stopSpinning();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressWheel.stopSpinning();
                        return false;
                    }
                }).into(photoView);
    }

    public static void start(Context context, String imageUrl) {
        Intent intent = new Intent(context, ImagePreviewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_IMAGE_URL, imageUrl);
        context.startActivity(intent);
    }
}
