/*
    INSERT USER
*/
INSERT INTO user (id,creation_date,first_name,last_name,email,password,role) VALUES (1000,NOW(),'Michal','Krolewski','michal.krolewski@mail.com','$2a$12$03HJZA3gXJ2CHzELLJVTS.1fOTxl5dCPX74x0u8NuBc7mOtGAgvL2',0);
INSERT INTO user (id,creation_date,first_name,last_name,email,password,role) VALUES (1001,NOW(),'Piotr','Gorczyca','piotr.gorczyca@mail.com','$2a$12$03HJZA3gXJ2CHzELLJVTS.1fOTxl5dCPX74x0u8NuBc7mOtGAgvL2',0);
INSERT INTO user (id,creation_date,first_name,last_name,email,password,role) VALUES (1002,NOW(),'Marta','Miler','marta.miler@mail.com','$2a$12$03HJZA3gXJ2CHzELLJVTS.1fOTxl5dCPX74x0u8NuBc7mOtGAgvL2',0);
INSERT INTO user (id,creation_date,first_name,last_name,email,password,role) VALUES (1003,NOW(),'Piotr','Gazda','piotr.gazda@mail.com','$2a$12$03HJZA3gXJ2CHzELLJVTS.1fOTxl5dCPX74x0u8NuBc7mOtGAgvL2',0);
INSERT INTO user (id,creation_date,first_name,last_name,email,password,role) VALUES (1004,NOW(),'Olaf','Kryus','olaf.kryus@mail.com','$2a$12$03HJZA3gXJ2CHzELLJVTS.1fOTxl5dCPX74x0u8NuBc7mOtGAgvL2',0);
INSERT INTO user (id,creation_date,first_name,last_name,email,password,role) VALUES (1005,NOW(),'Mateusz','Krzyzanowski','mateusz.krzyzanowski@mail.com','$2a$12$03HJZA3gXJ2CHzELLJVTS.1fOTxl5dCPX74x0u8NuBc7mOtGAgvL2',0);

 /*
     INSERT PHOTO
 */
 INSERT INTO photo (id,description,name,state,visibility,creation_date,user_id,path) VALUES (1001,'photo1_description','tlo.jpg',1,1,'18-04-21 10:34:09',1000,'michal.krolewski@mail.com\\tlo.jpg');
 INSERT INTO photo (id,description,name,state,visibility,creation_date,user_id,path) VALUES (1002,'photo2_description','USC50FOC051B021001.jpg',1,1,'18-02-27 10:34:09',1000,'michal.krolewski@mail.com\\USC50FOC051B021001.jpg');
 INSERT INTO photo (id,description,name,state,visibility,creation_date,user_id,path) VALUES (1003,'photo3_description','20171120_103445.jpg',1,0,'18-03-21 10:34:09',1001,'piotr.gorczyca@mail.com\\20171120_103445.jpg');
 INSERT INTO photo (id,description,name,state,visibility,creation_date,user_id,path) VALUES (1004,'photo4_description','images.jpg',1,0,'18-04-21 10:34:09',1002,'marta.miler@mail.com\\images.jpg');
 INSERT INTO photo (id,description,name,state,visibility,creation_date,user_id,path) VALUES (1005,'photo5_description','pexels-photo-247932.jpg',1,0,'18-01-12 10:34:09',1002,'marta.miler@mail.com\\pexels-photo-247932.jpg');
 INSERT INTO photo (id,description,name,state,visibility,creation_date,user_id,path) VALUES (1006,'photo6_description','maxresdefault.jpg',1,0,'18-02-23 10:34:09',1003,'piotr.gazda@mail.com\\maxresdefault.jpg');
 INSERT INTO photo (id,description,name,state,visibility,creation_date,user_id,path) VALUES (1007,'photo7_description','img_63351521.jpg',1,0,'18-04-16 10:34:09',1004,'olaf.kris@mail.com\\img_63351521.jpg');
 INSERT INTO photo (id,description,name,state,visibility,creation_date,user_id,path) VALUES (1008,'photo8_description','pexels-photo-46710.jpeg',1,1,'18-03-05 10:34:09',1004,'olaf.kris@mail.com\\pexels-photo-46710.jpeg');
 INSERT INTO photo (id,description,name,state,visibility,creation_date,user_id,path) VALUES (1009,'photo9_description','ocean.jpg',1,1,'18-04-11 10:34:09',1005,'mateusz.krzyzanowski@mail.com\\ocean.jpg');


 /*
     INSERT CATEGORY
 */
 INSERT INTO category (id,creation_date,name,parent_id,user_id) VALUES (1001,NOW(),'holidays',null,1000);
 INSERT INTO category (id,creation_date,name,parent_id,user_id) VALUES (1002,NOW(),'Egypt',1001,1000);
 INSERT INTO category (id,creation_date,name,parent_id,user_id) VALUES (1008,NOW(),'Italy',1001,1000);
 INSERT INTO category (id,creation_date,name,parent_id,user_id) VALUES (1009,NOW(),'Poland',1001,1000);
 INSERT INTO category (id,creation_date,name,parent_id,user_id) VALUES (1010,NOW(),'sea',1002,1000);
 INSERT INTO category (id,creation_date,name,parent_id,user_id) VALUES (1011,NOW(),'school',null,1000);
 INSERT INTO category (id,creation_date,name,parent_id,user_id) VALUES (1012,NOW(),'exams',1011,1000);
 INSERT INTO category (id,creation_date,name,parent_id,user_id) VALUES (1003,NOW(),'sport',null,1002);
 INSERT INTO category (id,creation_date,name,parent_id,user_id) VALUES (1004,NOW(),'flowers',null,1003);
 INSERT INTO category (id,creation_date,name,parent_id,user_id) VALUES (1005,NOW(),'anime',null,1004);
 INSERT INTO category (id,creation_date,name,parent_id,user_id) VALUES (1006,NOW(),'animals',null,1005);
 INSERT INTO category (id,creation_date,name,parent_id,user_id) VALUES (1007,NOW(),'cars',null,1005);

 /*
     INSERT PHOTO TO CATEGORY
 */
 INSERT INTO photo_to_category (category_id,photo_id) VALUES (1002,1001);
 INSERT INTO photo_to_category (category_id,photo_id) VALUES (1003,1003);
 INSERT INTO photo_to_category (category_id,photo_id) VALUES (1004,1004);
 INSERT INTO photo_to_category (category_id,photo_id) VALUES (1004,1005);
 INSERT INTO photo_to_category (category_id,photo_id) VALUES (1006,1007);
 INSERT INTO photo_to_category (category_id,photo_id) VALUES (1006,1008);
 INSERT INTO photo_to_category (category_id,photo_id) VALUES (1007,1009);
 INSERT INTO photo_to_category (category_id,photo_id) VALUES (1011,1002);
 INSERT INTO photo_to_category (category_id,photo_id) VALUES (1011,1001);

 /*
     INSERT TAG
 */
 INSERT INTO tag (id,creation_date,name,photo_id) VALUES (1001,NOW(),'wpolishboy',1007);
 INSERT INTO tag (id,creation_date,name,photo_id) VALUES (1002,NOW(),'wholidays',1007);
 INSERT INTO tag (id,creation_date,name,photo_id) VALUES (1004,NOW(),'wholidays',1001);
 INSERT INTO tag (id,creation_date,name,photo_id) VALUES (1006,NOW(),'wholidays',1002);
 INSERT INTO tag (id,creation_date,name,photo_id) VALUES (1005,NOW(),'wpolishboy',1001);
 INSERT INTO tag (id,creation_date,name,photo_id) VALUES (1003,NOW(),'sunrise',1005);

 /*
     INSERT SHARE
 */
 INSERT INTO share (id,creation_date,photo_id,user_id) VALUES (1001,NOW(),1002,1001);
 INSERT INTO share (id,creation_date,photo_id,user_id) VALUES (1003,NOW(),1005,1005);
 INSERT INTO share (id,creation_date,photo_id,user_id) VALUES (1002,NOW(),1006,1000);
 INSERT INTO share (id,creation_date,photo_id,user_id) VALUES (1004,NOW(),1007,1000);
 INSERT INTO share (id,creation_date,photo_id,user_id) VALUES (1005,NOW(),1008,1000);
 INSERT INTO share (id,creation_date,photo_id,user_id) VALUES (1006,NOW(),1009,1000);

 /*
     INSERT RATE
 */
 INSERT INTO rate (id,creation_date,photo_id,user_id,value) VALUES (1001,'2018-04-21 10:34:09',1001,1001,5);
 INSERT INTO rate (id,creation_date,photo_id,user_id,value) VALUES (1002,'2018-03-12 10:34:09',1001,1005,5);
 INSERT INTO rate (id,creation_date,photo_id,user_id,value) VALUES (1003,'2018-03-12 10:34:09',1002,1005,5);
 INSERT INTO rate (id,creation_date,photo_id,user_id,value) VALUES (1004,'2018-03-12 10:34:09',1002,1001,5);
 INSERT INTO rate (id,creation_date,photo_id,user_id,value) VALUES (1005,'2018-03-12 10:34:09',1004,1003,5);
 INSERT INTO rate (id,creation_date,photo_id,user_id,value) VALUES (1006,'2018-03-12 10:34:09',1006,1005,5);

