package GUI;

import Model.Cat;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Random;

public class Capplication extends Application {
    private static ArrayList<String> breedlist = new ArrayList<>();
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
//    grid.setGridLinesVisible(true);

    grid.add(title,15,0,1,1);
    grid.add(inst,0,1,30,1);

    Button catb = new Button("CAT");
    title.setId("title");
    inst.setId("inst");

    Button back = new Button("Back");

    buttonHandler(primaryStage,scene,back);



    grid.add(catb,15,2,1,1);

    catb.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent e){

            try {
                Cat grumpy = new Cat(selectBreed());
                generateImage(grumpy);
                generateDesc(grumpy);
                generateName(grumpy);
            }
            catch(IOException io){
                System.out.println("oi");
            }


                GridPane grid = gridInit();


                Text name = textInit(catname,"Calibri",FontWeight.NORMAL,20);
                name.setId("title");


                Label ldesc = new Label(catdesc);
                ldesc.setWrapText(true);
                ldesc.setId("desc");

                grid.add(name, 0, 0, 1, 1);
                grid.add(ldesc, 0, 1, 30, 2);


                // Image code from https://www.tutorialspoint.com/javafx/javafx_images.htm
                ImageView imageView = new ImageView();
                imageView.setImage(cattax);


                imageView.setX(300);
                imageView.setY(200);


                imageView.setFitHeight(250);

                imageView.setPreserveRatio(true);


                grid.add(imageView, 0, 3, 30, 30);
                grid.add(back,32,31,1,1);
;
                System.out.println(catname);

                Scene scene = new Scene(grid, 600, 550);
                scene.getStylesheets().add("catstyle2.css");

                primaryStage.setTitle("Your cat tax form has been approved");

                primaryStage.setScene(scene);

                primaryStage.show();


            }
    });



    primaryStage.show();

    }
    public static void main(String args[])throws IOException{
        initializeBreedList();
        launch(args);
    }

    // API code adapted from CPSC 210 edx page, which in turn was from http://zetcode.com/articles/javareadwebpage/
    // EFFECTS: Initializes a list of breeds with data taken from the API
    private static void initializeBreedList() throws IOException {
        BufferedReader br = null;

        try {
            String apikey = "4654f6ca-0eb3-4f4d-b314-6e8623019cf6";
            String catapi = "https://api.thecatapi.com/v1/breeds?api_key=";
            String theURL = catapi + apikey;
            URL url = new URL(theURL);
            br = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;

            StringBuilder sb = new StringBuilder();



            while ((line = br.readLine()) != null) {

                sb.append(line);
                sb.append(System.lineSeparator());


            }


            JSONParser parser = new JSONParser();


            try {
                Object obj = parser.parse(sb.toString());
                JSONArray array = (JSONArray) obj;

                breednum = array.size();

                for (int i = 0; i < array.size(); i++) {
                    JSONObject obj2 = (JSONObject) array.get(i);
                    breedlist.add(obj2.get("id").toString());
                }


            } catch (ParseException pe) {

                System.out.println("position: " + pe.getPosition());
                System.out.println(pe);
            }


        } finally {

            if (br != null) {
                br.close();
            }
        }
    }

    private static String selectBreed(){
        Random rng = new Random();
        int no = rng.nextInt(67);
        return breedlist.get(no);
    }

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

}
