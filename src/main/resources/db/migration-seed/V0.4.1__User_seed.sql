
INSERT INTO `user` (id,password,name,email,phone,account)
VALUES( 1,
        "1234",
        "testUser",
        "test@test.com",
        "01012345678",
        "123456789"),
      ( 2,
        "1234",
        "testUser2",
        "test@test2.com",
        "01012345678",
        "123456789"),
      ( 3,
        "1234",
        "noAdressUser",
        "no@adress.com",
        "01012345678",
        "123456789");


INSERT INTO address (id,user_id,alias,address,address_detail,is_default)
VALUES(
        1,
        1,
        "집",
        "대한민국",
        "서울",
        1
    ),(
      2,
      1,
      "집2",
      "일본",
      "도쿄",
      0
      ),(
        3,
        1,
        "집3",
        "중국",
        "베이징",
        0
        ),
        (
        4,
        2,
        "집2-1",
        "중국2-1",
        "베이징2-1",
        1
        );
