package com.tw.domain.impl;

import com.tw.domain.Category;
import com.tw.domain.CategoryRepository;
import com.tw.domain.Policy;
import com.tw.mapper.CategoryMapper;
import com.tw.mapper.PolicyMapper;

import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

import static java.util.Arrays.asList;

public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryMapper categoryMapper;
    private final PolicyMapper policyMapper;

    public CategoryRepositoryImpl(CategoryMapper categoryMapper, PolicyMapper policyMapper) {

        this.categoryMapper = categoryMapper;
        this.policyMapper = policyMapper;
    }

    @Override
    public Category createCategory(MultivaluedMap<String, String> map) {
        List<String> keys = asList("name", "policyFactor", "policyPercentage", "policyTermLengthByMonth");
        for (String key : keys) {
            if (!map.containsKey(key)) {
                return null;
            }
        }

        Category category = new Category(map.getFirst("name"));
        categoryMapper.save(category);
        Policy policy = new Policy(
                Integer.parseInt(map.getFirst("policyPercentage")),
                Integer.parseInt(map.getFirst("policyFactor")),
                Integer.parseInt(map.getFirst("policyTermLengthByMonth")));
        policyMapper.savePolicy(category.getId(), policy);
        return categoryMapper.getById(category.getId());
    }

    @Override
    public Category getCategoryById(int id) {
        return null;
    }

    @Override
    public List<Category> getCategories() {
        return null;
    }
}
