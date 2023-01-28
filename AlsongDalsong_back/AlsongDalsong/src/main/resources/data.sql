/* insert into users (user_id, created_date_time, modified_date_time, kakao_id, name, email, nickname, profile, introduce, role, point, sticker, withdraw) values
    (1, '2023-01-19 17:07:41.445', '2023-01-19 17:07:41.445', null, '김가경', '1234@gmail.com', '가경', 'http', '안녕하세요.', 'ROLE_USER', 0, 0, '0'),
    (2, '2023-01-19 17:08:41.445', '2023-01-19 17:08:41.445', null, '김가깅', '123@gmail.com', '가깅', 'http', '안녕하세요.', 'ROLE_USER', 0, 0, '0');

insert into post (post_id, created_date_time, modified_date_time, user_id, todo, category, who, old, date, what, content, link, importance, decision, reason) values
    (1, '2023-01-19 17:10:41.445', '2023-01-19 17:10:41.445', 1, '살까 말까', '패션', '여성', '20대', '2022년 11월', '신발', '나이키 신발을~', 'www', 3, '미정', null),
    (2, '2023-01-19 17:11:41.445', '2023-01-19 17:11:41.445', 1, '살까 말까', '패션', '여성', '20대', '2022년 11월', '신발', '아디다스 신발을~', 'www', 4, '미정', null),
    (3, '2023-01-19 17:12:41.445', '2023-01-19 17:12:41.445', 1, '살까 말까', '패션', '여성', '20대', '2022년 12월', '양말', '나이키 양말을~', 'www', 3, '미정', null),
    (4, '2023-01-19 17:13:41.445', '2023-01-19 17:13:41.445', 1, '살까 말까', '패션', '여성', '20대', '2022년 12월', '양말', '아디다스 양말을~', 'www', 4, '미정', null);

insert into photo (photo_id, created_date_time, modified_date_time, post_id, orig_photo_name, photo_name, photo_url) values
    (1, '2023-01-19 17:10:41.445', '2023-01-19 17:10:41.445', 1, 'star (2).png', 'c85ee049-61ab-43e9-83fc-aab0e5f6fec6.png', 'https://alsongdalsong.s3.ap-northeast-2.amazonaws.com/c85ee049-61ab-43e9-83fc-aab0e5f6fec6.png');

insert into comments (comment_id, created_date_time, modified_date_time, user_id, post_id, content) values
    (1, '2023-01-19 17:11:41.445', '2023-01-19 17:11:41.445', 1, 1, '꼭 사세요.'),
    (2, '2023-01-19 17:11:41.445', '2023-01-19 17:11:41.445', 2, 1, '저는 비추합니다.');

insert into likes (like_id, created_date_time, modified_date_time, user_id, comment_id) values
    (1, '2023-01-19 17:12:41.445', '2023-01-19 17:12:41.445', 1, 1);

insert into vote (vote_id, created_date_time, modified_date_time, user_id, post_id, vote) values
    (1, '2023-01-19 17:14:41.445', '2023-01-19 17:14:41.445', 2, 1, '1');

insert into scrap (scrap_id, created_date_time, modified_date_time, user_id, post_id) values
    (1, '2023-01-19 17:13:41.445', '2023-01-19 17:13:41.445', 1, 2);
*/