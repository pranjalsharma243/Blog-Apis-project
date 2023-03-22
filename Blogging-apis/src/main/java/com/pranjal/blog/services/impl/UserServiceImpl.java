package com.pranjal.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pranjal.blog.entities.User;
import com.pranjal.blog.exceptions.ResourceNotFoundException;
import com.pranjal.blog.payload.UserDto;
import com.pranjal.blog.repository.UserRepo;
import com.pranjal.blog.services.UserService;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
	private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
	@Override
	public UserDto createUser(UserDto userDto) {
		User user=this.dtoToUser(userDto);
		User usersaved=this.userRepo.save(user);
		
		return this.userToUserDto(usersaved);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user=userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","id",userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User saveUpdatedUser=this.userRepo.save(user);
		UserDto userDto1=this.userToUserDto(saveUpdatedUser);
	    return userDto1;
		
		
		
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user=userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","id",userId));
		
		
		return this.userToUserDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		
		List<User> users=this.userRepo.findAll();
		List<UserDto> userDtos=users.stream().map(user->this.userToUserDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		this.userRepo.delete(user);

	}
	private User dtoToUser(UserDto UserDto)
	{
		User user=this.modelMapper.map(UserDto, User.class);
//		user.setId(UserDto.getId());
//		user.setName(UserDto.getName());
//		user.setEmail(UserDto.getEmail());
//		user.setPassword(UserDto.getPassword());
//		user.setAbout(UserDto.getAbout());
		return user;
		
		
	}
	private UserDto userToUserDto(User user)
	{
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		return userDto;
		
	}

}
