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

package org.apache.shenyu.admin.listener.zookeeper;

import lombok.SneakyThrows;
import org.I0Itec.zkclient.ZkClient;
import org.apache.shenyu.admin.listener.DataChangedListener;
import org.apache.shenyu.common.constant.ZkPathConstants;
import org.apache.shenyu.common.dto.AppAuthData;
import org.apache.shenyu.common.dto.MetaData;
import org.apache.shenyu.common.dto.PluginData;
import org.apache.shenyu.common.dto.RuleData;
import org.apache.shenyu.common.dto.SelectorData;
import org.apache.shenyu.common.enums.DataEventTypeEnum;

import java.net.URLEncoder;
import java.util.List;

/**
 * Use zookeeper to push data changes.
 */
public class ZookeeperDataChangedListener implements DataChangedListener {

    private final ZkClient zkClient;

    public ZookeeperDataChangedListener(final ZkClient zkClient) {
        this.zkClient = zkClient;
    }

    @Override
    public void onAppAuthChanged(final List<AppAuthData> changed, final DataEventTypeEnum eventType) {
        for (AppAuthData data : changed) {
            String appAuthPath = ZkPathConstants.buildAppAuthPath(data.getAppKey());
            // delete
            if (eventType == DataEventTypeEnum.DELETE) {
                deleteZkPath(appAuthPath);
                continue;
            }
            // create or update
            insertZkNode(appAuthPath, data);
        }
    }

    @SneakyThrows
    @Override
    public void onMetaDataChanged(final List<MetaData> changed, final DataEventTypeEnum eventType) {
        for (MetaData data : changed) {
            String metaDataPath = ZkPathConstants.buildMetaDataPath(URLEncoder.encode(data.getPath(), "UTF-8"));
            // delete
            if (eventType == DataEventTypeEnum.DELETE) {
                deleteZkPath(metaDataPath);
                continue;
            }
            // create or update
            insertZkNode(metaDataPath, data);
        }
    }

    @Override
    public void onPluginChanged(final List<PluginData> changed, final DataEventTypeEnum eventType) {
        for (PluginData data : changed) {
            String pluginPath = ZkPathConstants.buildPluginPath(data.getName());
            // delete
            if (eventType == DataEventTypeEnum.DELETE) {
                deleteZkPathRecursive(pluginPath);
                String selectorParentPath = ZkPathConstants.buildSelectorParentPath(data.getName());
                deleteZkPathRecursive(selectorParentPath);
                String ruleParentPath = ZkPathConstants.buildRuleParentPath(data.getName());
                deleteZkPathRecursive(ruleParentPath);
                continue;
            }
            //create or update
            insertZkNode(pluginPath, data);
        }
    }

    @Override
    public void onSelectorChanged(final List<SelectorData> changed, final DataEventTypeEnum eventType) {
        if (eventType == DataEventTypeEnum.REFRESH && !changed.isEmpty()) {
            String selectorParentPath = ZkPathConstants.buildSelectorParentPath(changed.get(0).getPluginName());
            deleteZkPathRecursive(selectorParentPath);
        }
        for (SelectorData data : changed) {
            String selectorRealPath = ZkPathConstants.buildSelectorRealPath(data.getPluginName(), data.getId());
            if (eventType == DataEventTypeEnum.DELETE) {
                deleteZkPath(selectorRealPath);
                continue;
            }
            String selectorParentPath = ZkPathConstants.buildSelectorParentPath(data.getPluginName());
            createZkNode(selectorParentPath);
            //create or update
            insertZkNode(selectorRealPath, data);
        }
    }

    @Override
    public void onRuleChanged(final List<RuleData> changed, final DataEventTypeEnum eventType) {
        if (eventType == DataEventTypeEnum.REFRESH && !changed.isEmpty()) {
            String selectorParentPath = ZkPathConstants.buildRuleParentPath(changed.get(0).getPluginName());
            deleteZkPathRecursive(selectorParentPath);
        }
        for (RuleData data : changed) {
            String ruleRealPath = ZkPathConstants.buildRulePath(data.getPluginName(), data.getSelectorId(), data.getId());
            if (eventType == DataEventTypeEnum.DELETE) {
                deleteZkPath(ruleRealPath);
                continue;
            }
            String ruleParentPath = ZkPathConstants.buildRuleParentPath(data.getPluginName());
            createZkNode(ruleParentPath);
            //create or update
            insertZkNode(ruleRealPath, data);
        }
    }
    
    private void insertZkNode(final String path, final Object data) {
        createZkNode(path);
        zkClient.writeData(path, data);
    }
    
    private void createZkNode(final String path) {
        if (!zkClient.exists(path)) {
            zkClient.createPersistent(path, true);
        }
    }
    
    private void deleteZkPath(final String path) {
        if (zkClient.exists(path)) {
            zkClient.delete(path);
        }
    }
    
    private void deleteZkPathRecursive(final String path) { 
        if (zkClient.exists(path)) {
            zkClient.deleteRecursive(path);
        }
    }
}
