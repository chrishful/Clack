package data;
import data.ClackData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * A Class, inheriting from ClackData, that represents an image.
 *
 * @author: Chris Hickman
 */
public class ImageClackData extends ClackData  {
    private String imgPath;
    transient BufferedImage imgContent;

    /**
     * A constructor for ImageClackData that initializes the child and parents
     * user name, image file path, and type with user given values. Initializes image contents to null.
     *
     * @param userName String representing the users name.
     * @param imgPath String representing the path of the image.
     * @param type Integer representing the type of file.
     */
    public ImageClackData(String userName, String imgPath, int type){
        super(userName, type);
        this.imgPath = imgPath;
        this.imgContent = null;
        }

    /**
     * Constructor for ImageClackData that takes no arguments and initializes file name and user name to "anon"
     * and the type to 1. Calls ClackData's constructor.
     */
    public ImageClackData(){
        this("anon", "anon", 1);
        //calls super through above constructor
    }

    /**
     * Mutator for ImageClackData's file name.
     *
     * @param imgPath String representing the file name.
     */
    public void setImgPath(String imgPath){
        this.imgPath = imgPath;
    }

    /**
     * Accessor for ImageClackData's file name.
     *
     * @return String representing the file name.
     */
    public String getImgPath(){
        return this.imgPath;
    }

    /**
     * gets the image in the form of a string
     * @return a string representing the image
     */
    @Override
    public String getData() {
        return this.imgContent.toString();
    }

    /**
     * gets the image stored
     * @return BufferedImage
     */
    public BufferedImage getImage() {
        return this.imgContent;
    }

    /**
     * gets the image in the form of a string
     * @param key Key used to decrypt string of object
     * @return
     */
    @Override
    public String getData(String key) {
        return decrypt(this.imgContent.toString(), key);
    }

    /**
     * Accessor for ImageClackData's data
     *
     * @return BufferedImage representing the object's data.
     */
    public BufferedImage getImgContent(){ return this.imgContent;}

    /**
     * Reads in an image.
     */
    public void readImg() throws IOException, FileNotFoundException {
        try{
            this.imgContent = ImageIO.read(new File(this.imgPath));
        } catch(FileNotFoundException fnfe){throw new FileNotFoundException("File: " + this.imgPath +  " not found");
        } catch(IOException ioe) {System.err.println("Error in opening, writing to, or closing file: " + this.imgPath);
        } catch(NullPointerException npe){throw new IOException("null pointer");
        }
    }

    /**
     * writes an image object as bytes
     * @param out
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        if(this.imgPath.endsWith(".png"))
            ImageIO.write(this.imgContent, "png", out);
        else ImageIO.write(this.imgContent, "jpg", out);
    }

    /**
     * reads in an image object as bytes
     * @param in
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.imgContent = ImageIO.read(in);
    }

    /**
     * Generates a hashcode for an ImageClackData object
     *
     * @return A unique integer representing the object's hashcode.
     */
    public int hashCode(){
        int dummy = 13;
        dummy = 17*dummy + imgPath.hashCode();
        if(this.getData() != null)
            dummy = 17*dummy + this.getData().hashCode();
        else
            dummy = 17*dummy + 31;
        dummy = 17*dummy + this.getUserName().hashCode();
        dummy = 17*dummy + this.getType();

        return dummy;
    }

    /**
     * Checks to see if two ImageClackData objects are identical to each other.
     *
     * @param other ImageClackData being compared to original.
     *
     * @return A boolean representing whether the two ImageClackData objects are equal.
     */
    public boolean equals(Object other){
        if(other instanceof ImageClackData) {
            ImageClackData f2 = (ImageClackData) other;
            return this.hashCode() == f2.hashCode();
        }
        else if (other == null) return false;
        else return false;
    }

    /**
     * Converts a ImageClackData object to a string representing the details of the object.
     *
     * @return A string representing the details of a ImageClackData object.
     */
    public String toString(){
        return  "image path: " + this.imgPath  +
                ", image content: " + this.imgContent.toString()  +
                ", user name: " + this.getUserName()  +
                ", type: " + this.getType()  +
                ", date: " + this.getDate();
    }
}
