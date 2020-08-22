name: NBReader

on:
  watch:
    types: [started]

jobs:
  build:
    runs-on: ubuntu-latest
    strategy:
      matrix:
        cflags: [ "master" ]

    steps:
      - name: Checkout this project
        uses: actions/checkout@v2
        with:
          path: actions

      - name: set up JDK 1.8
        uses: actions/setup-java@v1
        with:
          java-version: 1.8

      - name: Checkout android project
        uses: actions/checkout@v2
        with:
          repository: jushenziao/Cloud9PhotoView
          ref: ${{matrix.cflags}}
          fetch-depth: 1
          path: legado

      - name: Checkout private key
        uses: actions/checkout@v2
        with:
          repository: Celeter/secrets
          token: ${{ secrets.ACCESS_TOKEN }}
          path: key

      - name: Decrypt large secret
        run: bash actions/.github/scripts/decrypt_secret.sh
        env:
          LARGE_SECRET_PASSPHRASE: ${{ secrets.ANDROID_SECRET }}

     # - name: Fix build.gradle
     #   run: |
     #     echo "insert [ndkVersion '21.3.6528147']"
     #     sed -i "23a\    ndkVersion '21.3.6528147'" legado/app/build.gradle

      - name: Build with Gradle
        run: |
          cd legado
          chmod +x gradlew
          ./gradlew assembleRelease

      - name: upload apk
        uses: actions/upload-artifact@v2
        with:
          name: NBReader_${{matrix.cflags}}
          path: legado/app/build/outputs/apk/release/*.apk
          if-no-files-found: error
