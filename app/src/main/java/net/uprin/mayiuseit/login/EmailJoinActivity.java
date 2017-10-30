package net.uprin.mayiuseit.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import net.uprin.mayiuseit.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class EmailJoinActivity extends AppCompatActivity {

    public static final String UPRINKEY = "$2Y$10$IMT5G4U9FP1KDOM5S7EPWU/08FFNFZMME/9JF4AQ99P";
    EditText et_id, et_pw, et_pw_chk;
    String sId, sPw, sPw_chk;

    final Context context = this; //이거 onPostExecute부분에서 필요한 것이었음

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        et_id = (EditText) findViewById(R.id.et_Id);
        et_pw = (EditText) findViewById(R.id.et_Password);
        et_pw_chk = (EditText) findViewById(R.id.et_Password_chk);

        sId = et_id.getText().toString();
        sPw = et_pw.getText().toString();
        sPw_chk = et_pw_chk.getText().toString();
    }

    public void bt_Join(View view){
        sId = et_id.getText().toString();
        sPw = et_pw.getText().toString();
        sPw_chk = et_pw_chk.getText().toString();

        if(sPw.equals(sPw_chk))
        {
            registDB rdb = new registDB();
            rdb.execute();
        }
        else
        {
            /* 패스워드 불일치*/
            Toast.makeText(getApplicationContext(),"패스워드가 불일치합니다",Toast.LENGTH_LONG).show();

        }
    }

    public class registDB extends AsyncTask<Void, Integer, Void> {
        String data         = "";

        @Override
        protected Void doInBackground(Void... voids) {
            String param = "uprinkey="+UPRINKEY+"&u_id="+sId+"&u_pw="+sPw+"";
            try{
                URL url = new URL(
                        "http://dev.uprin.net/mayiuseit/join.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

            /* 안드로이드 -> 서버 파라메터값 전달 */
                OutputStream outs = conn.getOutputStream();
                outs.write(param.getBytes("UTF-8"));
                outs.flush();
                outs.close();

            /* 서버 -> 안드로이드 파라메터값 전달 */
                InputStream is      = null;
                BufferedReader in   = null;


                is  = conn.getInputStream();
                in  = new BufferedReader(new InputStreamReader(is), 8 * 1024);
                String line = null;
                StringBuffer buff   = new StringBuffer();
                while ( ( line = in.readLine() ) != null )
                {
                    buff.append(line + "\n");
                }

                data    = buff.toString().trim();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);//이거 넣어줘야 builder을 사용가능
            Log.e("RECV DATA",data);

            if(data.equals("0"))
            {
                Log.e("RESULT","성공적으로 처리되었습니다!");
                alertBuilder
                        .setTitle("알림")
                        .setMessage("성공적으로 등록되었습니다!")
                        .setCancelable(true)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                AlertDialog dialog = alertBuilder.create();
                dialog.show();
            } else if (data.equals("1062")){
                Log.e("RESULT","에러 발생! ERRCODE = " + data);
                Toast.makeText(getApplicationContext(),"동일한 ID가 존재합니다",Toast.LENGTH_LONG).show();
            }else
            {
                Log.e("RESULT","에러 발생! ERRCODE = " + data);
                Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();
            }
        }

    }

}


