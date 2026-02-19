package com.example.lab2.Model;

import javafx.beans.property.*;

public class GameModel {
    private DoubleProperty x = new SimpleDoubleProperty();
    private DoubleProperty y = new SimpleDoubleProperty();

    private double speedX;
    private double speedY;
    private double startSpeedX;
    private double startSpeedY;

    private double radius;
    private double width;
    private double height;

    private IntegerProperty score = new SimpleIntegerProperty(0);
    private boolean paused = false;

    private double mouseX = 0;
    private double mouseY = 0;
    private boolean attract = false;

    public GameModel(double startX, double startY, double ballRadius, double w, double h) {
        x.set(startX);
        y.set(startY);
        radius = ballRadius;
        width = w;
        height = h;

        double angle = Math.random() * 2 * 3.14;
        speedX = 1 * Math.cos(angle);
        speedY = 1 * Math.sin(angle);
        startSpeedX = speedX;
        startSpeedY = speedY;
    }

    public void update() {
        if (paused) return;

        double dx = mouseX - x.get();
        double dy = mouseY - y.get();
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (attract) {
            if (dist > 1) {
                speedX = speedX + (dx / dist) * 0.1;
                speedY = speedY + (dy / dist) * 0.1;
            }
        } else {
            if (dist < 100 && dist > 1) {
                speedX = speedX - (dx / dist) * 0.1;
                speedY = speedY - (dy / dist) * 0.1;
            }
        }

        double nowSpeed = Math.sqrt(speedX * speedX + speedY * speedY);
        if (nowSpeed > 3) {
            speedX = (speedX / nowSpeed) * 1;
            speedY = (speedY / nowSpeed) * 1;
        }

        double newX = x.get() + speedX;
        double newY = y.get() + speedY;

        if (newX - radius <= 0 || newX + radius >= width) {
            speedX = -speedX;
            startSpeedX = -startSpeedX;
            newX = x.get() + speedX;
        }

        if (newY - radius <= 0 || newY + radius >= height) {
            speedY = -speedY;
            startSpeedY = -startSpeedY;
            newY = y.get() + speedY;
        }

        x.set(newX);
        y.set(newY);
    }

    public boolean checkHit(double clickX, double clickY) {
        if (paused) return false;

        double dx = clickX - x.get();
        double dy = clickY - y.get();
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist <= radius) {
            score.set(score.get() + 1);
            return true;
        }
        return false;
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setMouse(double mx, double my) {
        mouseX = mx;
        mouseY = my;
    }

    public void setAttract(boolean b) {
        attract = b;
    }

    public void togglePause() {
        paused = !paused;
    }

    public DoubleProperty xProp() { return x; }
    public DoubleProperty yProp() { return y; }
}