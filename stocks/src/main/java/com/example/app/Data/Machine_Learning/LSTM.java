package com.example.app.Data.Machine_Learning;


import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.conf.BackpropType;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerMinMaxScaler;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LSTM {

    /**
     * Attributes for the LSTM model using Keras library
     * Represents parameters for the model to function
     * (This can be changed and improved to improve the performance or accuracy of data).
     */

    private int seed = 12345;
    private int numEpochs = 30;
    private Double learningRate = 0.005;

    public void lstmModel(String fileName, Configuration configurationlineChart,
                          Configuration configurationscatterChart,
                          Configuration configurationbarChart) throws IOException, InterruptedException {

        // Load the CSV file
        File file = new File(fileName);

        // convert the File object to a RecordReader and then to a DataSetIterator for splitting.
        RecordReader recordReader = new CSVRecordReader();
        recordReader.initialize(new FileSplit(file));
        int batchSize = 50;
        int labelIndex = 4; // index of the label (open, close, high, low)
        int numClasses = 4;
        DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, batchSize, labelIndex, numClasses);

        // Load the data from the CSV file into a DataSet object
        DataSet dataSet = iterator.next();

        // Split the data into training and testing sets
        int trainDataSize = (int) (dataSet.numExamples() * 0.8);
        DataSet trainData = (DataSet) dataSet.getRange(0, trainDataSize);
        DataSet testData = (DataSet) dataSet.getRange(trainDataSize, dataSet.numExamples());

        // Normalise
        NormalizerMinMaxScaler normalizer = new NormalizerMinMaxScaler(0, 1);
        normalizer.fit(dataSet);
        normalizer.transform(dataSet);

        /*
         * Defining the network architecture
         */
        MultiLayerConfiguration neuralConfiguration = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .weightInit(WeightInit.XAVIER)
                .updater(new Adam(learningRate))
                .list()
                .layer(new org.deeplearning4j.nn.conf.layers.LSTM.Builder().nIn(4).nOut(10).activation(Activation.TANH).build())
                .layer(new org.deeplearning4j.nn.conf.layers.LSTM.Builder().nIn(10).nOut(10).activation(Activation.TANH).build())
                .layer(new RnnOutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.IDENTITY)
                        .nIn(10).nOut(1).build())
                .build();

        // passing configuration.
        MultiLayerNetwork model = new MultiLayerNetwork(neuralConfiguration);
        model.init();
        model.setListeners(new ScoreIterationListener(100));

        // Training model here by iterating the Epochs and then the trainingData to the model.
        for (int i = 0; i < numEpochs; i++) {
            model.fit(trainData);
        }

        // Evaluate the model on the test data
        INDArray output = model.output(testData.getFeatures());
        double evaluationScore = model.score(testData, false);
        System.out.println("Evaluation score: " + evaluationScore);

        // Set the chart title and axis labels
        Chart lineChart = new Chart(ChartType.LINE);
        Chart scatterChart = new Chart(ChartType.SCATTER);
        Chart barChart = new Chart(ChartType.BAR);

        configurationlineChart = lineChart.getConfiguration();
        configurationscatterChart = scatterChart.getConfiguration();
        configurationbarChart = barChart.getConfiguration();

        configurationlineChart.setTitle("Stock Predictions");
        configurationlineChart.getxAxis().setTitle("Date");
        configurationlineChart.getyAxis().setTitle("Stock Price");

        configurationscatterChart.setTitle("Stock Predictions");
        configurationscatterChart.getxAxis().setTitle("Date");
        configurationscatterChart.getyAxis().setTitle("Stock Price");

        configurationbarChart.setTitle("Stock Predictions");
        configurationbarChart.getxAxis().setTitle("Date");
        configurationbarChart.getyAxis().setTitle("Stock Price");

        // Create a line series for each prediction result
        List<Series> seriesList = new ArrayList<>();
        for (int i = 0; i < output.columns(); i++) {
            double[] predictionArray = output.getColumn(i).toDoubleVector();
            List<DataSeriesItem> data = new ArrayList<>();
            for (int j = 0; j < predictionArray.length; j++) {
                data.add(new DataSeriesItem(j, predictionArray[j]));
            }
            DataSeries series = new DataSeries(data);
            series.setName("Prediction " + (i + 1));
            seriesList.add(series);
        }

        configurationlineChart.setSeries(seriesList);
        configurationscatterChart.setSeries(seriesList);
        configurationbarChart.setSeries(seriesList);
    }
}
