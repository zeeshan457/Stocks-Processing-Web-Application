package com.example.app.Data.Machine_Learning;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LSTM_Algorithm {

    /**
     * Attributes for the LSTM model using Keras library
     * Represents parameters for the model to function
     * (This can be changed and improved to improve the performance or accuracy of data).
     */

    private int seed = 12345;
    private int numEpochs = 30;
    private Double learningRate = 0.005;
    DataSeries series = new DataSeries();

    /**
     *
     * Processes a dataset using an LSTM model, dnd trains a dataset.
     *
     * @throws IOException file errors
     * @throws InterruptedException splitting files / converting files
     */
    public void lstmModel(String fileName, MemoryBuffer memory, Chart lineChart,
                          Chart scatterChart, Chart barChart) throws IOException, InterruptedException {

        // Load the CSV file
        File file = new File(fileName);

        try {
            InputStreamReader reader = new InputStreamReader(memory.getInputStream());
            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withCSVParser(new CSVParserBuilder().withSeparator(',').build()).build();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String[] record;
            while ((record = csvReader.readNext()) != null) {
                Date date = dateFormat.parse(record[0]);
                int dateInMilliseconds = (int) date.getTime();
                double open = Double.parseDouble(record[1]);
                double close = Double.parseDouble(record[2]);
                double high = Double.parseDouble(record[3]);
                double low = Double.parseDouble(record[4]);

                DataSeriesItem item = new DataSeriesItem();
                item.setX(date.getTime());
                item.setY(open);
                series.add(item);

            }
        } catch (IOException | ParseException | CsvValidationException e) {
            e.printStackTrace();
        }

//        DataSeries series = new DataSeries();
//        series.add((DataSeriesItem) data);


//        VectorAssembler featureAssembler = new VectorAssembler().setInputCols(new String[]{"open", "close", "high", "low"}).setOutputCol("features");
        /*
         * Defining the network architecture
        */
//        MultiLayerConfiguration neuralConfiguration = new NeuralNetConfiguration.Builder()
//                .seed(seed)
//                .weightInit(WeightInit.XAVIER)
//                .updater(new Adam(learningRate))
//                .list()
//                .layer(new LSTM.Builder().nIn(4).nOut(10).activation(Activation.TANH).build())
//                .layer(new LSTM.Builder().nIn(4).nOut(10).activation(Activation.TANH).build())
//                        .layer(new RnnOutputLayer.Builder(LossFunctions.LossFunction.MSE)
//                        .activation(Activation.IDENTITY)
//                        .nIn(10).nOut(1).build())
//                .build();
//
//        // passing configuration.
//        MultiLayerNetwork model = new MultiLayerNetwork(neuralConfiguration);
//        model.init();
//        model.setListeners(new ScoreIterationListener(100));

//        DataSeries series = new DataSeries();
//        series.add(new DataSeriesItem(0, 10));
//        series.add(new DataSeriesItem(1, 20));
//        series.add(new DataSeriesItem(2, 15));


        Configuration configurationlineChart = lineChart.getConfiguration();
        Configuration configurationscatterChart = scatterChart.getConfiguration();
        Configuration configurationbarChart = barChart.getConfiguration();

        configurationlineChart.addSeries(series);
        configurationscatterChart.addSeries(series);
        configurationbarChart.addSeries(series);

    }
}
