create user if not exists'scout' identified by 'Pa$$word4' password expire never;
grant all on scouting.* to 'scout';
create user if not exists'tableau' identified by 'Pa$$word8' password expire never;
grant SELECT,EXECUTE,SHOW VIEW on scouting.* to 'tableau';