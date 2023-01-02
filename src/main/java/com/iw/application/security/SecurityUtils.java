package com.iw.application.security;

//public final class SecurityUtils {
//
//    private SecurityUtils() {
//        // Util methods only
//    }
//
//// TODO to be removed (contained in superclass of SecurityConfig)
////
////    static boolean isFrameworkInternalRequest(HttpServletRequest request) {
////        final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
////        return parameterValue != null
////            && Stream.of(RequestType.values())
////            .anyMatch(r -> r.getIdentifier().equals(parameterValue));
////    }
//
//    static boolean isUserLoggedIn() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return authentication != null
//            && !(authentication instanceof AnonymousAuthenticationToken)
//            && authentication.isAuthenticated();
//    }
//}