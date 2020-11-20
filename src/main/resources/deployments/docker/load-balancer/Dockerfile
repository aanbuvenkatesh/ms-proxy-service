ARG LOAD_BALANCER_IMAGE
FROM ${LOAD_BALANCER_IMAGE}

ARG load_balancer_config
RUN rm /etc/nginx/nginx.conf
COPY ${load_balancer_config} /etc/nginx/${load_balancer_config}