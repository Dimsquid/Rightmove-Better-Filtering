package sample;

import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Houses {

    private Long idColumn;
    private String bedrooms;
    private ImageView photoColumn;
    private String locationColumn;
    private String priceColumn;
    private String descColumn;
    private String numberColumn;
    private Hyperlink linkColumn;

    public Houses () {

    }

    public Long getIdColumn() {
        return idColumn;
    }

    public void setIdColumn(Long idColumn) {
        this.idColumn = idColumn;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public ImageView getPhotoColumn() {
        return photoColumn;
    }

    public void setPhotoColumn(ImageView photoColumn) {
        this.photoColumn = photoColumn;
    }

    public String getLocationColumn() {
        return locationColumn;
    }

    public void setLocationColumn(String locationColumn) {
        this.locationColumn = locationColumn;
    }

    public String getPriceColumn() {
        return priceColumn;
    }

    public void setPriceColumn(String priceColumn) {
        this.priceColumn = priceColumn;
    }

    public String getDescColumn() {
        return descColumn;
    }

    public void setDescColumn(String descColumn) {
        this.descColumn = descColumn;
    }

    public String getNumberColumn() {
        return numberColumn;
    }

    public void setNumberColumn(String numberColumn) {
        this.numberColumn = numberColumn;
    }

    public Hyperlink getLinkColumn() {
        return linkColumn;
    }

    public void setLinkColumn(Hyperlink linkColumn) {
        this.linkColumn = linkColumn;
    }











}
