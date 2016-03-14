package com.example.tony.opencvnonfree;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class GrabCut extends AppCompatActivity {
    static {
        System.loadLibrary("opencv_java");
        System.loadLibrary("nonfree");
    }

    private static final String TAG = "GrabCut";
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabcut);
        imageView = (ImageView) this.findViewById(R.id.imageView);
        grabCut();
    }

    private void grabCut() {
        // 取得原始圖片FileInputStream
        FileInputStream srcFis = getFileInputStream(MainActivity.testImagesSrcPath + "/messi5.jpg");

        // 取得原始圖片bitmap
        Bitmap srcBitmap = BitmapFactory.decodeStream(srcFis);
//        Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.messi5);

        if(srcBitmap != null) {
            //JPEG2RGB888
            Bitmap bitmap = srcBitmap.copy(Bitmap.Config.ARGB_8888, true);
            Log.d(TAG, "bitmap 8888: " + bitmap.getWidth() + "x" + bitmap.getHeight());

            //GrabCut part
            Mat img = new Mat();
            Utils.bitmapToMat(bitmap, img);
            Log.d(TAG, "img: " + img);

            int r = img.rows();
            int c = img.cols();
            Point p1 = new Point(c / 10, r / 10);
            Point p2 = new Point(c - c / 10, r - r / 10);
            Rect rect = new Rect(p1, p2);
            Log.d(TAG, "rect: " + rect);

            Mat mask = new Mat();
            Mat fgdModel = new Mat();
            Mat bgdModel = new Mat();


            Mat imgC3 = new Mat();
            Imgproc.cvtColor(img, imgC3, Imgproc.COLOR_RGBA2RGB);
            Log.d(TAG, "imgC3: " + imgC3);

            Log.d(TAG, "Grabcut begins");
            Imgproc.grabCut(imgC3, mask, rect, bgdModel, fgdModel, 2, Imgproc.
                    GC_INIT_WITH_RECT);
            Log.d(TAG, "Grabcut ends");

            Log.d(TAG, "mask: " + mask);
            Log.d(TAG, "bgdModel: " + bgdModel);
            Log.d(TAG, "fgdModel: " + fgdModel);

            Core.convertScaleAbs(mask, mask, 100, 0);
            Imgproc.cvtColor(mask, mask, Imgproc.COLOR_GRAY2RGBA);
            Log.d(TAG, "maskC4: " + mask);

            //convert to Bitmap
            Log.d(TAG, "Convert to Bitmap");
//            Utils.matToBitmap(imgC3, bitmap);
            Utils.matToBitmap(mask, bitmap);

            //release MAT part
            img.release();
            imgC3.release();
            mask.release();
            fgdModel.release();
            bgdModel.release();

            imageView.setImageBitmap(bitmap);
        }
    }

    private FileInputStream getFileInputStream(String filepath) {
        File file = new File(filepath);

        if(file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return fis;
        }
        return null;
    }
}
