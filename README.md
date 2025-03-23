# NBA Statistics API

This project is a RESTful API for logging and retrieving NBA player and team statistics, built with Spring Boot, JDBI, and PostgreSQL.
Built for Skyhawk Security as part of the interview process as Senior BE home assignment.

## Running the Project

### Prerequisites

- Docker and Docker Compose

## Steps

1. Clone the repository.
2. Run the following command to start the services:

   ```bash
   docker-compose up --build
   ```

3. Access the API at [http://localhost:8080/api/stats](http://localhost:8080/api/stats).

## Examples

### Log Player Stats:

```bash
curl -X POST http://localhost:8080/api/stats/log -H "Content-Type: application/json" -d '{
  "gameId": 3,
  "seasonId": 2,
  "gameDate": "2025-03-23",
  "homeTeamId": 1,
  "awayTeamId": 3,
  "playerStatsRequests": [
    {
      "playerId": 1,
      "points": 30,
      "rebounds": 9,
      "assists": 5,
      "steals": 2,
      "blocks": 1,
      "fouls": 2,
      "turnovers": 1,
      "minutesPlayed": 38.0
    }
  ]
}'
```

### Get Player Averages:

```bash
curl http://localhost:8080/api/stats/player/1/season/1/averages
```

### Get Team Averages:

```bash
curl http://localhost:8080/api/stats/team/1/season/1/averages
```

---

## Architecture and Database Choice

### Thought Process
The focus was on creating a simple and functional API. The database schema was simplified by using static team affiliations for players, avoiding the complexity of tracking historical player movements.

### Scalability and High Availability

- **Horizontal Scaling:** Multiple backend instances behind a load balancer.
- **Database:** PostgreSQL with replication (e.g., multi-AZ on AWS RDS) for fault tolerance.
- **Concurrency:** Row-level locking ensures safe updates to player averages.

### Challenges and Trade-offs

- **Immediate Availability:** Synchronous updates to player averages ensure real-time data but may increase transaction time. Asynchronous updates via queues were considered but rejected due to delay risks.
- **Team Averages:** Computing on-the-fly avoids contention but may slow down under heavy load. Caching (e.g., Redis) could optimize this but wasn’t implemented to maintain simplicity and consistency.
- **Concurrency:** Player average updates are serialized per player, but team average queries scale with game volume.

### Challenges
- Maintaining data consistency with a simplified schema.
- Resolving JDBI parameter binding issues, which were fixed by using explicit binding.
- Implementing basic error handling with try-catch blocks due to limited time.

### Database:

PostgreSQL was chosen for its:

- **Reliability:** Robust support for structured data and ACID transactions.
- **Concurrency:** Efficient handling of concurrent writes with row-level locking.
- **Scalability:** Supports replication and partitioning for high throughput.

Alternatives like NoSQL (e.g., MongoDB) were considered but deemed less suitable due to the structured nature of the data and need for transactional consistency. In-memory databases (e.g., Redis) were ruled out due to persistence and size constraints.

### Backend:

- Java with Spring Boot for RESTful APIs, paired with JDBI for database interactions (no ORM).

### Deployment:

- Docker Compose for containerization, with considerations for AWS deployment.

---

## AWS Deployment

### Architecture:

- **RDS:** PostgreSQL instance for the database.
- **Elastic Beanstalk:** Hosts the Spring Boot application.
- **S3:** Optional storage for logs or backups.

### Steps:

1. Set up a PostgreSQL instance on AWS RDS.
2. Package the application as a JAR file.
3. Deploy the JAR to Elastic Beanstalk, configuring it to connect to the RDS instance.
4. Use CloudWatch for monitoring and logging.

---

## Areas for Improvement
- **Exception Handling:** The current try-catch blocks should be refactored to use `@ControllerAdvice` for centralized error management. This was left as-is due to time constraints, showcasing alternative approaches.
- **Code Consistency:** JDBI bindings vary between explicit and automatic due to a rushed implementation; these should be standardized in the future.