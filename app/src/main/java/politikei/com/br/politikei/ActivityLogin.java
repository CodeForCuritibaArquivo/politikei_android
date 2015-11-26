package politikei.com.br.politikei;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {

            JSONObject jsonLogin = new JSONObject();
            jsonLogin.put("email", "murilo.wlima@gmail.com");
            jsonLogin.put("password", "murilo");

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
}
