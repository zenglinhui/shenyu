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

package org.apache.shenyu.sync.data.nacos.config;

import org.junit.Assert;
import org.junit.Test;

/**
 * add test case for {@link NacosACMConfig}.
 */
public final class NacosACMConfigTest {

    @Test
    public void testGetterSetter() {
        NacosACMConfig nacosACMConfig = new NacosACMConfig();
        nacosACMConfig.setAccessKey("accessKey");
        nacosACMConfig.setEnabled(false);
        nacosACMConfig.setEndpoint("endpoint");
        nacosACMConfig.setNamespace("namespace");
        nacosACMConfig.setSecretKey("secreKey");
        Assert.assertEquals("accessKey", nacosACMConfig.getAccessKey());
        Assert.assertEquals(false, nacosACMConfig.isEnabled());
        Assert.assertEquals("endpoint", nacosACMConfig.getEndpoint());
        Assert.assertEquals("namespace", nacosACMConfig.getNamespace());
        Assert.assertEquals("secreKey", nacosACMConfig.getSecretKey());
    }
}

