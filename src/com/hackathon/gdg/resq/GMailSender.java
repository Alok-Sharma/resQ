package com.hackathon.gdg.resq;

import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import android.util.Log;

public class GMailSender extends javax.mail.Authenticator{
    private String mailhost = "smtp.gmail.com";   
    private String user;   
    private String password;   
    private Session session;   

    static {   
        Security.addProvider(new com.hackathon.gdg.resq.JSSEProvider());   
    }  

    public GMailSender(String user, String password) {   
        this.user = user;
        this.password = password;

        //set mail properties....
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");   
        props.setProperty("mail.host", mailhost);   
        props.put("mail.smtp.auth", "true");   
        props.put("mail.smtp.port", "465");   
        props.put("mail.smtp.socketFactory.port", "465");   
        props.put("mail.smtp.socketFactory.class",   
                "javax.net.ssl.SSLSocketFactory");   
        props.put("mail.smtp.socketFactory.fallback", "false");   
        props.setProperty("mail.smtp.quitwait", "false");   

        session = Session.getDefaultInstance(props, this);   
    }   

    protected PasswordAuthentication getPasswordAuthentication() {   
        return new PasswordAuthentication(user, password);   
    }   

    public synchronized void sendMail(String subject, String body, String sender, String recipients) throws Exception {   
        
    	try{
    		
    		//path to the attachment.. CHANGE THIS TO POINT TO THE VCF
    		String filename="/mnt/sdcard/Conquest.vcf";
    		
    		MimeMessage message = new MimeMessage(session);   
            //DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));   
            message.setFrom(new InternetAddress(sender));
            message.setSubject(subject);   
            message.setSentDate(new Date());
            //message.setDataHandler(handler);   

            //getting the message
            MimeBodyPart messagePart = new MimeBodyPart();
            messagePart.setText(body);

            //getting the attachment
            MimeBodyPart attachmentPart = new MimeBodyPart();
            Log.d("before file attach","before file attach");
            FileDataSource fileDataSource = new FileDataSource(filename) {
                @Override
                public String getContentType() {
                    return "application/octet-stream";
                }
            };
            attachmentPart.setDataHandler(new DataHandler(fileDataSource));
            attachmentPart.setFileName(fileDataSource.getName());
            Log.d("after file attach","after file attach");
            
            //merging the above parts together
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messagePart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);
            
            Log.d("after merging message","after merging message");

            
            if (recipients.indexOf(',') > 0) {  
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));   
            }
            else  {
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
            }
            Transport.send(message);
            Log.d("alok","final mail success");
            }catch(Exception e){

            }
    }   
}
