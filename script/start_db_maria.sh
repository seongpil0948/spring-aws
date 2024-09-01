#brew services start mariadb
docker run --rm -p 3306:3306 \
  --name 'six_plus' \
  --env MARIADB_USER=root \
  --env MARIADB_PASSWORD=1234qwer!! \
  --env MARIADB_DATABASE=six_plus \
  --env MARIADB_ROOT_PASSWORD=1234qwer!! \
  mariadb:latest
