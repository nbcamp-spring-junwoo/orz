#!/bin/sh

# AWS Parameter Store에서 환경 변수 로드
parameters=$(aws ssm get-parameters-by-path --path "/" --recursive --with-decryption --query "Parameters[*].[Name,Value]" --output text)
for line in $parameters; do
    # 키와 값을 분리
    key=$(echo $line | awk '{print $1}')
    value=$(echo $line | awk '{print $2}')

    # 슬래시를 언더스코어로 변경하고, 대문자로 변환
    # 예: /mysql/url -> MYSQL_URL
    env_key=$(echo $key | sed 's|/|_|g' | tr '[:lower:]' '[:upper:]')

    # 환경변수로 설정
    eval export $env_key='$value'
done

# 기존 Docker CMD 실행
exec "$@"
