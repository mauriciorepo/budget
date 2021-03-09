CREATE SEQUENCE order_service_items_id_seq;
CREATE TABLE  public.order_service_items
(
    id bigint NOT NULL DEFAULT nextval('order_service_items_id_seq'::regclass),
    description character varying(255) COLLATE pg_catalog."default",
    num_item integer NOT NULL,
    quantity bigint,
    scope_title boolean NOT NULL,
    value double precision NOT NULL,
    orderservice_id bigint,
    CONSTRAINT order_service_items_pkey PRIMARY KEY (id),
    CONSTRAINT fkginxgskbxy5qi2347bqe42o03 FOREIGN KEY (orderservice_id)
        REFERENCES public.order_service (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
