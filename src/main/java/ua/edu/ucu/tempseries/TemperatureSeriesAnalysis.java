package ua.edu.ucu.tempseries;


import lombok.Getter;

import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {

    @Getter
    private double[] temperatureSeries;
    private int actualSize = 0;// The filled space
    public static final double MAX_POSSIBLE_TEMPERATURE = 1000;
    public static final double MIN_POSSIBLE_TEMPERATURE = -273;

    public TemperatureSeriesAnalysis() {

    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {

        if (!areTempsRight(temperatureSeries)) {
            throw new InputMismatchException();
        }

        this.temperatureSeries = Arrays.copyOf(temperatureSeries, temperatureSeries.length);
        actualSize = temperatureSeries.length;
    }

    public boolean areTempsRight(double[] temps) {
        for (double temp: temps) {
            if (temp < MIN_POSSIBLE_TEMPERATURE) {
                return false;
            }
        }
        return true;
    }

    public double average() {

        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }

        double sum = 0;
        for (int i = 0; i < actualSize; i++) {
            sum += temperatureSeries[i];
        }

        return sum / temperatureSeries.length;
    }

    public double deviation() {

        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }

        double average = average();
        double squaredDiffSum = 0;

        for (int i = 0; i < actualSize; i++) {
            squaredDiffSum += Math.pow(Math.abs(average - temperatureSeries[i]), 2);
        }

        return Math.sqrt(squaredDiffSum / temperatureSeries.length);
    }

    public double min() {
        return findTempClosestToValue(MIN_POSSIBLE_TEMPERATURE);
    }

    public double max() {
        return findTempClosestToValue(MAX_POSSIBLE_TEMPERATURE);
    }

    public double findTempClosestToZero() {

        return findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) {

        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }

        double biggestDiff = Double.POSITIVE_INFINITY;
        double closestElement = temperatureSeries[0];

        for (int i = 0; i < actualSize; i++) {
            double diff = tempValue - temperatureSeries[i];
            if (Math.abs(diff) < Math.abs(biggestDiff)) {
                biggestDiff = diff;
                closestElement = temperatureSeries[i];
            } else if (Math.abs(diff) == Math.abs(biggestDiff)) {
                if (temperatureSeries[i] > closestElement) {
                    biggestDiff = diff;
                    closestElement = temperatureSeries[i];
                }
            }
        }

        return closestElement;
    }

    public double[] findTempsLessThen(double tempValue) {

        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }

        double[] tempsLessThan = new double[actualSize];
        int idx = 0;
        for(int i = 0; i < actualSize; i++) {
            if (temperatureSeries[i] < tempValue) {
                tempsLessThan[idx] = temperatureSeries[i];
                idx++;
            }
        }
        // At the end, the index will be the size of the array
        tempsLessThan = Arrays.copyOf(tempsLessThan, idx);

        return tempsLessThan;
    }

    public double[] findTempsGreaterThen(double tempValue) {

        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }

        double[] tempsGreaterThan = new double[actualSize];
        int idx = 0;
        for(int i = 0; i < actualSize; i++) {
            if (temperatureSeries[i] > tempValue) {
                tempsGreaterThan[idx] = temperatureSeries[i];
                idx++;
            }
        }
        // At the end, the index will be the size of the array
        tempsGreaterThan = Arrays.copyOf(tempsGreaterThan, idx);

        return tempsGreaterThan;
    }

    public TempSummaryStatistics summaryStatistics() {

        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }

        double average = average();
        double deviation = deviation();
        double max = max();
        double min = min();

        return new TempSummaryStatistics(average, deviation, min, max);
    }

    public int addTemps(double... temps) {

        if (!areTempsRight(temps)) {
            throw new InputMismatchException();
        }

        for (double temp : temps) {
            if (temperatureSeries.length == actualSize) {
                temperatureSeries = Arrays.copyOf(temperatureSeries, temperatureSeries.length * 2);
            }
            temperatureSeries[actualSize] = temp;
            actualSize++;
        }
        return 0;
    }
}
