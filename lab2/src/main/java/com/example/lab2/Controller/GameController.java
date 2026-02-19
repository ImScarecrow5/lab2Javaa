package com.example.lab2.Controller;

import com.example.lab2.Model.GameModel;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import java.util.Random;

public class GameController {
    @FXML private Label scoreLabel;
    @FXML private Label pauseLabel;
    @FXML private Pane gamePane;
    @FXML private Circle ball;

    private GameModel model;
    private AnimationTimer timer;
    private Random rand = new Random();

    @FXML
    public void initialize() {
        // Фиксированные размеры
        double width = 560;
        double height = 400;

        model = new GameModel(
                width / 2,
                height / 2,
                ball.getRadius(),
                width,
                height
        );

        ball.centerXProperty().bind(model.xProp());
        ball.centerYProperty().bind(model.yProp());

        // Обновляем счет каждую секунду (простой способ)
        timer = new AnimationTimer() {
            public void handle(long now) {
                model.update();
                scoreLabel.setText("Счет: " + model.getScore());
                if (model.isPaused()) {
                    pauseLabel.setText("ПАУЗА");
                } else {
                    pauseLabel.setText("");
                }
            }
        };
        timer.start();

        // Обработчики мыши
        gamePane.setOnMouseMoved(this::onMouseMove);
        gamePane.setOnMouseDragged(this::onMouseDrag);
        gamePane.setOnMouseReleased(this::onMouseUp);
    }

    @FXML
    private void onMouseMove(MouseEvent e) {
        if (model != null) {
            model.setMouse(e.getX(), e.getY());
        }
    }

    @FXML
    private void onMouseDrag(MouseEvent e) {
        if (model != null && e.isPrimaryButtonDown()) {
            model.setAttract(true);
            model.setMouse(e.getX(), e.getY());
        }
    }

    @FXML
    private void onMouseUp(MouseEvent e) {
        if (model != null && e.getButton() == MouseButton.PRIMARY) {
            model.setAttract(false);
        }
    }

    @FXML
    private void onMouseClick(MouseEvent e) {
        if (model != null) {
            if (model.checkHit(e.getX(), e.getY())) {
                int r = rand.nextInt(256);
                int g = rand.nextInt(256);
                int b = rand.nextInt(256);
                ball.setFill(Color.rgb(r, g, b));
            }
        }
    }

    @FXML
    private void onBallEnter() {
        if (model != null) {
            model.slowDown();
        }
    }

    @FXML
    private void onBallExit() {
        if (model != null) {
            model.normalSpeed();
        }
    }

    @FXML
    private void onPause() {
        if (model != null) {
            model.togglePause();
        }
    }
}