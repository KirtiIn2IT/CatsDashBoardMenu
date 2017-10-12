package com.in2it.cats.business.pojo;

import java.util.List;

/**
 * Category/Item information class. A category will have one or many
 * sub-categories.
 * 
 * @author In2IT Technologies
 * @version 1.0
 * @since v1.0
 *
 */
public class CategoryBO {
	private String categoryCode;
	
	/**
	 * Category Name
	 */
	private String categoryName;

	/**
	 * Category Description
	 */
	private String categoryDescription;

	/**
	 * Sub-Categories
	 */
	private List<SubCategoryBO> subCategories;

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName
	 *            the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return the categoryDescription
	 */
	public String getCategoryDescription() {
		return categoryDescription;
	}

	/**
	 * @param categoryDescription
	 *            the categoryDescription to set
	 */
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	/**
	 * @return the subCategories
	 */
	public List<SubCategoryBO> getSubCategories() {
		return subCategories;
	}

	/**
	 * @param subCategories
	 *            the subCategories to set
	 */
	public void setSubCategories(List<SubCategoryBO> subCategories) {
		this.subCategories = subCategories;
	}

	/**
	 * @return the categoryCode
	 */
	public String getCategoryCode() {
		return categoryCode;
	}

	/**
	 * @param categoryCode the categoryCode to set
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
}
