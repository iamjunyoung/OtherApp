package com.bbeaggoo.otherapp;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class JSONUtil {
    private JSONObject robotPOIResourceObject = null;
    public Context context = null;

    public JSONUtil(Context context) {
        this.context = context;
    }

    public void jsonParsing(String json) {
        try{
            JSONObject jsonObject = new JSONObject(json);

            JSONObject payloadObject = jsonObject.getJSONObject("payload");
            JSONObject floorsObject = payloadObject.getJSONObject("floors");
            JSONObject SNUH_SEOUL_DH_B2_Object = floorsObject.getJSONObject("SNUH_SEOUL_DH_B2");
            JSONObject poiDataObject = SNUH_SEOUL_DH_B2_Object.getJSONObject("poiData");
            Log.d("hey", poiDataObject.toString()); // [] 배열로 쭉 나옴

            // toJSONArray() 뭐하는애인가?

            Log.d("hey", "------------------------" );
            Log.d("hey", poiDataObject.names().toString()); // [] 배열로 쭉 나옴
            JSONArray array = poiDataObject.toJSONArray(poiDataObject.names());
            Log.d("hey", "------------------------" );
            Log.d("hey", "" + array);
            Log.d("hey", "------------------------" );

            // poiDataObject 의 key값을 일일히 입력하지 말고 뽑아내자.
            Iterator i = poiDataObject.keys();
            while (i.hasNext()) {
                String b = i.next().toString();
                Log.d("Hey", "" + b);
            }
            //235개가 나와야 하는데 왜 237개가 나오는가?

            /*
            for(int i=0; i < SNUH_SEOUL_DH_B2_Object.length(); i++)
            {
                JSONObject poiDataObject = SNUH_SEOUL_DH_B2_Object.getJSONObject("poiData");


                Movie movie = new Movie();

                movie.setTitle(movieObject.getString("title"));
                movie.setGrade(movieObject.getString("grade"));
                movie.setCategory(movieObject.getString("category"));

                movieList.add(movie);

            }
            */
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getJsonString()
    {
        String json = "";

        try {
            InputStream is = context.getAssets().open("BuildingDB_SeoulDh_B2F_20200130.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return json;
    }
}
