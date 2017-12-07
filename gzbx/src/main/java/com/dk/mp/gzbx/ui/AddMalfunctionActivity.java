package com.dk.mp.gzbx.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.dk.mp.core.entity.ImageResult;
import com.dk.mp.core.entity.JsonData;
import com.dk.mp.core.http.HttpUtil;
import com.dk.mp.core.http.request.HttpListener;
import com.dk.mp.core.ui.MyActivity;
import com.dk.mp.core.util.BroadcastUtil;
import com.dk.mp.core.util.DeviceUtil;
import com.dk.mp.core.util.Logger;
import com.dk.mp.core.util.StringUtils;
import com.dk.mp.core.view.DrawCheckMarkView;
import com.dk.mp.core.view.DrawCrossMarkView;
import com.dk.mp.core.view.DrawHookView;
import com.dk.mp.gzbx.R;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;


/**
 * 申请报修
 * @author janabo
 *
 */
public class AddMalfunctionActivity extends MyActivity implements View.OnClickListener,TakePhoto.TakeResultListener,InvokeListener {
	private EditText tro_address;//地点
	private EditText tro_dev;//设备
	private EditText tro_problem;//问题描述

	private LinearLayout ok;  //提交按钮
	private DrawHookView progress;
	private DrawCheckMarkView progress_check;
	private DrawCrossMarkView progress_cross;
	private TextView ok_text;
	private LinearLayout addPhotos;//添加图片
	private TakePhoto takePhoto;
	private InvokeParam invokeParam;
	public String mHeaderAbsolutePath;
	public final static int mMessageFlag = 0x1010;
	private LinearLayout lin_img;
	private LinearLayout addPhotos2;
	private int mWidth=0,mHeight=0;
	private TextView imgsize;
	private List<File> files = new ArrayList<>();
	private Map<Integer,String> fMap = new HashMap<>();//存放图片返回的地址
	private ArrayList<String> images = new ArrayList<>();

	@Override
	protected int getLayoutID() {
		return R.layout.layout_add_malfunction;
	}

	@Override
	protected void initialize() {
		super.initialize();
		setTitle("申请报修");
		findView();
	}
	
	private void findView(){
		imgsize = (TextView) findViewById(R.id.imgsize);
		ok = (LinearLayout) findViewById(R.id.ok);
		progress = (DrawHookView) findViewById(R.id.progress);
		progress_check = (DrawCheckMarkView) findViewById(R.id.progress_check);
		progress_cross = (DrawCrossMarkView) findViewById(R.id.progress_cross);
		ok_text = (TextView) findViewById(R.id.ok_text);
		addPhotos = (LinearLayout) findViewById(R.id.addPhotos);
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) addPhotos.getLayoutParams();
		int screenWidth = DeviceUtil.getScreenWidth(mContext);
		int paddingWidth = DeviceUtil.dip2px(mContext,20);
		mWidth = screenWidth-paddingWidth;
		mHeight = mWidth/16*9;
		params.height = mHeight;//计算控件的高度
		addPhotos.setLayoutParams(params);

		lin_img = (LinearLayout) findViewById(R.id.lin_img);
		addPhotos2 = (LinearLayout) findViewById(R.id.addPhotos2);

		tro_address = (EditText) findViewById(R.id.tro_address);
		tro_dev = (EditText) findViewById(R.id.tro_dev);
		tro_problem = (EditText) findViewById(R.id.tro_problem);
		SpannableString ss = new SpannableString(getResources().getString(R.string.tro_address_hint));
		AbsoluteSizeSpan ass = new AbsoluteSizeSpan(12,true);
		ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		tro_address.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
		SpannableString ssd = new SpannableString(getResources().getString(R.string.tro_dev_hint));
		AbsoluteSizeSpan assd = new AbsoluteSizeSpan(12,true);
		ssd.setSpan(assd, 0, ssd.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		tro_dev.setHint(new SpannedString(ssd)); // 一定要进行转换,否则属性会消失
		SpannableString ssp = new SpannableString(getResources().getString(R.string.tro_problemdescription_hint));
		AbsoluteSizeSpan assp = new AbsoluteSizeSpan(12,true);
		ssp.setSpan(assp, 0, ssp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		tro_problem.setHint(new SpannedString(ssp)); // 一定要进行转换,否则属性会消失

		tro_problem.addTextChangedListener(mTextWatcher3);
		tro_dev.addTextChangedListener(mTextWatcher2);
		tro_address.addTextChangedListener(mTextWatcher1);

		addPhotos.setOnClickListener(this);
		addPhotos2.setOnClickListener(this);
	}

	/**
	 * 设置ok按钮的样式
	 */
	public void dealOkButton(){
		if(tro_address.getText().toString().length()>0 && tro_dev.getText().toString().length()>0 && tro_problem.getText().toString().length()>0){
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

	public void submitLiusu(View v) {
		final String addressText = tro_address.getText().toString().trim();
		final String devText = tro_dev.getText().toString().trim();
		final String proText = tro_problem.getText().toString().trim();
		if(addressText.length()<=0){
			showMessage("请填写地点");
			return;
		}
		if(devText.length()<=0){
			showMessage("请填写设备");
			return;
		}
		if(proText.length()<=0){
			showMessage("请填写问题描述");
			return;
		}
		Logger.info("地址长度："+addressText.length()+":"+devText.length());
		if(addressText.length()>50 || devText.length()>50){
			showMessage("请填写少于50个字");
			return;
		}
		if(proText.length()>200){
			showMessage("请填写少于200个字");
			return;
		}

		ok_text.setVisibility(View.GONE);
		progress.setVisibility(View.VISIBLE);
		if(files.size()<=0){
			submitXX("");
		}else{
			for(int i=0;i<files.size();i++){
				updateImg(files.get(i),i);//上传图片
			}
		}

		
	}

	TextWatcher mTextWatcher1 = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}
		
		@Override
		public void afterTextChanged(Editable s) {
//			dealOkButton();
			if(tro_address.getText().toString().trim().length() >= 50){
				showMessage("地点不能大于50个字");
			}
		}
	};
	
	TextWatcher mTextWatcher2 = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}
		
		@Override
		public void afterTextChanged(Editable s) {
//			dealOkButton();
			if(tro_dev.getText().toString().trim().length() >= 50){
				showMessage("设备不能大于50个字");
			}
		}
	};
	TextWatcher mTextWatcher3 = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}
		
		@Override
		public void afterTextChanged(Editable s) {
//			dealOkButton();
			if(tro_problem.getText().toString().trim().length() >= 200){
				showMessage("问题描述不能大于200个字");
			}
		}
	};

	@Override
	public void onClick(View v) {
		showTakePhotoDialog();
	}

	//弹出选择框
	private void showTakePhotoDialog() {
		final String items[] = {"拍照", "相册"};
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("选择报修图片");
		builder.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				takeOrPickPhoto(which == 0 ? true : false);
			}
		});

		builder.create().show();
	}

	private void takeOrPickPhoto(boolean isTakePhoto) {
		File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
		if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
		Uri imageUri = Uri.fromFile(file);
		TakePhoto takePhoto = getTakePhoto();

		configCompress(takePhoto);
		configTakePhotoOption(takePhoto);

		if (isTakePhoto) {//拍照
			if (false) {//是否裁剪
				takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
			} else {
				takePhoto.onPickFromCapture(imageUri);
			}
		} else {//选取图片
			int limit = 1;//选择图片的个数。
			if (limit > 1) {
				//当选择图片大于1个时是否裁剪
				if (true) {
					takePhoto.onPickMultipleWithCrop(limit, getCropOptions());
				} else {
					takePhoto.onPickMultiple(limit);
				}
				return;
			}
			//是否从文件中选取图片
			if (false) {
				if (false) {//是否裁剪
					takePhoto.onPickFromDocumentsWithCrop(imageUri, getCropOptions());
				} else {
					takePhoto.onPickFromDocuments();
				}
				return;
			} else {
				if (false) {//是否裁剪
					takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
				} else {
					takePhoto.onPickFromGallery();
				}
			}
		}
	}

	/**
	 * 获取TakePhoto实例
	 *
	 * @return
	 */
	public TakePhoto getTakePhoto() {
		if (takePhoto == null) {
			takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
		}
		return takePhoto;
	}

	/**
	 * 配置 压缩选项
	 *
	 * @param takePhoto
	 */
	private void configCompress(TakePhoto takePhoto) {
		int maxSize = 102400;
		int width = 800;
		int height = 800;
		//是否显示 压缩进度条
		boolean showProgressBar = true;
		//压缩后是否保存原图
		boolean enableRawFile = true;
		CompressConfig config;
		if (false) {
			//使用自带的压缩工具
			config = new CompressConfig.Builder()
					.setMaxSize(maxSize)
					.setMaxPixel(width >= height ? width : height)
					.enableReserveRaw(enableRawFile)
					.create();
		} else {
			//使用开源的鲁班压缩工具
			LubanOptions option = new LubanOptions.Builder()
					.setMaxHeight(height)
					.setMaxWidth(width)
					.setMaxSize(maxSize)
					.create();
			config = CompressConfig.ofLuban(option);
			config.enableReserveRaw(enableRawFile);
		}
		takePhoto.onEnableCompress(config, showProgressBar);
	}

	/**
	 * 配置 裁剪选项
	 *
	 * @return
	 */
	private CropOptions getCropOptions() {
		int height = 100;
		int width = 100;

		CropOptions.Builder builder = new CropOptions.Builder();

		//按照宽高比例裁剪
		builder.setAspectX(width).setAspectY(height);
		//是否使用Takephoto自带的裁剪工具
		builder.setWithOwnCrop(false);
		return builder.create();
	}

	/**
	 * 拍照相关的配置
	 *
	 * @param takePhoto
	 */
	private void configTakePhotoOption(TakePhoto takePhoto) {
		TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
		//是否使用Takephoto自带的相册
		if (false) {
			builder.setWithOwnGallery(true);
		}
		//纠正拍照的旋转角度
		if (true) {
			builder.setCorrectImage(true);
		}
		takePhoto.setTakePhotoOptions(builder.create());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getTakePhoto().onCreate(savedInstanceState);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
		getTakePhoto().onSaveInstanceState(outState);
		super.onSaveInstanceState(outState, outPersistentState);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		getTakePhoto().onActivityResult(requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void takeSuccess(TResult result) {
		File file = new File(result.getImages().get(0).getCompressPath());
		mHeaderAbsolutePath = file.getAbsolutePath();
		//需要返回到UI线程 刷新图片
		Message msg = mHandler.obtainMessage();
		msg.what = mMessageFlag;
		msg.obj = file;
		mHandler.sendMessage(msg);
	}

	@Override
	public void takeFail(TResult result, String msg) {

	}

	@Override
	public void takeCancel() {

	}

	@Override
	public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
		PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
		if (PermissionManager.TPermissionType.WAIT.equals(type)) {
			this.invokeParam = invokeParam;
		}
		return type;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		//以下代码为处理Android6.0、7.0动态权限所需
		PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
		PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case mMessageFlag:
//					Glide.with(mContext).load(((File) msg.obj)).into(mUserheadImgUserinfo);
					addPhotos.setVisibility(View.GONE);
					lin_img.setVisibility(View.VISIBLE);
					addPhotos2.setVisibility(View.VISIBLE);
					File imgFile = (File) msg.obj;
					addFrameLayout(lin_img,imgFile);
					break;
				case 1://上传图片成功
					if(fMap.size() == files.size()) {//代表图片都上传成功
						String fileids="";
						for(int i=0;i<files.size();i++){
							fileids += fMap.get(i)+",";
						}
						submitXX(fileids);
					}
					break;
				case -1://上传图片失败
					fMap.clear();
					showMessage("上传报修照片失败");
					errorInfo();
					break;
				default:
					break;
			}
			super.handleMessage(msg);
		}
	};

	/**
	 * 动态添加FrameLayout
	 */
	public void addFrameLayout(final LinearLayout view, final File imgFile){
		final FrameLayout frameLayout = new FrameLayout(this);
		frameLayout.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mWidth,mHeight+DeviceUtil.dip2px(mContext,10));
		ImageView imageView = new ImageView(this);
		FrameLayout.LayoutParams viewPream =new FrameLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT
		);//设置布局控件的属性
		viewPream.topMargin=DeviceUtil.dip2px(this,10);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		Glide.with(mContext).load(imgFile).into(imageView);//填充图片
		files.add(imgFile);//图片放大集合中
		images.add(imgFile.getAbsolutePath());
		frameLayout.addView(imageView,viewPream);

		ImageView imageView2 = new ImageView(this);//删除按钮
		FrameLayout.LayoutParams viewPream2 =new FrameLayout.LayoutParams(
				DeviceUtil.dip2px(this,20),
				DeviceUtil.dip2px(this,20)
		);//设置布局控件的属性
		viewPream2.topMargin=DeviceUtil.dip2px(this,20);
		viewPream2.rightMargin=DeviceUtil.dip2px(this,10);
		viewPream2.gravity = Gravity.RIGHT;
		Glide.with(mContext).load(R.mipmap.gzbx_del).into(imageView2);//填充图片
		frameLayout.addView(imageView2,viewPream2);
		view.addView(frameLayout,params);

		imageView2.setOnClickListener(new View.OnClickListener() {//删除样式
			@Override
			public void onClick(View v) {
				view.removeView(frameLayout);
				int mViewCount = view.getChildCount();
				if(mViewCount ==0){//判断是否有图片
					addPhotos2.setVisibility(View.GONE);
					addPhotos.setVisibility(View.VISIBLE);
				}else{
					addPhotos2.setVisibility(View.VISIBLE);
					addPhotos.setVisibility(View.GONE);
					imgsize.setText("继续添加报修照片（"+view.getChildCount()+"/3）");
				}
				images.remove(imgFile.getAbsolutePath());
				files.remove(imgFile);
			}
		});

		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int position = 0;
				for(int i=0;i<images.size();i++){
					if(images.get(i).equals(imgFile.getAbsolutePath())){
						position = i;
					}
				}
				Intent intent = new Intent(mContext, ImagePreviewActivity.class);
				intent.putExtra("index",position);
				intent.putStringArrayListExtra("list", images);
				startActivity(intent);
			}
		});

		int mCount = view.getChildCount();
		imgsize.setText("继续添加报修照片（"+mCount+"/3）");
		if(mCount>=3){//大于3张，不给继续添加
			addPhotos2.setVisibility(View.GONE);
		}
	}

	/**
	 * 上传图片
	 */
	public void updateImg(File img, final int flag){
		HttpUtil.getInstance().uploadImg2("fileUpload",img,new okhttp3.Callback(){
			@Override
			public void onFailure(Call call, IOException e) {
				mHandler.sendEmptyMessage(-1);
				call.cancel();// 上传失败取消请求释放内存
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				if(response.code() == 200){
					String result = response.body().string();
					Logger.info("######################result="+result);
					if(StringUtils.isNotEmpty(result)) {
						try {
							JSONObject ja = new JSONObject(result).getJSONObject("json");
							int code = ja.optInt("code");
							if (code == 200) {
								call.cancel();// 上传失败取消请求释放内存
									ImageResult imageResult = getGson().fromJson(ja.getJSONArray("data").get(0).toString(),ImageResult.class);
									fMap.put(flag,imageResult.getFileId());
									mHandler.sendEmptyMessage(1);//成功
							} else {
								call.cancel();// 上传失败取消请求释放内存
								mHandler.sendEmptyMessage(-1);
							}

						} catch (JSONException e) {
							e.printStackTrace();
							call.cancel();// 上传失败取消请求释放内存
							mHandler.sendEmptyMessage(-1);
						}
					}else{
						call.cancel();// 上传失败取消请求释放内存
						mHandler.sendEmptyMessage(-1);
					}
				}else{
					call.cancel();// 上传失败取消请求释放内存
					mHandler.sendEmptyMessage(-1);
				}
			}
		});
	}


	public void submitXX(String imgs){
		if(DeviceUtil.checkNet()){
			final String addressText = tro_address.getText().toString().trim();
			final String devText = tro_dev.getText().toString().trim();
			final String proText = tro_problem.getText().toString().trim();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("dd", addressText);
			map.put("sb", devText);
			map.put("wtms", proText);
			map.put("tp", imgs);
			HttpUtil.getInstance().postJsonObjectRequest("apps/gzbx/tjbx", map, new HttpListener<JSONObject>() {
				@Override
				public void onSuccess(JSONObject result) {
					JsonData jd = getGson().fromJson(result.toString(),JsonData.class);
					if (jd.getCode() == 200 && (Boolean) jd.getData()) {
						progress.setVisibility(View.GONE);
						progress_check.setVisibility(View.VISIBLE);
						new Handler().postDelayed(new Runnable() {//等待成功动画结束
							@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
							@Override
							public void run() {
								ok.setEnabled(true);
								showMessage("提交成功");
								BroadcastUtil.sendBroadcast(mContext, "com.test.action.refresh");
								onBackPressed();
							}
						},1500);
					}else{
						showErrorMsg(jd.getMsg());
						errorInfo();
					}
				}
				@Override
				public void onError(VolleyError error) {
					errorInfo();
				}
			});
		}else {
			showErrorMsg(getString(R.string.net_no2));
			errorInfo();
		}
	}
}
