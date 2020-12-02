package com.inobu.mquestioner;

public class Insert {

	private int id;
	private String KodeDesa;
	private String KodeKec;
	private String KodeKab;
	private String NamaDesa;

	public Insert() {
	}

	public Insert(String KdDesa, String KdKec, String KdKab, String NmDesa) {
		super();
		this.KodeDesa = KdDesa;
		this.KodeKec = KdKec;
		this.KodeKab = KdKab;
		this.NamaDesa = NmDesa;
	}

	public String getKodeDesa() {
		return KodeDesa;
	}

	public void setKodeDesa(String kdDesa) {
		this.KodeDesa = kdDesa;
	}

	public String getKodeKec() {
		return KodeKec;
	}

	public void setKodeKec(String kdKec) {
		this.KodeKec = kdKec;
	}

	public String getKodeKab() {
		return KodeKab;
	}

	public void setKodeKab(String kdKab) {
		this.KodeKab = kdKab;
	}

	public String getNamaDesa() {
		return NamaDesa;
	}

	public void setNamaDesa(String nmDesa) {
		this.NamaDesa = nmDesa;
	}
}
