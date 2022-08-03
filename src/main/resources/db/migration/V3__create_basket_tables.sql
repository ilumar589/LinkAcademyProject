CREATE TABLE basket
(
    id                UUID NOT NULL,
    buyer_id          UUID NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE basket_item
(
    id                UUID NOT NULL,
    basket_id         UUID NOT NULL,
    quantity BIGINT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT fk_basket FOREIGN KEY (basket_id) REFERENCES basket(id) ON DELETE CASCADE ON UPDATE CASCADE
);