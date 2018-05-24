package com.taruc.onlinedatabasefile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by taruc on 24/5/2018.
 */

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextID, editTextName, editTextAge, editTextMarried;
    private Button buttonSave, buttonReset;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editTextID = (EditText) findViewById(R.id.editTextID);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextMarried = (EditText) findViewById(R.id.editTextMarried);

        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonReset = (Button) findViewById(R.id.buttonReset);

        buttonSave.setOnClickListener(this);
        buttonReset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
int id= v.getId();
switch(id){
    case R.id.buttonSave:
        save();
        break;
    case  R.id.buttonReset:
        reset();
}

    }

    public void reset() {
        editTextID.setText("");
        editTextName.setText("");
        editTextAge.setText("");
        editTextMarried.setText("");

    }

    public void save(){

        //grab all the input data
       String id = editTextID.getText().toString();
        String name = editTextName.getText().toString();
        String age = editTextAge.getText().toString();
        String married = editTextMarried.getText().toString();

        User user = new User(id, name, age, married);

        makeServiceCall(this, "https://amyling.000webhostapp.com/insert_record.php",user);



    }
    public void makeServiceCall(Context context, String url, final User user){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String, String> getParams(){

                Map<String, String> params = new HashMap<>();
                params.put("id", user.getId().trim());
                params.put("name", user.getName().trim());
                params.put("age", user.getAge().trim());
                params.put("married", user.getMarried().trim());
                return params;
            }

            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }

        };

requestQueue.add(stringRequest);

    }


}

