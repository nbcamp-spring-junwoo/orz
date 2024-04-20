# 빌더 스테이지
FROM bellsoft/liberica-openjdk-alpine-musl:17 AS builder
COPY build/libs/OTT-0.0.1-SNAPSHOT.jar /app.jar

# 런타임 스테이지
FROM bellsoft/liberica-openjdk-alpine-musl:17

# 필요한 패키지 설치 및 AWS CLI, jq 설치
RUN apk add --no-cache python3 py3-pip groff less jq

# Python 가상 환경 설정
RUN python3 -m venv /venv
ENV PATH="/venv/bin:$PATH"

# AWS CLI 및 필요한 도구 설치
RUN pip install --upgrade pip && \
    pip install awscli

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
