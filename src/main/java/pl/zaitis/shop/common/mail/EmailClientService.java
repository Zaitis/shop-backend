package pl.zaitis.shop.common.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailClientService {

//    @Value("${app.email.sender}")
    private String isFakeProp = "fakeEmailService";
    private final Map<String,EmailSender> senderMap;



    public EmailSender getUInstance(){
        if(isFakeProp.equals("fakeEmailService")){
            return senderMap.get("fakeEmailService");
        }
        return senderMap.get("emailSimpleService");
    }

    public EmailSender getUInstance(String beanName){
        if(isFakeProp.equals("fakeEmailService")){
            return senderMap.get("fakeEmailService");
        }
        return senderMap.get(beanName);
    }
}
