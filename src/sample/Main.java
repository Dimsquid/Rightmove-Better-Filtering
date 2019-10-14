package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main extends Application {

    public static JSONObject jsob = new JSONObject();
    public static JSONArray jsarr = new JSONArray();
    public static Map houseMap = new LinkedHashMap(5);

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Rightmove Better Searching");
        primaryStage.setScene(new Scene(root));
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void creatJSONFile(CheckBox oap, CheckBox beach, CheckBox garage, CheckBox land, CheckBox apartment, CheckBox flat, CheckBox studio, CheckBox mobile, CheckBox onebed, CheckBox twobeds, CheckBox threebeds, CheckBox fourbeds, TextField searchPrice) {
        jsob.clear();
        jsarr.clear();
        houseMap.clear();
        int count = 0;
        Integer price = 500000;
        try {
            Document elemnt = Jsoup.connect("https://www.rightmove.co.uk/property-for-sale/find.html?locationIdentifier=POSTCODE%5E1005452&radius=10.0&sortType=1&propertyTypes=&includeSSTC=false&mustHave=&dontShow=&furnishTypes=&keywords=").get();
            String indexCounter = elemnt.getElementsByClass("searchHeader-resultCount").get(0).text();
            for (int i = 0; i < Integer.parseInt(indexCounter.replaceAll(",", "")); i += 24) {
                Document doc = Jsoup.connect("https://www.rightmove.co.uk/property-for-sale/find.html?locationIdentifier=POSTCODE%5E1005452&radius=5.0&sortType=1&index=" + i + "&propertyTypes=&includeSSTC=false&mustHave=&dontShow=&furnishTypes=&keywords=").get();
                Elements houses = doc.getElementsByClass("l-searchResult");

                for (Element house : houses) {
                    String houseAddress = house.getElementsByClass("propertyCard-address").text();
                    String houseBedroom = house.getElementsByClass("propertyCard-title").text();
                    String housePhoto = house.getElementsByAttributeValueContaining("src", "https://media.rightmove.co.uk:443/dir/crop/").attr("src");
                    String houseBedroomCount = houseBedroom.split(" ")[0] + " " + houseBedroom.split(" ")[1];
                    String housePrice = house.getElementsByClass("propertyCard-priceValue").text();
                    String housePhoneNumber = house.getElementsByClass("propertyCard-contactsPhoneNumber").text();
                    String houseDescription = house.getElementsByAttributeValue("data-test", "property-description").text();
                    String houseLink = house.getElementsByClass("propertyCard-link").attr("href");
                    String finalLinkAddress = "https://www.rightmove.co.uk" + houseLink;


                    if (finalLinkAddress.equalsIgnoreCase("https://www.rightmove.co.uk")) {
                        outputJSONFile();
                        System.exit(1);
                    }


                    if(oap.isSelected() && (houseDescription.toLowerCase().contains("60s"))
                            || (houseDescription.toLowerCase().contains("over 60"))
                            || (houseDescription.toLowerCase().contains("over 50"))
                            || (houseDescription.toLowerCase().contains("50s"))
                            || (houseDescription.toLowerCase().contains("retirement"))){
                        break;
                    }
                    if(beach.isSelected() && (houseDescription.toLowerCase().contains("beach hut"))){
                        break;
                    }
                    if(flat.isSelected() && (houseDescription.toLowerCase().contains("flat"))){
                        break;
                    }
                    if(studio.isSelected() && (houseDescription.toLowerCase().contains("studio"))){
                        break;
                    }
                    if(mobile.isSelected() && (houseDescription.toLowerCase().contains("cabin")) && (houseDescription.toLowerCase().contains("park home")) && (houseDescription.toLowerCase().contains("park"))){
                        break;
                    }
                    if(land.isSelected() && (houseDescription.toLowerCase().contains("land"))){
                        break;
                    }
                    if(garage.isSelected() && (houseBedroomCount.toLowerCase().contains("garage")) && (houseDescription.toLowerCase().contains("garage"))){
                        break;
                    }
                    if(apartment.isSelected() && (houseDescription.toLowerCase().contains("apartment"))){
                        break;
                    }
                    if(onebed.isSelected() && (houseBedroomCount.toLowerCase().contains("1"))){
                        break;
                    }
                    if(twobeds.isSelected() && (houseBedroomCount.toLowerCase().contains("1")) && (houseBedroomCount.toLowerCase().contains("2"))){
                        break;
                    }
                    if(threebeds.isSelected() && (houseBedroomCount.toLowerCase().contains("1")) && (houseBedroomCount.toLowerCase().contains("2")) && (houseBedroomCount.toLowerCase().contains("3"))){
                        break;
                    }
                    if(fourbeds.isSelected() && (houseBedroomCount.toLowerCase().contains("1")) && (houseBedroomCount.toLowerCase().contains("2")) && (houseBedroomCount.toLowerCase().contains("3")) && (houseBedroomCount.toLowerCase().contains("4"))){
                        break;
                    }
                    if(!searchPrice.getText().equals("")){
                        price = Integer.parseInt(searchPrice.getText());
                    }
                    if((!housePrice.substring(1).equals("OA")) && (!housePrice.substring(1).equals("ffers Invited")) && (Integer.parseInt(housePrice.substring(1).replaceAll(",", "")) > price)){
                        break;
                    }
//

                    if((!houseDescription.toLowerCase().contains("lease")) && (!houseDescription.toLowerCase().contains("shared")) && (!housePrice.toLowerCase().contains("offers"))) {
                        addToMap( houseAddress,  houseBedroomCount,  housePhoto,  housePrice,  housePhoneNumber,  houseDescription,  finalLinkAddress, count++);
                    }
                }
                outputJSONFile();
            }
        } catch (IOException e) {
            outputJSONFile();
            System.out.println("Broken");
        }

    }

    public static void addToMap(String houseAddress,String  houseBedroomCount, String housePhoto, String housePrice, String housePhoneNumber, String houseDescription, String finalLinkAddress, int count){
        houseMap = new LinkedHashMap(4);
        houseMap.put("address", houseAddress);
        houseMap.put("photo", housePhoto);
        houseMap.put("price", housePrice);
        houseMap.put("description", houseDescription);
        houseMap.put("phoneNumber", housePhoneNumber);
        houseMap.put("bedrooms", houseBedroomCount);
        houseMap.put("linkAddress", finalLinkAddress);
        houseMap.put("id", count++);
        jsarr.add(houseMap);
    }


public static void outputJSONFile() {
        try {
        PrintWriter out = new PrintWriter("JSON_RIGHTMOVE.json");
        out.write(jsarr.toJSONString());
        out.flush();
        out.close();
        }catch(FileNotFoundException e){
        System.out.println("File not found.");
        }
        }

}
