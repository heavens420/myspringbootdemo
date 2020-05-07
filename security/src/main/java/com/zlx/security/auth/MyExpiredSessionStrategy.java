package com.zlx.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyExpiredSessionStrategy implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException, ServletException {
        Map<String,Object> map = new HashMap<>();
        map.put("code",250);
        map.put("message","您已在其它地方登录，如非本人，那说明号被盗了");
        ObjectMapper mapper = new ObjectMapper();
        sessionInformationExpiredEvent.getResponse().setContentType("application/json;charset=utf-8");
        sessionInformationExpiredEvent.getResponse().getWriter().write(mapper.writeValueAsString(map));
    }
}
