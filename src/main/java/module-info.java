module htl.steyr.rockpaperscissors {
    requires javafx.controls;
    requires javafx.fxml;


    opens htl.steyr.rockpaperscissors to javafx.fxml;
    exports htl.steyr.rockpaperscissors;
}