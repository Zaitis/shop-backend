package pl.zaitis.shop.common.mail;

public interface EmailSender {
    void send(String to,String subject, String msg);

}
