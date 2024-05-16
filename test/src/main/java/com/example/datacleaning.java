package com.example;
public class datacleaning {
    // creating the object of the datacleanser class that i have created for loading the data and cleaning.
    datacleanser df = new datacleanser("fundamental_data.csv");
    public void cleaning() {
        // printing the shape of the dataset.
        System.out.println("shape of the data set");
        System.out.println();
        df.shape();
        System.out.println();
        // printing the first 20 rows of the dataset.
        System.out.println("first few columns of the data set");
        System.out.println();
        df.head();
        System.out.println();
        // printing all the columns in the dataset.
        System.out.println("the columns in the data set are:");
        for(String i:df.columns()){
            System.out.println(i);
        }
        System.out.println();
        // checking for the null values in the dataset.
        System.out.println("checking the null values");
        System.out.println();
        df.isnull_sum();
        System.out.println();
        System.out.println("filling the null values with the mean of the perticular column.");
        System.out.println();
        // based of the null values found taking the mean of that column to fill the null values with mean.
        Double mean = df.mean("PB_ratio");
        df.fillna("PB_ratio", mean.toString());
        Double mean1 = df.mean("debt_to_equity");
        df.fillna("debt_to_equity", mean1.toString());
        Double mean2 = df.mean("ROE");
        df.fillna("ROE", mean2.toString());
        Double mean3 = df.mean("EPS");
        df.fillna("EPS", mean3.toString());
        df.isnull_sum();
        System.out.println();
        // finding all the unique values in the categorical column.
        System.out.println("finding the unique values in the categorical column");
        System.out.println();
        for(String i:df.unique("Type")){
            System.out.println(i);
        }
        System.out.println();
        // chaning the category into numerical for doing the machine learning.
        System.out.println("changing the categorical column into numerical column");
        df.encode("Type");
        System.out.println();
        df.head();
        // saving the cleaned data into the csv file for doing the machine learning.
        // df.to_csv("new_datas.csv");
        
    }
    // function for doing the statistical analysis.
    public void statistical(){
        System.out.println();
        // describing the data set
        // printing mean,median,mode,varience,quartiles,min and maximum of all the columns present in the dataset.
        System.out.println("statistical description of the dataset!");
        System.out.println();
        df.describe();
        System.out.println();
        // printing the correlation matrix of the data set.
        System.out.println("correlation matrix of the dataset");
        System.out.println();
        df.corr();
    }
    
}
