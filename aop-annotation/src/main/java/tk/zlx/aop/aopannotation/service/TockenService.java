package tk.zlx.aop.aopannotation.service;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TockenService {

    public String creteToken(){
        String uuid = UUID.randomUUID().toString();

        return uuid;
    }
}
