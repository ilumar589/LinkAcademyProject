CREATE TABLE product
(
    id                UUID NOT NULL,
    name              VARCHAR(255) NOT NULL,
    description       VARCHAR(255),
    price             BIGINT NOT NULL,
    picture_url       VARCHAR(255),
    type              VARCHAR(255) NOT NULL,
    brand             VARCHAR(255) NOT NULL,
    quantity_in_stock BIGINT NOT NULL,
    PRIMARY KEY (id)
);