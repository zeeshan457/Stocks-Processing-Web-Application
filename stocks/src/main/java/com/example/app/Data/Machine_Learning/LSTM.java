package com.example.app.Data.Machine_Learning;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import org.tensorflow.op.data.TextLineDataset;

public class LSTM {

    /**
     * Attributes for the LSTM model using Keras library
     * Represents parameters for the model to function
     * (This can be changed and improved to improve the performance or accuracy of data).
     */
    private int epochs = 0;

    public void lstmNetwork(List<List<Double>> stockData) {

//        // Define the input shape for the LSTM
//        int inputShape = 4;
//
//        // Create a new sequential model
//        Sequential model = new Sequential();
//
//        // Add an LSTM layer with 16 units
//        model.add(new LSTM(16, inputShape: inputShape));
//
//        // Add a dense layer with 1 unit for the output
//        model.add(new Dense(1));
//
//        // Compile the model using mean squared error as the loss function
//        // and the Adam optimizer
//        model.compile(loss: "mean_squared_error", optimizer: "adam");
//
//        // Create a TimeseriesGenerator object to generate input/output pairs
//        // for the LSTM
//        TimeseriesGenerator generator = new TimeseriesGenerator(stockData, stockData, length: 4, batchSize: 1);
//
//        // Train the model on the generated input/output pairs
//        model.fit(generator, epochs: 10);

    }
}
