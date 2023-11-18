package View.Splash_Screen;

import javafx.event.EventHandler;
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

public class SplashScreen {
    private Stage stage;

    private String assetPath = "././Assets/View/Splash_Screen/";
    private String imgPath = assetPath + "images/";

    public SplashScreen(Stage stage) {
        this.stage = stage;
    }

    public void start() {
        /* BACKGROUND PANE */
        Rectangle outBackground = createRectangle(900, 550, 60, 60, Color.rgb(20, 31, 35));
        /* LOGO */
        // image
        ImageView logo = createImage(imgPath + "/logo/celengan_image_logo.png", 58, 58, -397, -215);
        // text
        Text logoText = createText("Celengan", "-fx-font: 36 Poppins;", "#FFFFFF", -268, -209);

        /* PANES (TO SHOW) */
        StackPane outPane = new StackPane(outBackground);
        StackPane mainContent = new StackPane(logo, logoText);

        // adding all panes
        StackPane root = new StackPane();
        root.getChildren().addAll(outPane, mainContent);

        // Set mouse click event handler for root
        // setOnMouseClicked(root, logo);

        Scene scene = new Scene(root, Color.TRANSPARENT);
        // getting fonts
        scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Poppins:wght@700&display=swap");

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    // translation calculator
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

    // create rectangle
    private Rectangle createRectangle(double width, double height, double arcWidth, double arcHeight, Color color) {
        Rectangle rect = new Rectangle(width, height);
        rect.setArcWidth(arcWidth);
        rect.setArcHeight(arcHeight);
        rect.setFill(color);

        return rect;
    }

    // create image
    private ImageView createImage(String imgPath, double width, double height, double transX, double transY) {
        ImageView imageView = new ImageView();

        Image imageImg = new Image(imgPath);

        imageView.setImage(imageImg);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setTranslateX(transX);
        imageView.setTranslateY(transY);

        return imageView;
    }

    // create text
    private Text createText(String text, String style, String color, double transX, double transY) {
        Text newText = new Text(text);
        newText.setStyle(style);
        newText.setFill(Color.valueOf(color));
        newText.setTranslateX(transX);
        newText.setTranslateY(transY);

        return newText;
    }
}
