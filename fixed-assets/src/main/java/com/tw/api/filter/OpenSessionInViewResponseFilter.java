package com.tw.api.filter;

import org.apache.ibatis.session.SqlSessionManager;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class OpenSessionInViewResponseFilter implements ContainerResponseFilter {
    @Inject
    private SqlSessionManager sqlSessionManager;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if (sqlSessionManager.isManagedSessionStarted()) {
            try {
                if (responseContext.getStatus() < Response.Status.BAD_REQUEST.getStatusCode()) {
                    sqlSessionManager.commit(true);
                } else {
                    Boolean skipRollback = (Boolean) requestContext.getProperty("skipRollback");
                    if (skipRollback != null && skipRollback) {
                        sqlSessionManager.commit(true);
                    } else {
                        sqlSessionManager.rollback(true);
                    }
                }
            } finally {
                sqlSessionManager.close();
            }
        }
    }
}

