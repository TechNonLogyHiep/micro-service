package vti.dtn.auth_service.oauth2.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import vti.dtn.auth_service.oauth2.repository.HttpCookieAuthorizationRequestRepository;
import vti.dtn.auth_service.oauth2.user.UserPrincipal;
import vti.dtn.auth_service.oauth2.util.CookieUtils;
import vti.dtn.auth_service.service.JwtService;
import vti.dtn.auth_service.service.UserService;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSucessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Value("${app.oauth2.authorizedRedirectUris}")
    private String redirectUri;

    private final JwtService jwtService;
    private final HttpCookieAuthorizationRequestRepository httpCookieAuthorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targeUrl = determineTargetUrl(request, response,authentication);

        if(response.isCommitted()) {
            return;
        }
        clearAuthenticationAttributes(request,response);
        getRedirectStrategy().sendRedirect(request, response, targeUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request,HttpServletResponse response,Authentication authentication){
        Optional<String> redirectUri = CookieUtils.getCookie(request,HttpCookieAuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);
        String targetUrl = redirectUri.orElse(this.redirectUri);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String token = jwtService.generateAccessToken(userPrincipal);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token",token)
                .build().toString();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request,HttpServletResponse response){
        super.clearAuthenticationAttributes(request);
        httpCookieAuthorizationRequestRepository.removeAuthorizationRequest(request,response);
    }
}
