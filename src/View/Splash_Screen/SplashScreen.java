package View.Splash_Screen;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import javafx.concurrent.Service;

public class SplashScreen {
    private Stage stage;
    private String assetPath = "Assets/View/Splash_Screen/";
    private String imgPath = assetPath + "images";
    private List<Image> contents;

    public static final double CARO_IMAGE_WIDTH = 100;
    public static final double CARO_IMAGE_HEIGHT = 100;

    public SplashScreen(Stage stage) {
        this.stage = stage;
    }

    public void start() {

        Rectangle outBackground = createRectangle(900, 550, 60, 60, Color.rgb(20, 31, 35));

        ImageView logo = createImage(imgPath + "/logo/celengan_image_logo.png", 58, 58, -397, -215);
        Text logoText = createText("Celengan", "-fx-font: 36 Poppins;", "#FFFFFF", -268, -209);

        // Create a carousel
        contents = new ArrayList<>();
        contents.add(new Image(imgPath + "/carousel/caro_1.png"));
        contents.add(new Image(imgPath + "/carousel/caro_2.png"));
        contents.add(new Image(imgPath + "/carousel/caro_3.png"));
        contents.add(new Image(imgPath + "/carousel/caro_4.png"));
        contents.add(new Image(imgPath + "/carousel/caro_5.png"));

        Carousel carousel = new Carousel(contents);
        StackPane carouselPane = carousel.getCarouselPane();

        StackPane outPane = new StackPane(outBackground);
        StackPane mainContent = new StackPane(logo, logoText, carouselPane);

        StackPane root = new StackPane();
        root.getChildren().addAll(outPane, mainContent);

        Scene scene = new Scene(root, Color.TRANSPARENT);
        scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Poppins:wght@700&display=swap");

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

        // Start the carousel animation
        carousel.startAnimation();

    }

    private Rectangle createRectangle(double width, double height, double arcWidth, double arcHeight, Color color) {
        Rectangle rect = new Rectangle(width, height);
        rect.setArcWidth(arcWidth);
        rect.setArcHeight(arcHeight);
        rect.setFill(color);

        return rect;
    }

    private ImageView createImage(String imgPath, double width, double height, double transX, double transY) {
        System.out.println("IMGPATH: " + imgPath);

        ImageView imageView = new ImageView();
        Image imageImg = new Image(imgPath);

        imageView.setImage(imageImg);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setTranslateX(transX);
        imageView.setTranslateY(transY);

        return imageView;
    }

    private Text createText(String text, String style, String color, double transX, double transY) {
        Text newText = new Text(text);
        newText.setStyle(style);
        newText.setFill(Color.valueOf(color));
        newText.setTranslateX(transX);
        newText.setTranslateY(transY);

        return newText;
    }

    public void hideSplashScreen() {
        // menutup atau menyembunyikan splash screen
        Platform.runLater(() -> {
            stage.hide();
        });

    }

}

class Carousel {
    private List<Image> contents;
    private StackPane carouselPane;
    private int currentIndex = 0;
    private double dragStartX;
    private boolean isDragging = false;
    public static final double CARO_IMAGE_WIDTH = 400;
    public static final double CARO_IMAGE_HEIGHT = 400;

    public Carousel(List<Image> contents) {
        this.contents = contents;
        initializeCarousel();
    }

    public StackPane getCarouselPane() {
        return carouselPane;
    }

    public void startAnimation() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> showNextImage()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Add drag event handlers
        carouselPane.setOnMousePressed(event -> {
            dragStartX = event.getSceneX();
            isDragging = true;
        });

        carouselPane.setOnMouseDragged(event -> {
            if (isDragging) {
                double deltaX = event.getSceneX() - dragStartX;
                showImageByDrag(deltaX);
            }
        });

        carouselPane.setOnMouseReleased(event -> isDragging = false);

    }

    private void initializeCarousel() {
        carouselPane = new StackPane();
        carouselPane.setPrefSize(CARO_IMAGE_WIDTH, CARO_IMAGE_HEIGHT);

        // Initial image
        ImageView initialImage = createResizedImageView(contents.get(currentIndex), CARO_IMAGE_WIDTH,
                CARO_IMAGE_HEIGHT);
        carouselPane.getChildren().add(initialImage);
    }

    private void showNextImage() {
        currentIndex = (currentIndex + 1) % contents.size();
        ImageView imageView = createResizedImageView(contents.get(currentIndex), CARO_IMAGE_WIDTH, CARO_IMAGE_HEIGHT);
        fadeInImage(imageView);
        carouselPane.getChildren().set(0, imageView);
    }

    private void showImageByDrag(double deltaX) {
        int direction = (deltaX > 0) ? -1 : 1;
        int newIndex = (currentIndex + direction + contents.size()) % contents.size();
        double opacity = Math.abs(deltaX) / carouselPane.getWidth();
        ImageView imageView = createResizedImageView(contents.get(newIndex), CARO_IMAGE_WIDTH, CARO_IMAGE_HEIGHT);
        fadeInImage(imageView, opacity);
        carouselPane.getChildren().set(0, imageView);
    }

    private ImageView createResizedImageView(Image image, double width, double height) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private void fadeInImage(ImageView imageView) {
        fadeInImage(imageView, 1.0);
    }

    private void fadeInImage(ImageView imageView, double opacity) {
        imageView.setOpacity(opacity);
    }

}
