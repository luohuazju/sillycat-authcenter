IMAGE=keycloak
TAG=25.0
NAME=sillycat.keycloak
REPOSITORY=quay.io/keycloak

# 单节点开发模式
run:
	docker run -d -p 8080:8080 \
	-e KEYCLOAK_ADMIN=admin \
	-e KEYCLOAK_ADMIN_PASSWORD=admin \
	--name $(NAME) $(REPOSITORY)/$(IMAGE):$(TAG) \
	start-dev

# 多节点集群模式（使用 Docker Compose）
cluster-up:
	docker-compose up -d

cluster-down:
	docker-compose down

cluster-logs:
	docker-compose logs -f

cluster-restart:
	docker-compose restart

# 查看集群状态
cluster-status:
	@echo "=== Keycloak Nodes ==="
	@docker ps --filter "name=keycloak-node" --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"
	@echo "\n=== Database ==="
	@docker ps --filter "name=keycloak-postgres" --format "table {{.Names}}\t{{.Status}}"
	@echo "\n=== Load Balancer ==="
	@docker ps --filter "name=keycloak-lb" --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"

# 扩展到 N 个节点
scale:
	docker-compose up -d --scale keycloak-1=3

clean:
	docker stop $(NAME)
	docker rm $(NAME)

clean-cluster:
	docker-compose down -v

log:
	docker logs -t -f $(NAME)
