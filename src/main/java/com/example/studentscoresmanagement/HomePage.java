
package com.example.studentscoresmanagement;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Stack;

public class HomePage implements Initializable {

    @FXML
    private AnchorPane stackParent;

    @FXML
    private AnchorPane container;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button settingButton;

    @FXML
    private Button tableScoreButton;

    @FXML
    private ImageView test;

    @FXML
    private Button updateButton;

    @FXML
    private Circle circle;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {

            double deltaX = 2;
            double deltaY = 2;

            @Override
            public void handle(ActionEvent actionEvent) {
                circle.setLayoutX(circle.getLayoutX() + deltaX);
                circle.setLayoutY(circle.getLayoutY() + deltaY);

                Bounds bounds = stackParent.getBoundsInLocal();
                boolean rightBorder = circle.getLayoutX() >= (bounds.getMaxX() - circle.getRadius());
                boolean leftBorder = circle.getLayoutX() <= (bounds.getMinX() + circle.getRadius());
                boolean bottomBorder = circle.getLayoutY() >= (bounds.getMaxY() - circle.getRadius());
                boolean topBorder = circle.getLayoutY() <= (bounds.getMinY() + circle.getRadius());

                if (rightBorder || leftBorder) {
                    deltaX *= -1;
                }
                if (bottomBorder || topBorder) {
                    deltaY *= -1;
                }
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        makeFadeInTransition();
    }

    @FXML
    void onDashBoard(ActionEvent event) throws IOException {
        switchScene("DashBoard.fxml");
    }
    @FXML
    void onTableScore(ActionEvent event) throws IOException {
        switchScene("StudentManagement.fxml");
    }

    @FXML
    void onUpdate(ActionEvent event) throws IOException {
        switchScene("Update.fxml");
    }

    @FXML
    void onLogOut(ActionEvent event) throws IOException {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(stackParent);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);

        fadeTransition.setOnFinished((ActionEvent e)->{
            try{
                Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                Stage stage = (Stage) stackParent.getScene().getWindow();
                stage.setX(450);
                stage.setY(150);
                Scene newScene = new Scene(root);
                stage.setScene(newScene);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        fadeTransition.play();
    }

    public void makeFadeInTransition(){
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(container);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        fadeTransition.play();
    }

    public void switchScene(String link) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(link));
        Scene scene = container.getScene();
        root.translateYProperty().set(scene.getHeight());
        stackParent.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            stackParent.getChildren().remove(container);
        });
        timeline.play();
    }

}

