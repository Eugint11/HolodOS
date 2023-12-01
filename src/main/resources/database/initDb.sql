CREATE SEQUENCE good_id_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE IF NOT EXISTS public.good
(
    id bigint NOT NULL,
    name character varying(32) COLLATE pg_catalog."default" NOT NULL,
    units character varying(32) COLLATE pg_catalog."default" NOT NULL,
    qty double precision NOT NULL,
    description text COLLATE pg_catalog."default",
    grams double precision DEFAULT 0,
    scale double precision DEFAULT 0,
    date_of_purchase time with time zone,
    expiration_date time without time zone,
    CONSTRAINT good_pkey PRIMARY KEY (id)
);