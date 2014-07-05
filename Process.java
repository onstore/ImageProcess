import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics2D;
import java.awt.*;
import java.awt.Label;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;

public class Process {

    public static void main(String[] args) {
        try {
            BufferedImage out = ImageIO.read(new File("inputImg/testImg.jpg"));

            //out.getGraphics().drawImage(in, 0, 0, null);
            int w = out.getWidth();
            int h = out.getHeight();
            int pixel[] = new int[w * h];

            //write the image to pixel array
            out.getRGB(0, 0, w, h, pixel, 0, w);
            int tmpR, tmpG, tmpB, Gray,tmpGray;
            Color cGray;
            Image tmpImg;

            //use the preImg to save the unprocess image
            int preImg[][] = new int[w][h];
            int position;

            for(int hCount = 0; hCount < h; hCount++){
                for(int wCount = 0; wCount < w; wCount++){
                    position = wCount + hCount * w;
                    tmpR = (pixel[position] >> 16) & 0xff;
                    tmpG = (pixel[position] >> 8) & 0xff;
                    tmpB = (pixel[position]) & 0xff;
                    Gray = (tmpR + tmpG + tmpB) / 3;
                    cGray = new Color(Gray, Gray, Gray);
                    int RGB = cGray.getRGB();
                    preImg[wCount][hCount] = RGB;
                    out.setRGB(wCount, hCount, RGB);
                }
            }

            Graphics2D g2d = out.createGraphics();
            g2d.drawImage(out, 0, 0, null);
            g2d.dispose();

            ImageIO.write(out, "jpg", new File("outputImg/testImg.jpg"));
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
