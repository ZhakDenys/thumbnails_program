package com.company;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Converter {
    private File[] files;
    private String type;
    private int width, height;

    public Converter(File[] files, String type, int width, int height){
        this.files = files;
        this.type = type;
        this.width = width;
        this.height = height;
    }

    // Converts an array of files into new files with specified type ans sizes. New images saves as 
    // thumbnail_filename and placed into the same folder as the original image.
    public String convertTo(){
            try {
                for(int i = 0; i < files.length; i++){
                    File file = files[i];
                    String filename = file.getName().substring(0, file.getName().lastIndexOf(".")) + "." + type;
                    String newName = file.getAbsolutePath().replace(file.getName(), "thumbnail_" + filename);
                    BufferedImage originalImage = ImageIO.read(file);
                    int typeImage = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
                    BufferedImage resizedImageHint = resizeImageWithHint(originalImage, typeImage);
                    ImageIO.write(resizedImageHint, type, new File(newName));
            }
                return "File(s) converted.";
            } catch (IOException e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }

    }

    // Method for changing image size.
    private BufferedImage resizeImageWithHint(BufferedImage originalImage, int type){
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        return resizedImage;
    }
}
