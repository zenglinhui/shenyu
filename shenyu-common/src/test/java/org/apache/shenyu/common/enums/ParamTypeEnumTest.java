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

package org.apache.shenyu.common.enums;

import org.apache.shenyu.common.exception.ShenyuException;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * Test Cases for ParamTypeEnum.
 */
public final class ParamTypeEnumTest {

    @Test
    public void testAcquireSupport() {
        List<ParamTypeEnum> supportParamTypeList = Arrays.stream(ParamTypeEnum.values())
                .filter(ParamTypeEnum::getSupport).collect(Collectors.toList());
        assertEquals(ParamTypeEnum.acquireSupport(), supportParamTypeList);
    }

    @Test
    public void testGetParamTypeEnumByNameValid() {
        Arrays.stream(ParamTypeEnum.values())
                .filter(ParamTypeEnum::getSupport)
                .forEach(paramTypeEnum -> assertEquals(paramTypeEnum, ParamTypeEnum.getParamTypeEnumByName(paramTypeEnum.getName())));
    }

    @Test(expected = ShenyuException.class)
    public void testGetParamTypeEnumByNameInvalid() {
        ParamTypeEnum.getParamTypeEnumByName("InvalidName");
    }
}
