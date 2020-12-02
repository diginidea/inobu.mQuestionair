package set.mquestioner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class SetBibit extends Activity {
	RadioButton rbbDinas, rbbKUD, rbbUsaha, rbbSemai, rbbBeli, rbbLain;
	RadioButton rbbNPK, rbbUrea, rbbBorat;
	RadioButton rbbya, rbbtidak, rbbtdktau;
	RadioButton rbbHibrida, rbbBukanH, rbbTidaktau;
	RadioGroup rgAsal, rgBbt, rgHib;
	EditText txtKUD, txtUsaha, txtAsalLain;
	Button butNext;
	TextView shapeIDs;

	String asalBibitchk, asalBibit;
	String jenisBibit;
	String sertifikat;
	String ShapeIDL;
	boolean isUpdate;
	com.inobu.mquestioner.DbHelper db = new com.inobu.mquestioner.DbHelper(this);
	String getKosong = null;
	Context context = this;
	int id_kk = 0;
	String fstatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bibit);
		// Log.d("asal","bibit");
		// getActionBar().setTitle("Pembibitan");

		asalBibitchk = "Dinas Perkebunan";
		jenisBibit = "Hibrida";
		sertifikat = "Ya";

		shapeIDs = (TextView) findViewById(R.id.shapesIDs);
		rbbDinas = (RadioButton) findViewById(R.id.rbDinasKebunBibit);
		rbbKUD = (RadioButton) findViewById(R.id.rbKUDBibit);
		rbbUsaha = (RadioButton) findViewById(R.id.rbPerusahaanbibit);
		rbbSemai = (RadioButton) findViewById(R.id.rbSemaibibit);
		rbbBeli = (RadioButton) findViewById(R.id.rbBelibibit);

		rgAsal = (RadioGroup) findViewById(R.id.rgAsalBibit);
		rgBbt = (RadioGroup) findViewById(R.id.rgBibitbibit);
		rgHib = (RadioGroup) findViewById(R.id.rgSertifikatbibit);

		rbbya = (RadioButton) findViewById(R.id.rbSertYabibit);
		rbbtidak = (RadioButton) findViewById(R.id.rbSertTdkbibit);
		rbbtdktau = (RadioButton) findViewById(R.id.rbSertTdkTahubibit);

		rbbHibrida = (RadioButton) findViewById(R.id.rbHibridabibit);
		rbbBukanH = (RadioButton) findViewById(R.id.rbBukanHbibit);
		rbbTidaktau = (RadioButton) findViewById(R.id.rbhtidaktaubibit);

		txtKUD = (EditText) findViewById(R.id.txKUDbibit);
		txtUsaha = (EditText) findViewById(R.id.txPerusahaanbibit);
		txtAsalLain = (EditText) findViewById(R.id.txAsalLainbibit);
		butNext = (Button) findViewById(R.id.btBibitNextbibit);

		// rbbDinas.setChecked(true);
		// rbbHibrida.setChecked(true);
		// rbbya.setChecked(true);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			isUpdate = getIntent().getExtras().getBoolean("updateList");

			if (isUpdate) {
				txtKUD.setEnabled(false);
				txtUsaha.setEnabled(false);
				txtAsalLain.setEnabled(false);

				id_kk = Integer.parseInt(getIntent().getExtras().getString(
						"rowID"));
				fstatus = getIntent().getExtras().getString("status");

				ShapeIDL = getIntent().getExtras().getString("shapeid");
				butNext.setText("Save");
				String dinas_kebun = getIntent().getExtras().getString(
						"asalbbtchk");
				// rbbDinas.setChecked(false);
				if (dinas_kebun.contains("Dinas Perkebunan")) {
					rgAsal.check(R.id.rbDinasKebunBibit);
				} else if (dinas_kebun.contains("KUD")) {
					rgAsal.check(R.id.rbKUDBibit);
					txtKUD.setEnabled(true);
					txtKUD.setText(getIntent().getExtras().getString("asalbbt"));
				} else if (dinas_kebun.equals("Perusahaan")) {
					rgAsal.check(R.id.rbPerusahaanbibit);
					txtUsaha.setEnabled(true);
					txtUsaha.setText(getIntent().getExtras().getString(
							"asalbbt"));
				} else if (dinas_kebun.equals("Semai")) {
					rgAsal.check(R.id.rbSemaibibit);
				} else {
					rgAsal.check(R.id.rbBelibibit);
					txtAsalLain.setEnabled(true);
					txtAsalLain.setText(getIntent().getExtras().getString(
							"asalbbt"));
				}

				String hibrida = getIntent().getExtras().getString("jenisbbt");
				// rbbHibrida.setChecked(false);
				if (hibrida.equals("Hibrida")) {
					rgBbt.check(R.id.rbHibridabibit);
				} else if (hibrida.equals("Bukan Hibrida")) {
					Log.d("hibrida", hibrida);
					rgBbt.check(R.id.rbBukanHbibit);
				} else if (hibrida.equals("Tidak Tahu")) {
					Log.d("hibrida", hibrida);
					rgBbt.check(R.id.rbhtidaktaubibit);
				}

				String jkhibrida = getIntent().getExtras().getString("sertf");
				// rbbya.setChecked(false);
				if (jkhibrida.equals("Ya")) {
					rgHib.check(R.id.rbSertYabibit);
				} else if (jkhibrida.equals("Tidak")) {
					rgHib.check(R.id.rbSertTdkbibit);
				} else if (jkhibrida.equals("Tidak Tahu")) {
					rgHib.check(R.id.rbSertTdkTahubibit);
				}
			} else {
				ShapeIDL = getIntent().getExtras().getString("shapeid");
				getKosong = getIntent().getExtras().getString("kosong");
				Log.d("getKosong2 Response", getKosong);
			}

			shapeIDs.setText(ShapeIDL);
		}

		rgAsal.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rbDinasKebunBibit:
					asalBibitchk = "Dinas Perkebunan";
					asalBibit = "";
					txtKUD.setEnabled(false);
					txtUsaha.setEnabled(false);
					txtAsalLain.setEnabled(false);
					break;
				case R.id.rbKUDBibit:
					asalBibitchk = "KUD";
					txtKUD.setEnabled(true);
					txtUsaha.setEnabled(false);
					txtAsalLain.setEnabled(false);
					if (txtKUD.getText().toString().isEmpty()) {
						getKosong = "kosong";
						asalBibit = "";
					} else {
						asalBibit = txtKUD.getText().toString();
					}
					break;
				case R.id.rbPerusahaanbibit:
					asalBibitchk = "Perusahaan";
					txtKUD.setEnabled(false);
					txtUsaha.setEnabled(true);
					txtAsalLain.setEnabled(false);
					if (txtUsaha.getText().toString().isEmpty()) {
						getKosong = "kosong";
						asalBibit = "";
					} else {
						asalBibit = txtUsaha.getText().toString();
					}
					break;
				case R.id.rbSemaibibit:
					txtKUD.setEnabled(false);
					txtUsaha.setEnabled(false);
					txtAsalLain.setEnabled(false);
					asalBibitchk = "Semai Sendiri";
					asalBibit = "";
					break;
				case R.id.rbBelibibit:
					txtKUD.setEnabled(false);
					txtUsaha.setEnabled(false);
					txtAsalLain.setEnabled(true);
					if (txtAsalLain.getText().toString().isEmpty()) {
						getKosong = "kosong";
						asalBibit = "lainnya";
					} else {
						asalBibitchk = "";
						asalBibit = txtAsalLain.getText().toString();
					}
					break;
				}
			}
		});

		rgBbt.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rbHibridabibit:
					jenisBibit = "Hibrida";
					break;
				case R.id.rbBukanHbibit:
					jenisBibit = "Bukan Hibrida";
					break;
				case R.id.rbhtidaktaubibit:
					jenisBibit = "Tidak Tahu";
					break;
				}
			}
		});

		rgHib.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rbSertYabibit:
					sertifikat = "Ya";
					break;
				case R.id.rbSertTdkbibit:
					sertifikat = "Tidak";
					break;
				case R.id.rbSertTdkTahubibit:
					sertifikat = "Tidak Tahu";
					break;
				}
			}
		});

		butNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (rgAsal.getCheckedRadioButtonId()) {
				case R.id.rbDinasKebunBibit:
					asalBibitchk = "Dinas Perkebunan";
					asalBibit = "";
					break;
				case R.id.rbKUDBibit:
					asalBibitchk = "KUD";
					asalBibit = txtKUD.getText().toString();
					break;
				case R.id.rbPerusahaanbibit:
					asalBibitchk = "Perusahaan";
					asalBibit = txtUsaha.getText().toString();
					break;
				case R.id.rbSemaibibit:
					asalBibitchk = "Semai Sendiri";
					asalBibit = "";
					break;
				case R.id.rbBelibibit:
					asalBibitchk = "";
					asalBibit = txtAsalLain.getText().toString();
					break;
				default:
					break;
				}

				Intent intent = null;
				db.updateBibit(ShapeIDL, asalBibitchk, asalBibit, jenisBibit,
						sertifikat);

				if (isUpdate) {
					intent = new Intent(SetBibit.this, list.mquestioner.ListLahan.class);
					intent.putExtra("idKK", Integer.toString(id_kk));
					intent.putExtra("status", fstatus);
				} else {
					intent = new Intent(SetBibit.this, SetPupuk.class);
					intent.putExtra("kosong", getKosong);
					intent.putExtra("shapeid", ShapeIDL);
				}

				startActivity(intent);
			}
		});
	}

	@Override
	public void onBackPressed() {
		if (isUpdate) {
			finish();
		} else {
			Intent i = new Intent(getApplicationContext(), SetLahan.class);
			i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(i);
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.home:
			goExit();
			return true;
		case R.id.datalist:
			goListData();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void goExit() {
		AlertDialog.Builder alert = new AlertDialog.Builder(SetBibit.this);
		alert.setTitle(R.string.errorTitle);
		alert.setMessage("Anda Ingin keluar ?");
		alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Intent intent = null;
				intent = new Intent(context, com.inobu.mquestioner.HalamanUtama.class);
				Log.d("update Response", "update");
				// db.updateBibit(ShapeIDL, asalBibitchk, asalBibit, jenisBibit,
				// sertifikat);
				startActivity(intent);
			}

		});
		alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		alert.show();
	}

	public void goListData() {
		AlertDialog.Builder alert = new AlertDialog.Builder(SetBibit.this);
		alert.setTitle(R.string.errorTitle);
		alert.setMessage("Anda Ingin keluar ?");
		alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Intent intent = null;
				intent = new Intent(context, com.inobu.mquestioner.HalamanUtama.class);
				Log.d("update Response", "update");
				// db.updateBibit(ShapeIDL, asalBibitchk, asalBibit, jenisBibit,
				// sertifikat);
				startActivity(intent);
			}
		});
		alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		alert.show();
	}
}
