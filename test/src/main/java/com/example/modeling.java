package com.example;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;



// creating a class for doing the machine learning.
public class modeling {
    // function for normalizing the data we are going to get for predicting.
    static Double[] normalize(Double[] input){
        Double[] min ={0.0,5.0,-100.0,-50.0,0.0,6.565E-4,0.003582595,-19.9717487,0.147519038};
        Double[] max = {2.0,1995099.0,250.0,100.0,50.0,2.901219722,99.8708998,59.91933605,99.37462035};
        for(int i=0;i<input.length;i++){
            input[i] = (input[i]-min[i])/(max[i]-min[i]);
        }
        return input;

    }
    // function for taking feature inputs from user.
    static Double[] take_inputs(){
        Scanner scan = new Scanner(System.in);
        System.out.println("enter the market capital type of the stock");
        System.out.println("large_cap:0,mid_cap:1,small_cap:2");
        Double in1 = Double.parseDouble(scan.nextLine());
        System.out.println("enter the market_capital");
        Double in2 = Double.parseDouble(scan.nextLine());
        System.out.println("enter the PE_ratio");
        Double in3 = Double.parseDouble(scan.nextLine());
        System.out.println("enter the industry_PE ratio");
        Double in4 = Double.parseDouble(scan.nextLine());
        System.out.println("enter the PB_ratio");
        Double in5 = Double.parseDouble(scan.nextLine());
        System.out.println("enter the debt_to_equity");
        Double in6 = Double.parseDouble(scan.nextLine());
        System.out.println("enter the ROE");
        Double in7 = Double.parseDouble(scan.nextLine());
        System.out.println("enter the EPS");
        Double in8 = Double.parseDouble(scan.nextLine());
        System.out.println("enter the dividend_yield");
        Double in9 = Double.parseDouble(scan.nextLine());
        Double[] list = {in1,in2,in3,in4,in5,in6,in7,in8,in9};

        return list;
    }
    // function for creating the model
    public  void model() {
        // creating the object of the datacleanser class that i have created for doing the data manipulation.
        datacleanser data = new datacleanser("new_datas.csv");
        System.out.println();
        System.out.println();
        // normalizing the data for doing the machine learning .
        data.normalize();
        System.out.println();
        // taking the labels that we want to predict into the output variable.
        Double[] output = data.column("label");
        data.drop("label");
        // taking all features into the inputs variable.
        Double[][] inputs = data.getdataframe();
        // creating the object of the regression algorithm class that i have created.
        Regression model = new Regression(0.75,2000);
        // feeding the data into the model for training.
        model.fit(inputs,output);
        // normalizing the inputs that we are getting from the user.
        Double[] input = normalize(take_inputs());
        // predicting the output.
        Double prediction = model.predict(input);
        System.out.println();
        System.out.print("the given stock is: ");
        System.out.println((prediction*7)*(25/2)+" % "+"fundamentally strong!");

    }
}
