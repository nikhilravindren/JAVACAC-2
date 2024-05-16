package com.example;
class Regression {
    // declearing all the data members in the regression class.
    private Double[][] inputs;
    private Double[] output;
    private double[] wieghts;
    private double bias;
    private double[] prediction;
    private double learning_rate;
    private double loss;
    private double[] w_slope;
    private double b_slope;
    private int iterations;
    // defualt constructor for initialization.
    Regression() {
        this.bias = 0.00f;
        this.learning_rate = 0.01;
        this.loss = 0;
        this.iterations = 1000;
    }
    // parameterized constructor which takes learning rate and the number of iterations as inputs.
    Regression(double learning_rate, int iterations) {
        this.bias = 0.00f;
        this.learning_rate = learning_rate;
        this.loss = 0;
        this.iterations = iterations;
    }
    // function which learning the pattern or mapping the inuts into the output over iterations.
    public void fit(Double[][] inputs, Double[] output) {
        this.inputs = inputs;
        this.output = output;
        this.wieghts = new double[this.inputs[0].length];
        this.prediction = new double[this.output.length];
        for (int i = 0; i < this.iterations; i++) {
            this.w_slope = new double[this.inputs[0].length];
            predicting();
            loss();
            find_slpoe();
            new_wieghts();
        }
    }
    // function for predicting the output from the unseen data.
    public Double predict(Double[] input){
        double p = 0;
        for (int j = 0; j < input.length;j++) {
            p += input[j] * this.wieghts[j];
        }
        return p+this.bias;
    }
    // function for predicting the output while learning.
    private void predicting() {
        for (int i = 0; i < this.inputs.length; i++) {
            double p = 0;
            for (int j = 0; j < this.inputs[i].length; j++) {
                p += this.inputs[i][j] * this.wieghts[j];
            }
            this.prediction[i] = p+this.bias;
        }
    }
    // function which calculating our loss in the prediction while learning.
    private void loss() {
        double error = 0;
        for (int i = 0; i < this.prediction.length; i++) {
            error += ((this.output[i] - this.prediction[i]) * (this.output[i] - this.prediction[i]));
        }
        this.loss = error / (2 * (this.prediction.length));
    }
    // function which finding the slope of the loss function over iterations.
    private void find_slpoe() {
        for (int i = 0; i < this.w_slope.length; i++) {
            for (int j = 0; j < this.output.length; j++) {
                this.w_slope[i] += (-this.output[j] + this.prediction[j]) * inputs[j][i] ;
            }
            this.w_slope[i] = w_slope[i] / (this.output.length);
        }
        for (int k = 0; k < this.output.length; k++) {
            this.b_slope += (-this.output[k] + this.prediction[k]);
        }
        this.b_slope = this.b_slope / (this.output.length);
    }
    // function for updating the wieghts of each features over iteration based 
    // on the slope and the learning rate.
    private void new_wieghts() {
        for (int i = 0; i < this.wieghts.length; i++) {
            this.wieghts[i] = this.wieghts[i] - (this.learning_rate * this.w_slope[i]);
        }
        this.bias = this.bias - (this.learning_rate * this.b_slope);
    }

}