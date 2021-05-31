<p align="center" >
    <a href="https://dromara.org"><img src="https://dromara.org/img/logo/ShenYu.png" width="45%"></a>
</p>
<p align="center">
  <strong>Scalable, High Performance, Responsive API Gateway Solution for all MicroServices</strong>
</p>
<p align="center">
  <a href="https://dromara.org">https://dromara.org/</a>
</p>

<p align="center">
  English | <a href="https://github.com/dromara/shenyu/blob/master/README_CN.md">简体中文</a>
</p>

<p align="center">
    <a target="_blank" href="https://search.maven.org/search?q=g:org.dromara%20AND%20a:soul">
        <img src="https://img.shields.io/maven-central/v/org.dromara/soul.svg?label=maven%20central" />
    </a>
    <a target="_blank" href="https://github.com/Dromara/soul/blob/master/LICENSE">
        <img src="https://img.shields.io/badge/License-Apache%202.0-blue.svg?label=license" />
    </a>
    <a target="_blank" href="https://www.oracle.com/technetwork/java/javase/downloads/index.html">
        <img src="https://img.shields.io/badge/JDK-8+-green.svg" />
    </a>
    <a target="_blank" href="https://github.com/dromara/soul/actions">
        <img src="https://github.com/dromara/soul/workflows/ci/badge.svg" />
    </a>
    <a href="https://www.codacy.com/app/yu199195/soul?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Dromara/soul&amp;utm_campaign=Badge_Grade">
        <img src="https://api.codacy.com/project/badge/Grade/4367ffad5b434b7e8078b3a68cc6398d"/>
    </a>
    <a target="_blank" href='https://gitee.com/dromara/soul/stargazers'>
        <img src='https://gitee.com/dromara/soul/badge/star.svg?theme=gvp' alt='gitee stars'/>
   </a>
   <a target="_blank" href='https://github.com/dromara/soul'>
        <img src="https://img.shields.io/github/forks/dromara/soul.svg" alt="github forks"/>
   </a>
   <a target="_blank" href='https://github.com/dromara/soul'>
        <img src="https://img.shields.io/github/stars/dromara/soul.svg" alt="github stars"/>
   </a>
   <a target="_blank" href='https://github.com/dromara/soul'>
        <img src="https://img.shields.io/github/contributors/dromara/soul.svg" alt="github contributors"/>
   </a>     
   <a href="https://github.com/Dromara/soul">
        <img src="https://tokei.rs/b1/github/Dromara/soul?category=lines"/>
   </a>
   <a target="_blank" href="https://codecov.io/gh/dromara/soul">
        <img src="https://codecov.io/gh/dromara/soul/branch/master/graph/badge.svg" />
   </a>
</p>
<br/>

--------------------------------------------------------------------------------

# Architecture
 
 ![](https://yu199195.github.io/images/soul/soul-framework.png)  
 
-------------------------------------------------------------------------------- 
  
# Mind maps
 
 ![](https://dromara.org/img/soul/activite/soul-xmind.png)
 
--------------------------------------------------------------------------------  
  
# Modules

 * shenyu-admin : plugins and other configuration information management background
 
 * shenyu-bootstrap : with the startup project, users can refer to
 
 * shenyu-client : user fast access with Spring MVC, Dubbo, Spring Cloud.
  
 * shenyu-common : framework common class
 
 * shenyu-disruptor : based on disruptor Enclosure
 
 * shenyu-register-center : rpc type register for shenyu-client
 
 * shenyu-dist : build project

 * shenyu-metrics : metrics impl by prometheus.
 
 * shenyu-plugin : ShenYu provider plugin collection.
 
 * shenyu-spi : ShenYu spi define.
 
 * shenyu-spring-boot-starter : support for the spring boot starter
 
 * shenyu-sync-data-center : provider ZooKeeper, HTTP, WebSocket, Nacos to sync data
 
 * shenyu-examples : the RPC examples project
 
 * shenyu-web : core processing packages including plugins, request routing and forwarding, and so on
 
--------------------------------------------------------------------------------   
 
# Features

   * ShenYu provides ability such as current limiting, fusing, forwarding, routing monitoring and so on by its plugins.
   
   * Support HTTP, RESTFul, WebSocket, Dubbo, GRPC, Tars and Spring Cloud Proxy.
   
   * Plug-in hot plug, users can customize the development.
   
   * Selectors and rules are dynamically configured for flexible matching.

   * Support for cluster deployment.
   
   * Support A/B test and grayscale publishing.
   
--------------------------------------------------------------------------------  
 
# Plugin

 Whenever a request comes in, ShenYu will execute it by all enabled plugins through the chain of responsibility.
 
 As the heart of ShenYu, plugins are extensible and hot-pluggable.
 
 Different plugins do different things.
 
 Of course, users can also customize plugins to meet their own needs.
 
 If you want to customize, see [custom-plugin](https://dromara.org/projects/soul/custom-plugin/)
 
--------------------------------------------------------------------------------  
 
# Selector & rule 

  According to your HTTP request headers, selectors and rules are used to route your requests.
  
  Selector is your first route, It is coarser grained, for example, at the module level.
  
  Rule is your second route and what do you think your request should do. For example a method level in a module.
  
  The selector and the rule match only once, and the match is returned. So the coarsest granularity should be sorted last.
 
--------------------------------------------------------------------------------  
   
# Data Caching & Data Sync
 
  Since all data have been cached using ConcurrentHashMap in the JVM, it's very fast.
  
  When user have changed the configuration in the background management, ShenYu wiil dynamically updates its cache by listening to the ZooKeeper node, WebSocket push, HTTP longPull.
  
  ![](https://yu199195.github.io/images/soul/soul-config-processor.png)
  
  ![](https://yu199195.github.io/images/soul/config-strage-processor.png)

--------------------------------------------------------------------------------    

# Prerequisite
 
   * JDK 1.8+
   
--------------------------------------------------------------------------------     
   
# About
  
   ShenYu has been used widely in more and more systems in many companies, and it's simple and convenient to integrate Services/APIs with the high performance and flexibility.
   
   In double eleven online shopping carnival of China, ShenYu clusters successfully supported a large volume of internet business.
   
--------------------------------------------------------------------------------  
    
# Document & Website

[![EN doc](https://img.shields.io/badge/document-English-blue.svg)](https://dromara.org/projects/soul/overview)
[![CN doc](https://img.shields.io/badge/document-Chinese-blue.svg)](https://dromara.org/zh/projects/soul/overview)
  
--------------------------------------------------------------------------------  
        
# Stargazers over time

[![Stargazers over time](https://starchart.cc/dromara/soul.svg)](https://starchart.cc/dromara/soul)

--------------------------------------------------------------------------------  

# Known Users

In order of registration, More access companies are welcome to register at [https://github.com/dromara/soul/issues/68](https://github.com/dromara/soul/issues/68) (For open source users only)

<table>
  <tbody>
    <tr>
      <td><img src="https://yu199195.github.io/images/soul/users/joyy.png"  width="1800" height="90" alt="yy"/>
      <td><img src="https://yu199195.github.io/images/soul/users/mihoyo.jpg"  width="1800" height="90" alt="mihoyo"/>
      <td><img src="https://yu199195.github.io/images/soul/users/keking.png"  width="1800" height="90" alt="kk group"/>
      <td><img src="https://yu199195.github.io/images/soul/users/shansong.jpg"  width="1800" height="90" alt="shansong"/>
    </tr>
  </tbody>
  <tbody>
    <tr>
      <td><img src="https://yu199195.github.io/images/soul/users/sibu.jpg"  width="1800" height="90" alt="sibu group"/>
      <td><img src="https://yu199195.github.io/images/soul/users/guojiadianwang.jpg"  width="1800" height="90" alt="guojiadianwang"/>
      <td><img src="https://yu199195.github.io/images/soul/users/caibeike.png"  width="1800" height="90" alt="caibeike"/>
      <td><img src="https://yu199195.github.io/images/soul/users/jiangsuyonggang.jpg"  width="1800" height="90" alt="jiangsuyonggang"/>
    </tr>
  </tbody>
  <tbody>
    <tr>
      <td><img src="https://yu199195.github.io/images/soul/users/fangfutong.png"  width="1800" height="90" alt="fangfutong"/>
      <td><img src="https://yu199195.github.io/images/soul/users/lixiang.jpg"  width="1800" height="90" alt="lixiang"/>
      <td><img src="https://yu199195.github.io/images/soul/users/kaipuyun.png"  width="1800" height="90" alt="kaipuyun"/>
      <td><img src="https://yu199195.github.io/images/soul/users/songda.png"  width="1800" height="90" alt="songda"/>
     </tr>
  </tbody>
  <tbody>
    <tr>
      <td><img src="https://yu199195.github.io/images/soul/users/aoyou.jpg"  width="1800" height="90" alt="aoyou"/>
      <td><img src="https://yu199195.github.io/images/soul/users/cheyipai.jpg"  width="1800" height="90" alt="cheyipai"/>
      <td><img src="https://yu199195.github.io/images/soul/users/caomao.jpg"  width="1800" height="90" alt="caomao"/>
      <td><img src="https://yu199195.github.io/images/soul/users/zuyun.jpg"  width="1800" height="90" alt="zuyun"/>
    </tr>
  </tbody>
  <tbody>
    <tr>
      <td><img src="https://yu199195.github.io/images/soul/users/hezhi.png"  width="1800" height="90" alt="hezhi"/>
      <td><img src="https://yu199195.github.io/images/soul/users/qidianyun.jpg"  width="1800" height="90" alt="qidianyun"/>
      <td><img src="https://yu199195.github.io/images/soul/users/wanwei.gif"  width="1800" height="90" alt="wanwei"/>
      <td><img src="https://yu199195.github.io/images/soul/users/wuyiyuntong.jpg"  width="1800" height="90" alt="wuyiyuntong"/>
    </tr>            
  </tbody>
  <tbody>
    <tr>
      <td><img src="https://yu199195.github.io/images/soul/users/haokangzaijia.jpg"  width="1800" height="90" alt="haokangzaijia"/>
      <td><img src="https://yu199195.github.io/images/soul/users/caissa.jpg"  width="1800" height="90" alt="caissa"/>
      <td><img src="https://yu199195.github.io/images/soul/users/deepBule.png"  width="1800" height="90" alt="deepBule"/>
      <td><img src="https://yu199195.github.io/images/soul/users/anka.png"  width="1800" height="90" alt="anka"/>
    </tr>
  </tbody>     
  <tbody>
    <tr>
      <td><img src="https://dromara.org/img/users/jd_logo.png"  width="1800" height="90" alt="jd"/>
      <td><img src="https://yu199195.github.io/images/soul/users/minglamp.jpeg"  width="1800" height="90" alt="minglamp"/>
      <td><img src="https://yu199195.github.io/images/soul/users/webuy.jpg"  width="1800" height="90" alt="webuy"/>
      <td><img src="https://dromara.org/img/users/cass.png"  width="1800" height="90" alt="cass"/>
    </tr>
  </tbody> 
  <tbody>
    <tr>
      <td><img src="https://dromara.org/img/users/songguo.png"  width="1800" height="90" alt="songguo"/>
      <td><img src="https://dromara.org/img/users/lianlian.png"  width="1800" height="90" alt="lianlian"/>
      <td><img src="https://dromara.org/img/users/dasouche.png"  width="1800" height="90" alt="dasouche"/>
      <td><img src="https://dromara.org/img/users/weimai.png"  width="1800" height="90" alt="weimai"/>
    </tr>
  </tbody> 
</table>
