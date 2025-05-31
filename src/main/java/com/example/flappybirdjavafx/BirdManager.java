package com.example.flappybirdjavafx;

import javafx.scene.image.ImageView;

public class BirdManager {

    private ImageView bird;
    private BirdController birdController;

    private double gravity = 0.25;
    private double velocity = 0;
    private int jumpHeight = -40;

    public BirdManager(ImageView imageView, BirdController birdController) {
        this.bird = imageView;
        this.birdController = birdController;

    }

    public void update() {
        gravity();
    }

    private void gravity() {
        velocity += gravity;
        double newY = bird.getLayoutY() + velocity;
        bird.setLayoutY(newY);
    }

    public void jump() {
        velocity = 0;
        double newY = bird.getLayoutY() + jumpHeight;
        bird.setLayoutY(Math.max(newY, birdController.getGamePane().getLayoutY()));
    }

    public boolean checkBirdFall() {
        return bird.getLayoutY() > 640;
    }

    public void reset() {
        bird.setLayoutX(40);
        bird.setLayoutY(307);
        velocity = 0;
    }
}
