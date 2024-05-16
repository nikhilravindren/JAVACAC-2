package com.example;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Iterator;

import javax.management.openmbean.ArrayType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

// creating a class called datacleaner for diong all the cleaning , statistical manupulations,
// and statistical anaysis.
public class datacleanser {
    private String[][] dataframe;
    private String[] columns;
    private int rows;
    datacleanser(){
        this.dataframe = new String[200000][];
    }

    // constructer which takes the filename as string and loading the data into the class.
    datacleanser(String filename){
        count_rows(filename);
        dataframer(filename);
    }

    // function for getting the number of data points or the number of rows in the data set. 
    private void count_rows(String filename){
        try{
            this.rows=-1;
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream(filename);
            BufferedReader scan = new BufferedReader(new InputStreamReader(is));
            if(is != null){
                String str = "";
                while ((str = scan.readLine()) != null) {
                    this.rows++;
                }
            }
                
        }catch(IOException e){
            System.out.println("cant read this file");
            e.printStackTrace();
        }
    }

    // the function for reading the csv file and and creating an data frame out of it.
    private void dataframer(String filename){
        String[][] tem = new String[this.rows][];
        int counter = 0;
        try{
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream(filename);
            BufferedReader scan = new BufferedReader(new InputStreamReader(is));
            for (int i=0;i<(tem.length+1);i++){
                String[] line = scan.readLine().split(",");
                if(i>0){
                    tem[counter] = line;
                    counter++;
                }
                else{
                    
                    this.columns =line;
                }
            }
        }catch(IOException e){
            System.out.println("cant read this file!");
            e.printStackTrace();
        }
        this.dataframe = tem;
    }

    // function for getting a perticular column data from the dataframe.
    public String[] print_data(String column){
        String[] list = new String[this.rows];
        int counter = 0;
        int index = -1;
        for(int i=0;i<this.columns.length;i++){
            if (this.columns[i].equals(column)){
                index=i;
                break;
            }
        }
        if (index !=-1){
            for (int i=0;i<this.rows;i++){
                list[counter] = this.dataframe[i][index];
                counter++;            
            }
        }
        else{
            System.out.println("there is no such column");
        } 
        return list; 
    }

    // function for getting all the column names in an list. 
    public String[] columns(){
        return this.columns;
    }

    // function for returning the entire dataset.
    public Double[][] getdataframe(){
        Double[][] data = new Double[this.dataframe.length][this.dataframe[0].length];
        
        for(int i=0;i<this.dataframe.length;i++){
            for(int j=0;j<this.dataframe[i].length;j++){
                data[i][j] = Double.parseDouble(this.dataframe[i][j]);
            }
        }

        
        return data;
    }

    // function for printing the shape of the data set ,that is the number of columns and 
    // number of rows.
    public void shape(){
        System.out.println(this.rows+","+this.columns.length);
    }

    // function for printing the dataframe.
    public void print() {
        for (String column : this.columns) {
            System.out.printf("%-14s", column); 
        }
        System.out.println();
    
        for (String[] row : this.dataframe) {
            for (String value : row) {
                if (value.isEmpty()) {
                    System.out.printf("%-14s", "NaN");
                } else {
                    System.out.printf("%-14s", value); 
                }
            }
            System.out.println();
        }
    }
    

    // function for printing the first 20 rows in the dataset.
    public void head() {
        for (String column : this.columns) {
            System.out.printf("%-14s", column); 
        }
        System.out.println();
    
        for (int i = 0; i < 20; i++) {
            for (String value : this.dataframe[i]) {
                if (value.isEmpty()) {
                    System.out.printf("%-14s", "NaN");
                } else {
                    System.out.printf("%-14s", value);
                }
            }
            System.out.println();
        }
    }
    
    // function for printing the the number of null values in each column.
    public void isnull_sum(){
        for(String i:this.columns){
            Integer null_counter = 0;
            for(String j:print_data(i)){
                if(j.equals("")){
                    null_counter++;
                }
                
            }
            System.out.println(i+" : "+(null_counter.toString()));
            
        }
    }

    // function for returning the mean of a perticular column.
    public double mean(String column){
        String[] col = print_data(column);
        double sum = 0;
        int count = 0;
        for(String i:col){
            if(!i.equals("")){
                sum += Double.parseDouble(i);
                count++;
            }
        }
        return sum/count;
    }
    
    // function for printing the mean of all the columns.
    public void mean(){
        for(String i:this.columns){
            System.out.println(i+" : "+mean(i));
        }
    }

    // function for finding the median value in a perticular column.
    public double median(String column){
        sort_by(column, true);
        Double[] col = column(column);
        if ((col.length%2) == 0){
            return (col[(col.length/2)-1]+col[(col.length/2)])/2;
        }
        else{
            return col[(col.length/2)];
        }
    }
    // function for printing median of all the columns.
    public void median(){
        for(String column:this.columns){
            System.out.println(column+" : "+median(column));
        }
    }
    // function for finding the mode of a perticular column.
    public  Double mode(String column){
        int[] values = new int[unique(column).length];
        int counter = 0;
        for(String i:unique(column)){
            int count = 0;
            for(String j:print_data(column)){
                if(i.equals(j)){
                    count++;
                }
            }
            values[counter] = count;
            counter++;
        }
        int index = -1;
        int max = values[0];
        for(int i=0;i<values.length;i++){
            if(values[i]>max){
                max = values[i];
            }
        }
        for(int i=0;i<values.length;i++){
            if(values[i] == max){
                index = i;
                break;
            }
        }
        return Double.parseDouble(unique(column)[index]);
    }
    // function for printing mode of all the columns.
    public void mode(){
        for(String column:this.columns){
            System.out.println(column+" : "+mode(column));
        }
    }
    // function for finding the quartile of the column.
    public Double que1(String column){
        sort_by(column, true);
        Double[] col = column(column);
        if ((col.length%2) == 0){
            return (col[(col.length/4)-1]+col[(col.length/4)])/2;
        }
        else{
            return col[(col.length/4)];
        }
    }

    public Double que3(String column){
        sort_by(column, true);
        Double[] col = column(column);
        if ((col.length%2) == 0){
            return (col[((col.length*3)/4)-1]+col[((col.length*3)/4)])/2;
        }
        else{
            return col[((col.length*3)/4)];
        }

    }
    // function for printing all the basic statistics in the data set.
    public void describe(){
        System.out.print("\t");
        for (String column : this.columns) {
            System.out.printf("%12s", column);
        }
        System.out.println();
        System.out.println();
        System.out.printf("%12s","mean");
        for(int i=0;i<this.columns.length;i++){
            String formattedNumber = String.format("%.4f", mean(this.columns[i]));
            System.out.printf("%12s", formattedNumber);
        }
        System.out.println();
        System.out.println();
        System.out.printf("%12s","median");
        for(int i=0;i<this.columns.length;i++){
            String formattedNumber = String.format("%.4f", median(this.columns[i]));
            System.out.printf("%12s", formattedNumber);
        }
        System.out.println();
        System.out.println();
        System.out.printf("%12s","mode");
        for(int i=0;i<this.columns.length;i++){
            String formattedNumber = String.format("%.4f", mode(this.columns[i]));
            System.out.printf("%12s", formattedNumber);
        }
        System.out.println();
        System.out.println();
        System.out.printf("%12s","std");
        for(int i=0;i<this.columns.length;i++){
            String formattedNumber = String.format("%.4f", std(this.columns[i]));
            System.out.printf("%12s", formattedNumber);
        }
        System.out.println();
        System.out.println();
        System.out.printf("%12s","min");
        for(int i=0;i<this.columns.length;i++){
            String formattedNumber = String.format("%.4f", Min(this.columns[i]));
            System.out.printf("%12s", formattedNumber);
        }
        System.out.println();
        System.out.println();
        System.out.printf("%12s","max");
        for(int i=0;i<this.columns.length;i++){
            String formattedNumber = String.format("%.4f", Max(this.columns[i]));
            System.out.printf("%12s", formattedNumber);
        }
        System.out.println();
        System.out.println();
        System.out.printf("%12s","Q1");
        for(int i=0;i<this.columns.length;i++){
            String formattedNumber = String.format("%.4f", que1(this.columns[i]));
            System.out.printf("%12s", formattedNumber);
        }
        System.out.println();
        System.out.println();
        System.out.printf("%12s","Q3");
        for(int i=0;i<this.columns.length;i++){
            String formattedNumber = String.format("%.4f", que3(this.columns[i]));
            System.out.printf("%12s", formattedNumber);
        }
        System.out.println();
    }

    // function for returning  the varience of a perticular column. 
    public double varience(String column){
        String[] col = print_data(column);
        double mean = mean(column);
        int count = -1;
        double variation = 0;
        for(String i:col){
            if(!i.equals("")){
            variation += (Double.parseDouble(i)-mean)*(Double.parseDouble(i)-mean);
            count++;
            }
        }
        return variation/count;
    }

    // function for printing the varience of all the columns.
    public void varience(){
        for(String i:this.columns){
            System.out.println(i+" : "+varience(i));
        }
    }

    // function for returning the standerd deviation of a perticular column.
    public double std(String column){
        return Math.sqrt(varience(column));
    }

    // function for printing the standerd deviation of all the columns.
    public void std(){
        for(String i:this.columns){
            System.out.println(i+" : "+std(i));
        }
    }

    // function for returning the maximum value in a column.
    public double Max(String column){
        String[] col = print_data(column);
        Double Max = Double.parseDouble(col[0]);
        for(String i:col){
            if(!i.equals("")){
            if(Double.parseDouble(i)>Max){
                Max = Double.parseDouble(i);
            }
        }
        }
        return Max;
    }

    // function for returning the minimum value in a column.
    public double Min(String column){
        String[] col = print_data(column);
        Double Min = Double.parseDouble(col[0]);
        for(String i:col){
            if(!i.equals("")){
            if(Double.parseDouble(i)<Min){
                Min = Double.parseDouble(i);
            }
        }
        }
        return Min;
    }

    // function for standardizing the entire dataset.
    public void standardize(){
        for(int i=0;i<this.columns.length;i++){
            String[] col = print_data(this.columns[i]);
            double mean = mean(this.columns[i]);
            double std = std(this.columns[i]);
            for(int j=0;j<col.length;j++){
                if(!col[j].equals("")){
                    Double value = ((Double.parseDouble(this.dataframe[j][i])-mean)/std);
                    this.dataframe[j][i] = value.toString();
                }
            }

        }
    }

    // function for normalizing the entire dataset.
    public void normalize(){
        for(int i=0;i<this.columns.length;i++){
            String[] col = print_data(this.columns[i]);
            double max = Max(this.columns[i]);
            double min = Min(this.columns[i]);
            for(int j=0;j<col.length;j++){
                if(!col[j].equals("")){
                    Double value = ((Double.parseDouble(this.dataframe[j][i])-min)/(max-min));
                    this.dataframe[j][i] = value.toString();
                }
            }
        }
    }

    // function for replacing a perticular value in an perticular column with a new value.
    public void replace(String column,String old_value,String new_value){
        String[] col = print_data(column);
        int index = -1;
        for(int j=0;j<this.columns.length;j++){
            if (this.columns[j].equals(column)){
                index =j;
            }
        }

        for(int i=0;i<col.length;i++){
            if (this.dataframe[i][index].equals(old_value)){
                this.dataframe[i][index] = new_value;
            }
        }
    }

    // function for filling all the null values in the data set with a perticular value 
    // which is given by the user.
    public void fillna(String column,String value){
        String[] col = print_data(column);
        int index = -1;
        for(int j=0;j<this.columns.length;j++){
            if (this.columns[j].equals(column)){
                index =j;
            }
        }

        for(int i=0;i<col.length;i++){
            if (this.dataframe[i][index].equals("")){
                this.dataframe[i][index] = value;
            }
        }

    }

    // function for finding all the unique values in an perticular column.
    public String[] unique(String column){
        String[] col = print_data(column);
        String[] tem = new String[this.dataframe.length];
        int counter  = 0;
        for(int i=0;i<col.length;i++){
            int index = 0;
            for(int j=0;j<counter;j++){
                if(tem[j].equals(col[i])){
                    index = 1;
                }
            }
            if(index == 0){
                tem[counter] = col[i];
                counter++;
            }
        }

        String[] tem1 = new String[counter];
        for(int i=0;i<counter;i++){
            tem1[i] = tem[i];
        }

        return tem1;
    }

    // function for encoding the data in an perticular column.
    // that is it will convert an categorical column into numerical based on certain logic.
    public void encode(String column){
        String[] unique = unique(column);
        for(Integer i=0;i<unique.length;i++){
            replace(column, unique[i], i.toString());
        }
    }

    // function for droping an perticular column.
    public void drop(String column){
        ArrayList<String> col = new ArrayList<String>();
        Collections.addAll(col,this.columns);
        int index = col.indexOf(column);
        col.remove(column);

        this.columns = col.toArray(new String[col.size()]);


        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        for (String[] row : this.dataframe) {
            ArrayList<String> new_row = new ArrayList<>();
            Collections.addAll(new_row, row);
            data.add(new_row);
        }

        for(ArrayList<String> i:data){
            i.remove(index);
        }
        
        String[][] arr = new String[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            ArrayList<String> row = data.get(i);
            arr[i] = row.toArray(new String[row.size()]);
        }
        this.dataframe = arr; 


    }

    // function for droping rows which has null values in it.
    public void dropna(){
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        for (String[] row : this.dataframe) {
            ArrayList<String> new_row = new ArrayList<>();
            Collections.addAll(new_row, row);
            data.add(new_row);
        }
        
        Iterator<ArrayList<String>> iterator = data.iterator();
        while (iterator.hasNext()) {
            ArrayList<String> row = iterator.next();
            if (row.contains("")) {
                iterator.remove();
            }
        }

        String[][] arr = new String[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            ArrayList<String> row = data.get(i);
            arr[i] = row.toArray(new String[row.size()]);
        }
        this.dataframe = arr;
        this.rows = this.dataframe.length;
    }

    // function for creating an csv file with all the changes we did in that dataframe.
    public void to_csv(String filename){
        try{
            FileWriter file = new FileWriter(filename,true);
            for(int i=0;i<this.columns.length;i++){
                if(i==0){
                    file.append(this.columns[i]);
                }
                else{
                    file.append(","+this.columns[i]);
                }
            }
            file.append("\n");
            for(int i=0;i<this.dataframe.length;i++){
                for(int j=0;j<this.dataframe[i].length;j++){
                    if(j==0){
                        file.append(this.dataframe[i][j]);
                    }
                    else{
                        file.append(","+this.dataframe[i][j]);
                    }
                }
                file.append("\n");
            }
            file.close();
        }catch(IOException e){
            System.out.println("can't write into this file");
            e.printStackTrace();
        }
    }

    // function returing a perticular column as double values.
    public Double[] column(String column){
        String[] col = print_data(column);
        Double[] tem = new Double[col.length];
        int count = 0;

        for(String i:col){
            tem[count] = Double.parseDouble(i);
            count++;
        }
        return tem;
    }

    // function for finding the correlation between two columns
    public Double corr(String column1,String column2){
        Double [] col1 = column(column1);
        Double [] col2 = column(column2);
        Double m1 = mean(column1);
        Double m2 = mean(column2);
        Double n = 0.0;
        for(int i=0;i<col1.length;i++){
            n +=((col1[i]-m1)*(col2[i]-m2));
        }

        Double d1 = 0.0;
        Double d2 = 0.0;

        for(int i=0;i<col1.length;i++){
            d1 +=(col1[i]-m1)*(col1[i]-m1);
            d2 +=(col2[i]-m2)*(col2[i]-m2);
        }
        Double val = n/(Math.sqrt((d1*d2)));

        return val;
    }

    // function for creating the list for all the correlation
    private String[][] correlation(){
        String[][] list = new String[this.columns.length][this.columns.length];
        int row = 0;
        for(String column:this.columns){
            int col = 0;
            for(String column1:this.columns){
                String formattedNumber = String.format("%.4f", corr(column, column1));
                list[row][col] = formattedNumber;
                col++;
            }
            row++;
        }
        return list;
    }

    // function for printing all the correlation in an matrix.
    public void corr() {
        System.out.print("\t");
        for (String column : this.columns) {
            System.out.printf("%10s", column);
        }
        System.out.println();
    
        for (int i = 0; i < correlation().length; i++) {
            System.out.printf("%10s", this.columns[i]);
            for (String value : correlation()[i]) {
                System.out.printf("%10s", value);
            }
            System.out.println();
        }
    }

    // function for grouping the numerical column based on an categorical column and 
    // it will fing the group wise count,sum and average.
    public void groupby(String category_column,String numerical_column,String type){
        String[] col1 = print_data(category_column);
        Double[] col2 = column(numerical_column);
        String[] uniques = unique(category_column);

        for(String unique:uniques){
            int counter = 0;
            Double sum = 0.0;
            for(int i=0;i<col1.length;i++){
                if(col1[i].equals(unique)){
                    counter++;
                    sum += col2[i];
                }
            }
            if (type.equals("count")){
                System.out.println(unique+" : "+counter);           
             }
             else if (type.equals("sum")){
                System.out.println(unique+" : "+sum);
             }
             else if(type.equals("avg")){
                System.out.println(unique+" : "+(sum/counter));
             }
        }
    }


    // function for sorting the dataframe based on perticular numerical column.
    public void sort_by(String numerical_column,boolean ascending){
        int index = -1;
        for(int i=0;i<this.columns.length;i++){
            if(this.columns[i].equals(numerical_column)){
                index = i;
                break;
            }
        }

        for(int i=0;i<this.dataframe.length;i++){
            for(int j=i+1;j<this.dataframe.length;j++){
                if(ascending == true){
                if(Double.parseDouble(this.dataframe[i][index])>Double.parseDouble(this.dataframe[j][index])){
                    String[] tem = this.dataframe[i];
                    this.dataframe[i] = this.dataframe[j];
                    this.dataframe[j] = tem;
                }
            }
            else{
                if(Double.parseDouble(this.dataframe[i][index])<Double.parseDouble(this.dataframe[j][index])){
                    String[] tem = this.dataframe[i];
                    this.dataframe[i] = this.dataframe[j];
                    this.dataframe[j] = tem;
                }
            }
            }
        }

    }
    

}
