package com.example;

import java.util.Scanner;

/**
 * Created by hale38 on 1/9/18.
 */

public class ImageEditor {
    public static void main(String[] args){
        System.out.println(args[0]);

        image worker = new image(args[0],args[1]);
        String option = args[2];
        switch(option){
            case "invert":

                worker.invert();
                break;
            case "grayscale":
                worker.grayscale();
                break;
            case "emboss":
                worker.emboss();
                break;
            case "motionblur":
                Integer blurLength = new Integer(args[3]);
                worker.motionblur(blurLength);
                break;

        }

    }

}

