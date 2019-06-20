package com.innodealing.util;

import java.util.Arrays;

public class SimpleStatistics {
    private Double[] data;
    private int size;   

    public SimpleStatistics(Double[] data) 
    {
        this.data = data;
        size = data.length;
    }   

    public Double getMean()
    {
        double sum = 0.0;
        for(double a : data)
            sum += a;
        return sum/size;
    }

    public Double getVariance()
    {
        double mean = getMean();
        double temp = 0;
        for(double a :data)
            temp += (a-mean)*(a-mean);
        return temp/size;
    }

    public Double getStdDev()
    {
        return Math.sqrt(getVariance());
    }

    public Double median() 
    {
       Arrays.sort(data);

       if (data.length % 2 == 0) 
       {
          return (data[(data.length / 2) - 1] + data[data.length / 2]) / 2.0;
       } 
       return data[data.length / 2];
    }
}
