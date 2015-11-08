package com.tw;

import com.tw.api.exception.NotFoundException;
import com.tw.api.filter.OpenSessionInViewRequestFilter;
import com.tw.api.filter.OpenSessionInViewResponseFilter;
import com.tw.domain.AssetRepository;
import com.tw.domain.CategoryRepository;
import com.tw.domain.impl.AssetRepositoryImpl;
import com.tw.domain.impl.CategoryRepositoryImpl;
import com.tw.mapper.*;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionManager;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

public class Main {

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://0.0.0.0/").port(8080).build();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final HttpServer httpServer = startServer();

        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                httpServer.shutdownNow();
            }
        }
    }

    private static HttpServer startServer() {
        SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
        final SqlSessionManager sqlSessionManager = SqlSessionManager.newInstance(sqlSessionFactory);

        final BaseMapper baseMapper = sqlSessionManager.getMapper(BaseMapper.class);
        final CategoryMapper categoryMapper = sqlSessionManager.getMapper(CategoryMapper.class);
        final PolicyMapper policyMapper = sqlSessionManager.getMapper(PolicyMapper.class);
        final AssetMapper assetMapper = sqlSessionManager.getMapper(AssetMapper.class);

        final AssetRepository assetRepository = new AssetRepositoryImpl(assetMapper, baseMapper);
        final CategoryRepository categoryRepository = new CategoryRepositoryImpl(categoryMapper, policyMapper);


        final ResourceConfig config = new ResourceConfig()
                .packages("com.tw.api")
                .register(NotFoundException.class)
                .register(OpenSessionInViewRequestFilter.class)
                .register(OpenSessionInViewResponseFilter.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(assetRepository).to(AssetRepository.class);
                        bind(categoryRepository).to(CategoryRepository.class);
                        bind(sqlSessionManager).to(SqlSessionManager.class);
                    }
                });
        return GrizzlyHttpServerFactory.createHttpServer(getBaseURI(), config);
    }

}
