package com.inobu.mquestioner;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.inobu.mquestionair.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class login extends Activity {
	Button btLogin;
	EditText userNameET, passWordET;
	private TextView statusTV;
	ProgressBar webservicePG;
	static boolean errored = false;
//	String enumID = "9999";

	String enumID = null;
	
	StringBuffer buffer;
	ProgressDialog dialog = null;
//	String editTextUsername = "test";
	String editTextUsername = null;
//	String editTextPassword = "1234";
	String editTextPassword = null;
	String loginStatus;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();
	DbHelper db = new DbHelper(this);
	private static final String LOGIN_URL = "/getenumid.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	JSONObject json;
	UserFunctions userFunction = new UserFunctions();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		btLogin = (Button) findViewById(R.id.btnLogin);
		userNameET = (EditText) findViewById(R.id.txUser);
		passWordET = (EditText) findViewById(R.id.txpassword);
		statusTV = (TextView) findViewById(R.id.txPeringatan);
		webservicePG = (ProgressBar) findViewById(R.id.progressBar1);

		// Login button Click Event
		btLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (userNameET.getText().length() != 0
						&& userNameET.getText().toString() != "") {
					if (passWordET.getText().length() != 0
							&& passWordET.getText().toString() != "") {
						editTextUsername = userNameET.getText().toString();
						editTextPassword = passWordET.getText().toString();
						
						//offline mode
//						db.insertUser(editTextUsername, editTextPassword, enumID);
//						Intent i = new Intent(login.this, HalamanUtama.class);
//						startActivity(i);
						
						statusTV.setText("");
						btLogin.setEnabled(false);
						new AttemptLogin().execute();
					} else {
						statusTV.setText("Please enter Password");
					}
				} else {
					statusTV.setText("Please enter Username");
				}
			}
		});
	}

	class AttemptLogin extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			webservicePG.setVisibility(View.VISIBLE);
		}

		@Override
		protected String doInBackground(String... args) {
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("login", editTextUsername));
				params.add(new BasicNameValuePair("password", editTextPassword));

				Log.d("request!", "starting");
				json = jsonParser.makeHttpRequest(db.getURL() + LOGIN_URL,
						"POST", params);

				Log.d("Login attempt", json.toString());

				JSONArray jsonArrayChanged = json
						.getJSONArray("swd_enumerator");

				for (int i = 0; i < jsonArrayChanged.length(); i++) {
					JSONObject jsonOBject = jsonArrayChanged.getJSONObject(i);
					Log.d("JSON ",
							"json (" + i + ") = " + jsonOBject.toString());
					enumID = jsonOBject.getString("enum_id");
					Log.d("Login Successful!", enumID);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;

		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			webservicePG.setVisibility(View.INVISIBLE);

			if (!enumID.equals("0000")) {
				Log.d("save user ", editTextUsername + "" + editTextPassword
						+ "" + enumID);
				userFunction.logoutUser(getApplicationContext());
				db.insertUser(editTextUsername, editTextPassword, enumID);
				btLogin.setEnabled(true);
				statusTV.setText("Login Successful!");
				Intent i = new Intent(login.this, HalamanUtama.class);
				startActivity(i);
				//i.putExtra("nama", editTextUsername);
				finish();
			} else if (enumID.equals("0000")) {
				Toast.makeText(getActivity(), "Login Failed, try again",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	public Context getActivity() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void hideSoftKeyboard(Activity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager) activity
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
				.getWindowToken(), 0);
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
		switch (item.getItemId()) {
		case R.id.search:
			Intent intent = new Intent(getApplicationContext(), setting.class);
			startActivity(intent);
			break;
		}
		return false;
	}
}