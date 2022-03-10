package com.zlx.http.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.awt.print.Book;
import java.util.HashMap;
import java.util.Map;


@RestController
@Slf4j
public class WebClientController {

    //    final String URL = "http://localhost:8082/cloud-manage/cmp-res-region/region?cloudId={1}";
//    final String URL = "http://www.baidu.com/s?ie=UTF-8&wd={wd}";
    final String URL = "https://redisdatarecall.csdn.net/recommend/get_head_word?bid={1}";
//    final String Cookie = "token=eyJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxIiwiYXVkIjoiMSIsInVuYW1lIjoic3VwZXJBZG1pbiIsInJuYW1lIjoi6LaF57qn566h55CG5ZGYIiwiaXNzIjoiY2xvdWQtbWFuYWdlIiwib2lkIjoiMCIsImlkIjoiMV8xNTAxMDk2Nzk1MDc5NzQ5NjM0IiwiZXhwIjoxNjQ2ODEwNzM5LCJpYXQiOjE2NDY3MjQzMzksInRpZCI6IjEiLCJqdGkiOiIxXzE1MDEwOTY3OTUwNzk3NDk2MzQifQ._5qZg38rXM06BLBsInFrc-x_I12Z-CweWaEnPGX2jnE";
    final String Cookie = "uuid_tt_dd=10_10321240420-1645262066585-809784; hide_login=1; firstDie=1; c_dl_fpage=/download/weixin_38625351/12744821; c_dl_um=distribute.pc_aggpage_search_result.none-task-download-2~aggregatepage~first_rank_ecpm_v1~rank_v31_ecpm-1-12744642.pc_agg_new_rank; c_dl_prid=1646745981812_962787; c_dl_rid=1646746184866_251591; c_dl_fref=https://blog.csdn.net/ka3p06/article/details/111935907; dc_session_id=10_1646790026683.992083; c_ref=https://www.baidu.com/link; c_first_ref=www.baidu.com; c_segment=8; dc_sid=76f83463e088c56f7a9c590da2aff861; unlogin_scroll_step=1646790037494; SESSION=dc036edb-37b7-40ef-8262-49a249c5b327; c_pref=https://www.baidu.com/link; acw_sc__v2=622805eff23512fc8e89e2164840559927d7866d; ssxmod_itna=euD=0KD5AIxIEmDzaKR2xxUxmqDIxDOlb/UnlUDBk7r4iNDnD8x7YDvmIwb5qHYErPi5HBQ+PoNffjCxRpEjDB3DEx0=5hAYxiiyDCeDIDWeDiDG4GmU4GtDpxG=Dj7I=lBAxYPGWQqDoDYRQ0DitD4qDB+E3DKqGgQMGrQd0f3l6KkuhDo0QDjMrD/8xrZ0je56WNfdito7IinDDHKO9xl0wOTihxBTP0DB6GxBjBzuUL8C9nUTt/mYm4Wr8oSO+P72hx3EDNCYq3Yid=RYyAZYqY4gqHji3LxD3C67+i4rD===; ssxmod_itna2=euD=0KD5AIxIEmDzaKR2xxUxmqDIxDOlb/UnlD8dZ2xGX2eGaBIPo1x8Oi9TzYhtz/DfdWXs8e/My4HekUmAR1YTlf3X+OpaA0TIeDLcOa+sht0gr+tWDqO242VzY/xH9bS+TCp9v3XMCrqVCx0s0uxqOkXPo4XK5iooEn05yTRc7lRx2CbBY3YstfDtDkrQQbKMD=vgFBvGRE0xGW5kU=vOSucCpav7vTcHpgY+gg7pGf+QDd9iz2vWpULZI/8ObPNjx6MTZoFci=rRapnjc8Owd5Z+bwM00kAZA5nw0yEgt9qHGtf86vDff4PY4QYkPKpW0ZGYaaoHlop7XgOYsBqtBen6DviXijt6G5unefY2GOA+ELOeACQ+x6w72brGRq33h7XR0iH=51x2rS3MrPMpGWDG2424Ci5ob=/h5GFbMAF8f6+a4SH4qrxGWsBGKI7R98+Sux4hdbAqWw2+FHFi4I5Nbdn7LqQkFu8Yjuxz0OTKzcMDD08Dij/gxeD+/9GvQDtlP4WhvCGHiDN0=G7HkOFiWD35xKvyu/KNBDynyiQGvDhu/r0pH0FD=zp/F4aMDxD=; UserName=heavens420; UserInfo=11adbbad14bd475ea6bf490df18009ec; UserToken=11adbbad14bd475ea6bf490df18009ec; UserNick=heavens420; AU=AEE; UN=heavens420; BT=1646790148936; p_uid=U010000; Hm_up_6bcd52f51e9b3dce32bec4a3997715ac={\"islogin\":{\"value\":\"1\",\"scope\":1},\"isonline\":{\"value\":\"1\",\"scope\":1},\"isvip\":{\"value\":\"0\",\"scope\":1},\"uid_\":{\"value\":\"heavens420\",\"scope\":1}}; Hm_ct_6bcd52f51e9b3dce32bec4a3997715ac=6525*1*10_10321240420-1645262066585-809784!5744*1*heavens420; log_Id_click=101; log_Id_view=395; c_first_page=https://blog.csdn.net/lizz861109/article/details/106762356; c_page_id=default; dc_tos=r8gfqc; log_Id_pv=107; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1646790030,1646790061,1646790153,1646790566; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1646790566";
    final String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36";

    @GetMapping("/get1")
    public Mono<String> sendGetWithParams(String value) {
        final Mono<String> bodyToMono = WebClient.create()
                .method(HttpMethod.GET)
                .uri(URL,value)     // 设置请求参数
//                .cookie("Cookie", Cookie)  // 设置cookie 直接设置 对认证无效
//                .header("Cookie",Cookie) // 设置认证信息，此处设置cookie对认证生效
                .header("user-agent",userAgent)
                .retrieve()
                .bodyToMono(String.class);
        log.info("result:{}",bodyToMono.block());
        return bodyToMono;
    }

    @GetMapping("/get2")
    public Mono<String> sendGetWithParams2(String value) {
        Mono<String> resp = WebClient.create()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("redisdatarecall.csdn.net")
                        .path("/recommend/get_head_word")
                        .queryParam("bid", value)
                        .queryParam("other", "test")
                        .build())
                .header("user-agent",userAgent)
                .retrieve()
                .bodyToMono(String.class);
        log.info("result:{}",resp.block());
        return resp;
    }

    @GetMapping("/post1")
    public Mono<String> sendPostWithParams() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("page","1");
        formData.add("size","3");
        formData.add("fold","unfold");
        Mono<String> resp = WebClient.create().post()
                .uri("https://blog.csdn.net/phoenix/web/v1/comment/list/106762356")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("user-agent",userAgent)
                .header("cookie",Cookie)
                .body(BodyInserters.fromFormData(formData))
                .retrieve().bodyToMono(String.class);
        return resp;
    }

    @GetMapping("/post2")
    public Mono<String> sendPostWithParams2(){
        // 传参可以是实体类对象 这里为了方便用了map
        Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("size", "3");
        map.put("fold", "unfold");

        Mono<String> resp = WebClient.create().post()
                .uri("https://blog.csdn.net/phoenix/web/v1/comment/list/106762356")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("user-agent",userAgent)
                .header("cookie",Cookie)
                .body(Mono.just(map),Book.class)
                .retrieve().bodyToMono(String.class);
        log.info("result:{}",resp.block());
        return resp;
    }
}
