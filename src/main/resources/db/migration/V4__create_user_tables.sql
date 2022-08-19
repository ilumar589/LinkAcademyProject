CREATE TABLE role
(
    id                UUID NOT NULL,
    commerce_role     VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE commerce_user
(
    id                UUID NOT NULL,
    email             VARCHAR(255) NOT NULL,
    password          VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_email UNIQUE (email)
);

CREATE TABLE user_roles
(
    user_id           UUID NOT NULL,
    role_id           UUID NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES commerce_user(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE ON UPDATE CASCADE
);