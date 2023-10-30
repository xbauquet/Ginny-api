FROM openjdk:19-alpine AS builder
LABEL org.opencontainers.image.authors="xavier.bauquet@gmail.com"

# Install depandenties for JPackage
RUN apk add --no-cache binutils

# Copy the jar
RUN mkdir -p /home/workdir/libs/
COPY build/libs/Ginny-api-0.0.0.jar /home/workdir/libs/
WORKDIR /home/workdir/

# Build executable app with JPackage
RUN jpackage -n app --input libs --main-jar Ginny-api-0.0.0.jar -t app-image

# -----------------------------------------------------------
FROM alpine:3.18.4

LABEL org.opencontainers.image.authors="xavier.bauquet@gmail.com"

ARG arg_version=0.0.0
ENV app.version=$arg_version

# Copy the executable app from the builder image
RUN mkdir -p /home/workdir/app/
COPY --from=builder /home/workdir/app/ /home/workdir/app/

# Run the app
WORKDIR /home/workdir/app/bin/
CMD ["./app"]
