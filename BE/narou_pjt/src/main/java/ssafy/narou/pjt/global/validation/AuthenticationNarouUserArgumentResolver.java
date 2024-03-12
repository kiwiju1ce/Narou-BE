package ssafy.narou.pjt.global.validation;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import ssafy.narou.pjt.global.auth.oauthAuth.model.NarouUserDetails;

public class AuthenticationNarouUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(NarouUserId.class);
    }

    @Override
    public Long resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        NarouUserDetails user = (NarouUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null){
            throw new SecurityException("인증 정보가 없습니다.");
        }

        return user.getUserId();
    }
}
