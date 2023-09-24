
## Требования
- [JDK 17](https://adoptium.net/temurin/releases/?arch=x64&package=jdk)
- [Docker Compose](https://docs.docker.com/compose/install/) (входит в [Docker Desktop](https://www.docker.com/products/docker-desktop/))

Для сборки из командной строки также установите [Maven](https://maven.apache.org/download.cgi)
следуя [инструкции](https://maven.apache.org/install.html)

**! (для Windows не забудьте [добавить папку bin в PATH](https://stackoverflow.com/questions/44272416/how-to-add-a-folder-to-path-environment-variable-in-windows-10-with-screensho))**

Склонируйте репозиторий

```console
$ git clone https://github.com/mcdodik20008/pi-backend.git
```

## Запуск контейнера с БД

1. Откройте командную строку и перейдите в директорию с проектом
2. Выполните
```console
$ docker-compose up -d
```
## Запуск проекта

### Из IntelliJ IDEA
1. Установите [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/download/?section=windows)
2. Откройте проект в IDE
3. Выберите в тулбаре справа **Maven**
4. Выполните **package** из **Lifecycle**
5. Запустите проект (Shift+10)
6. Убедитесь в доступности **Swagger UI** по адресу http://localhost:777/pi-api/swagger-ui.html

### Из командной строки

1. Убедитесь, что **Java** доступна из командной строки
```console
$ java --version

openjdk 17.0.8.1 2023-08-24
OpenJDK Runtime Environment Temurin-17.0.8.1+1 (build 17.0.8.1+1)
OpenJDK 64-Bit Server VM Temurin-17.0.8.1+1 (build 17.0.8.1+1, mixed mode, sharing)

```
2. Убедитесь, что **Maven** доступен из командной строки
```console
$ mvn -v

Apache Maven 3.9.4 (dfbb324ad4a7c8fb0bf182e6d91b0ae20e3d2dd9)
Maven home: C:\tools\Maven
Java version: 17.0.8.1, vendor: Eclipse Adoptium, runtime: C:\tools\OpenJDK
Default locale: en_US, platform encoding: Cp1252
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"
```
3. Перейдите в директорию с проектом
4. Выполните
```console
$ mvn compile
```
5. Запустите полученный **.jar** файл 
```console
$ mvn exec:java -Dexec.mainClass=pibackend.PiBackendApp
```
6. Убедитесь в доступности Swagger UI по адресу http://localhost:777/pi-api/swagger-ui.html
