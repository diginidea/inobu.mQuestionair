package com.inobu.mquestioner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import com.inobu.mquestionair.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class setting extends Activity {
	Button btbackup;
	Button btRestore;

	Button btSave;
	EditText txtUrl;
	DbHelper db = new DbHelper(this);
	final Context context = this;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);

		btSave = (Button) findViewById(R.id.btSaveSetting);
		txtUrl = (EditText) findViewById(R.id.txUrl);
		txtUrl.setText(db.getURL());

		btSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				db.updateURL(1, txtUrl.getText().toString());

				Toast.makeText(setting.this, "URL Save", Toast.LENGTH_LONG)
						.show();

				Intent intent = new Intent(context, HalamanUtama.class);
				startActivity(intent);
			}
		});

		// btbackup=(Button)findViewById(R.id.btBackup);
		// btRestore=(Button)findViewById(R.id.btRestore);
		//
		// btbackup.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// exportDB();
		// // Intent intent = new Intent(context, HalamanUtama.class);
		// // startActivity(intent);
		// }
		//
		// });
		//
		// btRestore.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// importDB();
		// Intent intent = new Intent(context, HalamanUtama.class);
		// startActivity(intent);
		// }
		//
		// });
	}

	// private void exportDB() {
	// try {
	// File sd = Environment.getExternalStorageDirectory();
	// File data = Environment.getDataDirectory();
	//
	// if (sd.canWrite()) {
	// String currentDBPath = "//data//" + "inobu.mQuestioner"
	// + "//databases//" + "mQuestionair.db";
	// String backupDBPath = "//sdcard//inobuBackup//";
	// File currentDB = new File(data, currentDBPath);
	// File backupDB = new File(sd, backupDBPath);
	//
	// FileChannel src = new FileInputStream(currentDB).getChannel();
	// FileChannel dst = new FileOutputStream(backupDB).getChannel();
	// dst.transferFrom(src, 0, src.size());
	// src.close();
	// dst.close();
	// Toast.makeText(getApplicationContext(), "Backup Successful!",
	// Toast.LENGTH_SHORT).show();
	//
	// }
	// } catch (Exception e) {
	//
	// Toast.makeText(getApplicationContext(), "Backup Failed!",
	// Toast.LENGTH_SHORT)
	// .show();
	//
	// }
	// }
	//
	// private void importDB() {
	// try {
	// File sd = Environment.getExternalStorageDirectory();
	// File data = Environment.getDataDirectory();
	// if (sd.canWrite()) {
	// String currentDBPath = "//data//" + "inobu.mQuestioner"
	// + "//databases//" + "mQuestionair.db";
	// String backupDBPath = "//sdcard//inobuBackup//"; // From SD directory.
	// File backupDB = new File(data, currentDBPath);
	// File currentDB = new File(sd, backupDBPath);
	//
	// FileChannel src = new FileInputStream(currentDB).getChannel();
	// FileChannel dst = new FileOutputStream(backupDB).getChannel();
	// dst.transferFrom(src, 0, src.size());
	// src.close();
	// dst.close();
	// Toast.makeText(getApplicationContext(), "Import Successful!",
	// Toast.LENGTH_SHORT).show();
	//
	// }
	// } catch (Exception e) {
	//
	// Toast.makeText(getApplicationContext(), "Import Failed!",
	// Toast.LENGTH_SHORT)
	// .show();
	//
	// }
	// }
}
