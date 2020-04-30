package Model;

import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class ImageHandler {
    private String url;

    public ImageHandler(String url) {
        this.url = url;
    }

    public Image generateImage() throws IOException {
        URLConnection openConnection = new URL(this.url).openConnection();
        openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");

        Image image = new Image(openConnection.getInputStream());
        return image;
    }
}
