package com.gizwits.common.qrcode;

import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;


/**
 * 二维码的生成需要借助MatrixToImageWriter类，该类是由Google提供的，可以将该类直接拷贝到源码中使用，当然你也可以自己写个
 * 生产条形码的基类
 *
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
public class MatrixToImageWriter {

    private MatrixToImageWriter() {

    }

    /**
     * 图片矩阵。默认是机智图案.黄底黑图案
     *
     * @param matrix
     * @param front      图片颜色
     * @param background 背景色
     * @return
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix, Integer front, Integer background) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (front == null || background == null) {
                    image.setRGB(x, y, (matrix.get(x, y) ? Color.BLACK.getRGB() : Color.YELLOW.getRGB()));
                } else {
                    image.setRGB(x, y, (matrix.get(x, y) ? front : background));
                }
            }
        }

        return image;
    }

    public static void writeToFile(BitMatrix matrix, String format, File file, String logUri, Integer front, Integer background) throws IOException {

        BufferedImage image = toBufferedImage(matrix, front, background);

        if (logUri != null) {
            //设置logo图标
            QRCodeFactory logoConfig = new QRCodeFactory();
            image = logoConfig.setMatrixLogo(image, logUri);
        }

        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }

    }

    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream, String logUri, Integer front, Integer background) throws IOException {

        BufferedImage image = toBufferedImage(matrix, front, background);

        if (logUri != null) {
            //设置logo图标
            QRCodeFactory logoConfig = new QRCodeFactory();
            image = logoConfig.setMatrixLogo(image, logUri);
        }

        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }
}

