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

package org.apache.shenyu.admin.utils;

import org.apache.shenyu.admin.config.properties.JwtProperties;
import org.apache.shenyu.admin.spring.SpringBeanUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * test case for {@link JwtUtils}.
 */
public class JwtUtilsTest {

    public static final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ1c2VyTmFtZSIsImlhdCI6MTYxMTU5MDUwOH0.yAuGpmg1DSYNryZQQA6d66HO87E8eWAFLJVhYscx8K8";

    @Before
    public void setUp() {
        ConfigurableApplicationContext context = mock(ConfigurableApplicationContext.class);
        JwtProperties jwtProperties = mock(JwtProperties.class);
        when(jwtProperties.getKey()).thenReturn("jwt-key");
        when(context.getBean(JwtProperties.class)).thenReturn(jwtProperties);
        SpringBeanUtils.getInstance().setCfgContext(context);
    }

    @Test
    public void testGetIssuer() {
        assertThat(JwtUtils.getIssuer(TOKEN), is("userName"));
    }

    @Test
    public void testGenerateToken() {
        String token = JwtUtils.generateToken("userName", "userId");
        assertThat(token, notNullValue());
        assertThat(JwtUtils.getIssuerDate(token), comparesEqualTo(LocalDate.now()));
        assertThat(JwtUtils.getIssuer(token), is("userName"));
    }
}
