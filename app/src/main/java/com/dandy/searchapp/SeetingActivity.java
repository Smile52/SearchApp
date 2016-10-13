package com.dandy.searchapp;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.dandy.searchapp.util.FastBlur;
import com.dandy.searchapp.util.SeetingPresenter;


/**
 * 搜索设置页面
 */
public class SeetingActivity extends Activity {
    private LinearLayout mRootLayout;
    private Bitmap wallpaperbitmap;
    private ImageView mReturn;
    private SeetingPresenter mPresenter;
    private ListView mResultListView,mSeetingListView;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeting);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){//4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);//calculateStatusColor(Color.WHITE, (int) alphaValue)
        }

        mRootLayout= (LinearLayout) findViewById(R.id.activity_switch);
        wallpaperbitmap = getWallpaperbitmap(SeetingActivity.this);
        Drawable drawable=new BitmapDrawable(FastBlur.blur(SeetingActivity.this,8,wallpaperbitmap));
        mRootLayout.setBackground(drawable);

        mSeetingListView= (ListView) findViewById(R.id.search_seeting_liv);
        mResultListView= (ListView) findViewById(R.id.search_result_liv);
        mPresenter=new SeetingPresenter(getApplicationContext());
        mPresenter.showViews(mSeetingListView,mResultListView);
        mReturn= (ImageView) findViewById(R.id.seeting_return);
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SeetingActivity.this.finish();
            }
        });

    }



    private Bitmap getWallpaperbitmap(Context context) {
        // 获取壁纸管理器
        WallpaperManager wallpaperManager = WallpaperManager
                .getInstance(context);
        // 获取当前壁纸
        Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        // 将Drawable,转成Bitmap
        Bitmap bitmap = ((BitmapDrawable) wallpaperDrawable).getBitmap();
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int newWidth = dm.widthPixels;
        int newHeight = dm.heightPixels;

        return zoomImg(bitmap, newWidth, newHeight);
    }


    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片   www.2cto.com
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }
}
