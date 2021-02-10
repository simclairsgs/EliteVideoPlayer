/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elitemediaplayer;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;


/**
 *
 * @author George Simclair
 */
public class FXMLDocumentController implements Initializable {
    
    private MediaPlayer mediaPlayer; 
    
    @FXML
    private MediaView mediaView;
    
  
    @FXML
    private Slider slider;
    
    @FXML
    private Slider seekSlider;
    
    private String filepath;
    
    @SuppressWarnings("FieldMayBeFinal")
    private  Double playbackRate = 1.0;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
       FileChooser fileChooser = new FileChooser();
       FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Slect a file(*.mp4)", "*.mp4");
       fileChooser.getExtensionFilters().add(filter);
       
       File file = fileChooser.showOpenDialog(null);
       filepath = file.toURI().toString();
       if (filepath != null)
       {
           Media media = new Media(filepath);
           mediaPlayer = new MediaPlayer(media);
           mediaView.setMediaPlayer(mediaPlayer);
           DoubleProperty width = mediaView.fitWidthProperty();
           DoubleProperty height = mediaView.fitHeightProperty();
           
           width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
           height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
           
           slider.setValue(mediaPlayer.getVolume()*100);
           slider.valueProperty().addListener(new InvalidationListener(){
               @Override
               public void invalidated(Observable observable) {
                  mediaPlayer.setVolume(slider.getValue()/100);
               }
           });
           
        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
               @Override
               public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                  seekSlider.setValue(newValue.toSeconds()/mediaPlayer.getTotalDuration().toSeconds()*100);
                  
               }
           });
        
        seekSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
               @Override
               public void handle(MouseEvent event) {
                mediaPlayer.seek(Duration.seconds(seekSlider.getValue()*mediaPlayer.getTotalDuration().toSeconds()/100));
               }
           });
           mediaPlayer.play();
       }
    }
    
    @FXML
    private void playVideo(ActionEvent event)
    {
        playbackRate = 1.0;
        setRate();
        mediaPlayer.play();
    }
    
     @FXML
    private void pauseVideo(ActionEvent event)
    {
        mediaPlayer.pause();
    }
    
     @FXML
    private void StopVideo(ActionEvent event)
    {
        mediaPlayer.stop();
    }
    
     @FXML
    private void fastVideo(ActionEvent event)
    {
       playbackRate += 0.5;
       if(playbackRate > 4.0)
      {
          playbackRate = 4.0;
      }
       setRate();
    }
    
   
     @FXML
    private void slowVideo(ActionEvent event)
    {
        // mediaPlayer.setRate(0.75);
      playbackRate -= 0.25;
      if(playbackRate<0.25)
      {
          playbackRate = 0.25;
      }
       setRate();
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    private void setRate() {
       mediaPlayer.setRate(playbackRate);
    }
    
    @FXML
    private void showInfo()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Developer Info");
        alert.setHeaderText(null);
        alert.setContentText("Developed By\n\tGeorge Simclair Sam,\n\t\tElite Coders.\n\tsimclair.sgs@gmail.com");
        alert.showAndWait();
    }
    
}
