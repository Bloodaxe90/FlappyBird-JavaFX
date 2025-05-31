package com.example.flappybirdjavafx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.concurrent.CopyOnWriteArrayList;

public class ObstacleManager {

    private BirdController birdController;
    private CopyOnWriteArrayList<Obstacle> obstacles;
    private Image topPipeImg;
    private Image bottomPipeImg;
    private double frames;
    final int space = 160;
    public ObstacleManager(BirdController birdController) {
        this.birdController = birdController;
        this.obstacles = new CopyOnWriteArrayList<>();

        try {
            topPipeImg = new Image(getClass().getResourceAsStream("topPipe.png"));
            bottomPipeImg = new Image(getClass().getResourceAsStream("bottomPipe.png"));

        } catch (Exception e) {
            System.out.println(e);
        }
        this.frames = 900;
    }
    public void update() {
        createObstacle();
        moveObstacles();
        checkObstaclePassed();
    }

    class Obstacle extends ImageView{

        private double velocity;
        private boolean passed;

        public Obstacle(Image pipeImage) {
            super(pipeImage);
            this.passed = false;
            this.velocity = -4;
        }

        public boolean isPassed() {
            return passed;
        }

        public double getVelocity() {
            return velocity;
        }

        public void setPassed(boolean passed) {
            this.passed = passed;
        }
    }

    public void moveObstacles() {
        for (Obstacle obstacle : obstacles) {
            double newX = obstacle.getLayoutX() + obstacle.getVelocity();
            obstacle.setLayoutX(newX);
            destroyObstacles(obstacle);
        }
    }

    public void destroyObstacles(Obstacle obstacle) {
        if(obstacle.getBoundsInParent().getMaxX() < 0) {
            obstacles.remove(obstacle);
            birdController.getGamePane().getChildren().remove(obstacle);
        }
    }

    public boolean checkObstacleCollision() {
        return obstacles.stream()
                .anyMatch(obstacle -> obstacle.getBoundsInParent().intersects(birdController.getBird().getBoundsInParent()));
    }

    public boolean checkObstaclePassed() {
        for (Obstacle obstacle : obstacles) {
            if (!obstacle.isPassed() && obstacle.getBoundsInParent().getMaxX() <= birdController.getBird().getBoundsInParent().getMinX()) {
                obstacle.setPassed(true);
                return true;
            }
        }
        return false;
    }

    public void createObstacle() {
        this.frames ++;
        if (this.frames >= 60) {
            this.frames = 0;
            Obstacle topPipe = new Obstacle(topPipeImg);
            topPipe.setFitWidth(64);
            topPipe.setFitHeight(512);
            topPipe.setX(360);
            //logic to randomly place the top pipe
            double newTopY = topPipe.getY() - topPipe.getFitHeight()/4 - Math.random()*(topPipe.getFitHeight()/2);
            topPipe.setY(newTopY);

            Obstacle bottomPipe = new Obstacle(bottomPipeImg);
            bottomPipe.setFitWidth(64);
            bottomPipe.setFitHeight(512);
            bottomPipe.setX(360);
            //logic to place a bottom pipe relative to the top pipe with a fixed space between
            double newBottomY = topPipe.getY() + topPipe.getFitHeight() + space;
            bottomPipe.setY(newBottomY);

            obstacles.add(topPipe);
            obstacles.add(bottomPipe);

            birdController.getGamePane().getChildren().addAll(topPipe, bottomPipe);
       }
     }

     public void reset() {
        birdController.getGamePane().getChildren().removeAll(obstacles);
        obstacles.clear();
     }
}
