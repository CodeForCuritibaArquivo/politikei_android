package politikei.com.br.politikei;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityLogin extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button entrarBt = (Button) findViewById(R.id.login_entrar);
        entrarBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EditText loginEt = (EditText) findViewById(R.id.login_usuario);
                    EditText senhaEt = (EditText) findViewById(R.id.login_senha);

                    String login = loginEt.getText().toString();
                    String senha = senhaEt.getText().toString();

                    JSONObject jsonLogin = new JSONObject();
                    jsonLogin.put("email", login);
                    jsonLogin.put("password", senha);

                    new HttpRequestExecuter(jsonLogin) {
                        @Override
                        protected void onPostExecute(String result) {
                            Log.i(Utils.tag, result + "");
                            if(result != null)
                            {
                                try {
                                    JSONObject json = new JSONObject(result);

                                    String token = json.getString("token");
                                    if(token != null)
                                    {
                                        BusinessLogic.getInstance().setAccessToken(token);
                                        setResult(Activity.RESULT_OK);
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(ActivityLogin.this, "Falha ao autenticar", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                Toast.makeText(ActivityLogin.this, "Falha ao autenticar", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }.execute("https://politikei-api.herokuapp.com/api/v1/auth", "POST");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
