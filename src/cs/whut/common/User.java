package cs.whut.common;

import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Enumeration;


public class User {
    private String name;
    private String password;
    private String role;

    String uploadPath = ".\\uploadfile\\";
    String downloadPath = ".\\downloadfile\\";

    User(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    private boolean fileIO(String ID, String downloadPath) throws SQLException {
        try {
            byte[] buffer = new byte[1024];
            Doc doc = DataProcessing.searchDoc(ID);

            if (doc == null) {
                return false;
            }
            File inFile = new File(uploadPath + doc.getFilename());
            File outFile = new File(downloadPath + doc.getFilename());
            if (!outFile.createNewFile()) {
                System.out.println(new Timestamp(System.currentTimeMillis()).toString()+"\t\t"+ downloadPath + doc.getFilename() + " has already existed.");
                return false;
            }

            BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(inFile));
            BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(outFile));


            int byteRead;
            while ((byteRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, byteRead);
            }

            inStream.close();
            outStream.close();
            System.out.println(new Timestamp(System.currentTimeMillis()).toString()+"\t\t"+ downloadPath +doc.getFilename()+" download successfully.");
            return true;
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }

        return false;
    }

    public boolean downloadFile(String ID, String path) throws SQLException {
        return fileIO(ID, path);
    }

    public boolean uploadFile(String filePath, String fileNumber, String fileDescription) {
        try {
            if (DataProcessing.searchDoc(fileNumber) != null) {
                System.out.println(new Timestamp(System.currentTimeMillis()).toString()+"\t\tDocument ["+fileNumber+"] has existed.");
                return false;
            }

            File inFile = new File(filePath);
            File outFile = new File(uploadPath + inFile.getName());

            if (!outFile.createNewFile()) {
                System.out.println(new Timestamp(System.currentTimeMillis()).toString()+"\t\t"+ uploadPath +inFile.getName()+" has existed.");
                return false;
            }

            BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(inFile));
            BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(outFile));

            byte[] buffer = new byte[1024];
            int byteRead;
            while ((byteRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, byteRead);
            }

            inStream.close();
            outStream.close();

            if (DataProcessing.insertDoc(fileNumber, filePath, new Timestamp(System.currentTimeMillis()),
                    fileDescription, inFile.getName())) {
                System.out.println(new Timestamp(System.currentTimeMillis()).toString()+"\t\t"+ uploadPath +inFile.getName()+" upload successfully.");
            } else {
                System.out.println(new Timestamp(System.currentTimeMillis()).toString()+"\tFile ID [" + fileNumber + "] has existed.");
            }
        } catch (SQLException | IOException e) {
            System.out.println(new Timestamp(System.currentTimeMillis()).toString()+"\t\t"+e.getLocalizedMessage());
        }
        return true;
    }

    public void exitSystem() {
        System.out.println(new Timestamp(System.currentTimeMillis()).toString()+"\t\t"+"System exits.");
        System.exit(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

}
