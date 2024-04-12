#!/bin/sh

parameter_store_prefix="/"
export_statement=$(aws ssm get-parameters-by-path \
    --path "${parameter_store_prefix}" \
    --region ap-northeast-2 --recursive \
    --with-decryption \
    | jq -r '.Parameters[] | "export " + (.Name | gsub("^/";"") | gsub("/"; "_") | ascii_upcase) + "=\"" + .Value + "\""' \
    )

# Evaluate the statements to load them into the current shell session
eval $export_statement

# 기존 Docker CMD 실행
exec "$@"
