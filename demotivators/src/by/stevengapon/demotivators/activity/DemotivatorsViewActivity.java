package by.stevengapon.demotivators.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import by.stevengapon.demotivators.R;
import by.stevengapon.demotivators.adapter.ListAdapter;
import by.stevengapon.demotivators.constant.Constant;
import by.stevengapon.demotivators.service.WidgetService;

public class DemotivatorsViewActivity extends Activity  implements OnClickListener{

	private Button btnPrev , btnNext ; 
	private ListView lvComments;
	private ImageView ivDemPhoto ;
	private TextView tvName , tvRating ;
	private HashMap<String, Object>  data ;
	private View header ;
	private Bitmap image ;
	private String url , name , id , count_comments , rating ;
	private boolean  has_prev , has_next ; 
	private String next_link , prev_link; 
	

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demotivators_view);
		initialization();
		
		IntentFilter ifilter = new IntentFilter();
		ifilter.addAction(Constant.COMMENTS_PREV);
		
		registerReceiver(broadcastReceiver, ifilter);
		
		header =  getLayoutInflater().inflate(R.layout.header , null);
		tvName = (TextView)header.findViewById(R.id.tvName);
		ivDemPhoto  = (ImageView)header.findViewById(R.id.ivDemPhoto);
		tvRating = (TextView)header.findViewById(R.id.tvRating);
		data = new HashMap<String, Object>();
		Intent intent = getIntent();
		if(intent.getAction().equalsIgnoreCase(Constant.VIEW_DEMOTIVATOR)  ){
			if(intent.getExtras().getSerializable("data")!= null){
			viewDemotivator(intent);
			}	
		}
		

	
	}
	
	
	private void  initialization(){
		lvComments = (ListView)findViewById(R.id.lvComments);
		btnPrev = (Button)findViewById(R.id.btnPrev);
		btnNext = (Button)findViewById(R.id.btnNext);
		btnPrev.setOnClickListener(this);
		btnNext.setOnClickListener(this);

	}


	@Override
	public void onClick(View v) {
		String url ;
		Intent  sintent ;
		switch(v.getId()){
		
		case R.id.btnPrev :
			
			url = "http://rtc.demotivators.to/comments/?chan=" +id +"&op=prev&cur=" + prev_link;
			
			sintent = new Intent(this, WidgetService.class);
			sintent.setAction(Constant.COMMENTS_PREV);
			sintent.putExtra("url_comment", url );
			startService(sintent);
			
			break;
		
		case R.id.btnNext :
			
			url = "http://rtc.demotivators.to/comments/?chan=" +id +"&op=next&cur=" + next_link;
			
			sintent = new Intent(this, WidgetService.class);
			sintent.setAction(Constant.COMMENTS_PREV);
			sintent.putExtra("url_comment", url );
			startService(sintent);
			
			break ;
		}
		
	}
	
	
	
	
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			
			
			
			if(intent.getAction().equalsIgnoreCase(Constant.COMMENTS_NEXT)){
				
				
				
				
				
			}
			
			if(intent.getAction().equalsIgnoreCase(Constant.COMMENTS_PREV)){
				Log.d("777", "PREVVVV");
				if(intent.getExtras().get("123")!= null){
					viewComments(intent);
				}
				
			}
			

		}
	};
	
	
	
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
		
		
		
		
		super.onResume();
	}
	
	
	@SuppressWarnings("unchecked")
	public void viewDemotivator(Intent intent){
		
	   
	    
	    data = (HashMap<String, Object>) intent.getExtras().getSerializable("data");
	    image =  (Bitmap) data.get("image");
	    id = data.get("id").toString();
	    name = data.get("name").toString();
	    url = data.get("url").toString();
	    count_comments = data.get("count_comments").toString();
	    rating = data.get("rating").toString();
	    
		ivDemPhoto.setImageBitmap(image);
		tvName.setText(name);
		tvRating.setText(rating);
		
		lvComments.addHeaderView(header, "3", false);
	
		Intent  sintent = new Intent(this, WidgetService.class);
		sintent.setAction(Constant.COMMENTS_PREV);
		sintent.putExtra("url_comment", "http://rtc.demotivators.to/comments/?chan=" +id);
		startService(sintent);
		
		
	}
	
	@SuppressWarnings("unchecked")
	public void viewComments(Intent intent){
	
		ArrayList<HashMap<String, Object>>  list_comments  = new ArrayList<HashMap<String,Object>>();
		
		
		
		HashMap<String, Object> pager  ;
		list_comments = (ArrayList<HashMap<String, Object>>) intent.getExtras().get("123");
		if(list_comments.size() > 1){
			
			Log.d("999", "LENGTH=  " +list_comments.size());
			pager =  list_comments.remove(list_comments.size() -1);
			Log.d("999", "LENGTH=  " +list_comments.size());
			has_next = Boolean.valueOf(pager.get("hasNext").toString());
			has_prev = Boolean.valueOf(pager.get("hasPrev").toString());
			visible_button(has_next, has_prev);
			Log.d("999", "LENGTH=  " +list_comments.size());
			next_link = list_comments.get(list_comments.size()-1).get("fslug").toString();
			prev_link = list_comments.get(0).get("fslug").toString();
			Log.d("999", "LENGTH=  " +list_comments.size());
			
		}
		
		
		 
		
		
		
		
		ListAdapter adapter = new ListAdapter(this, R.layout.list, list_comments);
		
		lvComments.setAdapter(adapter);
	
		
	}
	
	


	
	public void visible_button(boolean next , boolean prev){
		
		if(next){
			
			btnNext.setVisibility(Button.VISIBLE);
		}
		else{
			
			btnNext.setVisibility(Button.INVISIBLE);
		}
		
		
		if(prev){
			btnPrev.setVisibility(Button.VISIBLE);
		}
		else{
			btnPrev.setVisibility(Button.INVISIBLE);
		}
		
	}
	
	
	
	
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d("444", "onStop");
		unregisterReceiver(broadcastReceiver);
	}
	
}
