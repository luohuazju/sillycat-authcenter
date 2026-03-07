# Keycloak 扩展方案

## 当前架构
- 单个 Docker 容器
- 开发模式 (start-dev)
- 内置 H2 数据库
- 无持久化存储

## 扩展策略

### 1. 生产模式 + 外部数据库（基础）

首先需要切换到生产模式并使用外部数据库（PostgreSQL/MySQL）。

**优点：**
- 数据持久化
- 支持高可用
- 生产级配置

**架构：**
```
[Keycloak Container] → [PostgreSQL Database]
```

### 2. 多节点集群 + 负载均衡（推荐）

使用多个 Keycloak 实例 + 共享数据库 + 负载均衡器。

**优点：**
- 高可用性
- 水平扩展
- 故障转移

**架构：**
```
                    [Load Balancer]
                    /      |      \
        [Keycloak-1] [Keycloak-2] [Keycloak-3]
                    \      |      /
                  [PostgreSQL Cluster]
                  /                \
        [Primary DB]          [Replica DB]
```

### 3. Kubernetes 部署（企业级）

使用 Kubernetes 进行容器编排和自动扩展。

**优点：**
- 自动扩展
- 自愈能力
- 服务发现
- 滚动更新

**架构：**
```
[Ingress/Load Balancer]
        ↓
[Keycloak StatefulSet (3+ replicas)]
        ↓
[PostgreSQL StatefulSet/External DB]
        ↓
[Persistent Volumes]
```

## 实施步骤

### 步骤 1: 添加外部数据库

使用 Docker Compose 或独立的 PostgreSQL 实例。

### 步骤 2: 配置生产模式

- 启用 HTTPS
- 配置数据库连接
- 设置缓存（Infinispan）
- 配置集群发现

### 步骤 3: 部署多个实例

- 使用相同的数据库
- 配置会话复制
- 启用集群模式

### 步骤 4: 添加负载均衡

- Nginx/HAProxy/云负载均衡器
- 配置健康检查
- 启用 sticky sessions（可选）

### 步骤 5: 监控和日志

- Prometheus + Grafana
- ELK Stack
- 健康检查端点

## 关键配置项

### 数据库
- 连接池大小
- 事务隔离级别
- 备份策略

### 缓存
- Infinispan 集群配置
- 缓存失效策略
- 会话复制

### 网络
- 集群发现机制（JDBC_PING/KUBE_PING）
- JGroups 配置
- 端口配置

## 性能优化

1. **数据库优化**
   - 索引优化
   - 连接池调优
   - 读写分离

2. **缓存策略**
   - 本地缓存 + 分布式缓存
   - 缓存预热
   - TTL 配置

3. **负载均衡**
   - 会话亲和性
   - 健康检查
   - 连接限制

4. **资源限制**
   - CPU/内存限制
   - JVM 堆大小
   - 线程池配置
