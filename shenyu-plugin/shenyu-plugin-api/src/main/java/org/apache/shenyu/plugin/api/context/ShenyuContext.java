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

package org.apache.shenyu.plugin.api.context;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * the shenyu context.
 */
@Data
public class ShenyuContext implements Serializable {

    private static final long serialVersionUID = 8668695964617280718L;

    /**
     * is module data.
     */
    private String module;

    /**
     * is method name .
     */
    private String method;

    /**
     * is rpcType data. now we only support "http","dubbo","springCloud","sofa".
     */
    private String rpcType;

    /**
     * httpMethod now we only support "get","post" .
     */
    private String httpMethod;

    /**
     * this is sign .
     */
    private String sign;

    /**
     * timestamp .
     */
    private String timestamp;

    /**
     * appKey .
     */
    private String appKey;

    /**
     * path.
     */
    private String path;

    /**
     * the contextPath.
     */
    private String contextPath;

    /**
     * realUrl.
     */
    private String realUrl;

    /**
     * this is dubbo params.
     */
    private String dubboParams;

    /**
     * startDateTime.
     */
    private LocalDateTime startDateTime;
}
