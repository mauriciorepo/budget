CREATE TABLE public.users_permission(
  id_user bigint NOT NULL REFERENCES users (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
  id_permission bigint NOT NULL REFERENCES permission(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
  PRIMARY KEY(id_user,id_permission)

);