package GUI;

import Model.Cat;
import Model.CatHandler;
import Model.ImageHandler;
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
    primaryStage.setTitle("Purrfect");

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

    Button back = new Button("Back");

    buttonHandler(primaryStage,scene,back);

    addToGrid(grid,catb,15,2,1,1);

    mainButtonHandler(primaryStage, catb, back);



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

    private void buttonHandler(Stage primaryStage, Scene scene, Button button) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(scene);
            }
        });
    }

    private void mainButtonHandler(Stage primaryStage, Button button,Button back) {

        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e){

                try {
                    CatHandler ch = new CatHandler();
                    String breedid = ch.selectBreed();
                    Cat grumpy = new Cat(breedid);
                    display(back, primaryStage, grumpy);
                }
                catch(IOException io){
                    System.out.println("oi");
                }
            }
        });
    }

    private void display(Button back, Stage primaryStage,Cat cat) throws IOException {
        GridPane grid = gridInit();


        Text name = textInit(cat.getName(),"Calibri", FontWeight.NORMAL,20);
        name.setId("title");


        Label ldesc = new Label(cat.getDesc());
        ldesc.setWrapText(true);
        ldesc.setId("desc");

        addToGrid(grid, name, 0, 0, 1,1);
        addToGrid(grid,ldesc,0,1,30,2);

        addImage(back, primaryStage, grid, cat);
    }

    // Image code from https://www.tutorialspoint.com/javafx/javafx_images.htm
    private void addImage(Button back, Stage primaryStage, GridPane grid, Cat cat) throws IOException {
        ImageView imageView = new ImageView();
        ImageHandler imghan = new ImageHandler(cat.getCaturl());
        Image image = imghan.generateImage();
        imageView.setImage(image);


        imageView.setX(300);
        imageView.setY(200);


        imageView.setFitHeight(250);

        imageView.setPreserveRatio(true);


        addToGrid(grid,imageView,0,3,30,30);
        addToGrid(grid,back,32,31,1,1);

        System.out.println(cat.getName());

        Scene scene = new Scene(grid, 600, 550);
        scene.getStylesheets().add("catstyle2.css");

        primaryStage.setTitle("Your cat tax form has been approved");

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void addToGrid(GridPane grid, Node node, int i, int i2, int i3, int i4) {
        grid.add(node, i, i2, i3, i4);
    }

}
