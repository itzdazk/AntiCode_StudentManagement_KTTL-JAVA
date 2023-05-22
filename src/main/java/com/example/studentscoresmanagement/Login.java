package com.example.studentscoresmanagement;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button buttonLI;

    @FXML
    private PasswordField inPW;

    @FXML
    private TextField inUN;

    private Parent root;
    private Stage stage;
    private Scene scene;

    private static String Username="anticode";
    private static String Password="123456";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        makeFadeInTransition();
    }

    @FXML
    void onLI(ActionEvent event) {
        if( inUN.getText().trim().equalsIgnoreCase(Username) && inPW.getText().trim().equals(Password)){
            makeFadeOut();
        }else{
            alertShow(inUN.getText(),inPW.getText());
        }
    }

    public void makeFadeInTransition(){
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        fadeTransition.play();
    }

    public void makeFadeOut(){
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);

        fadeTransition.setOnFinished((ActionEvent e)->{
            loadNextScene();
        });
        fadeTransition.play();
    }

    public void loadNextScene(){
        try{
            root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
            stage = (Stage) rootPane.getScene().getWindow();
            Scene newScene = new Scene(root);
            stage.setMaximized(true);
            stage.setScene(newScene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void alertShow(String name,String pass ){
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Đăng nhập thất bại!");
        if(name.trim().isEmpty()){
            alert.setContentText("Tên đăng nhập không được để trống");
        }else if(pass.isEmpty()){
            alert.setContentText("Mật khẩu không được để trống");
        }else{
            alert.setContentText("Tài khoản hoặc mật khẩu không chính xác");
        }
        DialogPane dialog=alert.getDialogPane();
        dialog.setStyle("-fx-font-size:13px;\n"+
                "-fx-background-color:linear-gradient(from 0.0% 0.0% to 100.0% 100.0%, #868686 0.0%, #ffffff 100.0%);\n"+
                "-fx-font-weight: bold;\n");
        ButtonType buttonTypeYes = new ButtonType("Xác Nhận",ButtonBar.ButtonData.YES);
        alert.getButtonTypes().setAll(buttonTypeYes);
        alert.show();
    }



}
