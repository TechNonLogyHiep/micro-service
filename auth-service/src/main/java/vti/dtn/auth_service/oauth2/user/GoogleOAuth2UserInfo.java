package vti.dtn.auth_service.oauth2.user;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

    private static final String SUB = "sub";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String PICTURE = "picture";

    protected GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return attributes.get(SUB).toString();
    }

    @Override
    public String getName() {
        return attributes.get(NAME).toString();
    }

    @Override
    public String getEmail() {
        return attributes.get(EMAIL).toString();
    }

    @Override
    public String getImageUrl() {
        return attributes.get(PICTURE).toString();
    }
}
