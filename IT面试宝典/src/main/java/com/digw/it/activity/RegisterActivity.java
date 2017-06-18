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
import android.view.MenuItem;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.digw.it.Constant;
import com.digw.it.R;
import com.digw.it.util.ThreadManager;
import com.digw.it.util.net.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends BaseActivity implements TextureView.SurfaceTextureListener {
    private TextureView textureView;
    private MediaPlayer mediaPlayer;
    private Surface mSurface;

    private TextInputLayout tilRegPhone,tilRegCode,tilRegPassWord;
    private TextView tvCode;
    private Button regBtn;
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

    private Thread timeThread=new Thread(){
        @Override
        public void run() {
            for (int i = 59; i >= 0; i--) {
                try {
                    sleep(1000);
                    final int finalI = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (finalI==0){
                                tvCode.setText("获取验证码");
                                tvCode.setOnClickListener(RegisterActivity.this);
                            }else {
                                tvCode.setText(finalI +"秒");
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void cleanEditTextError(){
        tilRegPhone.setError(null);
        tilRegCode.setError(null);
        tilRegPassWord.setError(null);
    }

    private boolean checkFrom(){
        String regPhone=tilRegPhone.getEditText().getText().toString().trim();
        String regCode=tilRegCode.getEditText().getText().toString().trim();
        String regPassword=tilRegPassWord.getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(regPhone)){
            tilRegPhone.setError("手机号不能为空");
            return false;
        }
        if (TextUtils.isEmpty(regCode)){
            tilRegCode.setError("验证码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(regPassword)){
            tilRegPassWord.setError("密码不能为空");
            return false;
        }
        if (regPassword.length()<6||regPassword.length()>21){
            tilRegPassWord.setError("密码必须为6到20位字符");
            return false;
        }
        return true;
    }

    @Override
    public void viewClick(View v) {
        switch (v.getId()){
            case R.id.register_btn:
                cleanEditTextError();
                if (checkFrom()){
                    UserRegisterAsyncTask task=new UserRegisterAsyncTask();
                    task.execute(
                            tilRegPhone.getEditText().getText().toString().trim(),
                            tilRegCode.getEditText().getText().toString().trim(),
                            tilRegPassWord.getEditText().getText().toString().trim()
                    );
                }
                break;
            case R.id.register_code_tv:
                CheckPhoneAsyncTask task=new CheckPhoneAsyncTask();
                task.execute(tilRegPhone.getEditText().getText().toString().trim());
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
        return R.layout.activity_register;
    }

    @Override
    public void initView(View view) {
        textureView=$(R.id.texture_view);
        tilRegPhone=$(R.id.register_phone);
        tilRegCode=$(R.id.register_code);
        tilRegPassWord=$(R.id.register_password);
        tvCode=$(R.id.register_code_tv);
        regBtn=$(R.id.register_btn);
        pd=new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("请稍后");
    }

    @Override
    public void setListener() {
        textureView.setSurfaceTextureListener(this);
        tvCode.setOnClickListener(this);
        regBtn.setOnClickListener(this);
        tilRegPhone.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tilRegPhone.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        tilRegCode.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tilRegCode.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        tilRegPassWord.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tilRegPassWord.setError(null);
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
    protected void onDestroy() {
        super.onDestroy();
        pd.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mSurface=new Surface(surface);
        ThreadManager.getPoolProxy().execute(thread);
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

    private class CheckPhoneAsyncTask extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            Map<String,String> map=new HashMap<>();
            map.put(Constant.CHECK_PHONE_REQUEST_KEY_PHONE,params[0]);
            String result=HttpUtil.doGetExecute(Constant.URL_GET_CHECK_PHONE,map);
            try {
                JSONObject resultJson=new JSONObject(result);
                if (resultJson.getInt("code")==0){
                    HttpUtil.UserAgent=Constant.URL_NOWCODER_USER_AGENT;
                    result=HttpUtil.doPostExecute(Constant.URL_POST_SEND_CHECK_PHONE,map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            try {
                JSONObject resultJson=new JSONObject(s);
                if (resultJson.getInt("code")==0){
                    tvCode.setOnClickListener(null);
                    ThreadManager.getPoolProxy().execute(timeThread);
                    return;
                }
                tilRegPhone.setError(resultJson.getString("msg"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private class UserRegisterAsyncTask extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            Map<String,String> map=new HashMap<>();
            map.put(Constant.REGISTER_REQUEST_KEY_PHONE,strings[0]);
            map.put(Constant.REGISTER_REQUEST_KEY_CODE,strings[1]);
            map.put(Constant.REGISTER_REQUEST_KEY_PASSWORD,strings[2]);
            return HttpUtil.doPostExecute(Constant.URL_POST_USER_REGISTER,map);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject result=new JSONObject(s);
                if (result.getInt("code")==0){
                    Snackbar.make(tvCode,"注册成功,三秒后跳转到登录页面",Snackbar.LENGTH_SHORT).show();
                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    RegisterActivity.this.finish();
                                }
                            });
                        }
                    },2500);
                }else if (result.getString("msg").contains("手机")){
                    tilRegPhone.setError(result.getString("msg"));
                }else if (result.getString("msg").contains("验证码")){
                    tilRegCode.setError(result.getString("msg"));
                }else if (result.getString("msg").contains("密码")){
                    tilRegPassWord.setError(result.getString("msg"));
                }else {
                    Snackbar.make(tvCode,result.getString("msg"),Snackbar.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                pd.dismiss();
            }
        }
    }
}
