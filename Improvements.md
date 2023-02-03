# Improvements

**Business and prod environment:**

1. Time should save in client zone or server zone? (business needs)
2. If services will grow, split service logic from session service to smaller parts
3. Add domain models when needed, and mapping from persistence to domain and back
4. postgres more applicable for prod environment rather then h2
5. Depends on database size, add indexes on most selective table columns

**Technical debt:**

1. Create some logout button in swagger-ui page to switch users?
2. e2e tests
3. > expand monitoring using prometheus
