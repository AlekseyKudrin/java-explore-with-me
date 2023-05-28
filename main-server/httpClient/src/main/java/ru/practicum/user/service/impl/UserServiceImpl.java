package ru.practicum.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventFullDto;
import ru.practicum.event.model.NewEventDto;
import ru.practicum.event.service.impl.EventServiceImpl;
import ru.practicum.exceptionHandler.exception.ValueNotFoundDbException;
import ru.practicum.reqest.model.ParticipationRequestDto;
import ru.practicum.reqest.service.RequestService;
import ru.practicum.reqest.service.impl.RequestServiceImpl;
import ru.practicum.user.dao.UserRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.model.UserDto;
import ru.practicum.user.model.UserMapper;
import ru.practicum.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final EventServiceImpl eventService;

    private final UserRepository userRepository;

    private final RequestServiceImpl requestService;

    @Override
    public UserDto creteUser(UserDto userDto) {
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
        log.info("Users search completed");
        return userDtoList;
    }

    @Override
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
        log.info("User deleted successfully");
    }

    @Override
    public EventFullDto createEventUser(Integer userId, NewEventDto newEventDto) {
        User user = findUserById(userId);
        return eventService.createEvent(user, newEventDto);
    }

    @Override
    public ParticipationRequestDto createRequestParticipate(Integer userId, Integer eventId) {
        return requestService.createRequest(userId, eventId);
    }

    public User findUserById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ValueNotFoundDbException("User not found"));
    }
}
