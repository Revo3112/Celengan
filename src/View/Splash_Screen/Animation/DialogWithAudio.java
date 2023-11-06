package View.Splash_Screen.Animation;

import java.util.List;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.util.Duration;

public class DialogWithAudio {
    Node node1, node2, node3, node4;

    public DialogWithAudio(Node node1, Node node2, Node node3, Node node4) {
        this.node1 = node1;
        this.node2 = node2;
        this.node3 = node3;
        this.node4 = node4;
    }

    public void playDialogWithAudio() {
        Duration animationDuration = Duration.seconds(2);
        TranslateTransition slideChat1 = new TranslateTransition(animationDuration, this.node1);
        TranslateTransition slideChat2 = new TranslateTransition(animationDuration, this.node2);
        TranslateTransition slideChat3 = new TranslateTransition(animationDuration, this.node3);
        TranslateTransition slideChat4 = new TranslateTransition(animationDuration, this.node4); // Membuat transisi

        // Membuat transisi paralel untuk slide 1 dan 2
        KeyFrame kf = new KeyFrame(Duration.millis(900), (ActionEvent event) -> {
            // frame 1
            slideChat1.setToY(250);
            slideChat2.setDelay(Duration.seconds(3));
            slideChat1.play();
            slideChat1.setOnFinished(e -> {
                slideChat1.setDuration(Duration.millis(900));
                slideChat1.setToY(200);
            });
            slideChat1.setDelay(Duration.seconds(3));

            // frame 2
            slideChat2.setToY(250);
            ParallelTransition pt = new ParallelTransition(slideChat1, slideChat2);
            pt.play();

            pt.setOnFinished(e2 -> {
                slideChat2.setDelay(Duration.seconds(3));
                slideChat2.setToY(200);
            });

            // frame 3
            slideChat1.setToY(150);
            slideChat3.setToY(250);

        });

        Timeline timeline = new Timeline(kf);
        timeline.setCycleCount(1); // Memainkan animasi hanya sekali
        timeline.play();
    }
}