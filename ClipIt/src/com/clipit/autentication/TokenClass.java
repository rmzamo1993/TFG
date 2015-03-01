package com.clipit.autentication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.StrictMode;

public class TokenClass {
	private String user;// Varible of user taken from editText
	private String password;// Varible of password taken from editText
	public static String token_string;// Token for the identification of the
										// user
	public static String id_user;
	public static String url_string;
	private Context context;

	/*
	 * Constructor of the class. Its arguments are the user and the password
	 * taken from editText
	 */
	public TokenClass(String user, String password, Context context) {
		this.user = user;
		this.password = password;
		this.url_string = "clipit.es/clipit_ruben";
		this.context = context;
	}

	/*
	 * This method return a token
	 */
	
	public String takeToken() throws IOException{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		String url="http://"+url_string+"/services/api/rest/xml/?method=clipit.site.get_token&login="+getUser()+"&password="+getPassword()+"&timeout=1000000";
		CookieStore cookieStore = new BasicCookieStore();
		
		// Create local HTTP context
		HttpContext localContext = new BasicHttpContext();
		// Bind custom cookie store to the local context
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		HttpGet httpget = new HttpGet(url);
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
		//toParserXML();
		return xml;

	}
	/*
	 * This method is call in takeToken() method and parse the xml and takes the token
	 */
	
	public String toParserXML(String xml) throws Throwable{	 
		SAXBuilder builder = new SAXBuilder();
		Reader in = new StringReader(xml);
		Document doc = null;
		Element root = null;
		String meta = null;
		doc = builder.build(in);
		 root = doc.getRootElement();
		 meta = root.getChild("result").getText();
		 token_string=meta;
		
	   return token_string;
		
	}
	
	/*
	 * We take the token admin and we use it for create a new user
	 */
	
	public String takeTokenAdmin() throws ClientProtocolException, IOException, JDOMException{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		String url="http://"+url_string+"/services/api/rest/xml/?method=clipit.site.get_token&login=admin&password=admin1!&timeout=1000000";
		CookieStore cookieStore = new BasicCookieStore();
		
		// Create local HTTP context
		HttpContext localContext = new BasicHttpContext();
		// Bind custom cookie store to the local context
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		HttpGet httpget = new HttpGet(url);
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
		
		 
		return xml;
	}
	/*
	 * createUser method create a new user and returns the user id
	 */
	public String createUser() throws ClientProtocolException, IOException{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		String url="http://"+url_string+"/services/api/rest/xml/?method=clipit.user.create&prop_value_array[login]="+getUser()+"&prop_value_array[password]="+getPassword();
		HttpClient client =new DefaultHttpClient();// HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		post.setHeader("AUTH-TOKEN", token_string);	 
		HttpResponse response = client.execute(post);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		String xmlCreateUser  = result.toString();
		
		return xmlCreateUser;
	}
	/*
	 * Method that returns true if the password and the user doesn't match
	 */
	public Boolean errorLogIn(String xml) throws JDOMException, IOException{
		SAXBuilder builder = new SAXBuilder();
		Reader in = new StringReader(xml);
		Document doc = null;
		Element root = null;
		String meta = null;
		doc = builder.build(in);
		 root = doc.getRootElement();
		 meta = root.getChild("status").getText();
		 System.out.println(meta);
		 if(meta.equalsIgnoreCase("0")){
			 return false;
		 }else{
			 return true;
		 }	
	}
	
	/*
	 * We return the id of user when we have the user and the password
	 */
	public String getIdByLogin() throws ClientProtocolException, IOException, JDOMException{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		String url="http://"+url_string+"/services/api/rest/xml/?method=clipit.user.get_by_login&login_array[]=student&login_array[]="+getUser()+"&login_array[]=04";
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
		SAXBuilder builder = new SAXBuilder();
		Reader in = new StringReader(xml);
		Document doc = null;
		Element root = null;
		Element meta,meta1,meta2 = null;
		String id= null;
		doc = builder.build(in);
		root = doc.getRootElement();
		meta = root.getChild("result");
		meta1=meta.getChild(getUser());
		if(meta1.getChildren().size()!=0){
		meta2=meta1.getChild("ClipitUser");
		id=meta2.getChild("id").getText();
		id_user=id;
		 }
		else {
			id="NOTFOUND";
		}
		return id;
	}

	public void savePreferences(){
		//Save the token and user name for manage the session
		 Editor edit = context.getSharedPreferences("userpreferences", context.MODE_PRIVATE).edit();
		 edit.putBoolean("cookie_session", true);
		 edit.putString("user_name", user);
		 edit.putString("token_id", token_string);
		 edit.putString("user_id", id_user);
		 edit.commit();
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
