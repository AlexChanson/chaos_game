package view;

import geometry.Game;
import geometry.Vector;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Controller {
    @FXML
    private BorderPane bPane;
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
    }

    @FXML
    public void menuNew(){
        Game g = new Game(16, 9, 2, 3);
        g.play(100000);
        System.out.println("Done, " + g.getOtherPoints().size());
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
            game.get().getOtherPoints().forEach(vector -> gc.fillOval(vector.getX() * widthRatio, vector.getY() * heightRatio, 2, 2));
            gc.setFill(Color.RED);
            for (Vector v : game.get().getStartingPoints())
                gc.fillOval(v.getX()*widthRatio, v.getY()*heightRatio, 8, 8);
        }
    }

    public Image export(){
        return null;
    }

}
