# 빌더 스테이지
FROM openjdk:17-alpine AS builder

# 소스 파일이나 빌드된 jar 파일을 컨테이너로 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 런타임 스테이지
FROM openjdk:17-alpine
WORKDIR /application

# 빌더 스테이지에서 빌드된 jar 파일을 런타임 스테이지로 복사
COPY --from=builder /app.jar .

# 환경 변수 설정을 위한 스크립트 추가
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

# 컨테이너가 시작할 때 실행할 스크립트 지정
ENTRYPOINT ["/entrypoint.sh"]
CMD ["java","-jar","app.jar"]
