package com.pranjal.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pranjal.blog.entities.Category;
import com.pranjal.blog.exceptions.ResourceNotFoundException;
import com.pranjal.blog.payload.CategoryDto;
import com.pranjal.blog.repository.CategoryRepo;
import com.pranjal.blog.services.CategoryService;
@Service
public class CategoryServiceimpl implements CategoryService {
	@Autowired
	private CategoryRepo categoryRepo;
    @Autowired
	private ModelMapper modelMapper;
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		Category cat=dtoToCategory(categoryDto);
		Category addedcat=this.categoryRepo.save(cat);
		CategoryDto saved=this.CategoryToDto(addedcat);
		return saved;
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatedCategory=this.categoryRepo.save(cat);
		CategoryDto updated=this.CategoryToDto(updatedCategory);
		return updated;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));
		this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category id", categoryId));
		CategoryDto result=this.CategoryToDto(cat);
		return result;
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<Category> cats=this.categoryRepo.findAll();
		List<CategoryDto> catdtos=cats.stream().map(category->this.CategoryToDto(category)).collect(Collectors.toList());
		return catdtos;
	}
	
	//converting CategoryDto to category
	private Category dtoToCategory(CategoryDto categoryDto)
	{
		Category cat=this.modelMapper.map(categoryDto, Category.class);
		return cat;
	}
	//converting category to categoryDto
	private CategoryDto CategoryToDto(Category category)
	{
		CategoryDto catdto=this.modelMapper.map(category, CategoryDto.class);
		return catdto;
	}

}
