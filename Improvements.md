# Improvements

**Business logic:**

1. Save meter value in session, not in connector. It will provide statistic per client (not total used kWh in connector)

**Business and prod environment:**

1. Time should save in client zone or server zone? (business needs)
2. PostgresSQL more applicable for production environment rather then h2
3. Depends on tables size in database, add indexes on most selective table columns

**Technical debt:**

1. Process If-Modified-Since header to decrease session list size
2. e2e tests
3. Create logout button in swagger-ui page to switch users
