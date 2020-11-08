INSERT INTO USER_AUTH (id, username, password)
VALUES (user_sequence.nextval, 'admin', '$2a$10$dA75iY7Yxw5G/FwOpW1VzucNIvrS9VVbU5gXWPskQRLmqlJx6uw16');

INSERT INTO CUSTOMER (id, birth_date, gender, name, surname)
VALUES (customer_sequence.nextval, CURRENT_DATE, 'M', 'NAME', 'SURNAME');

INSERT INTO ADDRESS (id, street, number, complement, city, province, country, postal_code, type, customer_id)
VALUES (address_sequence.nextval, 'STREET NAME', 123, '-', 'SAO PAULO', 'SAO PAULO', 'BRAZIL', '03211230', 'RESIDENCIAL', customer_sequence.currval);

INSERT INTO PHONE (customer_id, phones)
VALUES (customer_sequence.currval, '999999999');

INSERT INTO CUSTOMER (id, birth_date, gender, name, surname)
VALUES (customer_sequence.nextval, CURRENT_DATE, 'M', 'NAME 2', 'SURNAME 2');

INSERT INTO ADDRESS (id, street, number, complement, city, province, country, postal_code, type, customer_id)
VALUES (address_sequence.nextval, 'STREET NAME 2', 456, '-', 'SAO PAULO', 'SAO PAULO', 'BRAZIL', '12300321', 'COMERCIAL', customer_sequence.currval);

INSERT INTO PHONE (customer_id, phones)
VALUES (customer_sequence.currval, '33333333');