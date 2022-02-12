# fluentd/Dockerfile
FROM fluent/fluentd:v1.14.5-1.0
#RUN ["gem", "install", "fluent-plugin-elasticsearch", "--user-install", "--version", "5.1.5"]
USER root
RUN ["gem", "uninstall", "-I", "elasticsearch"]
RUN ["gem", "install", "elasticsearch", "-v", "7.17.0"]
RUN ["gem", "install", "fluent-plugin-elasticsearch"]
USER fluent
