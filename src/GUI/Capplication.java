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
    private static Image cattax;
    private static String catdesc;
    private static String catname;
    private static int i = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Purrfect");

    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25, 25, 25, 25));

    Scene scene = new Scene(grid, 600, 550);
    primaryStage.setScene(scene);
    Text title = new Text("Welcome");
    title.setFont(Font.font("Calibri", FontWeight.NORMAL, 20));
//    grid.setGridLinesVisible(true);
    grid.add(title,15,0,1,1);
    Text inst = new Text("Press the button below to get an image of a cat!");
    inst.setFont(Font.font("Calibri", FontWeight.NORMAL, 20));
    grid.add(inst,0,1,30,1);
    Button catb = new Button("Cat Tax");
    scene.getStylesheets().add("catstyle.css");
    title.setId("title");
    inst.setId("inst");

    Button back = new Button("Back");

    back.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            primaryStage.setScene(scene);
        }
    });

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


                GridPane grid = new GridPane();
                grid.setAlignment(Pos.CENTER);
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(25, 25, 25, 25));


                Text name = new Text(catname);
                name.setFont(Font.font("Calibri", FontWeight.NORMAL, 20));
                name.setId("title");

                Text desc = new Text(catdesc);
                desc.setFont(Font.font("Calibri", FontWeight.NORMAL, 10));
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
    private static void generateImage(Cat cat) throws IOException{
        String url = cat.getCaturl().toString();
        URLConnection openConnection = new URL(url).openConnection();
        openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");

        Image image = new Image(openConnection.getInputStream());
        cattax = image;
    }

    private static void generateDesc(Cat cat){
        catdesc = cat.getDesc();
    }

    private static void generateName(Cat cat){
        catname = cat.getName();
    }

    private static boolean refresh()throws IOException{
        i++;
        System.out.println("i: " +i);
        if(i % 5 == 0){
            i = 0;
            Cat newcat = new Cat(selectBreed());
            generateName(newcat);
            generateDesc(newcat);
            generateImage(newcat);
            return true;
        }
        return false;
    }
}
