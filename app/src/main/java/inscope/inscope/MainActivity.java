package inscope.inscope;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText password, email;
    private Button login, register;
    private RequestQueue requestQueue;
    private String registerUrl = "http://10.0.2.2/user/register.php";
    private String loginUrl = "http://10.0.2.2/user/login.php";
    private StringRequest request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        Log.d("test", "before login");
        setContentView(R.layout.login_screen);
        Log.d("test", "set view to login_screen");
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.bg);
        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.bg);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getApplicationContext().getResources(),bitmap);
        linearLayout.setBackgroundDrawable(bitmapDrawable);
        login();
        //test();

    }

    private void login() {
        email = (EditText) findViewById(R.id.emailfield);
        password = (EditText) findViewById(R.id.passwordfield);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "FOR DEBUG ONLY", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Main.class)); //FOR DEBUG ONLY

                request = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("success")) {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                email.setText("");
                                password.setText("");
                                Log.d("test", "Going to Main.class");
                                startActivity(new Intent(MainActivity.this, Main.class));
                                //setContentView(R.layout.activity_main);
                            } else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("email", email.getText().toString());
                        parameters.put("password", password.getText().toString());
                        return parameters;
                    }
                };

                requestQueue.add(request);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request = new StringRequest(Request.Method.POST, registerUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("success")) {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                email.setText("");
                                password.setText("");
                                startActivity(new Intent(getApplicationContext(), Main.class));
                                //setContentView(R.layout.welcome_activity);
                            } else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("email", email.getText().toString());
                        parameters.put("password", password.getText().toString());
                        return parameters;
                    }
                };

                requestQueue.add(request);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
