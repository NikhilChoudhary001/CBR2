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
	
	public static String getEmsbb() {
		return emsbb;
	}
	public static void setEmsbb(String emsbb) {
		PropertiesCBR.emsbb = emsbb;
	}
	public static String getLegacyNBN() {
		return legacyNBN;
	}
	public static void setLegacyNBN(String legacyNBN) {
		PropertiesCBR.legacyNBN = legacyNBN;
	}
	public static String getNbnPlus() {
		return nbnPlus;
	}
	public static void setNbnPlus(String nbnPlus) {
		PropertiesCBR.nbnPlus = nbnPlus;
	}
	public static int getCutoffpercentage() {
		return cutoffpercentage;
	}
	public static void setCutoffpercentage(int cutoffpercentage) {
		PropertiesCBR.cutoffpercentage = cutoffpercentage;
	}
	public static int getLegacyqueuepercentage() {
		return legacyqueuepercentage;
	}
	public static void setLegacyqueuepercentage(int legacyqueuepercentage) {
		PropertiesCBR.legacyqueuepercentage = legacyqueuepercentage;
	}
	public static String getActivemqurl() {
		return activemqurl;
	}
	public static void setActivemqurl(String activemqurl) {
		PropertiesCBR.activemqurl = activemqurl;
	}
	public static int getConfigloadingtime() {
		return configloadingtime;
	}
	public static void setConfigloadingtime(int configloadingtime) {
		PropertiesCBR.configloadingtime = configloadingtime;
	}
	
	
	
}
