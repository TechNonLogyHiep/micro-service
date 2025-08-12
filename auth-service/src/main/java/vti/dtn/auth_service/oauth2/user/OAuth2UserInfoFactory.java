package vti.dtn.auth_service.oauth2.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Map;

import static vti.dtn.auth_service.oauth2.common.OAuth2Constant.*;

@Slf4j
public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String,Object> attributes) {
        if(!StringUtils.hasText(registrationId)){
            log.error("registrationId is empty");
            throw new IllegalArgumentException("registrationId is empty");
        }
        if(registrationId.equalsIgnoreCase(GOOGLE)){
            return new GoogleOAuth2UserInfo(attributes);
        }
        else if(registrationId.equalsIgnoreCase(FACEBOOK)){
            return new FacebookOAuth2UserInfo(attributes);
        }
        else if(registrationId.equalsIgnoreCase(GITHUB)){
            return new GithubOAuth2UserInfo(attributes);
        }
        else{
            log.error("unknown registration id {}", registrationId);
            throw new IllegalArgumentException("unknown registration id " + registrationId);
        }
    }
}
