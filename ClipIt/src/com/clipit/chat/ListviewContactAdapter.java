package com.clipit.chat;

import java.util.ArrayList;

import com.example.clipit.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;

public class ListviewContactAdapter extends BaseAdapter {
	private static ArrayList<ListInbox> listContact;

	private LayoutInflater mInflater;

	public ListviewContactAdapter(Context ctx, ArrayList<ListInbox> results){
	    listContact = results;
	    mInflater = LayoutInflater.from(ctx);
	}

	@Override
	public int getCount() {
	    // TODO Auto-generated method stub
	    return listContact.size();
	}

	@Override
	public Object getItem(int arg0) {
	    // TODO Auto-generated method stub
	    return listContact.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
	    // TODO Auto-generated method stub
	    return arg0;
	}


	public View getView(int position, View convertView, ViewGroup parent) {
	    // TODO Auto-generated method stub
	    ViewHolder holder;
	    if(convertView == null){
	        convertView = mInflater.inflate(R.layout.contact_item, null);
	        holder = new ViewHolder();
	        holder.txtname = (TextView) convertView.findViewById(R.id.item_name);          
	        holder.txtphoto = (ImageView) convertView.findViewById(R.id.item_photo);
	        holder.txtlastMessage=(TextView) convertView.findViewById(R.id.item_lastMessage);  

	        convertView.setTag(holder);
	    } else {
	        holder = (ViewHolder) convertView.getTag();
	    }

	    holder.txtname.setText(listContact.get(position).getName());
	    holder.txtphoto.setImageResource(listContact.get(position).getImagen());
	    holder.txtlastMessage.setText(listContact.get(position).getLastMessage());

	    return convertView;
	}

	static class ViewHolder{
	    TextView txtname,txtlastMessage;
	    ImageView txtphoto;
	}
}
