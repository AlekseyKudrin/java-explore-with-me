package ru.practicum.admin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.user.dao.UserRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.model.UserDto;
import ru.practicum.user.model.UserMapper;
import ru.practicum.admin.service.AdminService;
import ru.practicum.category.dao.CategoryRepository;
import ru.practicum.category.model.Category;
import ru.practicum.category.model.CategoryDto;
import ru.practicum.category.model.CategoryMapper;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userRepository.save(UserMapper.toUser(userDto));
        log.info("User successfully created");
        return UserMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size) {
        List<UserDto> userDtoList;
        if (ids == null) {
            int page = from / size;
            PageRequest pageRequest = PageRequest.of(page, size);
            userDtoList = userRepository.findAll(pageRequest).stream().map(UserMapper::toUserDto).collect(Collectors.toList());
        } else {
            userDtoList = userRepository.findByIdIn(ids).stream().map(UserMapper::toUserDto).collect(Collectors.toList());
        }
        log.info("User search completed");
        return userDtoList;
    }

    @Override
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
        log.info("User deleted successfully");
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.save(CategoryMapper.toCategory(categoryDto));
        log.info("Category successfully created");
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public void deleteCategory(Integer catId) {
        categoryRepository.deleteById(catId);
        log.info("Category deleted successfully");
    }

    @Override
    public CategoryDto patchCategory(Integer catId, CategoryDto categoryDto) {
        categoryDto.setId(catId);
        Category category = categoryRepository.save(CategoryMapper.toCategory(categoryDto));
        log.info("Category successfully change");
        return CategoryMapper.toCategoryDto(category);
    }
}
