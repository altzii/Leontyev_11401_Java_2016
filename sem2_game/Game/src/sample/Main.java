package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    int ballSize = 10;

    int paneWidth = 600;
    int paneHeight = 800;

    int stickWidth = 100;
    int stickHeight = 20;

    Text score = new Text();
    Text level = new Text();


    Circle ball = new Circle(ballSize);
    Rectangle stick = new Rectangle(stickWidth, stickHeight);

    boolean runningUp = true;
    boolean runningLeft = false;
    boolean running = true;

    int count = 0;

    int n;

    Action action = Action.NONE;

    Timeline timeline = new Timeline();

    public Pane initialize() {
        Pane pane = new Pane();
        pane.setPrefSize(paneWidth, paneHeight);

        stick.setTranslateX(paneWidth / 2 - stickWidth / 2);
        stick.setTranslateY(paneHeight - stickHeight);
        stick.setFill(Color.RED);


        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.016), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                if (!running) {
                    return;
                }

                if (count > 4) {
                    n = 8;
                    level.setText("Level: 2");

                    if (count > 9) {
                        n = 10;
                        level.setText("Level: 3");

                        if (count > 14) {
                            n = 12;
                            level.setText("Level: 4");

                            if (count > 19) {
                                n = 14;
                                level.setText("Level: 5");
                            }
                        }
                    }
                }


                //движение платформы (влево или вправо)
                switch (action) {
                    case LEFT:
                        if (stick.getTranslateX() - 10 >= 0) {
                            stick.setTranslateX(stick.getTranslateX() - 10);
                        }
                        break;
                    case RIGHT:
                        if (stick.getTranslateX() + stickWidth + 10 <= paneWidth) {
                            stick.setTranslateX(stick.getTranslateX() + 10);
                        }
                        break;
                    case NONE:
                        break;
                }

                if (runningLeft) {
                    ball.setTranslateX(ball.getTranslateX() - n);      //мяч двигается влево
                } else {
                    ball.setTranslateX(ball.getTranslateX() + n);      //мяч двигается вправо
                }

                if (runningUp) {
                    ball.setTranslateY(ball.getTranslateY() - n);       //мяч двигается вверх
                } else {
                    ball.setTranslateY(ball.getTranslateY() + n);      //мяч двигается вниз
                }

                if (ball.getTranslateX() - ballSize <= 0) {     //врезался в левую границу
                    runningLeft = false;
                } else if (ball.getTranslateX() + ballSize >= paneWidth) {    //врезался в правую границу
                    runningLeft = true;
                }

                if (ball.getTranslateY() - ballSize <= 0) {    //врезался в верхнюю границу
                    runningUp = false;
                } else if ((ball.getTranslateY() + ballSize >= paneHeight - stickHeight &&
                        ball.getTranslateX() + ballSize >= stick.getTranslateX() &&
                        ball.getTranslateX() - ballSize <= stick.getTranslateX() + stickWidth)) {
                    count++;
                    score.setText("Score: " + count + "");
                    runningUp = true;
                }

                if (ball.getTranslateY() + ballSize >= paneHeight) {
                    stopGame();
                }
            }
        });

        pane.getChildren().add(stick);
        pane.getChildren().add(ball);
        pane.getChildren().add(score);
        pane.getChildren().add(level);

        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);


        return pane;
    }

    public void stopGame() {
        running = false;
        timeline.stop();
    }

    public void startGame() {
        n = 5;
        count = 0;

        score.setLayoutX(30);
        score.setLayoutY(30);
        score.setFont(Font.font(20));
        score.setText("Score: 0");

        level.setLayoutX(paneWidth - 100);
        level.setLayoutY(30);
        level.setFont(Font.font(20));
        level.setText("Level: 1");

        ball.setTranslateX(300);
        ball.setTranslateY(600);
        ball.setFill(Color.BLACK);

        running = true;
        runningUp = true;
        timeline.play();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = initialize();

        Scene scene = new Scene(pane);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT:
                        action = Action.LEFT;
                        break;
                    case RIGHT:
                        action = Action.RIGHT;
                        break;
                    case R:
                        if (!running) {
                            startGame();
                            break;
                        }
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT:
                        action = Action.NONE;
                        break;
                    case RIGHT:
                        action = Action.NONE;
                        break;
                }
            }
        });

        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        startGame();
    }

    public static void main(String[] args) {
        launch(args);
    }
}