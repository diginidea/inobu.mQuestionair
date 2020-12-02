package list.mquestioner;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class ListLahan extends Activity {
	public static final String ROW_ID = "id_kk";
	com.inobu.mquestioner.DbHelper db;
	Cursor model = null;
	Button btAddLahan;

	SQLiteDatabase dataBase;
	// Cursor model=null;
	int listcounter = 0;

	final Context context = this;
	private ArrayList<String> userId = new ArrayList<String>();
	private ArrayList<String> get_Nama = new ArrayList<String>();
	private ArrayList<String> get_status = new ArrayList<String>();
	private ArrayList<String> get_status_lahan = new ArrayList<String>();
	String enumid, shapeid, statuslahan, lamalahan, luasprod, luasnonprod,
			tahuntanamprod, tahuntanamnonprod, pernahpanen, sikluspanen,
			beratbuahpanen, properpanen, asalbibitchk, asalbibit, jenisbibit,
			hibrida, asalpupukchk, asalpupuk, jenispupuk, gunapupukchk,
			gunapupuk, campurpupuk, bahanpupuk, tutupanlahan, lokasijualchk,
			lokasijual;
	String StatusLah;

	private ListView listLahan;
	private AlertDialog.Builder build;
	private String rowID;
	private String fstatus;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_lahan);
		btAddLahan = (Button) findViewById(R.id.btnDelete);

		listLahan = (ListView) findViewById(R.id.ListLahan);
		db = new com.inobu.mquestioner.DbHelper(this);

		Bundle extras = getIntent().getExtras();
		Log.d("fdesa Response", extras + "");
		if (extras != null) {
			rowID = getIntent().getExtras().getString("idKK");
			fstatus = getIntent().getExtras().getString("status");
			Log.d("fputValue status listlahan menu", fstatus + "");
			Log.d("rowID Response", rowID + "");
		}

		btAddLahan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ListLahan.this,
						set.mquestioner.SetLahan.class);
				intent.putExtra("tambah", true);
				intent.putExtra("rowID", rowID);
				startActivity(intent);
			}
		});

		listLahan.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.d("row", rowID + "");
				Log.d("arg2", arg2 + "");
				Log.d("arg3", arg3 + "");

				model = db.getViewLahan(rowID);
				model.moveToPosition(arg2);
				rowID = db.getidkklahan(model);
				enumid = db.getEnumID(model);
				shapeid = db.getshape_ID(model);
				statuslahan = db.getstatus_lahan(model);
				lamalahan = db.getlama_lahan(model);
				luasprod = db.getluas_prod(model);
				luasnonprod = db.getluas_nonprod(model);
				tahuntanamprod = db.gettahun_tanam(model);
				tahuntanamnonprod = db.gettahun_tanamnon(model);
				pernahpanen = db.getpernah_panen(model);
				sikluspanen = db.getsiklus_panen(model);
				beratbuahpanen = db.getberat_buah(model);
				properpanen = db.getprod_perpanen(model);
				asalbibitchk = db.getasal_bibitchk(model);
				asalbibit = db.getasal_bibit(model);

				jenisbibit = db.getjenis_bibit(model);
				hibrida = db.gethibrida(model);
				asalpupukchk = db.getasal_pupukchk(model);
				asalpupuk = db.getasal_pupuk(model);
				jenispupuk = db.getjenis_pupuk(model);
				gunapupukchk = db.getpenggunaan_pupukchk(model);
				gunapupuk = db.getpenggunaan_pupuk(model);
				campurpupuk = db.getcampur_pupuk(model);
				bahanpupuk = db.getbahan_pupuk(model);
				tutupanlahan = db.gettutupan_lahan(model);
				lokasijualchk = db.getlokasi_jualchk(model);
				lokasijual = db.getlokasi_jual(model);
				StatusLah = db.getStatusKirim_lahan(model);
				Log.d("jenis :  ", rowID + " " + enumid + " " + shapeid + " "
						+ statuslahan + " " + lamalahan + " " + luasprod + " "
						+ luasnonprod + " " + tahuntanamprod + " "
						+ tahuntanamnonprod + " " + pernahpanen + " "
						+ sikluspanen + " " + beratbuahpanen + " "
						+ properpanen + " " + asalbibitchk + " " + asalbibit);

				Intent i = new Intent(getApplicationContext(),
						list.mquestioner.DetailLahan.class);
				i.putExtra("status", fstatus);
				i.putExtra("rowID", rowID);
				i.putExtra("status", fstatus);
				i.putExtra("fenumid", enumid);
				i.putExtra("fshapeid", shapeid);
				i.putExtra("fstatuslahan", statuslahan);
				i.putExtra("flamalahan", lamalahan);
				i.putExtra("fluasprod", luasprod);
				i.putExtra("fluasnonprod", luasnonprod);
				i.putExtra("ftahuntanamprod", tahuntanamprod);
				i.putExtra("ftahuntanamnonprod", tahuntanamnonprod);
				i.putExtra("fpernahpanen", pernahpanen);
				i.putExtra("fsikluspanen", sikluspanen);
				i.putExtra("fberatbuahpanen", beratbuahpanen);
				i.putExtra("fProdperTaun", properpanen);
				i.putExtra("fasalbibitchk", asalbibitchk);
				i.putExtra("fasalbibit", asalbibit);
				i.putExtra("fjenisbibit", jenisbibit);
				i.putExtra("fhibrida", hibrida);
				i.putExtra("fasalpupukchk", asalpupukchk);
				i.putExtra("fasalpupuk", asalpupuk);
				i.putExtra("fjenispupuk", jenispupuk);
				i.putExtra("fgunapupukchk", gunapupukchk);
				i.putExtra("fgunapupuk", gunapupuk);
				i.putExtra("fcampurpupuk", campurpupuk);
				i.putExtra("fbahanpupuk", bahanpupuk);
				i.putExtra("ftutupanlahan", tutupanlahan);
				i.putExtra("flokasijualchk", lokasijualchk);
				i.putExtra("flokasijual", lokasijual);
				i.putExtra("fstatuslah", StatusLah);

				Log.d("jenis pupuk ", jenispupuk);
				Log.d("gunapupukchk", gunapupukchk);
				Log.d("gunapupuk ", gunapupuk);
				startActivity(i);
			}
		});

		listLahan.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {

				build = new AlertDialog.Builder(ListLahan.this);
				build.setTitle("Delete " + get_Nama.get(arg2) + " "
						+ get_status.get(arg2));
				build.setMessage("Do you want to delete ?");
				build.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								Toast.makeText(
										getApplicationContext(),
										get_Nama.get(arg2) + " "
												+ get_status.get(arg2)
												+ " is deleted.", 3000).show();

								dataBase.delete("tb_lahan", db.KEY_IDLAHAN
										+ "=" + userId.get(arg2), null);

								dataBase.delete("tb_lahan", db.KEY_IDLAHAN
										+ "=" + userId.get(arg2), null);
								displayData();
								dialog.cancel();
							}
						});

				build.setNegativeButton("No",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});
				AlertDialog alert = build.create();
				alert.show();

				return true;
			}
		});
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent(getApplicationContext(), ListNarasumber.class);
		startActivity(i);
	}

	@Override
	protected void onResume() {
		// Log.d("rowID Response", "tes tes");
		displayData();
		super.onResume();
	}

	private void displayData() {
		dataBase = db.getWritableDatabase();
		Cursor mCursor = dataBase
				.rawQuery(
						"SELECT a.shape_id, a.status,a.status_lahan, b.nama_kk FROM tb_lahan a LEFT JOIN tb_kk b ON a.id_kk =b.id_kk WHERE a.id_kk="
								+ rowID + " ORDER BY id_lahan", null);

		userId.clear();
		get_Nama.clear();
		get_status.clear();
		get_status_lahan.clear();

		// String arrStatus[] = null;

		if (mCursor.moveToFirst()) {
			do {
				userId.add(mCursor.getString(mCursor.getColumnIndex("shape_id")));
				get_Nama.add(mCursor.getString(mCursor
						.getColumnIndex("nama_kk")));
				// get_status_lahan.add(mCursor.getString(mCursor.getColumnIndex("status_lahan")));

				int statKiri = 0;
				String statLah = "";
				String sttt = "";
				String stk = "";

				statKiri = mCursor.getInt(mCursor.getColumnIndex("status"));
				if (statKiri == 0) {
					stk = "Open";
				} else if (statKiri == 1) {
					stk = "Sent";
				}
				get_status.add(stk);

				statLah = mCursor.getString(mCursor
						.getColumnIndex("status_lahan"));
				Log.d("sttt Response", sttt);
				String arrStatus[] = statLah.split(",");

				if (arrStatus[0].trim().equals("SKT")) {
					Log.d("sett Response", arrStatus[0].toString());
					sttt = "SKT";
				}

				if (arrStatus[1].trim().equals("SHM")) {
					Log.d("sett Response", arrStatus[1].toString());

					if (!sttt.isEmpty() & !sttt.equals("")) {
						sttt = sttt + "," + " SHM";
					} else {
						sttt = "SHM";
					}
				}

				if (arrStatus[2].trim().equals("SKTA")) {
					Log.d("sett Response", arrStatus[2].toString());

					if (!sttt.isEmpty() & !sttt.equals("")) {
						sttt = sttt + "," + " SKTA";
					} else {
						sttt = "SKTA";
					}
				}

				if (!arrStatus[3].equals("SKT") && !arrStatus[3].equals("SHM")
						&& !arrStatus[3].equals("SKTA")
						&& !arrStatus[3].equals("null")) {
					Log.d("sett Response 3", arrStatus[3].toString());
					if (!sttt.isEmpty() & !sttt.equals("")) {
						sttt = sttt + ", " + arrStatus[3].toString();
					} else {
						sttt = arrStatus[3].toString();
					}
				}
				get_status_lahan.add(sttt);

			} while (mCursor.moveToNext());
		}
		list.mquestioner.DisplayAdapterLahan disadpt = new list.mquestioner.DisplayAdapterLahan(ListLahan.this,
				userId, get_Nama, get_status_lahan, get_status);
		listLahan.setAdapter(disadpt);
		mCursor.close();
	}
}
