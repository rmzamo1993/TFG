package com.clipit.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import android.os.StrictMode;

public class ActivitiesMethods {
	public static String url_string;

	public ActivitiesMethods(){
		this.url_string = "clipit.es/clipit_ruben";

	}
	
	public ArrayList<Activities> getActivities(String token_string,String idUser) throws ClientProtocolException, IOException, JDOMException{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		String url="http://"+url_string+"/services/api/rest/xml/?method=clipit.activity.get_all";
		CookieStore cookieStore = new BasicCookieStore();		
		// Create local HTTP context
		HttpContext localContext = new BasicHttpContext();
		// Bind custom cookie store to the local context
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		HttpGet httpget = new HttpGet(url);
		httpget.setHeader("AUTH-TOKEN", token_string);
		HttpClient client = new DefaultHttpClient();
		System.out.println("executing request " + httpget.getURI());

		// Pass local context as a parameter

		HttpResponse response = client.execute(httpget, localContext);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		String xml = result.toString();
		List<Element> children= new ArrayList<Element>();
		Activities activities=new Activities();
		ArrayList <Activities> listActivities=new ArrayList<Activities>();
		SAXBuilder builder = new SAXBuilder();
		Reader in = new StringReader(xml);
		Document doc = null;
		Element root = null;
		doc = builder.build(in);
		root = doc.getRootElement();
		children = root.getChild("result").getChildren();
		for(Element elem:children){
			Element e=elem.getChild("ClipitActivity");
			if((e.getChild("student_array").getChild("array_item").getText()).equalsIgnoreCase(idUser)){
			activities.setStatus(e.getChild("status").getText());
			activities.setInitialTime(e.getChild("start").getText());
			activities.setFinalTime(e.getChild("end").getText());
			activities.setNameActivities(e.getChild("name").getText());
			activities.setDescription(e.getChild("description").getText());
			listActivities.add(activities);
			}
		}
		return listActivities;
	}
	
}
