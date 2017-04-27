package view;

import geometry.Game;
import geometry.Vector;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Controller {
    @FXML
    private BorderPane bPane;
    @FXML
    private TextField startPointsField, distanceField;
    @FXML
    Slider precisionSlider;
    private Canvas canvas;
    private ObjectProperty<Game> game = new SimpleObjectProperty<>(null);

    public void initialize(){
        //Setting up the canvas in the center of the border pane
        Pane wrapperPane = new Pane();
        wrapperPane.setStyle("-fx-background-color: black");
        bPane.setCenter(wrapperPane);
        canvas = new Canvas();
        wrapperPane.getChildren().add(canvas);
        //setting the bindings to resize
        canvas.widthProperty().bind(wrapperPane.widthProperty());
        canvas.heightProperty().bind(wrapperPane.heightProperty());

        canvas.heightProperty().addListener(observable -> drawGame());
        canvas.widthProperty().addListener(observable -> drawGame());

        startPointsField.setTextFormatter(new TextFormatter<>(FxUtils.intFilter));
        distanceField.setTextFormatter(new TextFormatter<>(FxUtils.intFilter));
    }

    @FXML
    public void menuNew(){

    }

    @FXML
    public void generate(){
        Game g = new Game(16, 9, 1.0/Integer.parseInt(distanceField.getText()), Integer.parseInt(startPointsField.getText()));
        g.play((int) (100000*precisionSlider.getValue()));
        game.set(g);
        drawGame();
    }

    private void drawGame(){
        if (game.get() != null){
            double heightRatio = canvas.getHeight() / game.get().getHeight();
            double widthRatio = canvas.getWidth() / game.get().getWidth();
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0,0,canvas.getWidth(), canvas.getHeight());
            gc.setFill(Color.WHITE);
            game.get().getOtherPoints().forEach(vector -> gc.fillOval(vector.getX() * widthRatio, vector.getY() * heightRatio, 1, 1));
            gc.setFill(Color.RED);
            for (Vector v : game.get().getStartingPoints())
                gc.fillOval(v.getX()*widthRatio, v.getY()*heightRatio, 8, 8);
        }
    }

    public Image export(){
        return null;
    }

}
