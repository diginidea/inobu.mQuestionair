package com.inobu.mquestioner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.inobu.mquestionair.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
//import android.view.Menu;
//import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HalamanUtama extends Activity {
	Button butInput, butViewData, butGPS;
	TextView txcektime, txnama;
	TextView txrandom;
	
	int cNara = 0;
	int cLahan = 0;
	String txNara = "";
	String txLahan = "";
	//String namaUser = null;

	final Context context = this;
	UserFunctions userFunctions = new UserFunctions();
	DbHelper db = new DbHelper(this);
	Boolean isInternetPresent = false;
	ConnectionDetector cd;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.halaman_utama);
		txnama = (TextView) findViewById(R.id.txUser);
		txrandom = (TextView) findViewById(R.id.txRandom);
		
		txrandom.setText(String.valueOf(db.getRandom(99999,00000)));
		
		cd = new ConnectionDetector(getApplicationContext());

//		Bundle extras = getIntent().getExtras();
//		if (extras != null) {
//			namaUser = getIntent().getExtras().getString("nama");
			txnama.setText(db.getUser());
//		}

		if (userFunctions.isUserLoggedIn(getApplicationContext())) {
			getCurrentDate();
			butInput = (Button) findViewById(R.id.btInputData);
			butInput.setOnClickListener(inputdataClick);
			butViewData = (Button) findViewById(R.id.btViewData);
			txcektime = (TextView) findViewById(R.id.cektime);
			butViewData.setOnClickListener(viewdataClick);

			cekTime();
		} else {
			Intent login = new Intent(getApplicationContext(), login.class);
			login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(login);
			finish();
		}
	}

	Button.OnClickListener inputdataClick = new Button.OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(context, set.mquestioner.SetDesa.class);
			startActivity(intent);
		}
	};

	Button.OnClickListener viewdataClick = new Button.OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(context,
					list.mquestioner.ListNarasumber.class);
			startActivity(intent);
		}
	};

	void getCurrentDate() {
		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());

		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		String formattedDate = df.format(c.getTime());
		System.out.println(formattedDate);

		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat.format(cal.getTime())); // 2014/08/06
																// 16:00:22
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent intent = new Intent(getApplicationContext(), setting.class);
			startActivity(intent);
			break;
		case R.id.logout:
			userFunctions.logoutUser(getApplicationContext());
			Intent in = new Intent(getApplicationContext(), login.class);
			in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(in);
			finish();
			break;
		}
		return false;
	}

	public void cekTime() {
		String currentDateTime = DateFormat.getDateTimeInstance().format(
				new Date());
		txcektime.setText(currentDateTime);

		cNara = db.cekSentNarasumber();
		if (cNara > 0) {
			txNara = "Data narasumber belum upload";
		}

		cLahan = db.cekSentLahan();
		if (cLahan > 0) {
			txLahan = "Data lahan belum upload";
		}

		txcektime.setText(txNara + "\n" + txLahan);
	}
}
