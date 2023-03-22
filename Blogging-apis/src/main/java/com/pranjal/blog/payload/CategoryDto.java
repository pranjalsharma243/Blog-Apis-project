package com.pranjal.blog.payload;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	private Integer categoryId;
	@NotEmpty
	@Size(min = 4,message="Minimum length has to be 4")
	private String categoryTitle;
	
	@NotEmpty
	@Size(min = 10,message="Minimum length has to be 10")
	private String categoryDescription;
	
	

}
