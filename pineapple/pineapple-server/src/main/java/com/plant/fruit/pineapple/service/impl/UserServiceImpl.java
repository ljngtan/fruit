package com.plant.fruit.pineapple.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.util.StrUtil;
import com.plant.fruit.pineapple.dto.UserDto;
import com.plant.fruit.pineapple.entity.User;
import com.plant.fruit.pineapple.entity.User_;
import com.plant.fruit.pineapple.mapper.UserMapper;
import com.plant.fruit.pineapple.repository.UserRepository;
import com.plant.fruit.pineapple.service.UserService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.EscapeCharacter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void create(UserDto userDto) {
        User user = UserMapper.INSTANCE.dtoToEntity(userDto);
        String password = user.getPassword();
        String md5Pwd = SaSecureUtil.md5(password);
        user.setPassword(md5Pwd);
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void update(UserDto userDto) {
        Long id = userDto.getId();
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return;
        }
        User user = optionalUser.get();
        String password = userDto.getPassword();
        if (!StrUtil.isEmpty(password)) {
            String md5Pwd = SaSecureUtil.md5(password);
            user.setPassword(md5Pwd);
        }
        String name = userDto.getName();
        if (!StrUtil.isEmpty(name)) {
            user.setName(name);
        }
        String mobile = userDto.getMobile();
        if (!StrUtil.isEmpty(mobile)) {
            user.setMobile(mobile);
        }
        String email = userDto.getEmail();
        if (!StrUtil.isEmpty(email)) {
            user.setEmail(email);
        }
        userRepository.save(user);
    }

    @Override
    public void delete(Long[] ids) {
        userRepository.deleteAllById(Arrays.asList(ids));
    }

    @Override
    public Page<UserDto> page(UserDto userDto, PageRequest pageRequest) {
        Page<User> page = userRepository.findAll(new UserSpecification(userDto), pageRequest);
        return null;
    }

    @Override
    public UserDto getByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return UserMapper.INSTANCE.entityToDto(user);
    }

    @RequiredArgsConstructor
    static class UserSpecification implements Specification<User> {

        private final EscapeCharacter escapeCharacter = EscapeCharacter.DEFAULT;

        private final UserDto userDto;

        @Override
        public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();
            if (!StrUtil.isEmpty(userDto.getUsername())) {
                Predicate predicate = criteriaBuilder.like(root.get(User_.name),
                        "%" + escapeCharacter.escape(userDto.getUsername()) + "%",
                        escapeCharacter.getEscapeCharacter());
                predicates.add(predicate);
            }

            return query.getRestriction();
        }

    }

}
