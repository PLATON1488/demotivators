package by.stevengapon.demotivators.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import by.stevengapon.demotivators.R;
import by.stevengapon.demotivators.parser.Parser;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<HashMap<String, Object>> {
 
	private List<HashMap<String, Object>> data;
	private LayoutInflater mInflater;
	private String text , to , slug , fslug , name ;
	private Bitmap image ;
    

	public ListAdapter(Context context,  int textViewResourceId,
			List<HashMap<String, Object>> objects) {
		super(context, textViewResourceId, objects);
		
		data = objects;
		Log.d("999", data.size()+"");
		mInflater = LayoutInflater.from(context);
		
		// TODO Auto-generated constructor stub
	}

 

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		Log.d("777", "ïïïïïïïïïïïïï");
		 if (convertView == null) {
	            convertView = mInflater.inflate (R.layout.list, parent, false); 
	         }
		 
		 
		    String  slug , name , text;
		    Bitmap image ;
		    
		    image = (Bitmap) data.get(position).get("image");
		    name = data.get(position).get("name").toString();
		    text = data.get(position).get("text").toString();
		    slug = data.get(position).get("slug").toString();
		 
		   
		   int margin_left = Parser.count_symbol(slug) ;
		   Log.d("999" , "margin =  " + margin_left);
           
		   
		   convertView.setPadding( (8*margin_left), 0, 0, 0);
		   
		   ImageView imageView = (ImageView)convertView.findViewById(R.id.ivUserPhoto);
		   TextView tvName = (TextView) convertView.findViewById(R.id.tvNameUser);
		   TextView tvComment = (TextView) convertView.findViewById(R.id.tvComment);
		   
		   imageView.setImageBitmap(image); 
	       tvName.setText(name);
	       tvComment.setText(text);

	       
    	return convertView;
    	
    	
    	
    	
    }
	

}
