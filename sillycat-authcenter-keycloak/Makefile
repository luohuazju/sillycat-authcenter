run-keycloak:
	docker run -d -p 8080:8080 \
	-e KEYCLOAK_ADMIN=admin \
	-e KEYCLOAK_ADMIN_PASSWORD=admin \
	--name keycloack-server quay.io/keycloak/keycloak:25.0 \
	start-dev

