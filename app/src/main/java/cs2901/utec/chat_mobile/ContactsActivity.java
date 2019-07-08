package cs2901.utec.chat_mobile;


import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class ContactsActivity extends AppCompatActivity {
    private String url = "http://10.0.2.2:8080/users";
    private String url_current = "http://10.0.2.2:8080/current";

    RecyclerView rviewList;
    RecyclerView.Adapter mAdapter;

    /*private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Contacts> contactsList;
    private String currentUsername;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        rviewList = findViewById(R.id.main_recycler_view);
        setTitle("@"+getIntent().getExtras().get("username").toString());
        /*contactsList = new ArrayList<>();
        adapter = new ContactsAdapter(getApplicationContext(),contactsList);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(rviewList.getContext(), linearLayoutManager.getOrientation());

        rviewList.setHasFixedSize(true);
        rviewList.setLayoutManager(linearLayoutManager);
        rviewList.addItemDecoration(dividerItemDecoration);
        rviewList.setAdapter(adapter);*/
        //currentUser();
        //getData();
    }

    @Override
    protected void onResume(){
        super.onResume();
        rviewList.setLayoutManager(new LinearLayoutManager(this));
        getUsers();
    }

    public Activity getActivity(){
        return this;
    }

    public void getUsers(){
        String url = "http://10.0.2.2:8080/users";
        RequestQueue queue = Volley.newRequestQueue(this);
        Map<String, String> params = new HashMap();
        JSONObject parameters = new JSONObject(params);
        final String userId = getIntent().getExtras().get("user_id").toString();
        //JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, parameters, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("data");
                            mAdapter = new ChatAdapter(data, getActivity(), userId);
                            rviewList.setAdapter(mAdapter);
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();

            }
        });
        queue.add(jsonObjectRequest);
    }

    /*private void currentUser(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url_current, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){
                try{
                    currentUsername = response.getString("username");
                    setTitle(currentUsername);
                    System.out.println(currentUsername);
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
    private void getData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0; i < response.length();i++){
                    try{
                        JSONObject jsonObject = response.getJSONObject(i);
                        Contacts contact = new Contacts();
                        contact.setFullname(jsonObject.getString("fullname"));
                        contact.setId(jsonObject.getInt("id"));
                        contact.setName(jsonObject.getString("name"));
                        contact.setPassword(jsonObject.getString("password"));
                        contact.setUsername(jsonObject.getString("username"));
                        contactsList.add(contact);
                    } catch (JSONException e){
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
    */
}
