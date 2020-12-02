package list.mquestioner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.inobu.mquestionair.R;
import com.inobu.mquestionair.R.id;
import com.inobu.mquestionair.R.layout;
import com.inobu.mquestionair.R.menu;
import com.inobu.mquestioner.Contact;
import com.inobu.mquestioner.DbHelper;
import com.inobu.mquestioner.JSONParser;

import android.os.AsyncTask;
import android.os.Bundle;
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

public class DetailNarasumber extends Activity {

	DbHelper db = new DbHelper(this);
	private String rowID;
	private int idkk;
	private boolean isUpdate;
	private Button btn_save, btn_lahan, btEditDesa, btEditNarasumber;
	private EditText edit_first, edit_last;
	private DbHelper mHelper;
	private SQLiteDatabase dataBase;
	private String id, fidkk, fenum, fnama, fdesa, fkecamatan, fkabupaten,
			fpovinsi, fNIK, ftelp, fnamakk, fpdesa, fpkecamatan, fpkabupaten,
			fppovinsi, fjml, ftmplahir, ftgllahir, fpddk, fsuku, fpkjutama,
			fpkjlain, fagtorg, desad, putValue, ftglinput, fstatus;
	String asalbibit = null;
	String asalpupuk = null;
	String stlahn = null;
	String jnsppk = null;
	String dtlppk = null;

	Cursor model = null;
	Cursor modellahan = null;
	Cursor hapuslahan = null;
	final Context context = this;

	private TextView anNama, anDesa, anKecamatan, anKabupaten, anProvinsi,
			anNIK, anTelp, anNamaKK, anJml, anTmpLahir, anTglLahir, anPddk,
			anSuku, anPkjUtama, anPkjLain, anAnggOrg;
	JSONParser jsonParser = new JSONParser();
	Contact selectNara;
	private static String url_save_narasumber = "/savekk.php";
	private static String url_save_lahan = "/savelahan.php";
	JSONObject json;
	int cLahan = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		// btEditNarasumber=(Button)findViewById(R.id.btEditNarasumber);

		setUpViews();
		deleteKosong();

		Bundle extras = getIntent().getExtras();
		Log.d("fdesa Response", extras + "");
		if (extras != null) {
			rowID = getIntent().getExtras().getString("idKK");
			idkk = Integer.parseInt(rowID);
			Log.d("idkk Response", idkk + "");
			cLahan = db.getCountLahan(idkk);
			Log.d("cLahan Response", cLahan + "");

			fidkk = getIntent().getExtras().getString("fidkk");
			fenum = getIntent().getExtras().getString("fEnumID");
			fnama = getIntent().getExtras().getString("fNama");
			fdesa = getIntent().getExtras().getString("fDesa");
			fkecamatan = getIntent().getExtras().getString("fKec");
			fkabupaten = getIntent().getExtras().getString("fKab");
			fpovinsi = getIntent().getExtras().getString("fProv");

			fpdesa = getIntent().getExtras().getString("fpDesa");
			fpkecamatan = getIntent().getExtras().getString("fpKec");
			fpkabupaten = getIntent().getExtras().getString("fpKab");
			fppovinsi = getIntent().getExtras().getString("fpProv");

			fNIK = getIntent().getExtras().getString("fNIK");
			ftelp = getIntent().getExtras().getString("fHp");
			fnamakk = getIntent().getExtras().getString("fNamaKK");
			fjml = getIntent().getExtras().getString("fJml");
			ftmplahir = getIntent().getExtras().getString("fTempatLhr");
			ftgllahir = getIntent().getExtras().getString("fTanggalLhr");
			fpddk = getIntent().getExtras().getString("fPendidikan");
			fsuku = getIntent().getExtras().getString("fSuku");
			fpkjutama = getIntent().getExtras().getString("fPekUtama");
			fpkjlain = getIntent().getExtras().getString("fPekLain");
			fagtorg = getIntent().getExtras().getString("fAnggotaOrg");
			ftglinput = getIntent().getExtras().getString("fTglInput");
			fstatus = getIntent().getExtras().getString("fStatus");
			putValue = getIntent().getExtras().getString("fputValue");
			Log.d("fputValue Response", fstatus + "");
		}
		getItem();
	}

	void deleteKosong() {
		int cc = db.getLahanKosong();
		Log.d("cc", cc + "");

		if (cc > 0) {
			for (int i = 1; i <= cc; i++) {
				Log.d("lahan", i + "");
				hapuslahan = db.getAllLahanKosong();
				hapuslahan.moveToPosition(0);

				String idll = db.getidlahan(hapuslahan);
				Log.d(" idll", idll + "");
				db.deleteRowLahan(Integer.parseInt(idll));
			}
		}
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent(getApplicationContext(),
				list.mquestioner.ListNarasumber.class);
		startActivity(i);
	}

	private void Narasumber() {
		Intent intent = new Intent(getApplicationContext(),
				set.mquestioner.SetNarasumber.class);
		intent.putExtra("idKK", rowID);
		intent.putExtra("narasumber", fnama);
		intent.putExtra("nik", fNIK);
		intent.putExtra("telp", ftelp);
		intent.putExtra("namakk", fnamakk);
		intent.putExtra("jml", fjml);
		intent.putExtra("tmplair", ftmplahir);
		intent.putExtra("tgllair", ftgllahir);
		intent.putExtra("pendidik", fpddk);
		intent.putExtra("suku", fsuku);
		intent.putExtra("kjutama", fpkjutama);
		intent.putExtra("kjlain", fpkjlain);
		intent.putExtra("agtorg", fagtorg);
		intent.putExtra("updateList", true);
		startActivity(intent);
	}

	private void Desa() {
		Log.d("move to main", rowID + " " + fpdesa + " " + fpkecamatan + " "
				+ fpkabupaten + " " + fppovinsi);
		Intent intent = new Intent(getApplicationContext(),
				set.mquestioner.SetDesa.class);
		intent.putExtra("idKK", rowID);
		intent.putExtra("desa", fdesa);
		intent.putExtra("keca", fkecamatan);
		intent.putExtra("kabu", fkabupaten);
		intent.putExtra("prov", fpovinsi);

		intent.putExtra("updateList", true);
		intent.putExtra("inputBack", "list");
		startActivity(intent);
	}

	private void setUpViews() {
		anNama = (TextView) findViewById(R.id.txAnsNama);
		anDesa = (TextView) findViewById(R.id.txAnsDesa);
		anKecamatan = (TextView) findViewById(R.id.txAnsKec);
		anKabupaten = (TextView) findViewById(R.id.txAnsKab);
		anProvinsi = (TextView) findViewById(R.id.txAnsProv);
		anNIK = (TextView) findViewById(R.id.txAnsNIK);
		anTelp = (TextView) findViewById(R.id.txAnsTelp);
		anNamaKK = (TextView) findViewById(R.id.txAnsKK);
		anJml = (TextView) findViewById(R.id.txAnsJml);
		anTmpLahir = (TextView) findViewById(R.id.txAnsTmptLahir);
		anTglLahir = (TextView) findViewById(R.id.txAnsTglLarhir);
		anPddk = (TextView) findViewById(R.id.txAnsPddk);
		anSuku = (TextView) findViewById(R.id.txAnsSuku);
		anPkjUtama = (TextView) findViewById(R.id.txAnsPkjUtama);
		anPkjLain = (TextView) findViewById(R.id.txAnsPkjLain);
		anAnggOrg = (TextView) findViewById(R.id.txAnsAnggtOrgn);
	}

	public void getItem() {
		anNama.setText(fnama);
		anDesa.setText(fdesa);
		anKecamatan.setText(fkecamatan);
		anKabupaten.setText(fkabupaten);
		anProvinsi.setText(fpovinsi);
		anNIK.setText(fNIK);
		anTelp.setText(ftelp);
		anNamaKK.setText(fnamakk);
		anJml.setText(fjml);
		anTmpLahir.setText(ftmplahir);
		anTglLahir.setText(ftgllahir);
		anPddk.setText(fpddk);
		anSuku.setText(fsuku);
		anPkjUtama.setText(fpkjutama);
		anPkjLain.setText(fpkjlain);
		anAnggOrg.setText(fagtorg);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.detaildata, menu);
		return true;
	}

	public boolean onPrepareOptionsMenu(Menu menu) {
		Log.d("fputValue Response menu", fstatus + "");
		if (Integer.parseInt(fstatus) == 1) {
			menu.findItem(R.id.kirimdata).setEnabled(false);
			menu.findItem(R.id.editDesa).setEnabled(false);
			menu.findItem(R.id.editNarass).setEnabled(false);
		}
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.kirimdata:
			new sentlahan().execute();
			return true;
		case R.id.listdata:
			startActivity(new Intent(this,
					list.mquestioner.ListNarasumber.class));
			return true;
		case R.id.listlahan:
			toLahan();
			return true;
		case R.id.editDesa:
			Desa();
			return true;
		case R.id.editNarass:
			Narasumber();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void toLahan() {
		Intent intent = new Intent(getApplicationContext(),
				list.mquestioner.ListLahan.class);
		intent.putExtra("idKK", rowID);
		intent.putExtra("status", fstatus);
		startActivity(intent);
	}

	class sentlahan extends AsyncTask<Object, Void, String> {
		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(DetailNarasumber.this);
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
			if (Integer.parseInt(fidkk) < 10) {
				kk = "0" + fidkk + "";
				Log.d("log s ", kk + "");
			} else if (Integer.parseInt(fidkk) >= 10) {
				kk = fidkk + "";
			}

			Log.d("log s", kk + "" + fenum);

			// String idkkn = kk + "" + fenum;

			String enumid = fenum;
			String narasumber = fnama;
			String desa = fdesa;

			String kirimid = fenum + "" + db.getDesaID(desa) + ""
					+ String.valueOf(db.getRandom(99999,00000));

			String kecamatan = fkecamatan;
			String kabupaten = fkabupaten;
			String provinsi = fpovinsi;
			String noKTP = fNIK;
			String noHP = ftelp;
			String namakk = fnamakk;
			String jumlahkk = fjml;
			String tempatlhr = ftmplahir;
			String tanggallhr = ftgllahir;
			String pendidikan = fpddk;
			String suku = fsuku;
			String pekerjaanutama = fpkjutama;
			String pekerjaanlain = fpkjlain;
			String anggotaorg = fagtorg;
			String tglinput = ftglinput;

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
				db.updateStatus(idkk, "1", getTimeOn);
				json = jsonParser.makeHttpRequest(db.getURL()
						+ url_save_narasumber, "POST", nameValuePairs);
				Log.d("Request Ok", json.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}

			Log.d("lahan ", cLahan + "");
			if (cLahan > 0) {
				Log.d("lahan ", cLahan + "");
				for (int i = 1; i <= cLahan; i++) {
					Log.d("lahan 1", cLahan + "");
					Log.d("lahan 11", i - 1 + "");
					modellahan = db.getAllLahan(rowID);
					modellahan.moveToPosition(i - 1);
					String enumidLahan = db.getEnumID(modellahan);
					Log.d("enumid ", enumidLahan + "");
					String shapeid = db.getshape_ID(modellahan);
					Log.d("shapeid ", shapeid + "");
					String statuslahan = db.getstatus_lahan(modellahan);

					String arrStatus[] = statuslahan.split(",");
					if (arrStatus[0].trim().equals("SKT")) {
						Log.d("sett Response", arrStatus[0].toString());
						stlahn = "SKT";
					}

					if (arrStatus[1].trim().equals("SHM")) {
						Log.d("sett Response", arrStatus[1].toString());
						if (stlahn != null) {
							stlahn = stlahn + ",SHM";
						} else {
							stlahn = "SHM";
						}
					}

					if (arrStatus[2].trim().equals("SKTA")) {
						Log.d("sett Response", arrStatus[2].toString());
						if (stlahn != null) {
							stlahn = stlahn + ",SKTA";
						} else {
							stlahn = "SKTA";
						}
					}

					if (!arrStatus[3].equals("SKT")
							&& !arrStatus[3].equals("SHM")
							&& !arrStatus[3].equals("SKTA")
							&& !arrStatus[3].equals("null")) {
						Log.d("sett Response 3", arrStatus[3].toString());
						if (stlahn != null) {
							stlahn = stlahn + "," + arrStatus[3].toString();
						} else {
							stlahn = arrStatus[3].toString();
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
					String asalpupuk = db.getasal_pupuk(modellahan);
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

					if (!arr[3].trim().equals("NPK")
							&& !arr[3].trim().equals("Urea")
							&& !arr[3].trim().equals("Borat")
							&& !arr[3].trim().equals("null")) {
						if (dtlppk != null) {
							dtlppk = dtlppk + "," + arrjmlchk[3] + " "
									+ arrJml[3] + " kg";
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

					List<NameValuePair> paramLahan = new ArrayList<NameValuePair>();
					// paramLahan.add(new BasicNameValuePair("id_kk", kk + ""
					// + fenum));kirimid
					paramLahan.add(new BasicNameValuePair("id_kk", kirimid));
					paramLahan.add(new BasicNameValuePair("enum_id",
							enumidLahan));
					paramLahan.add(new BasicNameValuePair("shape_id", shapeid));
					paramLahan.add(new BasicNameValuePair("status_lahan",
							stlahn));
					paramLahan.add(new BasicNameValuePair("lama_lahan",
							lamalahan));
					paramLahan.add(new BasicNameValuePair("luas_produksi",
							luasprod));
					paramLahan.add(new BasicNameValuePair("luas_nonproduksi",
							luasnonprod));
					paramLahan.add(new BasicNameValuePair("tahun_tanam_prod",
							tahuntanamprod));
					paramLahan.add(new BasicNameValuePair(
							"tahun_tanam_nonprod", tahuntanamnonprod));
					paramLahan.add(new BasicNameValuePair("pernah_panen",
							pernahpanen));
					paramLahan.add(new BasicNameValuePair("siklus_panen",
							sikluspanen));
					paramLahan.add(new BasicNameValuePair(
							"berat_buah_perpanen", beratbuahpanen));
					paramLahan.add(new BasicNameValuePair("prod_perpanen",
							properpanen));
					paramLahan.add(new BasicNameValuePair("asal_bibit",
							asalbibitchk + " " + asalbibit));
					paramLahan.add(new BasicNameValuePair("jenis_bibit",
							jenisbibit));
					paramLahan.add(new BasicNameValuePair("hibrida", hibrida));
					paramLahan.add(new BasicNameValuePair("asal_pupuk",
							asalpupukchk + " " + asalpupuk));
					paramLahan
							.add(new BasicNameValuePair("jenis_pupuk", jnsppk));
					paramLahan.add(new BasicNameValuePair("penggunaan_pupuk",
							dtlppk));
					paramLahan.add(new BasicNameValuePair("campur_pupuk",
							campurpupuk));
					paramLahan.add(new BasicNameValuePair("bahan_pupuk",
							bahanpupuk));
					paramLahan.add(new BasicNameValuePair("tutupan_lahan",
							tutupanlahan));
					paramLahan.add(new BasicNameValuePair("lokasi_jual",
							lokasijualchk + " " + lokasijual));

					try {
						JSONObject jsonlahan = jsonParser.makeHttpRequest(
								db.getURL() + url_save_lahan, "POST",
								paramLahan);
						db.updateStatusLahan(shapeid, "1", getTimeOn);
						Log.d("Mengirim data lahan", shapeid);
						Log.d("Request Ok", "True");
					} catch (Exception e) {
						e.printStackTrace();
					}

					Intent intent = new Intent(context,
							list.mquestioner.ListNarasumber.class);
					startActivity(intent);
				}
			}

			// if(cLahan > 0)
			// {
			// Log.d("lahan ",cLahan+"");
			// for(int i=1; i<=cLahan; i++)
			// {
			// Log.d("lahan 1",cLahan+"");
			// Log.d("lahan 11",i+"");
			// modellahan=db.getAllLahan(rowID);
			// modellahan.moveToPosition(0);
			// String enumidLahan = db.getEnumID(modellahan);
			// Log.d("enumid ",enumidLahan+"");
			// String shapeid = db.getshape_ID(modellahan);
			// Log.d("shapeid ",shapeid+"");
			// String statuslahan = db.getstatus_lahan(modellahan);
			//
			// String arrStatus[] = statuslahan.split(",");
			// if(arrStatus[0].trim().equals("SKT")){
			// Log.d("sett Response",arrStatus[0].toString());
			// stlahn = "SKT";
			// }
			//
			// if(arrStatus[1].trim().equals("SHM")){
			// Log.d("sett Response",arrStatus[1].toString());
			// if(stlahn != null){
			// stlahn = stlahn + ",SHM";
			// }else{
			// stlahn = "SHM";
			// }
			// }
			//
			// if(arrStatus[2].trim().equals("SKTA")){
			// Log.d("sett Response",arrStatus[2].toString());
			// if(stlahn != null){
			// stlahn = stlahn + ",SKTA";
			// }else{
			// stlahn = "SKTA";
			// }
			// }
			//
			// if(!arrStatus[3].equals("SKT") && !arrStatus[3].equals("SHM") &&
			// !arrStatus[3].equals("SKTA") && !arrStatus[3].equals("null")){
			// Log.d("sett Response 3",arrStatus[3].toString());
			// if(stlahn != null){
			// stlahn = stlahn + ","+arrStatus[3].toString();
			// }else{
			// stlahn=arrStatus[3].toString();
			// }
			// }
			//
			// String lamalahan = db.getlama_lahan(modellahan);
			// String luasprod = db.getluas_prod(modellahan);
			// String luasnonprod = db.getluas_nonprod(modellahan);
			// String tahuntanamprod = db.gettahun_tanam(modellahan);
			// String tahuntanamnonprod = db.gettahun_tanamnon(modellahan);
			// String pernahpanen = db.getpernah_panen(modellahan);
			// String sikluspanen = db.getsiklus_panen(modellahan);
			// String beratbuahpanen = db.getberat_buah(modellahan);
			// String properpanen = db.getprod_perpanen(modellahan);
			// String asalbibitchk = db.getasal_bibitchk(modellahan);
			// String asalbibit = db.getasal_bibit(modellahan);
			// String jenisbibit = db.getjenis_bibit(modellahan);
			//
			// String hibrida = db.gethibrida(modellahan);
			// String asalpupukchk = db.getasal_pupukchk(modellahan);
			// String asalpupuk = db.getasal_pupuk(modellahan);
			// String jenispupuk = db.getjenis_pupuk(modellahan);
			// String arr[] = jenispupuk.split(",");
			//
			// String gunapupukchk = db.getpenggunaan_pupukchk(modellahan);
			// String gunapupuk = db.getpenggunaan_pupuk(modellahan);
			//
			// String arrjmlchk[] = gunapupukchk.split(",");
			// String arrJml[] = gunapupuk.split(",");
			//
			// if(arr[0].trim().equals("NPK")){
			// Log.d("sett Response",arr[0].toString());
			// dtlppk = "NPK "+arrJml[0] + " kg";
			// jnsppk = "NPK";
			// }
			//
			// Log.d("arr Response",arr.length+"");
			// if(arr[1].trim().equals("Urea")){
			// if(dtlppk != null){
			// dtlppk = dtlppk + ",NPK "+arrJml[1] +" kg";
			// }else{
			// Log.d("sett Response",arr[1].toString());
			// dtlppk = "Urea "+arrJml[1] +" kg";
			// }
			//
			// if(jnsppk != null){
			// jnsppk = jnsppk + ",Urea";
			// }else{
			// jnsppk ="Urea";
			// }
			// }
			//
			// if(arr[2].trim().equals("Borat")){
			// if(dtlppk != null){
			// dtlppk = dtlppk + ",Borat "+arrJml[2]+" kg";
			// }else{
			// Log.d("sett Response",arr[2].toString());
			// dtlppk = "Urea "+arrJml[2] +" kg";
			// }
			//
			// if(jnsppk != null){
			// jnsppk = jnsppk + ",Borat";
			// }else{
			// jnsppk ="Borat";
			// }
			// }
			//
			// if(!arr[3].trim().equals("NPK") && !arr[3].trim().equals("Urea")
			// && !arr[3].trim().equals("Borat") &&
			// !arr[3].trim().equals("null")){
			// if(dtlppk != null){
			// dtlppk = dtlppk + ","+arrjmlchk[3]+" "+arrJml[3]+" kg";
			// }else{
			// dtlppk = arrjmlchk[3]+" "+arrJml[3] +" kg";
			// }
			//
			// if(jnsppk != null){
			// jnsppk = jnsppk +","+ arrjmlchk[3];
			// }else{
			// jnsppk = arrjmlchk[3];
			// }
			// }
			//
			// String campurpupuk =db.getcampur_pupuk(modellahan);
			// String bahanpupuk = db.getbahan_pupuk(modellahan);
			// String tutupanlahan = db.gettutupan_lahan(modellahan);
			// String lokasijualchk = db.getlokasi_jualchk(modellahan);
			// String lokasijual = db.getlokasi_jual(modellahan);
			//
			// List<NameValuePair> paramLahan = new ArrayList<NameValuePair>();
			// paramLahan.add(new BasicNameValuePair("id_kk", kk+""+fenum));
			// paramLahan.add(new BasicNameValuePair("enum_id", enumidLahan));
			// paramLahan.add(new BasicNameValuePair("shape_id", shapeid));
			// paramLahan.add(new BasicNameValuePair("status_lahan", stlahn));
			// paramLahan.add(new BasicNameValuePair("lama_lahan", lamalahan));
			// paramLahan.add(new BasicNameValuePair("luas_produksi",
			// luasprod));
			// paramLahan.add(new BasicNameValuePair("luas_nonproduksi",
			// luasnonprod));
			// paramLahan.add(new BasicNameValuePair("tahun_tanam_prod",
			// tahuntanamprod));
			// paramLahan.add(new BasicNameValuePair("tahun_tanam_nonprod",
			// tahuntanamnonprod));
			// paramLahan.add(new BasicNameValuePair("pernah_panen",
			// pernahpanen));
			// paramLahan.add(new BasicNameValuePair("siklus_panen",
			// sikluspanen));
			// paramLahan.add(new BasicNameValuePair("berat_buah_perpanen",
			// beratbuahpanen));
			// paramLahan.add(new BasicNameValuePair("prod_perpanen",
			// properpanen));
			// paramLahan.add(new BasicNameValuePair("asal_bibit",
			// asalbibitchk+" "+asalbibit));
			// paramLahan.add(new BasicNameValuePair("jenis_bibit",
			// jenisbibit));
			// paramLahan.add(new BasicNameValuePair("hibrida", hibrida));
			// paramLahan.add(new BasicNameValuePair("asal_pupuk",
			// asalpupukchk+" "+asalpupuk));
			// paramLahan.add(new BasicNameValuePair("jenis_pupuk", jnsppk));
			// paramLahan.add(new BasicNameValuePair("penggunaan_pupuk",
			// dtlppk));
			// paramLahan.add(new BasicNameValuePair("campur_pupuk",
			// campurpupuk));
			// paramLahan.add(new BasicNameValuePair("bahan_pupuk",
			// bahanpupuk));
			// paramLahan.add(new BasicNameValuePair("tutupan_lahan",
			// tutupanlahan));
			// paramLahan.add(new BasicNameValuePair("lokasi_jual",
			// lokasijualchk+" "+lokasijual));
			//
			// try {
			// JSONObject jsonlahan =
			// jsonParser.makeHttpRequest(db.getURL()+url_save_lahan,
			// "POST", paramLahan);
			// db.updateStatus(idkk, "1", getTimeOn);
			// db.updateStatusLahan(idkk, "1",getTimeOn);
			// Log.d("Mengirim data lahan", jsonlahan.toString());
			// Log.d("Request Ok", "True");
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			//
			// Intent intent = new Intent(context, ViewData.class);
			// startActivity(intent);
			// }
			// }

			return null;
		}
	}
}
