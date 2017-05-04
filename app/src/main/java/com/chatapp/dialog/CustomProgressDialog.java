package com.chatapp.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.chatapp.R;


public class CustomProgressDialog extends ProgressDialog implements
		View.OnClickListener {
	private ViewGroup mContainer;
	private ImageView mImageView, mImageView1;
	private Context context;
	private boolean isBackOfCardShowing = true;
	private Handler handler = new Handler();

	public CustomProgressDialog(Context context) {

		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_dialog_layout);
		mImageView = (ImageView) findViewById(R.id.picture);
		mImageView1 = (ImageView) findViewById(R.id.picture1);
		mContainer = (ViewGroup) findViewById(R.id.container);
		// mImageView.setClickable(true);
		// mImageView.setFocusable(true);
		mImageView.setOnClickListener(this);

		// mImageView1.setClickable(true);
		// mImageView1.setFocusable(true);
		mImageView1.setOnClickListener(this);

		// Since we are caching large views, we want to keep their cache
		// between each animation
		mContainer
				.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				applyRotation(0, 0, 90);
			}
		}, 300);
		// applyRotation(0, 0, 90);
		// applyRotation(-1, 0, 90);
	}

	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		super.dismiss();

	}

	private void applyRotation(int position, float start, float end) {
		// Find the center of the container
		final float centerX = mContainer.getWidth() / 2.0f;
		final float centerY = mContainer.getHeight() / 2.0f;

		// Create a new 3D rotation with the supplied parameter
		// The animation listener is used to trigger the next animation
		final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end,
				centerX, centerY, 310.0f, true);
		rotation.setDuration(500);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new AccelerateInterpolator());
		rotation.setAnimationListener(new DisplayNextView(position));

		mContainer.startAnimation(rotation);
	}

	/**
	 * This class listens for the end of the first half of the animation. It
	 * then posts a new action that effectively swaps the views when the
	 * container is rotated 90 degrees and thus invisible.
	 */
	private final class DisplayNextView implements AnimationListener {
		private final int mPosition;

		private DisplayNextView(int position) {
			mPosition = position;
		}

		public void onAnimationStart(Animation animation) {
		}

		public void onAnimationEnd(Animation animation) {
			mContainer.post(new SwapViews(mPosition));
		}

		public void onAnimationRepeat(Animation animation) {
		}
	}

	/**
	 * This class is responsible for swapping the views and start the second
	 * half of the animation.
	 */
	private final class SwapViews implements Runnable {
		private final int mPosition;

		public SwapViews(int position) {
			mPosition = position;
		}

		public void run() {
			final float centerX = mContainer.getWidth() / 2.0f;
			final float centerY = mContainer.getHeight() / 2.0f;
			Rotate3dAnimation rotation;

			if (mPosition > -1) {
				mImageView.setVisibility(View.GONE);
				mImageView1.setVisibility(View.VISIBLE);
				mImageView1.requestFocus();

				rotation = new Rotate3dAnimation(270, 360, centerX, centerY,
						310.0f, false);
			} else {
				mImageView1.setVisibility(View.GONE);
				mImageView.setVisibility(View.VISIBLE);
				mImageView.requestFocus();

				rotation = new Rotate3dAnimation(270, 360, centerX, centerY,
						310.0f, false);
			}

			rotation.setDuration(500);
			rotation.setFillAfter(true);
			rotation.setInterpolator(new DecelerateInterpolator());
			rotation.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub

					if (isBackOfCardShowing) {

						applyRotation(-1, 0, 90);

					} else {

						applyRotation(0, 0, 90);
					}
					isBackOfCardShowing = !isBackOfCardShowing;
				}
			});

			mContainer.startAnimation(rotation);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		// if (v == mImageView) {
		// applyRotation(0, 0, 90);
		// } else if (v == mImageView1) {
		// // applyRotation(-1, 180, 270);
		// applyRotation(-1, 0, 90);
		// }
	}
}
