insert into member(offer_level, oauth_id, oauth_type, nickname, profile_image_url, created_date)
values (1, 2036674018, 'KAKAO', '빼어난 가전제품 3호', 'http://k.kakaocdn.net/dn/ia9Y5/btrXSxAKtaj/1yZZWN0bhuA627JBHiN2ck/img_640x640.jpg', now());

insert into category(name, exposure_title) values ('MEN_FASHION', '남성패션/잡화');
insert into category(name, exposure_title) values ('WOMEN_FASHION', '여성패션/잡화');
insert into category(name, exposure_title) values ('GAME', '게임');
insert into category(name, exposure_title) values ('SPORTS', '스포츠/레저');
insert into category(name, exposure_title) values ('TOY', '장난감/취미');
insert into category(name, exposure_title) values ('DIGITAL', '디지털기기');
insert into category(name, exposure_title) values ('CAR', '자동차/공구');
insert into category(name, exposure_title) values ('APPLIANCE', '생활가전');
insert into category(name, exposure_title) values ('FURNITURE', '가구/인테리어');
insert into category(name, exposure_title) values ('BOOKS', '도서/티켓/음반');
insert into category(name, exposure_title) values ('PET', '반려동물용품');
insert into category(name, exposure_title) values ('OTHER', '기타 중고물품');

insert into sort_group(sort_type) values ('POST');
insert into sort_group(sort_type) values ('OFFER');

insert into sort_item(sort_group_id, exposure_title, name) values (1, '높은 가격순', 'PRICE_DESC');
insert into sort_item(sort_group_id, exposure_title, name) values (1, '최신순', 'CREATED_DATE_DESC');
insert into sort_item(sort_group_id, exposure_title, name) values (2, '높은 가격순', 'PRICE_DESC');
insert into sort_item(sort_group_id, exposure_title, name) values (2, '최신순', 'CREATED_DATE_DESC');


insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type)
values (10000, now(), now(), 1, 'MEN_FASHION', '남성 청바지 팔아요~~', '동작구 사당동', 'SECONDHAND', 'https://picsum.photos/200', '남성 청바지 팝니다.', 'FACE_TO_FACE');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type)
values (11000, now(), now(), 1, 'MEN_FASHION', '후드티 팔아요~~', '동작구 사당동', 'SECONDHAND', 'https://picsum.photos/200', '후드티 판매', 'FACE_TO_FACE');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type)
values (12000, now(), now(), 1, 'MEN_FASHION', '반팔티 팔아요~~', '동작구 사당동', 'NEW', 'https://picsum.photos/200', '반팔티 판매', 'SHIPPING');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type)
values (13000, now(), now(), 1, 'GAME', '젤다 게임팩 판매중', '동작구 사당동', 'NEW', 'https://picsum.photos/200', '젤다 게임팩', 'FACE_TO_FACE');

insert into post(price, created_at, modified_at, seller_id, category, description, location, product_condition, thumbnail_image_url, title, trade_type)
values (14000, now(), now(), 1, 'GAME', '닌텐도 스위치 판매중', '동작구 사당동', 'NEW', 'https://picsum.photos/200', '닌텐도 스위치 팝니다.', 'SHIPPING');
