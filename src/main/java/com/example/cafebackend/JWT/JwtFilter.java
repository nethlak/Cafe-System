//package com.example.cafebackend.JWT;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import java.io.IOException;
//
//@Component
//public class JwtFilter implements Filter {
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        // Initialization code
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        // Filtering logic
//    }
//
//    @Override
//    public void destroy() {
//        // Cleanup code
//    }
//}

package com.example.cafebackend.JWT;

        import io.jsonwebtoken.Claims;
        import jakarta.servlet.Filter;
        import jakarta.servlet.FilterChain;
        import jakarta.servlet.http.HttpServletRequest;
        import jakarta.servlet.http.HttpServletResponse;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
        import org.springframework.security.core.context.SecurityContextHolder;
        import org.springframework.security.core.userdetails.UserDetails;
        import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
        import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
        import org.springframework.stereotype.Component;
        import org.springframework.web.filter.OncePerRequestFilter;

        import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter{

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomerUsersDetailsSevice service;

    Claims claims = null;
    private String userName = null;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {

//        if(httpServletRequest.getServletPath().matches(
//                "/user/login/user/forgotPassword|/user/signup"
//        )){
//            filterChain.doFilter(httpServletRequest,httpServletResponse);
//        }else{
//            String authorizationHeader = httpServletRequest.getHeader("Auhtorization");
//            String token = null;
//
//            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
//                token =authorizationHeader.substring(7);
//                userName = jwtUtil.extractUserName(token);
//                claims = jwtUtil.extractAllClaims(token);
//            }
//
//            if(userName != null && SecurityContextHolder.getContext().getAuthentication()==null){
//                UserDetails userDetails = service.loadUserByUsername(userName);
//                if(jwtUtil.validateToken(token, userDetails)){
//                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
//                            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//                    usernamePasswordAuthenticationToken.setDetails(
//                            new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
//                    );
//                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//                }
//            }
//            filterChain.doFilter(httpServletRequest,httpServletResponse);
//        }




        String authHeader = httpServletResponse.getHeader("Authorization");
        String token = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = service.loadUserByUsername(userName).getUsername();
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = service.loadUserByUsername(userName);
            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);




    }
    public boolean isAdmin(){
        return "admin".equalsIgnoreCase((String) claims.get("role"));
    }

    public boolean isUser(){
        return "user".equalsIgnoreCase((String) claims.get("role"));
    }

    public String getCurrentUser(){
        return userName;
    }
}