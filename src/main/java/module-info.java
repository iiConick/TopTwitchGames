module com.example.toptwitchgames {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.toptwitchgames to javafx.fxml;
    exports com.example.toptwitchgames;
}