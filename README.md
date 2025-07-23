# 使用Spring AI Alibaba实现的智能客服 

> Spring AI Alibaba Repo: https://github.com/alibaba/spring-ai-alibaba
>
> Spring AI Alibaba Website:  https://java2ai.com
>
> Spring AI Alibaba Website Repo: https://github.com/springaialibaba/spring-ai-alibaba-website



EcommSpringBot

EcommSpringBot 是一个基于 SpringAI Alibaba 的企业级智能客服助手系统，支持用户通过自然语言交互完成订单查询、自动取消、规则问答等操作，融合微服务架构、RAG 技术、Redis 聊天上下文记忆与 Elasticsearch 向量数据库，实现私有化可扩展的智能体服务平台。

🌟 项目亮点
•	🧠 基于 SpringAI Alibaba 实现企业智能体对话能力（支持通义千问/OpenAI）
•	🔗 模块化设计：订单服务、中台服务、SSE方式连接MCP服务、RAG 知识库清晰分离
•	🧾 支持自然语言查询订单、自动取消订单
•	📚 使用 Elasticsearch 构建企业知识库，支持 RAG 检索增强
•	🧠 使用 Redis 实现上下文记忆功能，支持多轮对话

🏗️ 模块结构

EcommSpringBot/
├── mall-order/                  # 核心订单服务（Spring Boot + MySQL）
├── mall-order-cmp-server/       # CMP 中台服务（封装订单能力）
├── mall-order-cmp_sso-client/   # 提供 Controller 接口 + Redis 聊天上下文支持
├── mall-order-es-rag/          # 向量知识库模块（退换货规则文档 → ES 向量）
└── README.md

⚙️ 模块说明

1. mall-order
   •	Spring Boot 编写的基础订单服务
   •	提供订单查询、取消等接口
   •	连接 MySQL，数据表设计合理

2. mall-order-cmp-server
   •	将订单能力封装成 CMP Server，可供多智能体复用
   •	提供中台统一鉴权、日志、调用封装

3. mall-order-cmp_sse-client
   •	SSO 登录，支持前端用户身份识别
   •	暴露聊天 Assistant API，基于 SpringAI 调用大模型
   •	连接 Redis 实现对话历史持久化（上下文保持）

4. mall-order-es-rag
   •	使用企业规则文档生成 embedding
   •	存入 Elasticsearch，支持余弦相似度检索
   •	构建私有化知识问答能力

🚀 快速启动

前提条件
•	JDK 17+
•	Maven 3.9+
•	Redis
•	MySQL
•	Elasticsearch 8.11+

启动顺序

# 启动 mall-order 服务
cd mall-order && mvn spring-boot:run

# 启动 mall-order-cmp-server
cd ../mall-order-cmp-server && mvn spring-boot:run

# 启动 sso-client 接口层（含AI智能体接口）
cd ../mall-order-cmp_sso-client && mvn spring-boot:run

# 启动 es-rag 服务（用于知识库管理）
cd ../mall-order-es-rag && mvn spring-boot:run

📖 示例功能

查询订单

用户输入：我想查一下我的订单
➡ 系统提取用户身份 ➡ 调用 mall-order 接口 ➡ 返回订单列表

取消订单

用户输入：帮我取消订单 12345
➡ 系统识别订单号 ➡ 发起取消调用 ➡ 返回取消成功提示

企业规则问答

用户输入：退货流程是怎样的？
➡ 向量检索规则文档 ➡ 提供准确回答

📄 LICENSE

本项目基于 Apache 2.0 License 开源，可自由使用、修改与分发。

⸻

如果你觉得该项目有价值，欢迎 Star⭐️ 或 Fork🍴！

📬 联系作者：996766130@qq.com
## 前端页面
![img_1.png](img_1.png)
