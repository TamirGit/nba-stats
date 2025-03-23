CREATE TABLE seasons
(
    season_id   SERIAL PRIMARY KEY,
    season_name VARCHAR(255) NOT NULL,
    start_date  DATE,
    end_date    DATE
);

CREATE TABLE teams
(
    team_id   SERIAL PRIMARY KEY,
    team_name VARCHAR(255) NOT NULL
);

CREATE TABLE players
(
    player_id   SERIAL PRIMARY KEY,
    player_name VARCHAR(255)                   NOT NULL,
    team_id     INT REFERENCES teams (team_id) NOT NULL
);

CREATE TABLE games
(
    game_id      SERIAL PRIMARY KEY,
    season_id    INT  NOT NULL,
    game_date    DATE NOT NULL,
    home_team_id INT REFERENCES teams (team_id),
    away_team_id INT REFERENCES teams (team_id),
    FOREIGN KEY (season_id) REFERENCES seasons (season_id)
);

CREATE TABLE player_game_stats
(
    player_id      INT REFERENCES players (player_id),
    game_id        INT REFERENCES games (game_id),
    points         INT   NOT NULL CHECK (points >= 0),
    rebounds       INT   NOT NULL CHECK (rebounds >= 0),
    assists        INT   NOT NULL CHECK (assists >= 0),
    steals         INT   NOT NULL CHECK (steals >= 0),
    blocks         INT   NOT NULL CHECK (blocks >= 0),
    fouls          INT   NOT NULL CHECK (fouls >= 0 AND fouls <= 6),
    turnovers      INT   NOT NULL CHECK (turnovers >= 0),
    minutes_played FLOAT NOT NULL CHECK (minutes_played >= 0 AND minutes_played <= 48.0),
    PRIMARY KEY (player_id, game_id)
);

CREATE TABLE player_season_totals
(
    player_id            INT REFERENCES players (player_id),
    season_id            INT REFERENCES seasons (season_id),
    games_played         INT   NOT NULL CHECK (games_played > 0),
    total_points         INT   NOT NULL,
    total_rebounds       INT   NOT NULL,
    total_assists        INT   NOT NULL,
    total_steals         INT   NOT NULL,
    total_blocks         INT   NOT NULL,
    total_fouls          INT   NOT NULL,
    total_turnovers      INT   NOT NULL,
    total_minutes_played FLOAT NOT NULL,
    PRIMARY KEY (player_id, season_id)
);