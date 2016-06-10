package inscope.inscope;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Scope extends android.support.v4.app.Fragment {

    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter myRecyclerAdapter;
    private List<Scopeinfo> data;
    public Scope() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_scope, container, false);

        mRecyclerView = (RecyclerView) layout.findViewById(R.id.my_recycler_view);
        myRecyclerAdapter = new MyRecyclerAdapter(getActivity(),getData());
        mRecyclerView.setAdapter(myRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return layout;
    }

    public List<Scopeinfo> getData(){
        data = new ArrayList<>();
        final int[] icons = {R.drawable.circle1,R.drawable.circle2,R.drawable.circle3,R.drawable.circle4,R.drawable.circle5,R.drawable.circle6,R.drawable.circle7};
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = "http://10.0.2.2/functions/return_locations.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if (response.length() > 0) {
                        data.clear();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            Scopeinfo info = new Scopeinfo();
                            if (!jsonObject.isNull("name")) info.title = jsonObject.getString("name");
                            if(!jsonObject.isNull("vicinity")) info.subTitle = jsonObject.getString("vicinity");
                            if (!jsonObject.isNull("mood")) info.mood = jsonObject.getString("mood");
                            if (!jsonObject.isNull("local_phone_number")) info.phoneNumber = jsonObject.getString("local_phone_number");
                            info.iconId = icons[i%icons.length];
                            data.add(info);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("database", "no locations returned");
            }
        });

        requestQueue.add(jsonArrayRequest);
        return data;

    }

}
