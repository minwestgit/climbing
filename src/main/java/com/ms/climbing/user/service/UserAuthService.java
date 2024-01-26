package com.ms.climbing.user.service;

import com.ms.climbing.user.dto.UserLoginDto;
import com.ms.climbing.user.entity.User;
import com.ms.climbing.user.exception.UserErrorCode;
import com.ms.climbing.user.exception.UserException;
import com.ms.climbing.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserAuthService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserLoginDto loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = userRepository.findById(loginId)
                .orElseThrow(() -> new UserException(UserErrorCode.ID_NOT_FOUND));
        return UserLoginDto.from(user);
    }
}
