

package com.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        datacleaning clean = new datacleaning();
        Visualization visual = new Visualization();
        modeling model = new modeling();

        
            System.out.println("WELCOME TO MY ANALYSIS AND MODELING OF FUNDAMENTALS OF STOCKS");
            System.out.println("*****************************************************************************************************************");
            System.out.println("this contains the cleaning of the dataset,statistical analysis,vishualization");
            System.out.println("and the machine learning prediction.");
            System.out.println("*****************************************************************************************************************");
            System.out.println("DATASET DESCRIPTION");
            System.out.println("the dataset i took from kaggle but it hasn't labeled ,but i did based on my experience");
            System.out.println("in the stock market.and the label is the fundamental rating of the stock,based of the columns");
            System.out.println("this data set contains the following columns.");
            System.out.println("market capital type-in which category the company is falling.");
            System.out.println("market_capital-how much the company valuation");
            System.out.println("PE_ratio-is a price to earning ratio which indicating the company cashflow ");
            System.out.println("industry_PE-is a price to earning ratio which indicating the company cashflow in the perticular industry ");
            System.out.println("PB_ratio-price to book ratio indicate that company valuation based on it book value");
            System.out.println("debt_to_equity-indicating the company's liabalities");
            System.out.println("ROE-indicating how much profit got by the investor for one dollor investment.");
            System.out.println("EPS-indicating the average return of one share of the company. ");
            System.out.println("dividend_yield-how much portion of company share has been given to the share holders.");
            System.out.println("*****************************************************************************************************************");
       while(true){
        System.out.println("enter 1 for the cleaning the data set.");
        System.out.println("enter 2 for the statistical analysis.");
        System.out.println("enter 3 for the visualization of the data.");
        System.out.println("enter 4 for the machine learning and predicting the rating.");
        System.out.println("enter 5 for exiting");
        System.out.println("*****************************************************************************************************************");
        System.out.print("enter your choice :");
        int choice = Integer.parseInt(scan.nextLine());
        switch (choice) {
            case 1:
            clean.cleaning();
            break;
            case 2:
            clean.statistical();
            break;
            case 3:
            visual.chart1();
            visual.chart2();
            visual.chart3();
            break;
            case 4:
            model.model();
            break;
            case 5:
            System.out.println("Exiting.....");
            System.exit(0);
            break;
            default:
            break;
        }
    }
               
       
    }
}