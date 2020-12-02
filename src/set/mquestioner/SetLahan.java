package set.mquestioner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.inobu.mquestionair.R.id;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SetLahan extends Activity {
	LinearLayout mainLayout;
	LinearLayout jualLayout;
	com.inobu.mquestioner.InputFilterMinMax InputFilter = null;
	TextView shapeIDs, txtProdTaun;

	EditText txtstLain, txtLuasProd, txtLuasNonProd, txThProd, txThNonProd,
			txtLainKali, txtBerat, txshapeid;
	CheckBox cbcSKT, cbcSHM, cbcSKTA, cbcWarisan, cbcNull, cbcstLain;
	final Context context = this;
	RadioButton rb10, rb1020, rb20, rbWarisan;
	RadioButton rbya, rbtdk, rb1, rb2;
	RadioGroup rgUmur, rgPernah;

	RadioButton rbrHutan, rbrklpsawit, rbrGambut, rbrsawah, rbrttplain;
	RadioButton rbrPabrik, rbrKoperasi, rbrPengepul, rbrJuallain;
	RadioGroup rgrTutupan, rgrJual, rgrPanen;
	EditText txttutplain, txtpabrik, txtkoperasi, txtpengepul, txtTBSlain;
	String getKosong = "";

	com.inobu.mquestioner.DbHelper db = new com.inobu.mquestioner.DbHelper(this);
	Button butNext;
	int id_kk = 0;
	// String kkn = "";
	String enumID = null;
	int lahan = 1;
	// int randomPIN =0;
	String id_lahan = "01";

	double luasProd = 0;
	double luasNonProd = 0;
	String TahunTanamProd = "0";
	String TahunTanamNonProd = "0";
	// String BeratProd = "0";
	String lama = "";
	Boolean pernah = true;
	int panen = 1;
	String tutup = "";
	String jual = "";
	String jual_chk = "";
	String status = null;
	String shapeIDB = "";
	double Berat = 0;
	double Luas = 0;
	double lainkali = 1;
	double ProdPer = 0;
	private boolean isUpdate;
	private boolean isTambah;
	private String UpFrom;
	String getkosong = "";
	CheckBox[] ckDigits = new CheckBox[4];
	String idlhn;
	String idkkn;
	String fstatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lahan);
		getActionBar().setTitle("Lahan");

		mainLayout = (LinearLayout) this.findViewById(R.id.layoutPanen);
		jualLayout = (LinearLayout) this.findViewById(R.id.layoutJual);

		shapeIDs = (TextView) findViewById(R.id.shapesIDs);

		cbcSKT = (CheckBox) findViewById(R.id.cb0);
		cbcSHM = (CheckBox) findViewById(R.id.cb1);
		cbcSKTA = (CheckBox) findViewById(R.id.cb2);
		cbcWarisan = (CheckBox) findViewById(R.id.cb3);
		cbcNull = (CheckBox) findViewById(R.id.cb4);
		cbcstLain = (CheckBox) findViewById(R.id.cb5);

		txtstLain = (EditText) findViewById(R.id.txstLain);
		txshapeid = (EditText) findViewById(R.id.shapesIDs);

		rgUmur = (RadioGroup) findViewById(R.id.rgUmurLahan);
		rgPernah = (RadioGroup) findViewById(R.id.rgPernahPanen);

		txtLuasProd = (EditText) findViewById(R.id.txLuasProd);
		txtLuasNonProd = (EditText) findViewById(R.id.txLuasNonProd);
		txThProd = (EditText) findViewById(R.id.txThProd);
		txThNonProd = (EditText) findViewById(R.id.txThNonProd);
		txtLainKali = (EditText) findViewById(R.id.txlainkali);
		txtBerat = (EditText) findViewById(R.id.txBerat);
		txtProdTaun = (TextView) findViewById(R.id.txProdPerTaun);

		rb10 = (RadioButton) findViewById(R.id.rb10th);
		rbya = (RadioButton) findViewById(R.id.rbya);
		rb1 = (RadioButton) findViewById(R.id.rb1);

		rb10.setChecked(true);
		rbya.setChecked(true);
		rb1.setChecked(true);
		rb1.setSelected(true);

		rbrHutan = (RadioButton) findViewById(R.id.rbHutan);

		rbrklpsawit = (RadioButton) findViewById(R.id.rbKlpSwt);
		rbrGambut = (RadioButton) findViewById(R.id.rbLhnGmbut);
		rbrsawah = (RadioButton) findViewById(R.id.rbSawah);
		rbrttplain = (RadioButton) findViewById(R.id.rbTtpLain);

		rbrPabrik = (RadioButton) findViewById(R.id.rbPabrik);
		rbrKoperasi = (RadioButton) findViewById(R.id.rbKoperasi);
		rbrPengepul = (RadioButton) findViewById(R.id.rbPengepul);
		rbrJuallain = (RadioButton) findViewById(R.id.rbJualLain);

		rgrTutupan = (RadioGroup) findViewById(R.id.rgTutupan);
		rgrJual = (RadioGroup) findViewById(R.id.rbJual);
		rgrPanen = (RadioGroup) findViewById(R.id.rgPanen);

		txttutplain = (EditText) findViewById(R.id.txTtplain);
		txtpabrik = (EditText) findViewById(R.id.txpabrik);
		txtkoperasi = (EditText) findViewById(R.id.txKoperasi);
		txtpengepul = (EditText) findViewById(R.id.txPengepul);
		txtTBSlain = (EditText) findViewById(R.id.txJualLain);
		butNext = (Button) findViewById(R.id.btLahanNext);

		mainLayout = (LinearLayout) this.findViewById(R.id.layoutPanen);
		jualLayout = (LinearLayout) this.findViewById(R.id.layoutJual);

		// tp1.setVisibility(View.VISIBLE);
		mainLayout.setVisibility(View.GONE);
		jualLayout.setVisibility(View.GONE);

		txtstLain.setEnabled(false);
		txtpabrik.setEnabled(false);
		txtkoperasi.setEnabled(false);
		txtpengepul.setEnabled(false);
		txtTBSlain.setEnabled(false);

		txThProd.setEnabled(false);
		txThNonProd.setEnabled(false);

		// DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddhhmmss");
		DateFormat dateFormatter = new SimpleDateFormat("ddMMyyhhmmss");
		dateFormatter.setLenient(false);
		Date today = new Date();
		String shapedate = dateFormatter.format(today);

		Log.d("tag ", shapedate);
		txttutplain.setEnabled(false);
		txtLainKali.setEnabled(false);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			isUpdate = getIntent().getExtras().getBoolean("updateList");
			getkosong = getIntent().getExtras().getString("kosong");
			isTambah = getIntent().getExtras().getBoolean("tambah");
			id_kk = getIntent().getExtras().getInt("rowID");
			idkkn = getIntent().getExtras().getString("rowID");
			fstatus = getIntent().getExtras().getString("status");

			Log.d("isUpdate Response", id_kk + "");
			Log.d("isUpdate Response idkkn", idkkn + "");
			if (isUpdate) {
				Log.d("isUpdate response", "isUpdate");
				butNext.setText("Simpan");
				txshapeid.setText(getIntent().getExtras().getString("shapeid"));

				Log.d("update Response", id_kk + "");
				String status_lahan = getIntent().getExtras().getString(
						"statuslhn");
				Log.d("isUpdate Response", isUpdate + "");
				String arr[] = status_lahan.split(",");

				int ckDigit[] = { id.cb0, id.cb1, id.cb2, id.cb3, id.cb4,
						id.cb5 };
				Log.d("isUpdate Response", ckDigit.length + "");
				String get = "";

				int sett = 0;
				for (int i = 0; i < arr.length; i++) {
					sett = i;
				}

				Log.d("sett Response", sett + "");
				Log.d("sett Response", arr[0].trim().toString());
				Log.d("sett Response", arr[1].trim().toString());
				Log.d("sett Response", arr[2].trim().toString());
				Log.d("sett Response", arr[3].trim().toString());
				Log.d("sett Response", arr[4].trim().toString());
				Log.d("sett Response", arr[5].trim().toString());

				if (arr[0].trim().equals("SKT")) {
					Log.d("sett Response", arr[0].toString());
					ckDigits[0] = (CheckBox) findViewById(ckDigit[0]);
					ckDigits[0].setChecked(true);
				}

				if (arr[1].trim().equals("SHM")) {
					Log.d("sett Response", arr[1].toString());
					ckDigits[1] = (CheckBox) findViewById(ckDigit[1]);
					ckDigits[1].setChecked(true);
				}

				if (arr[2].trim().equals("SKTA")) {
					Log.d("sett Response", arr[2].toString());
					ckDigits[2] = (CheckBox) findViewById(ckDigit[2]);
					ckDigits[2].setChecked(true);
				}

				if (arr[3].trim().equals("Warisan")) {
					Log.d("sett Response", arr[3].toString());
					ckDigits[3] = (CheckBox) findViewById(ckDigit[3]);
					ckDigits[3].setChecked(true);
				}

				if (arr[4].trim().equals("Tidak Punya")) {
					Log.d("sett Response", arr[4].toString());
					ckDigits[4] = (CheckBox) findViewById(ckDigit[4]);
					ckDigits[4].setChecked(true);
				}

				if (sett != 0 && sett >= 5)
					if (!arr[5].equals("SKT") && !arr[5].equals("SHM")
							&& !arr[5].equals("SKTA") && !arr[5].equals("null")) {
						Log.d("sett Response 5", arr[5].toString());
						ckDigits[5] = (CheckBox) findViewById(ckDigit[5]);
						ckDigits[5].setChecked(true);
						txtstLain.setEnabled(true);
						get = arr[5];
					}

				txtstLain.setText(get);
				Log.d("isUpdate Response", get + "");

				String lama_lahan = getIntent().getExtras()
						.getString("lamalhn");
				if (lama_lahan.equals("Di bawah 10 tahun")) {
					rgUmur.check(R.id.rb10th);
				} else if (lama_lahan.equals("Antara 10-20 Tahun")) {
					rgUmur.check(R.id.rb1020th);
				} else if (lama_lahan.equals("Di atas 20 Tahun")) {
					rgUmur.check(R.id.rb20th);
				} else if (lama_lahan.equals("Turun Temurun (warisan)")) {
					rgUmur.check(R.id.rbwarisan);
				}
				Log.d("rgumur Response", "rgumur");

				txtLuasProd.setText(getIntent().getExtras().getString("luasp"));
				txtLuasNonProd.setText(getIntent().getExtras().getString(
						"luasnp"));
				txThProd.setText(getIntent().getExtras().getString("taunp"));
				txThNonProd
						.setText(getIntent().getExtras().getString("tanunp"));
				Log.d("text Response", "text");

				String pernah_panen = getIntent().getExtras().getString(
						"sudahp");
				if (pernah_panen.equals("1")) {
					rgPernah.check(R.id.rbya);
				} else if (pernah_panen.equals("0")) {
					rgPernah.check(R.id.rbtdk);
				}
				Log.d("pernah_panen response", "pernah_panen");
				String berapa = getIntent().getExtras().getString("pbulan");
				if (berapa.equals("1")) {
					rgrPanen.check(R.id.rb1);
				} else if (berapa.equals("2")) {
					rgrPanen.check(R.id.rb2);
				} else {
					rgrPanen.check(R.id.lainkali);
					txtLainKali.setEnabled(true);
					txtLainKali.setText(berapa);
				}

				Log.d("pernah_panen response", "pernah_panen");

				txtBerat.setText(getIntent().getExtras().getString("kirab"));
				txtProdTaun.setText(getIntent().getExtras().getString(
						"prodtaun"));

				Log.d("text2 response", "text2");
				String tutup = getIntent().getExtras().getString("tutuplahan");
				if (tutup.equals("Hutan")) {
					rgrTutupan.check(R.id.rbHutan);
				} else if (tutup.equals("Kebun Kelapa Sawit")) {
					rgrTutupan.check(R.id.rbKlpSwt);
				} else if (tutup.equals("Lahan Gambut")) {
					rgrTutupan.check(R.id.rbLhnGmbut);
				} else if (tutup.equals("Sawah")) {
					rgrTutupan.check(R.id.rbSawah);
				} else {
					rgrTutupan.check(R.id.rbTtpLain);
					txttutplain.setText(getIntent().getExtras().getString(
							"tutuplahan"));
					txttutplain.setEnabled(true);
				}
				Log.d("tutup response", "tutup");
				String jual = getIntent().getExtras().getString("jualtbschk");
				if (jual.equals("Pabrik")) {
					rgrJual.check(R.id.rbPabrik);
					txtpabrik.setEnabled(true);
					txtpabrik.setText(getIntent().getExtras().getString(
							"jualtbs"));
				} else if (jual.equals("Koperasi")) {
					rgrJual.check(R.id.rbKoperasi);
					txtkoperasi.setEnabled(true);
					txtkoperasi.setText(getIntent().getExtras().getString(
							"jualtbs"));
				} else if (jual.equals("Pengepul")) {
					rgrJual.check(R.id.rbPengepul);
					txtpengepul.setEnabled(true);
					txtpengepul.setText(getIntent().getExtras().getString(
							"jualtbs"));
				} else {
					rgrJual.check(R.id.rbJualLain);
					txtTBSlain.setEnabled(true);
					txtTBSlain.setText(getIntent().getExtras().getString(
							"jualtbs"));
				}
				Log.d("rgrJual response", "rgrJual");
			} else if (isTambah) {
				Log.d("isTambah response", "isTambah");
				enumID = Integer.toString(db.getEnumID());
				db.insertLahan(Integer.parseInt(idkkn), enumID, "null", status,
						lama, luasProd + "", luasNonProd + "", TahunTanamProd,
						TahunTanamNonProd, pernah, Integer.toString(panen),
						Double.toString(Berat), Double.toString(ProdPer),
						tutup, jual_chk, jual);
				// Log.d("shapeIDB Response",id_kk+"");
				// Log.d("db.getidLahan(id_kk) Response",db.getidLahan()+"");
				//
				// int idlahan = db.getidLahan();
				// Log.d("shapeIDB Response",idlahan+"");
				// if(idlahan <10){
				// idlhn = Integer.toString(db.getidLahan());
				// idlhn = "0"+idlhn;
				// }else if(idlahan >=10){
				// idlhn = Integer.toString(db.getidLahan());
				// }

				shapeIDs.setText(enumID + shapedate);
				shapeIDB = enumID + shapedate;
				Log.d("shapeIDB Response", enumID + shapedate + "");
				db.updateshapeid(db.getidLahan(), shapeIDB);
			} else {
				Log.d("normal response", "normal");
				id_kk = db.getid();
				Log.d("id_kk response", "id_kk");
				enumID = Integer.toString(db.getEnumID());
				Log.d("enumID response", "enumID");

				db.insertLahan(id_kk, enumID, "null", status, lama, luasProd
						+ "", luasNonProd + "", TahunTanamProd,
						TahunTanamNonProd, pernah, Integer.toString(panen),
						Double.toString(Berat), Double.toString(ProdPer),
						tutup, jual_chk, jual);
				// Log.d("shapeIDB Response",id_kk+"");
				// Log.d("db.getidLahan(id_kk) Response",db.getidLahan()+"");
				//
				// int idlahan = db.getidLahan();
				// Log.d("shapeIDB Response",idlahan+"");
				// if(idlahan <10){
				// idlhn = Integer.toString(db.getidLahan());
				// idlhn = "0"+idlhn;
				// }else if(idlahan >=10){
				// idlhn = Integer.toString(db.getidLahan());
				// }

				shapeIDs.setText(enumID + shapedate);
				shapeIDB = enumID + shapedate;
				Log.d("shapeIDB Response", enumID + shapedate + "");
				db.updateshapeid(db.getidLahan(), shapeIDB);
			}
		}

		txtLuasNonProd.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				// if (!txtLuasNonProd.getText().toString().matches("")) {
				//
				// } else {
				//
				// }

				if (!s.toString().isEmpty() && s.toString() != null) {
					luasNonProd = Double.parseDouble(s.toString());
					txThNonProd.setEnabled(true);
				} else {
					luasNonProd = 0;
					txThNonProd.setEnabled(false);
				}
				Log.d("Berat Response", s.toString());
			}
		});

		txtLuasProd.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!s.toString().isEmpty() && s.toString() != null) {
					luasProd = Double.parseDouble(s.toString());
					txThProd.setEnabled(true);
				} else {
					luasProd = 0;
					txThProd.setEnabled(false);
				}

				Log.d("Berat Response", s.toString());
				// ProdPer = Berat * lainkali *12;
				ProdPer = Berat * lainkali * 12;
				ProdPer = ProdPer / luasProd;

				txtProdTaun.setText(String.format("%.2f", ProdPer) + " ");

				if (s.toString().matches("0") || s.toString().matches("")) {
					txtProdTaun.setText("0.00");
				}

				// txtProdTaun.setText(ProdPer+" ");
			}
		});

		txThProd.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!txtLuasProd.getText().toString().matches("")
						&& txThProd.getText().toString().matches("")) {
					mainLayout.setVisibility(LinearLayout.GONE);
					jualLayout.setVisibility(View.GONE);
				} else {
					mainLayout.setVisibility(LinearLayout.VISIBLE);
					jualLayout.setVisibility(View.VISIBLE);
				}
			}
		});

		txtBerat.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				if (!s.toString().isEmpty() && s.toString() != null) {
					Berat = Double.parseDouble(s.toString());
					txThNonProd.setEnabled(true);
				} else {
					txThNonProd.setEnabled(false);
				}

				Log.d("Berat Response", s.toString());
				// ProdPer = Berat * lainkali *12;
				ProdPer = Berat * lainkali * 12;
				ProdPer = ProdPer / luasProd;

				txtProdTaun.setText(String.format("%.2f", ProdPer) + " ");
				if (s.toString().matches("0") || s.toString().matches("")) {
					txtProdTaun.setText("0.00");
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		rgrPanen.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb1:
					panen = 1;
					txtLainKali.setEnabled(false);
					break;
				case R.id.rb2:
					panen = 2;
					txtLainKali.setEnabled(false);
					break;
				case R.id.lainkali:
					txtLainKali.setEnabled(true);
					if (!txtLainKali.getText().toString().isEmpty()
							&& txtLainKali.getText().toString() != null)
						panen = Integer.parseInt(txtLainKali.getText()
								.toString());
					else
						panen = 0;
					break;
				}
				// ProdPer = Berat * panen *12;
				ProdPer = Berat * panen * 12;
				ProdPer = ProdPer / luasProd;
				// txtProdTaun.setText(Double.toString(ProdPer));
				txtProdTaun.setText(String.format("%.2f", ProdPer) + " ");
				// txtProdTaun.setText(ProdPer+" ");
			}
		});

		rgrTutupan.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rbHutan:
					tutup = "Hutan";
					txttutplain.setEnabled(false);
					break;
				case R.id.rbKlpSwt:
					tutup = "Kebun Kelapa Sawit";
					txttutplain.setEnabled(false);
					break;
				case R.id.rbLhnGmbut:
					tutup = "Lahan Gambut";
					txttutplain.setEnabled(false);
					break;
				case R.id.rbSawah:
					tutup = "Sawah";
					txttutplain.setEnabled(false);
					break;
				case R.id.rbTtpLain:
					txttutplain.setEnabled(true);
					break;
				}

				if (!txttutplain.getText().toString().isEmpty()
						&& txttutplain.getText().toString() != null)
					tutup = tutup + " " + txttutplain.getText().toString();
				else {
					getKosong = "kosong";
				}
			}
		});

		rgrJual.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rbPabrik:
					txtpabrik.setEnabled(true);
					txtkoperasi.setEnabled(false);
					txtpengepul.setEnabled(false);
					txtTBSlain.setEnabled(false);
					jual = "Pabrik";
					if (!txtpabrik.getText().toString().isEmpty()
							&& txtpabrik.getText().toString() != null)
						jual = jual + " " + txtpabrik.getText().toString();
					else {
						getKosong = "kosong";
					}
					break;
				case R.id.rbKoperasi:
					txtkoperasi.setEnabled(true);
					txtpabrik.setEnabled(false);
					txtpengepul.setEnabled(false);
					txtTBSlain.setEnabled(false);
					jual = "Koperasi";
					if (!txtkoperasi.getText().toString().isEmpty()
							&& txtkoperasi.getText().toString() != null)
						jual = jual + " " + txtkoperasi.getText().toString();
					else {
						getKosong = "kosong";
					}
					break;
				case R.id.rbPengepul:
					txtpengepul.setEnabled(true);
					txtpabrik.setEnabled(false);
					txtkoperasi.setEnabled(false);
					txtTBSlain.setEnabled(false);
					jual = "Pengepul";
					if (!txtpengepul.getText().toString().isEmpty()
							&& txtpengepul.getText().toString() != null)
						jual = jual + " " + txtpengepul.getText().toString();
					else {
						getKosong = "kosong";
					}
					break;
				case R.id.rbJualLain:
					txtTBSlain.setEnabled(true);
					txtpabrik.setEnabled(false);
					txtkoperasi.setEnabled(false);
					txtpengepul.setEnabled(false);
					jual = "";
					if (!txtTBSlain.getText().toString().isEmpty()
							&& txtTBSlain.getText().toString() != null)
						jual = jual + " " + txtTBSlain.getText().toString();
					else {
						getKosong = "kosong";
					}
					break;
				}
			}
		});

		cbcstLain.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (cbcstLain.isChecked()) {
					txtstLain.setEnabled(true);
					if (txtstLain.getText().toString().isEmpty()) {
						getKosong = "kosong";
					}
				} else if (!cbcstLain.isChecked()) {
					txtstLain.setEnabled(false);
					txtstLain.setText("");
				}
			}
		});

		txtLainKali.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!s.toString().isEmpty() && s.toString() != null)
					lainkali = Double.parseDouble(s.toString());
				else
					lainkali = 0;

				Log.d("Berat Response", s.toString());
				// produktifitas itu = (Jumlah Ton/panen * jumlah panen * 12) /
				// luas lahan
				ProdPer = Berat * lainkali * 12;
				ProdPer = ProdPer / luasProd;
				// txtProdTaun.setText(Double.toString(ProdPer));
				txtProdTaun.setText(String.format("%.2f", ProdPer) + " ");
				txtProdTaun.setText(ProdPer + " ");
			}
		});

		if (txtLuasProd.getText().toString().isEmpty())
			getKosong = "kosong";
		if (txtLuasNonProd.getText().toString().isEmpty())
			getKosong = "kosong";
		if (txThProd.getText().toString().isEmpty())
			getKosong = "kosongn";
		if (txThNonProd.getText().toString().isEmpty())
			getKosong = "kosongn";
		if (txtLainKali.getText().toString().isEmpty())
			getKosong = "kosong";
		if (txtBerat.getText().toString().isEmpty())
			getKosong = "kosong";
		if (rbrttplain.isChecked()) {
			if (txttutplain.getText().toString().isEmpty())
				getKosong = "kosong";
		}
		if (rbrPabrik.isChecked()) {
			if (txtpabrik.getText().toString().isEmpty())
				getKosong = "kosong";
		}
		if (rbrKoperasi.isChecked()) {
			if (txtkoperasi.getText().toString().isEmpty())
				getKosong = "kosong";
		}
		if (rbrPengepul.isChecked()) {
			if (txtpengepul.getText().toString().isEmpty())
				getKosong = "kosong";
		}
		if (rbrJuallain.isChecked()) {
			if (txtTBSlain.getText().toString().isEmpty())
				getKosong = "kosong";
		}

		butNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// if (status.matches("")) {
				// Toast.makeText(getBaseContext(),
				// "Lengkapi field yang masih kosong",
				// Toast.LENGTH_SHORT).show();
				// } else {
				justSaveData();

				Intent intent = null;
				if (isUpdate) {
					Log.d("update Response", "update");
					intent = new Intent(SetLahan.this, list.mquestioner.ListLahan.class);
					intent.putExtra("idKK", idkkn + "");
					intent.putExtra("status", fstatus);
					Log.d("update Response", id_kk + "");

				} else {
					intent = new Intent(SetLahan.this, SetBibit.class);
					intent.putExtra("shapeid", shapeIDs.getText().toString());
					intent.putExtra("kosong", getKosong);
				}
				startActivity(intent);
				// }
			}
		});
	}

	void justSaveData() {
		if (cbcSKT.isChecked()) {
			status = "SKT";
		} else {
			status = "null";
		}

		if (cbcSHM.isChecked()) {
			status = status + ",SHM";
		} else {
			status = status + ",null";
		}

		if (cbcSKTA.isChecked()) {
			status = status + ",SKTA";
		} else {
			status = status + ",null";
		}

		if (cbcWarisan.isChecked()) {
			status = status + ",Warisan";
		} else {
			status = status + ",null";
		}

		if (cbcNull.isChecked()) {
			status = status + ",Tidak Punya";
		} else {
			status = status + ",null";
		}

		if (cbcstLain.isChecked()) {
			if (!txtstLain.getText().toString().equals("")) {
				status = status + "," + txtstLain.getText().toString();
			} else {
				status = status + ",null";
			}
		} else {
			status = status + ",null";
		}

		switch (rgUmur.getCheckedRadioButtonId()) {
		case R.id.rb10th:
			lama = "Di bawah 10 tahun";
			break;
		case R.id.rb1020th:
			lama = "Antara 10-20 Tahun";
			break;
		case R.id.rb20th:
			lama = "Di atas 20 tahun";
			break;
		case R.id.rbwarisan:
			lama = "Turun temurun (warisan)";
			break;
		}

		// luasProd = Double.parseDouble(txtLuasProd.getText().toString());
		// luasNonProd = txtLuasNonProd.getText().toString();

		TahunTanamProd = txThProd.getText().toString();
		TahunTanamNonProd = txThNonProd.getText().toString();

		switch (rgPernah.getCheckedRadioButtonId()) {
		case R.id.rbya:
			pernah = true;
			break;
		case R.id.rbtdk:
			pernah = false;
			break;
		}

		switch (rgrTutupan.getCheckedRadioButtonId()) {
		case R.id.rbHutan:
			tutup = "Hutan";
			break;
		case R.id.rbKlpSwt:
			tutup = "Kebun Kelapa Sawit";
			break;
		case R.id.rbLhnGmbut:
			tutup = "Lahan Gambut";
			break;
		case R.id.rbSawah:
			tutup = "Sawah";
			break;
		case R.id.rbTtpLain:
			tutup = txttutplain.getText().toString();
			break;
		}

		switch (rgrJual.getCheckedRadioButtonId()) {
		case R.id.rbPabrik:
			jual_chk = "Pabrik";
			jual = txtpabrik.getText().toString();
			break;
		case R.id.rbKoperasi:
			jual_chk = "Koperasi";
			jual = txtkoperasi.getText().toString();
			break;
		case R.id.rbPengepul:
			jual_chk = "Pengepul";
			jual = txtpengepul.getText().toString();
			break;
		case R.id.rbJualLain:
			jual_chk = "";
			jual = txtTBSlain.getText().toString();
			break;
		}

		switch (rgrPanen.getCheckedRadioButtonId()) {
		case R.id.rb1:
			panen = 1;
			break;
		case R.id.rb2:
			panen = 2;
			break;
		case R.id.lainkali:
			if (!txtLainKali.getText().toString().isEmpty()
					&& txtLainKali.getText().toString() != null)
				panen = Integer.parseInt(txtLainKali.getText().toString());
			else
				panen = 0;
			break;
		}

		db.updateLahan(txshapeid.getText().toString(), status, lama,
				txtLuasProd.getText().toString(), luasNonProd + "",
				TahunTanamProd, TahunTanamNonProd, pernah,
				Integer.toString(panen), txtBerat.getText().toString(),
				txtProdTaun.getText().toString(), tutup, jual_chk, jual);
		Log.d("UpFrom Response", txshapeid.getText().toString() + " " + status
				+ " " + lama + " " + luasProd + " " + luasNonProd + " "
				+ TahunTanamProd + " " + TahunTanamNonProd + " " + pernah + " "
				+ Integer.toString(panen) + " " + txtBerat.getText().toString()
				+ " " + txtProdTaun.getText().toString() + " " + tutup + " "
				+ jual_chk + " " + jual);
	}

	@Override
	public void onBackPressed() {
		Intent i = null;
		if (isUpdate) {
			finish();
		} else if (isTambah) {
			i = new Intent(getApplicationContext(), list.mquestioner.ListLahan.class);
			i.putExtra("idKK", idkkn);
			db.deleteRowLahan(db.getidLahan());
			startActivity(i);
		} else {
			i = new Intent(getApplicationContext(), SetNarasumber.class);
			i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			db.deleteRowLahan(db.getidLahan());
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
		AlertDialog.Builder alert = new AlertDialog.Builder(SetLahan.this);
		alert.setTitle(R.string.errorTitle);
		alert.setMessage("Anda Ingin keluar ?");
		alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Intent intent = null;
				intent = new Intent(context, com.inobu.mquestioner.HalamanUtama.class);

				if (isUpdate) {
					finish();
				} else if (isTambah) {
					db.deleteRowLahan(db.getidLahan());
				} else {
					db.deleteRowLahan(db.getidLahan());
				}

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
		AlertDialog.Builder alert = new AlertDialog.Builder(SetLahan.this);
		alert.setTitle(R.string.errorTitle);
		alert.setMessage("Anda Ingin keluar ?");
		alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Intent intent = null;
				intent = new Intent(SetLahan.this, list.mquestioner.ListLahan.class);

				if (isUpdate) {
					finish();
				} else if (isTambah) {
					intent.putExtra("idKK", idkkn);
					db.deleteRowLahan(db.getidLahan());
				} else {
					db.deleteRowLahan(db.getidLahan());
				}

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
