package com.digw.it.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.MenuItem;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.digw.it.Constant;
import com.digw.it.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class LoginActivity extends BaseActivity implements TextureView.SurfaceTextureListener {
    private TextureView textureView;
    private MediaPlayer mediaPlayer;
    private Surface mSurface;

    private View bgView;
    private TextInputLayout tilUserName,tilPassWord;
    private TextView tvRegister;
    private Button btnLogin;
    private ProgressDialog pd;
    private Thread thread=new Thread(){
        @Override
        public void run() {
            try {

                mediaPlayer=new MediaPlayer();
                mediaPlayer.setDataSource(getAssets().openFd("bg_login.mp4").getFileDescriptor(),getAssets().openFd("bg_login.mp4").getStartOffset(),getAssets().openFd("bg_login.mp4").getLength());
                mediaPlayer.setSurface(mSurface);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setLooping(true);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void viewClick(View v) {
        switch (v.getId()){
            case R.id.tv_register:
                startActivity(RegisterActivity.class);
                break;
            case R.id.login_btn:
                /*pd.show();
                Map<String,String> map=new HashMap<>();
                map.put("username",tilUserName.getEditText().getText().toString());
                map.put("password",tilPassWord.getEditText().getText().toString());
                HttpUtil.doPost(LoginActivity.this,Constant.URL_POST_USER_LOGIN, map, new NetListener.HttpCallbackListener() {
                    @Override
                    public void onFinish(Response response) {
                        try {
                            JSONObject jsonObject= new JSONObject(response.body().string());
                            if (jsonObject.getBoolean("state")){
                                ITApplication.getInstance().setCurrUser(JsonUtil.jsonToBean(jsonObject.getString("result"), User.class));
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                            }
                            pd.dismiss();
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        pd.dismiss();
                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    }
                });*/
                MyAsyncTask m=new MyAsyncTask();
                m.execute(tilUserName.getEditText().getText().toString(),tilPassWord.getEditText().getText().toString());
                break;
        }
    }

    @Override
    public void initParams(Bundle bundle) {
        setAllowFullScreen(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(View view) {
        textureView=$(R.id.texture_view);
        bgView=$(R.id.bg_view);
        tilUserName=$(R.id.login_username);
        tilPassWord=$(R.id.login_password);
        btnLogin=$(R.id.login_btn);
        tvRegister=$(R.id.tv_register);
        pd=new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("请稍后");
    }

    @Override
    public void setListener() {
        textureView.setSurfaceTextureListener(this);
        tvRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mSurface=new Surface(surface);
        thread.start();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mSurface=null;
        surface=null;
        mediaPlayer.stop();
        mediaPlayer.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class MyAsyncTask extends AsyncTask<String,Integer,String>{

        @Override
        protected void onPreExecute() {
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String result="";
            OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS).build();
            Map<String,String> map=new HashMap<>();
            map.put("username",params[0]);
            map.put("password",params[1]);
            FormBody.Builder builder = new FormBody.Builder();
            if (null != map) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    builder.add(entry.getKey(), entry.getValue());
                }
            }
            RequestBody requestBody = builder.build();
            Request request = new Request.Builder().url(Constant.URL_POST_USER_LOGIN).post(requestBody).build();
            try {
                result=okHttpClient.newCall(request).execute().body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            Toast.makeText(LoginActivity.this, ""+s, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }
    }
}
