CONTAINER_NAME=cbr-manager

.PHONY: stop
stop:
	@docker rm -f $(CONTAINERNAME)

.PHONY: build
build: 
	-@docker build -t ${CONTAINER_NAME} .

.PHONY: start
start: build
	-@docker run -dp 8000:8000 --name ${CONTAINER_NAME} ${CONTAINER_NAME}