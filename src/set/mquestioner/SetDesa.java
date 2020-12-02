package set.mquestioner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class SetDesa extends Activity implements OnItemSelectedListener {
	com.inobu.mquestioner.DbHelper db = new com.inobu.mquestioner.DbHelper(this);
	List<String> list;
	ArrayAdapter<String> adapProf;
	ArrayAdapter<String> adapKab;
	ArrayAdapter<String> adapKec;
	ArrayAdapter<String> adapDesa;

	TextView txtdesa, txtKec, txtKab;
	Button butNext;
	final Context context = this;
	Spinner spDesa, spKec, spKab, spProv;
	String Desa = null;
	String Kabupaten = null;
	String Kecamatan = null;
	String Provinsi = null;

	String pDesa = null;
	String pKabupaten = null;
	String pKecamatan = null;
	String pProvinsi = null;

	long selectDesa = 0;
	long selectKec = 0;
	long selectKab = 0;
	long selectProv = 0;

	int id_kk = 0;
	String idkk_ = null;

	private boolean isUpdate;
	private String UpFrom = "normal";
	private String fdesa, fkeca, fkabu, fprov;
	String formattedDate = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_main);

		spDesa = (Spinner) findViewById(R.id.txDesa);
		spKec = (Spinner) findViewById(R.id.txkec);
		spKab = (Spinner) findViewById(R.id.txKab);
		spProv = (Spinner) findViewById(R.id.txProv);

		butNext = (Button) findViewById(R.id.btNext);

		Bundle extras = getIntent().getExtras();
		loadSpinnerProv();

		spProv.setOnItemSelectedListener(this);
		spKab.setOnItemSelectedListener(this);
		spKec.setOnItemSelectedListener(this);
		spDesa.setOnItemSelectedListener(this);

		if (extras != null) {
			idkk_ = getIntent().getExtras().getString("idKK");
			Log.d("get idKK : ", idkk_);
			id_kk = Integer.parseInt(idkk_);

			isUpdate = getIntent().getExtras().getBoolean("updateList");
			UpFrom = getIntent().getExtras().getString("inputBack");
			Log.d("UpFrom : ", UpFrom);

			if (isUpdate) {
				butNext.setText("Save");

				fdesa = getIntent().getExtras().getString("desa");
				fkeca = getIntent().getExtras().getString("keca");
				fkabu = getIntent().getExtras().getString("kabu");
				fprov = getIntent().getExtras().getString("prov");
				Log.d("Main Response", fdesa + " " + fkeca + " " + fkabu + " "
						+ fprov);

				for (int i = 0; i < adapProf.getCount(); i++) {
					if (fprov.equals(adapProf.getItem(i).toString())) {
						spProv.setSelection(i);
						loadSpinnerKab(adapProf.getItem(i).toString());
						Log.d("Prov ", adapProf.getItem(i).toString());
						break;
					}
				}

				for (int i = 0; i < adapKab.getCount(); i++) {
					if (fkabu.equals(adapKab.getItem(i).toString())) {
						spKab.setSelection(i);
						loadSpinnerKec(adapKab.getItem(i).toString());
						Log.d("kabu ", adapKab.getItem(i).toString());
						break;
					}
				}

				for (int i = 0; i < adapKec.getCount(); i++) {
					if (fkeca.equals(adapKec.getItem(i).toString())) {
						spKec.setSelection(i);
						loadSpinnerDesa(adapKec.getItem(i).toString());
						Log.d("kece ", adapKec.getItem(i).toString());
						break;
					}
				}
			}
		}

		butNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("UpFrom ", UpFrom);
				if (spDesa != null && spDesa.getSelectedItem() != null
						&& spKec != null && spKec.getSelectedItem() != null
						&& spKab != null && spKab.getSelectedItem() != null
						&& spProv != null && spProv.getSelectedItem() != null) {
					Intent intent = null;
					Desa = spDesa.getSelectedItem().toString();
					Kabupaten = spKab.getSelectedItem().toString();
					Kecamatan = spKec.getSelectedItem().toString();
					Provinsi = spProv.getSelectedItem().toString();

					if (UpFrom == "normal") {
						db.insertDesa(Desa, Kecamatan, Kabupaten, Provinsi);
						intent = new Intent(context, SetNarasumber.class);
						intent.putExtra("desaID", db.getDesaID(Desa) + "");
					} else {
						db.updateDesa(id_kk, Desa, Kecamatan, Kabupaten,
								Provinsi);
						intent = new Intent(context, list.mquestioner.ListNarasumber.class);
					}
					startActivity(intent);
				} else {
					Toast.makeText(getBaseContext(), "Pilih field yang Kosong",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	void getCurrentDate() {
		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());

		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		formattedDate = df.format(c.getTime());
		System.out.println(formattedDate);

		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat.format(cal.getTime())); // 2014/08/06
																// 16:00:22
	}

	private void loadSpinnerDesa(String camat) {
		// here i used Set Because Set doesn't allow duplicates.
		Set<String> set = db.getAllDesa(camat);

		List<String> list = new ArrayList<String>(set);
		Collections.sort(list);

		adapDesa = new ArrayAdapter<String>(SetDesa.this,
				android.R.layout.simple_spinner_item, list);

		adapDesa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spDesa.setAdapter(adapDesa);
		spDesa.setWillNotDraw(false);
	}

	private void loadSpinnerProv() {
		Set<String> set = db.getAllProv();

		List<String> list = new ArrayList<String>(set);
		Collections.sort(list);

		adapProf = new ArrayAdapter<String>(SetDesa.this,
				android.R.layout.simple_spinner_item, list);

		adapProf.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spProv.setAdapter(adapProf);
		spProv.setWillNotDraw(false);
	}

	private void loadSpinnerKec(String kab) {
		Set<String> set = db.getAllKec(kab);

		List<String> list = new ArrayList<String>(set);
		Collections.sort(list);

		adapKec = new ArrayAdapter<String>(SetDesa.this,
				android.R.layout.simple_spinner_item, list);

		adapKec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spKec.setAdapter(adapKec);
		spKec.setWillNotDraw(false);
	}

	private void loadSpinnerKab(String prov) {
		Set<String> set = db.getAllKab(prov);

		List<String> list = new ArrayList<String>(set);
		Collections.sort(list);

		adapKab = new ArrayAdapter<String>(SetDesa.this,
				android.R.layout.simple_spinner_item, list);

		adapKab.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spKab.setAdapter(adapKab);
		spKab.setWillNotDraw(false);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.home:
			goHome();
			return true;
		case R.id.datalist:
			goListData();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void goHome() {
		AlertDialog.Builder alert = new AlertDialog.Builder(SetDesa.this);
		alert.setTitle(R.string.errorTitle);
		alert.setMessage("Anda Ingin keluar ?");
		alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// if(!isUpdate && UpFrom == "normal")
				// {
				// id_kk = db.getid();
				// db.deleteRow(id_kk);
				// }

				Intent intent = new Intent(context, com.inobu.mquestioner.HalamanUtama.class);
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
		AlertDialog.Builder alert = new AlertDialog.Builder(SetDesa.this);
		alert.setTitle(R.string.errorTitle);
		alert.setMessage("Anda Ingin keluar ?");
		alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// if(!isUpdate && UpFrom == "normal"){
				// id_kk = db.getid();
				// db.deleteRow(id_kk);
				// }

				Intent intent = new Intent(context, list.mquestioner.ListNarasumber.class);
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

	@Override
	public void onBackPressed() {
		// if(!isUpdate && UpFrom == "normal")
		// {
		// id_kk = db.getid();
		// db.deleteRow(id_kk);
		// }

		Intent intent = new Intent(context, com.inobu.mquestioner.HalamanUtama.class);
		if (isUpdate) {
			intent = new Intent(getApplicationContext(), list.mquestioner.ListNarasumber.class);
		}

		startActivity(intent);
	}

	// AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
	// alert.setTitle(R.string.errorTitle);
	// alert.setMessage("Anda Ingin keluar ?");
	// alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int id) {
	// Intent intent = null;
	// if(!isUpdate && UpFrom == "normal"){
	// id_kk = db.getid();
	// db.deleteRow(id_kk);
	// Log.d("id_KK", id_kk+"");
	// intent = new Intent(context, HalamanUtama.class);
	// } else if(isUpdate){
	// finish();
	// }
	// startActivity(intent);
	// }
	// });
	// alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int id) {
	// dialog.cancel();
	// }
	// });
	// alert.show();

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		Spinner spinner = (Spinner) parent;
		if (spinner.getId() == R.id.txDesa) {
			selectDesa = parent.getItemIdAtPosition(position);
		} else if (spinner.getId() == R.id.txkec) {
			String KecName = parent.getItemAtPosition(position).toString();
			selectKec = parent.getItemIdAtPosition(position);
			Log.d("Create selectKec", selectKec + "");
			Log.d("Response kec", KecName);
			loadSpinnerDesa(KecName);
		} else if (spinner.getId() == R.id.txKab) {
			String KabName = parent.getItemAtPosition(position).toString();
			selectKab = parent.getItemIdAtPosition(position);
			Log.d("Create selectKab", selectKab + "");
			Log.d("Create Response", KabName);
			if (!isUpdate) {
				loadSpinnerKec(KabName);
			}
		} else if (spinner.getId() == R.id.txProv) {
			// do this
			String provName = parent.getItemAtPosition(position).toString();
			selectProv = parent.getItemIdAtPosition(position);
			Log.d("Create Response", provName);
			if (!isUpdate) {
				loadSpinnerKab(provName);
			}
		}
		isUpdate = false;
	}
}
