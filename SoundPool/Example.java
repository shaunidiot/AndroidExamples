package com.shaunidiot.skrillex;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SkrillexNSSMActivity extends Activity {
	/** Called when the activity is first created. */
	/** Called when the activity is first created. */
	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundsMap;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 100);
		soundsMap = new HashMap<Integer, Integer>();
		soundsMap.put(1, soundPool.load(this, R.raw.one, 1)); // where one is your audio file in /res/raw folder.
		soundsMap.put(2, soundPool.load(this, R.raw.two, 1));// where two is your audio file in /res/raw folder.
		Button btn1 = (Button) findViewById(R.id.button1);
		btn1.setOnClickListener(bOnClick);
		Button btn2 = (Button) findViewById(R.id.button2);
		btn2.setOnClickListener(bOnClick);
	}

	public void playSound(int sound, float fSpeed) {
		AudioManager mgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = mgr
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = mgr
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = streamVolumeCurrent / streamVolumeMax;

		soundPool.play(soundsMap.get(sound), volume, volume, 1, 0, fSpeed);
	}

	View.OnClickListener bOnClick = new View.OnClickListener() {
		float fSpeed = 1.0f;

		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.button1:
				playSound(1, fSpeed); //play it!
			break;

			case R.id.button2:
				playSound(2, fSpeed);
				break;
			}
		}
	};

	public void onDestroy() {
		super.onDestroy();
	}

}