CREATE SEQUENCE permission_id_seq;

CREATE TABLE public.permission(
    id bigint NOT NULL DEFAULT nextval('permission_id_seq'::regclass),
    description character varying(255),
    CONSTRAINT permission_pkey PRIMARY KEY(id)
);