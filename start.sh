docker-compose down

docker build -t backend-files:latest .

docker-compose up --build --force-recreate --remove-orphans
