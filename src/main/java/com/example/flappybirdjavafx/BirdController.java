package com.example.flappybirdjavafx;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class BirdController implements Initializable {

    @FXML
    private ImageView bird;
    @FXML
    private AnchorPane gamePane;
    @FXML
    private Label scoreLabel;

    private AnimationTimer gameloop;
    private BirdManager birdManager;
    private ObstacleManager obstacleManager;
    private ScoreManager scoreManager;
    private boolean on;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.birdManager = new BirdManager(bird, this);
        this.obstacleManager = new ObstacleManager(this);
        this.scoreManager = new ScoreManager(this, scoreLabel);

        this.gameloop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };
        this.gameloop.start();
        this.on = true;

    }

    private void update() {
        birdManager.update();
        obstacleManager.update();
        scoreManager.update();
        if(birdManager.checkBirdFall() || obstacleManager.checkObstacleCollision()) {
            gameOver();
        }
    }

    private void gameOver() {
        gameloop.stop();
        on = false;
        reset();
    }

    private void reset() {
        birdManager.reset();
        obstacleManager.reset();
        scoreManager.reset();
    }

    @FXML
    public void keyPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.SPACE) {
            if(on) {
                birdManager.jump();

            } else {
                gameloop.start();
                on = true;
            }
        }
    }

    public ImageView getBird() {
        return bird;
    }

    public AnchorPane getGamePane() {
        return gamePane;
    }

    public ObstacleManager getObstacleManager() {
        return obstacleManager;
    }
}
