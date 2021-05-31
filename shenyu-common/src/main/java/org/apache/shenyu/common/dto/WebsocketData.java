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

package org.apache.shenyu.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.shenyu.common.enums.ConfigGroupEnum;
import org.apache.shenyu.common.enums.DataEventTypeEnum;

import java.io.Serializable;
import java.util.List;

/**
 * Data set, including {@link AppAuthData}、{@link ConditionData}、{@link PluginData}、{@link RuleData}、{@link SelectorData}.
 *
 * @param <T> the type parameter
 * @since 2.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class WebsocketData<T> implements Serializable {

    private static final long serialVersionUID = -3068033915566306050L;

    /**
     * group type.
     * {@linkplain ConfigGroupEnum}
     */
    private String groupType;

    /**
     * event type.
     * {@linkplain DataEventTypeEnum}
     */
    private String eventType;

    /**
     * data list.
     * {@link AppAuthData}、{@link ConditionData}、{@link PluginData}、{@link RuleData}、{@link SelectorData}.
     */
    private List<T> data;
}
