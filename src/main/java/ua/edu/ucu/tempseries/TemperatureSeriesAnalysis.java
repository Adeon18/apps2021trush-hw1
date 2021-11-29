package ua.edu.ucu.tempseries;


import lombok.Getter;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class TemperatureSeriesAnalysis {

    @Getter
    private double[] temperatureSeries;
    private static final double MAX_POSSIBLE_TEMPERATURE = 1000;
    private static final double MIN_POSSIBLE_TEMPERATURE = -273;
    @Getter int actual_size;// The filled space

    public TemperatureSeriesAnalysis() {

    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {

        if (!areTempsRight(temperatureSeries)) {
            throw new InputMismatchException();
        }

        this.temperatureSeries = Arrays.copyOf(temperatureSeries, temperatureSeries.length);
        actual_size = temperatureSeries.length;
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

        double sum = 0;

        for (int i = 0; i < actual_size; i++) {
            sum += temperatureSeries[i];
        }

        return sum / temperatureSeries.length;
    }

    public double deviation() {
        double average = average();

        double squaredDiffSum = 0;

        for (int i = 0; i < actual_size; i++) {
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

        for (int i = 0; i < actual_size; i++) {
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
        return null;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        return null;
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

        for (double temp : temps) {
            if (temperatureSeries.length == actual_size) {
                temperatureSeries = Arrays.copyOf(temperatureSeries, temperatureSeries.length * 2);
            }
            temperatureSeries[actual_size] = temp;
            actual_size++;
        }
        return 0;
    }

    public static void main(String[] args) {
        double[] temperatureSeries = {-1.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        seriesAnalysis.addTemps(5.0, 777.777, 15.9, 9.9);
        System.out.println(Arrays.toString(seriesAnalysis.getTemperatureSeries()));
        System.out.println(seriesAnalysis.getTemperatureSeries().length);
        System.out.println(seriesAnalysis.getActual_size());

    }
}
