package set.mquestioner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.inobu.mquestionair.R.id;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class SetPupuk extends Activity {
	RadioGroup rgrAsal, rgrCampur;
	LinearLayout mainLayout, simpanLayout;
	EditText txtKUD, txtPerusahaan, txtBeli, txtPpLain;
	EditText txtJmlNPK, txtJmlUrea, txtJmlBorat, txtJmlHCL, txtJmlLain;
	EditText txtJkCampur;

	RadioButton rbrDinas, rbrKUD, rbrPerusahaan, rbrBeli;
	CheckBox rbrNPK, rbrUrea, rbrBorat, rbrHCL, rbrLain;
	CheckBox rbrJmlNPK, rbrJmlUrea, rbrJmlBorat, rbrJmlHCL, rbrJmlLain;
	RadioButton rbYa, rbTidak;
	Button btSaveHalUTama;
	com.inobu.mquestioner.DbHelper db = new com.inobu.mquestioner.DbHelper(this);

	String ShapeIDL = null;
	private String getKosong = "";
	String cek;
	String asalchk = "", asal = "";
	String jenis = "";
	String jumlahchk = "";
	String jumlah = "";
	String stlahn = null;
	String jnsppk = null;
	String dtlppk = null;

	boolean campur = true;
	String ppkcampur = "";
	private boolean isUpdate;
	String texty;
	final Context context = this;
	TextView shapeIDs;
	com.inobu.mquestioner.JSONParser jsonParser = new com.inobu.mquestioner.JSONParser();
	Cursor model = null;
	int id_kk = 0;
	Cursor modellahan = null;
	CheckBox[] ckRbps = new CheckBox[4];
	CheckBox[] ckJmls = new CheckBox[4];
	private static String url_save_narasumber = "/savekk.php";
	private static String url_save_lahan = "/savelahan.php";
	JSONObject json;
	String fstatus;
	String asalpupuk;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pupuk);
		getActionBar().setTitle("Pemupukan");
		id_kk = db.getid();
		Log.d("ikk", id_kk + "");
		mainLayout = (LinearLayout) this.findViewById(R.id.layoutPupuk);
		simpanLayout = (LinearLayout) this.findViewById(R.id.layoutsimpandata);
		shapeIDs = (EditText) findViewById(R.id.shapesIDspupk);
		txtKUD = (EditText) findViewById(R.id.txpKUD);
		txtPerusahaan = (EditText) findViewById(R.id.txpPerusahaan);
		txtBeli = (EditText) findViewById(R.id.txpBeli);

		txtPpLain = (EditText) findViewById(R.id.txPpLain);

		txtJmlNPK = (EditText) findViewById(R.id.txJmlNPK);
		txtJmlUrea = (EditText) findViewById(R.id.txJmlUrea);
		txtJmlBorat = (EditText) findViewById(R.id.txJmlBorat);
		txtJmlHCL = (EditText) findViewById(R.id.txJmlHCL);
		txtJmlLain = (EditText) findViewById(R.id.txJmlLain);

		rgrAsal = (RadioGroup) findViewById(R.id.rgAsal);
		rgrCampur = (RadioGroup) findViewById(R.id.rgcampur);

		txtJkCampur = (EditText) findViewById(R.id.txJkCampur);

		rbrDinas = (RadioButton) findViewById(R.id.rbpDinasKebun);
		rbrKUD = (RadioButton) findViewById(R.id.rbpKUD);
		rbrPerusahaan = (RadioButton) findViewById(R.id.rbpPerusahaan);
		rbrBeli = (RadioButton) findViewById(R.id.rbpBeli);

		rbrNPK = (CheckBox) findViewById(R.id.rbpNPK);
		rbrUrea = (CheckBox) findViewById(R.id.rbpUrea);
		rbrBorat = (CheckBox) findViewById(R.id.rbpBorat);
		rbrHCL = (CheckBox) findViewById(R.id.rbpHCL);
		rbrLain = (CheckBox) findViewById(R.id.rbpLain);

		rbrJmlNPK = (CheckBox) findViewById(R.id.rbJmNPK);
		rbrJmlUrea = (CheckBox) findViewById(R.id.rbJmUrea);
		rbrJmlBorat = (CheckBox) findViewById(R.id.rbJmBorat);
		rbrJmlHCL = (CheckBox) findViewById(R.id.rbJmHCL);
		rbrJmlLain = (CheckBox) findViewById(R.id.rbJmLain);

		rbYa = (RadioButton) findViewById(R.id.rbPupukYa);
		rbYa.setChecked(true);
		rbTidak = (RadioButton) findViewById(R.id.rbPupukTidak);
		btSaveHalUTama = (Button) findViewById(R.id.btSimpanData);

		txtJmlNPK.setEnabled(false);
		txtJmlUrea.setEnabled(false);
		txtJmlBorat.setEnabled(false);
		txtJmlHCL.setEnabled(false);
		txtJmlLain.setEnabled(false);
		txtPpLain.setEnabled(false);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			isUpdate = getIntent().getExtras().getBoolean("updateList");
			Log.d("UpFrom Response1", isUpdate + "");
			if (isUpdate) {
				ShapeIDL = getIntent().getExtras().getString("shapeid");
				btSaveHalUTama.setVisibility(View.VISIBLE);
				simpanLayout.setVisibility(View.VISIBLE);
				id_kk = Integer.parseInt(getIntent().getExtras().getString(
						"rowID"));
				fstatus = getIntent().getExtras().getString("status");

				String asal_pupuk = getIntent().getExtras().getString(
						"asalppkchk");
				if (asal_pupuk.equals("Dinas Perkebunan")) {
					rgrAsal.check(R.id.rbpDinasKebun);
				} else if (asal_pupuk.equals("KUD")) {
					rgrAsal.check(R.id.rbpKUD);
					txtKUD.setText(getIntent().getExtras().getString("asalppk"));
				} else if (asal_pupuk.equals("Perusahaan")) {
					rgrAsal.check(R.id.rbpPerusahaan);
					txtPerusahaan.setText(getIntent().getExtras().getString(
							"asalppk"));
				} else if (asal_pupuk.equals("Beli di")) {
					rgrAsal.check(R.id.rbpBeli);
					txtBeli.setText(getIntent().getExtras()
							.getString("asalppk"));
				}

				String jenis_pupuk = getIntent().getExtras().getString(
						"jenisppk");
				String jumlah_pupukchk = getIntent().getExtras().getString(
						"jumlahppkchk");
				String jumlah_pupuk = getIntent().getExtras().getString(
						"jumlahppk");
				Log.d("isUpdate Response", jenis_pupuk + "");
				Log.d("isUpdate Response", jumlah_pupukchk + "");
				Log.d("isUpdate Response", jumlah_pupuk + "");
				String arr[] = jenis_pupuk.split(",");
				String arrjmlchk[] = jumlah_pupukchk.split(",");
				String arrJml[] = jumlah_pupuk.split(",");

				int ckRbp[] = { id.rbpNPK, id.rbpUrea, id.rbpBorat, id.rbpHCL, id.rbpLain };
				int ckRbjml[] = { id.rbJmNPK, id.rbJmUrea, id.rbJmBorat, id.rbJmHCL, id.rbJmLain};
				Log.d("isUpdate Response", ckRbp.length + "");

				if (arr[0].trim().equals("NPK")) {
					Log.d("sett Response", arr[0].toString());
					ckRbps[0] = (CheckBox) findViewById(ckRbp[0]);
					ckRbps[0].setChecked(true);

					ckJmls[0] = (CheckBox) findViewById(ckRbjml[0]);
					ckJmls[0].setChecked(true);
					txtJmlNPK.setEnabled(true);
					txtJmlNPK.setText(arrJml[0]);
				}

				Log.d("arr Response", arr.length + "");
				if (arr[1].trim().equals("Urea")) {
					Log.d("sett Response", arr[1].toString());
					ckRbps[1] = (CheckBox) findViewById(ckRbp[1]);
					ckRbps[1].setChecked(true);

					ckJmls[1] = (CheckBox) findViewById(ckRbjml[1]);
					ckJmls[1].setChecked(true);
					txtJmlUrea.setEnabled(true);
					txtJmlUrea.setText(arrJml[1]);
				}

				if (arr[2].trim().equals("Borat")) {
					Log.d("sett Response", arr[2].toString());
					ckRbps[2] = (CheckBox) findViewById(ckRbp[2]);
					ckRbps[2].setChecked(true);

					ckJmls[2] = (CheckBox) findViewById(ckRbjml[2]);
					ckJmls[2].setChecked(true);
					txtJmlBorat.setEnabled(true);
					txtJmlBorat.setText(arrJml[2]);
				}
				
				if (arr[3].trim().equals("HCL")) {
					Log.d("sett Response", arr[3].toString());
					ckRbps[3] = (CheckBox) findViewById(ckRbp[3]);
					ckRbps[3].setChecked(true);

					ckJmls[3] = (CheckBox) findViewById(ckRbjml[3]);
					ckJmls[3].setChecked(true);
					txtJmlHCL.setEnabled(true);
					txtJmlHCL.setText(arrJml[3]);
				}

				if (!arr[3].trim().equals("NPK")
						&& !arr[3].trim().equals("Urea")
						&& !arr[3].trim().equals("Borat")
						&& !arr[3].trim().equals("HCL")
						&& !arr[3].trim().equals("null")) {
					Log.d("sett Response", arr[3].toString());
					ckRbps[3] = (CheckBox) findViewById(ckRbp[3]);
					ckRbps[3].setChecked(true);

					if (arrjmlchk[3] != "null") {
						txtPpLain.setEnabled(true);
						txtPpLain.setText(arrjmlchk[3]);
					} else {
						txtPpLain.setText("");
					}

					ckJmls[3] = (CheckBox) findViewById(ckRbjml[3]);
					ckJmls[3].setChecked(true);
					if (arrJml[3] != "0") {
						txtJmlLain.setEnabled(true);
						txtJmlLain.setText(arrJml[3]);
					} else {
						txtJmlLain.setText("");
					}
				}

				String campur_pupuk = getIntent().getExtras().getString(
						"campur");
				if (campur_pupuk.equals("1")) {
					rgrCampur.check(R.id.rbPupukYa);
					txtJkCampur.setText(getIntent().getExtras().getString(
							"dicampur"));
					Log.d("need", getIntent().getExtras().getString("dicampur"));
				} else if (campur_pupuk.equals("0")) {
					rgrCampur.check(R.id.rbPupukTidak);
				}
			} else {
				ShapeIDL = getIntent().getExtras().getString("shapeid");
				getKosong = getIntent().getExtras().getString("kosong");
				Log.d("getKosong3 Response", getKosong);
			}
			shapeIDs.setText(ShapeIDL);
		}

		rgrAsal.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rbpDinasKebun:
					asalchk = "Dinas Perkebunan";
					asal = "";
					txtKUD.setEnabled(false);
					txtPerusahaan.setEnabled(false);
					txtBeli.setEnabled(false);
					break;
				case R.id.rbpKUD:
					asalchk = "KUD";
					txtKUD.setEnabled(true);
					txtPerusahaan.setEnabled(false);
					txtBeli.setEnabled(false);
					if (txtKUD.getText().toString().isEmpty()) {
						getKosong = "kosong";
						asal = "";
					} else {
						asal = txtKUD.getText().toString();
					}
					break;
				case R.id.rbpPerusahaan:
					asalchk = "Perusahaan";
					txtKUD.setEnabled(false);
					txtPerusahaan.setEnabled(true);
					txtBeli.setEnabled(false);
					if (txtPerusahaan.getText().toString().isEmpty()) {
						getKosong = "kosong";
						asal = "";
					} else {
						asal = txtPerusahaan.getText().toString();
					}
					break;
				case R.id.rbpBeli:
					asalchk = "Beli";
					txtKUD.setEnabled(false);
					txtPerusahaan.setEnabled(false);
					txtBeli.setEnabled(true);
					if (!txtBeli.getText().toString().isEmpty()) {
						getKosong = "kosong";
						asal = "";
					} else {
						asal = txtBeli.getText().toString();
					}
					break;
				}
			}
		});

		rbrNPK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (rbrNPK.isChecked()) {
					txtJmlNPK.setEnabled(true);
					rbrJmlNPK.setChecked(true);
					if (txtJmlNPK.getText().toString().isEmpty())
						getKosong = "kosong";
				} else if (!rbrNPK.isChecked()) {
					txtJmlNPK.setEnabled(false);
					txtJmlNPK.setText("");
					rbrJmlNPK.setChecked(false);
				}
			}
		});

		rbrJmlNPK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (rbrJmlNPK.isChecked()) {
					txtJmlNPK.setEnabled(true);
					rbrNPK.setChecked(true);
					if (txtJmlNPK.getText().toString().isEmpty())
						getKosong = "kosong";
				} else if (!rbrJmlNPK.isChecked()) {
					txtJmlNPK.setEnabled(false);
					txtJmlNPK.setText("");
					rbrNPK.setChecked(false);
				}
			}
		});

		rbrUrea.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (rbrUrea.isChecked()) {
					txtJmlUrea.setEnabled(true);
					rbrJmlUrea.setChecked(true);
					if (txtJmlUrea.getText().toString().isEmpty())
						getKosong = "kosong";
				} else if (!rbrUrea.isChecked()) {
					txtJmlUrea.setEnabled(false);
					txtJmlUrea.setText("");
					rbrJmlUrea.setChecked(false);
				}
			}
		});

		rbrJmlUrea.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (rbrJmlUrea.isChecked()) {
					txtJmlUrea.setEnabled(true);
					rbrUrea.setChecked(true);
					if (txtJmlUrea.getText().toString().isEmpty())
						getKosong = "kosong";
				} else if (!rbrJmlUrea.isChecked()) {
					txtJmlUrea.setEnabled(false);
					txtJmlUrea.setText("");
					rbrUrea.setChecked(false);
				}
			}
		});

		rbrBorat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (rbrBorat.isChecked()) {
					txtJmlBorat.setEnabled(true);
					rbrJmlBorat.setChecked(true);
					if (txtJmlBorat.getText().toString().isEmpty())
						getKosong = "kosong";
				} else if (!rbrBorat.isChecked()) {
					txtJmlBorat.setEnabled(false);
					txtJmlBorat.setText("");
					rbrJmlBorat.setChecked(false);
				}
			}
		});

		rbrJmlBorat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (rbrJmlBorat.isChecked()) {
					txtJmlBorat.setEnabled(true);
					rbrBorat.setChecked(true);
					if (txtJmlBorat.getText().toString().isEmpty())
						getKosong = "kosong";
				} else if (!rbrJmlBorat.isChecked()) {
					txtJmlBorat.setEnabled(false);
					txtJmlBorat.setText("");
					rbrBorat.setChecked(false);
				}
			}
		});

		rbrHCL.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (rbrHCL.isChecked()) {
					txtJmlHCL.setEnabled(true);
					rbrJmlHCL.setChecked(true);
					if (txtJmlHCL.getText().toString().isEmpty())
						getKosong = "kosong";
				} else if (!rbrHCL.isChecked()) {
					txtJmlHCL.setEnabled(false);
					txtJmlHCL.setText("");
					rbrJmlHCL.setChecked(false);
				}
			}
		});

		rbrJmlHCL.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (rbrJmlHCL.isChecked()) {
					txtJmlHCL.setEnabled(true);
					rbrHCL.setChecked(true);
					if (txtJmlHCL.getText().toString().isEmpty())
						getKosong = "kosong";
				} else if (!rbrJmlHCL.isChecked()) {
					txtJmlHCL.setEnabled(false);
					txtJmlHCL.setText("");
					rbrHCL.setChecked(false);
				}
			}
		});
		
		
		rbrLain.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (rbrLain.isChecked()) {
					txtPpLain.setEnabled(true);
					rbrJmlLain.setChecked(true);
					txtJmlLain.setEnabled(true);
					if (txtPpLain.getText().toString().isEmpty())
						getKosong = "kosong";
				} else if (!rbrLain.isChecked()) {
					txtPpLain.setEnabled(false);
					txtPpLain.setText("");
					rbrJmlLain.setChecked(false);
				}
			}
		});

		rbrJmlLain.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (rbrJmlLain.isChecked()) {
					txtPpLain.setEnabled(true);
					rbrLain.setChecked(true);
					txtJmlLain.setEnabled(true);
					if (txtPpLain.getText().toString().isEmpty())
						getKosong = "kosong";
				} else if (!rbrJmlLain.isChecked()) {
					txtPpLain.setEnabled(false);
					txtPpLain.setText("");
					rbrLain.setChecked(false);
				}
			}
		});

		rgrCampur.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rbPupukYa:
					campur = true;
					mainLayout.setVisibility(View.VISIBLE);
					if (txtJkCampur.getText().toString().isEmpty()) {
						getKosong = "kosong";
						ppkcampur = "";
					} else {
						ppkcampur = txtJkCampur.getText().toString();
					}
					break;
				case R.id.rbPupukTidak:
					campur = false;
					mainLayout.setVisibility(View.GONE);
					ppkcampur = "";
					txtJkCampur.setText("");
					break;
				}
			}
		});

		btSaveHalUTama.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				save();
				db.updatePupuk(ShapeIDL, asalchk, asal, jenis, jumlahchk,
						jumlah, campur, txtJkCampur.getText().toString());
				Log.d("texty hal utama", ShapeIDL + " " + asal + " " + jenis
						+ " " + jumlah + " " + campur + " "
						+ txtJkCampur.getText().toString());

				Intent intent = new Intent(SetPupuk.this, list.mquestioner.ListLahan.class);
				intent.putExtra("idKK", Integer.toString(id_kk));
				intent.putExtra("status", fstatus);
				startActivity(intent);
			}
		});
	}

	public void save() {
		Log.d("simpan data", "simpan data");
		switch (rgrAsal.getCheckedRadioButtonId()) {
		case R.id.rbpDinasKebun:
			asalchk = "Dinas Perkebunan";
			break;
		case R.id.rbpKUD:
			asalchk = "KUD";
			if (!txtKUD.getText().toString().isEmpty()) {
				asal = txtKUD.getText().toString();
			}
			break;
		case R.id.rbpPerusahaan:
			asalchk = "Perusahaan";
			if (!txtPerusahaan.getText().toString().isEmpty()) {
				asal = txtPerusahaan.getText().toString();
			}
			break;
		case R.id.rbpBeli:
			asalchk = "Beli di";
			if (!txtBeli.getText().toString().isEmpty()) {
				asal = txtBeli.getText().toString();
			}
			break;
		}

		if (rbrNPK.isChecked() && rbrJmlNPK.isChecked()) {
			jenis = "NPK";
			jumlahchk = "NPK";

			if (!txtJmlNPK.getText().toString().isEmpty()) {
				jumlah = txtJmlNPK.getText().toString();
			} else {
				jumlah = "0";
			}
		} else {
			jenis = "null";
			jumlahchk = "null";
			jumlah = "0";
		}

		if (rbrUrea.isChecked() && rbrJmlUrea.isChecked()) {
			jenis = jenis + ",Urea";
			jumlahchk = jumlahchk + ",Urea";

			if (!txtJmlUrea.getText().toString().isEmpty()) {
				jumlah = jumlah + "," + txtJmlUrea.getText().toString();
			} else {
				jumlah = jumlah + "," + "0";
			}
		} else {
			jenis = jenis + ",null";
			jumlahchk = jumlahchk + ",null";
			jumlah = jumlah + "," + "0";
		}

		if (rbrBorat.isChecked() && rbrJmlBorat.isChecked()) {
			jenis = jenis + ",Borat";
			jumlahchk = jumlahchk + ",Borat";

			if (!txtJmlBorat.getText().toString().isEmpty()) {
				jumlah = jumlah + "," + txtJmlBorat.getText().toString();
			} else {
				jumlah = jumlah + "," + "0";
			}
		} else {
			jenis = jenis + ",null";
			jumlahchk = jumlahchk + ",null";
			jumlah = jumlah + "," + "0";
		}

		if (rbrHCL.isChecked() && rbrJmlHCL.isChecked()) {
			jenis = jenis + ",HCL";
			jumlahchk = jumlahchk + ",HCL";

			if (!txtJmlHCL.getText().toString().isEmpty()) {
				jumlah = jumlah + "," + txtJmlHCL.getText().toString();
			} else {
				jumlah = jumlah + "," + "0";
			}
		} else {
			jenis = jenis + ",null";
			jumlahchk = jumlahchk + ",null";
			jumlah = jumlah + "," + "0";
		}
		
		if (rbrLain.isChecked() && rbrJmlLain.isChecked()) {
			if (!txtPpLain.getText().toString().isEmpty()) {
				jenis = jenis + "," + txtPpLain.getText().toString();
			} else {
				jenis = jenis + ", pupuk lain";
			}

			jumlahchk = jumlahchk + ",lain";

			if (!txtJmlLain.getText().toString().isEmpty()) {
				jumlah = jumlah + "," + txtJmlLain.getText().toString();
			} else {
				jumlah = jumlah + "," + "0";
			}
		} else {
			jenis = jenis + ",null";
			jumlahchk = jumlahchk + ",null";
			jumlah = jumlah + "," + "0";
		}
		Log.d("jumlah ", jumlah);

		switch (rgrCampur.getCheckedRadioButtonId()) {
		case R.id.rbPupukYa:
			campur = true;
			break;
		case R.id.rbPupukTidak:
			campur = false;
			break;
		}
	}

	@Override
	public void onBackPressed() {
		if (isUpdate) {
			finish();
		} else {
			Intent i = new Intent(getApplicationContext(), SetBibit.class);
			i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(i);
		}
	}

	public void goExit() {
		AlertDialog.Builder alert = new AlertDialog.Builder(SetPupuk.this);
		alert.setTitle(R.string.errorTitle);
		alert.setMessage("Anda Ingin keluar ?");
		alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// db.updatePupuk(ShapeIDL, asalchk, asal, jenis, jumlahchk,
				// jumlah, campur, txtJkCampur.getText().toString());
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
		AlertDialog.Builder alert = new AlertDialog.Builder(SetPupuk.this);
		alert.setTitle(R.string.errorTitle);
		alert.setMessage("Anda Ingin keluar ?");
		alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// db.updatePupuk(ShapeIDL, asalchk, asal, jenis, jumlahchk,
				// jumlah, campur, txtJkCampur.getText().toString());
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

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.savepupuk, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.TambahLahan:
			Log.d("simpan data", "simpan data");
			switch (rgrAsal.getCheckedRadioButtonId()) {
			case R.id.rbpDinasKebun:
				asalchk = "Dinas Perkebunan";
				break;
			case R.id.rbpKUD:
				asalchk = "KUD";
				if (!txtKUD.getText().toString().isEmpty()) {
					asal = txtKUD.getText().toString();
				}
				break;
			case R.id.rbpPerusahaan:
				asalchk = "Perusahaan";
				if (!txtPerusahaan.getText().toString().isEmpty()) {
					asal = txtPerusahaan.getText().toString();
				}
				break;
			case R.id.rbpBeli:
				asalchk = "Beli di";
				if (!txtBeli.getText().toString().isEmpty()) {
					asal = txtBeli.getText().toString();
				}
				break;
			}

			if (rbrNPK.isChecked() && rbrJmlNPK.isChecked()) {
				jenis = "NPK";
				jumlahchk = "NPK";

				if (!txtJmlNPK.getText().toString().isEmpty()) {
					jumlah = txtJmlNPK.getText().toString();
				} else {
					jumlah = "0";
				}
			} else {
				jenis = "null";
				jumlahchk = "null";
				jumlah = "0";
			}

			if (rbrUrea.isChecked() && rbrUrea.isChecked()) {
				jenis = jenis + ",Urea";
				jumlahchk = jumlahchk + ",Urea";

				if (!txtJmlUrea.getText().toString().isEmpty()) {
					jumlah = jumlah + "," + txtJmlUrea.getText().toString();
				} else {
					jumlah = jumlah + "," + "0";
				}
			} else {
				jenis = jenis + ",null";
				jumlahchk = jumlahchk + ",null";
				jumlah = jumlah + "," + "0";
			}

			if (rbrBorat.isChecked() && rbrBorat.isChecked()) {
				jenis = jenis + ",Borat";
				jumlahchk = jumlahchk + ",Borat";

				if (!txtJmlBorat.getText().toString().isEmpty()) {
					jumlah = jumlah + "," + txtJmlBorat.getText().toString();
				} else {
					jumlah = jumlah + "," + "0";
				}
			} else {
				jenis = jenis + ",null";
				jumlahchk = jumlahchk + ",null";
				jumlah = jumlah + "," + "0";
			}

			if (rbrHCL.isChecked() && rbrJmlHCL.isChecked()) {
				jenis = jenis + ",HCL";
				jumlahchk = jumlahchk + ",HCL";

				if (!txtJmlHCL.getText().toString().isEmpty()) {
					jumlah = jumlah + "," + txtJmlHCL.getText().toString();
				} else {
					jumlah = jumlah + "," + "0";
				}
			} else {
				jenis = jenis + ",null";
				jumlahchk = jumlahchk + ",null";
				jumlah = jumlah + "," + "0";
			}
			
			if (rbrLain.isChecked() && rbrJmlLain.isChecked()) {
				if (!txtPpLain.getText().toString().isEmpty()) {
					jenis = jenis + "," + txtPpLain.getText().toString();
				} else {
					jenis = jenis + ", pupuk lain";
				}

				jumlahchk = jumlahchk + ",lain";

				if (!txtJmlLain.getText().toString().isEmpty()) {
					jumlah = jumlah + "," + txtJmlLain.getText().toString();
				} else {
					jumlah = jumlah + "," + "0";
				}
			} else {
				jenis = jenis + ",null";
				jumlahchk = jumlahchk + ",null";
				jumlah = jumlah + "," + "0";
			}
			Log.d("jumlah ", jumlah);

			switch (rgrCampur.getCheckedRadioButtonId()) {
			case R.id.rbPupukYa:
				campur = true;
				break;
			case R.id.rbPupukTidak:
				campur = false;
				break;
			}

			db.updatePupuk(ShapeIDL, asalchk, asal, jenis, jumlahchk, jumlah,
					campur, txtJkCampur.getText().toString());
			Log.d("texty hal utama", ShapeIDL + " " + asal + " " + jenis + " "
					+ jumlah + " " + campur + " "
					+ txtJkCampur.getText().toString());

			Intent i = new Intent(SetPupuk.this, SetLahan.class);
			i.putExtra("tambah", true);
			i.putExtra("rowID", id_kk + "");
			startActivity(i);
			return true;

		case R.id.SimpanData:
			Log.d("simpan data", "simpan data");
			switch (rgrAsal.getCheckedRadioButtonId()) {
			case R.id.rbpDinasKebun:
				asalchk = "Dinas Perkebunan";
				break;
			case R.id.rbpKUD:
				asalchk = "KUD";
				if (!txtKUD.getText().toString().isEmpty()) {
					asal = txtKUD.getText().toString();
				}
				break;
			case R.id.rbpPerusahaan:
				asalchk = "Perusahaan";
				if (!txtPerusahaan.getText().toString().isEmpty()) {
					asal = txtPerusahaan.getText().toString();
				}
				break;
			case R.id.rbpBeli:
				asalchk = "Beli di";
				if (!txtBeli.getText().toString().isEmpty()) {
					asal = txtBeli.getText().toString();
				}
				break;
			}

			if (rbrNPK.isChecked() && rbrJmlNPK.isChecked()) {
				jenis = "NPK";
				jumlahchk = "NPK";

				if (!txtJmlNPK.getText().toString().isEmpty()) {
					jumlah = txtJmlNPK.getText().toString();
				} else {
					jumlah = "0";
				}
			} else {
				jenis = "null";
				jumlahchk = "null";
				jumlah = "0";
			}

			if (rbrUrea.isChecked() && rbrJmlUrea.isChecked()) {
				jenis = jenis + ",Urea";
				jumlahchk = jumlahchk + ",Urea";

				if (!txtJmlUrea.getText().toString().isEmpty()) {
					jumlah = jumlah + "," + txtJmlUrea.getText().toString();
				} else {
					jumlah = jumlah + "," + "0";
				}
			} else {
				jenis = jenis + ",null";
				jumlahchk = jumlahchk + ",null";
				jumlah = jumlah + "," + "0";
			}

			if (rbrBorat.isChecked() && rbrJmlBorat.isChecked()) {
				jenis = jenis + ",Borat";
				jumlahchk = jumlahchk + ",Borat";

				if (!txtJmlBorat.getText().toString().isEmpty()) {
					jumlah = jumlah + "," + txtJmlBorat.getText().toString();
				} else {
					jumlah = jumlah + "," + "0";
				}
			} else {
				jenis = jenis + ",null";
				jumlahchk = jumlahchk + ",null";
				jumlah = jumlah + "," + "0";
			}

			if (rbrHCL.isChecked() && rbrJmlHCL.isChecked()) {
				jenis = jenis + ",HCL";
				jumlahchk = jumlahchk + ",HCL";

				if (!txtJmlHCL.getText().toString().isEmpty()) {
					jumlah = jumlah + "," + txtJmlHCL.getText().toString();
				} else {
					jumlah = jumlah + "," + "0";
				}
			} else {
				jenis = jenis + ",null";
				jumlahchk = jumlahchk + ",null";
				jumlah = jumlah + "," + "0";
			}
			
			if (rbrLain.isChecked() && rbrJmlLain.isChecked()) {
				if (!txtPpLain.getText().toString().isEmpty()) {
					jenis = jenis + "," + txtPpLain.getText().toString();
				} else {
					jenis = jenis + ", pupuk lain";
				}

				jumlahchk = jumlahchk + ",lain";

				if (!txtJmlLain.getText().toString().isEmpty()) {
					jumlah = jumlah + "," + txtJmlLain.getText().toString();
				} else {
					jumlah = jumlah + "," + "0";
				}
			} else {
				jenis = jenis + ",null";
				jumlahchk = jumlahchk + ",null";
				jumlah = jumlah + "," + "0";
			}
			Log.d("jumlah ", jumlah);

			switch (rgrCampur.getCheckedRadioButtonId()) {
			case R.id.rbPupukYa:
				campur = true;
				break;
			case R.id.rbPupukTidak:
				campur = false;
				break;
			}

			db.updatePupuk(ShapeIDL, asalchk, asal, jenis, jumlahchk, jumlah,
					campur, txtJkCampur.getText().toString());
			Log.d("texty hal utama", ShapeIDL + " " + asal + " " + jenis + " "
					+ jumlah + " " + campur + " "
					+ txtJkCampur.getText().toString());

			i = new Intent(SetPupuk.this, com.inobu.mquestioner.HalamanUtama.class);
			startActivity(i);
			return true;
		case R.id.KirimData:
			Log.d("simpan data", "simpan data");
			switch (rgrAsal.getCheckedRadioButtonId()) {
			case R.id.rbpDinasKebun:
				asalchk = "Dinas Perkebunan";
				break;
			case R.id.rbpKUD:
				asalchk = "KUD";
				if (!txtKUD.getText().toString().isEmpty()) {
					asal = txtKUD.getText().toString();
				}
				break;
			case R.id.rbpPerusahaan:
				asalchk = "Perusahaan";
				if (!txtPerusahaan.getText().toString().isEmpty()) {
					asal = txtPerusahaan.getText().toString();
				}
				break;
			case R.id.rbpBeli:
				asalchk = "Beli di";
				if (!txtBeli.getText().toString().isEmpty()) {
					asal = txtBeli.getText().toString();
				}
				break;
			}

			if (rbrNPK.isChecked() && rbrJmlNPK.isChecked()) {
				jenis = "NPK";
				jumlahchk = "NPK";

				if (!txtJmlNPK.getText().toString().isEmpty()) {
					jumlah = txtJmlNPK.getText().toString();
				} else {
					jumlah = "0";
				}
			} else {
				jenis = "null";
				jumlahchk = "null";
				jumlah = "0";
			}

			if (rbrUrea.isChecked() && rbrJmlUrea.isChecked()) {
				jenis = jenis + ",Urea";
				jumlahchk = jumlahchk + ",Urea";

				if (!txtJmlUrea.getText().toString().isEmpty()) {
					jumlah = jumlah + "," + txtJmlUrea.getText().toString();
				} else {
					jumlah = jumlah + "," + "0";
				}
			} else {
				jenis = jenis + ",null";
				jumlahchk = jumlahchk + ",null";
				jumlah = jumlah + "," + "0";
			}

			if (rbrBorat.isChecked() && rbrJmlBorat.isChecked()) {
				jenis = jenis + ",Borat";
				jumlahchk = jumlahchk + ",Borat";

				if (!txtJmlBorat.getText().toString().isEmpty()) {
					jumlah = jumlah + "," + txtJmlBorat.getText().toString();
				} else {
					jumlah = jumlah + "," + "0";
				}
			} else {
				jenis = jenis + ",null";
				jumlahchk = jumlahchk + ",null";
				jumlah = jumlah + "," + "0";
			}

			if (rbrHCL.isChecked() && rbrJmlHCL.isChecked()) {
				jenis = jenis + ",HCL";
				jumlahchk = jumlahchk + ",HCL";

				if (!txtJmlHCL.getText().toString().isEmpty()) {
					jumlah = jumlah + "," + txtJmlHCL.getText().toString();
				} else {
					jumlah = jumlah + "," + "0";
				}
			} else {
				jenis = jenis + ",null";
				jumlahchk = jumlahchk + ",null";
				jumlah = jumlah + "," + "0";
			}
			
			if (rbrLain.isChecked() && rbrJmlLain.isChecked()) {
				if (!txtPpLain.getText().toString().isEmpty()) {
					jenis = jenis + "," + txtPpLain.getText().toString();
				} else {
					jenis = jenis + ", pupuk lain";
				}

				jumlahchk = jumlahchk + ",lain";

				if (!txtJmlLain.getText().toString().isEmpty()) {
					jumlah = jumlah + "," + txtJmlLain.getText().toString();
				} else {
					jumlah = jumlah + "," + "0";
				}
			} else {
				jenis = jenis + ",null";
				jumlahchk = jumlahchk + ",null";
				jumlah = jumlah + "," + "0";
			}
			Log.d("jumlah ", jumlah);

			switch (rgrCampur.getCheckedRadioButtonId()) {
			case R.id.rbPupukYa:
				campur = true;
				break;
			case R.id.rbPupukTidak:
				campur = false;
				break;
			}

			db.updatePupuk(ShapeIDL, asalchk, asal, jenis, jumlahchk, jumlah,
					campur, txtJkCampur.getText().toString());
			Log.d("texty hal utama", ShapeIDL + " " + asal + " " + jenis + " "
					+ jumlah + " " + campur + " "
					+ txtJkCampur.getText().toString());

			new sentlahan().execute();

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	class sentlahan extends AsyncTask<Object, Void, String> {
		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SetPupuk.this);
			pDialog.setMessage("Kirim data...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			pDialog.dismiss();
		}

		@Override
		protected String doInBackground(Object... params) {
			Calendar cal = Calendar.getInstance(TimeZone.getDefault());

			String kk = "";
			if (id_kk < 10) {
				kk = "0" + id_kk + "";
				Log.d("log s ", kk + "");
			} else if (id_kk >= 10) {
				kk = id_kk + "";
			}

			model = db.getAllKK(id_kk);
			model.moveToPosition(0);

			//String idkkn = kk + "" + db.getEnumIDnara(model);
			
			String enumid = db.getEnumIDnara(model);
			Log.d("log kk", kk + "" + enumid);

			String narasumber = db.getNama(model);
			String desa = db.getDesa(model);
			String kecamatan = db.getKec(model);
			String kabupaten = db.getKab(model);
			String provinsi = db.getProv(model);
			String noKTP = db.getKTP(model);
			String noHP = db.getHP(model);
			String namakk = db.getnamaKK(model);
			String jumlahkk = db.getJmlKK(model);
			String tempatlhr = db.getTempatLhr(model);
			String tanggallhr = db.gettanggalLhr(model);
			String pendidikan = db.getpendidikan(model);
			String suku = db.getsuku(model);
			String pekerjaanutama = db.getpekerjaanutama(model);
			String pekerjaanlain = db.getpekerjaanlain(model);
			String anggotaorg = db.getagt_org(model);
			String tglinput = db.gettglinput(model);
			
			String kirimid = db.getEnumIDnara(model) + "" + db.getDesaID(desa)+""+ String.valueOf(db.getRandom(99999,00000));
			
			Log.d("narasumber sent", kirimid + "" + enumid + "" + narasumber + ""
					+ desa + "" + kecamatan + "" + kabupaten + "" + provinsi
					+ "" + noKTP + "" + noHP + "" + namakk + "" + jumlahkk + ""
					+ tempatlhr + "" + tanggallhr + "" + pendidikan + "" + suku
					+ "" + pekerjaanutama + "" + pekerjaanlain + ""
					+ anggotaorg + "" + tglinput + "");

			Log.d("log idkkn ", kirimid);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("id_kk", kirimid));
			nameValuePairs.add(new BasicNameValuePair("enum_id", enumid));
			nameValuePairs
					.add(new BasicNameValuePair("narasumber", narasumber));
			nameValuePairs.add(new BasicNameValuePair("desa", desa));
			nameValuePairs.add(new BasicNameValuePair("kecamatan", kecamatan));
			nameValuePairs.add(new BasicNameValuePair("kabupaten", kabupaten));
			nameValuePairs.add(new BasicNameValuePair("propinsi", provinsi));
			nameValuePairs.add(new BasicNameValuePair("no_KTP", noKTP));
			nameValuePairs.add(new BasicNameValuePair("no_HP", noHP));
			nameValuePairs.add(new BasicNameValuePair("nama_kk", namakk));
			nameValuePairs.add(new BasicNameValuePair("jumlah_kk", jumlahkk));
			nameValuePairs.add(new BasicNameValuePair("tempat_lhr", tempatlhr));
			nameValuePairs
					.add(new BasicNameValuePair("tanggal_lhr", tanggallhr));
			nameValuePairs
					.add(new BasicNameValuePair("pendidikan", pendidikan));
			nameValuePairs.add(new BasicNameValuePair("suku", suku));
			nameValuePairs.add(new BasicNameValuePair("pekerjaan_utama",
					pekerjaanutama));
			nameValuePairs.add(new BasicNameValuePair("pekerjaan_lain",
					pekerjaanlain));
			nameValuePairs.add(new BasicNameValuePair("anggota_organisasi",
					anggotaorg));
			nameValuePairs.add(new BasicNameValuePair("tgl_input", tglinput));
			nameValuePairs.add(new BasicNameValuePair("status", "1"));

			String getTimeOn = cal.getTime().toLocaleString();
			nameValuePairs.add(new BasicNameValuePair("sent_on", getTimeOn));

			try {
				json = jsonParser.makeHttpRequest(db.getURL()
						+ url_save_narasumber, "POST", nameValuePairs);
				Log.d("Request Ok", json.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}

			int cLahan = db.getidLahannya(id_kk);
			Log.d("lahan ", cLahan + "");
			// if(cLahan > 0)
			// {
			// Log.d("lahan ",cLahan+"");
			// for(int i=1; i<=cLahan; i++)
			// {
			Log.d("lahan 1", cLahan + "");
			// Log.d("lahan 11",i+"");
			modellahan = db.getAlltoLahan(id_kk + "", cLahan);
			modellahan.moveToPosition(0);
			String enumidLahan = db.getEnumID(modellahan);
			Log.d("enumid ", enumidLahan + "");
			String shapeid = db.getshape_ID(modellahan);
			Log.d("shapeid ", shapeid + "");
			String sttLahan = db.getstatus_lahan(modellahan);
			String statuslahan = null;

			String arrStatus[] = sttLahan.split(",");
			if (arrStatus[0].trim().equals("SKT")) {
				Log.d("sett Response", arrStatus[0].toString());
				statuslahan = "SKT";
			}

			if (arrStatus[1].trim().equals("SHM")) {
				Log.d("sett Response", arrStatus[1].toString());
				if (statuslahan != null) {
					statuslahan = statuslahan + ",SHM";
				} else {
					statuslahan = "SHM";
				}
			}

			if (arrStatus[2].trim().equals("SKTA")) {
				Log.d("sett Response", arrStatus[2].toString());
				if (statuslahan != null) {
					statuslahan = statuslahan + ",SKTA";
				} else {
					statuslahan = "SKTA";
				}
			}

			if (!arrStatus[3].equals("SKT") && !arrStatus[3].equals("SHM")
					&& !arrStatus[3].equals("SKTA")
					&& !arrStatus[3].equals("null")) {
				Log.d("sett Response 3", arrStatus[3].toString());
				if (statuslahan != null) {
					statuslahan = statuslahan + "," + arrStatus[3].toString();
				}
			}

			String lamalahan = db.getlama_lahan(modellahan);
			String luasprod = db.getluas_prod(modellahan);
			String luasnonprod = db.getluas_nonprod(modellahan);
			String tahuntanamprod = db.gettahun_tanam(modellahan);
			String tahuntanamnonprod = db.gettahun_tanamnon(modellahan);
			String pernahpanen = db.getpernah_panen(modellahan);
			String sikluspanen = db.getsiklus_panen(modellahan);
			String beratbuahpanen = db.getberat_buah(modellahan);
			String properpanen = db.getprod_perpanen(modellahan);
			String asalbibitchk = db.getasal_bibitchk(modellahan);
			String asalbibit = db.getasal_bibit(modellahan);
			String jenisbibit = db.getjenis_bibit(modellahan);
			String hibrida = db.gethibrida(modellahan);
			String asalpupukchk = db.getasal_pupukchk(modellahan);
			asalpupuk = db.getasal_pupuk(modellahan);

			String jenispupuk = db.getjenis_pupuk(modellahan);
			String arr[] = jenispupuk.split(",");

			String gunapupukchk = db.getpenggunaan_pupukchk(modellahan);
			String gunapupuk = db.getpenggunaan_pupuk(modellahan);

			String arrjmlchk[] = gunapupukchk.split(",");
			String arrJml[] = gunapupuk.split(",");

			if (arr[0].trim().equals("NPK")) {
				Log.d("sett Response", arr[0].toString());
				dtlppk = "NPK " + arrJml[0] + " kg";
				jnsppk = "NPK";
			}

			Log.d("arr Response", arr.length + "");
			if (arr[1].trim().equals("Urea")) {
				if (dtlppk != null) {
					dtlppk = dtlppk + ",NPK " + arrJml[1] + " kg";
				} else {
					Log.d("sett Response", arr[1].toString());
					dtlppk = "Urea " + arrJml[1] + " kg";
				}

				if (jnsppk != null) {
					jnsppk = jnsppk + ",Urea";
				} else {
					jnsppk = "Urea";
				}
			}

			if (arr[2].trim().equals("Borat")) {
				if (dtlppk != null) {
					dtlppk = dtlppk + ",Borat " + arrJml[2] + " kg";
				} else {
					Log.d("sett Response", arr[2].toString());
					dtlppk = "Urea " + arrJml[2] + " kg";
				}

				if (jnsppk != null) {
					jnsppk = jnsppk + ",Borat";
				} else {
					jnsppk = "Borat";
				}
			}

			if (!arr[3].trim().equals("NPK") && !arr[3].trim().equals("Urea")
					&& !arr[3].trim().equals("Borat")
					&& !arr[3].trim().equals("null")) {
				if (dtlppk != null) {
					dtlppk = dtlppk + "," + arrjmlchk[3] + " " + arrJml[3]
							+ " kg";
				} else {
					dtlppk = arrjmlchk[3] + " " + arrJml[3] + " kg";
				}

				if (jnsppk != null) {
					jnsppk = jnsppk + "," + arrjmlchk[3];
				} else {
					jnsppk = arrjmlchk[3];
				}
			}

			String campurpupuk = db.getcampur_pupuk(modellahan);
			String bahanpupuk = db.getbahan_pupuk(modellahan);
			String tutupanlahan = db.gettutupan_lahan(modellahan);
			String lokasijualchk = db.getlokasi_jualchk(modellahan);
			String lokasijual = db.getlokasi_jual(modellahan);

			// Log.d("lahan sent",statuslahan+""+lamalahan+""+luasprod+""+luasnonprod+""+tahuntanamprod+""+tahuntanamnonprod+""+pernahpanen+""+sikluspanen+""+beratbuahpanen+""+properpanen
			// +""+asalbibitchk+""+asalbibit+""+jenisbibit+""+hibrida+""+asalpupukchk+""+asalpupuk+""+jenispupuk+""+gunapupukchk+""+gunapupuk+""+campurpupuk+""+bahanpupuk
			// +""+tutupanlahan+""+lokasijualchk+""+lokasijual+"");

			List<NameValuePair> paramLahan = new ArrayList<NameValuePair>();
			paramLahan.add(new BasicNameValuePair("id_kk", kirimid));
			paramLahan.add(new BasicNameValuePair("enum_id", enumidLahan));
			paramLahan.add(new BasicNameValuePair("shape_id", shapeid));
			paramLahan.add(new BasicNameValuePair("status_lahan", statuslahan));
			paramLahan.add(new BasicNameValuePair("lama_lahan", lamalahan));
			paramLahan.add(new BasicNameValuePair("luas_produksi", luasprod));
			paramLahan.add(new BasicNameValuePair("luas_nonproduksi",
					luasnonprod));
			paramLahan.add(new BasicNameValuePair("tahun_tanam_prod",
					tahuntanamprod));
			paramLahan.add(new BasicNameValuePair("tahun_tanam_nonprod",
					tahuntanamnonprod));
			paramLahan.add(new BasicNameValuePair("pernah_panen", pernahpanen));
			paramLahan.add(new BasicNameValuePair("siklus_panen", sikluspanen));
			paramLahan.add(new BasicNameValuePair("berat_buah_perpanen",
					beratbuahpanen));
			paramLahan
					.add(new BasicNameValuePair("prod_perpanen", properpanen));
			paramLahan.add(new BasicNameValuePair("asal_bibit", asalbibitchk
					+ " " + asalbibit));
			paramLahan.add(new BasicNameValuePair("jenis_bibit", jenisbibit));
			paramLahan.add(new BasicNameValuePair("hibrida", hibrida));
			paramLahan.add(new BasicNameValuePair("asal_pupuk", asalpupukchk
					+ " " + asalpupuk));
			paramLahan.add(new BasicNameValuePair("jenis_pupuk", jnsppk));
			paramLahan.add(new BasicNameValuePair("penggunaan_pupuk", dtlppk));
			paramLahan.add(new BasicNameValuePair("campur_pupuk", campurpupuk));
			paramLahan.add(new BasicNameValuePair("bahan_pupuk", bahanpupuk));
			paramLahan
					.add(new BasicNameValuePair("tutupan_lahan", tutupanlahan));
			paramLahan.add(new BasicNameValuePair("lokasi_jual", lokasijualchk
					+ " " + lokasijual));

			try {
				JSONObject jsonlahan = jsonParser.makeHttpRequest(db.getURL()
						+ url_save_lahan, "POST", paramLahan);
				db.updateStatus(id_kk, "1", getTimeOn);
				db.updateStatusLahan(shapeid, "1", getTimeOn);
				Log.d("Mengirim data lahan", jsonlahan.toString());
				Log.d("Request Ok", "True");
			} catch (Exception e) {
				e.printStackTrace();
			}

			Intent intent = new Intent(context, com.inobu.mquestioner.HalamanUtama.class);
			startActivity(intent);
			// }
			// }
			return null;
		}
	}
}