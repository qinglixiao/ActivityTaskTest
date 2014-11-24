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
		private Button start;// ��ʼ¼�ư�ť
		private Button stop;// ֹͣ¼�ư�ť
		private MediaRecorder mediarecorder;// ¼����Ƶ����
		private MSurface surfaceview;// ��ʾ��Ƶ�Ŀؼ�
		// ������ʾ��Ƶ��һ���ӿڣ��ҿ����û����У�Ҳ����˵��mediarecorder¼����Ƶ���ø������濴
		// ��͵͵¼��Ƶ��ͬѧ���Կ��Ǳ�İ취��������Ҫʵ������ӿڵ�Callback�ӿ�
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
			SurfaceHolder holder = surfaceview.getHolder();// ȡ��holder
			holder.addCallback(this); // holder����ص��ӿ�
			// setType�������ã�Ҫ������.
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
					mediarecorder = new MediaRecorder();// ����mediarecorder����
					// ����¼����ƵԴΪCamera(���)
					mediarecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
					// ����¼����ɺ���Ƶ�ķ�װ��ʽTHREE_GPPΪ3gp.MPEG_4Ϊmp4
					mediarecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
					// ����¼�Ƶ���Ƶ����h263 h264
					mediarecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
					// ������Ƶ¼�Ƶķֱ��ʡ�����������ñ���͸�ʽ�ĺ��棬���򱨴�
					mediarecorder.setVideoSize(176, 144);
					// ����¼�Ƶ���Ƶ֡�ʡ�����������ñ���͸�ʽ�ĺ��棬���򱨴�
					mediarecorder.setVideoFrameRate(20);
					mediarecorder.setPreviewDisplay(surfaceHolder.getSurface());
					// ������Ƶ�ļ������·��
					mediarecorder.setOutputFile("/sdcard/love.3gp");
					try {
						// ׼��¼��
						mediarecorder.prepare();
						// ��ʼ¼��
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
						// ֹͣ¼��
						mediarecorder.stop();
						// �ͷ���Դ
						mediarecorder.release();
						mediarecorder = null;
					}
				}

			}

		}
	}
	
	

}
