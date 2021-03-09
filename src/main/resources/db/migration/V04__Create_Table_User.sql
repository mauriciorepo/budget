CREATE SEQUENCE users_id_seq;

CREATE TABLE public.users(
    id bigint NOT NULL DEFAULT nextval('users_id_seq'::regclass),
    user_name character varying(255) COLLATE pg_catalog."default",
    full_name character varying(255) COLLATE pg_catalog."default",
    account_non_expired boolean,
    account_non_locked boolean,
    credentials_non_expired  boolean,
    enabled boolean,
    password character varying(255) NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY(id)

);