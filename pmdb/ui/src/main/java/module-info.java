module pmdb.ui {
    requires java.net.http;

    requires pmdb.core;
    requires javafx.controls;
    requires javafx.fxml;

    opens ui to javafx.graphics, javafx.fxml;
}
