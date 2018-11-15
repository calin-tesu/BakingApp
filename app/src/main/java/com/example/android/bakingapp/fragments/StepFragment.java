package com.example.android.bakingapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Calin Tesu on 11/7/2018.
 */
public class StepFragment extends Fragment {

    @BindView(R.id.step_description)
    TextView stepDescription;
    @BindView(R.id.thumbnail_step)
    ImageView thumbnailStepView;
    @BindView(R.id.player_view)
    SimpleExoPlayerView mPlayerView;

    Step currentStep;
    Uri mediaUri;
    //Store video time position
    private long videoTimePosition;

    private SimpleExoPlayer mExoPlayer;

    // Mandatory empty constructor
    public StepFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_step, container, false);

        ButterKnife.bind(this, rootView);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            currentStep = getArguments().getParcelable("currentStep");
        }

        if (currentStep != null) {
            stepDescription.setText(currentStep.getDescription());
            mediaUri = Uri.parse(currentStep.getVideoURL());
        }

        if (savedInstanceState != null)
            videoTimePosition = savedInstanceState.getLong("video_position");

        return rootView;
    }

    /**
     * Initialize ExoPlayer.
     *
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {

        //Only display the video if there is a valid url else display an image
        if (currentStep.getVideoURL() != null && !currentStep.getVideoURL().equals("")) {
            mPlayerView.setVisibility(View.VISIBLE);
            thumbnailStepView.setVisibility(View.INVISIBLE);
            if (mExoPlayer == null) {
                // Create an instance of the ExoPlayer.
                TrackSelector trackSelector = new DefaultTrackSelector();
                LoadControl loadControl = new DefaultLoadControl();
                mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
                mPlayerView.setUseController(false);
                mPlayerView.setPlayer(mExoPlayer);

                // Prepare the MediaSource.
                String userAgent = Util.getUserAgent(getActivity(), "bakingapp");
                MediaSource mediaSource = new ExtractorMediaSource(mediaUri,
                        new DefaultDataSourceFactory(getActivity(), userAgent),
                        new DefaultExtractorsFactory(), null, null);
                mExoPlayer.prepare(mediaSource);
                mExoPlayer.seekTo(videoTimePosition);
                mExoPlayer.setPlayWhenReady(true);
            }
        } else if (currentStep.getThumbnailURL() != null && !currentStep.getThumbnailURL().equals("")) {
            mPlayerView.setVisibility(View.INVISIBLE);
            thumbnailStepView.setVisibility(View.VISIBLE);
            Picasso.with(getActivity())
                    .load(currentStep.getThumbnailURL())
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .into(thumbnailStepView);
        } else {
            mPlayerView.setVisibility(View.INVISIBLE);
            thumbnailStepView.setVisibility(View.VISIBLE);
            thumbnailStepView.setImageResource(R.drawable.ic_broken_image_black_24dp);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        videoTimePosition = mExoPlayer.getCurrentPosition();
        if (Util.SDK_INT < 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) initializePlayer(mediaUri);
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) initializePlayer(mediaUri);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("video_position", videoTimePosition);
    }
}
