#!/bin/bash
set -e

apt-get update -y
apt-get install -y ca-certificates curl gnupg lsb-release git

curl -fsSL https://download.docker.com/linux/ubuntu/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] \
  https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" \
  | tee /etc/apt/sources.list.d/docker.list > /dev/null

apt-get update -y
apt-get install -y docker-ce docker-ce-cli containerd.io

systemctl enable --now docker
usermod -aG docker ubuntu

mkdir -p /home/ubuntu/app
chown ubuntu:ubuntu /home/ubuntu/app
cd /home/ubuntu/app

DB_PROD_PASSWORD=$(aws ssm get-parameter --name "/myth-of-fact/db-password" --with-decryption --query "Parameter.Value" --output text)
JWT_SECRET=$(aws ssm get-parameter --name "/myth-of-fact/jwt-secret" --with-decryption --query "Parameter.Value" --output text)
APP_PASSWORD=$(aws ssm get-parameter --name "/myth-of-fact/app-password" --with-decryption --query "Parameter.Value" --output text)

cat > .env << EOF
SPRING_PROFILES_ACTIVE=prod
DB_PROD_USERNAME=postgres
DB_PROD_PASSWORD=$DB_PROD_PASSWORD
DB_STRING=jdbc:postgresql://db.goazwmbvraoengmetdpy.supabase.co:5432/postgres?sslmode=require&preparedStatementCacheQueries=0
JWT_SECRET=$JWT_SECRET
APP_PASSWORD=$APP_PASSWORD
EOF

cat > Dockerfile << 'EOF'
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline -B
COPY src src
RUN ./mvnw clean package -DskipTests -Pprod

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
EOF

cat > deploy.sh << 'EOF'
#!/bin/bash
set -e

cd /home/ubuntu/app

if [ -d "MythOrFactLGBTQIA" ]; then
  cd MythOrFactLGBTQIA
  git pull origin main
  cd ..
else
  git clone https://github.com/Veras-D/MythOrFactLGBTQIA.git
fi

cp -r MythOrFactLGBTQIA/Backend/* .

docker stop myth-or-fact || true
docker rm myth-or-fact || true

docker image prune -f

docker build -t myth-or-fact .
docker run -d --name myth-or-fact \
  --env-file .env \
  -p 80:8080 \
  myth-or-fact

echo "Deploy completo: http://$(curl -s http://169.254.169.254/latest/meta-data/public-ipv4)"
EOF

chmod +x deploy.sh
chown -R ubuntu:ubuntu /home/ubuntu/app

sudo -u ubuntu /home/ubuntu/app/deploy.sh
