package cs2901.utec.chat_mobile;


import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.util.Log;
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

public class ContactsActivity extends AppCompatActivity {
    private String url = "http://10.0.2.2:8080/users";
    private RecyclerView rviewList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Contacts> contactsList;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        rviewList = findViewById(R.id.main_list);
        contactsList = new ArrayList<>();
        adapter = new ContactsAdapter(getApplicationContext(),contactsList);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(rviewList.getContext(), linearLayoutManager.getOrientation());

        rviewList.setHasFixedSize(true);
        rviewList.setLayoutManager(linearLayoutManager);
        rviewList.addItemDecoration(dividerItemDecoration);
        rviewList.setAdapter(adapter);

        getData();
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

}
