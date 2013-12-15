package br.com.vinigodoy.kmeans;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainKmeans {
    public static void showHelp(String erro) {
        if (!erro.isEmpty()) {
            System.err.println(erro);
            System.err.println();
        }

        System.out.println("Help");
        System.out.println("----");
        System.out.println();
        System.out.println("Usage:");
        System.out.println("java -jar kmeans.jar classNumber image");
        System.exit(1);
    }

    public static void main(String[] args) {
        if (args.length != 2)
            showHelp("Incorrect parameters!");

        int classNumber = 0;
        try {
            classNumber = Integer.parseInt(args[0]);
        } catch (Exception e) {
            showHelp(e.getMessage());
        }

        if (classNumber > 7) {
            showHelp("Number of classes cannot be bigger than 7!");
        }

        File file = new File(args[1]);
        if (!file.exists())
            showHelp("File not found: " + file);

        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
            showHelp("Invalid image!");
        }

        //Demonstrate how to use kmeans to classify an image
        //The red, green and blue image components will be the characteristics.

        //First: Transform each image pixel in a 3d vector containing image r, g and b components.
        List<Vector> colors = new ArrayList<>();
        assert img != null;
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color c = new Color(img.getRGB(x, y));
                colors.add(new Vector(c.getRed(), c.getGreen(), c.getBlue()));
            }
        }

        //Create a kmeans classifier
        Kmeans kmeans = new Kmeans(classNumber, colors);

        //Colors of each exit class
        int[] exitColor = {Color.BLACK.getRGB(),
                Color.RED.getRGB(), Color.GREEN.getRGB(), Color.BLUE.getRGB(),
                Color.YELLOW.getRGB(), Color.MAGENTA.getRGB(), Color.CYAN.getRGB(),
                Color.ORANGE.getRGB()};

        //Create the destination image
        BufferedImage dest = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                //Read each pixel from source image into a vector
                Color c = new Color(img.getRGB(x, y));
                Vector amostra = new Vector(c.getRed(), c.getGreen(), c.getBlue());

                //Classify the pixel and set it to the destionation image
                dest.setRGB(x, y, exitColor[kmeans.classify(amostra)]);
            }

            //Show some progress status
            if (y % 50 == 0) {
                System.out.println("   Line " + (y + 1));
            }
        }

        //Record the final classified image into disk
        String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
        File out = new File(file.getParentFile(), fileName + "-" + classNumber + "classes.png");
        try {
            ImageIO.write(dest, "png", out);
        } catch (IOException e) {
            e.printStackTrace();
            showHelp("Could not generate file!");
        }
        System.out.println("File " + out + " successfully created!");
    }
}
