package com.example.sharingapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

/**
 * Item class
 */
public class Item {

    private String title;
    private String maker;
    private String description;
    private Dimensions dimensions;
    private String status;
    private Contact borrower;
    protected transient Bitmap image;
    protected String image_base64;
    private String id;

    public Item(String title, String maker, String description, Bitmap image,
                String id ) {
        this.title = title;
        this.maker = maker;
        this.description = description;
        this.dimensions = null;
        this.status = "Available";
        this.borrower = null;
        addImage(image);

        if (id == null){
            setId();
        } else {
            updateId(id);
        }
    }

    public String getId(){
        return this.id;
    }

    public void setId() {
        this.id = UUID.randomUUID().toString();
    }

    public void updateId(String id){
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getMaker() {
        return maker;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDimensions(String length, String width, String height) {
        this.dimensions = new Dimensions(length, width, height);
    }

    public String getLength(){
        return dimensions.getLength();
    }

    public String getWidth(){
        return dimensions.getWidth();
    }

    public String getHeight(){
        return dimensions.getHeight();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setBorrower(Contact borrower) {
        this.borrower = borrower;
    }

    public Contact getBorrower() {
        return borrower;
    }

    public void addImage(Bitmap new_image){
        if (new_image != null) {
            image = new_image;
            ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
            new_image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);

            byte[] b = byteArrayBitmapStream.toByteArray();
            image_base64 = Base64.encodeToString(b, Base64.DEFAULT);
        }
    }

    public Bitmap getImage(){
        if (image == null && image_base64 != null) {
            byte[] decodeString = Base64.decode(image_base64, Base64.DEFAULT);
            image = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
        }
        return image;
    }
}
