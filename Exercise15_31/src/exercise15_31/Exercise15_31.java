
package exercise15_31;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Exercise15_31 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        PendulumPane pendulumPane = new PendulumPane(400, 400);

        Scene scene = new Scene(pendulumPane);
        primaryStage.setTitle("Pendulum Animation");
        primaryStage.setScene(scene);
        pendulumPane.play();
        primaryStage.show();

        scene.setOnKeyPressed(e-> {
            switch (e.getCode()) {
                case UP: pendulumPane.increase(); break;
                case DOWN: pendulumPane.decrease(); break;
            }
        });

    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    private class PendulumPane extends Pane {

        private double w = 400;
        private double h;
        PathTransition bPath;

        Circle topC;
        Circle lowerC;
        Line line;
        Arc arc;

        PendulumPane(double width, double height) {

            w = width;
            h = height;
            setPrefWidth(w);
            setPrefHeight(h);
            arc = new Arc(w / 2, h * 0.8, w * 0.15, w * 0.15, 180, 180);

            lowerC = new Circle(arc.getCenterX() - arc.getRadiusX(), arc.getCenterY(), 10);
            topC = new Circle(arc.getCenterX(), arc.getCenterY() - h / 2, lowerC.getRadius() / 2);
            arc = new Arc(topC.getCenterX(), topC.getCenterY(), w / 2, h / 2, 240, 60);
            line = new Line(
                    topC.getCenterX(), topC.getCenterY(),
                    lowerC.getCenterX(), lowerC.getCenterY());

            line.endXProperty().bind(lowerC.translateXProperty().add(lowerC.getCenterX()));
            line.endYProperty().bind(lowerC.translateYProperty().add(lowerC.getCenterY()));
            bPath = new PathTransition();
            bPath.setDuration(Duration.millis(1000));
            bPath.setPath(arc);
            bPath.setNode(lowerC);
            bPath.setOrientation(PathTransition.OrientationType.NONE);
            bPath.setCycleCount(PathTransition.INDEFINITE);
            bPath.setAutoReverse(true);

            getChildren().addAll(lowerC, topC,line);

        }

        public void play() {
            bPath.play();
        }


        public void increase() {
            bPath.setRate(bPath.getCurrentRate() + 5);
        }

        public void decrease() {
            bPath.setRate(bPath.getCurrentRate() - 5);
        }
    }
}
