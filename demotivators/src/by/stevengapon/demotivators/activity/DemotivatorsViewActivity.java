package by.stevengapon.demotivators.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
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
	private BroadcastReceiver broadcastReceiver1;
	private HashMap<String, Object>  data ;
	private View header ;
	

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demotivators_view);
		searchView();
		
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
	
	
	private void searchView(){
		lvComments = (ListView)findViewById(R.id.lvComments);
		
		btnPrev = (Button)findViewById(R.id.btnPrev);
		btnNext = (Button)findViewById(R.id.btnNext);
		btnPrev.setOnClickListener(this);
		btnNext.setOnClickListener(this);
		
	}


	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		
		case R.id.btnPrev :
			
			break;
		
		case R.id.btnNext :
			
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
		
		Bitmap image ;
	    String url , name , id , count_comments , rating ;
	    
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
	
		Intent  qw = new Intent(this, WidgetService.class);
		qw.setAction(Constant.COMMENTS_PREV);
		startService(qw);
		
		
	}
	
	public void viewComments(Intent intent){
	
		ArrayList<HashMap<String, Object>>  r  = new ArrayList<HashMap<String,Object>>();
		
		
		String [] t = new  String[10];
		HashMap<String, Object>[]  e  ;
		r = (ArrayList<HashMap<String, Object>>) intent.getExtras().get("123");
		ArrayList<HashMap<String, Object>>  d = new ArrayList<HashMap<String,Object>>(r);
		d.remove(d.size()-1);
		
		Log.d("888", d.size()+"");
		
		lvComments.addHeaderView(header, "3", false);
		
		ListAdapter adapter = new ListAdapter(this, R.layout.list, d);
		
		lvComments.setAdapter(adapter);
		
		
		
		
		
		
	}
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		
		Log.d("444", "onPause");
		
		
		
		super.onPause();
	}

	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
	}
	
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d("444", "onStop");
		unregisterReceiver(broadcastReceiver);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("444", "onDestroy");
	}
}
