package by.stevengapon.demotivators.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;



public class JSONObjectWrapper implements Parcelable {
    private static final String TAG = JSONObjectWrapper.class.getSimpleName();
    private JSONObject jo;
 
    public JSONObjectWrapper() {
        jo = new JSONObject();
    }
 
    public JSONObjectWrapper(JSONObject object) {
        jo = object;
    }
 
    public JSONObjectWrapper(String json) {
        try {
            jo = new JSONObject(json);
        } catch (JSONException e) {
            Log.e(TAG, "JSON Not Valid (constructor object)", e);
        }
    }
 
    public JSONObjectWrapper(Parcel source) {
        this(source.readString());
    }
 
    protected int getInt(String name) {
        try {
            if (isNull(name)) {
                return 0;
            }
            return jo.getInt(name);
        } catch (JSONException e) {
            Log.e(TAG, "JSON not contain " + name, e);
        }
        return 0;
    }
 
    protected String getString(String name) {
        try {
            if (isNull(name)) {
                return "";
            }
            return jo.getString(name);
        } catch (JSONException e) {
            Log.e(TAG, "JSON not contain " + name, e);
        }
        return "";
    }
 
    public JSONArray getJSONArray(String name) {
        try {
            if (isNull(name)) {
                return null;
            }
            return jo.getJSONArray(name);
        } catch (JSONException e) {
            Log.e(TAG, "JSON not contain " + name, e);
        }
        return null;
    }
 
    public JSONObjectWrapper getJSONObject(String name) {
        if (isNull(name)) {
            return null;
        }
        try {
            return new JSONObjectWrapper(jo.getJSONObject(name).toString());
        } catch (JSONException e) {
            Log.e(TAG, "JSON not contain " + name, e);
        }
        return null;
    }
    
    public JSONObject getJson(){
        return jo;
    }
 
    private boolean isNull(String name) {
        return jo.isNull(name);
    }
 
    public String toString() {
        return jo.toString();
    }
 
    @Override
    public int describeContents() {
        return 0;
    }
 
        
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(toString());
    }
 
    public static final Parcelable.Creator<JSONObjectWrapper> CREATOR = new Creator<JSONObjectWrapper>() {
 
        @Override
        public JSONObjectWrapper createFromParcel(Parcel source) {
            return new JSONObjectWrapper(source);
        }
 
        @Override
        public JSONObjectWrapper[] newArray(int size) {
            return new JSONObjectWrapper[size];
        }
    };
}

