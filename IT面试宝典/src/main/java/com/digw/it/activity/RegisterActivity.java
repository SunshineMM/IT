package com.digw.it.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.MenuItem;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.digw.it.Constant;
import com.digw.it.R;
import com.digw.it.util.net.HttpUtil;
import com.digw.it.util.net.NetListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

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

    @Override
    public void viewClick(View v) {
        switch (v.getId()){
            case R.id.register_btn:
                pd.show();
                Map<String,String> map=new HashMap<>();
                map.put("username",tilRegUserName.getEditText().getText().toString());
                map.put("password",tilRegPassWord.getEditText().getText().toString());
                map.put("email",tilRegEmail.getEditText().getText().toString());
                map.put("sex","男");
                HttpUtil.doPost(Constant.URL_POST_USER_REGISTER, map, new NetListener.HttpCallbackListener() {
                    @Override
                    public void onFinish(Response response) {
                        try {
                            JSONObject jsonObject= new JSONObject(response.body().string());
                            if (jsonObject.getBoolean("state")){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            pd.dismiss();
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pd.dismiss();
                                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                break;
        }
    }

    @Override
    public void initParams(Bundle bundle) {
        setAllowFullScreen(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
}
