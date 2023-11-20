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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

import javafx.event.EventHandler;
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

    public SplashScreen(Window owner) {
        this.stage = new Stage();
        this.stage.initOwner(owner);
        this.stage.initStyle(StageStyle.TRANSPARENT);
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
        // on mouse click
        // setOnMouseClicked(root, logo);

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

    private boolean dragOccurred = false;

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
            double deltaX = event.getSceneX() - dragStartX;
            if (isDragging) {
                showImageByDrag(event, deltaX);
            }
        });

        carouselPane.setOnMouseReleased(event -> {
            if (isDragging) {
                double deltaX = event.getSceneX() - dragStartX;
                showImageByDrag(event, deltaX);
                isDragging = false; // Reset the flag after dragging is complete
            }
            dragStartX = 0; // Reset dragStartX after releasing the mouse
        });
    }

    private void initializeCarousel() {
        carouselPane = new StackPane();
        carouselPane.setPrefSize(CARO_IMAGE_WIDTH, CARO_IMAGE_HEIGHT);

        // Initial image
        ImageView initialImage = createResizedImageView(contents.get(currentIndex), CARO_IMAGE_WIDTH,
                CARO_IMAGE_HEIGHT);
        carouselPane.getChildren().add(initialImage);
    }

    private void showImageByDrag(MouseEvent event, double deltaX) {
        double totalDeltaX = Math.abs(deltaX);

        if (totalDeltaX > DRAG_THRESHOLD) {
            if (deltaX > 0) {
                // Dragging to the right
                currentIndex = (currentIndex + 1) % contents.size();
            } else {
                // Dragging to the left
                currentIndex = (currentIndex - 1 + contents.size()) % contents.size();
            }

            ImageView imageView = createResizedImageView(contents.get(currentIndex), CARO_IMAGE_WIDTH,
                    CARO_IMAGE_HEIGHT);
            carouselPane.getChildren().set(0, imageView);

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

    private void fadeInImage(ImageView imageView) {
        fadeInImage(imageView, 1.0);
    }

    private void fadeInImage(ImageView imageView, double opacity) {
        imageView.setOpacity(opacity);
    }

}
