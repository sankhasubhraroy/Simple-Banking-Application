CREATE TABLE customer (
    ac_no INT NOT NULL AUTO_INCREMENT,
    cname VARCHAR(40) DEFAULT NULL,
    balance VARCHAR(40) DEFAULT NULL,
    pass_code INT DEFAULT NULL,
    PRIMARY KEY (ac_no),
    UNIQUE KEY cname_UNIQUE (cname)
);