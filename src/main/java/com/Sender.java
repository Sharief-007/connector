package com;

import org.apache.commons.fileupload.FileItem;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Sender {
    private String mailID = "";
    private String password = "";


    private List<FileItem> filesList = new ArrayList<FileItem>();
    public void addFileItem(FileItem fileitem)
    {
        this.filesList.add(fileitem);
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String subject="";
    private String text ="";
    public void setMailID(String mailID) {
        this.mailID = mailID;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    void sendTo(String recipient)
    {
        System.out.println("Process intiated");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");


        Session session = Session.getInstance(properties,new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return (new PasswordAuthentication(mailID, password));
            }
        });

        MimeMessage message = prepareMessage(session,mailID,recipient);
        System.out.println("message intiated");

        try {

            Transport.send(message);
            System.out.println("message  sent");
        } catch (Exception e) {
            System.out.println("erroe here"+"\n");
            e.printStackTrace();
        }
    }
    private MimeMessage prepareMessage(Session session, String mailID, String recipient) {
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(mailID));
            msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipient));
            msg.setSubject(subject);
            if (areFilesIncluded())
            {
                int no_of_files = filesList.size();
                Multipart multipart = new MimeMultipart();
                BodyPart part = new MimeBodyPart();
                multipart.addBodyPart(part);
                part.setText(text);
                MimeBodyPart parts[] = new MimeBodyPart[no_of_files];
                File f[] = new File[no_of_files];
                int i =0;
                for (FileItem item:filesList) {
                    f[i] = new File("C:/Users/Sharief/Documents/JavaMailFileUpload/"+item.getName());
                    System.out.println(f[i].getPath());
                    parts[i] = new MimeBodyPart();
                    item.write(f[i]);
                    DataSource source = new FileDataSource(f[i].getPath().replace('\\','/'));
                    System.out.println(f[i].getPath().replace('\\','/'));
                    parts[i].setDataHandler(new DataHandler(source));
                    parts[i].setFileName(f[i].getName());
                    multipart.addBodyPart(parts[i]);
                    i++;
                }
                msg.setContent(multipart);
            }
            else {
                msg.setText(text);
            }
            return msg;
        }catch (Exception e) {
            System.out.println("Error -2");
            e.printStackTrace();
        }
        return null;
    }
    private boolean areFilesIncluded()
    {
        if (this.filesList.size() ==0)
        {
            return false;
        }
        else {
            return true;
        }
    }
}