package com.example.app.Data.Machine_Learning;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.LSTM;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class LSTM_Algorithm {

    /*
     * Attributes for the LSTM model using Keras library
     * Represents parameters for the model to function
     * (This can be changed and improved to improve the performance or accuracy of data).
     */
    private DataSeries series = new DataSeries();
    private int inputSize = 1;
    private int lstmLayerSize = 10;
    private int outputSize = 1;

    /**
     *
     * Processes a dataset using an LSTM model, and trains it based on training data.
     *
     * @throws IOException file errors
     * @throws InterruptedException splitting files / converting files
     */
    public void lstmModel(String fileName, MemoryBuffer memory, Double rate, int epochs, Chart lineChart,
                          Chart scatterChart, Chart barChart, Grid grid) throws IOException, InterruptedException {

        if (memory == null) {
            Notification notification = Notification.show("Buffer is Null");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else if (fileName == null) {
            Notification notification = Notification.show("File is Null");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
        else {
            // Load the CSV file
            File file = new File(fileName);

            try {
                InputStreamReader reader = new InputStreamReader(memory.getInputStream());
                CSVReader csvReader = new CSVReaderBuilder(reader)
                        .withCSVParser(new CSVParserBuilder().withSeparator(',').build()).build();

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String[] record;
                DataSeriesItem item = new DataSeriesItem();
                List<Date> dateValues = new ArrayList<>();
                List<Double> openValues = new ArrayList<>();
                List<Double> closeValues = new ArrayList<>();
                List<Double> highValues = new ArrayList<>();
                List<Double> lowValues = new ArrayList<>();

                // Iterating the CSV file, each column.
                while ((record = csvReader.readNext()) != null) {
                    Date date = dateFormat.parse(record[0]);
                    double open = Double.parseDouble(record[1]);
                    double close = Double.parseDouble(record[2]);
                    double high = Double.parseDouble(record[3]);
                    double low = Double.parseDouble(record[4]);
                    dateValues.add(date);
                    openValues.add(open);
                    closeValues.add(close);
                    highValues.add(high);
                    lowValues.add(low);
                }

                // splitting dataset into training and testing, 80% training and 20% testing
                int trainingSet = (int) (openValues.size() * 0.8);
                int testingSet = openValues.size() - trainingSet;
                double[][] trainOpen = new double[trainingSet][1];
                double[][] testOpen = new double[testingSet][1];

                for (int i = 0; i < trainingSet; i++) {
                    trainOpen[i][0] = openValues.get(i);
                }

                for (int i = trainingSet; i < openValues.size(); i++) {
                    testOpen[i - trainingSet][0] = openValues.get(i);
                }

                // Prepare training and testing data
                INDArray trainOpenArray = Nd4j.create(trainOpen);
                INDArray testOpenArray = Nd4j.create(testOpen);
                // Reshape data to 3D input
                int examples = trainOpenArray.rows();
                int sequenceLength = 1;
                int features = 1;
                trainOpenArray = trainOpenArray.reshape(examples, sequenceLength, features);
                testOpenArray = testOpenArray.reshape(testOpenArray.rows(), sequenceLength, features);

                // Creating dataset objects
                DataSet trainData = new DataSet(trainOpenArray, trainOpenArray);
                DataSet testData = new DataSet(testOpenArray, testOpenArray);

                /*
                 * Architecture of the model
                 *
                 * The random number generator's seed is set to 12345 using the command.seed(12345). This is done during
                 *  the training process. This helps to guarantee that the training process's outcomes can be replicated.
                 *
                 * The weight initialization technique is changed to Xavier initialization using the.weightInit(WeightInit
                 * .XAVIER) command. The Xavier initialization technique is a strategy to set up the weights in a neural
                 * network so that the variance of each layer's outputs is equal to the variance of its inputs, preventing
                 * vanishing gradients.
                 *
                 * The updater is configured to the Adam optimization method with a learning rate of 0.001 by the
                 * command.updater(new Adam(0.001)). Popular neural network training techniques like the Adam optimization
                 * algorithm have been proven to function well for a variety of issues.
                 *
                 * The .list() starts the definition of the layers in the network.
                 *
                 * With inputSize inputs and lstmLayerSize outputs, the.layer(new LSTM.Builder().nIn(inputSize).nOut
                 * (lstmLayerSize).activation(Activation.TANH).build()) construct adds a Long Short-Term Memory (LSTM)
                 * layer to the network. The hyperbolic tangent is the activation function (Activation.TANH).
                 *
                 * The new RnnOutputLayer.Builder(LossFunctions.LossFunction.MSE).activation(Activation.IDENTITY) of the
                 * layer has the following code: The network is extended with an output layer using the functions
                 * nIn(lstmLayerSize).nOut(outputSize).build() and mean squared error (LossFunctions.LossFunction.MSE)
                 * as the loss function and identity activation function, respectively (Activation.IDENTITY). The layer
                 * contains outputSize outputs and lstmLayerSize inputs.
                 *
                 * The build() creates the MultiLayerConfiguration object.
                 *
                */
                MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder()
                        .seed(12345)
                        .weightInit(WeightInit.XAVIER)
                        .updater(new Adam(rate))
                        .list()
                        .layer(new LSTM.Builder().nIn(inputSize).nOut(lstmLayerSize)
                                .activation(Activation.TANH).build())
                        .layer(new RnnOutputLayer.Builder(LossFunctions.LossFunction.MSE)
                                .activation(Activation.IDENTITY)
                                .nIn(lstmLayerSize).nOut(outputSize).build())
                        .build();

                // Initialising the model
                MultiLayerNetwork network = new MultiLayerNetwork(configuration);
                network.init();
                network.setListeners(new ScoreIterationListener(1));

                // train the model
                for (int i = 0; i < epochs; i++) {
                    network.fit(trainData);
                }

                // Make predictions with the test data and reshape to 1D array
                INDArray prediction = network.output(testData.getFeatures());
                prediction = prediction.reshape(prediction.length());

                // Convert the predictions to a double array for plotting
                double[] predictedOpenValues = prediction.toDoubleVector();

                // Create a list to store the data
                List<Double> predictionDataList = new ArrayList<>();

                /*
                 *
                 * Iterate through data and add to data series
                 * X Axis is time step
                 * Y Axis is predicted values
                 *
                 */
                for (int i = 0; i < predictedOpenValues.length; i++) {
                    series.add(new DataSeriesItem(i, predictedOpenValues[i]));
                    predictionDataList.add(predictedOpenValues[i]);
                }

//                ListDataProvider<Double> dataProvider = DataProvider.ofCollection(predictionDataList);
//                grid.setItems((Stream<String[]>) dataProvider);

            } catch (IOException | ParseException | CsvValidationException e) {
                e.printStackTrace();
            }

            // Configurations for charts
            Configuration configurationlineChart = lineChart.getConfiguration();
            Configuration configurationscatterChart = scatterChart.getConfiguration();
            Configuration configurationheatrChart = barChart.getConfiguration();


            // Set name to the dataset selected
            series.setName(fileName);

            configurationlineChart.addSeries(series);
            configurationscatterChart.addSeries(series);
            configurationheatrChart.addSeries(series);
        }
    }
}
