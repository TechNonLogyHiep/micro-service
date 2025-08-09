package vti.dtn.auth_service.oauth2.user;

import java.util.Map;

public class GithubOAuth2UserInfo extends OAuth2UserInfo {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String AVATAR_URL = "avatar_url";
    private static final String LOGIN = "login";
    protected GithubOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return attributes.get(ID).toString();
    }

    @Override
    public String getName() {
        return attributes.get(NAME) != null ? attributes.get(NAME).toString() : attributes.get(LOGIN).toString();
    }

    @Override
    public String getEmail() {
        return attributes.get(EMAIL).toString();
    }

    @Override
    public String getImageUrl() {
        return attributes.get(AVATAR_URL).toString();
    }
}
