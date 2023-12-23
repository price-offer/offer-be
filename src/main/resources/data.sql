insert into member(offer_level, oauth_id, oauth_type, nickname, profile_image_url, created_date)
values (1, 2036674018, 'KAKAO', '빼어난 가전제품 3호', 'http://k.kakaocdn.net/dn/ia9Y5/btrXSxAKtaj/1yZZWN0bhuA627JBHiN2ck/img_640x640.jpg', now());

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

insert into sort_item(sort_group_id, exposure_title, name) values (1, '높은 가격순', 'PRICE_DESC');
insert into sort_item(sort_group_id, exposure_title, name) values (1, '최신순', 'CREATED_DATE_DESC');
insert into sort_item(sort_group_id, exposure_title, name) values (2, '높은 가격순', 'PRICE_DESC');
insert into sort_item(sort_group_id, exposure_title, name) values (2, '최신순', 'CREATED_DATE_DESC');


insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type, trade_status)
values (10000, now(), now(), 1, 'MEN_FASHION', '남성 청바지 팔아요~~', '동작구 사당동', 'SECONDHAND', 'https://picsum.photos/200', '남성 청바지 팝니다.', 'FACE_TO_FACE', 'SELLING');

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
