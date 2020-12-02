package com.inobu.mquestioner;

//import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
//import java.util.List;
import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
//import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "mQuestionair.db";
	// tasks table name
	private static final String TABLE_KK = "tb_kk";
	private static final String TABLE_LAHAN = "tb_lahan";
	private static final String TABLE_USER = "tb_user";
	private static final String TABLE_DESA = "mst_desa";
	private static final String TABLE_KEC = "mst_kecamatan";
	private static final String TABLE_KAB = "mst_kabkot";
	private static final String TABLE_PROV = "mst_prov";
	private static final String TABLE_KOSONG = "mst_kosong";
	private static final String TABLE_SETTING = "tb_setting";

	public static final String KEY_SETURL = "setURL";

	// posisi di id_desa
	public static final String KEY_DESAPOS = "pos_desa";
	public static final String KEY_KECPOS = "pos_kecamatan";
	public static final String KEY_KABPOS = "pos_kabkot";
	public static final String KEY_PROVPOS = "pos_prov";

	// untuk table desa
	public static final String KEY_KDESA = "kode_desa";
	public static final String KEY_KKEC = "kode_kecamatan";
	public static final String KEY_KKAB = "kode_kabupaten";
	public static final String KEY_NDESA = "nama_desa";
	// untuk table kecamatan
	public static final String KEY_NKEC = "nama_kecamatan";
	public static final String KEY_KKAB_KOTA = "kode_kabkot";
	// untuk table kabupaten
	private static final String KEY_NKAB_KOTA = "nama_kab_kot";
	// private static final String KEY_PROVINSI = "nama_kab_kot";
	// untuk table provinsi
	private static final String KEY_KODEPROV = "kode_prov";
	private static final String KEY_NPROV = "nama_provinsi";

	// untuk table kk
	// public static final String KEY_IDNARA = "id_nara";
	public static final String KEY_IDKK = "id_kk";
	public static final String KEY_ENUM = "enumID";
	public static final String KEY_NARA = "narasumber";
	private static final String KEY_DESA = "desa";
	private static final String KEY_KEC = "kecamatan";
	private static final String KEY_KAB = "kabupaten";
	private static final String KEY_PROV = "provinsi";
	public static final String KEY_KTP = "no_KTP";
	private static final String KEY_HP = "no_HP";
	public static final String KEY_NAMAKK = "nama_kk";
	private static final String KEY_JMLKK = "jumlah_kk";
	private static final String KEY_TMPLHR = "tempat_lhr";
	private static final String KEY_TGLLHR = "tanggal_lhr";
	private static final String KEY_PDDK = "pendidikan";
	private static final String KEY_SUKU = "suku";
	private static final String KEY_PKJUTAMA = "pekerjaan_utama ";
	private static final String KEY_PKJLAIN = "pekerjaan_lain ";
	private static final String KEY_AGTORG = "anggota_organisasi";
	private static final String KEY_TGLINPUT = "tgl_input";
	private static final String KEY_WKTINPUT = "waktu_input";
	// private static final String KEY_DATE= "tgl_input";
	// private static final String KEY_TIME= "waktu_input";
	private static final String KEY_STATUS = "status";
	private static final String KEY_CHECKON = "sent_on";

	// untuk table lahan
	public static final String KEY_IDLAHAN = "id_lahan";
	public static final String KEY_SHAPEID = "shape_id";
	public static final String KEY_STSLAHAN = "status_lahan";
	private static final String KEY_LAMALAHAN = "lama_lahan";
	private static final String KEY_LUASPROD = "luas_produksi";
	private static final String KEY_LUASNPROD = "luas_nonproduksi";
	private static final String KEY_THNTNMPROD = "tahun_tanam_prod";
	private static final String KEY_THNTNMNPROD = "tahun_tanam_nonprod";
	private static final String KEY_PRNPANEN = "pernah_panen";
	private static final String KEY_SKLSPANEN = "siklus_panen";
	private static final String KEY_BRTBUAHPANEN = "berat_buah_perpanen";
	private static final String KEY_PRODPANEN = "prod_perpanen";
	private static final String KEY_ASALBBTCHK = "asal_bibitchk";
	private static final String KEY_ASALBBT = "asal_bibit";
	private static final String KEY_JNSBBT = "jenis_bibit";
	private static final String KEY_HIBRIDA = "hibrida";
	private static final String KEY_ASALPPKCHK = "asal_pupukchk";
	private static final String KEY_ASALPPK = "asal_pupuk";
	private static final String KEY_JNSPPK = "jenis_pupuk";
	private static final String KEY_GUNAPPKCHK = "penggunaan_pupukchk";
	private static final String KEY_GUNAPPK = "penggunaan_pupuk";
	private static final String KEY_CMPRPPK = "campur_pupuk";
	private static final String KEY_BHNPPK = "bahan_pupuk";
	private static final String KEY_TTPLHN = "tutupan_lahan";
	private static final String KEY_LOKJUALCHK = "lokasi_jual_chk";
	private static final String KEY_LOKJUAL = "lokasi_jual";
	// private static final String KEY_STATUSLahan = "status";

	// private static final String KEY_IDKOSONG = "id_kosong";
	private static final String KEY_DESKRIPSI = "deskripsi";

	private static final String KEY_USERNAME = "user";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_ENUMID = "enumID";

	private static final String[] COLUMNS = { KEY_IDKK, KEY_NARA, KEY_DESA,
			KEY_KEC, KEY_KAB, KEY_KTP, KEY_HP, KEY_NAMAKK, KEY_JMLKK,
			KEY_TMPLHR, KEY_TGLLHR, KEY_PDDK, KEY_SUKU, KEY_PKJUTAMA,
			KEY_PKJLAIN, KEY_AGTORG, KEY_TGLINPUT, KEY_WKTINPUT };

	// private SQLiteDatabase dbase;
	private int randomValue;
	private static Random random = new Random();
	
	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql_kk = "CREATE TABLE IF NOT EXISTS tb_kk ("
				+ " id_kk INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " enumID VARCHAR(50)," + " narasumber VARCHAR(50),"
				+ " desa VARCHAR(50)," + " kecamatan VARCHAR(50),"
				+ " kabupaten VARCHAR(50)," + " provinsi VARCHAR(50),"
				+ " no_KTP VARCHAR(48)," + " no_HP VARCHAR(48),"
				+ " nama_kk VARCHAR(50)," + " jumlah_kk INTEGER,"
				+ " tempat_lhr VARCHAR(50)," + " tanggal_lhr VARCHAR(50),"
				+ " pendidikan VARCHAR(50)," + " suku VARCHAR(50),"
				+ " pekerjaan_utama VARCHAR(50),"
				+ " pekerjaan_lain VARCHAR(50),"
				+ " anggota_organisasi VARCHAR(50),"
				+ " tgl_input TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
				+ " status BOOLEAN DEFAULT 0," + " sent_on VARCHAR(50))";
		db.execSQL(sql_kk);

		String sql_lahan = "CREATE TABLE IF NOT EXISTS tb_lahan ("
				+ " id_lahan INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " id_kk INTEGER," + " enumID VARCHAR(50),"
				+ " shape_id VARCHAR(50)," + " status_lahan VARCHAR(50),"
				+ " lama_lahan VARCHAR(50)," + " luas_produksi DOUBLE,"
				+ " luas_nonproduksi DOUBLE," + " tahun_tanam_prod VARCHAR(4),"
				+ " tahun_tanam_nonprod VARCHAR(4)," + " pernah_panen BOOLEAN,"
				+ " siklus_panen INTEGER," + " berat_buah_perpanen DOUBLE,"
				+ " prod_perpanen DOUBLE," + " asal_bibitchk VARCHAR(50),"
				+ " asal_bibit VARCHAR(50)," + " jenis_bibit VARCHAR(50),"
				+ " hibrida VARCHAR(50)," + " asal_pupukchk VARCHAR(50),"
				+ " asal_pupuk VARCHAR(50)," + " jenis_pupuk VARCHAR(50),"
				+ " penggunaan_pupukchk TEXT," + " penggunaan_pupuk TEXT,"
				+ " campur_pupuk BOOLEAN," + " bahan_pupuk VARCHAR(50),"
				+ " tutupan_lahan VARCHAR(50),"
				+ " lokasi_jual_chk VARCHAR(50)," + " lokasi_jual VARCHAR(50),"
				+ " status BOOLEAN DEFAULT 0," + " sent_on VARCHAR(50))";
		db.execSQL(sql_lahan);

		String sql_user = "CREATE TABLE IF NOT EXISTS tb_user ("
				+ " id_user INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " user VARCHAR(50)," + " password VARCHAR(50),"
				+ " enumID INTEGER)";
		db.execSQL(sql_user);

		String sql_desa = "CREATE TABLE IF NOT EXISTS mst_desa ("
				+ " kode_kecamatan INTEGER ," + " kode_desa INTEGER,"
				+ " nama_desa VARCHAR(200))";
		db.execSQL(sql_desa);

		String sql_kec = "CREATE TABLE IF NOT EXISTS mst_kecamatan ("
				+ " kode_kabkot int(11)," + " kode_kecamatan int(11),"
				+ " nama_kecamatan varchar(200))";
		db.execSQL(sql_kec);

		String sql_kab = "CREATE TABLE IF NOT EXISTS mst_kabkot ("
				+ " kode_prov int(11)," + " kode_kabkot int(11),"
				+ " nama_kab_kot varchar(200))";
		db.execSQL(sql_kab);

		String sql_prov = "CREATE TABLE IF NOT EXISTS mst_prov ("
				+ " kode_prov int(11)," + " nama_provinsi varchar(200))";
		db.execSQL(sql_prov);

		String sql_kosong = "CREATE TABLE IF NOT EXISTS tb_kosong ("
				+ " id_kosong int(11)," + " id_kk int(11),"
				+ " shape_id VARCHAR(50)," + " deskripsi VARCHAR(250))";
		db.execSQL(sql_kosong);

		String sql_setting = "CREATE TABLE IF NOT EXISTS tb_setting ("
				+ " id_set INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " setURL varchar(200))";
		db.execSQL(sql_setting);

		// String sql_count = "CREATE TABLE IF NOT EXISTS tb_count (" +
		// " id_count INTEGER PRIMARY KEY AUTOINCREMENT," +
		// " name VARCHAR(50),"+
		// " count int(11))";
		// db.execSQL(sql_count);
	}

	public void deleteRow(int idKK) {
		getWritableDatabase().delete("tb_kk", "id_kk=" + idKK, null);
	}

	public void deleteRowLahan(int idLahan) {
		getWritableDatabase().delete("tb_lahan", "id_lahan=" + idLahan, null);
	}

	public void delete(String mst) {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(mst, null, null);
		db.close();
	}

	public void insertURL() {
		ContentValues val = new ContentValues();
		val.put(KEY_SETURL, "http://smallholder.inobu.org/");
//		 val.put(KEY_SETURL, "http://158.69.130.3/");

		getWritableDatabase().insert(TABLE_SETTING, null, val);
	}

	public void insertCount(String name) {
		ContentValues val = new ContentValues();
		val.put(KEY_USERNAME, name);

		getWritableDatabase().insert(TABLE_USER, null, val);
	}

	public void insertUser(String username, String password, String enumid) {
		ContentValues val = new ContentValues();
		val.put(KEY_USERNAME, username);
		val.put(KEY_PASSWORD, password);
		val.put(KEY_ENUMID, enumid);

		getWritableDatabase().insert(TABLE_USER, null, val);
	}

	public void updateURL(long id, String url) {
		ContentValues val = new ContentValues();
		val.put(KEY_SETURL, url);

		getWritableDatabase().update(TABLE_SETTING, val, "id_set=" + id, null);
		close();
	}

	public void updateDesa(long id, String desa, String kecamatan,
			String kabupaten, String provinsi) {
		ContentValues val = new ContentValues();
		val.put(KEY_DESA, desa);
		val.put(KEY_KEC, kecamatan);
		val.put(KEY_KAB, kabupaten);
		val.put(KEY_PROV, provinsi);

		getWritableDatabase().update(TABLE_KK, val, "id_kk=" + id, null);
		close();
	}

	public void insertDesa(String desa, String kecamatan, String kabupaten,
			String provinsi) {
		ContentValues val = new ContentValues();
		val.put(KEY_DESA, desa);
		val.put(KEY_KEC, kecamatan);
		val.put(KEY_KAB, kabupaten);
		val.put(KEY_PROV, provinsi);

		getWritableDatabase().insert(TABLE_KK, null, val);
	}

	public void updateStatus(int id, String status, String checkon) {
		ContentValues val = new ContentValues();
		val.put(KEY_STATUS, status);
		val.put(KEY_CHECKON, checkon);

		getWritableDatabase().update(TABLE_KK, val, "id_kk=" + id, null);
		close();
	}

	public void updateStatusLahan(String id, String status, String checkon) {
		ContentValues val = new ContentValues();
		val.put(KEY_STATUS, status);
		val.put(KEY_CHECKON, checkon);

		getWritableDatabase().update(TABLE_LAHAN, val,
				"shape_id= '" + id + "'", null);
		close();
	}

	public void insertNarasumber(String enumne, String narasumber, int KTP,
			int HP, String namakk, String jmlkk, String tempatlhr,
			String tanggallhr, String pendidikan, String suku,
			String kerjaUtama, String kerjaLain, String AngOrg) {
		ContentValues val = new ContentValues();
		val.put(KEY_ENUM, enumne);
		val.put(KEY_NARA, narasumber);
		val.put(KEY_KTP, KTP);
		val.put(KEY_HP, HP);
		val.put(KEY_NAMAKK, namakk);
		val.put(KEY_JMLKK, jmlkk);
		val.put(KEY_TMPLHR, tempatlhr);
		val.put(KEY_TGLLHR, tanggallhr);
		val.put(KEY_PDDK, pendidikan);
		val.put(KEY_SUKU, suku);
		val.put(KEY_PKJUTAMA, kerjaUtama);
		val.put(KEY_PKJLAIN, kerjaLain);
		val.put(KEY_AGTORG, AngOrg);

		getWritableDatabase().insert(TABLE_KK, null, val);
	}

	public void updateNarasumber(long id, String enumID, String narasumber,
			String KTP, String HP, String namakk, int jmlkk, String tempatlhr,
			String tanggallhr, String pendidikan, String suku,
			String kerjaUtama, String kerjaLain, String AngOrg) {
		ContentValues val = new ContentValues();
		val.put(KEY_ENUM, enumID);
		val.put(KEY_NARA, narasumber);
		val.put(KEY_KTP, KTP);
		val.put(KEY_HP, HP);
		val.put(KEY_NAMAKK, namakk);
		val.put(KEY_JMLKK, jmlkk);
		val.put(KEY_TMPLHR, tempatlhr);
		val.put(KEY_TGLLHR, tanggallhr);
		val.put(KEY_PDDK, pendidikan);
		val.put(KEY_SUKU, suku);
		val.put(KEY_PKJUTAMA, kerjaUtama);
		val.put(KEY_PKJLAIN, kerjaLain);
		val.put(KEY_AGTORG, AngOrg);

		getWritableDatabase().update(TABLE_KK, val, "id_kk=" + id, null);
		close();
	}

	public void insertLahan(int idkk, String enumne, String shapeID,
			String statusLahan, String lamaLahan, String luasProd,
			String luasNonProd, String tahunTanamProd,
			String tahunTanamNonProd, Boolean pernahPanen, String siklusPanen,
			String beratPerPanen, String prodPerPanen, String tutupLahan,
			String lokJualChk, String lokJual) {
		ContentValues val = new ContentValues();
		val.put(KEY_IDKK, idkk);
		val.put(KEY_ENUM, enumne);
		val.put(KEY_SHAPEID, shapeID);
		val.put(KEY_STSLAHAN, statusLahan);
		val.put(KEY_LAMALAHAN, lamaLahan);
		val.put(KEY_LUASPROD, luasProd);
		val.put(KEY_LUASNPROD, luasNonProd);
		val.put(KEY_THNTNMPROD, tahunTanamProd);
		val.put(KEY_THNTNMNPROD, tahunTanamNonProd);
		val.put(KEY_PRNPANEN, pernahPanen);
		val.put(KEY_SKLSPANEN, siklusPanen);
		val.put(KEY_BRTBUAHPANEN, beratPerPanen);
		val.put(KEY_PRODPANEN, prodPerPanen);
		val.put(KEY_TTPLHN, tutupLahan);
		val.put(KEY_LOKJUALCHK, lokJualChk);
		val.put(KEY_LOKJUAL, lokJual);
		val.put(KEY_STATUS, "0");
		getWritableDatabase().insert(TABLE_LAHAN, null, val);
	}

	public void updateLahan(String shapeID, String statusLahan,
			String lamaLahan, String luasProd, String luasNonProd,
			String tahunTanamProd, String tahunTanamNonProd,
			Boolean pernahPanen, String siklusPanen, String beratPerPanen,
			String prodPerPanen, String tutupLahan, String lokJualChk,
			String lokJual) {
		ContentValues val = new ContentValues();
		val.put(KEY_STSLAHAN, statusLahan);
		val.put(KEY_LAMALAHAN, lamaLahan);
		val.put(KEY_LUASPROD, luasProd);
		val.put(KEY_LUASNPROD, luasNonProd);
		val.put(KEY_THNTNMPROD, tahunTanamProd);
		val.put(KEY_THNTNMNPROD, tahunTanamNonProd);
		val.put(KEY_PRNPANEN, pernahPanen);
		val.put(KEY_SKLSPANEN, siklusPanen);
		val.put(KEY_BRTBUAHPANEN, beratPerPanen);
		val.put(KEY_PRODPANEN, prodPerPanen);
		val.put(KEY_TTPLHN, tutupLahan);
		val.put(KEY_LOKJUALCHK, lokJualChk);
		val.put(KEY_LOKJUAL, lokJual);

		getWritableDatabase().update(TABLE_LAHAN, val,
				"shape_id='" + shapeID + "'", null);
	}

	public void updateshapeid(int id_lahan, String shapeID) {
		ContentValues val = new ContentValues();
		val.put(KEY_SHAPEID, shapeID);

		getWritableDatabase().update(TABLE_LAHAN, val,
				"id_lahan ='" + id_lahan + "'", null);
	}

	public void updateBibit(String ShapeID, String asalBibitchk,
			String asalBibit, String jenisBibit, String hibrida) {
		ContentValues val = new ContentValues();
		val.put(KEY_ASALBBT, asalBibit);
		val.put(KEY_ASALBBTCHK, asalBibitchk);
		val.put(KEY_JNSBBT, jenisBibit);
		val.put(KEY_HIBRIDA, hibrida);

		getWritableDatabase().update(TABLE_LAHAN, val, "shape_id=" + ShapeID,
				null);
	}

	public void updatePupuk(String ShapeID, String asalPupukchk,
			String asalPupuk, String jenisPupuk, String gunaPupukchk,
			String gunaPupuk, boolean campurPupuk, String bahanPupuk) {
		ContentValues val = new ContentValues();
		val.put(KEY_ASALPPKCHK, asalPupukchk);
		val.put(KEY_ASALPPK, asalPupuk);
		val.put(KEY_JNSPPK, jenisPupuk);
		val.put(KEY_GUNAPPKCHK, gunaPupukchk);
		val.put(KEY_GUNAPPK, gunaPupuk);
		val.put(KEY_CMPRPPK, campurPupuk);
		val.put(KEY_BHNPPK, bahanPupuk);

		getWritableDatabase().update(TABLE_LAHAN, val, "shape_id=" + ShapeID,
				null);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_KK);
		onCreate(db);

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LAHAN);
		onCreate(db);

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
		onCreate(db);

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DESA);
		onCreate(db);

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_KEC);
		onCreate(db);

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_KAB);
		onCreate(db);

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROV);
		onCreate(db);

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTING);
		onCreate(db);

		// db.execSQL("DROP TABLE IF EXISTS tb_count");
		// onCreate(db);
	}

	public Cursor getAll() {
		return (getReadableDatabase().rawQuery(
				"SELECT * FROM tb_kk ORDER BY id_kk", null));
	}

	public Cursor getAllKK(int idKK) {
		Log.d("idKK Response", idKK + "");
		return (getReadableDatabase().rawQuery(
				"SELECT * FROM tb_kk WHERE id_kk = '" + idKK + "'", null));
	}

	public Cursor getViewLahan(String idKK) {
		Log.d("idKK Response", idKK + "");
		return (getReadableDatabase().rawQuery(
				"SELECT * FROM tb_lahan WHERE id_kk = '" + idKK
						+ "' ORDER BY id_lahan", null));
	}

	public Cursor getAllLahan(String idKK) {
		Log.d("idKK Response", idKK + "");
		return (getReadableDatabase().rawQuery(
				"SELECT * FROM tb_lahan WHERE id_kk = '" + idKK
						+ "' and status = '0' ORDER BY id_lahan", null));
	}

	public Cursor getAlltoLahan(String idKK, int idLahan) {
		// Log.d("idKK Response", idKK+"");
		return (getReadableDatabase().rawQuery(
				"SELECT * FROM tb_lahan WHERE id_kk = '" + idKK
						+ "' and id_lahan=" + idLahan + " ORDER BY id_lahan",
				null));
	}

	public Cursor getAllLahanKosong() {
		return (getReadableDatabase()
				.rawQuery(
						"select * from tb_lahan where status_lahan is null and lama_lahan ='' and tahun_tanam_prod='0'",
						null));
	}

	public Cursor getAlltoLhn(String idKK, String shapeid) {
		Log.d("idKK Response", idKK + " " + shapeid);
		return (getReadableDatabase().rawQuery(
				"select * from tb_lahan where id_kk='" + idKK
						+ "' and shape_id='" + shapeid + "' order by id_lahan",
				null));
	}

	public Cursor getOneContact(long id) {
		return (getReadableDatabase().query("tb_kk", null, "id_kk=" + id, null,
				null, null, null));
	}

	public Cursor getLastdateKK() {
		return (getReadableDatabase().rawQuery(
				"select * from tb_kk where status = 0", null));
	}

	public Cursor getLastdateLahan() {
		return (getReadableDatabase().rawQuery(
				"select * from tb_lahan where status = 0", null));
	}

	// public Cursor getURL()
	// {
	// return (getReadableDatabase().query("tb_setting", null, "id_set= 1",
	// null, null, null, null));
	// }

	public String getURL() {
		String row = null;
		try {
			String selectQuery = "SELECT setURL FROM tb_setting where id_set=1";

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor != null) {
				cursor.moveToFirst();
			}

			row = cursor.getString(0);
		} catch (Throwable e) {

		}
		return row;
	}

//	public String setRandom() {
//		String setrandom = null;
//		myRandom = new Random();
//		
//		for(int i = 0; i < 10; i++){
//			setrandom += String.valueOf(myRandom.nextInt()) + "\n";
//	          }
//		return setrandom;
//	}

	public static int getRandom(int max, int min){
		return random.nextInt(max - min + 1)+min;
	}

	public String getUser() {
		String row = null;
		try {
			String selectQuery = "SELECT user FROM tb_user where id_user=1";

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor != null) {
				cursor.moveToFirst();
			}

			row = cursor.getString(0);
		} catch (Throwable e) {

		}
		return row;
	}

	// public Cursor getOneDesa(String id)
	// {
	// return (getReadableDatabase().query("mst_desa", null, "kode_desa=" + id,
	// null, null, null, null));
	// }
	//
	// public Cursor getOneKec(String id)
	// {
	// return (getReadableDatabase().query("mst_kecamatan", null,
	// "kode_kecamatan=" + id, null, null, null, null));
	// }
	//
	// public Cursor getOneKab(String id)
	// {
	// return (getReadableDatabase().query("mst_kabupaten", null, "kode_kabkot="
	// + id, null, null, null, null));
	// }
	//
	// public Cursor getOneProv(String id)
	// {
	// return (getReadableDatabase().query("mst_prov", null, "kode_prov=" + id,
	// null, null, null, null));
	// }

	public Contact readContact(int id) {
		// get reference of the BookDB database
		SQLiteDatabase db = this.getReadableDatabase();

		// get book query
		Cursor cursor = db.query(
				TABLE_KK, // a. table
				COLUMNS, " id_kk = ?", new String[] { String.valueOf(id) },
				null, null, null, null);

		// if results !=null, parse the first one
		if (cursor != null)
			cursor.moveToFirst();

		Contact cont = new Contact();
		cont.setID(Integer.parseInt(cursor.getString(0)));
		cont.setName(cursor.getString(1));
		cont.setName(cursor.getString(2));

		return cont;
	}

	public int getid() {
		int row = 0;
		String selectQuery = "SELECT MAX(id_kk) FROM tb_kk";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}

		row = cursor.getInt(0);
		return row;
	}

	// public int getidLahan(int idkk)
	// {
	// int row=0;
	// String selectQuery = "SELECT MAX(id_lahan) FROM tb_lahan where id_kk='"+
	// idkk+"'";
	// SQLiteDatabase db = this.getWritableDatabase();
	// Cursor cursor = db.rawQuery(selectQuery, null);
	// if (cursor != null) {
	// cursor.moveToFirst();
	// }
	//
	// row = cursor.getInt(0);
	// return row;
	// }

	public int getidLahannya(int idkk) {
		int row = 0;
		try {
			String selectQuery = "SELECT MAX(id_lahan) FROM tb_lahan where id_kk='"
					+ idkk + "'";
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor != null) {
				cursor.moveToFirst();
			}

			row = cursor.getInt(0);
		} catch (Throwable e) {

		}
		return row;
	}

	public int getidLahan() {
		int row = 0;
		try {
			String selectQuery = "SELECT MAX(id_lahan) FROM tb_lahan";
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor != null) {
				cursor.moveToFirst();
			}

			row = cursor.getInt(0);
		} catch (Throwable e) {

		}
		return row;
	}

	public int getEnumID() {
		int row = 0;
		String selectQuery = "SELECT MAX(enumID) FROM tb_user";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}

		row = cursor.getInt(0);
		return row;
	}

	public int getShapeID(int idk) {
		int row = 0;
		try {
			String selectQuery = "SELECT MAX(shape_id) FROM tb_lahan where id_kk="
					+ idk;
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor != null) {
				cursor.moveToFirst();
			}

			row = cursor.getInt(0);
		} catch (Throwable e) {

		}
		return row;
	}

	public Set<String> getAllDesa(String kec) {
		Set<String> set = new HashSet<String>();

		String selectQuery = null;

		selectQuery = "select b.kode_desa, b.nama_desa from mst_kecamatan a"
				+ " inner join mst_desa b on a.kode_kecamatan = b.kode_kecamatan"
				+ " where nama_kecamatan like '%" + kec
				+ "%' order by nama_desa asc";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				set.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();

		return set;
	}

	public Set<String> getAllKec(String kab) {
		Set<String> set = new HashSet<String>();

		String selectQuery = "select b.kode_kecamatan, b.nama_kecamatan"
				+ " from mst_kecamatan b"
				+ " left join mst_kabkot c on b.kode_kabkot = c.kode_kabkot"
				+ " where c.nama_kab_kot like '%" + kab
				+ "%' order by nama_kecamatan asc";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				set.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();

		return set;
	}

	public Set<String> getAllKab(String prov) {
		Set<String> set = new HashSet<String>();

		String selectQuery = "select b.kode_kabkot, b.nama_kab_kot"
				+ " from mst_kabkot b"
				+ " left join mst_prov c on b.kode_prov = c.kode_prov"
				+ " where c.nama_provinsi like '%" + prov
				+ "%' order by b.nama_kab_kot asc";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				set.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();

		return set;
	}

	public Set<String> getAllProv() {
		Set<String> set = new HashSet<String>();
		String selectQuery = "select kode_prov, nama_provinsi from mst_prov order by nama_provinsi asc";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				set.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();

		return set;
	}

	public int getDesaID(String ndesa) {
		int row = 0;
		try {
			String selectQuery = "SELECT kode_desa FROM mst_desa WHERE nama_desa ='"
					+ ndesa + "'";

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor != null) {
				cursor.moveToFirst();
			}

			row = cursor.getInt(0);
		} catch (Throwable e) {

		}
		return row;
	}

	public int getKecID(String idkec) {
		int row = 0;
		try {
			String selectQuery = "SELECT kode_kecamatan FROM mst_kecamatan where nama_kecamatan ='"
					+ idkec + "'";

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor != null) {
				cursor.moveToFirst();
			}

			row = cursor.getInt(0);
		} catch (Throwable e) {

		}
		return row;
	}

	public int getKabID(String idkab) {
		int row = 0;
		try {
			String selectQuery = "SELECT kode_kabkot FROM mst_kabkot where nama_kab_kot='"
					+ idkab + "'";

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor != null) {
				cursor.moveToFirst();
			}

			row = cursor.getInt(0);
		} catch (Throwable e) {

		}
		return row;
	}

	public int getProvID(String idProv) {
		int row = 0;
		try {
			String selectQuery = "SELECT kode_prov FROM mst_prov where nama_provinsi ='"
					+ idProv + "'";

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor != null) {
				cursor.moveToFirst();
			}

			row = cursor.getInt(0);
		} catch (Throwable e) {

		}
		return row;
	}

	public int countURL() {
		int row = 0;
		try {
			String selectQuery = "SELECT COUNT(*) from tb_setting";

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor != null) {
				cursor.moveToFirst();
			}

			row = cursor.getInt(0);
		} catch (Throwable e) {

		}
		return row;
	}

	public int countDesa() {
		int row = 0;
		try {
			String selectQuery = "SELECT COUNT(*) from mst_desa";

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor != null) {
				cursor.moveToFirst();
			}

			row = cursor.getInt(0);
		} catch (Throwable e) {

		}
		return row;
	}

	public int countKec() {
		int row = 0;
		try {
			String selectQuery = "SELECT COUNT(*) from mst_kecamatan";

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor != null) {
				cursor.moveToFirst();
			}

			row = cursor.getInt(0);
		} catch (Throwable e) {

		}
		return row;
	}

	public int countKab() {
		int row = 0;
		try {
			String selectQuery = "SELECT COUNT(*) from mst_kab_kota";

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor != null) {
				cursor.moveToFirst();
			}

			row = cursor.getInt(0);
		} catch (Throwable e) {

		}
		return row;
	}

	public int countProv() {
		int row = 0;
		try {
			String selectQuery = "SELECT COUNT(*) from mst_prov";

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor != null) {
				cursor.moveToFirst();
			}

			row = cursor.getInt(0);
		} catch (Throwable e) {

		}
		return row;
	}

	// public void InsertCount(String namec, int count)
	// {
	// ContentValues val = new ContentValues();
	// val.put("name", namec);
	// val.put("count", count);
	//
	// getWritableDatabase().insert("tb_count", null, val);
	// }

	public void createInsertDesa(int kodekecamatan, int kodedesa,
			String namadesa) {
		ContentValues val = new ContentValues();
		val.put(KEY_KKEC, kodekecamatan);
		val.put(KEY_KDESA, kodedesa);
		val.put(KEY_NDESA, namadesa);

		getWritableDatabase().insert(TABLE_DESA, null, val);
	}

	public void createInsertKec(int kodekabkot, int kodekecamatan,
			String namakec) {
		ContentValues val = new ContentValues();
		val.put(KEY_KKAB_KOTA, kodekabkot);
		val.put(KEY_KKEC, kodekecamatan);
		val.put(KEY_NKEC, namakec);

		getWritableDatabase().insert(TABLE_KEC, null, val);
	}

	public void createInsertProv(int kodeprov, String namaprov) {
		ContentValues val = new ContentValues();
		val.put(KEY_KODEPROV, kodeprov);
		val.put(KEY_NPROV, namaprov);

		getWritableDatabase().insert(TABLE_PROV, null, val);
	}

	public void createInsertKab(int kodeprov, int kodekabkot, String namakab) {
		ContentValues val = new ContentValues();
		val.put(KEY_KODEPROV, kodeprov);
		val.put(KEY_KKAB_KOTA, kodekabkot);
		val.put(KEY_NKAB_KOTA, namakab);

		getWritableDatabase().insert(TABLE_KAB, null, val);
	}

	public void createInsertKosong(int idKK, String deskripsi) {
		ContentValues val = new ContentValues();
		val.put(KEY_IDKK, idKK);
		val.put(KEY_DESKRIPSI, deskripsi);

		getWritableDatabase().insert(TABLE_KOSONG, null, val);
	}

	public void createUpdateKosong(int idKK, String deskripsi) {
		ContentValues val = new ContentValues();
		val.put(KEY_DESKRIPSI, deskripsi);

		getWritableDatabase().update(TABLE_KOSONG, val, "id_kk=" + idKK, null);
	}

	// dari tabel kk
	public String getIdKK(Cursor c) {
		return (c.getString(0));
	}

	// dari tabel kk
	public String getEnumIDnara(Cursor c) {
		return (c.getString(1));
	}

	public String getNama(Cursor c) {
		return (c.getString(2));
	}

	public String getDesa(Cursor c) {
		return (c.getString(3));
	}

	public String getKec(Cursor c) {
		return (c.getString(4));
	}

	public String getKab(Cursor c) {
		return (c.getString(5));
	}

	public String getProv(Cursor c) {
		return (c.getString(6));
	}

	public String getKTP(Cursor c) {
		return (c.getString(7));
	}

	public String getHP(Cursor c) {
		return (c.getString(8));
	}

	public String getnamaKK(Cursor c) {
		return (c.getString(9));
	}

	public String getJmlKK(Cursor c) {
		return (c.getString(10));
	}

	public String getTempatLhr(Cursor c) {
		return (c.getString(11));
	}

	public String gettanggalLhr(Cursor c) {
		return (c.getString(12));
	}

	public String getpendidikan(Cursor c) {
		return (c.getString(13));
	}

	public String getsuku(Cursor c) {
		return (c.getString(14));
	}

	public String getpekerjaanutama(Cursor c) {
		return (c.getString(15));
	}

	public String getpekerjaanlain(Cursor c) {
		return (c.getString(16));
	}

	public String getagt_org(Cursor c) {
		return (c.getString(17));
	}

	public String gettglinput(Cursor c) {
		return (c.getString(18));
	}

	public String getStatus(Cursor c) {
		return (c.getString(19));
	}

	// Declare Lahan
	public String getidlahan(Cursor c) {
		return (c.getString(0));
	}

	public String getidkklahan(Cursor c) {
		return (c.getString(1));
	}

	public String getEnumID(Cursor c) {
		return (c.getString(2));
	}

	public String getshape_ID(Cursor c) {
		return (c.getString(3));
	}

	public String getstatus_lahan(Cursor c) {
		return (c.getString(4));
	}

	public String getlama_lahan(Cursor c) {
		return (c.getString(5));
	}

	public String getluas_prod(Cursor c) {
		return (c.getString(6));
	}

	public String getluas_nonprod(Cursor c) {
		return (c.getString(7));
	}

	public String gettahun_tanam(Cursor c) {
		return (c.getString(8));
	}

	public String gettahun_tanamnon(Cursor c) {
		return (c.getString(9));
	}

	public String getpernah_panen(Cursor c) {
		return (c.getString(10));
	}

	public String getsiklus_panen(Cursor c) {
		return (c.getString(11));
	}

	public String getberat_buah(Cursor c) {
		return (c.getString(12));
	}

	public String getprod_perpanen(Cursor c) {
		return (c.getString(13));
	}

	public String getasal_bibitchk(Cursor c) {
		return (c.getString(14));
	}

	public String getasal_bibit(Cursor c) {
		return (c.getString(15));
	}

	public String getjenis_bibit(Cursor c) {
		return (c.getString(16));
	}

	public String gethibrida(Cursor c) {
		return (c.getString(17));
	}

	public String getasal_pupukchk(Cursor c) {
		return (c.getString(18));
	}

	public String getasal_pupuk(Cursor c) {
		return (c.getString(19));
	}

	public String getjenis_pupuk(Cursor c) {
		return (c.getString(20));
	}

	public String getpenggunaan_pupukchk(Cursor c) {
		return (c.getString(21));
	}

	public String getpenggunaan_pupuk(Cursor c) {
		return (c.getString(22));
	}

	public String getcampur_pupuk(Cursor c) {
		return (c.getString(23));
	}

	public String getbahan_pupuk(Cursor c) {
		return (c.getString(24));
	}

	public String gettutupan_lahan(Cursor c) {
		return (c.getString(25));
	}

	public String getlokasi_jualchk(Cursor c) {
		return (c.getString(26));
	}

	public String getlokasi_jual(Cursor c) {
		return (c.getString(27));
	}

	public String getStatusKirim_lahan(Cursor c) {
		return (c.getString(28));
	}

	// ------------------------
	public String getOneDesa(String iddesa) {
		String row = null;
		try {
			String selectQuery = "SELECT nama_desa FROM mst_desa WHERE kode_desa ='"
					+ iddesa + "'";

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor != null) {
				cursor.moveToFirst();
			}

			row = cursor.getString(0);
		} catch (Throwable e) {

		}
		return row;
	}

	public String getOneKec(String idkec) {
		String row = null;
		try {
			String selectQuery = "SELECT nama_kecamatan FROM mst_kecamatan WHERE kode_kecamatan ='"
					+ idkec + "'";

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor != null) {
				cursor.moveToFirst();
			}

			row = cursor.getString(0);
		} catch (Throwable e) {

		}
		return row;
	}

	public String getOneKab(String idkab) {
		String row = null;
		try {
			String selectQuery = "SELECT nama_kab_kot FROM mst_kabkot WHERE kode_kabkot ='"
					+ idkab + "'";

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor != null) {
				cursor.moveToFirst();
			}

			row = cursor.getString(0);
		} catch (Throwable e) {

		}
		return row;
	}

	public String getOneProv(String idprov) {
		String row = null;
		try {
			String selectQuery = "SELECT nama_provinsi FROM mst_prov WHERE kode_prov ='"
					+ idprov + "'";

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor != null) {
				cursor.moveToFirst();
			}

			row = cursor.getString(0);
		} catch (Throwable e) {

		}
		return row;
	}

	public String getNarasumber(int id_kk) {
		String row = null;
		try {
			String selectQuery = "select b.narasumber, a.id_lahan, a.shape_id, a.status_lahan from tb_lahan a left join tb_kk b on a.id_kk=b.id_kk where id_kk='"
					+ id_kk + "'";

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor != null) {
				cursor.moveToFirst();
			}

			row = cursor.getString(0);
		} catch (Throwable e) {

		}
		return row;
	}

	// public int getCount(String idcount)
	// {
	// Integer row=null;
	// try{
	// String selectQuery =
	// "SELECT count FROM tb_count WHERE name ='"+idcount+"'";
	//
	// SQLiteDatabase db = this.getWritableDatabase();
	// Cursor cursor = db.rawQuery(selectQuery, null);
	// if (cursor != null) {
	// cursor.moveToFirst();
	// }
	//
	// row = cursor.getInt(0);
	// }catch (Throwable e) {
	//
	// }
	// return row;
	// }

	public int getRowCount() {
		String countQuery = "SELECT  * FROM " + TABLE_USER;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();

		// return row count
		return rowCount;
	}

	public int cekSentNarasumber() {
		String countQuery = "SELECT * FROM tb_kk where status=0";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int cnt = cursor.getCount();
		cursor.close();
		return cnt;
	}

	public int cekSentLahan() {
		String countQuery = "SELECT * FROM tb_lahan where status=0";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int cnt = cursor.getCount();
		cursor.close();
		return cnt;
	}

	public int getCountLahan(int idkk) {
		String countQuery = "SELECT * FROM tb_lahan where id_kk=" + idkk
				+ " and status=0";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int cnt = cursor.getCount();
		cursor.close();
		return cnt;
	}

	public int getLahanKosong() {
		String countQuery = "select * from tb_lahan where status_lahan is null and lama_lahan ='' and tahun_tanam_prod='0'";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int cnt = cursor.getCount();
		cursor.close();
		return cnt;
	}

	public void resetTables() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_USER, null, null);
		db.close();
	}

	public int getstatusKK(String idkk) {
		int status = 0;
		try {
			String selectQuery = "select status from tb_kk where id_kk='"
					+ idkk + "'";

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor != null) {
				cursor.moveToFirst();
			}

			status = cursor.getInt(0);
		} catch (Throwable e) {

		}
		return status;
	}

	public Cursor fetchAllCountries() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor mCursor = db.query(TABLE_KK, new String[] { KEY_IDKK,
				KEY_NAMAKK, KEY_KTP }, null, null, null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

}
