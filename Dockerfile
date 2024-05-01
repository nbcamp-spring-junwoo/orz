# 빌더 스테이지
FROM openjdk:17-alpine AS builder
COPY build/libs/OTT-0.0.1-SNAPSHOT.jar /app.jar

# 런타임 스테이지
FROM openjdk:17-alpine

# 필요한 패키지 설치 및 AWS CLI, jq 설치
RUN apk add --no-cache python3 py3-pip groff less jq && \
    pip3 install --no-cache-dir --upgrade pip awscli

# 작업 디렉토리 설정
WORKDIR /application

# 빌더 스테이지에서 생성된 JAR 파일과 진입점 스크립트 복사
COPY --from=builder /app.jar /application/app.jar
COPY entrypoint.sh /entrypoint.sh

# 진입점 스크립트 실행 권한 부여
RUN chmod +x /entrypoint.sh

# 컨테이너 시작 시 실행할 명령 지정
ENTRYPOINT ["/entrypoint.sh"]
CMD ["java", "-jar", "app.jar"]
