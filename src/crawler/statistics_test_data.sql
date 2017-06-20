USE statistics;

INSERT INTO `persons` VALUES (1,'Путин'),(2,'Медведев'),(3,'Нава');

INSERT INTO `sites` VALUES (1,'lenta.ru'),(2,'meduza.io');

INSERT INTO `keywords` VALUES (1,'Путину',1),(2,'Путина',1),(3,'Навального',3),(4,'Навальный',3),(5,'Медведева',2),(6,'Медведеву',2);

INSERT INTO `pages` VALUES (1,'http://lenta.ru/news/putin',1,'2017-06-15 00:00:00',NULL),(2,'http://lenta.ru/news/medvedev',1,'2017-06-15 17:21:14',NULL),(3,'http://meduza.io/news/putin',2,'2017-06-15 17:21:40',NULL),(4,'http://meduza.io/news/navalniy',2,'2017-06-15 17:21:53',NULL);

INSERT INTO `personpagerank` VALUES (1,1,10),(1,3,10),(3,4,10),(2,2,2);
