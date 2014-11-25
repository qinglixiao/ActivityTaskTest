package com.example.activitytasktest;

import java.io.IOException;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements SurfaceHolder.Callback {
		private Button start;// 开始录制按钮
		private Button stop;// 停止录制按钮
		private MediaRecorder mediarecorder;// 录制视频的类
		private MSurface surfaceview;// 显示视频的控件
		// 用来显示视频的一个接口，我靠不用还不行，也就是说用mediarecorder录制视频还得给个界面看
		// 想偷偷录视频的同学可以考虑别的办法。。嗯需要实现这个接口的Callback接口
		private SurfaceHolder surfaceHolder;
		private View rootView;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			rootView = inflater.inflate(R.layout.fragment_main, container, false);
			Button button = (Button) rootView.findViewById(R.id.btn_start);
			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getActivity(), FirstActivity.class);
					startActivity(intent);
				}
			});
			initMedia();
			return rootView;
		}

		private void initMedia() {
			start = (Button) rootView.findViewById(R.id.btn_start_rec);
			stop = (Button) rootView.findViewById(R.id.btn_stop);
			start.setOnClickListener(new TestVideoListener());
			stop.setOnClickListener(new TestVideoListener());
			surfaceview = (MSurface) rootView.findViewById(R.id.mSurface1);
			SurfaceHolder holder = surfaceview.getHolder();// 取得holder
			holder.addCallback(this); // holder加入回调接口
			// setType必须设置，要不出错.
// holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			 surfaceHolder = holder;  
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			// TODO Auto-generated method stub
			Log.d("LX", "surfaceChanged");
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub

		}

		class TestVideoListener implements OnClickListener {

			@Override
			public void onClick(View v) {
				if (v.getId() == start.getId()) {
					mediarecorder = new MediaRecorder();// 创建mediarecorder对象
					// 设置录制视频源为Camera(相机)
					mediarecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
					// 设置录制完成后视频的封装格式THREE_GPP为3gp.MPEG_4为mp4
					mediarecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
					// 设置录制的视频编码h263 h264
					mediarecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
					// 设置视频录制的分辨率。必须放在设置编码和格式的后面，否则报错
					mediarecorder.setVideoSize(176, 144);
					// 设置录制的视频帧率。必须放在设置编码和格式的后面，否则报错
					mediarecorder.setVideoFrameRate(20);
					mediarecorder.setPreviewDisplay(surfaceHolder.getSurface());
					// 设置视频文件输出的路径
					mediarecorder.setOutputFile("/sdcard/love.3gp");
					try {
						// 准备录制
						mediarecorder.prepare();
						// 开始录制
						mediarecorder.start();
					}
					catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (v == stop) {
					if (mediarecorder != null) {
						// 停止录制
						mediarecorder.stop();
						// 释放资源
						mediarecorder.release();
						mediarecorder = null;
					}
				}

			}

		}
	}
	
	

}
