module com.sgs.elitevideoplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.sgs.elitevideoplayer to javafx.fxml;
    exports com.sgs.elitevideoplayer;
}