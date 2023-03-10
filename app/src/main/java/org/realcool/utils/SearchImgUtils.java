package org.realcool.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.benjaminwan.ocrlibrary.OcrEngine;
import com.benjaminwan.ocrlibrary.OcrResult;
import com.benjaminwan.ocrlibrary.TextBlock;

import org.opencv.android.Utils;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.DMatch;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.features2d.BFMatcher;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.ORB;
import org.opencv.features2d.SIFT;
import org.opencv.imgproc.Imgproc;
import org.realcool.base.msg.PicTextMsg;
import org.realcool.base.msg.PointMsg;
import org.realcool.bean.TempMat;
import org.realcool.bean.MatchPoint;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SearchImgUtils {

    public static Mat getMatByBitmap(Bitmap bitmap) {
        Mat mat = new Mat();
        Utils.bitmapToMat(bitmap, mat);
        return mat;
    }

    /**
     * @param target 特征图
     * @param origin 大的图(录屏)
     * @return
     */
    public static boolean matchImg(Bitmap target, Bitmap origin) {
        Mat tm = getMatByBitmap(target);
        Mat om = getMatByBitmap(origin);
        MatchPoint keyPoint = getKeyPoint(new TempMat(om, tm));
        return tempInOrigin(keyPoint).getGoodMatch().size() >= MatchPoint.MATCH_NUM;
    }

    public static MatchPoint getKeyPoint(TempMat mat) {
        Mat temp = mat.getTemp();
        Mat origin = mat.getOriginal();
        MatchPoint point = new MatchPoint();
        switch (point.getDetectType()) {
            case MatchPoint.TYPE_ORB:
                ORB orb = ORB.create();
                orb.detect(temp, point.getTempK());
                orb.detect(origin, point.getOriginK());
                orb.compute(temp, point.getTempK(), point.getTemp());
                orb.compute(origin, point.getOriginK(), point.getOrigin());
                break;
            case MatchPoint.TYPE_SIFT:
                SIFT sift = SIFT.create();
                sift.detect(temp, point.getTempK());
                sift.detect(origin, point.getOriginK());
                sift.compute(temp, point.getTempK(), point.getTemp());
                sift.compute(origin, point.getOriginK(), point.getOrigin());
        }
        return point;
    }

    public static MatchPoint tempInOrigin(MatchPoint point) {
        Mat resT = point.getTemp();
        Mat resO = point.getOrigin();
        LinkedList<MatOfDMatch> matches = new LinkedList<>();
        switch (point.getDetectType()) {
            case MatchPoint.TYPE_ORB:
                BFMatcher bfMatcher = BFMatcher.create(BFMatcher.BRUTEFORCE_HAMMING);
                bfMatcher.knnMatch(resT, resO, matches, 2);
                break;
            /**
             * knnMatch方法的作用就是在给定特征描述集合中寻找最佳匹配
             * 使用KNN-matching算法，令K=2，则每个match得到两个最接近的descriptor，然后计算最接近距离和次接近距离之间的比值，当比值大于既定值时，才作为最终match。
             */
            case MatchPoint.TYPE_SIFT:
                DescriptorMatcher descriptorMatcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);
                descriptorMatcher.knnMatch(resT, resO, matches, 2);
        }
        LinkedList<DMatch> goodMatch = point.getGoodMatch();
        //对匹配结果进行筛选，依据distance进行筛选
        matches.forEach(match -> {
            DMatch[] dmatcharray = match.toArray();
            DMatch m1 = dmatcharray[0];
            DMatch m2 = dmatcharray[1];
            if (m1.distance <= m2.distance * MatchPoint.NDR_RATIO) {
                goodMatch.addLast(m1);
            }
        });
        Log.e("符合数量", goodMatch.size() + "");
        return point;
    }

    //存入是否符合
    public static MatchPoint boolMatch(TempMat mat) {
        MatchPoint keyPoint = getKeyPoint(mat);
        keyPoint.setMatch(tempInOrigin(keyPoint).getGoodMatch().size() >= MatchPoint.MATCH_NUM);
        return keyPoint;
    }

    public static PointMsg getPoints(TempMat mat) {
        MatchPoint keyPoint = boolMatch(mat);
        return getPoints(keyPoint, mat);
    }

    public static PointMsg getPoints(MatchPoint keyPoint, TempMat mat) {
        Mat temp = mat.getTemp();
        if (keyPoint.isMatch()) {
            List<KeyPoint> tkp = keyPoint.getTempK().toList();
            List<KeyPoint> okp = keyPoint.getOriginK().toList();
            LinkedList<Point> op = new LinkedList<>();
            LinkedList<Point> sp = new LinkedList<>();
            for (DMatch match : keyPoint.getGoodMatch()) {
                op.addLast(tkp.get(match.queryIdx).pt);
                sp.addLast(okp.get(match.trainIdx).pt);
            }
            MatOfPoint2f op2f = new MatOfPoint2f();
            MatOfPoint2f sp2f = new MatOfPoint2f();
            op2f.fromList(op);
            sp2f.fromList(sp);
            //使用 findHomography 寻找匹配上的关键点的变换
            Mat homography = Calib3d.findHomography(op2f, sp2f, Calib3d.RANSAC, 3);
            /**
             * 透视变换(Perspective Transformation)是将图片投影到一个新的视平面(Viewing Plane)，也称作投影映射(Projective Mapping)。
             */
            Mat tempCorners = new Mat(4, 1, CvType.CV_32FC2);
            Mat tempResult = new Mat(4, 1, CvType.CV_32FC2);
            tempCorners.put(0, 0, 0, 0);
            tempCorners.put(1, 0, temp.cols(), 0);
            tempCorners.put(2, 0, temp.cols(), temp.rows());
            tempCorners.put(3, 0, 0, temp.rows());
            //使用 perspectiveTransform 将模板图进行透视变以矫正图象得到标准图片
            Core.perspectiveTransform(tempCorners, tempResult, homography);
            return new PointMsg(tempResult.get(0, 0), tempResult.get(1, 0),
                    tempResult.get(2, 0), tempResult.get(3, 0));
        }
        return null;
    }

    public static PointMsg searchText(OcrEngine engine, String text, Bitmap bitmap) {
        OcrResult or = detect(engine, bitmap, .6f);
        if (or.getTextBlocks().size() > 0) {
            for (TextBlock tb :
                    or.getTextBlocks()) {
                if (tb.getText().contains(text)) {
                    // 找到符合的第一个点，当前直接跳出函数
                    return new PointMsg(tb.getBoxPoint().get(0), tb.getBoxPoint().get(1), tb.getBoxPoint().get(2), tb.getBoxPoint().get(3));
                }
            }
        }
        return null;
    }

    public static PicTextMsg searchAllText(OcrEngine engine, Bitmap bitmap) {
        OcrResult or = detect(engine, bitmap, .6f);
        if (or.getTextBlocks().size() > 0) {
            PicTextMsg picTextMsg = new PicTextMsg(or.getTextBlocks());
            return picTextMsg;
        }
        return null;
    }

    /**
     * 获取bitmap图片内所有文字包含特征文字的数量是否大于 fitNum
     *
     * @param engine   ocr引擎
     * @param bitmap   当前图片的bitmap
     * @param features 特征文字列表
     * @param fitNum   满足条件文字数量
     * @return
     */
    public static boolean matchText(OcrEngine engine, Bitmap bitmap, List<String> features, int fitNum) {
        OcrResult or = detect(engine, bitmap, .6f);
        ArrayList<TextBlock> textBlocks = or.getTextBlocks();
        if (textBlocks.size() > 0) {
            StringBuilder builder = new StringBuilder("");
            int fit = 0, forIndex = 0;
            int size = textBlocks.size();
            while (fit < fitNum && forIndex < size && features.size() > fit) {
                String s = features.get(fit);
                String text = textBlocks.get(forIndex).getText();
                forIndex++;
                if (text.contains(s)) {
                    fit++;
                    continue;
                }
                builder.append(text).append("\n");
                if (builder.indexOf(s) != -1) {
                    fit++;
                }
            }
            return fit >= fitNum;
        }
        return false;
    }

    public static boolean matchImg(Context context, Bitmap bitmap, List<String> features, int fitNum) {
        int fit = 0;
        for (int i = 0; i < features.size(); i++) {
            String s = features.get(i);
            Bitmap img = FileUtils.getBitmapByFileName(context, s);
            img.recycle();
            if (matchImg(bitmap, img)) {
                fit++;
                if (fit == fitNum) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 文字检测
     *
     * @return
     */
    public static OcrResult detect(OcrEngine engine, Bitmap bitmap, float ocrRatio) {
        Bitmap temp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        int size = Math.max(bitmap.getHeight(), bitmap.getWidth());
        size = (int) (ocrRatio * size);
        return engine.detect(bitmap, temp, size);
    }
}
