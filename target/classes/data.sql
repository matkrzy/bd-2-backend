/*
    INSERT USER
*/
INSERT INTO user (id,first_name,last_name,email,password,role) VALUES (1000,'Michal','Krolewski','michal.krolewski@mail.com','$2a$12$03HJZA3gXJ2CHzELLJVTS.1fOTxl5dCPX74x0u8NuBc7mOtGAgvL2','ADMIN');
INSERT INTO user (id,first_name,last_name,email,password,role) VALUES (1001,'Piotr','Gorczyca','piotr.gorczyca@mail.com','$2a$12$03HJZA3gXJ2CHzELLJVTS.1fOTxl5dCPX74x0u8NuBc7mOtGAgvL2','USER');
INSERT INTO user (id,first_name,last_name,email,password,role) VALUES (1002,'Marta','Miler','marta.miler@mail.com','$2a$12$03HJZA3gXJ2CHzELLJVTS.1fOTxl5dCPX74x0u8NuBc7mOtGAgvL2','USER');
INSERT INTO user (id,first_name,last_name,email,password,role) VALUES (1003,'Piotr','Gazda','piotr.gazda@mail.com','$2a$12$03HJZA3gXJ2CHzELLJVTS.1fOTxl5dCPX74x0u8NuBc7mOtGAgvL2','USER');
INSERT INTO user (id,first_name,last_name,email,password,role) VALUES (1004,'Olaf','Kris','olaf.kris@mail.com','$2a$12$03HJZA3gXJ2CHzELLJVTS.1fOTxl5dCPX74x0u8NuBc7mOtGAgvL2','USER');
INSERT INTO user (id,first_name,last_name,email,password,role) VALUES (1005,'Mateusz','Krzyzanowski','mateusz.krzyzanowski@mail.com','$2a$12$03HJZA3gXJ2CHzELLJVTS.1fOTxl5dCPX74x0u8NuBc7mOtGAgvL2','USER');

/*
    INSERT PHOTO
*/
INSERT INTO photo (id,has_category,description,name,photo_state,share_state,upload_time,owner,path) VALUES (1001,1,'photo1_description','tlo.jpg',1,1,'18-04-21 10:34:09',1000,'michal.krolewski@mail.com\\tlo.jpg');
INSERT INTO photo (id,has_category,description,name,photo_state,share_state,upload_time,owner,path) VALUES (1002,1,'photo2_description','USC50FOC051B021001.jpg',1,1,'18-02-27 10:34:09',1000,'michal.krolewski@mail.com\\USC50FOC051B021001.jpg');
INSERT INTO photo (id,has_category,description,name,photo_state,share_state,upload_time,owner,path) VALUES (1003,1,'photo3_description','20171120_103445.jpg',1,0,'18-03-21 10:34:09',1001,'piotr.gorczyca@mail.com\\20171120_103445.jpg');
INSERT INTO photo (id,has_category,description,name,photo_state,share_state,upload_time,owner,path) VALUES (1004,1,'photo4_description','images.jpg',1,0,'18-04-21 10:34:09',1002,'marta.miler@mail.com\\images.jpg');
INSERT INTO photo (id,has_category,description,name,photo_state,share_state,upload_time,owner,path) VALUES (1005,1,'photo5_description','pexels-photo-247932.jpg',1,0,'18-01-12 10:34:09',1002,'marta.miler@mail.com\\pexels-photo-247932.jpg');
INSERT INTO photo (id,has_category,description,name,photo_state,share_state,upload_time,owner,path) VALUES (1006,1,'photo6_description','maxresdefault.jpg',1,0,'18-02-23 10:34:09',1003,'piotr.gazda@mail.com\\maxresdefault.jpg');
INSERT INTO photo (id,has_category,description,name,photo_state,share_state,upload_time,owner,path )VALUES (1007,1,'photo7_description','img_63351521.jpg',1,0,'18-04-16 10:34:09',1004,'olaf.kris@mail.com\\img_63351521.jpg');
INSERT INTO photo (id,has_category,description,name,photo_state,share_state,upload_time,owner,path) VALUES (1008,1,'photo8_description','pexels-photo-46710.jpeg',1,1,'18-03-05 10:34:09',1004,'olaf.kris@mail.com\\pexels-photo-46710.jpeg');
INSERT INTO photo (id,has_category,description,name,photo_state,share_state,upload_time,owner,path) VALUES (1009,1,'photo9_description','ocean.jpg',1,1,'18-04-11 10:34:09',1005,'mateusz.krzyzanowski@mail.com\\ocean.jpg');


/*
    INSERT CATEGORY
*/
INSERT INTO category (id,name,parent_category,user) VALUES (1001,'holidays',null,1000);
INSERT INTO category (id,name,parent_category,user) VALUES (1002,'Egypt',1001,1000);
INSERT INTO category (id,name,parent_category,user) VALUES (1008,'Italy',1001,1000);
INSERT INTO category (id,name,parent_category,user) VALUES (1009,'Poland',1001,1000);
INSERT INTO category (id,name,parent_category,user) VALUES (1010,'sea',1002,1000);
INSERT INTO category (id,name,parent_category,user) VALUES (1011,'school',null,1000);
INSERT INTO category (id,name,parent_category,user) VALUES (1012,'exams',1011,1000);
INSERT INTO category (id,name,parent_category,user) VALUES (1003,'sport',null,1002);
INSERT INTO category (id,name,parent_category,user) VALUES (1004,'flowers',null,1003);
INSERT INTO category (id,name,parent_category,user) VALUES (1005,'anime',null,1004);
INSERT INTO category (id,name,parent_category,user) VALUES (1006,'animals',null,1005);
INSERT INTO category (id,name,parent_category,user) VALUES (1007,'cars',null,1005);

/*
    INSERT PHOTO TO CATEGORY
*/
INSERT INTO photo_to_category (id,category,photo) VALUES (1001,1002,1001);
INSERT INTO photo_to_category (id,category,photo) VALUES (1003,1003,1003);
INSERT INTO photo_to_category (id,category,photo) VALUES (1004,1004,1004);
INSERT INTO photo_to_category (id,category,photo) VALUES (1005,1004,1005);
INSERT INTO photo_to_category (id,category,photo) VALUES (1007,1006,1007);
INSERT INTO photo_to_category (id,category,photo) VALUES (1008,1006,1008);
INSERT INTO photo_to_category (id,category,photo) VALUES (1009,1007,1009);
INSERT INTO photo_to_category (id,category,photo) VALUES (1010,1011,1002);
INSERT INTO photo_to_category (id,category,photo) VALUES (1011,1011,1001);


/*
    INSERT TAG
*/
INSERT INTO tag (id,name,photo,user) VALUES (1001,'wpolishboy',1007,1004);
INSERT INTO tag (id,name,photo,user) VALUES (1002,'wholidays',1007,1004);
INSERT INTO tag (id,name,photo,user) VALUES (1004,'wholidays',1001,1000);
INSERT INTO tag (id,name,photo,user) VALUES (1006,'wholidays',1002,1000);
INSERT INTO tag (id,name,photo,user) VALUES (1005,'wpolishboy',1001,1000);
INSERT INTO tag (id,name,photo,user) VALUES (1003,'sunrise',1005,1002);

/*
    INSERT SHARE
*/
INSERT INTO share (id,photo,user,owner) VALUES (1001,1002,1001,1000);
INSERT INTO share (id,photo,user,owner) VALUES (1003,1005,1005,1002);
INSERT INTO share (id,photo,user,owner) VALUES (1002,1006,1000,1003);
INSERT INTO share (id,photo,user,owner) VALUES (1004,1007,1000,1004);
INSERT INTO share (id,photo,user,owner) VALUES (1005,1008,1000,1004);
INSERT INTO share (id,photo,user,owner) VALUES (1006,1009,1000,1005);

/*
    INSERT RATE
*/
INSERT INTO rate (id,date,photo,user) VALUES (1001,'18-04-21 10:34:09',1001,1001);
INSERT INTO rate (id,date,photo,user) VALUES (1002,'18-03-12 10:34:09',1001,1005);
INSERT INTO rate (id,date,photo,user) VALUES (1003,'18-03-12 10:34:09',1002,1005);
INSERT INTO rate (id,date,photo,user) VALUES (1004,'18-03-12 10:34:09',1002,1001);
INSERT INTO rate (id,date,photo,user) VALUES (1005,'18-03-12 10:34:09',1004,1003);
INSERT INTO rate (id,date,photo,user) VALUES (1006,'18-03-12 10:34:09',1006,1005);

