package com.example.app.Data.Machine_Learning;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
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

public class LSTM_Algorithm {

    /*
     * Attributes for the LSTM model
     * Represents parameters for the model to function
     * (This can be changed and improved to improve the performance or accuracy of data).
     */
    private DataSeries actualSeries = new DataSeries();
    private DataSeries predictedSeries = new DataSeries();
    private int inputSize = 1;
    private int lstmLayerSize = 20;
    private int outputSize = 1;

    /**
     *
     * Processes a dataset using an LSTM model, and trains it based on training data.
     *
     * @throws IOException file errors
     * @throws InterruptedException splitting files / converting files
     */
    public void predictionModel(String fileName, MemoryBuffer memory, Double rate, int epochs, Chart lineChart,
                                Chart scatterChart, Chart barChart) throws IOException, InterruptedException {

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
                double[][] trainData = new double[trainingSet][4];
                double[][] testData = new double[testingSet][4];
                for (int i = 0; i < trainingSet; i++) {
                    trainData[i][0] = openValues.get(i);
                    trainData[i][1] = closeValues.get(i);
                    trainData[i][2] = highValues.get(i);
                    trainData[i][3] = lowValues.get(i);
                }
                for (int i = trainingSet; i < openValues.size(); i++) {
                    testData[i - trainingSet][0] = openValues.get(i);
                    testData[i - trainingSet][1] = closeValues.get(i);
                    testData[i - trainingSet][2] = highValues.get(i);
                    testData[i - trainingSet][3] = lowValues.get(i);
                }

                // Prepare training and testing data
                INDArray trainArray = Nd4j.create(trainData);
                INDArray testArray = Nd4j.create(testData);
                // Reshape data to 3D input
                int examples = trainArray.rows();
                int sequenceLength = 1;
                int features = 4;
                trainArray = trainArray.reshape(examples, sequenceLength, features);
                testArray = testArray.reshape(testArray.rows(), sequenceLength, features);
                // Creating dataset objects
                DataSet trainDataSet = new DataSet(trainArray, trainArray);
                DataSet testDataSet = new DataSet(testArray, testArray);

                /*
                 * Architecture of the model
                 *
                 * The random number generator's seed is set to 12345 using the command.seed(12345). This is done during
                 * the training process. This helps to guarantee that the training process's outcomes can be replicated.
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

                // train the model, which requires a 3d array.
                for (int i = 0; i < epochs; i++) {
                    network.fit(trainDataSet);
                }

                // Create an empty list to hold predicted values
                List<Double> predictedValues = new ArrayList<>();

                // Loop through each testing data point and predict its value
                for (int i = 0; i < testingSet; i++) {
                    INDArray testPoint = Nd4j.create(new double[][]{{testData[i][0], testData[i][1], testData[i][2], testData[i][3]}});
                    testPoint = testPoint.reshape(1, 1, 4); // Reshape testPoint to a 3D array
                    INDArray predicted = network.output(testPoint); // Get the predicted output
                    double[] predictionArray = predicted.ravel().toDoubleVector();
                    double prediction = predictionArray[0];
                    predictedSeries.add(new DataSeriesItem(i, prediction));
                }

                // Add the actual data
                actualSeries = new DataSeries();
                for (int i = 0; i < testDataSet.numExamples(); i++) {
                    double x = i + trainDataSet.numExamples();
                    double y = testDataSet.getFeatures().getDouble(i);
                    actualSeries.add(new DataSeriesItem(x, y));
                }

//                // Print actual vs predicted values
//                System.out.println("Actual\tPredicted");
//                for (int i = 0; i < testDataSet.numExamples(); i++) {
//                    System.out.print(testDataSet.getFeatures().getDouble(i) + "\t");
//                    System.out.println(predicted.getDouble(i) + "\t");
//                }

            } catch (IOException | ParseException | CsvValidationException e) {
                e.printStackTrace();
            }

            // Configurations for charts
            Configuration configurationlineChart = lineChart.getConfiguration();
            Configuration configurationscatterChart = scatterChart.getConfiguration();
            Configuration configurationbarChart = barChart.getConfiguration();

            // Set name to the dataset selected
            predictedSeries.setName("Predicted " + fileName);
            actualSeries.setName("Actual " + fileName);

            configurationlineChart.addSeries(predictedSeries);
            configurationscatterChart.addSeries(predictedSeries);
            configurationbarChart.addSeries(predictedSeries);

        }
    }
}
