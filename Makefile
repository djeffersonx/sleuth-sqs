down:
	@docker-compose down --v
run:
	@$(MAKE) down
	@$(MAKE) setup
	docker-compose up -d --build
setup:
	@$(MAKE) down
	$(MAKE) -C ./producer setup
	$(MAKE) -C ./consumer-1 setup
	$(MAKE) -C ./consumer-2 setup