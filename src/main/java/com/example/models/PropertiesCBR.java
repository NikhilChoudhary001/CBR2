package com.example.models;

import org.springframework.stereotype.Component;

@Component
public class PropertiesCBR {

	private static String emsbb;
	private static String legacyNBN;
	private static String nbnPlus;
	private static int cutoffpercentage;
	private static int legacyqueuepercentage;
	private static String activemqurl;
	private static int configloadingtime;
	
	
	public String getEmsbb() {
		return emsbb;
	}
	public void setEmsbb(String emsbb) {
		this.emsbb = emsbb;
	}
	public String getLegacyNBN() {
		return legacyNBN;
	}
	public void setLegacyNBN(String legacyNBN) {
		this.legacyNBN = legacyNBN;
	}
	public String getNbnPlus() {
		return nbnPlus;
	}
	public void setNbnPlus(String nbnPlus) {
		this.nbnPlus = nbnPlus;
	}
	public int getCutoffpercentage() {
		return cutoffpercentage;
	}
	public void setCutoffpercentage(int cutoffpercentage) {
		this.cutoffpercentage = cutoffpercentage;
	}
	public int getLegacyqueuepercentage() {
		return legacyqueuepercentage;
	}
	public void setLegacyqueuepercentage(int legacyqueuepercentage) {
		this.legacyqueuepercentage = legacyqueuepercentage;
	}
	public String getActivemqurl() {
		return activemqurl;
	}
	public void setActivemqurl(String activemqurl) {
		this.activemqurl = activemqurl;
	}
	public int getConfigloadingtime() {
		return configloadingtime;
	}
	public void setConfigloadingtime(int configloadingtime) {
		this.configloadingtime = configloadingtime;
	}
	
	
}
