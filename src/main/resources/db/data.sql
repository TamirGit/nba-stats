INSERT INTO seasons (season_name, start_date, end_date)
VALUES ('2023-2024', '2023-10-01', '2024-04-30'),
       ('2024-2025', '2024-10-01', '2025-04-30');

INSERT INTO teams (team_name)
VALUES ('Boston Celtics'),
       ('Los Angeles Lakers'),
       ('Golden State Warriors');

INSERT INTO players (player_name, team_id)
VALUES ('Jayson Tatum', 1),  -- Celtics
       ('Jaylen Brown', 1),  -- Celtics
       ('LeBron James', 2),  -- Lakers
       ('Anthony Davis', 2), -- Lakers
       ('Stephen Curry', 3); -- Warriors

INSERT INTO games (season_id, game_date, home_team_id, away_team_id)
VALUES (1, '2023-10-25', 1, 2), -- Celtics vs Lakers in 2023-2024
       (1, '2023-10-26', 3, 1); -- Warriors vs Celtics in 2023-2024

INSERT INTO player_game_stats (player_id, game_id, points, rebounds, assists, steals, blocks, fouls, turnovers,
                               minutes_played)
VALUES (1, 1, 28, 8, 4, 2, 1, 3, 2, 36.5),  -- Tatum in Celtics vs Lakers
       (2, 1, 18, 5, 3, 1, 0, 2, 1, 32.0),  -- Brown in Celtics vs Lakers
       (3, 1, 25, 10, 7, 1, 2, 2, 3, 38.0), -- LeBron in Celtics vs Lakers
       (4, 1, 22, 7, 5, 0, 3, 4, 2, 35.0),  -- Davis in Celtics vs Lakers
       (1, 2, 22, 7, 5, 2, 1, 3, 1, 37.0),  -- Tatum in Warriors vs Celtics
       (5, 2, 33, 4, 6, 3, 0, 1, 2, 35.0); -- Curry in Warriors vs Celtics

INSERT INTO player_season_totals (player_id, season_id, games_played, total_points, total_rebounds, total_assists,
                                  total_steals, total_blocks, total_fouls, total_turnovers, total_minutes_played)
VALUES (1, 1, 2, 50, 15, 9, 4, 2, 6, 3, 25), -- Tatum
       (2, 1, 1, 18, 5, 3, 1, 0, 2, 1, 32.0),  -- Brown
       (3, 1, 1, 25, 10, 7, 1, 2, 2, 3, 38.0), -- LeBron
       (4, 1, 1, 22, 7, 5, 0, 3, 4, 2, 35.0),  -- Davis
       (5, 1, 1, 33, 4, 6, 3, 0, 1, 2, 35.0); -- Curry