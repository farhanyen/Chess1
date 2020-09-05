import Models.Pieces.Piece;
import Models.Square;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class ImagesLoader {
    private static final File dir = new File("Images/");

    private HashMap<String, BufferedImage> imagesMap = new HashMap<>();

    public ImagesLoader(){
        File directory = new File("./");
        System.out.println(directory.getAbsolutePath());
        loadImages();
        processImages();
    }

    private void loadImages(){
        for(final File f: dir.listFiles()){
            if(!f.getName().endsWith(".png")) continue;

            BufferedImage img = null;
            String imageName = f.getName().replaceFirst("[.][^.]+$", "");
            try {
                img = ImageIO.read(f);
            } catch(IOException e){
                System.out.println("Error reading file: " + e);
            }

            if(img != null && imageName != null){
                imagesMap.put(imageName, img);
            }
        }
    }

    private void processImages(){
        imagesMap.forEach((k, v) -> {
            int length;
            if(k.endsWith("Square")) length = Square.LENGTH;
            else length = Piece.PLENGTH;

            imagesMap.put(k, getScaledImage(v, length, length));
        });
    }

    private Dimension getScaleDimension(Image img, double length){
        double hratio = length/img.getHeight(null);
        double wratio = length/img.getWidth(null);
        double ratio = Math.min(wratio, hratio);

        return new Dimension((int) (img.getWidth(null) * ratio),
                            (int) (img.getHeight(null) * ratio));
    }

    private BufferedImage getScaledImage(BufferedImage img, int width, int height){
        return Scalr.resize(img, Scalr.Method.QUALITY, width, height);
    }

    public Image getImage(String imageName){
        Image img = imagesMap.get(imageName);
        if(img == null){
            System.out.println("No image stored under: " + imageName);
            return null;
        }

        return img;
    }
}
