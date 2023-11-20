package View.Splash_Screen;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

import javafx.event.EventHandler;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;
import javafx.concurrent.Service;
import javafx.animation.FadeTransition;

public class SplashScreen {
    private Stage stage;
    private String assetPath = "Assets/View/Splash_Screen/";
    private String imgPath = assetPath + "images";
    private List<Image> contents;
    private List<Image> chats;

    public static final double CARO_IMAGE_WIDTH = 100;
    public static final double CARO_IMAGE_HEIGHT = 100;

    public SplashScreen(Window owner) {
        this.stage = new Stage();
        this.stage.initOwner(owner);
        this.stage.initStyle(StageStyle.TRANSPARENT);
    }

    public void start() {

        Rectangle outBackground = createRectangle(900, 550, 60, 60, Color.rgb(20, 31, 35));

        ImageView logo = createImage(imgPath + "/logo/celengan_image_logo.png", 58, 58, -397, -215);
        Text logoText = createText("Celengan", "-fx-font: 36 Poppins;", "#FFFFFF", -268, -209);

        // create button
        ImageView buttonMasuk = createImage(imgPath + "/button/masuk.png", 100, 100, 334, -179);

        // Create a carousel
        // adding carousel content image
        contents = new ArrayList<>();
        contents.add(new Image(imgPath + "/carousel/caro_1.png"));
        contents.add(new Image(imgPath + "/carousel/caro_2.png"));
        contents.add(new Image(imgPath + "/carousel/caro_3.png"));
        contents.add(new Image(imgPath + "/carousel/caro_4.png"));
        contents.add(new Image(imgPath + "/carousel/caro_5.png"));
        // adding carousel chat image
        // chats = new ArrayList<>();
        // chats.add(new Image(imgPath + "/carousel/chatCaro1_1.png"));
        // chats.add(new Image(imgPath + "/carousel/chatCaro1_2.png"));
        // chats.add(new Image(imgPath + "/carousel/chatCaro2_1.png"));
        // chats.add(new Image(imgPath + "/carousel/chatCaro2_2.png"));
        // chats.add(new Image(imgPath + "/carousel/chatCaro3_1.png"));
        // chats.add(new Image(imgPath + "/carousel/chatCaro3_2.png"));
        // chats.add(new Image(imgPath + "/carousel/chatCaro4_1.png"));
        // chats.add(new Image(imgPath + "/carousel/chatCaro4_2.png"));
        // chats.add(new Image(imgPath + "/carousel/chatCaro5_1.png"));
        // chats.add(new Image(imgPath + "/carousel/chatCaro5_2.png"));

        Carousel carousel = new Carousel(contents);
        StackPane carouselPane = carousel.getCarouselPane();
        StackPane outPane = new StackPane(outBackground);
        StackPane mainContent = new StackPane(carouselPane, buttonMasuk, logo, logoText);

        StackPane root = new StackPane();
        root.getChildren().addAll(outPane, mainContent);
        // on mouse click
        // setOnMouseClicked(root, buttonMasuk);

        Scene scene = new Scene(root, Color.TRANSPARENT);
        scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Poppins:wght@700&display=swap");

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

        // Start the carousel animation
        carousel.startAnimation();

    }

    // Mouse COORDINATES TRACKER
    private void setOnMouseClicked(StackPane root, Node item) {
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                double x = me.getSceneX();
                double y = me.getSceneY();

                // Calculate the translation values for the item
                double itemX = x - (root.getWidth() / 2); // Adjust for the center of the root
                double itemY = y - (root.getHeight() / 2); // Adjust for the center of the root

                // Set the new translation values for the item
                item.setTranslateX(itemX);
                item.setTranslateY(itemY);

                System.out.println("Item placed at X -> " + itemX);
                System.out.println("Item placed at Y -> " + itemY);
            }
        });
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

    private static final double DRAG_THRESHOLD = 20.0;

    private HBox dotContainer;
    final Color activeDots = Color.valueOf("#AEFD3A");
    final Color passiveDots = Color.valueOf("#263940");

    public Carousel(List<Image> contents) {
        this.contents = contents;
        initializeCarousel();
    }

    public StackPane getCarouselPane() {
        return carouselPane;
    }

    public void startAnimation() {
        // Add drag event handlers
        carouselPane.setOnMousePressed(event -> {
            dragStartX = event.getSceneX();
            isDragging = true;
        });

        carouselPane.setOnMouseDragged(event -> {
            carouselPane.setOnMouseReleased(event2 -> {
                double deltaX = event.getSceneX() - dragStartX;
                showImageByDrag(event, deltaX);
                isDragging = false; // Reset the flag after dragging is complete
                dragStartX = 0; // Reset dragStartX after releasing the mouse
            });
        });
    }

    private void initializeCarousel() {
        carouselPane = new StackPane();
        carouselPane.setPrefSize(CARO_IMAGE_WIDTH, CARO_IMAGE_HEIGHT);

        // Initial image
        ImageView initialImage = createResizedImageView(contents.get(currentIndex), CARO_IMAGE_WIDTH,
                CARO_IMAGE_HEIGHT);
        carouselPane.getChildren().add(initialImage);

        // Create dot indicators
        dotContainer = createDotIndicators(contents.size());
        StackPane.setAlignment(dotContainer, Pos.BOTTOM_CENTER);
        carouselPane.getChildren().add(dotContainer);
    }

    private HBox createDotIndicators(int numDots) {
        HBox dots = new HBox(20); // Adjust the spacing between dots
        dots.setAlignment(Pos.CENTER);
        dots.setTranslateY(200);

        for (int i = 0; i < numDots; i++) {
            Circle dot = new Circle(5);
            dot.setFill(i == 0 ? activeDots : passiveDots); // Highlight the first dot
            dots.getChildren().add(dot);
        }

        return dots;
    }

    private void updateDotIndicators() {
        for (Node dot : dotContainer.getChildren()) {
            ((Circle) dot).setFill(passiveDots);
        }

        ((Circle) dotContainer.getChildren().get(currentIndex)).setFill(activeDots);
    }

    private void showImageByDrag(MouseEvent event, double deltaX) {
        double totalDeltaX = Math.abs(deltaX);

        if (totalDeltaX > DRAG_THRESHOLD) {
            if (deltaX > 0) {
                // Dragging to the right
                currentIndex = (currentIndex - 1 + contents.size()) % contents.size();
            } else {
                // Dragging to the left
                currentIndex = (currentIndex + 1) % contents.size();
            }

            // Create a new image with opacity set to 0
            ImageView imageView = createResizedImageView(contents.get(currentIndex), CARO_IMAGE_WIDTH,
                    CARO_IMAGE_HEIGHT);
            imageView.setOpacity(0.0);

            // Add fade-out animation
            FadeTransition fadeOut = new FadeTransition(Duration.millis(80), carouselPane.getChildren().get(0));
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(e -> {
                // Update the carousel pane with the new image after fade-out
                carouselPane.getChildren().set(0, imageView);

                // Add fade-in animation
                FadeTransition fadeIn = new FadeTransition(Duration.millis(80), imageView);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();

                // Update dot indicators
                updateDotIndicators();
            });
            fadeOut.play();

            // Reset dragStartX after changing the image
            dragStartX = event.getSceneX();
        }
    }

    private ImageView createResizedImageView(Image image, double width, double height) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);
        return imageView;
    }
}