/*
    INSERT USER
*/
INSERT INTO user (id,creation_date,first_name,last_name,email,password,role,uuid) VALUES (1000,NOW(),'Michal','Krolewski','michal.krolewski@mail.com','$2a$12$03HJZA3gXJ2CHzELLJVTS.1fOTxl5dCPX74x0u8NuBc7mOtGAgvL2','USER','846e160c-673f-11e8-adc0-fa7ae01bbebc');
INSERT INTO user (id,creation_date,first_name,last_name,email,password,role,uuid) VALUES (1001,NOW(),'Piotr','Gorczyca','piotr.gorczyca@mail.com','$2a$12$03HJZA3gXJ2CHzELLJVTS.1fOTxl5dCPX74x0u8NuBc7mOtGAgvL2','USER','846e19a4-673f-11e8-adc0-fa7ae01bbebc');
INSERT INTO user (id,creation_date,first_name,last_name,email,password,role,uuid) VALUES (1002,NOW(),'Marta','Miler','marta.miler@mail.com','$2a$12$03HJZA3gXJ2CHzELLJVTS.1fOTxl5dCPX74x0u8NuBc7mOtGAgvL2','USER','846e1c10-673f-11e8-adc0-fa7ae01bbebc');
INSERT INTO user (id,creation_date,first_name,last_name,email,password,role,uuid) VALUES (1003,NOW(),'Piotr','Gazda','piotr.gazda@mail.com','$2a$12$03HJZA3gXJ2CHzELLJVTS.1fOTxl5dCPX74x0u8NuBc7mOtGAgvL2','USER','846e2160-673f-11e8-adc0-fa7ae01bbebc');
INSERT INTO user (id,creation_date,first_name,last_name,email,password,role,uuid) VALUES (1004,NOW(),'Olaf','Kryus','olaf.kryus@mail.com','$2a$12$03HJZA3gXJ2CHzELLJVTS.1fOTxl5dCPX74x0u8NuBc7mOtGAgvL2','USER','846e2426-673f-11e8-adc0-fa7ae01bbebc');
INSERT INTO user (id,creation_date,first_name,last_name,email,password,role,uuid) VALUES (1005,NOW(),'Mateusz','Krzyzanowski','mateusz.krzyzanowski@mail.com','$2a$12$03HJZA3gXJ2CHzELLJVTS.1fOTxl5dCPX74x0u8NuBc7mOtGAgvL2','USER','846e2642-673f-11e8-adc0-fa7ae01bbebc');

 /*
     INSERT PHOTO
 */
 INSERT INTO photo (id,description,name,state,visibility,creation_date,user_id,path, url) VALUES (1001,'photo1_description','tlo','ACTIVE','PRIVATE','18-04-21 10:34:09',1000,'846e160c-673f-11e8-adc0-fa7ae01bbebc/1.jpg','https://db-2-photos-archive.s3.amazonaws.com/846e160c-673f-11e8-adc0-fa7ae01bbebc/1.jpg');
 INSERT INTO photo (id,description,name,state,visibility,creation_date,user_id,path, url) VALUES (1002,'photo2_description','USC50FOC051B021001','ACTIVE','PRIVATE','18-02-27 10:34:09',1000,'846e160c-673f-11e8-adc0-fa7ae01bbebc/2.jpg','https://db-2-photos-archive.s3.amazonaws.com/846e160c-673f-11e8-adc0-fa7ae01bbebc/2.jpg');
 INSERT INTO photo (id,description,name,state,visibility,creation_date,user_id,path, url) VALUES (1003,'photo3_description','20171120_103445','ACTIVE','PUBLIC','18-03-21 10:34:09',1001,'846e19a4-673f-11e8-adc0-fa7ae01bbebc/3.jpg','https://db-2-photos-archive.s3.amazonaws.com/846e19a4-673f-11e8-adc0-fa7ae01bbebc/3.jpg');
 INSERT INTO photo (id,description,name,state,visibility,creation_date,user_id,path, url) VALUES (1004,'photo4_description','images','ACTIVE','PUBLIC','18-04-21 10:34:09',1002,'846e1c10-673f-11e8-adc0-fa7ae01bbebc/5.jpg','https://db-2-photos-archive.s3.amazonaws.com/846e1c10-673f-11e8-adc0-fa7ae01bbebc/5.jpg');
 INSERT INTO photo (id,description,name,state,visibility,creation_date,user_id,path, url) VALUES (1005,'photo5_description','pexels-photo-247932','ACTIVE','PUBLIC','18-01-12 10:34:09',1002,'846e1c10-673f-11e8-adc0-fa7ae01bbebc/6.jpg','https://db-2-photos-archive.s3.amazonaws.com/846e1c10-673f-11e8-adc0-fa7ae01bbebc/6.jpg');
 INSERT INTO photo (id,description,name,state,visibility,creation_date,user_id,path, url) VALUES (1006,'photo6_description','maxresdefault','ACTIVE','PUBLIC','18-02-23 10:34:09',1003,'846e2160-673f-11e8-adc0-fa7ae01bbebc/7.jpg','https://db-2-photos-archive.s3.amazonaws.com/846e2160-673f-11e8-adc0-fa7ae01bbebc/7.jpg');
 INSERT INTO photo (id,description,name,state,visibility,creation_date,user_id,path, url) VALUES (1007,'photo7_description','img_63351521','ACTIVE','PUBLIC','18-04-16 10:34:09',1004,'846e2426-673f-11e8-adc0-fa7ae01bbebc/9.jpg','https://db-2-photos-archive.s3.amazonaws.com/846e2426-673f-11e8-adc0-fa7ae01bbebc/9.jpg');
 INSERT INTO photo (id,description,name,state,visibility,creation_date,user_id,path, url) VALUES (1008,'photo8_description','pexels-photo-46710','ACTIVE','PRIVATE','18-03-05 10:34:09',1004,'846e2426-673f-11e8-adc0-fa7ae01bbebc/10.jpg','https://db-2-photos-archive.s3.amazonaws.com/846e2426-673f-11e8-adc0-fa7ae01bbebc/10.jpg');
 INSERT INTO photo (id,description,name,state,visibility,creation_date,user_id,path, url) VALUES (1009,'photo9_description','ocean','ACTIVE','PRIVATE','18-04-11 10:34:09',1005,'846e2642-673f-11e8-adc0-fa7ae01bbebc/11.jpg','https://db-2-photos-archive.s3.amazonaws.com/846e2642-673f-11e8-adc0-fa7ae01bbebc/11.jpg');
 INSERT INTO photo (id,description,name,state,visibility,creation_date,user_id,path, url) VALUES (1010,'photo9_description','test with EXIF','ACTIVE','PRIVATE','18-04-11 10:34:09',1005,'846e2642-673f-11e8-adc0-fa7ae01bbebc/13.jpg','https://db-2-photos-archive.s3.amazonaws.com/846e2642-673f-11e8-adc0-fa7ae01bbebc/13.jpg');


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
 INSERT INTO category (id,creation_date,name,parent_id,user_id) VALUES (1006,NOW(),'animals',null,1004);
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
 INSERT INTO tag (name,creation_date) VALUES ('wpolishboy',NOW());
 INSERT INTO tag (name,creation_date) VALUES ('wholidays',NOW());
 INSERT INTO tag (name,creation_date) VALUES ('sunrise',NOW());

 /*
     INSERT PHOTO TO TAG
 */
 INSERT INTO photo_to_tag (photo_id,tag_name) VALUES (1007,'wpolishboy');
 INSERT INTO photo_to_tag (photo_id,tag_name) VALUES (1007,'wholidays');
 INSERT INTO photo_to_tag (photo_id,tag_name) VALUES (1001,'wholidays');
 INSERT INTO photo_to_tag (photo_id,tag_name) VALUES (1002,'wholidays');
 INSERT INTO photo_to_tag (photo_id,tag_name) VALUES (1001,'wpolishboy');
 INSERT INTO photo_to_tag (photo_id,tag_name) VALUES (1005,'sunrise');

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
 INSERT INTO `like` (id,creation_date,photo_id,user_id) VALUES (1001,'2018-04-21 10:34:09',1001,1001);
 INSERT INTO `like` (id,creation_date,photo_id,user_id) VALUES (1002,'2018-03-12 10:34:09',1001,1005);
 INSERT INTO `like` (id,creation_date,photo_id,user_id) VALUES (1003,'2018-03-12 10:34:09',1002,1005);
 INSERT INTO `like` (id,creation_date,photo_id,user_id) VALUES (1004,'2018-03-12 10:34:09',1002,1001);
 INSERT INTO `like` (id,creation_date,photo_id,user_id) VALUES (1005,'2018-03-12 10:34:09',1004,1003);
 INSERT INTO `like` (id,creation_date,photo_id,user_id) VALUES (1006,'2018-03-12 10:34:09',1006,1005);

