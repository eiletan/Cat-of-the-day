package GUI;

import Model.Cat;
import Model.CatHandler;
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
import java.net.URL;
import java.net.URLConnection;

public class Capplication extends Application {
    private static int breednum;

    private int gridgap = 10;
    private int padding = 25;
    private int windowwidth = 600;
    private int windowheight = 550;
    // These do not have to be fields
    private static Image cattax;
    private static String catdesc;
    private static String catname;
    private static int i = 0;

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
    public static void main(String args[])throws IOException{
        launch(args);
    }

    // API code adapted from CPSC 210 edx page, which in turn was from http://zetcode.com/articles/javareadwebpage/
    // EFFECTS: Initializes a list of breeds with data taken from the API



    // Code handling the image link from stackoverflow
    // REQUIRES: Cat is not null
    // MODIFIES: This
    // EFFECTS: Creates an image from the cat's image link and sets it in the field
    private void generateImage(Cat cat) throws IOException{
        String url = cat.getCaturl();
        URLConnection openConnection = new URL(url).openConnection();
        openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");

        Image image = new Image(openConnection.getInputStream());
        cattax = image;
    }

    private void generateDesc(Cat cat){
        catdesc = cat.getDesc();
    }

    private void generateName(Cat cat){
        catname = cat.getName();
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
                    generateImage(grumpy);
                    generateDesc(grumpy);
                    generateName(grumpy);
                }
                catch(IOException io){
                    System.out.println("oi");
                }
                display(back, primaryStage);
            }
        });
    }

    private void display(Button back, Stage primaryStage) {
        GridPane grid = gridInit();


        Text name = textInit(catname,"Calibri", FontWeight.NORMAL,20);
        name.setId("title");


        Label ldesc = new Label(catdesc);
        ldesc.setWrapText(true);
        ldesc.setId("desc");

        addToGrid(grid, name, 0, 0, 1,1);
        addToGrid(grid,ldesc,0,1,30,2);

        addImage(back, primaryStage, grid);
    }

    // Image code from https://www.tutorialspoint.com/javafx/javafx_images.htm
    private void addImage(Button back, Stage primaryStage, GridPane grid) {
        ImageView imageView = new ImageView();
        imageView.setImage(cattax);


        imageView.setX(300);
        imageView.setY(200);


        imageView.setFitHeight(250);

        imageView.setPreserveRatio(true);


        addToGrid(grid,imageView,0,3,30,30);
        addToGrid(grid,back,32,31,1,1);

        System.out.println(catname);

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
