insert into member(offer_level, oauth_id, oauth_type, nickname, profile_image_url, created_date)
values (1, 2036674018, 'KAKAO', '빼어난 가전제품 3호', 'http://k.kakaocdn.net/dn/ia9Y5/btrXSxAKtaj/1yZZWN0bhuA627JBHiN2ck/img_640x640.jpg', now());

insert into member(offer_level, oauth_id, oauth_type, nickname, profile_image_url, created_date)
values (1, 2026674018, 'KAKAO', '지친 어썸오 1호', 'http://k.kakaocdn.net/dn/ia9Y5/btrXSxAKtaj/1yZZWN0bhuA627JBHiN2ck/img_640x640.jpg', now());

insert into member(offer_level, oauth_id, oauth_type, nickname, profile_image_url, created_date)
values (1, 3221448503, 'KAKAO', '빼어난 다리미 64호', null, now());

insert into member(offer_level, oauth_id, oauth_type, nickname, profile_image_url, created_date)
values (1, 3158623659, 'KAKAO', '빼어난 다리미 11호', null, now());

insert into category(code, name, image_url) values ('MEN_FASHION', '남성패션/잡화', 'https://offer-be.kro.kr/images/7e0099c4-e75d-4636-88c1-d412676bb0b9.png');
insert into category(code, name, image_url) values ('WOMEN_FASHION', '여성패션/잡화', 'https://offer-be.kro.kr/images/ef4e0e85-ef6b-452f-9968-dc57db57a76b.png');
insert into category(code, name, image_url) values ('GAME', '게임', 'https://offer-be.kro.kr/images/c688952b-f585-4df1-8187-d836c0280492.png');
insert into category(code, name, image_url) values ('SPORTS', '스포츠/레저', 'https://offer-be.kro.kr/images/e287d3d3-209d-47d8-a5bd-ebde682d883e.png');
insert into category(code, name, image_url) values ('TOY', '장난감/취미', 'https://offer-be.kro.kr/images/e218c2a1-1bd7-442f-b0f6-195a1736c62b.png');
insert into category(code, name, image_url) values ('DIGITAL', '디지털기기', 'https://offer-be.kro.kr/images/a1126089-ec83-4cb2-8599-d507b0978749.png');
insert into category(code, name, image_url) values ('CAR', '자동차/공구', 'https://offer-be.kro.kr/images/5920f618-de40-45da-8731-a9e4f2c9a757.png');
insert into category(code, name, image_url) values ('APPLIANCE', '생활가전', 'https://offer-be.kro.kr/images/bccb8f2e-bf2d-4bcb-83ec-e4ea37c45cf7.png');
insert into category(code, name, image_url) values ('FURNITURE', '가구/인테리어', 'https://offer-be.kro.kr/images/44bd7ce9-6178-41a5-a5ec-a6ec84078c97.png');
insert into category(code, name, image_url) values ('BOOKS', '도서/티켓/음반', 'https://offer-be.kro.kr/images/0831ed40-5e99-47eb-9435-d41644f973e6.png');
insert into category(code, name, image_url) values ('PET', '반려동물용품', 'https://offer-be.kro.kr/images/fb0d656b-5063-4125-8907-3c0b395b0975.png');
insert into category(code, name, image_url) values ('OTHER', '기타 중고물품', 'https://offer-be.kro.kr/images/8d1db7d6-d478-4ad2-99be-c952abb201f9.png');

insert into sort_group(sort_type) values ('POST');
insert into sort_group(sort_type) values ('OFFER');

insert into sort_item(sort_group_id, name, code) values (1, '높은 가격순', 'PRICE_DESC');
insert into sort_item(sort_group_id, name, code) values (1, '낮은 가격순', 'PRICE_ASC');
insert into sort_item(sort_group_id, name, code) values (1, '최신순', 'CREATED_DATE_DESC');
insert into sort_item(sort_group_id, name, code) values (2, '높은 가격순', 'PRICE_DESC');
insert into sort_item(sort_group_id, name, code) values (2, '낮은 가격순', 'PRICE_ASC');
insert into sort_item(sort_group_id, name, code) values (2, '최신순', 'CREATED_DATE_DESC');


insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (30000, now(), now(), 1, 'MEN_FASHION', '남성 청바지 팔아요~~', '동작구 사당동', 'SECONDHAND', 'https://picsum.photos/200', '남성 청바지 팝니다.', 'FACE_TO_FACE', 'SELLING');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (11000, now(), now(), 1, 'MEN_FASHION', '후드티 팔아요~~', '동작구 사당동', 'SECONDHAND', 'https://picsum.photos/200', '후드티 판매', 'FACE_TO_FACE', 'SELLING');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (12000, now(), now(), 1, 'MEN_FASHION', '반팔티 팔아요~~', '동작구 사당동', 'NEW', 'https://picsum.photos/200', '반팔티 판매', 'SHIPPING', 'SELLING');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (13000, now(), now(), 1, 'GAME', '젤다 게임팩 판매중', '동작구 사당동', 'NEW', 'https://picsum.photos/200', '젤다 게임팩', 'FACE_TO_FACE', 'SELLING');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (14000, now(), now(), 1, 'GAME', '닌텐도 스위치 판매중', '동작구 사당동', 'NEW', 'https://picsum.photos/200', '닌텐도 스위치 팝니다.', 'SHIPPING', 'SELLING');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (15000, now(), now(), 1, 'GAME', '닌텐도 스위치 판매중', '동작구 사당동', 'NEW', 'https://picsum.photos/200', '닌텐도 스위치 팝니다.', 'SHIPPING', 'SELLING');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (16000, now(), now(), 1, 'GAME', '닌텐도 스위치 판매중', '동작구 사당동', 'NEW', 'https://picsum.photos/200', '닌텐도 스위치 팝니다.', 'SHIPPING', 'SELLING');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (17000, now(), now(), 1, 'GAME', '닌텐도 스위치 판매중', '동작구 사당동', 'NEW', 'https://picsum.photos/200', '닌텐도 스위치 팝니다.', 'SHIPPING', 'SELLING');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (18000, now(), now(), 1, 'GAME', '닌텐도 스위치 판매중', '동작구 사당동', 'NEW', 'https://picsum.photos/200', '닌텐도 스위치 팝니다.', 'SHIPPING', 'SELLING');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (19000, now(), now(), 1, 'GAME', '닌텐도 스위치 판매중', '동작구 사당동', 'NEW', 'https://picsum.photos/200', '닌텐도 스위치 팝니다.', 'SHIPPING', 'SELLING');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (20000, now(), now(), 1, 'GAME', '닌텐도 스위치 판매중', '동작구 사당동', 'NEW', 'https://picsum.photos/200', '닌텐도 스위치 팝니다.', 'SHIPPING', 'SELLING');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (21000, now(), now(), 1, 'GAME', '닌텐도 스위치 판매중', '동작구 사당동', 'NEW', 'https://picsum.photos/200', '닌텐도 스위치 팝니다.', 'SHIPPING', 'SELLING');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (22000, now(), now(), 1, 'GAME', '닌텐도 스위치 판매중', '동작구 사당동', 'NEW', 'https://picsum.photos/200', '닌텐도 스위치 팝니다.', 'SHIPPING', 'SELLING');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (23000, now(), now(), 1, 'GAME', '닌텐도 스위치 판매중', '동작구 사당동', 'NEW', 'https://picsum.photos/200', '닌텐도 스위치 팝니다.', 'SHIPPING', 'SELLING');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (24000, now(), now(), 1, 'GAME', '닌텐도 스위치 판매중', '동작구 사당동', 'NEW', 'https://picsum.photos/200', '닌텐도 스위치 팝니다.', 'SHIPPING', 'SELLING');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (25000, now(), now(), 1, 'GAME', '닌텐도 스위치 판매중', '동작구 사당동', 'NEW', 'https://picsum.photos/200', '닌텐도 스위치 팝니다.', 'SHIPPING', 'SELLING');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (26000, now(), now(), 1, 'GAME', '닌텐도 스위치 판매중', '동작구 사당동', 'NEW', 'https://picsum.photos/200', '닌텐도 스위치 팝니다.', 'SHIPPING', 'SELLING');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (27000, now(), now(), 1, 'GAME', '닌텐도 스위치 판매중', '동작구 사당동', 'NEW', 'https://picsum.photos/200', '닌텐도 스위치 팝니다.', 'SHIPPING', 'SELLING');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (28000, now(), now(), 1, 'GAME', '닌텐도 스위치 판매중', '동작구 사당동', 'NEW', 'https://picsum.photos/200', '닌텐도 스위치 팝니다.', 'SHIPPING', 'SELLING');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (29000, now(), now(), 1, 'GAME', '닌텐도 스위치 판매중', '동작구 사당동', 'NEW', 'https://picsum.photos/200', '닌텐도 스위치 팝니다.', 'SHIPPING', 'SELLING');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (234, now(), now(), 1, 'MEN_FASHION', '234', '234', 'NEW', 'https://picsum.photos/200', '324', 'FACE_TO_FACE', 'SELLING');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (234, now(), now(), 4, 'MEN_FASHION', '123', '123', 'NEW', 'https://offer-be.kro.kr/images/00d81cca-8c5e-4644-9b25-1a6718bea3a8.jpeg', '수정', 'FACE_TO_FACE', 'SOLD');
--  23
insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (234, now(), now(), 4, 'WOMEN_FASHION', '2345234', '123123124', 'NEW', 'https://offer-be.kro.kr/images/56ce3d34-3611-49c5-bd16-1e69c59ad849.jpeg', '가나다라마', 'FACE_TO_FACE', 'SELLING');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (234, now(), now(), 4, 'WOMEN_FASHION', '2345234', '123123124', 'NEW', 'https://offer-be.kro.kr/images/56ce3d34-3611-49c5-bd16-1e69c59ad849.jpeg', '가나다라마', 'FACE_TO_FACE', 'SELLING');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (234, now(), now(), 4, 'WOMEN_FASHION', '2345234', '123123124', 'NEW', 'https://offer-be.kro.kr/images/56ce3d34-3611-49c5-bd16-1e69c59ad849.jpeg', '가나다라마', 'ALL', 'SELLING');

--  25
insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (234, now(), now(), 4, 'WOMEN_FASHION', '123123', '234', 'NEW', null, '12312234', 'FACE_TO_FACE', 'SELLING');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (234, now(), now(), 4, 'MEN_FASHION', '123213', '123123124', 'NEW', null, '가나다라마', 'FACE_TO_FACE', 'SELLING');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (234, now(), now(), 4, 'MEN_FASHION', '23423423', '123123124', 'NEW', 'https://offer-be.kro.kr/images/0597c051-24aa-40f0-b185-e69a55f28033.jpeg', '사세요 제발', 'FACE_TO_FACE', 'SELLING');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (234, now(), now(), 4, 'MEN_FASHION', '우리집', '우릴집', 'NEW', 'https://offer-be.kro.kr/images/8f6e0d08-7e5a-4cd8-b26c-9bb4ea421520.jpeg', '사장님이 미쳤어요', 'FACE_TO_FACE', 'SELLING');

insert into post_image(post_id, url) values (27, 'https://offer-be.kro.kr/images/252b35b5-ec17-46c1-897e-c582e21f053a.jpeg');
insert into post_image(post_id, url) values (27, 'https://offer-be.kro.kr/images/c356b06d-c456-4be1-bf3e-0b46efa5016a.jpeg');
insert into post_image(post_id, url) values (28, 'https://offer-be.kro.kr/images/9a5eb056-22aa-4adb-95f7-27d218c22ce2.jpeg');
insert into post_image(post_id, url) values (28, 'https://offer-be.kro.kr/images/259c2fcb-e706-41af-b3ca-b11e495c2270.jpeg');
insert into post_image(post_id, url) values (28, 'https://offer-be.kro.kr/images/05805172-5785-414e-8950-f7663701cce9.jpeg');
insert into post_image(post_id, url) values (22, 'https://offer-be.kro.kr/images/4b4fd33f-a0d1-451e-819c-21f034d96b22.jpeg');

insert into favorite(member_id, post_id) values (3, 28);
insert into favorite(member_id, post_id) values (3, 27);
insert into favorite(member_id, post_id) values (4, 22);

insert into offer(is_selected, price, created_at, offerer_id, post_id, trade_type) values (false, 30000, now(), 1, 20, 'ALL');
insert into offer(is_selected, price, created_at, offerer_id, post_id, trade_type) values (false, 30000, now(), 1, 19, 'ALL');
insert into offer(is_selected, price, created_at, offerer_id, post_id, trade_type) values (false, 30000, now(), 3, 19, 'ALL');
insert into offer(is_selected, price, created_at, offerer_id, post_id, trade_type) values (false, 29000, now(), 3, 20, 'ALL');
insert into offer(is_selected, price, created_at, offerer_id, post_id, trade_type) values (false, 27000, now(), 3, 18, 'ALL');
insert into offer(is_selected, price, created_at, offerer_id, post_id, trade_type) values (false, 12220, now(), 3, 22, 'FACE_TO_FACE');
insert into offer(is_selected, price, created_at, offerer_id, post_id, trade_type) values (false, 120, now(), 3, 22, 'FACE_TO_FACE');
insert into offer(is_selected, price, created_at, offerer_id, post_id, trade_type) values (false, 30000, now(), 3, 18, 'FACE_TO_FACE');

insert into msg_room(created_at, offer_id, offerer_id, seller_id) values (now(), 5, 3, 1);
insert into msg_room(created_at, offer_id, offerer_id, seller_id) values (now(), 6, 3, 4);

insert into msg(is_read, created_at, room_id, sender_id, content) values (false, now(), 1, 1, '판매자로부터의 첫 메시지입니다.');
insert into msg(is_read, created_at, room_id, sender_id, content) values (false, now(), 2, 4, '팔아요~');
insert into msg(is_read, created_at, room_id, sender_id, content) values (false, now(), 2, 4, '이거 사세요');
insert into msg(is_read, created_at, room_id, sender_id, content) values (false, now(), 2, 4, '사시라구요');
insert into msg(is_read, created_at, room_id, sender_id, content) values (false, now(), 2, 4, '얼른');
insert into msg(is_read, created_at, room_id, sender_id, content) values (false, now(), 2, 4, '사시에료');
insert into msg(is_read, created_at, room_id, sender_id, content) values (false, now(), 2, 4, 'ㅎㅎ');
insert into msg(is_read, created_at, room_id, sender_id, content) values (false, now(), 2, 4, '안녕하세요');
insert into msg(is_read, created_at, room_id, sender_id, content) values (false, now(), 2, 4, '구매 원하세요?');
insert into msg(is_read, created_at, room_id, sender_id, content) values (false, now(), 2, 4, '구매해주세요');
insert into msg(is_read, created_at, room_id, sender_id, content) values (false, now(), 2, 4, '안녕');
insert into msg(is_read, created_at, room_id, sender_id, content) values (false, now(), 2, 4, '안녕');
insert into msg(is_read, created_at, room_id, sender_id, content) values (false, now(), 2, 4, '반가워요');
insert into msg(is_read, created_at, room_id, sender_id, content) values (false, now(), 2, 4, '안녕하세요');
insert into msg(is_read, created_at, room_id, sender_id, content) values (false, now(), 2, 4, '바보');
insert into msg(is_read, created_at, room_id, sender_id, content) values (false, now(), 2, 4, '바보');
insert into msg(is_read, created_at, room_id, sender_id, content) values (false, now(), 2, 4, '안녕');
insert into msg(is_read, created_at, room_id, sender_id, content) values (false, now(), 2, 4, '안녕');
insert into msg(is_read, created_at, room_id, sender_id, content) values (false, now(), 2, 4, '안녕하세요');

insert into review(reviewee_is_buyer, score, created_at, modified_at, post_id, reviewee_id, reviewer_id, content) values (false, 1, now(), now(), 20, 1, 3, '조타이 조타이~?');
insert into review(reviewee_is_buyer, score, created_at, modified_at, post_id, reviewee_id, reviewer_id, content) values (false, 1, now(), now(), 18, 1, 3, '조타이 조타이~?');
insert into review(reviewee_is_buyer, score, created_at, modified_at, post_id, reviewee_id, reviewer_id, content) values (false, 2, now(), now(), 22, 4, 3, '좋은디요?');
