package com.example.lab2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class HelloController {
    public Circle ball;
    public Pane gamePane;
    @FXML
    private Label welcomeText;

    public class GameController {
        @FXML private Pane gamePane;
        @FXML private Circle ball;

        public void initialize() {
            // Шарик в центре панели
            ball.setCenterX(gamePane.getWidth() / 2);
            ball.setCenterY(gamePane.getHeight() / 2);
        }
    }
}