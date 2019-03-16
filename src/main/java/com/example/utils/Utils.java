package com.example.utils;

import com.example.models.User;
import com.example.services.UserService;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.joda.time.LocalTime;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

/*import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;*/

public class Utils {

    UserService userService;

    public Utils(UserService userService) {
        this.userService = userService;
    }

    public static String randomToken(int length) {
        final String mCHAR = "qwertyuioplkjhgfdsazxcvbnmABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(mCHAR.length());
            char ch = mCHAR.charAt(number);
            builder.append(ch);
        }

        return builder.toString();
    }

    public static String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "jpg";
    }

    public static File compress(File file, String extension, double fileSize) throws IOException {

        BufferedImage image = ImageIO.read(file);

        File compressedImageFile = new File("compress.jpg");
        OutputStream os = new FileOutputStream(compressedImageFile);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(extension);
        ImageWriter writer = writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        float quality = (float) (1f / fileSize);
        quality = quality>1?1:quality;
        param.setCompressionQuality(quality);
        writer.write(null, new IIOImage(image, null, null), param);

        os.close();
        ios.close();
        writer.dispose();

        File outputfile = new File("image.jpg");
        ImageIO.write(image, "jpg", outputfile);

        return outputfile;
    }

    public static double getFileSizeMegaBytes(File file) {
        return (double) file.length() / (1024 * 1024);
    }

    public static void putImg(String path, String photoToken) throws StorageException, URISyntaxException, IOException, InvalidKeyException {
        CloudStorageAccount account = CloudStorageAccount.parse("DefaultEndpointsProtocol=https;AccountName=excursium;AccountKey=fbMSD2cjYX08BJeKQvNM4Wk87I7fGWJShZvdtR3BdwvhXKUFuYv//qtJs9eAKmESG4Ib7CAHDJlgOIxSw5wwfg==;EndpointSuffix=core.windows.net");
        CloudBlobClient client = account.createCloudBlobClient();
        CloudBlobContainer container = client.getContainerReference("img");
        CloudBlockBlob blob1 = container.getBlockBlobReference(photoToken);
        blob1.uploadFromFile(path);
    }

    public User getUser(Principal principal){
        if (principal!=null) {
            User user = null;
            String loginOrEmail = principal.getName();
            if (!loginOrEmail.equals("")) {
                user = userService.getByEmail(loginOrEmail);
            }
            return user;
        }
        else return null;
    }

    public boolean isAdmin(Principal principal) {
        User nowUser = getUser(principal);
        return nowUser != null && nowUser.getType() == Consts.USER_ADMIN;
    }

    public String getTime(){
        String time = new LocalTime().toDateTimeToday().toString().replace('T', ' ');
        time = time.substring(0, time.indexOf('.'));
        return time;
    }

    public static File streamToFile(String fileExtension, InputStream in) {
        try {
            File tempFile = File.createTempFile(System.getProperty("catalina.home") + File.separator + "tmpFiles" + randomToken(10), fileExtension);
            tempFile.deleteOnExit();
            FileOutputStream out = new FileOutputStream(tempFile);
            IOUtils.copy(in, out);
            return tempFile;
        } catch (IOException exception){
            exception.printStackTrace();
        }
        return null;
    }

    public String getDateFormat(Date date){
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss a zzz");
        return formatForDateNow.format(date);
    }

}
