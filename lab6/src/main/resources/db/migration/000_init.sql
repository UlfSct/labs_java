DROP TABLE IF EXISTS order_books;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS book_info;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;


CREATE TABLE IF NOT EXISTS authors(
    id SERIAL PRIMARY KEY,
    surname VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NULL
);

INSERT INTO authors (surname, name, lastname) VALUES
    ('Пушкин', 'Александр', 'Сергеевич'),
    ('Мосян', 'Тунсю', null),
    ('Фрай', 'Макс', null),
    ('Пратчетт', 'Теренс', 'Дэвид Джон'),
    ('Камша', 'Вера', 'Викторовна'),
    ('Прозоров', 'Алексей', 'Яковлевич'),
    ('Геммел', 'Дэвид', 'Эндрю'),
    ('Альфсен', 'Сэм', null),
    ('Далин', 'Макс', null),
    ('Звонцова', 'Екатерина', null),
    ('Райд', 'Ава', null),
    ('Арнаутова', 'Дана', null),
    ('Люцида', 'Аквила', null),
    ('Маро', 'Моргана', null),
    ('Хобб', 'Робин', null),
    ('Бардуго', 'Ли', null)
;

CREATE TABLE IF NOT EXISTS books(
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    year INTEGER NOT NULL,
    author_id INTEGER NOT NULL,
    FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE CASCADE
);

INSERT INTO books (title, year, author_id) VALUES
    ('Евгений Онегин', 1833, 1),
    ('Руслан и Людмила', 1820, 1),
    ('Благословение небожителей', 2017, 2),
    ('Лабиринт Мёнина', 2004, 3),
    ('Магистр дьявольского культа', 2016, 2),
    ('Судьба Шута', 2003, 15),
    ('Мор, ученик Смерти', 1987, 4),
    ('Красное на красном', 2004, 5),
    ('Рим должен пасть', 2007, 6),
    ('Белый Волк', 2003, 7),
    ('Рассеивая сумрак. Лекарь из трущоб', 2023, 8),
    ('Убить некроманта', 2021, 9),
    ('Серебряная клятва', 2020, 10),
    ('Этюд багровых вод', 2024, 11),
    ('Год некроманта', 2016, 12),
    ('Янтарь рассеивает тьму', 2023, 13),
    ('Цветы пиона на снегу', 2024, 14),
    ('Ученик убийцы', 1995, 15),
    ('Продажного королевства', 2016, 16)
;

CREATE TABLE IF NOT EXISTS book_info(
    id SERIAL PRIMARY KEY,
    isbn VARCHAR(20) UNIQUE,
    book_id INTEGER UNIQUE NOT NULL,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);

INSERT INTO book_info (isbn, book_id) VALUES
    ('978-5-17-103766-6', 1),
    ('978-5-04-172862-5', 2),
    ('978-5-04-128246-2', 3),
    ('978-5-17-090207-1', 4),
    ('978-5-907340-00-8', 5),
    ('978-5-389-11396-1', 6),
    ('978-5-04-195285-3', 14),
    ('978-5-04-174789-3', 15),
    ('978-5-17-890123-6', 16),
    ('978-5-04-901234-7', 17),
    ('978-5-699-012345-8', 18),
    ('978-5-17-123789-9', 19)
;

CREATE TABLE IF NOT EXISTS orders(
    id SERIAL PRIMARY KEY,
    client VARCHAR(250) NOT NULL,
    created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP NULL
);

CREATE TABLE IF NOT EXISTS order_books(
    order_id INTEGER NOT NULL,
    book_id INTEGER NOT NULL,
    PRIMARY KEY (order_id, book_id),
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);

INSERT INTO orders (client) VALUES
    ('Васецкий Михаил'),
    ('Скичко Евгения')
;

INSERT INTO order_books (order_id, book_id) VALUES
    (1, 1),
    (1, 2),
    (2, 3),
    (2, 4)
;