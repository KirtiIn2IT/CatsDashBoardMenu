package com.in2it.cats.dao.pojo;

import java.util.List;

public class CategoryEntity {
	private String categoryTypeCode;
	
	private String categoryTypeDescription;
	
	private String categoryTypeName;
	
	private boolean isActive;
	
	private List<SubCategoryEntity> subCategories;

	/**
	 * @return the categoryTypeCode
	 */
	public String getCategoryTypeCode() {
		return categoryTypeCode;
	}

	/**
	 * @param categoryTypeCode the categoryTypeCode to set
	 */
	public void setCategoryTypeCode(String categoryTypeCode) {
		this.categoryTypeCode = categoryTypeCode;
	}

	/**
	 * @return the categoryTypeDescription
	 */
	public String getCategoryTypeDescription() {
		return categoryTypeDescription;
	}

	/**
	 * @param categoryTypeDescription the categoryTypeDescription to set
	 */
	public void setCategoryTypeDescription(String categoryTypeDescription) {
		this.categoryTypeDescription = categoryTypeDescription;
	}

	/**
	 * @return the categoryTypeName
	 */
	public String getCategoryTypeName() {
		return categoryTypeName;
	}

	/**
	 * @param categoryTypeName the categoryTypeName to set
	 */
	public void setCategoryTypeName(String categoryTypeName) {
		this.categoryTypeName = categoryTypeName;
	}

	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the subCategories
	 */
	public List<SubCategoryEntity> getSubCategories() {
		return subCategories;
	}

	/**
	 * @param subCategories the subCategories to set
	 */
	public void setSubCategories(List<SubCategoryEntity> subCategories) {
		this.subCategories = subCategories;
	}	
}
