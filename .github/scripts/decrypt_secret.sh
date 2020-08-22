#!/bin/sh

# 解密文件
gpg --quiet --batch --yes --decrypt --passphrase="$LARGE_SECRET_PASSPHRASE" --output $GITHUB_WORKSPACE/legado/app/gradle.properties $GITHUB_WORKSPACE/key/encrypt/gradle.properties.gpg
echo "Decrypt gradle.properties done"
gpg --quiet --batch --yes --decrypt --passphrase="$LARGE_SECRET_PASSPHRASE" --output $GITHUB_WORKSPACE/legado/app/key.jks $GITHUB_WORKSPACE/key/encrypt/key.jks.gpg
echo "Decrypt key.jks done"
