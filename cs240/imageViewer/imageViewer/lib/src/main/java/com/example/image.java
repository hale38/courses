package com.example;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

import static java.lang.Math.abs;


/**
 * Created by hale38 on 1/10/18.
 */

public class image {
    private String inputFile;
    private String outputFile;
    private Boolean validImage;
    private Pixel picture[][]; //array height width


    //image info
    private StringBuilder header;
    private int height;
    private int width;
    private int maxColor;
    private String type;


    public image(String inFile, String outFile)
    {
        this.inputFile=inFile;
        this.outputFile=outFile;
        validImage=loadImage();
    }

    public boolean loadImage()
    {

        try {
            Scanner reader = new Scanner(new File("/Users/Riley/cs240/imageViewer/imageViewer/lib/src/main/java/com/example/pictures" + inputFile)).useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
            //Scanner reader = new Scanner(new File("/users/admin/hale38/classes/cs240/imageViewer/imageViewer/lib/src/main/java/com/example/pictures/" + inputFile)).useDelimiter("((#[^\\n]*\\n)|(\\s+))+");

            type=reader.next();
            width=reader.nextInt();
            height=reader.nextInt();
            maxColor=reader.nextInt();

            //initalize array of pixels
            picture = new Pixel[width][height];
            while (reader.hasNext())
            {
                for (int i =0; i<height; i++){
                    {
                        for (int j =0; j<width; j++){
                            picture[j][i] = new Pixel(reader.nextInt(),reader.nextInt(),reader.nextInt());

                        }
                    }
                }

            }
            System.out.println(' ');
           ;

        } catch (FileNotFoundException e) {
            System.out.println("imageFailed");
            return false;
        }

        return true;
    }




    private void writeImage(){
        File outPut = new File("/Users/Riley/cs240/imageViewer/imageViewer/lib/src/main/java/com/example/pictures"+outputFile);
        //File outPut = new File("/users/admin/hale38/classes/cs240/imageViewer/imageViewer/lib/src/main/java/com/example/pictures/"+outputFile);
        StringBuilder builder = new StringBuilder();
        builder.append(type +"\n"+width+' '+height+"\n"+maxColor+"\n");
        for (int i =0; i< height; i++)
        {
            for (int j =0; j<width; j++)
            {
                builder.append(picture[j][i].toString());
            }
            builder.append("\n");
        }
        //System.out.println(builder.toString());
        try {
            FileWriter writer = new FileWriter(outPut);
            writer.write(builder.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void invert()
    {
        for(int i =0; i< height; i++)
        {
            for (int j =0; j<width;j++)
            {
                picture[j][i].inverse();
            }
        }

        writeImage();
    }

    public void grayscale()
    {
        for(int i =0; i< height; i++)
        {
            for (int j =0; j<width;j++)
            {
                picture[j][i].greyscale();
            }
        }
        writeImage();
    }

    public void emboss()
    {
        for (int i =height-1; i>0; i--)
        {
            for(int j =width-1; j >0 ;j--)
            {
                if((i==0)||(j==0))
                {
                    picture[j][i].setV(128);
                }
                else
                {
                    picture[j][i].setV(diffChecker(picture[j][i].red - picture[j-1][i-1].red, picture[j][i].green - picture[j-1][i-1].green,picture[j][i].blue - picture[j-1][i-1].blue));
                }
                /*
                int redDiff = picture[j][i].red - picture[j-1][i-1].red;
                int greenDiff = picture[j][i].green - picture[j-1][i-1].green;
                int blueDiff = picture[j][i].blue - picture[j-1][i-1].blue;
                */


            }
        }
        writeImage();
    }

    private int diffChecker (int redDiff, int greenDiff, int blueDiff)
    {
        int maxDiff =0;
        if(abs(redDiff)>abs(maxDiff))
        {
            maxDiff=redDiff;
        }
        if (abs(greenDiff)>abs(maxDiff))
        {
            maxDiff=greenDiff;
        }
        if(abs(blueDiff)> abs(maxDiff) )
        {
            maxDiff=blueDiff;
        }

        //still need to check for equal differences, check with TA for what that means

        int v = 128 + maxDiff;

        if(v<0)
        {
            v=0;
        }
        else if(v>255)
        {
            v=255;
        }

        return v;
    }


    public void motionblur(int blurLength)
    {
        for (int i = 0;  i < height; i++)
        {
            for (int j =0; j<width-1; j++)
            {

                    int rangeCheck = (j+blurLength);

                    if(rangeCheck>=(width-2))
                    {
                        int adjustBlur=width-j;
                        blur(j,i,adjustBlur);
                    }
                    else{
                        blur(j,i,blurLength);
                    }

            }
           System.out.println(i);
//            writeImage();
        }
        writeImage();

    }

    private void blur(int wPos, int hPos, int length)
    {
        Pixel currentPixel = picture[wPos][hPos];
        int red =0;
        int green=0;
        int blue=0;

        try {

            for (int i = 0; i < length; i++) {
                red += picture[wPos +i][hPos].red;
                green += picture[wPos +i][hPos].green;
                blue += picture[wPos+i][hPos].blue;
            }//add all values of each color together for the alloted length
        } catch (ArrayIndexOutOfBoundsException e){
           System.out.println("failed");
            //
            // writeImage();
        }

        //sets current pixels value to the average
        currentPixel.red = (red/length);
        currentPixel.green = (green/length);
        currentPixel.blue=(blue/length);
    }





}
