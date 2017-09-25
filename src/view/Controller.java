package view;

import geometry.Game;
import geometry.Vector;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import java.awt.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utility.Pixel;
import utility.Rand;
import utility.State;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Controller {
    @FXML
    private BorderPane bPane;
    @FXML
    private TextField startPointsField, distanceField;
    @FXML
    Slider precisionSlider;
    @FXML
    private ComboBox<String> rules;
    private HashMap<String, Function<State, Vector>> ruleFunctions;
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

        //Adding rules to the selector
        ruleFunctions = new HashMap<>();

        rules.getItems().add("Sin");
        ruleFunctions.put("Sin", state -> Rand.choose(state.getStartingPoints()).minus(state.getCurrent()).multiply((Math.abs(Math.sin(state.getIterration())))).add(state.getCurrent()));

        rules.getItems().add("Classic");
        ruleFunctions.put("Classic", (state) -> Rand.choose(state.getStartingPoints()).minus(state.getCurrent()).multiply(0.5).add(state.getCurrent()));

        rules.getItems().add("Random");
        ruleFunctions.put("Random", ((state) -> Rand.choose(state.getStartingPoints()).minus(state.getCurrent()).multiply(Rand.randDouble(1)).add(state.getCurrent())));


    }

    @FXML
    public void menuNew(){

    }

    @FXML
    public void generate(){
        Game g = new Game(16, 9, 1.0/Integer.parseInt(distanceField.getText()), Integer.parseInt(startPointsField.getText()));
        g.play((int) (200000*precisionSlider.getValue()));
        game.set(g);
        drawGame();
    }

    @FXML
    public void customRule(){
        int n = 3;
        try{
            n = Integer.parseInt(startPointsField.getText());
        } catch (NumberFormatException ignored){}
        Game g = new Game(16, 9, 0, n);
        Function<State, Vector> rule = ruleFunctions.get(rules.getSelectionModel().getSelectedItem());
        if(rule == null)
            rule = ruleFunctions.get("Classic");
        g.playCustom((int) (200000*precisionSlider.getValue()), rule);
        game.set(g);
        drawGame();

        //Vector demo = game.get().getStartingPoints()[0];
        //System.out.printf("debug : x=%f, y=%f", demo.getX(), demo.getY());
    }

    private void drawGame(){
        if (game.get() != null){
            double heightRatio = canvas.getHeight() / game.get().getHeight();
            double widthRatio = canvas.getWidth() / game.get().getWidth();
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0,0,canvas.getWidth(), canvas.getHeight());
            gc.setFill(javafx.scene.paint.Color.WHITE);
            game.get().getOtherPoints().forEach(vector -> gc.fillOval(vector.getX() * widthRatio, vector.getY() * heightRatio, 1, 1));
            gc.setFill(javafx.scene.paint.Color.RED);
            for (Vector v : game.get().getStartingPoints())
                gc.fillOval(v.getX()*widthRatio, v.getY()*heightRatio, 8, 8);
        }
    }

    public Set<Pixel> getPixels(HashSet<Vector> vectors, double scaleX, double scaleY){
        TreeSet<Pixel> result = new TreeSet<>();
        vectors.forEach(vector -> {
            Vector temp = vector.multiply(scaleX, scaleY);
            result.add(new Pixel((int) (temp.getX()), (int) (temp.getY()), new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff}));
        });
        return result;
    }

    @FXML
    public void saveImage(){
        int width = 1920, height = 1080;

        FileChooser fc = new FileChooser();
        fc.setTitle("Save a level file");
        fc.setInitialDirectory(new File(System.getProperty("user.dir")));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Bitmap", "*.bmp"));

        Optional<File> file = Optional.ofNullable(fc.showSaveDialog(new Stage()));
        if (file.isPresent()){

            double heightRatio = height / (game.get() != null ? game.get().getHeight() : 1);
            double widthRatio = width / (game.get() != null ? game.get().getWidth() : 1);

            Set<Pixel> existingPixels = getPixels(game.get().getOtherPoints(), heightRatio, widthRatio);


            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            existingPixels.forEach(pixel -> {
                Color c = new Color(255,255,255);
                try {
                    bufferedImage.setRGB(pixel.getX(), pixel.getY(), c.getRGB());

                }catch (Exception ignored){}
            });
            try {
                ImageIO.write(bufferedImage, "bmp", file.get());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
