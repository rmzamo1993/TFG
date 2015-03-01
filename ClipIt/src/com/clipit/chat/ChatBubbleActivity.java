package com.clipit.chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import org.apache.http.client.ClientProtocolException;
import org.jdom2.JDOMException;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

//import com.clipit.sockets.ServerSockets;
import com.example.clipit.R;


public class ChatBubbleActivity extends ListActivity {
	ArrayList<Message> messages;
	/*Socket miCliente;
	ServerSocket skServidor;
	String TimeStamp;
	private boolean connected = false;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	Message mdata;
	String ip_s="2.137.49.125";
	String ip_d="2.137.49.125";
	final int PORT_S = 5555;*/
	ChatArrayAdapter adapter;
	EditText text;
	private static final String TAG = "ChatBubbleActivity";
	static Random rand = new Random();	
	static String sender;
	public String idDestino; 
	String token=null;
	String user_id=null;
	ListInbox lInbox;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_message);
		lInbox=(ListInbox)this.getIntent().getSerializableExtra("ConversationWithUser");
		Log.i(TAG, ""+lInbox.getIdUserSender());
		text = (EditText) this.findViewById(R.id.text);
		//Ponemos el nombre del usuario en la actionbar
		sender = lInbox.getName();
		this.setTitle(sender);
		String user_name=null;
		
		SharedPreferences sp = this.getSharedPreferences("userpreferences", Context.MODE_PRIVATE);
		if(sp.contains("cookie_session")){
			user_name = sp.getString("user_name", null);
			token = sp.getString("token_id", null);
			user_id=sp.getString("user_id", null);}
		ChatsMethods cm=new ChatsMethods();
		try {
			messages = cm.getConversation(token, lInbox.getIdUserSender(), user_id);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		idDestino=lInbox.getIdUserSender();
		adapter = new ChatArrayAdapter(this, messages);
		setListAdapter(adapter);
		getListView().setSelection(messages.size()-1);
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					while (true){
					synchronized (this) {
						wait(3000);

						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								ChatsMethods cm=new ChatsMethods();
								try {
									messages = cm.getConversation(token, lInbox.getIdUserSender(), user_id);
								} catch (ClientProtocolException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (JDOMException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								adapter = new ChatArrayAdapter(getApplicationContext(), messages);
								setListAdapter(adapter);
								getListView().setSelection(messages.size()-1);
								
							}
						});

					}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			};
		};
		thread.start();
	}
	public void sendMessage(View v) throws ClientProtocolException, IOException
	{
		//boolean conectstatus = Connect();
		String newMessage = text.getText().toString();//.toString().trim(); 
		if(newMessage.length() > 0)
		{
			text.setText("");
			String user_name=null;
			SharedPreferences sp = this.getSharedPreferences("userpreferences", Context.MODE_PRIVATE);
			if(sp.contains("cookie_session")){
				user_name = sp.getString("user_name", null);
				token = sp.getString("token_id", null);
				user_id=sp.getString("user_id", null);}
			ChatsMethods cm=new ChatsMethods();
			cm.createMessage(token, idDestino, newMessage);
			messages.add(new Message(newMessage, true));
			adapter.notifyDataSetChanged();
			getListView().setSelection(messages.size()-1);
			
			/*oos = new ObjectOutputStream(miCliente.getOutputStream());
			oos.writeObject(newMessage);
			miCliente.close();*/
			//creo objeto mensaje
			
			
		}
	}
	
	/*public void recieveMessage(){
try {
			
			// creamos server socket
			skServidor = new ServerSocket(PORT_S);
			System.out.println("Escuchando el puerto " + PUERTO);
			System.out.println("En Espera....");

			TimeStamp = new java.util.Date().toString();

			try {
				// Creamos socket para manejar conexion con cliente
				miCliente = skServidor.accept(); // esperamos al cliente
				// una vez q se conecto obtenemos la ip
				ip_s = miCliente.getInetAddress().toString();
				System.out.println("[" + TimeStamp + "] Conectado al cliente "
						+ "IP:" + IP_client);

				while (true) {
					// Manejamos flujo de Entrada
					ObjectInputStream ois = new ObjectInputStream(
							miCliente.getInputStream());
					// Cremos un Objeto con lo recibido del cliente
					Object aux = ois.readObject();// leemos objeto

						String mensajerecibido =  aux.toString();
						messages.add(new Message(mensajerecibido, true));
						
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("[" + TimeStamp + "] Error ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[" + TimeStamp + "] Error ");
		}
	
	}*/
	
	/*private class SendMessage extends AsyncTask<Void, String, String>
	{
		@Override
		protected String doInBackground(Void... params) {
			try {
				Thread.sleep(5000); //simulate a network call
				
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			ChatsMethods cm=new ChatsMethods();
			try {
				messages = cm.getConversation(token, lInbox.getIdUserSender(), user_id);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			adapter.notifyDataSetChanged();
			getListView().setSelection(messages.size()-1);
			return null;
			
		}
	}/*
		@Override
		public void onProgressUpdate(String... v) {
			
			if(messages.get(messages.size()-1).isStatusMessage)//check wether we have already added a status message
			{
				messages.get(messages.size()-1).setMessage(v[0]); //update the status for that
				adapter.notifyDataSetChanged(); 
				getListView().setSelection(messages.size()-1);
			}
			else{
				addNewMessage(new Message(true,v[0])); //add new message, if there is no existing status message
			}
		}
		@Override
		protected void onPostExecute(String text) {
			if(messages.get(messages.size()-1).isStatusMessage)//check if there is any status message, now remove it.
			{
				messages.remove(messages.size()-1);
			}
			
			addNewMessage(new Message(text, false)); // add the orignal message from server.
		}
		

	}
	void addNewMessage(Message m)
	{
		messages.add(m);
		adapter.notifyDataSetChanged();
		getListView().setSelection(messages.size()-1);
	}*/
	/*public boolean Connect() {
		//Obtengo datos ingresados en campos
		String IP = ip_d;//PONER IP
		int PORT = PORT_S; //PONER PUERTO

		try {//creamos sockets con los valores anteriores
			miCliente = new Socket(IP, PORT);
			//si nos conectamos
			if (miCliente.isConnected() == true) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			//Si hubo algun error mostrmos error
			Log.e("Error connect()", "" + e);
			return false;
		}
	}*/
	
	
	
}