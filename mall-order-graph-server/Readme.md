# Mall Order Graph Server

这是一个基于Spring Boot和阿里云Spring AI Alibaba构建的订单图谱服务项目，用于处理电商订单相关的图谱查询和分析。

## 项目概述

该项目使用Spring AI Alibaba的图计算能力，构建了一个订单处理图谱系统。主要功能包括：

- 使用MCP（Model Control Protocol）客户端与外部服务通信
- 通过图计算框架处理订单相关查询
- 集成阿里云千问大模型进行智能处理

## 技术栈

- Java 17
- Spring Boot
- Spring AI Alibaba
- 阿里云千问大模型 (Qwen)
- MCP (Model Control Protocol)

## 项目结构

```
mall-order-graph-server/
├── src/main/java/
│   └── com/example/mallordergraphserver/
│       ├── config/           # 配置类
│       ├── controller/       # 控制器
│       ├── node/            # 图节点实现
│       └── tools/           # 工具类
└── src/main/resources/
    └── application.yml      # 配置文件
```


## 核心组件

### 1. 图计算配置 (`OrderMcpGraphConfig`)
- 定义了订单处理图谱的结构
- 配置了MCP节点及其连接关系
- 使用ReplaceStrategy处理状态更新

### 2. 控制器 (`OrderGraphController`)
- 提供 `/graph/mcp` 接口处理订单查询
- 默认查询为"手机订单"

### 3. MCP节点 (`McpNode`)
- 实现图谱中的MCP处理节点
- 集成聊天客户端和工具回调

## 配置说明

在 `application.yml` 中的主要配置项：

```yaml
server:
  port: 8889

spring:
  ai:
    dashscope:
      chat:
        enabled: true
        model: qwen-turbo
        api-key: ${DASHSCOPE_API_KEY}
    
    mcp:
      client:
        enabled: true
        name: my-mcp-client
        version: 1.0.0
        request-timeout: 30s
        type: ASYNC
        sse:
          connections:
            server1:
              url: http://localhost:8080
    
    graph:
      nodes:
        node2servers:
          mcp-node:
            - server1
```


## 使用方法

1. 配置阿里云千问API Key:
   ```bash
   export DASHSCOPE_API_KEY=your_api_key_here
   ```


2. 启动应用:
   ```bash
   ./mvnw spring-boot:run
   ```


3. 发起查询请求:
   ```bash
   curl "http://localhost:8889/graph/mcp?query=查询手机订单状态"
   ```


## 依赖说明

主要依赖包括：
- `spring-boot-starter-web`: Web应用基础依赖
- `spring-ai-alibaba-starter-dashscope`: 阿里云千问模型支持
- `spring-ai-starter-mcp-client-webflux`: MCP客户端支持
- `spring-ai-alibaba-graph-core`: 图计算核心组件

## 注意事项

- 需要配置有效的阿里云千问API Key
- 需要确保MCP服务端地址可访问
- Java版本需为17或以上