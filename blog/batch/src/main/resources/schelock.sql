DROP TABLE IF EXISTS tbl_shedlock;

CREATE TABLE tbl_shedlock (
      name       VARCHAR(64)  NOT NULL,
      lock_until TIMESTAMP    NULL,
      locked_at  TIMESTAMP    NULL,
      locked_by  VARCHAR(255) NULL,
      CONSTRAINT PK_shedlock PRIMARY KEY (name)
);