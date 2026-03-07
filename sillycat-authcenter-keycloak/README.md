# Keycloak 部署指南

## 快速开始

### 单节点开发模式
```bash
make run
```
访问: http://localhost:8080

### 多节点集群模式（Docker Compose）
```bash
# 启动集群（2个 Keycloak 节点 + PostgreSQL + Nginx）
make cluster-up

# 查看状态
make cluster-status

# 查看日志
make cluster-logs

# 停止集群
make cluster-down
```
访问: http://localhost:8080 (通过 Nginx 负载均衡)

## 架构说明

### Docker Compose 集群架构
```
Client → Nginx (Load Balancer) → Keycloak Node 1
                                → Keycloak Node 2
                                       ↓
                                  PostgreSQL
```

特性：
- 2个 Keycloak 节点（可扩展）
- PostgreSQL 数据库（持久化）
- Nginx 负载均衡（IP hash 会话保持）
- 健康检查
- 自动重启

### Kubernetes 部署

```bash
# 创建 namespace 和 secrets
kubectl apply -f k8s-secrets.yaml

# 部署 Keycloak 集群
kubectl apply -f k8s-deployment.yaml

# 查看状态
kubectl get pods -n keycloak
kubectl get svc -n keycloak

# 查看日志
kubectl logs -f deployment/keycloak -n keycloak
```

特性：
- 3个 Keycloak 副本（最少）
- StatefulSet PostgreSQL
- 自动扩展（HPA）：3-10 个副本
- Ingress 配置
- 健康检查和探针
- 资源限制

## 配置说明

### 环境变量

| 变量 | 说明 | 默认值 |
|------|------|--------|
| KEYCLOAK_ADMIN | 管理员用户名 | admin |
| KEYCLOAK_ADMIN_PASSWORD | 管理员密码 | admin |
| KC_DB | 数据库类型 | postgres |
| KC_DB_URL | 数据库连接 | - |
| KC_HOSTNAME | 主机名 | localhost |
| KC_PROXY | 代理模式 | edge |

### 集群配置

Keycloak 使用 Infinispan 进行缓存和会话复制：

- **Docker Compose**: 使用 TCP 协议和 DNS 查询
- **Kubernetes**: 使用 KUBE_PING 协议自动发现

### 负载均衡策略

- **IP Hash**: 保持会话亲和性（同一客户端总是路由到同一节点）
- **健康检查**: 自动移除不健康的节点
- **故障转移**: 节点失败时自动切换

## 性能调优

### JVM 参数
```bash
JAVA_OPTS_APPEND="-Xms1024m -Xmx2048m -XX:MetaspaceSize=256m"
```

### 数据库连接池
在 Keycloak 配置中调整：
- 最小连接数: 10
- 最大连接数: 50

### 缓存配置
- 本地缓存: 10000 条目
- 分布式缓存: 启用
- 缓存失效: 3600 秒

## 监控

### 健康检查端点
- `/health/live` - 存活探针
- `/health/ready` - 就绪探针
- `/metrics` - Prometheus 指标

### 日志
```bash
# Docker Compose
docker-compose logs -f keycloak-1

# Kubernetes
kubectl logs -f deployment/keycloak -n keycloak
```

## 故障排查

### 集群节点无法通信
检查网络配置和 JGroups 日志：
```bash
docker logs keycloak-node-1 | grep jgroups
```

### 数据库连接失败
检查数据库状态和连接字符串：
```bash
docker exec -it keycloak-postgres psql -U keycloak -d keycloak
```

### 负载均衡问题
检查 Nginx 日志：
```bash
docker logs keycloak-lb
```

## 安全建议

1. 修改默认管理员密码
2. 使用 HTTPS（生产环境必须）
3. 配置防火墙规则
4. 定期备份数据库
5. 使用 secrets 管理敏感信息
6. 启用审计日志
7. 限制管理控制台访问

## 备份和恢复

### 数据库备份
```bash
docker exec keycloak-postgres pg_dump -U keycloak keycloak > backup.sql
```

### 恢复
```bash
docker exec -i keycloak-postgres psql -U keycloak keycloak < backup.sql
```

## 扩展阅读

- [Keycloak 官方文档](https://www.keycloak.org/documentation)
- [高可用部署指南](https://www.keycloak.org/high-availability/introduction)
- [性能调优](https://www.keycloak.org/server/configuration-production)
