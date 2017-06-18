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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.digw.it.Constant;
import com.digw.it.ITApplication;
import com.digw.it.R;
import com.digw.it.entity.User;
import com.digw.it.util.JsonUtil;
import com.digw.it.util.ThreadManager;
import com.digw.it.util.net.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.digw.it.Constant.UserTable.email;

public class LoginActivity extends BaseActivity implements TextureView.SurfaceTextureListener {
    private TextureView textureView;
    private MediaPlayer mediaPlayer;
    private Surface mSurface;

    private View bgView;
    private TextInputLayout tilPhone, tilPassWord;
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
        tilPhone.setError(null);
        tilPassWord.setError(null);
    }

    private boolean checkFrom() {
        String phone=tilPhone.getEditText().getText().toString().trim();
        String password=tilPassWord.getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(email)){
            tilPhone.setError("手机号不能为空");
            return false;
        }
        if (TextUtils.isEmpty(password)){
            tilPassWord.setError("密码不能为空");
            return false;
        }
        if (password.length()<6||password.length()>21){
            tilPassWord.setError("密码必须为6到20位字符");
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
                    m.execute(
                            tilPhone.getEditText().getText().toString(),
                            tilPassWord.getEditText().getText().toString()
                    );
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
        tilPhone = $(R.id.login_phone);
        tilPassWord = $(R.id.login_password);
        btnLogin = $(R.id.login_btn);
        tvRegister = $(R.id.tv_register);
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("请稍后...");
    }

    @Override
    public void setListener() {
        textureView.setSurfaceTextureListener(this);
        tvRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tilPhone.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tilPhone.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        tilPassWord.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tilPassWord.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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

    private class UserLoginAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            Map<String,String> map=new HashMap<>();
            map.put(Constant.LOGIN_REQUEST_KEY_ACCOUNT, params[0]);
            map.put(Constant.LOGIN_REQUEST_KEY_PASSWORD, params[1]);
            /*try {
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
            }*/
            return HttpUtil.doPostExecute(Constant.URL_POST_USER_LOGIN,map);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject result = new JSONObject(s);
                if (result.getInt("code")==0) {
                    ITApplication.getInstance().setCurrUser(JsonUtil.jsonToBean(result.getJSONObject("data").toString(), User.class));
                    Snackbar.make(bgView, "登录成功,三秒后返回上一页", Snackbar.LENGTH_SHORT).show();
                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LoginActivity.this.finish();
                        }
                    }, 2500);
                } else {
                    Snackbar.make(bgView,result.getString("msg"),Snackbar.LENGTH_SHORT).show();
                    tilPhone.setError(" ");
                    tilPassWord.setError(" ");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                pd.dismiss();
            }
        }
    }
}
