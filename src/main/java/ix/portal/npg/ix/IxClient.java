package ix.portal.npg.ix;

import ix.portal.npg.service.SettingQueryService;
import ix.portal.npg.service.dto.SettingDTO;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IxClient {

    private static final Log logger = LogFactory.getLog(IxClient.class);

    @Value("${ix.dispatcher.server.url}")
    private String dispatcherUrl;

    @Value("${ix.reload.cache.url}")
    private String reloadCacheUrl;

    private final SettingQueryService settingQueryService;

    public IxClient(SettingQueryService settingQueryService) {
        this.settingQueryService = settingQueryService;
    }

    /**
     * Input arg[0]= serviceName
     * Input arg[1]=  actionName
     * Input arg[2]=    senderId
     * Input arg[3]=  receiverId
     * Input arg[4]=     version
     * */
    /*private <T> T sendMessage(final Object payload, final String... args) throws IxssException {
        SettingDTO setting = settingQueryService.findByKey("ix.dispatcher.url").orElse(null);
        if (setting != null) {
            dispatcherUrl = setting.getValue();
        }

        String serviceName = args[0] != null ? args[0] : "";
        String actionName = args[1] != null ? args[1] : "";
        String senderId = args[2] != null ? args[2] : "";
        String receiverId = args[3] != null ? args[3] : "";
        String version = args[4] != null ? args[4] : "";

        *//*JniBPJWSSec jniBPJWSSec = new JniBPJWSSec();
        String plainId = new SimpleDateFormat("yyyyMMddHHmmssS").format(new java.util.Date());
        String encryptId = jniBPJWSSec.passGen2(plainId.toString(), senderId);*//*
        String plainId = "dummy";
        String encryptId = "dummy";
        DispatcherRequest request = new DispatcherRequest();
        request.setPlainId(plainId);
        request.setEncryptedId(encryptId);
        request.setSenderId(senderId);
        request.setReceiverId(receiverId);
        request.setVersion(version);
        request.setAction(actionName);
        request.setService(serviceName);
        request.setPayload(payload);
        long startDate = new java.util.Date().getTime();
        DispatcherResponse response1 = DispatcherClient.send(request, dispatcherUrl);
        logger.info("response DispatcherResponse = " + JsonCodec.toString(response1));
        float secondsBetween = (float) ((new java.util.Date().getTime()) - startDate) / 1000;
        logger.info("------- response get in " + secondsBetween + " Seconds ");
        if (PayloadType.EXCEPTION.equals(response1.getType())) {
            ExceptionPayload exceptionPayload = response1.getPayload();
            throw new IxssException(exceptionPayload.getExceptionMessage(), exceptionPayload.getExceptionCode());
        }
        return response1.getPayload();
    }*/

    public LinkedHashMap sendReloadCacheMessage(final String cacheName) {
        SettingDTO setting = settingQueryService.findByKey("ix.reload.cache.url").orElse(null);
        if (setting != null) {
            reloadCacheUrl = setting.getValue();
        }
        System.out.println("cacheName = " + cacheName);
        System.out.println("reloadCacheUrl = " + reloadCacheUrl);

        long startDate = new java.util.Date().getTime();
        Map<String, Object> request = new HashMap<>();
        request.put("cacheName", cacheName);
        LinkedHashMap send = restTemplate().postForObject(reloadCacheUrl, request, LinkedHashMap.class);
        float secondsBetween = (float) ((new java.util.Date().getTime()) - startDate) / 1000;
        logger.info("------- response get in " + secondsBetween + " Seconds ");
        return send;
    }

    public static RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(1000);
        factory.setReadTimeout(3000);
        return new RestTemplate(factory);
    }
}


