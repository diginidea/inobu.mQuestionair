package com.inobu.mquestioner;

public class Contact {
	// private variables
	int _id;
	String _name;
	String _nik;
	String _desa;
	String _kec;
	String _kab;
	String _noKTP;
	String _noHP;
	String _namaKK;
	String _JmlKK;
	String _tmptLhr;
	String _tglLhr;
	String _pendidikan;
	String _suku;
	String _pekerjaan_utama;
	String _pekerjaan_lain;
	String _anggota_org;
	String _tgl_input;
	String _waktu_input;
	String Status;
	private boolean selected;

	// Empty constructor
	public Contact() {

	}

	// constructor
	public Contact(int id, String name, String nik, String desa, String kec,
			String kab, String noKTP, String noHP, String namakk, String jmlkk,
			String tmplhr, String tgllhr, String pendidikan, String suku,
			String pkerjaUtama, String pkerjaLain, String andggota_org) {
		this._id = id;
		this._name = name;
		this._nik = nik;
		this._desa = desa;
		this._kec = kec;
		this._kab = kab;
		this._noKTP = noKTP;
		this._noHP = noHP;
		this._namaKK = namakk;
		this._JmlKK = jmlkk;
		this._tmptLhr = tmplhr;
		this._tglLhr = tgllhr;
		this._pendidikan = pendidikan;
		this._suku = suku;
		this._pekerjaan_utama = pkerjaUtama;
		this._pekerjaan_lain = pkerjaLain;
		this._anggota_org = andggota_org;
	}

	// constructor
	// public Contact(String name, String _nik){
	// this._name = name;
	// this._nik = _nik;
	// }
	// getting ID
	public int getID() {
		return this._id;
	}

	// setting id
	public void setID(int id) {
		this._id = id;
	}

	// getting Nama
	public String getName() {
		return this._name;
	}

	// setting Nama
	public void setName(String name) {
		this._name = name;
	}

	// getting Telp
	public String getNIK() {
		return this._nik;
	}

	// setting phone number
	public void setNIK(String nik) {
		this._nik = nik;
	}

	// getting phone number
	public String getDesa() {
		return this._desa;
	}

	// setting phone number
	public void setDesa(String desa) {
		this._desa = desa;
	}

	// getting phone number
	public String getKec() {
		return this._kec;
	}

	// setting phone number
	public void setKec(String kec) {
		this._kec = kec;
	}

	// getting phone number
	public String getKab() {
		return this._kab;
	}

	// setting phone number
	public void setKab(String kab) {
		this._kab = kab;
	}

	// getting phone number
	public String getHP() {
		return this._noHP;
	}

	// setting phone number
	public void setHP(String telp) {
		this._noHP = telp;
	}

	// getting phone number
	public String getNamaKK() {
		return this._namaKK;
	}

	// setting phone number
	public void setNamaKK(String namakk) {
		this._namaKK = namakk;
	}

	// getting phone number
	public String getJmlKK() {
		return this._JmlKK;
	}

	// setting phone number
	public void setJmlKK(String jmlkk) {
		this._JmlKK = jmlkk;
	}

	// getting phone number
	public String getTmptLahir() {
		return this._tmptLhr;
	}

	// setting phone number
	public void setTmptLahir(String tmptlhr) {
		this._tmptLhr = tmptlhr;
	}

	// getting phone number
	public String gettglLhr() {
		return this._tglLhr;
	}

	// setting phone number
	public void settglLhr(String tgllhr) {
		this._tglLhr = tgllhr;
	}

	public String getPendidikan() {
		return this._pendidikan;
	}

	// setting phone number
	public void setPendidikan(String pendidikan) {
		this._pendidikan = pendidikan;
	}

	// getting phone number
	public String getsuku() {
		return this._suku;
	}

	// setting phone number
	public void setsuku(String suku) {
		this._suku = suku;
	}

	public String getPekerjaan_utama() {
		return this._pekerjaan_utama;
	}

	// setting phone number
	public void setPekerjaan_utama(String pekjutama) {
		this._pekerjaan_utama = pekjutama;
	}

	public String getPekerjaan_lain() {
		return this._pekerjaan_lain;
	}

	// setting phone number
	public void setPekerjaan_lain(String pekjlain) {
		this._pekerjaan_lain = pekjlain;
	}

	public String getAnggota_org() {
		return this._anggota_org;
	}

	// setting phone number
	public void setAnggotaOrg(String anggotaorg) {
		this._anggota_org = anggotaorg;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
