package ua.edu.ucu.tempseries;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.InputMismatchException;

public class TemperatureSeriesAnalysisTest {

    private TemperatureSeriesAnalysis emptyTemperatureSeries;
    private TemperatureSeriesAnalysis oneElementTemperatureSeries;
    private TemperatureSeriesAnalysis sampleTemperatureSeries1;
    private TemperatureSeriesAnalysis sampleTemperatureSeries2;
    private TemperatureSeriesAnalysis sampleTemperatureSeries3;


    @BeforeEach
    public void setUp(){
        emptyTemperatureSeries = new TemperatureSeriesAnalysis(new double[] {});
        oneElementTemperatureSeries = new TemperatureSeriesAnalysis(new double[] {5.5});
        sampleTemperatureSeries1 = new TemperatureSeriesAnalysis(new double[] {1.0, 2.0, 3.0, 4.0});
        sampleTemperatureSeries2 = new TemperatureSeriesAnalysis(new double[] {-2.0, -1.0, 0.0, 1.0, 2.0});
        sampleTemperatureSeries3 = new TemperatureSeriesAnalysis(new double[] {1.5, -0.5, 0.5, -9.5});
    }

    @Test
    public void testAverageWithOneElementArray() {
        // setup input data and expected result
        double[] temperatureSeries = {-1.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = -1.0;

        // call tested method
        double actualResult = seriesAnalysis.average();

        // compare expected result with actual result
        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAverageWithEmptyArray() {
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // expect exception here
        seriesAnalysis.average();
    }

    @Test
    public void testAverage() {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 1.0;

        double actualResult = seriesAnalysis.average();
        
        assertEquals(expResult, actualResult, 0.00001);        
    }
    @Test
    public void testFindClosestToZeroWithoutExceptions() {
        setUp();
        assertEquals(5.5, oneElementTemperatureSeries.findTempClosestToZero(), 0.00001);
        assertEquals(1.0, sampleTemperatureSeries1.findTempClosestToZero(), 0.00001);
        assertEquals(0.0, sampleTemperatureSeries2.findTempClosestToZero(), 0.00001);
        assertEquals(0.5, sampleTemperatureSeries3.findTempClosestToZero(), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindClosestToZeroWithExceptions() {
        setUp();
        emptyTemperatureSeries.findTempClosestToZero();
    }
    @Test
    public void testFindTempClosestToValueWithoutExceptions() {
        setUp();
        assertEquals(5.5, oneElementTemperatureSeries.findTempClosestToValue(5.0), 0.00001);
        assertEquals(4.0, sampleTemperatureSeries1.findTempClosestToValue(5.0), 0.00001);
        assertEquals(-1.0, sampleTemperatureSeries2.findTempClosestToValue(-0.7), 0.00001);
        assertEquals(0.5, sampleTemperatureSeries3.findTempClosestToValue(0.45), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindTempClosestToValueWithExceptions() {
        setUp();
        emptyTemperatureSeries.findTempClosestToValue(0.0);
    }

    @Test
    public void testDeviation() {
        setUp();
        assertEquals(0, oneElementTemperatureSeries.deviation(), 0.00001);
        assertEquals(1.118033988749895, sampleTemperatureSeries1.deviation(), 0.00001);
        assertEquals(1.4142135623731, sampleTemperatureSeries2.deviation(), 0.00001);
        assertEquals(4.387482193696061, sampleTemperatureSeries3.deviation(), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeviationWithException() {
        setUp();
        emptyTemperatureSeries.deviation();
    }

    @Test
    public void testMin() {
        setUp();
        assertEquals(5.5, oneElementTemperatureSeries.min(), 0.00001);
        assertEquals(1.0, sampleTemperatureSeries1.min(), 0.00001);
        assertEquals(-2.0, sampleTemperatureSeries2.min(), 0.00001);
        assertEquals(-9.5, sampleTemperatureSeries3.min(), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMinWithException() {
        setUp();
        emptyTemperatureSeries.min();
    }

    @Test
    public void testMax() {
        setUp();
        assertEquals(5.5, oneElementTemperatureSeries.max(), 0.00001);
        assertEquals(4.0, sampleTemperatureSeries1.max(), 0.00001);
        assertEquals(2.0, sampleTemperatureSeries2.max(), 0.00001);
        assertEquals(1.5, sampleTemperatureSeries3.max(), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMaxWithException() {
        setUp();
        emptyTemperatureSeries.max();
    }

    @Test
    public void testFindTempsLessThen() {
        setUp();
        assertArrayEquals(new double[]{5.5}, oneElementTemperatureSeries.findTempsLessThen(5.6), 0.00001);
        assertArrayEquals(new double[]{1.0, 2.0}, sampleTemperatureSeries1.findTempsLessThen(2.1), 0.00001);
        assertArrayEquals(new double[]{-2.0}, sampleTemperatureSeries2.findTempsLessThen(-1.5), 0.00001);
        assertArrayEquals(new double[]{}, sampleTemperatureSeries3.findTempsLessThen(-10), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindTempsLessThenWithException() {
        setUp();
        emptyTemperatureSeries.findTempsLessThen(0.0);
    }

    @Test
    public void testFindTempsGreaterThen() {
        setUp();
        assertArrayEquals(new double[]{}, oneElementTemperatureSeries.findTempsGreaterThen(5.6), 0.00001);
        assertArrayEquals(new double[]{3.0, 4.0}, sampleTemperatureSeries1.findTempsGreaterThen(2.1), 0.00001);
        assertArrayEquals(new double[]{-1.0, 0.0, 1.0, 2.0}, sampleTemperatureSeries2.findTempsGreaterThen(-1.5), 0.00001);
        assertArrayEquals(new double[]{}, sampleTemperatureSeries3.findTempsGreaterThen(10), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindTempsGreaterThenWithException() {
        setUp();
        emptyTemperatureSeries.findTempsGreaterThen(0.0);
    }
    @Test
    public void testSummaryStatistics() {
        setUp();
        assertEquals(new TempSummaryStatistics(5.5, 0, 5.5, 5.5).toString(), oneElementTemperatureSeries.summaryStatistics().toString());
        assertEquals(new TempSummaryStatistics(2.5, 1.118033988749895, 1.0, 4.0).toString(), sampleTemperatureSeries1.summaryStatistics().toString());
        assertEquals(new TempSummaryStatistics(0.0, 1.4142135623730951, -2.0, 2.0).toString(), sampleTemperatureSeries2.summaryStatistics().toString());
        assertEquals(new TempSummaryStatistics(-2.0, 4.387482193696061, -9.5, 1.5).toString(), sampleTemperatureSeries3.summaryStatistics().toString());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testSummaryStatisticsWithException() {
        setUp();
        emptyTemperatureSeries.summaryStatistics();
    }
    @Test
    public void testAddTemps() {
        setUp();
        sampleTemperatureSeries1.addTemps(5.0, 6.0);
        assertArrayEquals(new double[]{1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 0.0, 0.0},sampleTemperatureSeries1.getTemperatureSeries() , 0.00001);
    }

    @Test(expected = InputMismatchException.class)
    public void testAddTempsWithException() {
        setUp();
        sampleTemperatureSeries1.addTemps(TemperatureSeriesAnalysis.MIN_POSSIBLE_TEMPERATURE - 1);
    }
}
