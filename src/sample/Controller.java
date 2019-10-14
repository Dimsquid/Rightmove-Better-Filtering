package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;


public class Controller {

    @FXML
    public TableView houseTable;
    @FXML
    public TableColumn idColumn;
    @FXML
    public TableColumn photoColumn;
    @FXML
    public TableColumn locationColumn;
    @FXML
    public TableColumn priceColumn;
    @FXML
    public TableColumn descColumn;
    @FXML
    public TableColumn numberColumn;
    @FXML
    public TableColumn linkColumn;

    @FXML
    public CheckBox oap;
    @FXML
    public CheckBox beach;
    @FXML
    public CheckBox garage;
    @FXML
    public CheckBox land;
    @FXML
    public CheckBox apartment;
    @FXML
    public CheckBox flat;
    @FXML
    public CheckBox studio;
    @FXML
    public CheckBox mobile;
    @FXML
    public CheckBox onebed;
    @FXML
    public CheckBox twobeds;
    @FXML
    public CheckBox threebeds;
    @FXML
    public CheckBox fourbeds;
    @FXML
    public TextField searchPrice;


    public String linkAddress;

    public void reloadData() {
        Main.creatJSONFile( oap,  beach,  garage,  land,  apartment,  flat,  studio,  mobile,  onebed,  twobeds,  threebeds,  fourbeds, searchPrice);
        houseTable.getItems().clear();
        try {
        File json = new File("JSON_RIGHTMOVE.JSON");
            JSONParser parser = new JSONParser();
            FileReader file = new FileReader("JSON_RIGHTMOVE.JSON");
            Object jsob = parser.parse(file);
            JSONArray a = (JSONArray) jsob;
            for (Object o : a) {
                Houses houseClass = new Houses();
                JSONObject house = (JSONObject) o;
                System.out.println(house.get("id"));
                houseClass.setIdColumn((Long) house.get("id"));

                if((!house.get("photo").equals(null)) && (!house.get("photo").equals(""))) {
                    Image img = new Image((String) house.get("photo"));
                    ImageView photo = new ImageView(img);
                    houseClass.setPhotoColumn(photo);
                }
                houseClass.setLocationColumn((String) house.get("address"));
                houseClass.setPriceColumn((String) house.get("price"));
                houseClass.setDescColumn((String) house.get("description"));
                houseClass.setNumberColumn((String) house.get("phoneNumber"));

                Hyperlink link = new Hyperlink((String) house.get("linkAddress"));
                linkAddress = (String) house.get("linkAddress");
                link.setOnMouseClicked(buttonHandler);
                houseClass.setLinkColumn(link);
            idColumn.setCellValueFactory(new PropertyValueFactory<>("idColumn"));
            photoColumn.setCellValueFactory(new PropertyValueFactory<>("photoColumn"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("locationColumn"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("priceColumn"));
            descColumn.setCellValueFactory(new PropertyValueFactory<>("descColumn"));
            numberColumn.setCellValueFactory(new PropertyValueFactory<>("numberColumn"));
            linkColumn.setCellValueFactory(new PropertyValueFactory<>("linkColumn"));

                houseTable.getItems().add(houseClass);
            }

            file.close();
            try {
                Files.deleteIfExists(Paths.get("JSON_RIGHTMOVE.JSON"));
            }
            catch(NoSuchFileException e) {
                System.out.println("No such file/directory exists");
            }
            catch(DirectoryNotEmptyException e)
            {
                System.out.println("Directory is not empty.");
            }
            catch(IOException e)
            {
                System.out.println("Invalid permissions.");
            }
            System.out.println("Deletion successful.");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

}

    EventHandler<MouseEvent> buttonHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            try {
                Desktop.getDesktop().browse(new URL(((Hyperlink)event.getSource()).getText()).toURI());
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
        }
    };

}
