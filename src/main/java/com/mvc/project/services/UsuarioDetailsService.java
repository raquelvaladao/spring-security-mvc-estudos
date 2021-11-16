package com.mvc.project.services;


import com.mvc.project.enums.RoleType;
import com.mvc.project.models.Usuario;
import com.mvc.project.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final String USUARIO_NOT_FOUND = "Usuário de email %s não encontrado";
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario;
            try{
                usuario = usuarioRepository.findByUsername(email);
            } catch (Exception e){
                throw new UsernameNotFoundException(String.format(USUARIO_NOT_FOUND, email) + e.getMessage());
        }
        List<GrantedAuthority> authorities = getUserAuthority(Set.of(usuario.getRole()));


        return new User(usuario.getUsername(), usuario.getPassword(), authorities);
    }

    private List<GrantedAuthority> getUserAuthority(Set<RoleType> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (RoleType role : userRoles) {
            if(role.getName().equals("ROLE_USER")){
                roles.add(new SimpleGrantedAuthority(role.getName()));
            } else {
                roles.add(new SimpleGrantedAuthority("ROLE_USER"));
                roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }
/*    private UserDetails buildUserForAuthentication(Usuario user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getEnabled(), true, true, true, authorities);
    }*/

    public void salvarUsuario(Usuario usuario){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscaUsuario(String email){
        return usuarioRepository.getOptionalUsuarioByUsername(email);
    }

}
