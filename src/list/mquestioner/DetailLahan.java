package list.mquestioner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.inobu.mquestionair.R;
import com.inobu.mquestionair.R.id;
import com.inobu.mquestionair.R.layout;
import com.inobu.mquestionair.R.menu;
import com.inobu.mquestioner.Contact;
import com.inobu.mquestioner.DbHelper;
import com.inobu.mquestioner.HalamanUtama;
import com.inobu.mquestioner.JSONParser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Contacts;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import android.widget.Spinner;

public class DetailLahan extends Activity {

	final Context context = this;
	DbHelper db = new DbHelper(this);
	private String rowID;
	// private long rowID;
	private boolean isUpdate;
	private Button btEditLahan, btEditBibit, btEditPupuk;
	private EditText edit_first, edit_last, anshapeid;
	private DbHelper mHelper;
	private SQLiteDatabase dataBase;
	private String id, fenum, fshape, fstatusLhn, flamaLhn, fluasProd,
			fluasNonProd, fThnPRod, fThnNonProd, fSudahPanen, fPanenBulan,
			fProdTaun, fKiraBrt, fstatus, fstatuslah, fAsalBibitchk,
			fAsalBibit, fJnsBibit, fSert, fAsalPpkchk, fAsalPpk, fJnsPpk,
			fJmlPpkchk, fJmlPpk, fCampur, fYaCampur, fTutupanLhn, fJualTBSchk,
			fJualTBS;

	private TextView anStLahan, anLamaLahan, anLuasProd, anLuasNonProd,
			anThnProd, anThnNonProd, anSdhPanen, anPanenBln, anKiraBrt,
			anProdPerTaun, anAsalBibit, anJnsBibit, anSertfkt, anAsalPpk,
			anJnsPpk, anJmlPpk, anCampur, anYaCampur, anTutupan, anJualchk,
			anJual;
	String statuslahan = "";
	String detailpupuk = null;
	String jenispupuk = null;
	Contact selectNara;
	Cursor dellahan = null;
	Cursor model = null;

	JSONParser jsonParser = new JSONParser();
	JSONObject json;
	private static String url_save_narasumber = "/savekk.php";
	private static String url_save_lahan = "/savelahan.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_lahan);

		setUpViews();
		Log.d("bibit save", "disini");
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			rowID = extras.getString("rowID");
			fstatus = getIntent().getExtras().getString("status");
			Log.d("fstatus status ss: ", fstatus + "");
			fenum = getIntent().getExtras().getString("fenumid");
			fshape = getIntent().getExtras().getString("fshapeid");
			fstatusLhn = getIntent().getExtras().getString("fstatuslahan");
			flamaLhn = getIntent().getExtras().getString("flamalahan");
			fluasProd = getIntent().getExtras().getString("fluasprod");
			fluasNonProd = getIntent().getExtras().getString("fluasnonprod");
			fThnPRod = getIntent().getExtras().getString("ftahuntanamprod");
			fThnNonProd = getIntent().getExtras().getString(
					"ftahuntanamnonprod");
			fSudahPanen = getIntent().getExtras().getString("fpernahpanen");
			fPanenBulan = getIntent().getExtras().getString("fsikluspanen");
			fKiraBrt = getIntent().getExtras().getString("fberatbuahpanen");
			fProdTaun = getIntent().getExtras().getString("fProdperTaun");
			fAsalBibit = getIntent().getExtras().getString("fasalbibit");
			fAsalBibitchk = getIntent().getExtras().getString("fasalbibitchk");
			fJnsBibit = getIntent().getExtras().getString("fjenisbibit");
			fSert = getIntent().getExtras().getString("fhibrida");
			fAsalPpkchk = getIntent().getExtras().getString("fasalpupukchk");
			fAsalPpk = getIntent().getExtras().getString("fasalpupuk");
			fJnsPpk = getIntent().getExtras().getString("fjenispupuk");

			fJmlPpkchk = getIntent().getExtras().getString("fgunapupukchk");
			fJmlPpk = getIntent().getExtras().getString("fgunapupuk");
			fstatuslah = getIntent().getExtras().getString("fstatuslah");

			String arrStatus[] = fstatusLhn.split(",");
			String arr[] = fJnsPpk.split(",");
			String arrjmlchk[] = fJmlPpkchk.split(",");
			String arrJml[] = fJmlPpk.split(",");

			Log.d("setResponse fstatusLhn", fstatusLhn);
			Log.d("setResponse fJnsPpk", fJnsPpk);
			Log.d("setResponse fJmlPpkchk", fJmlPpkchk);
			Log.d("setResponse fJmlPpk", fJmlPpk);

			if (arrStatus[0].trim().equals("SKT")) {
				Log.d("sett Response", arrStatus[0].toString());
				statuslahan = "SKT";
			}

			if (arrStatus[1].trim().equals("SHM")) {
				Log.d("sett Response", arrStatus[1].toString());
				if (statuslahan != null & !statuslahan.equals("")) {
					statuslahan = statuslahan + ", SHM";
				} else {
					statuslahan = "SHM";
				}
			}

			if (arrStatus[2].trim().equals("SKTA")) {
				Log.d("sett Response", arrStatus[2].toString());
				if (statuslahan != null & !statuslahan.equals("")) {
					statuslahan = statuslahan + ", SKTA";
				} else {
					statuslahan = "SKTA";
				}
			}

			if (!arrStatus[3].equals("SKT") && !arrStatus[3].equals("SHM")
					&& !arrStatus[3].equals("SKTA")
					&& !arrStatus[3].equals("null")) {
				Log.d("sett Response 3", arrStatus[3].toString());
				if (statuslahan != null & !statuslahan.equals("")) {
					statuslahan = statuslahan + ", " + arrStatus[3].toString();
				} else {
					statuslahan = arrStatus[3].toString();
				}
			}

			Log.d("arr Response", arr.length + "");
			if (arr[0].trim().equals("NPK")) {
				Log.d("sett Response", arr[0].toString());
				detailpupuk = "NPK " + arrJml[0] + " kg";
				jenispupuk = "NPK";
			}

			if (arr[1].trim().equals("Urea")) {
				if (detailpupuk != null) {
					detailpupuk = detailpupuk + ",Urea " + arrJml[1] + " kg";
				} else {
					Log.d("sett Response", arr[1].toString());
					detailpupuk = "Urea " + arrJml[1] + " kg";
				}

				if (jenispupuk != null) {
					jenispupuk = jenispupuk + ",Urea";
				} else {
					jenispupuk = "Urea";
				}
			}

			if (arr[2].trim().equals("Borat")) {
				if (detailpupuk != null) {
					detailpupuk = detailpupuk + ",Borat " + arrJml[2] + " kg";
				} else {
					Log.d("sett Response", arr[2].toString());
					detailpupuk = "Borat " + arrJml[2] + " kg";
				}

				if (jenispupuk != null) {
					jenispupuk = jenispupuk + ",Borat";
				} else {
					jenispupuk = "Borat";
				}
			}

			if (!arr[3].trim().equals("NPK") && !arr[3].trim().equals("Urea")
					&& !arr[3].trim().equals("Borat")
					&& !arr[3].trim().equals("null")) {
				if (detailpupuk != null) {
					detailpupuk = detailpupuk + "," + arr[3] + " " + arrJml[3]
							+ " kg";
				} else {
					detailpupuk = arr[3] + " " + arrJml[3] + " kg";
				}

				if (jenispupuk != null) {
					jenispupuk = jenispupuk + "," + arr[3];
				} else {
					jenispupuk = arr[3];
				}
			}

			fCampur = getIntent().getExtras().getString("fcampurpupuk");
			fYaCampur = getIntent().getExtras().getString("fbahanpupuk");
			fTutupanLhn = getIntent().getExtras().getString("ftutupanlahan");
			fJualTBSchk = getIntent().getExtras().getString("flokasijualchk");
			fJualTBS = getIntent().getExtras().getString("flokasijual");
			Log.d("Detail Lahan Response", detailpupuk);
		}
		getItem();
	}

	private void setUpViews() {
		anshapeid = (EditText) findViewById(R.id.shapesIDs);
		anStLahan = (TextView) findViewById(R.id.txAnsSttsLahan);
		anLamaLahan = (TextView) findViewById(R.id.txAnsLamaLhn);
		anLuasProd = (TextView) findViewById(R.id.txAnsLuasProd);
		anLuasNonProd = (TextView) findViewById(R.id.txAnsLuasNonProd);
		anThnProd = (TextView) findViewById(R.id.txAnsThnTanamProd);
		anThnNonProd = (TextView) findViewById(R.id.txAnsThnTanamNonProd);
		anSdhPanen = (TextView) findViewById(R.id.txAnsSdhPanen);
		anPanenBln = (TextView) findViewById(R.id.txAnsPanenPerBulan);
		anKiraBrt = (TextView) findViewById(R.id.txAnsKiraBerat);
		anProdPerTaun = (TextView) findViewById(R.id.txAnsProdperThn);
		anAsalBibit = (TextView) findViewById(R.id.txAnsAsalBibit);
		anJnsBibit = (TextView) findViewById(R.id.txAnsJenisBibit);
		anSertfkt = (TextView) findViewById(R.id.txAnsSrtfikat);
		anAsalPpk = (TextView) findViewById(R.id.txAnsAsalPpk);
		anJnsPpk = (TextView) findViewById(R.id.txAnsJnsPpk);
		anJmlPpk = (TextView) findViewById(R.id.txAnsJmlPupuk);
		anCampur = (TextView) findViewById(R.id.txAnsCampur);
		anYaCampur = (TextView) findViewById(R.id.txAnsyaCampur);
		anTutupan = (TextView) findViewById(R.id.txAnsTutupanLahan);
		anJual = (TextView) findViewById(R.id.txAnsJualTBS);
	}

	public void getItem() {
		anshapeid.setText(fshape);
		anStLahan.setText(statuslahan);
		anLamaLahan.setText(flamaLhn);
		anLuasProd.setText(fluasProd);
		anLuasNonProd.setText(fluasNonProd);
		anThnProd.setText(fThnPRod);
		anThnNonProd.setText(fThnNonProd);

		Log.d("pernah pane", fSudahPanen);
		if (fSudahPanen.equals("1")) {
			anSdhPanen.setText("Ya");
		} else if (fSudahPanen.equals("0")) {
			anSdhPanen.setText("Tidak");
		}

		anPanenBln.setText(fPanenBulan);
		anKiraBrt.setText(fKiraBrt);
		anProdPerTaun.setText(fProdTaun);
		anAsalBibit.setText(fAsalBibitchk + " " + fAsalBibit);
		anJnsBibit.setText(fJnsBibit);
		anSertfkt.setText(fSert);
		anAsalPpk.setText(fAsalPpkchk + " " + fAsalPpk);
		anJnsPpk.setText(jenispupuk);
		anJmlPpk.setText(detailpupuk);

		if (fCampur.equals("1")) {
			anCampur.setText("Ya");
		} else if (fCampur.equals("0")) {
			anCampur.setText("Tidak");
		}

		anYaCampur.setText(fYaCampur);
		anTutupan.setText(fTutupanLhn);
		anJual.setText(fJualTBSchk + " " + fJualTBS);

		Log.d("load semua", " oke");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.detaillahan, menu);
		return true;
	}

	public boolean onPrepareOptionsMenu(Menu menu) {
		Log.d("fputValue Response menu", fstatuslah + "");
		if (Integer.parseInt(fstatuslah) == 1) {
			menu.findItem(R.id.kirimLahan).setEnabled(false);
			menu.findItem(R.id.editlahan).setEnabled(false);
			menu.findItem(R.id.editBibit).setEnabled(false);
			menu.findItem(R.id.editpupuk).setEnabled(false);
		}
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.kirimLahan:
			new sentlahan().execute();
			return true;
		case R.id.editlahan:
			toLahan();
			return true;
		case R.id.editBibit:
			toBibit();
			return true;
		case R.id.editpupuk:
			toPupuk();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void toLahan() {
		Log.d("Detail to Lahan", "detail");
		Intent intent = new Intent(context, set.mquestioner.SetLahan.class);
		intent.putExtra("rowID", rowID);
		intent.putExtra("status", fstatus);
		Log.d("isUpdate Response detail lahan", rowID + "");
		intent.putExtra("shapeid", fshape);
		intent.putExtra("statuslhn", fstatusLhn);
		intent.putExtra("lamalhn", flamaLhn);
		intent.putExtra("luasp", fluasProd);
		intent.putExtra("luasnp", fluasNonProd);
		intent.putExtra("taunp", fThnPRod);
		intent.putExtra("tanunp", fThnNonProd);
		intent.putExtra("sudahp", fSudahPanen);
		intent.putExtra("pbulan", fPanenBulan);
		intent.putExtra("kirab", fKiraBrt);
		intent.putExtra("prodtaun", fProdTaun);
		intent.putExtra("tutuplahan", fTutupanLhn);
		intent.putExtra("jualtbschk", fJualTBSchk);
		intent.putExtra("jualtbs", fJualTBS);
		intent.putExtra("updateList", true);
		startActivity(intent);
	}

	private void toBibit() {
		Log.d("Detail to bibit", "bibit");
		Intent intent = new Intent(DetailLahan.this, set.mquestioner.SetBibit.class);
		intent.putExtra("rowID", rowID);
		intent.putExtra("status", fstatus);
		intent.putExtra("shapeid", fshape);
		intent.putExtra("asalbbtchk", fAsalBibitchk);
		intent.putExtra("asalbbt", fAsalBibit);
		intent.putExtra("jenisbbt", fJnsBibit);
		intent.putExtra("sertf", fSert);
		intent.putExtra("updateList", true);
		startActivity(intent);
	}

	private void toPupuk() {
		Log.d("Detail to pupuk", "pupuk");
		Intent intent = new Intent(context, set.mquestioner.SetPupuk.class);
		intent.putExtra("rowID", rowID);
		intent.putExtra("status", fstatus);
		intent.putExtra("shapeid", fshape);
		intent.putExtra("asalppkchk", fAsalPpkchk);
		intent.putExtra("asalppk", fAsalPpk);
		intent.putExtra("jenisppk", fJnsPpk);
		intent.putExtra("jumlahppkchk", fJmlPpkchk);
		intent.putExtra("jumlahppk", fJmlPpk);
		intent.putExtra("campur", fCampur);
		intent.putExtra("dicampur", fYaCampur);
		intent.putExtra("updateList", true);
		startActivity(intent);
	}

	class sentlahan extends AsyncTask<Object, Void, String> {
		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(DetailLahan.this);
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
			if (Integer.parseInt(rowID) < 10) {
				kk = "0" + rowID + "";
				Log.d("log s ", kk + "");
			} else if (Integer.parseInt(rowID) >= 10) {
				kk = rowID + "";
			}

			Log.d("log s", kk + "" + fenum);

			model = db.getAllKK(Integer.parseInt(rowID));
			model.moveToPosition(0);

			String status = db.getStatus(model);

			if (Integer.parseInt(status) == 0) {
				Log.d("log status", status + "");
				String idkkn = db.getIdKK(model);
				String enumid = db.getEnumIDnara(model);
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

				Log.d("log idkkn ", idkkn);
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("id_kk", kk + ""
						+ fenum));
				nameValuePairs.add(new BasicNameValuePair("enum_id", enumid));
				nameValuePairs.add(new BasicNameValuePair("narasumber",
						narasumber));
				nameValuePairs.add(new BasicNameValuePair("desa", desa));
				nameValuePairs.add(new BasicNameValuePair("kecamatan",
						kecamatan));
				nameValuePairs.add(new BasicNameValuePair("kabupaten",
						kabupaten));
				nameValuePairs
						.add(new BasicNameValuePair("propinsi", provinsi));
				nameValuePairs.add(new BasicNameValuePair("no_KTP", noKTP));
				nameValuePairs.add(new BasicNameValuePair("no_HP", noHP));
				nameValuePairs.add(new BasicNameValuePair("nama_kk", namakk));
				nameValuePairs
						.add(new BasicNameValuePair("jumlah_kk", jumlahkk));
				nameValuePairs.add(new BasicNameValuePair("tempat_lhr",
						tempatlhr));
				nameValuePairs.add(new BasicNameValuePair("tanggal_lhr",
						tanggallhr));
				nameValuePairs.add(new BasicNameValuePair("pendidikan",
						pendidikan));
				nameValuePairs.add(new BasicNameValuePair("suku", suku));
				nameValuePairs.add(new BasicNameValuePair("pekerjaan_utama",
						pekerjaanutama));
				nameValuePairs.add(new BasicNameValuePair("pekerjaan_lain",
						pekerjaanlain));
				nameValuePairs.add(new BasicNameValuePair("anggota_organisasi",
						anggotaorg));
				nameValuePairs
						.add(new BasicNameValuePair("tgl_input", tglinput));
				nameValuePairs.add(new BasicNameValuePair("status", "1"));

				String getTimeOn = cal.getTime().toLocaleString();
				nameValuePairs
						.add(new BasicNameValuePair("sent_on", getTimeOn));

				try {
					db.updateStatus(Integer.parseInt(rowID), "1", getTimeOn);
					json = jsonParser.makeHttpRequest(db.getURL()
							+ url_save_narasumber, "POST", nameValuePairs);
					Log.d("Request Ok", json.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			int cLahan = db.getidLahannya(Integer.parseInt(rowID));
			Log.d("lahan ", cLahan + "");
			dellahan = db.getAlltoLhn(rowID, fshape);
			dellahan.moveToPosition(0);
			String enumidLahan = db.getEnumID(dellahan);
			Log.d("enumid ", enumidLahan + "");
			String shapeid = db.getshape_ID(dellahan);
			Log.d("shapeid ", shapeid + "");
			// String statuslahan = db.getstatus_lahan(dellahan);
			String lamalahan = db.getlama_lahan(dellahan);
			String luasprod = db.getluas_prod(dellahan);
			String luasnonprod = db.getluas_nonprod(dellahan);
			String tahuntanamprod = db.gettahun_tanam(dellahan);
			String tahuntanamnonprod = db.gettahun_tanamnon(dellahan);
			String pernahpanen = db.getpernah_panen(dellahan);
			String sikluspanen = db.getsiklus_panen(dellahan);
			String beratbuahpanen = db.getberat_buah(dellahan);
			String properpanen = db.getprod_perpanen(dellahan);
			String asalbibitchk = db.getasal_bibitchk(dellahan);
			String asalbibit = db.getasal_bibit(dellahan);
			String jenisbibit = db.getjenis_bibit(dellahan);
			String hibrida = db.gethibrida(dellahan);
			String asalpupukchk = db.getasal_pupukchk(dellahan);
			String asalpupuk = db.getasal_pupuk(dellahan);
			// String jenispupuk = db.getjenis_pupuk(dellahan);
			// String gunapupukchk = db.getpenggunaan_pupukchk(dellahan);
			// String gunapupuk = db.getpenggunaan_pupuk(dellahan);
			String campurpupuk = db.getcampur_pupuk(dellahan);
			String bahanpupuk = db.getbahan_pupuk(dellahan);
			String tutupanlahan = db.gettutupan_lahan(dellahan);
			String lokasijualchk = db.getlokasi_jualchk(dellahan);
			String lokasijual = db.getlokasi_jual(dellahan);

			Log.d("lahan sent", statuslahan + ";" + lamalahan + ";" + luasprod
					+ ";" + luasnonprod + ";" + tahuntanamprod + ";"
					+ tahuntanamnonprod + ";" + pernahpanen + ";" + sikluspanen
					+ ";" + beratbuahpanen + ";" + properpanen + ";"
					+ asalbibitchk + ";" + asalbibit + ";" + jenisbibit + ";"
					+ hibrida + ";" + asalpupukchk + ";" + asalpupuk + ";"
					+ jenispupuk + ";" + jenispupuk + ";" + detailpupuk + ";"
					+ campurpupuk + ";" + bahanpupuk + ";" + tutupanlahan + ";"
					+ lokasijualchk + ";" + lokasijual + ";");

			List<NameValuePair> paramLahan = new ArrayList<NameValuePair>();
			paramLahan.add(new BasicNameValuePair("id_kk", kk + "" + fenum));
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
			paramLahan.add(new BasicNameValuePair("jenis_pupuk", jenispupuk));
			paramLahan.add(new BasicNameValuePair("penggunaan_pupuk",
					detailpupuk));
			paramLahan.add(new BasicNameValuePair("campur_pupuk", campurpupuk));
			paramLahan.add(new BasicNameValuePair("bahan_pupuk", bahanpupuk));
			paramLahan
					.add(new BasicNameValuePair("tutupan_lahan", tutupanlahan));
			paramLahan.add(new BasicNameValuePair("lokasi_jual", lokasijualchk
					+ " " + lokasijual));

			String getTimeOn = cal.getTime().toLocaleString();

			try {
				JSONObject jsonlahan = jsonParser.makeHttpRequest(db.getURL()
						+ url_save_lahan, "POST", paramLahan);
				// db.updateStatus(id_kk, "1",getTimeOn);
				db.updateStatusLahan(shapeid, "1", getTimeOn);
				Log.d("Mengirim data lahan", jsonlahan.toString());
				Log.d("Request Ok", "True");
			} catch (Exception e) {
				e.printStackTrace();
			}

			Intent intent = new Intent(context, HalamanUtama.class);
			startActivity(intent);

			return null;
		}
	}
}
