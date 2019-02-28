package com.example;

public class Pixel {
    int red;
    int green;
    int blue;

    public Pixel(int r, int g, int b){
        red =r;
        green=g;
        blue=b;

    }
    public void inverse()
    {
        int index =0;
        int reverse [] = new int[256];
        for (int i = 255; i > 0; i--)
        {
            reverse [index] = i;
            index++;
        }

        red = reverse[red];
        green = reverse[green];
        blue = reverse[blue];

    }

    public void setV(int v)
    {
        red=v;
        green=v;
        blue=v;
    }


    public void greyscale()
    {
        int average = ((red + green + blue)/3);
        red =average;
        green=average;
        blue=average;
    }

    public String toString()
    {
        StringBuilder values = new StringBuilder();
        values.append(red +" "+green+" " + " "+ blue +" ");
        return values.toString();
    }


}
