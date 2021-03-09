CREATE SEQUENCE company_id_seq;
CREATE TABLE  public.company
(
    id bigint NOT NULL DEFAULT nextval('company_id_seq'::regclass),
    account character varying(255) COLLATE pg_catalog."default",
    cellphone character varying(255) COLLATE pg_catalog."default",
    contact_name character varying(255) COLLATE pg_catalog."default",
    country character varying(255) COLLATE pg_catalog."default",
    email character varying(255) COLLATE pg_catalog."default",
    localization character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    neighborhood character varying(255) COLLATE pg_catalog."default",
    registration_date character varying(255) COLLATE pg_catalog."default",
    situation boolean NOT NULL,
    state_abbrev character varying(255) COLLATE pg_catalog."default",
    state_registration character varying(255) COLLATE pg_catalog."default",
    telephone character varying(255) COLLATE pg_catalog."default",
    telephone2 character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT company_pkey PRIMARY KEY (id)
)