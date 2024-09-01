docker run --name postgres \
  -e POSTGRES_PASSWORD=yourpassword \
  -e POSTGRES_USER=youruser \
  -e POSTGRES_DB=yourdatabase \
  -p 5432:5432 \
  -d postgres
