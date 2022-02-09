package de.throwstnt.developing.labymod.cvc.api.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonObject;

import net.labymod.utils.JsonParse;

public class RemoteJsonUtil {

	public static JsonObject readRemoteJson(String url) {
		JsonObject requestResult;
	      try {
	         HttpURLConnection connection = (HttpURLConnection)(new URL(url)).openConnection();
	         connection.addRequestProperty("User-Agent", "java 8 HttpURLConnection (LabyMod CvCAddon by TnTGamesTV)");
	         connection.setConnectTimeout(20000);
	         connection.setReadTimeout(20000);
	         connection.setDoOutput(true);
	         connection.setUseCaches(false);
	         connection.setRequestMethod("GET");
	         StringBuilder resultBuilder = new StringBuilder();
	         BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	         Throwable var5 = null;

	         try {
	            String line;
	            try {
	               while((line = reader.readLine()) != null) {
	                  resultBuilder.append(line);
	               }
	            } catch (Throwable var15) {
	               var5 = var15;
	               throw var15;
	            }
	         } finally {
	            if (reader != null) {
	               if (var5 != null) {
	                  try {
	                     reader.close();
	                  } catch (Throwable var14) {
	                     var5.addSuppressed(var14);
	                  }
	               } else {
	                  reader.close();
	               }
	            }

	         }

	         requestResult = (JsonObject)JsonParse.parse(resultBuilder.toString());
	      } catch (ClassCastException | IOException var17) {
	         return null;
	      }
	      
	     return requestResult;
	}
}
