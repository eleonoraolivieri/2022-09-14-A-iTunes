package it.polito.tdp.itunes.model;

import java.util.Objects;

public class Adiacenza {
	
	public Adiacenza(Album v1, Album v2) {
		super();
		this.v1 = v1;
		this.v2 = v2;
	}
	private Album v1;
	private Album v2;
	public Album getV1() {
		return v1;
	}
	public void setV1(Album v1) {
		this.v1 = v1;
	}
	public Album getV2() {
		return v2;
	}
	public void setV2(Album v2) {
		this.v2 = v2;
	}
	@Override
	public int hashCode() {
		return Objects.hash(v1, v2);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adiacenza other = (Adiacenza) obj;
		return Objects.equals(v1, other.v1) && Objects.equals(v2, other.v2);
	}


}
