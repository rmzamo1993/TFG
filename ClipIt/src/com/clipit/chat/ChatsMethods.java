package com.clipit.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.clipit.autentication.TokenClass;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;

public class ChatsMethods {
	public static String url_string;
	private ArrayList<String> idList;
	public ChatsMethods(){
		this.url_string = "clipit.es/clipit_ruben";
		idList=new ArrayList<String>();
	}
	//Recoge todos los mensajes enviados a mi inbox.
	public String getInbox(String token_string,String user_id) throws ClientProtocolException, IOException{
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		String url="http://"+url_string+"/services/api/rest/xml/?method=clipit.chat.get_inbox&user_id="+user_id;
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
		return xml;
	}
	
	//Recoge los ids de los usuarios en una lista
	public ArrayList<String> takeUserIdSender(String xml,String user_id) throws JDOMException, IOException{
		SAXBuilder builder = new SAXBuilder();
		Reader in = new StringReader(xml);
		Document doc = null;
		Element root = null;
		List<Element> children= new ArrayList<Element>();
		doc = builder.build(in);
		root = doc.getRootElement();
		children = root.getChild("result").getChildren();
		for(Element e: children){
		String id= e.getAttributeValue("name").toString();
		if(!id.equalsIgnoreCase(user_id)){
		idList.add(id);}
		}
		

		return idList;
	}
	//Asocia los ids de los usuarios con su nombre en una lista 
	public ArrayList<ListInbox> takeUserNameSender(String token_string) throws ClientProtocolException, IOException, JDOMException{
		//Creo un arrayList para meter la lista de ListInbox 
		ArrayList<ListInbox> lil=new ArrayList<ListInbox>();
		//Por cada id busco su nombre y su login
		//AÑADIR EN EL FUTURO ULTIMO MENSAJE
		for(String id_user_list:idList){
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy); 
			String url="http://"+url_string+"/services/api/rest/xml/?method=clipit.user.get_by_id&id_array[]="+id_user_list;
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
			Element meta = null;
			Element meta1= null;
			Element meta2=null;
			doc = builder.build(in);
			root = doc.getRootElement();
			 meta = root.getChild("result");
			 meta1=meta.getChild("array_item");
			 meta2=meta1.getChild("ClipitUser");
			String name=meta2.getChild("name").getText();
			String login=meta2.getChild("login").getText();
			ListInbox listInbox=new ListInbox();
			listInbox.setName(name);
			listInbox.setLoginUser(login);
			listInbox.setIdUserSender(id_user_list);
			lil.add(listInbox);
		}
		return lil;
		
	}
	//Este metodo recoge las conversaciones y devuelve una lista de mensajes donde en cada uno se guarda
	// el titulo el mensaje y cuando fue creado.
	public ArrayList<Message> getConversation(String token_string, String idConversation, String myId) throws ClientProtocolException, IOException, JDOMException{
		ArrayList<Message> messagesList=new ArrayList<Message>();
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		String url="http://"+url_string+"/services/api/rest/xml/?method=clipit.chat.get_conversation&user1_id="+idConversation+"&user2_id="+myId;
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
		Element meta = null;
		Element meta1= null;
		List<Element> childrenList=new ArrayList<Element>();
		doc = builder.build(in);
		root = doc.getRootElement();
		meta = root.getChild("result");
		childrenList=meta.getChildren();
		for(Element e:childrenList){
			Message m = new Message();
			meta1=e.getChild("ClipitChat");
			m.setMessage(meta1.getChild("description").getText());
			m.setTitle(meta1.getChild("name").getText());
			m.setTimeCreated(meta1.getChild("time_created").getText());
			m.setDestination(meta1.getChild("destination").getText());
			if((meta1.getChild("destination").getText()).equals(myId)){
				m.setMine(false);
			}else{
				m.setMine(true);
			}
			messagesList.add(m);
		}
		return messagesList;
		
	}
	
	public void createMessage(String token_string,String idDestination,String messageSend) throws ClientProtocolException, IOException{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		String url="http://"+url_string+"/services/api/rest/xml/";//?method=clipit.chat.create&prop_value_array[name]=name_D&prop_value_array[description]="+messageSend+"&prop_value_array[destination]="+idDestination;
		HttpClient client =new DefaultHttpClient();// HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		post.setHeader("AUTH-TOKEN", token_string);	 
		 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("method", "clipit.chat.create"));
	        nameValuePairs.add(new BasicNameValuePair("prop_value_array[name]", "Default"));
	        nameValuePairs.add(new BasicNameValuePair("prop_value_array[description]", messageSend));
	        nameValuePairs.add(new BasicNameValuePair("prop_value_array[destination]", idDestination));
	        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		HttpResponse response = client.execute(post);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		String xmlCreateUser  = result.toString();
		
	}
	/*public String getIpActualUser(){
		
	}*/
	
}
