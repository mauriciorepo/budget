
CREATE SEQUENCE order_service_id_seq;
CREATE TABLE public.order_service
(
    id bigint NOT NULL DEFAULT nextval('order_service_id_seq'::regclass),
    annotation character varying(255) COLLATE pg_catalog."default",
    description character varying(255) COLLATE pg_catalog."default",
    indorsement boolean NOT NULL,
    modified date,
    order_number character varying(255) COLLATE pg_catalog."default",
    registration_date date NOT NULL,
    status character varying(255) COLLATE pg_catalog."default" NOT NULL,
    title character varying(255) COLLATE pg_catalog."default" NOT NULL,
    id_company bigint,
    CONSTRAINT order_service_pkey PRIMARY KEY (id),
    CONSTRAINT fk68egtsxnh7ubsfv7itfo00hw FOREIGN KEY (id_company)
        REFERENCES public.company (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)