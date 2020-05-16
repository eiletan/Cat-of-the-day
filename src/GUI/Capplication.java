package GUI;

import Exceptions.RestartException;
import Model.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Capplication extends Application {

    private int gridgap = 10;
    private int padding = 25;
    private int windowwidth = 600;
    private int windowheight = 550;

    @Override
    public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("C A T ");

    intializeScreen(primaryStage);

    primaryStage.show();

    }
    public static void main(String args[]){
        launch(args);
    }


    private GridPane gridInit() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(gridgap);
        grid.setVgap(gridgap);
        grid.setPadding(new Insets(padding,padding,padding,padding));
        return grid;
    }

    private Scene sceneInit(Stage primaryStage, GridPane grid) {
        Scene scene = new Scene(grid,windowwidth,windowheight);
        primaryStage.setScene(scene);
        return scene;
    }

    private Text textInit(String message, String font, FontWeight weight, int textsize) {
        Text text = new Text(message);
        text.setFont(Font.font(font,weight,textsize));
        return text;
    }

    private void backHandler(Stage primaryStage, Scene scene, Button button) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(scene);
            }
        });
    }

    private void mainButtonHandler(Stage primaryStage, Button button, Scene scene) {

        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e){

                try {
                    CatHandler ch = new CatHandler();
                    String breedid = ch.selectBreed();
                    CatFactory cf = new CatInitializer(breedid);
                    Cat grumpy = cf.initializeCat();
                    display(primaryStage, grumpy, scene);
                }
                catch(IOException io){
                    initializeScreen(primaryStage, scene, "Error! Cat image could not be retrieved!");
                }
                catch(RestartException re) {
                    initializeScreen(primaryStage,scene, "Error! The cat could not be retrieved!");
                }
            }
        });
    }

    private void display(Stage primaryStage,Cat cat, Scene iscene) throws IOException {
        GridPane grid = gridInit();


        Text name = textInit(cat.getName(),"Calibri", FontWeight.NORMAL,20);
        name.setId("title");


        Label ldesc = new Label(cat.getDesc());
        ldesc.setWrapText(true);
        ldesc.setId("desc");

        addToGrid(grid, name, 0, 0, 1,1);
        addToGrid(grid,ldesc,0,1,30,2);

        ImageView image = retrieveImage(cat);

        Button back = new Button("Back");
        backHandler(primaryStage,iscene,back);

        addToGrid(grid,image,0,3,30,30);
        addToGrid(grid,back,32,31,1,1);


        Scene scene = sceneInit(primaryStage, grid);
        scene.getStylesheets().add("catstyle2.css");

    }

    // Image code from https://www.tutorialspoint.com/javafx/javafx_images.htm
    private ImageView retrieveImage(Cat cat) throws IOException {
        ImageView imageView = new ImageView();
        ImageHandler imghan = new ImageHandler(cat.getCaturl());
        Image image = imghan.generateImage();
        imageView.setImage(image);


        imageView.setX(300);
        imageView.setY(200);


        imageView.setFitHeight(250);

        imageView.setPreserveRatio(true);

        return imageView;
    }

    private void addToGrid(GridPane grid, Node node, int i, int i2, int i3, int i4) {
        grid.add(node, i, i2, i3, i4);
    }

    private void initializeScreen(Stage primaryStage, Scene back, String message) {
        GridPane grid = gridInit();

        Scene scene = sceneInit(primaryStage,grid);
        scene.getStylesheets().add("catstyle.css");
        Text title = textInit( message,"Calibri", FontWeight.NORMAL,20);
        Text inst = textInit("Please press the back button and try again!","Calibri",FontWeight.NORMAL,20);

        addToGrid(grid, title, 15, 0, 30,1);
        addToGrid(grid, inst, 15, 0, 30,1);
        Button backbtn = new Button("Back");
        addToGrid(grid,backbtn,15,2,30,1);
        backHandler(primaryStage,back,backbtn);
    }

    private void intializeScreen(Stage primaryStage) {
        GridPane grid = gridInit();

        Scene scene = sceneInit(primaryStage,grid);
        scene.getStylesheets().add("catstyle.css");
        Text title = textInit("Welcome","Calibri", FontWeight.NORMAL,20);
        Text inst = textInit("Press the button below to get an image of a cat!","Calibri",FontWeight.NORMAL,20);

        addToGrid(grid, title, 15, 0, 1,1);
        addToGrid(grid, inst, 0, 1, 30,1);

        Button catb = new Button("CAT");
        title.setId("title");
        inst.setId("inst");

        addToGrid(grid,catb,15,2,1,1);

        mainButtonHandler(primaryStage, catb ,scene);
    }
}
