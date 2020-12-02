package set.mquestioner;

import com.inobu.mquestionair.R.id;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.content.Context;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class SetNarasumber extends Activity {
	EditText txtanggal, txbulan, txtahun;
	EditText txtNarasumber, txtNIK, txtTelp, txtKepala, txtjakLain,
			txtTmpLahir, txBlnLahir, txTaunLahir, txtSukuLain, txtKerUtamaLain,
			txtKerjaLain, txtAgtLain;

	EditText txtKoperasi, txtGapokTan, txtBumdes, txtKelompokTani;

	RadioGroup rgJkel, rgPddk, rgSK, rgKerjaUtama, rgAnggota;
	RadioButton rbSelectJmlKK, rbPddk, rbSelectSuku, rbSelectKerja,
			rbSelectAnggt;
	// RadioButton rbjk1, rbjk2, rbjk3, rbjk4, rbjklain;
	RadioButton rbTb, rbSD, rbSMP, rbSMA, rbDS;
	RadioButton rbJawa, rbSunda, rbMadura, rbMelayu, rbDayak, rbBanjar,
			rbSkLain;
	RadioButton rbKebun, rbPNS, rbWira, rbKruLain;
	RadioButton rbKop, rbGa, rbKopGa, rbKumpul, rbBumdes, rbKlpTani, rbAgtLain;
	Button butNext;
	final Context context = this;
	com.inobu.mquestioner.DbHelper db = new com.inobu.mquestioner.DbHelper(this);
	com.inobu.mquestioner.InputFilterMinMax InputFilter = null;
	String gDate, gMonth, gYear;
	String namans = "";
	String NIK = "0";
	String Telp = "0";
	String namaKK = "";
	String TempatLahir = "";
	String tglLahir = null;

	String tanggal = null;
	String bulan = null;
	String tahun = null;
	String TanggalLahir = null;
	String KerjaLain = null;
	int id_kk = 0;

	String jumlah = "0";
	String pendidikan = "";
	String organisasi = "";
	String suku = "";
	String kerjaUtama = "";
	String enumID = "";
	String getKosong = "";
	private boolean isUpdate;
	private String UpFrom = "normal";
	String kk = null;
	int kkid = 0;
	String desaid = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_narasumber);

		txtNarasumber = (EditText) findViewById(R.id.txNarasumber);
		txtNIK = (EditText) findViewById(R.id.txNIK);
		txtTelp = (EditText) findViewById(R.id.txTelp);
		txtKepala = (EditText) findViewById(R.id.txKepala);

		txtjakLain = (EditText) findViewById(R.id.txLainKali);
		txtTmpLahir = (EditText) findViewById(R.id.txTmLahir);

		txtKerjaLain = (EditText) findViewById(R.id.txKerjaLain);

		txtSukuLain = (EditText) findViewById(R.id.txSukuLain);
		txtKerUtamaLain = (EditText) findViewById(R.id.txKerUtamaLain);

		// rbjk1 = (RadioButton) findViewById(R.id.jak1);
		rbTb = (RadioButton) findViewById(R.id.rbPendTB);
		rbJawa = (RadioButton) findViewById(R.id.rbSukuJw);

		rbKebun = (RadioButton) findViewById(R.id.rbKerKebun);
		rbKop = (RadioButton) findViewById(R.id.rbAgtKop);

		rgJkel = (RadioGroup) findViewById(R.id.rgJmlKel);// jumlah keluarga
		rgPddk = (RadioGroup) findViewById(R.id.rgPendidikan);// penduduk
		rgSK = (RadioGroup) findViewById(R.id.rgSuku);// suku
		rgKerjaUtama = (RadioGroup) findViewById(R.id.rgKerja);// suku
		rgAnggota = (RadioGroup) findViewById(R.id.rgAnggota);// suku

		txtanggal = (EditText) findViewById(R.id.txDates);
		txbulan = (EditText) findViewById(R.id.txMonth);
		txtahun = (EditText) findViewById(R.id.txYear);

		butNext = (Button) findViewById(R.id.btNamaNext);

		txtKoperasi = (EditText) findViewById(R.id.txAgtKop);
		txtGapokTan = (EditText) findViewById(R.id.txGaPokTan);
		txtBumdes = (EditText) findViewById(R.id.txBumdes);
		txtKelompokTani = (EditText) findViewById(R.id.txKelompokTani);
		txtAgtLain = (EditText) findViewById(R.id.txAgtLain);

		// rbjk1.setChecked(true);
		// rbTb.setChecked(true);
		// rbJawa.setChecked(true);
		// rbKebun.setChecked(true);
		// rbKop.setChecked(true);

		id_kk = db.getid();

		enumID = Integer.toString(db.getEnumID()) + desaid;
		txtjakLain.setEnabled(false);
		txtKoperasi.setEnabled(false);
		txtGapokTan.setEnabled(false);
		txtBumdes.setEnabled(false);
		txtKelompokTani.setEnabled(false);
		txtAgtLain.setEnabled(false);

		txtSukuLain.setEnabled(false);
		txtKerUtamaLain.setEnabled(false);

		// kkid = Integer.parseInt(kkdd);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			isUpdate = getIntent().getExtras().getBoolean("updateList");
			if (isUpdate) {
				butNext.setText("Simpan");
				id_kk = Integer.parseInt(getIntent().getExtras().getString(
						"idKK"));
				txtNarasumber.setText(getIntent().getExtras().getString(
						"narasumber"));
				txtNIK.setText(getIntent().getExtras().getString("nik"));
				txtTelp.setText(getIntent().getExtras().getString("telp"));
				txtKepala.setText(getIntent().getExtras().getString("namakk"));
				// txtjakLain.setText(getIntent().getExtras().getString("jml"));
				Log.d("getKosong1 Response", Integer.toString(id_kk));
				String jmlKK = getIntent().getExtras().getString("jml");
				if (jmlKK.equals("1")) {
					rgJkel.check(R.id.jak1);
				} else if (jmlKK.equals("2")) {
					rgJkel.check(R.id.jak2);
				} else if (jmlKK.equals("3")) {
					rgJkel.check(R.id.jak3);
				} else if (jmlKK.equals("4")) {
					rgJkel.check(R.id.jak4);
				} else if (jmlKK.equals("5")) {
					rgJkel.check(R.id.jak5);
				} else {
					rgJkel.check(R.id.lainkali);
					txtjakLain.setEnabled(true);
					txtjakLain
							.setText(getIntent().getExtras().getString("jml"));
				}

				txtTmpLahir.setText(getIntent().getExtras()
						.getString("tmplair"));
				tglLahir = getIntent().getExtras().getString("tgllair");
				// Log.d("haduh ", tglLahir);
				String arr[] = tglLahir.split("-");
				tanggal = arr[0];
				txtanggal.setText(tanggal);
				// Log.d("tanggal ", tanggal);
				bulan = arr[1];
				txbulan.setText(bulan);
				// Log.d("bulan ", bulan);
				tahun = arr[2];
				txtahun.setText(tahun);
				// Log.d("tahun ", tahun);

				String didik = getIntent().getExtras().getString("pendidik");
				if (didik.equals("Tidak Bersekolah")) {
					rgPddk.check(R.id.rbPendTB);
				} else if (didik.equals("SD")) {
					rgPddk.check(R.id.rbPendSD);
				} else if (didik.equals("SMP")) {
					rgPddk.check(R.id.rbPendSMP);
				} else if (didik.equals("SMA")) {
					rgPddk.check(R.id.rbPendSMA);
				} else if (didik.equals("Lulusan Sarjana dan Sederajat")) {
					rgPddk.check(R.id.rbPendDplm);
				}

				suku = getIntent().getExtras().getString("suku");
				rbJawa.setChecked(false);
				if (suku.equals("Jawa")) {
					rgSK.check(R.id.rbSukuJw);
				} else if (suku.equals("Sunda")) {
					rgSK.check(R.id.rbSukuSnda);
				} else if (suku.equals("Madura")) {
					rgSK.check(R.id.rbSukuMdr);
				} else if (suku.equals("Melayu")) {
					rgSK.check(R.id.rbSukuMly);
				} else if (suku.equals("Dayak")) {
					rgSK.check(R.id.rbSukuDyk);
				} else if (suku.equals("Banjar")) {
					rgSK.check(R.id.rbSukuBjr);
				} else {
					rgSK.check(R.id.rbSukuLain);
					txtSukuLain.setText(suku);
					txtSukuLain.setEnabled(true);
				}

				kerjaUtama = getIntent().getExtras().getString("kjutama");
				rbKebun.setChecked(false);
				if (kerjaUtama.equals("Pekebun")) {
					rgKerjaUtama.check(R.id.rbKerKebun);
				} else if (kerjaUtama.equals("PNS")) {
					rgKerjaUtama.check(R.id.rbKerPNS);
				} else if (kerjaUtama.equals("Wirausaha")) {
					rgKerjaUtama.check(R.id.rbKerWira);
				} else {
					rgKerjaUtama.check(R.id.rbKerLain);
					txtKerUtamaLain.setText(getIntent().getExtras().getString(
							"kjutama"));
					txtKerUtamaLain.setEnabled(true);
				}

				KerjaLain = getIntent().getExtras().getString("kjlain");
				txtKerjaLain.setText(getIntent().getExtras()
						.getString("kjlain"));

				organisasi = getIntent().getExtras().getString("agtorg");
				rbKop.setChecked(false);
				if (organisasi.equals("Koperasi")) {
					rgAnggota.check(R.id.rbAgtKop);
					txtKoperasi.setText(organisasi);
					txtKoperasi.setEnabled(true);
				} else if (organisasi.equals("Gapoktan")) {
					rgAnggota.check(R.id.rbGapoktan);
					txtGapokTan.setText(organisasi);
					txtGapokTan.setEnabled(true);
				} else if (organisasi.equals("Koperasi dan Gapoktan")) {
					rgAnggota.check(R.id.rbKopGapok);
				} else if (organisasi.equals("Perkumpulan atau Asosiasi")) {
					rgAnggota.check(R.id.rbPerkumpulan);
				} else if (organisasi.equals("BUMDES")) {
					rgAnggota.check(R.id.rbBumdes);
					txtBumdes.setText(organisasi);
					txtBumdes.setEnabled(true);
				} else if (organisasi.equals("Kelompok Tani")) {
					rgAnggota.check(R.id.rbKelompokTani);
					txtKelompokTani.setText(organisasi);
					txtKelompokTani.setEnabled(true);
				} else {
					rgAnggota.check(R.id.rbAgtLain);
					txtAgtLain.setText(organisasi);
					txtAgtLain.setEnabled(true);
				}
			} else {
				desaid = getIntent().getExtras().getString("desaID");
			}
		}

		butNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (txtNarasumber.getText().toString().matches("")) {
					Toast.makeText(getBaseContext(),
							"Field Nama narasumber masih kosong",
							Toast.LENGTH_SHORT).show();
				} else if (txtKepala.getText().toString().matches("")) {
					Toast.makeText(getBaseContext(),
							"Field Nama kepala keluarga masih kosong",
							Toast.LENGTH_SHORT).show();
				} else {
					// Toast.makeText(getBaseContext(), "simpan data",
					// Toast.LENGTH_SHORT).show();
					savedata();
				}
			}
		});

		txtanggal.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				txtanggal.setFilters(new InputFilter[] { new com.inobu.mquestioner.InputFilterMinMax(
						"1", "31") });
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		txbulan.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				txbulan.setFilters(new InputFilter[] { new com.inobu.mquestioner.InputFilterMinMax(
						"1", "12") });
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		txtahun.addTextChangedListener(new TextWatcher() {
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
			}
		});

		rgKerjaUtama.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rbKerKebun:
					txtKerUtamaLain.setEnabled(false);
					txtKerUtamaLain.setFocusable(false);
					break;
				case R.id.rbKerPNS:
					txtKerUtamaLain.setEnabled(false);
					txtKerUtamaLain.setFocusable(false);
					break;
				case R.id.rbKerWira:
					txtKerUtamaLain.setEnabled(false);
					txtKerUtamaLain.setFocusable(false);
					break;
				case R.id.rbKerLain:
					txtKerUtamaLain.setEnabled(true);
					txtKerUtamaLain.setFocusable(true);
					break;
				}
			}
		});

		rgSK.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rbSukuJw:
					txtSukuLain.setEnabled(false);
					txtSukuLain.setFocusable(false);
					break;
				case R.id.rbSukuSnda:
					txtSukuLain.setEnabled(false);
					txtSukuLain.setFocusable(false);
					break;
				case R.id.rbSukuMdr:
					txtSukuLain.setEnabled(false);
					txtSukuLain.setFocusable(false);
					break;
				case R.id.rbSukuMly:
					txtSukuLain.setEnabled(false);
					txtSukuLain.setFocusable(false);
					break;
				case R.id.rbSukuDyk:
					txtSukuLain.setEnabled(false);
					txtSukuLain.setFocusable(false);
					break;
				case R.id.rbSukuBjr:
					txtSukuLain.setEnabled(false);
					txtSukuLain.setFocusable(false);
					break;
				case R.id.rbSukuLain:
					txtSukuLain.setEnabled(true);
					txtSukuLain.setFocusable(true);
					break;
				}
			}
		});

		rgAnggota.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.rbAgtKop:
					txtKoperasi.setEnabled(true);
					txtKoperasi.setFocusable(true);
					txtGapokTan.setEnabled(false);
					txtGapokTan.setFocusable(false);
					txtBumdes.setEnabled(false);
					txtBumdes.setFocusable(false);
					txtKelompokTani.setEnabled(false);
					txtKelompokTani.setFocusable(false);
					txtAgtLain.setEnabled(false);
					txtAgtLain.setFocusable(false);
					break;
				case R.id.rbGapoktan:
					txtGapokTan.setEnabled(false);
					txtGapokTan.setFocusable(false);
					txtKoperasi.setEnabled(true);
					txtKoperasi.setFocusable(true);
					txtBumdes.setEnabled(false);
					txtBumdes.setFocusable(false);
					txtKelompokTani.setEnabled(false);
					txtKelompokTani.setFocusable(false);
					txtAgtLain.setEnabled(false);
					txtAgtLain.setFocusable(false);
					break;
				case R.id.rbKopGapok:
					txtGapokTan.setEnabled(false);
					txtGapokTan.setFocusable(false);
					txtKoperasi.setEnabled(false);
					txtKoperasi.setFocusable(false);
					txtBumdes.setEnabled(false);
					txtBumdes.setFocusable(false);
					txtKelompokTani.setEnabled(false);
					txtKelompokTani.setFocusable(false);
					txtAgtLain.setEnabled(false);
					txtAgtLain.setFocusable(false);
					break;
				case R.id.rbPerkumpulan:
					txtGapokTan.setEnabled(false);
					txtGapokTan.setFocusable(false);
					txtKoperasi.setEnabled(false);
					txtKoperasi.setFocusable(false);
					txtBumdes.setEnabled(false);
					txtBumdes.setFocusable(false);
					txtKelompokTani.setEnabled(false);
					txtKelompokTani.setFocusable(false);
					txtAgtLain.setEnabled(false);
					txtAgtLain.setFocusable(false);
					break;
				case R.id.rbBumdes:
					txtGapokTan.setEnabled(false);
					txtGapokTan.setFocusable(false);
					txtKoperasi.setEnabled(false);
					txtKoperasi.setFocusable(false);
					txtBumdes.setEnabled(true);
					txtBumdes.setFocusable(false);
					txtKelompokTani.setEnabled(false);
					txtKelompokTani.setFocusable(false);
					txtAgtLain.setEnabled(false);
					txtAgtLain.setFocusable(false);
					break;
				case R.id.rbKelompokTani:
					txtGapokTan.setEnabled(false);
					txtGapokTan.setFocusable(false);
					txtKoperasi.setEnabled(false);
					txtKoperasi.setFocusable(false);
					txtBumdes.setEnabled(false);
					txtBumdes.setFocusable(false);
					txtKelompokTani.setEnabled(true);
					txtKelompokTani.setFocusable(true);
					txtAgtLain.setEnabled(false);
					txtAgtLain.setFocusable(false);
					break;
				case R.id.rbAgtLain:
					txtGapokTan.setEnabled(false);
					txtGapokTan.setFocusable(false);
					txtKoperasi.setEnabled(false);
					txtKoperasi.setFocusable(false);
					txtBumdes.setEnabled(false);
					txtBumdes.setFocusable(false);
					txtKelompokTani.setEnabled(false);
					txtKelompokTani.setFocusable(false);
					txtAgtLain.setEnabled(true);
					txtAgtLain.setFocusableInTouchMode(true);
					break;
				}
			}
		});

		rgJkel.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.jak1:
					txtjakLain.setEnabled(false);
					txtjakLain.setFocusableInTouchMode(false);
					break;
				case R.id.jak2:
					txtjakLain.setEnabled(false);
					txtjakLain.setFocusableInTouchMode(false);
					break;
				case R.id.jak3:
					txtjakLain.setEnabled(false);
					txtjakLain.setFocusableInTouchMode(false);
					break;
				case R.id.jak4:
					txtjakLain.setEnabled(false);
					txtjakLain.setFocusableInTouchMode(false);
					break;
				case R.id.jak5:
					txtjakLain.setEnabled(false);
					txtjakLain.setFocusableInTouchMode(false);
					break;
				case R.id.lainkali:
					txtjakLain.setEnabled(true);
					txtjakLain.setFocusableInTouchMode(true);
					break;
				}
			}
		});
	}

	void savedata() {
		namans = txtNarasumber.getText().toString();
		NIK = txtNIK.getText().toString();
		Telp = txtTelp.getText().toString();
		namaKK = txtKepala.getText().toString();
		TempatLahir = txtTmpLahir.getText().toString();

		tanggal = txtanggal.getText().toString();
		bulan = txbulan.getText().toString();
		tahun = txtahun.getText().toString();

		if (!tanggal.equals("") && !bulan.equals("") && !tahun.equals("")) {
			TanggalLahir = tanggal + "-" + bulan + "-" + tahun;
		} else {
			TanggalLahir = "00-00-0000";
		}

		KerjaLain = txtKerjaLain.getText().toString();

		switch (rgJkel.getCheckedRadioButtonId()) {
		case R.id.jak1:
			jumlah = "1";
			break;
		case R.id.jak2:
			jumlah = "2";
			break;
		case R.id.jak3:
			jumlah = "3";
			break;
		case R.id.jak4:
			jumlah = "4";
			break;
		case R.id.jak5:
			jumlah = "5";
			break;
		case R.id.lainkali:
			if (!txtjakLain.getText().toString().equals("")
					&& !txtjakLain.getText().toString().equals("0")) {
				jumlah = txtjakLain.getText().toString();
			} else if (txtjakLain.getText().toString().equals("")
					&& txtjakLain.getText().toString().equals("0")) {
				getKosong = "kosong";
				jumlah = "0";
			}
			break;
		}

		switch (rgPddk.getCheckedRadioButtonId()) {
		case R.id.rbPendTB:
			pendidikan = "Tidak Bersekolah";
			break;
		case R.id.rbPendSD:
			pendidikan = "SD";
			break;
		case R.id.rbPendSMP:
			pendidikan = "SMP";
			break;
		case R.id.rbPendSMA:
			pendidikan = "SMA";
			break;
		case R.id.rbPendDplm:
			pendidikan = "Lulusan Sarjana dan Sederajat";
			break;
		}

		switch (rgKerjaUtama.getCheckedRadioButtonId()) {
		case R.id.rbKerKebun:
			kerjaUtama = "Pekebun";
			break;
		case R.id.rbKerPNS:
			kerjaUtama = "PNS";
			break;
		case R.id.rbKerWira:
			kerjaUtama = "Wirausaha";
			break;
		case R.id.rbKerLain:
			if (!txtKerUtamaLain.getText().toString().isEmpty()) {
				kerjaUtama = txtKerUtamaLain.getText().toString();
			} else {
				getKosong = "kosong";
				kerjaUtama = " ";
			}
			break;
		}

		switch (rgAnggota.getCheckedRadioButtonId()) {
		case R.id.rbAgtKop:
			if (!txtKoperasi.getText().toString().isEmpty()) {
				organisasi = txtKoperasi.getText().toString();
			} else {
				getKosong = "kosong";
				organisasi = "";
			}
			break;
		case R.id.rbGapoktan:
			if (!txtGapokTan.getText().toString().isEmpty()) {
				organisasi = txtGapokTan.getText().toString();
			} else {
				getKosong = "kosong";
				organisasi = "";
			}
			break;
		case R.id.rbKopGapok:
			organisasi = "Koperasi dan Gapoktan";
			break;
		case R.id.rbPerkumpulan:
			organisasi = "Perkumpulan atau Asosiasi";
			break;
		case R.id.rbBumdes:
			if (!txtBumdes.getText().toString().isEmpty()) {
				organisasi = txtBumdes.getText().toString();
			} else {
				getKosong = "kosong";
				organisasi = "";
			}
			break;
		case R.id.rbKelompokTani:
			if (!txtKelompokTani.getText().toString().isEmpty()) {
				organisasi = txtKelompokTani.getText().toString();
			} else {
				getKosong = "kosong";
				organisasi = "";
			}
			break;
		case R.id.rbAgtLain:
			if (!txtAgtLain.getText().toString().isEmpty()) {
				organisasi = txtAgtLain.getText().toString();
			} else {
				getKosong = "kosong";
				organisasi = " ";
			}
			break;
		}

		switch (rgSK.getCheckedRadioButtonId()) {
		case R.id.rbSukuJw:
			suku = "Jawa";
			break;
		case R.id.rbSukuSnda:
			suku = "Sunda";
			break;
		case R.id.rbSukuMdr:
			suku = "Madura";
			break;
		case R.id.rbSukuMly:
			suku = "Melayu";
			break;
		case R.id.rbSukuDyk:
			suku = "Dayak";
			break;
		case R.id.rbSukuBjr:
			suku = "Banjar";
			break;
		case R.id.rbSukuLain:
			if (!txtSukuLain.getText().toString().isEmpty()) {
				suku = txtSukuLain.getText().toString();
			} else {
				getKosong = "kosong";
				suku = "";
			}
			break;
		}
		//
		// if (txtNarasumber.getText().toString().matches("")
		// || txtNama.getText().toString().matches("")) {
		// Toast.makeText(getBaseContext(), "Nama narasumber masih kosong",
		// Toast.LENGTH_SHORT).show();
		// } else {
		db.updateNarasumber(id_kk, enumID, namans, NIK, Telp, namaKK,
				Integer.parseInt(jumlah), TempatLahir, TanggalLahir,
				pendidikan, suku, kerjaUtama, KerjaLain, organisasi);
		Log.d("fdesa Response", id_kk + " " + kkid + " " + enumID + " "
				+ namans + " " + NIK + " " + Telp + " " + namaKK + " "
				+ Integer.parseInt(jumlah) + " " + TempatLahir + " "
				+ TanggalLahir + " " + pendidikan + " " + suku + " "
				+ kerjaUtama + " " + KerjaLain + " " + organisasi);

		Intent intent = null;
		if (isUpdate) {
			intent = new Intent(context, list.mquestioner.ListNarasumber.class);
			intent.putExtra("idKK", id_kk);
			intent.putExtra("fputValue", "narasumber");
		} else {
			intent = new Intent(context, SetLahan.class);
			intent.putExtra("kosong", getKosong);
		}
		startActivity(intent);
		// }
	}

	void justSaveData() {
		namans = txtNarasumber.getText().toString();
		NIK = txtNIK.getText().toString();
		Telp = txtTelp.getText().toString();
		namaKK = txtKepala.getText().toString();
		TempatLahir = txtTmpLahir.getText().toString();

		tanggal = txtanggal.getText().toString();
		bulan = txbulan.getText().toString();
		tahun = txtahun.getText().toString();

		if (!tanggal.equals("") && !bulan.equals("") && !tahun.equals("")) {
			TanggalLahir = tanggal + "-" + bulan + "-" + tahun;
		} else {
			TanggalLahir = "00-00-0000";
		}

		KerjaLain = txtKerjaLain.getText().toString();

		switch (rgJkel.getCheckedRadioButtonId()) {
		case R.id.jak1:
			jumlah = "1";
			break;
		case R.id.jak2:
			jumlah = "2";
			break;
		case R.id.jak3:
			jumlah = "3";
			break;
		case R.id.jak4:
			jumlah = "4";
			break;
		case R.id.jak5:
			jumlah = "5";
			break;
		case R.id.lainkali:
			if (!txtSukuLain.getText().toString().isEmpty()) {
				jumlah = txtjakLain.getText().toString();
			} else if (txtSukuLain.getText().toString().isEmpty()) {
				getKosong = "kosong";
				jumlah = "";
			}
			break;
		}

		switch (rgPddk.getCheckedRadioButtonId()) {
		case R.id.rbPendTB:
			pendidikan = "Tidak Bersekolah";
			break;
		case R.id.rbPendSD:
			pendidikan = "SD";
			break;
		case R.id.rbPendSMP:
			pendidikan = "SMP";
			break;
		case R.id.rbPendSMA:
			pendidikan = "SMA";
			break;
		case R.id.rbPendDplm:
			pendidikan = "Lulusan Sarjana dan Sederajat";
			break;
		}

		switch (rgKerjaUtama.getCheckedRadioButtonId()) {
		case R.id.rbKerKebun:
			kerjaUtama = "Pekebun";
			break;
		case R.id.rbKerPNS:
			kerjaUtama = "PNS";
			break;
		case R.id.rbKerWira:
			kerjaUtama = "Wirausaha";
			break;
		case R.id.rbKerLain:
			if (!txtKerUtamaLain.getText().toString().isEmpty()) {
				kerjaUtama = txtKerUtamaLain.getText().toString();
			} else {
				getKosong = "kosong";
				kerjaUtama = " ";
			}
			break;
		}

		switch (rgAnggota.getCheckedRadioButtonId()) {
		case R.id.rbAgtKop:
			organisasi = "Koperasi";
			if (!txtKoperasi.getText().toString().isEmpty()) {
				organisasi = txtKoperasi.getText().toString();
			} else {
				getKosong = "kosong";
				organisasi = " ";
			}
			break;
		case R.id.rbGapoktan:
			if (!txtGapokTan.getText().toString().isEmpty()) {
				organisasi = txtGapokTan.getText().toString();
			} else {
				getKosong = "kosong";
				organisasi = " ";
			}
			break;
		case R.id.rbKopGapok:
			organisasi = "Koperasi dan Gapoktan";
			break;
		case R.id.rbPerkumpulan:
			organisasi = "Perkumpulan atau Asosiasi";
			break;
		case R.id.rbBumdes:
			if (!txtBumdes.getText().toString().isEmpty()) {
				organisasi = txtBumdes.getText().toString();
			} else {
				getKosong = "kosong";
				organisasi = " ";
			}
			break;
		case R.id.rbKelompokTani:
			if (!txtKelompokTani.getText().toString().isEmpty()) {
				organisasi = txtKelompokTani.getText().toString();
			} else {
				getKosong = "kosong";
				organisasi = " ";
			}
			break;
		case R.id.rbAgtLain:
			if (!txtAgtLain.getText().toString().isEmpty()) {
				organisasi = txtAgtLain.getText().toString();
			} else {
				getKosong = "kosong";
				organisasi = " ";
			}
			break;
		}

		switch (rgSK.getCheckedRadioButtonId()) {
		case R.id.rbSukuJw:
			suku = "Jawa";
			break;
		case R.id.rbSukuSnda:
			suku = "Sunda";
			break;
		case R.id.rbSukuMdr:
			suku = "Madura";
			break;
		case R.id.rbSukuMly:
			suku = "Melayu";
			break;
		case R.id.rbSukuDyk:
			suku = "Dayak";
			break;
		case R.id.rbSukuBjr:
			suku = "Banjar";
			break;
		case R.id.rbSukuLain:
			if (!txtSukuLain.getText().toString().isEmpty()) {
				suku = txtSukuLain.getText().toString();
			} else {
				getKosong = "kosong";
				suku = "";
			}
			break;
		}

		db.updateNarasumber(id_kk, enumID, namans, NIK, Telp, namaKK,
				Integer.parseInt(jumlah), TempatLahir, TanggalLahir,
				pendidikan, suku, kerjaUtama, KerjaLain, organisasi);
	}

	@Override
	public void onBackPressed() {
		if (isUpdate) {
			// justSaveData();
			Intent i = new Intent(getApplicationContext(), list.mquestioner.ListNarasumber.class);
			startActivity(i);
		} else {
			id_kk = db.getid();
			db.deleteRow(id_kk);

			Intent i = new Intent(getApplicationContext(), SetDesa.class);
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
		AlertDialog.Builder alert = new AlertDialog.Builder(SetNarasumber.this);
		alert.setTitle(R.string.errorTitle);
		alert.setMessage("Anda Ingin keluar ?");
		alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// justSaveData();
				if (!isUpdate && UpFrom == "normal") {
					db.deleteRow(id_kk);
				}

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
		AlertDialog.Builder alert = new AlertDialog.Builder(SetNarasumber.this);
		alert.setTitle(R.string.errorTitle);
		alert.setMessage("Anda Ingin keluar ?");
		alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// justSaveData();
				if (!isUpdate && UpFrom == "normal") {
					db.deleteRow(id_kk);
				}

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
}