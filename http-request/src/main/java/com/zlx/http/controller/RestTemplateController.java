package com.zlx.http.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/rest")
public class RestTemplateController {

    @Resource
    private RestTemplate restTemplate;

//    final String URL = "http://localhost:8082/cloud-manage/cmp-res-region/region?cloudId={1}";
    final String URL = "https://redisdatarecall.csdn.net/recommend/get_head_word?bid={1}";

    final String Cookie = "uuid_tt_dd=10_10321240420-1645262066585-809784; ssxmod_itna=euD=0KD5AIxIEmDzaKR2xxUxmqDIxDOlb/UnlUDBk7r4iNDnD8x7YDvmIwb5qHYErPi5HBQ+PoNffjCxRpEjDB3DEx0=5hAYxiiyDCeDIDWeDiDG4GmU4GtDpxG=Dj7I=lBAxYPGWQqDoDYRQ0DitD4qDB+E3DKqGgQMGrQd0f3l6KkuhDo0QDjMrD/8xrZ0je56WNfdito7IinDDHKO9xl0wOTihxBTP0DB6GxBjBzuUL8C9nUTt/mYm4Wr8oSO+P72hx3EDNCYq3Yid=RYyAZYqY4gqHji3LxD3C67+i4rD===; ssxmod_itna2=euD=0KD5AIxIEmDzaKR2xxUxmqDIxDOlb/UnlD8dZ2xGX2eGaBIPo1x8Oi9TzYhtz/DfdWXs8e/My4HekUmAR1YTlf3X+OpaA0TIeDLcOa+sht0gr+tWDqO242VzY/xH9bS+TCp9v3XMCrqVCx0s0uxqOkXPo4XK5iooEn05yTRc7lRx2CbBY3YstfDtDkrQQbKMD=vgFBvGRE0xGW5kU=vOSucCpav7vTcHpgY+gg7pGf+QDd9iz2vWpULZI/8ObPNjx6MTZoFci=rRapnjc8Owd5Z+bwM00kAZA5nw0yEgt9qHGtf86vDff4PY4QYkPKpW0ZGYaaoHlop7XgOYsBqtBen6DviXijt6G5unefY2GOA+ELOeACQ+x6w72brGRq33h7XR0iH=51x2rS3MrPMpGWDG2424Ci5ob=/h5GFbMAF8f6+a4SH4qrxGWsBGKI7R98+Sux4hdbAqWw2+FHFi4I5Nbdn7LqQkFu8Yjuxz0OTKzcMDD08Dij/gxeD+/9GvQDtlP4WhvCGHiDN0=G7HkOFiWD35xKvyu/KNBDynyiQGvDhu/r0pH0FD=zp/F4aMDxD=; UserName=heavens420; UserInfo=11adbbad14bd475ea6bf490df18009ec; UserToken=11adbbad14bd475ea6bf490df18009ec; UserNick=heavens420; AU=AEE; UN=heavens420; BT=1646790148936; p_uid=U010000; Hm_up_6bcd52f51e9b3dce32bec4a3997715ac={\"islogin\":{\"value\":\"1\",\"scope\":1},\"isonline\":{\"value\":\"1\",\"scope\":1},\"isvip\":{\"value\":\"0\",\"scope\":1},\"uid_\":{\"value\":\"heavens420\",\"scope\":1}}; Hm_ct_6bcd52f51e9b3dce32bec4a3997715ac=6525*1*10_10321240420-1645262066585-809784!5744*1*heavens420; c_dl_prid=1646746184866_251591; c_dl_rid=1646811357963_614364; c_dl_fref=https://www.baidu.com/link; c_dl_fpage=/download/qq_32682301/12280534; c_dl_um=-; dc_session_id=10_1646891138768.284754; c_first_ref=www.baidu.com; c_first_page=https://blog.csdn.net/qq_35794202/article/details/102988078; c_segment=8; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1646811363,1646812916,1646815773,1646891142; dc_sid=15fb4b7e3ad783b8a0fd98e91fc65bc2; firstDie=1; c_utm_relevant_index=3; c_pref=https://blog.csdn.net/qq_35794202/article/details/102988078; c_ref=https://blog.csdn.net/u011164906/article/details/79876894?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_baidulandingword~default-0.pc_relevant_antiscanv2&spm=1001.2101.3001.4242.1&utm_relevant_index=3; c_utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_baidulandingword~default-0.pc_relevant_aa; log_Id_view=509; log_Id_click=122; c_page_id=default; dc_tos=r8ilzw; log_Id_pv=129; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1646891997";
    final String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36";

    @GetMapping("/get")
    public String sendGetWithParam(String value) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("cookie", Cookie);
        httpHeaders.add("user-agent",userAgent);

        Map<String, String> map = new HashMap<>(4);
        map.put("1", value);
        HttpEntity<String> httpEntity = new HttpEntity(null, httpHeaders);

        // get 传参 参数放最后
        final ResponseEntity<String> entity = restTemplate.exchange(URL, HttpMethod.GET, httpEntity, String.class,map);

        log.info("response:{}", entity.getBody());
        return entity.toString();
    }

    @GetMapping("/post")
    public String sendPostWithParam(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("cookie", Cookie);
        httpHeaders.add("user-agent",userAgent);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);


        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("page","1");
        formData.add("size","3");
        formData.add("fold","unfold");

        HttpEntity<String> httpEntity = new HttpEntity(formData,httpHeaders);

        // post传参 参数放httpEntity里面
        final ResponseEntity<String> response = restTemplate.exchange("https://blog.csdn.net/phoenix/web/v1/comment/list/106762356", HttpMethod.POST, httpEntity, String.class);
        log.info("response:{}",response.getBody());

        return response.toString();
    }
}
