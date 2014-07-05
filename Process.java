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
        final ImageFrame frame = new ImageFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        while(true){
            frame.repaint();
        }
   }
}

@SuppressWarnings("serial")
class ImageFrame extends JFrame{
    //use this to display the image from client
    public ImagePanel panel;

    public ImageFrame(){
        // get screen dimensions       
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        // center frame in screen
        setTitle("ImageWindow");
        setLocation((screenWidth / 2) - 195, (screenHeight - DEFAULT_HEIGHT) / 2);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.getContentPane().setBackground( Color.GRAY );

        // add panel to frame
        this.getContentPane().setLayout(null);
        panel = new ImagePanel();
        //set the size of image
        panel.setSize(500, 500); //640 480
        panel.setLocation(0, 0);
        add(panel);
    }
    public static final int DEFAULT_WIDTH = 500;
    public static final int DEFAULT_HEIGHT = 500;
}

@SuppressWarnings("serial")
class ImagePanel extends JPanel {     
    public void paintComponent(Graphics g){  
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

            Graphics2D g2 = (Graphics2D) g;
            //g2.drawImage(out, 0, 0, null);
            //g2.dispose();

            // Show the image on the panel.
            tmpImg = Toolkit.getDefaultToolkit().createImage(out.getSource());
            g2.drawImage(tmpImg, 0, 0, null);
            
            // Write the image to the output folder.
            ImageIO.write(out, "jpg", new File("outputImg/testImg.jpg"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

