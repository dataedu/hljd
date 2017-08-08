package com.dk.mp.knsrd;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dk.mp.core.entity.LoginMsg;
import com.dk.mp.core.entity.ResultCode;
import com.dk.mp.core.http.HttpUtil;
import com.dk.mp.core.ui.MyActivity;
import com.dk.mp.core.util.DeviceUtil;
import com.dk.mp.core.util.Logger;
import com.dk.mp.core.util.StringUtils;
import com.dk.mp.core.view.DrawCheckMarkView;
import com.dk.mp.core.view.DrawCrossMarkView;
import com.dk.mp.core.view.DrawHookView;
import com.dk.mp.knsrd.adapter.UploadImageAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Response;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by cobb on 2017/8/3.
 */
public class KnsrdMainActivity extends MyActivity implements EasyPermissions.PermissionCallbacks{

    private TextView openText; //展开收起
    private ImageButton openImg;
    private LinearLayout infomation;
    private String isOpen = "f"; //收起状态

    private EditText sqly; //申请理由
    private TextView tv_left_num;

    private LinearLayout ok;   //提交按钮
    private DrawHookView progress;
    private DrawCheckMarkView progress_check;
    private DrawCrossMarkView progress_cross;
    private TextView ok_text;

    private RecyclerView imgView; //附件上传
    private TextView imgSize;
    public static final String BASEPICPATH = Environment.getExternalStorageDirectory() + "/mobileschool/cache/";
    List<String> imgs = new ArrayList<>();//保存图片地址
    UploadImageAdapter wImageAdapter;
    private String noCutFilePath ="";
    private static final int WRITE_RERD = 1;
    private String uuid = UUID.randomUUID().toString();

    private ScrollView mRootView;

    @Override
    protected int getLayoutID() {
        return R.layout.ac_knsrd_main;
    }

    @Override
    protected void initView() {
        super.initView();

        mRootView = (ScrollView) findViewById(R.id.mRootView);

        openImg = (ImageButton) findViewById(R.id.openImg);
        openText = (TextView) findViewById(R.id.openText);
        infomation = (LinearLayout) findViewById(R.id.infomation);

        sqly = (EditText) findViewById(R.id.sqly);
        tv_left_num = (TextView) findViewById(R.id.tv_left_num);
        sqly.addTextChangedListener(mTextWatcher);

        ok = (LinearLayout) findViewById(R.id.ok);
        progress = (DrawHookView) findViewById(R.id.progress);
        progress_check = (DrawCheckMarkView) findViewById(R.id.progress_check);
        progress_cross = (DrawCrossMarkView) findViewById(R.id.progress_cross);
        ok_text = (TextView) findViewById(R.id.ok_text);
        ok.setEnabled(false);

        imgs.add("addImage");
        imgView = (RecyclerView) findViewById(R.id.imgView);
        imgSize = (TextView) findViewById(R.id.imgSize);
        imgView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        wImageAdapter = new UploadImageAdapter(mContext,KnsrdMainActivity.this,imgs);
        imgView.setAdapter(wImageAdapter);
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            int len = charSequence.length();
            tv_left_num.setText(len + "/200");

        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    @Override
    protected void initialize() {
        super.initialize();

        setTitle(getIntent().getStringExtra("title"));
        setRightText("查看申请", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KnsrdMainActivity.this,LookApplyActivity.class);
                intent.putExtra("type","困难生认定");
                startActivity(intent);
            }
        });
    }

    /**
     * 基本信息查看
     */
    public void openInfo(View v){
        if (isOpen.equals("f")){
            isOpen = "t";
            openImg.setBackgroundResource(R.mipmap.up);
            openText.setText("收起");
            infomation.setVisibility(View.VISIBLE);
        }else {
            isOpen = "f";
            openImg.setBackgroundResource(R.mipmap.dwn);
            openText.setText("展开");
            infomation.setVisibility(View.GONE);
        }
    }

    /**
     * 设置ok按钮的样式
     */
    public void dealOkButton(){
        if(sqly.getText().toString().length()>0){
            ok.setBackground(getResources().getDrawable(R.drawable.ripple_bg));
            ok.setEnabled(true);
        }else{
            ok.setBackgroundColor(getResources().getColor(R.color.rcap_gray));
            ok.setEnabled(false);
        }
    }

    private void errorInfo(){
        progress.setVisibility(View.GONE);
        progress_cross.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {//等待成功动画结束
            @Override
            public void run() {
                progress_cross.setVisibility(View.GONE);
                ok_text.setVisibility(View.VISIBLE);
                ok.setEnabled(true);
            }
        },1000);
    }

    @AfterPermissionGranted(WRITE_RERD)
    public void startReadWi(){
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(mContext, perms)) {
            try {
                ablum();
            } catch (Exception e) {
                showErrorMsg(mRootView,"请正确授权");
            }
        } else {
            EasyPermissions.requestPermissions(this,
                    "请求读写权限",
                    WRITE_RERD, perms);
        }
    }

    public void ablum(){
        File appDir = new File(BASEPICPATH);
        if(!appDir.exists()){
            appDir.mkdir();
        }
        noCutFilePath = BASEPICPATH + UUID.randomUUID().toString() + ".jpg";
        File file = new File(noCutFilePath);
        try {
            if(!file.exists()){
                file.createNewFile();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Intent getImageByCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       /*获取当前系统的android版本号*/
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion<24){
            getImageByCamera.setFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        }else{
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            Uri uri = mContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
//        Intent getImageByCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        getImageByCamera.setFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
//        getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(noCutFilePath)));
        startActivityForResult(getImageByCamera, 1);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        try {
            if(requestCode == WRITE_RERD) {
                ablum();
            }
        } catch (Exception e) {
            showErrorMsg(mRootView,"请正确授权");
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        showErrorMsg(mRootView,"请正确授权");
    }

    public void clearImg(){
        noCutFilePath="";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1://
                if (resultCode == RESULT_OK) {
                    if (StringUtils.isNotEmpty(noCutFilePath) && new File(noCutFilePath).exists()) {
                        Log.e("dskhfudihcpioahoias",noCutFilePath.toString()+"----------"+imgs.size());
                        Bitmap bitmap = createThumbnail(noCutFilePath);
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(new File(noCutFilePath));
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, fos);// 把数据写入文件
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        imgs.remove(imgs.size()-1);
                        imgs.add(noCutFilePath);
                        if (imgs.size()<3){
                            imgs.add("addImage");
                        }

                        wImageAdapter.notifyDataSetChanged();
                    }
                }
                break;
        }
    }

    /**
     * 上传图片
     */
    public void updateImg(){
        LoginMsg loginMsg = getSharedPreferences().getLoginMsg();
        String mUrl = getReString(R.string.uploadUrl);
        if(loginMsg != null) {
            mUrl +="/independent.service?.lm=ssgl-dwjk&.ms=view&action=fjscjk&.ir=true&type=sswzdjAttachment&userId="+loginMsg.getUid()+"&password="+ loginMsg.getEncpsw() +"&ownerId=sswzdjAttachment"+uuid;
//            mUrl +="attachmentUpload.service?type=sswzdjAttachment&userId="+loginMsg.getUid()+"&password="+ loginMsg.getEncpsw()+"&ownerId="+uuid;
        }else{
//            mUrl +="attachmentUpload.service?type=sswzdjAttachment&ownerId="+uuid;
            mUrl +="/independent.service?.lm=ssgl-dwjk&.ms=view&action=fjscjk&.ir=true&type=sswzdjAttachment&ownerId=sswzdjAttachment"+uuid;
        }
        List<File> files = new ArrayList<>();
        files.add(new File(noCutFilePath));
        HttpUtil.getInstance().uploadImg(mUrl,files,new okhttp3.Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
//                errorInfo();
                mHandler.sendEmptyMessage(-1);
                showErrorMsg("上传附件失败");
                call.cancel();// 上传失败取消请求释放内存
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() == 200 ){
                    String result = response.body().string();
                    Logger.info("######################result="+result);
                    if(StringUtils.isNotEmpty(result)){
                        ResultCode rcode = getGson().fromJson(result,ResultCode.class);
                        if(rcode.getCode() == 200) {
                            call.cancel();// 上传失败取消请求释放内存
//                            submit(uuid);
                        }else{
                            mHandler.sendEmptyMessage(-1);
                            showErrorMsg(rcode.getMsg());
                            call.cancel();// 上传失败取消请求释放内存
                        }
                    }else{
                        mHandler.sendEmptyMessage(-1);
                        showErrorMsg("上传附件失败");
                        call.cancel();// 上传失败取消请求释放内存
                    }
                }else{
                    mHandler.sendEmptyMessage(-1);
                    showErrorMsg("上传附件失败");
                    call.cancel();// 上传失败取消请求释放内存
                }
//
//                if(response.code() == 200) {
//                    String result = response.body().string();
//                    Logger.info("######################result=" + result);
//                    call.cancel();// 上传失败取消请求释放内存
//                    submit(uuid);
//                }else{
//                    mHandler.sendEmptyMessage(-1);
//                    showErrorMsg("上传附件失败");
//                    call.cancel();// 上传失败取消请求释放内存
//                }
            }
        });
    }

    /**
     * 压缩图片
     */
    private Bitmap createThumbnail(String filepath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内容
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        BitmapFactory.decodeFile(filepath, options);

        options.inJustDecodeBounds = false;
        int w = options.outWidth;
        int h = options.outHeight;
        // 想要缩放的目标尺寸
        float hh = h/2;// 设置高度为240f时，可以明显看到图片缩小了
        float ww = w;// 设置宽度为120f，可以明显看到图片缩小了
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (options.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (options.outHeight / hh);
        }
        if (be <= 0) be = 1;
        options.inSampleSize = 4;//设置缩放比例
        // 开始压缩图片，注意此时已经把options.inJustDecodeBounds 设回false了

        return BitmapFactory.decodeFile(filepath, options);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1://成功
                    successInfo();
                    break;
                case -1://失败
                    errorInfo();
                    break;
            }
        }
    };

    /**
     * 成功
     */
    private void successInfo(){
        progress.setVisibility(View.GONE);
        progress_check.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {//等待成功动画结束
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                ok.setEnabled(true);
                back();
            }
        },2000);
    }
}
