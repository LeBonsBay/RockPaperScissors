package htl.steyr.rockpaperscissors;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);

        scene.getStylesheets().add(getClass().getResource("/stylesheets/style.css").toExternalForm());

        stage.setTitle("Welcome to our funny Rock, Paper, Scissors Game!");
        stage.setScene(scene);
        stage.show();
    }
}
