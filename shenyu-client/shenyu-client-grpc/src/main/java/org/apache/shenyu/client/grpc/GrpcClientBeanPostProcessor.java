/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shenyu.client.grpc;

import com.google.common.collect.Lists;
import io.grpc.BindableService;
import io.grpc.ServerServiceDefinition;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shenyu.client.core.disruptor.ShenyuClientRegisterEventPublisher;
import org.apache.shenyu.client.grpc.common.annotation.ShenyuGrpcClient;
import org.apache.shenyu.client.grpc.common.dto.GrpcExt;
import org.apache.shenyu.client.grpc.json.JsonServerServiceInterceptor;
import org.apache.shenyu.common.utils.GsonUtils;
import org.apache.shenyu.common.utils.IpUtils;
import org.apache.shenyu.register.client.api.ShenyuClientRegisterRepository;
import org.apache.shenyu.register.common.config.ShenyuRegisterCenterConfig;
import org.apache.shenyu.register.common.dto.MetaDataRegisterDTO;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * The type Shenyu grpc client bean post processor.
 */
@Slf4j
public class GrpcClientBeanPostProcessor implements BeanPostProcessor {
    
    private ShenyuClientRegisterEventPublisher publisher = ShenyuClientRegisterEventPublisher.getInstance();
    
    private final ExecutorService executorService;
    
    private final String contextPath;
    
    private final String ipAndPort;

    private final String host;

    private final int port;

    @Getter
    private List<ServerServiceDefinition> serviceDefinitions = Lists.newArrayList();

    /**
     * Instantiates a new Shenyu client bean post processor.
     *
     * @param config the shenyu grpc config
     * @param shenyuClientRegisterRepository the shenyuClientRegisterRepository
     */
    public GrpcClientBeanPostProcessor(final ShenyuRegisterCenterConfig config, final ShenyuClientRegisterRepository shenyuClientRegisterRepository) {
        Properties props = config.getProps();
        String contextPath = props.getProperty("contextPath");
        String ipAndPort = props.getProperty("ipAndPort");
        String port = props.getProperty("port");
        if (StringUtils.isEmpty(contextPath) || StringUtils.isEmpty(ipAndPort) || StringUtils.isEmpty(port)) {
            throw new RuntimeException("grpc client must config the contextPath, ipAndPort");
        }
        this.ipAndPort = ipAndPort;
        this.contextPath = contextPath;
        this.host = props.getProperty("host");
        this.port = Integer.parseInt(port);
        executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        publisher.start(shenyuClientRegisterRepository);
    }

    @Override
    public Object postProcessAfterInitialization(@NonNull final Object bean, @NonNull final String beanName) throws BeansException {
        if (bean instanceof BindableService) {
            exportJsonGenericService(bean);
            executorService.execute(() -> handler(bean));
        }
        return bean;
    }

    private void handler(final Object serviceBean) {
        Class<?> clazz;
        try {
            clazz = serviceBean.getClass();
        } catch (Exception e) {
            log.error("failed to get grpc target class");
            return;
        }
        Class<?> parent = clazz.getSuperclass();
        Class<?> classes = parent.getDeclaringClass();
        String packageName;
        try {
            String serviceName = "SERVICE_NAME";
            Field field = classes.getField(serviceName);
            field.setAccessible(true);
            packageName = field.get(null).toString();
        } catch (Exception e) {
            log.error(String.format("SERVICE_NAME field not found: %s", classes));
            return;
        }
        if (StringUtils.isEmpty(packageName)) {
            log.error(String.format("grpc SERVICE_NAME can not found: %s", classes));
            return;
        }
        final Method[] methods = ReflectionUtils.getUniqueDeclaredMethods(clazz);
        for (Method method : methods) {
            ShenyuGrpcClient grpcClient = method.getAnnotation(ShenyuGrpcClient.class);
            if (Objects.nonNull(grpcClient)) {
                publisher.publishEvent(buildMetaDataDTO(packageName, grpcClient, method));
            }
        }
    }

    private MetaDataRegisterDTO buildMetaDataDTO(final String packageName, final ShenyuGrpcClient shenyuGrpcClient, final Method method) {
        String path = this.contextPath + shenyuGrpcClient.path();
        String desc = shenyuGrpcClient.desc();
        String configHost = this.host;
        String host = org.apache.commons.lang3.StringUtils.isBlank(configHost) ? IpUtils.getHost() : configHost;
        String configRuleName = shenyuGrpcClient.ruleName();
        String ruleName = ("".equals(configRuleName)) ? path : configRuleName;
        String methodName = method.getName();
        Class<?>[] parameterTypesClazz = method.getParameterTypes();
        String parameterTypes = Arrays.stream(parameterTypesClazz).map(Class::getName)
                .collect(Collectors.joining(","));
        return MetaDataRegisterDTO.builder()
                .appName(ipAndPort)
                .serviceName(packageName)
                .methodName(methodName)
                .contextPath(contextPath)
                .host(host)
                .port(port)
                .path(path)
                .ruleName(ruleName)
                .pathDesc(desc)
                .parameterTypes(parameterTypes)
                .rpcType("grpc")
                .rpcExt(buildRpcExt(shenyuGrpcClient))
                .enabled(shenyuGrpcClient.enabled())
                .build();
    }

    private String buildRpcExt(final ShenyuGrpcClient shenyuGrpcClient) {
        GrpcExt build = GrpcExt.builder().timeout(shenyuGrpcClient.timeout()).build();
        return GsonUtils.getInstance().toJson(build);
    }

    private void exportJsonGenericService(final Object bean) {
        BindableService bindableService = (BindableService) bean;
        ServerServiceDefinition serviceDefinition = bindableService.bindService();

        try {
            ServerServiceDefinition jsonDefinition = JsonServerServiceInterceptor.useJsonMessages(serviceDefinition);
            serviceDefinitions.add(serviceDefinition);
            serviceDefinitions.add(jsonDefinition);
        } catch (Exception e) {
            log.error("export json generic service is fail", e);
        }
    }
}
