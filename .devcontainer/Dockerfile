FROM mcr.microsoft.com/devcontainers/typescript-node:1-22-bookworm
EXPOSE 3000
EXPOSE 24678

# Install dos2unix, curl, iputils-ping
RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
    && apt-get -y install --no-install-recommends dos2unix curl iputils-ping