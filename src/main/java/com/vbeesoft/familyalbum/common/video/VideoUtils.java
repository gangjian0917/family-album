package com.vbeesoft.familyalbum.common.video;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 截取视频第N帧的图片
 */
public class VideoUtils {
    /**
     * 截取视频第六帧的图片
     *
     * @param videoFilePath 视频路径
     * @return 图片的相对路径 例：pic/1.png
     * @throws FrameGrabber.Exception
     */
    public static String makeVideoImage(String videoFilePath, int frame) throws FrameGrabber.Exception {
        String pngPath = "";
        FFmpegFrameGrabber ff = FFmpegFrameGrabber.createDefault(videoFilePath);

        ff.start();
        int ffLength = ff.getLengthInFrames();
        Frame f;
        int i = 0;
        while (i < ffLength) {
            f = ff.grabImage();
            //截取第6帧
            if ((i > (frame - 1)) && (f.image != null)) {
                //生成图片的相对路径 例如：pic/uuid.png
                pngPath = getPngPath(videoFilePath);
                //执行截图并放入指定位置
                doExecuteFrame(f, pngPath);
                break;
            }
            i++;
        }
        ff.stop();

        return pngPath;
    }

    /**
     * 生成图片的相对路径
     *
     * @return 图片的相对路径 例：pic/1.png
     */
    private static String getPngPath(String filePath) {
        return new File(filePath).getAbsoluteFile() + ".png";
    }


    /**
     * 生成唯一的uuid
     *
     * @return uuid
     */
    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * 截取缩略图
     *
     * @param f                       Frame
     * @param targerFilePath:封面图片存放路径
     */
    private static void doExecuteFrame(Frame f, String targerFilePath) {
        String imagemat = "png";
        if (null == f || null == f.image) {
            return;
        }
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bi = converter.getBufferedImage(f);
        int h = bi.getHeight();
        int w = bi.getWidth();
        int m = w > h ? w : h;

        int newW = w;
        int newH = h;
        if (m > 256) {
            double rate = 256.0 / m;
            newW = (int) (w * rate);
            newH = (int) (h * rate);
        }

        Image image = bi.getScaledInstance(newW, newH, Image.SCALE_DEFAULT);
        BufferedImage smallBi = toBufferedImage(image);
        File output = new File(targerFilePath);
        try {
            ImageIO.write(smallBi, imagemat, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();

        // Determine if the image has transparent pixels; for this method's
        // implementation, see e661 Determining If an Image Has Transparent Pixels
        //boolean hasAlpha = hasAlpha(image);

        // Create a buffered image with a format that's compatible with the screen
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            // Determine the type of transparency of the new buffered image
            int transparency = Transparency.OPAQUE;
	       /* if (hasAlpha) {
	         transparency = Transparency.BITMASK;
	         }*/

            // Create the buffered image
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                    image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
        }

        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            //int type = BufferedImage.TYPE_3BYTE_BGR;//by wang
	        /*if (hasAlpha) {
	         type = BufferedImage.TYPE_INT_ARGB;
	         }*/
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }

        // Copy image to buffered image
        Graphics g = bimage.createGraphics();

        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bimage;
    }


    public static void main(String[] args) throws FrameGrabber.Exception {
        String png = VideoUtils.makeVideoImage("/Users/jamesding/妈妈的手机/Camera/0815fbf795094acf18588debccc6eaca.mp4", 6);
        System.out.println("args = " + png);
    }
}
