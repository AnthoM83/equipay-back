package com.proyecto.g2.equipay.security;

import com.proyecto.g2.equipay.models.Admin;
import com.proyecto.g2.equipay.models.Usuario;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService
        implements UserDetailsService {

    @Autowired
    IUserDetailsRepository userDetailsRepo;
    @Autowired
    IAdminDetailsRepository adminDetailsRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> userDetail = userDetailsRepo.findById(username);
        if (userDetail.isPresent()) {
            return userDetail.map(UserInfoDetails::new)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado " + username));
        } else {
            Optional<Admin> adminDetail = adminDetailsRepo.findById(username);
            return adminDetail.map(UserInfoDetails::new)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado " + username));
        }

    }
}
