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
import android.view.MenuItem;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;

import com.digw.it.Constant;
import com.digw.it.R;
import com.digw.it.util.StringEncrypt;
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

public class RegisterActivity extends BaseActivity implements TextureView.SurfaceTextureListener {
    private TextureView textureView;
    private MediaPlayer mediaPlayer;
    private Surface mSurface;

    private TextInputLayout tilRegUserName,tilRegPassWord,tilRegCheckPassWord,tilRegEmail;
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

    private void cleanEditTextError(){
        tilRegUserName.setError(null);
        tilRegPassWord.setError(null);
        tilRegCheckPassWord.setError(null);
        tilRegEmail.setError(null);
    }

    private boolean checkFrom(){
        String emailReg="^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        String regUserName=tilRegUserName.getEditText().getText().toString().trim();
        String regPassword=tilRegPassWord.getEditText().getText().toString().trim();
        String regCheckPassWord=tilRegCheckPassWord.getEditText().getText().toString().trim();
        String regEmail=tilRegEmail.getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(regUserName)){
            tilRegUserName.setError("用户名不能为空");
            return false;
        }
        if (regUserName.length()<4||regUserName.length()>9){
            tilRegUserName.setError("用户名必须大于等于4位,小于等于8位字符");
            return false;
        }
        if (TextUtils.isEmpty(regPassword)){
            tilRegPassWord.setError("密码不能为空");
            return false;
        }
        if (regPassword.length()<6||regPassword.length()>16){
            tilRegPassWord.setError("密码必须大于等于6位,小于等于16位字符");
            return false;
        }
        if (TextUtils.isEmpty(regCheckPassWord)){
            tilRegCheckPassWord.setError("确认密码不能为空");
            return false;
        }
        if (!regPassword.equals(regCheckPassWord)){
            tilRegCheckPassWord.setError("确认密码和密码不一致");
        }
        Pattern p = Pattern.compile(emailReg);
        Matcher matcher=p.matcher(regEmail);
        if (TextUtils.isEmpty(regEmail)){
            tilRegEmail.setError("电子邮件不能为空");
        }
        if (!matcher.matches()){
            tilRegEmail.setError("电子邮件格式错误");
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
                    task.execute(tilRegUserName.getEditText().getText().toString().trim(),tilRegPassWord.getEditText().getText().toString().trim(),tilRegEmail.getEditText().getText().toString().trim());
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
        return R.layout.activity_register;
    }

    @Override
    public void initView(View view) {
        textureView=$(R.id.texture_view);
        tilRegUserName=$(R.id.register_username);
        tilRegPassWord=$(R.id.register_password);
        tilRegCheckPassWord=$(R.id.register_check_password);
        tilRegEmail=$(R.id.register_email);
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
        regBtn.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

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
        //thread.start();
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

    private class UserRegisterAsyncTask extends AsyncTask<String,Integer,String>{

        @Override
        protected void onPreExecute() {
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            JSONObject userJson;
            FormBody.Builder builder = new FormBody.Builder();
            builder.add(Constant.REGISTER_REQUEST_KEY_USERNAME, params[0]);
            builder.add(Constant.REGISTER_REQUEST_KEY_PASSWORD, StringEncrypt.encrypt(params[1]));
            builder.add(Constant.REGISTER_REQUEST_KEY_EMAIL, params[2]);
            /*builder.add(Constant.REGISTER_REQUEST_KEY_SEX, params[3]);*/
            RequestBody requestBody = builder.build();
            Request request = new Request.Builder().url(Constant.URL_POST_USER_REGISTER).post(requestBody).build();
            try {
                result = HttpUtil.okHttpClient.newCall(request).execute().body().string();
            } catch (IOException e) {
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
                    Snackbar.make(textureView, "注册成功,3秒后返回登录界面", Snackbar.LENGTH_SHORT).show();
                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(LoginActivity.class);
                            RegisterActivity.this.finish();
                        }
                    }, 2500);
                } else {
                    Snackbar.make(textureView, "注册失败,"+obj.getString("msg"), Snackbar.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
