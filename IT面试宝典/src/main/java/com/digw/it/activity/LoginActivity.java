package com.digw.it.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.digw.it.Constant;
import com.digw.it.ITApplication;
import com.digw.it.R;
import com.digw.it.entity.User;
import com.digw.it.util.ThreadManager;
import com.digw.it.util.net.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class LoginActivity extends BaseActivity implements TextureView.SurfaceTextureListener, View.OnFocusChangeListener {
    private TextureView textureView;
    private MediaPlayer mediaPlayer;
    private Surface mSurface;

    private View bgView;
    private TextInputLayout tilUserName, tilPassWord;
    private TextView tvRegister;
    private Button btnLogin;
    private ProgressDialog pd;
    private Thread thread = new Thread() {
        @Override
        public void run() {
            try {

                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(getAssets().openFd("bg_login.mp4").getFileDescriptor(), getAssets().openFd("bg_login.mp4").getStartOffset(), getAssets().openFd("bg_login.mp4").getLength());
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

    private void cleanEditTextError(){
        tilUserName.setError(null);
        tilPassWord.setError(null);
    }

    private boolean checkFrom() {
        String emailReg="^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        String email=tilUserName.getEditText().getText().toString().trim();
        String password=tilPassWord.getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(email)){
            tilUserName.setError("电子邮件不能为空");
            return false;
        }
        Pattern p = Pattern.compile(emailReg);
        Matcher matcher=p.matcher(email);
        if (!matcher.matches()){
            tilUserName.setError("电子邮件格式错误");
            return false;
        }
        if (TextUtils.isEmpty(password)){
            tilPassWord.setError("密码不能为空");
            return false;
        }
        if (!(password.length()>=6 && password.length() <= 16)){
            tilPassWord.setError("密码必须大于等于6位并小于等于17位");
            return false;
        }
        return true;
    }

    @Override
    public void viewClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                startActivity(RegisterActivity.class);
                break;
            case R.id.login_btn:
                cleanEditTextError();
                if (checkFrom()){
                    UserLoginAsyncTask m = new UserLoginAsyncTask();
                    m.execute(tilUserName.getEditText().getText().toString(), tilPassWord.getEditText().getText().toString());
                }
                break;
        }
    }

    @Override
    public void initParams(Bundle bundle) {
        setAllowFullScreen(true);
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
        textureView = $(R.id.texture_view);
        bgView = $(R.id.bg_view);
        tilUserName = $(R.id.login_username);
        tilPassWord = $(R.id.login_password);
        btnLogin = $(R.id.login_btn);
        tvRegister = $(R.id.tv_register);
        pd = new ProgressDialog(this);
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
        /*tilUserName.getEditText().setOnFocusChangeListener(this);
        tilPassWord.getEditText().setOnFocusChangeListener(this);*/
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mSurface = new Surface(surface);
        ThreadManager.getPoolProxy().execute(thread);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mSurface = null;
        surface = null;
        mediaPlayer.stop();
        mediaPlayer.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId()==tilUserName.getEditText().getId()&&hasFocus){
            tilUserName.setError(null);
        }else if (v.getId()==tilPassWord.getEditText().getId()&&hasFocus){
            tilPassWord.setError(null);
        }
    }

    private class UserLoginAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            JSONObject userJson;
            FormBody.Builder builder = new FormBody.Builder();
            builder.add(Constant.LOGIN_REQUEST_KEY_USERNAME, params[0]);
            builder.add(Constant.LOGIN_REQUEST_KEY_PASSWORD, params[1]);
            RequestBody requestBody = builder.build();
            Request request = new Request.Builder().url(Constant.URL_POST_USER_LOGIN).post(requestBody).build();
            try {
                result = HttpUtil.okHttpClient.newCall(request).execute().body().string();
                userJson = new JSONObject(result);
                userJson = userJson.getJSONObject("result");
                User user = new User();
                user.setId(userJson.getInt(Constant.UserTable.id));
                user.setType(userJson.getInt(Constant.UserTable.type));
                user.setName(userJson.getString(Constant.UserTable.name));
                user.setEmail(userJson.getString(Constant.UserTable.email));
                user.setSex(userJson.getString(Constant.UserTable.sex));
                user.setPassword(userJson.getString(Constant.UserTable.password));
                ITApplication.getInstance().setCurrUser(user);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                pd.dismiss();
                JSONObject obj = new JSONObject(s);
                if (obj.getBoolean("state")) {
                    Snackbar.make(bgView, "登录成功,3秒后返回上一页", Snackbar.LENGTH_SHORT).show();
                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LoginActivity.this.finish();
                        }
                    }, 2500);
                } else {
                    Snackbar.make(bgView, "登录失败", Snackbar.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
