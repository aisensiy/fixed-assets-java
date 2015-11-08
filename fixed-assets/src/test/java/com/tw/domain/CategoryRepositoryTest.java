package com.tw.domain;

import com.tw.domain.impl.CategoryRepositoryImpl;
import com.tw.mapper.CategoryMapper;
import com.tw.mapper.PolicyMapper;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

public class CategoryRepositoryTest extends RepositoryTestBase {

    private CategoryRepository categoryRepository;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        CategoryMapper categoryMapper = sqlSession.getMapper(CategoryMapper.class);
        PolicyMapper policyMapper = sqlSession.getMapper(PolicyMapper.class);
        categoryRepository = new CategoryRepositoryImpl(categoryMapper, policyMapper);
    }

    @Test
    public void should_create_category_with_policy_and_get_it_back() throws Exception {
        MultivaluedMap<String, String> map = new MultivaluedHashMap<>();
        map.putSingle("name", "cate name");
        map.putSingle("policyPercentage", "10");
        map.putSingle("policyFactor", "1");
        map.putSingle("policyTermLengthByMonth", "12");

        final Category category = categoryRepository.createCategory(map);
        assertThat(category.getId(), not(0));
        assertThat(category.getPolicy().getId(), not(0));
        assertThat(category.getPolicy().getFactor(), is(1));
    }

    @Test
    public void should_get_all_categories() throws Exception {
        MultivaluedMap<String, String> map = new MultivaluedHashMap<>();
        map.putSingle("name", "cate name");
        map.putSingle("policyPercentage", "10");
        map.putSingle("policyFactor", "1");
        map.putSingle("policyTermLengthByMonth", "12");

        categoryRepository.createCategory(map);
        categoryRepository.createCategory(map);
        List<Category> list = categoryRepository.getCategories();
        assertThat(list.size(), is(2));
    }
}