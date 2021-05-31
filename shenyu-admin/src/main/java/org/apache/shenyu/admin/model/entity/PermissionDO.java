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

package org.apache.shenyu.admin.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.shenyu.admin.model.dto.PermissionDTO;
import org.apache.shenyu.common.utils.UUIDUtils;
import reactor.util.StringUtils;

import java.sql.Timestamp;
import java.util.Optional;

/**
 * The Permission Entity.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class PermissionDO extends BaseDO {

    private static final long serialVersionUID = 8371869040638596986L;

    /**
     * user key or role key.
     */
    private String objectId;

    /**
     * resource key.
     */
    private String resourceId;

    /**
     * build Permission DO.
     *
     * @param permissionDTO {@linkplain PermissionDTO}
     * @return {@linkplain PermissionDO}
     */
    public static PermissionDO buildPermissionDO(final PermissionDTO permissionDTO) {
        return Optional.ofNullable(permissionDTO).map(item -> {
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            PermissionDO permissionDO = PermissionDO.builder()
                    .objectId(item.getObjectId())
                    .resourceId(item.getResourceId())
                    .build();
            if (StringUtils.isEmpty(item.getId())) {
                permissionDO.setId(UUIDUtils.getInstance().generateShortUuid());
                permissionDO.setDateCreated(currentTime);
            } else {
                permissionDO.setId(item.getId());
            }
            return permissionDO;
        }).orElse(null);
    }
}
