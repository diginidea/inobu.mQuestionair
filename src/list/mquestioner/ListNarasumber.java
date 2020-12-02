package list.mquestioner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListNarasumber extends Activity {

	public static final String ROW_ID = "id_kk";
	com.inobu.mquestioner.DbHelper db;
	Cursor model = null;

	SQLiteDatabase dataBase;
	// Cursor model=null;
	int listcounter = 0;

	final Context context = this;
	private ArrayList<String> userId = new ArrayList<String>();
	private ArrayList<String> get_Nama = new ArrayList<String>();
	private ArrayList<String> get_NIK = new ArrayList<String>();

	String idkk, enumid, nama, pdesa, desa, pkecamatan, kecamatan, pkabupaten,
			kabupaten, pprovinsi, provinsi, ktp, hp, namakk, jumlahkk,
			tempatlhr, tanggallhr, pendidikan, suku, pekerajanutama,
			pekerjaanlain, anggoaorg, tglinput, status;

	private ListView userList;
	private AlertDialog.Builder build;
	// private String[] ns_array;
	DisplayAdapter disadpt;

	private static final String KEY_STATUS = "status";
	private static final String KEY_CHECKON = "sent_on";

	String stlahn = null;
	String jnsppk = null;
	String dtlppk = null;
	int cLahan = 0;
	JSONObject json;
	com.inobu.mquestioner.JSONParser jsonParser = new com.inobu.mquestioner.JSONParser();

	Cursor modellahan = null;

	private static String url_save_narasumber = "/savekk.php";
	private static String url_save_lahan = "/savelahan.php";

	SparseBooleanArray mCheckStates;
	// ArrayList<String> data = new ArrayList<String>();
	ArrayAdapter<String> adapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		// ns_array = getResources().getStringArray(R.array.menu);
		// Arrays.sort(ns_array);

		userList = (ListView) findViewById(R.id.ListNaraSumber);
		db = new com.inobu.mquestioner.DbHelper(this);
		// int len = userList.getCount();

		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		// R.layout.row, ns_array);
		// userList.setAdapter(adapter);
		registerForContextMenu(userList);

		// add new record
		findViewById(R.id.btnAdd).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						set.mquestioner.SetDesa.class);
				startActivity(i);
			}
		});

		findViewById(R.id.btnDelete).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				StringBuilder result = new StringBuilder();
				for (int i = 0; i < disadpt.mCheckStates.size(); i++) {
					if (disadpt.mCheckStates.get(i) == true) {

						result.append(" ok ");
						result.append("\n");
					}

				}
				Toast.makeText(ListNarasumber.this, result, 1000).show();
			}
		});

		// click to update data
		userList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				model = db.getAll();
				model.moveToPosition(arg2);
				idkk = db.getIdKK(model);
				enumid = db.getEnumIDnara(model);
				nama = db.getNama(model);
				desa = db.getDesa(model);
				kecamatan = db.getKec(model);
				kabupaten = db.getKab(model);
				provinsi = db.getProv(model);
				ktp = db.getKTP(model);
				hp = db.getHP(model);
				namakk = db.getnamaKK(model);
				jumlahkk = db.getJmlKK(model);
				tempatlhr = db.getTempatLhr(model);
				tanggallhr = db.gettanggalLhr(model);
				pendidikan = db.getpendidikan(model);
				suku = db.getsuku(model);
				pekerajanutama = db.getpekerjaanutama(model);
				pekerjaanlain = db.getpekerjaanlain(model);
				anggoaorg = db.getagt_org(model);
				tglinput = db.gettglinput(model);
				status = db.getStatus(model);

				Intent i = new Intent(getApplicationContext(),
						DetailNarasumber.class);
				i.putExtra("fidkk", idkk);
				i.putExtra("fEnumID", enumid);
				i.putExtra("fNama", nama);
				i.putExtra("fDesa", desa);
				i.putExtra("fpDesa", pdesa);
				i.putExtra("fKec", kecamatan);
				i.putExtra("fpKec", pkecamatan);
				i.putExtra("fKab", kabupaten);
				i.putExtra("fpKab", pkabupaten);
				i.putExtra("fProv", provinsi);
				i.putExtra("fpProv", pprovinsi);
				i.putExtra("fNIK", ktp);
				i.putExtra("fHp", hp);
				i.putExtra("fNamaKK", namakk);
				i.putExtra("fJml", jumlahkk);
				i.putExtra("fTempatLhr", tempatlhr);
				i.putExtra("fTanggalLhr", tanggallhr);
				i.putExtra("fPendidikan", pendidikan);
				i.putExtra("fSuku", suku);
				i.putExtra("fPekUtama", pekerajanutama);
				i.putExtra("fPekLain", pekerjaanlain);
				i.putExtra("fAnggotaOrg", anggoaorg);
				i.putExtra("fTglInput", tglinput);
				i.putExtra("fStatus", status);
				i.putExtra("idKK", userId.get(arg2));

				Log.d("rowID Response", userId.get(arg2));
				startActivity(i);

			}
		});
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.contextmenu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		int index = info.position;

		switch (item.getItemId()) {
		case R.id.kirimdata:
			new sentlahan().execute();
			return true;
		case R.id.hapusdata:

			dataBase.delete("tb_kk", "id_kk" + "=" + userId.get(index), null);
			dataBase.delete("tb_lahan", "id_kk" + "=" + userId.get(index), null);

			Toast.makeText(
					getApplicationContext(),
					get_Nama.get(index) + " " + get_NIK.get(index)
							+ " is deleted.", 3000).show();

			displayData();
			return true;
		case R.id.listlahan:
			toLahan(index);
			return true;
		case R.id.editDesa:
			Desa(index);
			return true;
		case R.id.editNarasumber:
			Narasumber(index);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void Desa(int ind) {
		model = db.getAll();
		model.moveToPosition(ind);
		desa = db.getDesa(model);
		kecamatan = db.getKec(model);
		kabupaten = db.getKab(model);
		provinsi = db.getProv(model);

		// Log.d("move to main", rowID + " " + fpdesa + " " + fpkecamatan + " "
		// + fpkabupaten + " " + fppovinsi);
		Intent intent = new Intent(getApplicationContext(),
				set.mquestioner.SetDesa.class);
		intent.putExtra("idKK", userId.get(ind));
		intent.putExtra("desa", desa);
		intent.putExtra("keca", kecamatan);
		intent.putExtra("kabu", kabupaten);
		intent.putExtra("prov", provinsi);

		intent.putExtra("updateList", true);
		intent.putExtra("inputBack", "list");
		startActivity(intent);
	}

	private void Narasumber(int ind) {
		model = db.getAll();
		model.moveToPosition(ind);

		desa = db.getDesa(model);
		nama = db.getNama(model);
		ktp = db.getKTP(model);
		hp = db.getHP(model);
		namakk = db.getnamaKK(model);
		jumlahkk = db.getJmlKK(model);
		tempatlhr = db.getTempatLhr(model);
		tanggallhr = db.gettanggalLhr(model);
		pendidikan = db.getpendidikan(model);
		suku = db.getsuku(model);
		pekerajanutama = db.getpekerjaanutama(model);
		pekerjaanlain = db.getpekerjaanlain(model);
		anggoaorg = db.getagt_org(model);

		Intent intent = new Intent(getApplicationContext(),
				set.mquestioner.SetNarasumber.class);
		intent.putExtra("idKK", userId.get(ind));
		intent.putExtra("narasumber", nama);
		intent.putExtra("nik", ktp);
		intent.putExtra("telp", hp);
		intent.putExtra("namakk", namakk);
		intent.putExtra("jml", jumlahkk);
		intent.putExtra("tmplair", tempatlhr);
		intent.putExtra("tgllair", tanggallhr);
		intent.putExtra("pendidik", pendidikan);
		intent.putExtra("suku", suku);
		intent.putExtra("kjutama", pekerajanutama);
		intent.putExtra("kjlain", pekerjaanlain);
		intent.putExtra("agtorg", anggoaorg);
		intent.putExtra("updateList", true);
		startActivity(intent);
	}

	private void toLahan(int ind) {
		model = db.getAll();
		model.moveToPosition(ind);
		status = db.getStatus(model);
		idkk = db.getIdKK(model);

		Log.d("data ke lahan", userId.get(ind) + ", " + idkk + ", " + status
				+ " to lahan");

		Intent intent = new Intent(getApplicationContext(), ListLahan.class);

		intent.putExtra("idKK", userId.get(ind));
		intent.putExtra("status", status);
		startActivity(intent);
	}

	class sentlahan extends AsyncTask<Object, Void, String> {
		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ListNarasumber.this);
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

			// String kk = "";
			// if (Integer.parseInt(idkk) < 10) {
			// kk = "0" + idkk + "";
			// Log.d("log s ", kk + "");
			// } else if (Integer.parseInt(idkk) >= 10) {
			// kk = idkk + "";
			// }
			//
			// Log.d("log s", kk + "" + enumid);

			// String idkkn = kk + "" + enumid;
			String kirimid = enumid + "" + db.getDesaID(desa) + ""
					+ String.valueOf(db.getRandom(99999,00000));
			
			Log.d("log idkkn ", kirimid);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("id_kk", kirimid));
			nameValuePairs.add(new BasicNameValuePair("enum_id", enumid));
			nameValuePairs.add(new BasicNameValuePair("narasumber", nama));
			nameValuePairs.add(new BasicNameValuePair("desa", desa));
			nameValuePairs.add(new BasicNameValuePair("kecamatan", kecamatan));
			nameValuePairs.add(new BasicNameValuePair("kabupaten", kabupaten));
			nameValuePairs.add(new BasicNameValuePair("propinsi", provinsi));
			nameValuePairs.add(new BasicNameValuePair("no_KTP", ktp));
			nameValuePairs.add(new BasicNameValuePair("no_HP", hp));
			nameValuePairs.add(new BasicNameValuePair("nama_kk", namakk));
			nameValuePairs.add(new BasicNameValuePair("jumlah_kk", jumlahkk));
			nameValuePairs.add(new BasicNameValuePair("tempat_lhr", tempatlhr));
			nameValuePairs
					.add(new BasicNameValuePair("tanggal_lhr", tanggallhr));
			nameValuePairs
					.add(new BasicNameValuePair("pendidikan", pendidikan));
			nameValuePairs.add(new BasicNameValuePair("suku", suku));
			nameValuePairs.add(new BasicNameValuePair("pekerjaan_utama",
					pekerajanutama));
			nameValuePairs.add(new BasicNameValuePair("pekerjaan_lain",
					pekerjaanlain));
			nameValuePairs.add(new BasicNameValuePair("anggota_organisasi",
					anggoaorg));
			nameValuePairs.add(new BasicNameValuePair("tgl_input", tglinput));
			nameValuePairs.add(new BasicNameValuePair("status", "1"));

			String getTimeOn = cal.getTime().toLocaleString();
			nameValuePairs.add(new BasicNameValuePair("sent_on", getTimeOn));

			try {
				db.updateStatus(Integer.parseInt(idkk), "1", getTimeOn);
				json = jsonParser.makeHttpRequest(db.getURL()
						+ url_save_narasumber, "POST", nameValuePairs);
				Log.d("Request Ok", json.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Log.d("lahan ", cLahan + "");
			if (cLahan > 0) {
				Log.d("lahan ", cLahan + "");
				for (int i = 1; i <= cLahan; i++) {
					Log.d("lahan 1", cLahan + "");
					Log.d("lahan 11", i - 1 + "");
					modellahan = db.getAllLahan(idkk);
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
					// + enumid));kirimid
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

					Intent intent = new Intent(context, ListNarasumber.class);
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

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(context,
				com.inobu.mquestioner.HalamanUtama.class);
		startActivity(intent);
	}

	@Override
	protected void onResume() {
		displayData();
		super.onResume();
	}

	private void displayData() {
		dataBase = db.getWritableDatabase();
		Cursor mCursor = dataBase.rawQuery("SELECT * FROM tb_kk", null);

		userId.clear();
		get_Nama.clear();
		get_NIK.clear();
		if (mCursor.moveToFirst()) {
			do {
				userId.add(mCursor.getString(mCursor.getColumnIndex("id_kk")));
				get_Nama.add(mCursor.getString(mCursor
						.getColumnIndex("narasumber")));
				get_NIK.add(mCursor.getString(mCursor.getColumnIndex("no_KTP")));

			} while (mCursor.moveToNext());
		}
		disadpt = new DisplayAdapter(ListNarasumber.this, userId, get_Nama,
				get_NIK);
		userList.setAdapter(disadpt);
		// userList.setChoiceMode(userList.CHOICE_MODE_MULTIPLE);
		mCursor.close();
	}
}
