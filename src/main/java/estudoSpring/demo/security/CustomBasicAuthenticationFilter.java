package estudoSpring.demo.security;

import estudoSpring.demo.Model.User;
import estudoSpring.demo.Repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;


@Component
@RequiredArgsConstructor
public class CustomBasicAuthenticationFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "Basic ";

    private final UserRepository userRepository;

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(isBasicAuthentication(request)){
            String[] credentials = decodeBase64(getHeader(request).replace(BASIC, ""))
                    .split(":");

            String username = credentials[0];
            String password = credentials[1];


            User user = userRepository.findByUsernameFetchRoles(username);

            if(user == null ){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Usuario não existe!");
                return; // Encerra o filtro se o usuário não existir
            }

            boolean validPassword = checkPassword(user.getPassword(), password);
            boolean validToken = validateToken(user.getToken());

            if(!validPassword || !validToken){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Credenciais invalidas!");
                return; // Encerra o filtro se as credenciais forem inválidas
            }

            setAuthentication(user);


            setAuthentication(user);

        }

        filterChain.doFilter(request, response);
    }

    private boolean validateToken(String token) {
        // Verifica se o token é nulo ou vazio
        return token != null && !token.isEmpty();

        // Aqui você pode implementar a lógica para verificar se o token é válido
        // Isso pode envolver consultas ao banco de dados para verificar se o token existe e se está associado ao usuário correto,
        // ou você pode ter uma lógica de validação específica, como verificar a assinatura do token se estiver usando JWT.

        // Neste exemplo simplificado, apenas retornamos true se o token não for nulo ou vazio
    }


    private void setAuthentication(User user) {
        UsernamePasswordAuthenticationToken authentication = createAuthenticationToken(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private UsernamePasswordAuthenticationToken createAuthenticationToken(User user) {
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
    }

    private boolean checkPassword(String userPassword, String loginPassword) {
        return passwordEncoder().matches(loginPassword, userPassword);
    }

    private String decodeBase64(String base64) {
        byte[] decotedBytes = Base64.getDecoder().decode(base64);
        return new String(decotedBytes);
    }

    private boolean isBasicAuthentication(HttpServletRequest request) {
        String header = getHeader(request);
        return header != null && header.startsWith(BASIC);
    }

    private String getHeader(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION);
    }
}
