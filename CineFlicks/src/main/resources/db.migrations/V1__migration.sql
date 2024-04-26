-- Enable UUID extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create the language table
CREATE TABLE language (
                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          language VARCHAR(100) UNIQUE NOT NULL
);

-- Create the genre table
CREATE TABLE genre (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       genre VARCHAR(100) UNIQUE NOT NULL
);


-- Create the writers table
CREATE TABLE writer (
                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                        writer VARCHAR(255) UNIQUE NOT NULL
);

-- Create the actors table
CREATE TABLE actor (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       actor VARCHAR(255) UNIQUE NOT NULL
);

-- Create the directors table
CREATE TABLE director (
                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          director VARCHAR(255) UNIQUE NOT NULL
);

-- Create the movies table
CREATE TABLE movie (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       title VARCHAR(255) NOT NULL,
                       year INTEGER,
                       rated VARCHAR(50),
                       runtime VARCHAR(50),
                       plot TEXT,
                       country VARCHAR(100),
                       awards TEXT,
                       poster VARCHAR(255),
                       metascore INTEGER,
                       imdb_rating DOUBLE PRECISION,
                       imdb_votes INTEGER,
                       imdb_id VARCHAR(20) UNIQUE
);

-- Create the movie_genre table (many-to-many relationship between movies and genres)
CREATE TABLE movie_genre (
                             movie_id UUID REFERENCES movie(id),
                             genre_id UUID REFERENCES genre(id),
                             PRIMARY KEY (movie_id, genre_id)
);

-- Create the movie_director table (many-to-many relationship between movies and directors)
CREATE TABLE movie_director (
                                movie_id UUID REFERENCES movie(id),
                                director_id UUID REFERENCES director(id),
                                PRIMARY KEY (movie_id, director_id)
);

-- Create the movie_writer table (many-to-many relationship between movies and writers)
CREATE TABLE movie_writer (
                              movie_id UUID REFERENCES movie(id),
                              writer_id UUID REFERENCES writer(id),
                              PRIMARY KEY (movie_id, writer_id)
);

-- Create the movie_actor table (many-to-many relationship between movies and actors)
CREATE TABLE movie_actor (
                             movie_id UUID REFERENCES movie(id),
                             actor_id UUID REFERENCES actor(id),
                             PRIMARY KEY (movie_id, actor_id)
);

-- Create the movie_language table (many-to-many relationship between movies and languages)
CREATE TABLE movie_language (
                                movie_id UUID REFERENCES movie(id),
                                language_id UUID REFERENCES language(id),
                                PRIMARY KEY (movie_id, language_id)
);

-- Create the cinemas table
CREATE TABLE cinema (
                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                        room VARCHAR(50),
                        row INTEGER,
                        "column" INTEGER
);

-- Create the cinema_movie table (many-to-many relationship between cinemas and movies)
CREATE TABLE cinema_movie (
                              cinema_id UUID REFERENCES cinema(id),
                              movie_id UUID REFERENCES movie(id),
                              start_time TIMESTAMP,
                              end_time TIMESTAMP,
                              PRIMARY KEY (cinema_id, movie_id)
);

-- Create the users table
CREATE TABLE "user" (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       first_name VARCHAR(100),
                       last_name VARCHAR(100),
                       username VARCHAR(100) UNIQUE,
                       email VARCHAR(255) UNIQUE,
                       password VARCHAR(255),
                       role VARCHAR(50)
);

-- Create the tickets table
CREATE TABLE ticket (
                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                        user_id UUID REFERENCES "user"(id),
                        cinema_id UUID REFERENCES cinema(id),
                        movie_id UUID REFERENCES movie(id),
                        price DOUBLE PRECISION,
                        selected_seats TEXT
);

INSERT INTO "user" (first_name, last_name, username, email, password, role) VALUES
        ('Bruno','Galić','bg121788','bg121788@unist.hr','$2a$10$.Yo3X0l3rfdzgMGoZ6tqVuoOxK7225qLn25MClV6klDxJf/XvoAnu','ADMIN');
--password here is root


-- Insert all languages into the table
INSERT INTO language (language) VALUES
                                    ('Afar'),
                                    ('Abkhazian'),
                                    ('Avestan'),
                                    ('Afrikaans'),
                                    ('Akan'),
                                    ('Amharic'),
                                    ('Aragonese'),
                                    ('Arabic'),
                                    ('Assamese'),
                                    ('Avaric'),
                                    ('Aymara'),
                                    ('Azerbaijani'),
                                    ('Bashkir'),
                                    ('Belarusian'),
                                    ('Bulgarian'),
                                    ('Bihari'),
                                    ('Bislama'),
                                    ('Bambara'),
                                    ('Bengali'),
                                    ('Tibetan'),
                                    ('Breton'),
                                    ('Bosnian'),
                                    ('Catalan'),
                                    ('Chechen'),
                                    ('Chamorro'),
                                    ('Corsican'),
                                    ('Cree'),
                                    ('Czech'),
                                    ('Chuvash'),
                                    ('Welsh'),
                                    ('Danish'),
                                    ('German'),
                                    ('Divehi'),
                                    ('Dzongkha'),
                                    ('Ewe'),
                                    ('Greek'),
                                    ('English'),
                                    ('Esperanto'),
                                    ('Spanish'),
                                    ('Estonian'),
                                    ('Basque'),
                                    ('Persian'),
                                    ('Fulah'),
                                    ('Finnish'),
                                    ('Fijian'),
                                    ('Faroese'),
                                    ('French'),
                                    ('Western Frisian'),
                                    ('Irish'),
                                    ('Scottish Gaelic'),
                                    ('Galician'),
                                    ('Guarani'),
                                    ('Gujarati'),
                                    ('Manx'),
                                    ('Hausa'),
                                    ('Hebrew'),
                                    ('Hindi'),
                                    ('Hiri Motu'),
                                    ('Croatian'),
                                    ('Haitian Creole'),
                                    ('Hungarian'),
                                    ('Armenian'),
                                    ('Herero'),
                                    ('Interlingua'),
                                    ('Indonesian'),
                                    ('Interlingue'),
                                    ('Igbo'),
                                    ('Sichuan Yi'),
                                    ('Inupiaq'),
                                    ('Ido'),
                                    ('Icelandic'),
                                    ('Italian'),
                                    ('Inuktitut'),
                                    ('Japanese'),
                                    ('Javanese'),
                                    ('Georgian'),
                                    ('Kongo'),
                                    ('Kikuyu'),
                                    ('Kuanyama'),
                                    ('Kazakh'),
                                    ('Kalaallisut'),
                                    ('Central Khmer'),
                                    ('Kannada'),
                                    ('Korean'),
                                    ('Kanuri'),
                                    ('Kashmiri'),
                                    ('Kurdish'),
                                    ('Komi'),
                                    ('Cornish'),
                                    ('Kirghiz'),
                                    ('Latin'),
                                    ('Luxembourgish'),
                                    ('Ganda'),
                                    ('Limburgan'),
                                    ('Lingala'),
                                    ('Lao'),
                                    ('Lithuanian'),
                                    ('Luba-Katanga'),
                                    ('Latvian'),
                                    ('Malagasy'),
                                    ('Marshallese'),
                                    ('Maori'),
                                    ('Macedonian'),
                                    ('Malayalam'),
                                    ('Mongolian'),
                                    ('Marathi'),
                                    ('Malay'),
                                    ('Maltese'),
                                    ('Burmese'),
                                    ('Nauru'),
                                    ('Navajo'),
                                    ('North Ndebele'),
                                    ('Nepali'),
                                    ('Ndonga'),
                                    ('Dutch'),
                                    ('Norwegian Nynorsk'),
                                    ('Norwegian Bokmål'),
                                    ('South Ndebele'),
                                    ('Chichewa'),
                                    ('Occitan'),
                                    ('Ojibwa'),
                                    ('Oromo'),
                                    ('Oriya'),
                                    ('Ossetian'),
                                    ('Panjabi'),
                                    ('Pali'),
                                    ('Polish'),
                                    ('Pushto'),
                                    ('Portuguese'),
                                    ('Quechua'),
                                    ('Romansh'),
                                    ('Rundi'),
                                    ('Romanian'),
                                    ('Russian'),
                                    ('Kinyarwanda'),
                                    ('Sanskrit'),
                                    ('Sardinian'),
                                    ('Sindhi'),
                                    ('Northern Sami'),
                                    ('Samoan'),
                                    ('Sango'),
                                    ('Serbian'),
                                    ('Gaelic'),
                                    ('Shona'),
                                    ('Sinhala'),
                                    ('Slovak'),
                                    ('Slovenian'),
                                    ('Sundanese'),
                                    ('Swahili'),
                                    ('Swati'),
                                    ('Swedish'),
                                    ('Tamil'),
                                    ('Telugu'),
                                    ('Tajik'),
                                    ('Thai'),
                                    ('Tigrinya'),
                                    ('Turkmen'),
                                    ('Tagalog'),
                                    ('Tswana'),
                                    ('Tonga'),
                                    ('Turkish'),
                                    ('Tsonga'),
                                    ('Tatar'),
                                    ('Twi'),
                                    ('Tahitian'),
                                    ('Uighur'),
                                    ('Ukrainian'),
                                    ('Urdu'),
                                    ('Uzbek'),
                                    ('Venda'),
                                    ('Vietnamese'),
                                    ('Volapük'),
                                    ('Walloon'),
                                    ('Wolof'),
                                    ('Xhosa'),
                                    ('Yiddish'),
                                    ('Yoruba'),
                                    ('Zhuang'),
                                    ('Chinese'),
                                    ('Zulu');


-- Insert all genres into the table
INSERT INTO genre (genre) VALUES
                              ('Action'),
                              ('Adventure'),
                              ('Animation'),
                              ('Biography'),
                              ('Comedy'),
                              ('Crime'),
                              ('Documentary'),
                              ('Drama'),
                              ('Family'),
                              ('Fantasy'),
                              ('Film-Noir'),
                              ('History'),
                              ('Horror'),
                              ('Music'),
                              ('Musical'),
                              ('Mystery'),
                              ('Romance'),
                              ('Sci-Fi'),
                              ('Sport'),
                              ('Thriller'),
                              ('War'),
                              ('Western');

-- Insert all writers into the table
INSERT INTO writer (writer) VALUES
                                ('Quentin Tarantino'),
                                ('Christopher Nolan'),
                                ('Steven Spielberg'),
                                ('Martin Scorsese'),
                                ('Woody Allen'),
                                ('Stanley Kubrick'),
                                ('Alfred Hitchcock'),
                                ('Ingmar Bergman'),
                                ('Francis Ford Coppola'),
                                ('Akira Kurosawa'),
                                ('David Lynch'),
                                ('Paul Thomas Anderson'),
                                ('Coen Brothers'),
                                ('Spike Lee'),
                                ('Charlie Kaufman'),
                                ('Wes Anderson'),
                                ('Pedro Almodóvar'),
                                ('David Fincher'),
                                ('Hayao Miyazaki'),
                                ('James Cameron'),
                                ('Darren Aronofsky'),
                                ('Guillermo del Toro'),
                                ('Michael Haneke'),
                                ('Clint Eastwood'),
                                ('Aaron Sorkin'),
                                ('Greta Gerwig'),
                                ('Jordan Peele'),
                                ('Ava DuVernay'),
                                ('Barry Jenkins'),
                                ('Chloé Zhao'),
                                ('Bong Joon-ho'),
                                ('Taika Waititi'),
                                ('Lulu Wang'),
                                ('Bo Burnham'),
                                ('Sofia Coppola'),
                                ('Jane Campion'),
                                ('Yorgos Lanthimos'),
                                ('Lynne Ramsay'),
                                ('Safdie Brothers'),
                                ('Denis Villeneuve'),
                                ('Damien Chazelle'),
                                ('Ryan Coogler'),
                                ('Shonda Rhimes'),
                                ('Noah Baumbach'),
                                ('Rebecca Hall'),
                                ('Rian Johnson'),
                                ('Ryan Murphy'),
                                ('Céline Sciamma'),
                                ('Patty Jenkins'),
                                ('Judd Apatow'),
                                ('David O. Russell'),
                                ('Alejandro González Iñárritu');


-- Insert all actors into the table
INSERT INTO actor (actor) VALUES
                              ('Tom Hanks'),
                              ('Meryl Streep'),
                              ('Leonardo DiCaprio'),
                              ('Brad Pitt'),
                              ('Robert De Niro'),
                              ('Johnny Depp'),
                              ('Denzel Washington'),
                              ('Cate Blanchett'),
                              ('Daniel Day-Lewis'),
                              ('Harrison Ford'),
                              ('Kate Winslet'),
                              ('Joaquin Phoenix'),
                              ('Emma Stone'),
                              ('Emma Watson'),
                              ('Natalie Portman'),
                              ('Charlize Theron'),
                              ('Jennifer Lawrence'),
                              ('Julia Roberts'),
                              ('Al Pacino'),
                              ('Robert Downey Jr.'),
                              ('Nicole Kidman'),
                              ('Anthony Hopkins'),
                              ('Sandra Bullock'),
                              ('Scarlett Johansson'),
                              ('Matt Damon'),
                              ('Angelina Jolie'),
                              ('Will Smith'),
                              ('Christian Bale'),
                              ('Jennifer Aniston'),
                              ('George Clooney'),
                              ('Chris Evans'),
                              ('Samuel L. Jackson'),
                              ('Chris Hemsworth'),
                              ('Kevin Spacey'),
                              ('Tom Cruise'),
                              ('Mark Wahlberg'),
                              ('Anne Hathaway'),
                              ('Sean Connery'),
                              ('Jack Nicholson'),
                              ('Keanu Reeves'),
                              ('Liam Neeson'),
                              ('Keira Knightley'),
                              ('Hugh Jackman'),
                              ('Viola Davis'),
                              ('Ben Affleck'),
                              ('Amy Adams'),
                              ('Jamie Foxx'),
                              ('Eddie Redmayne'),
                              ('Renee Zellweger'),
                              ('Dwayne Johnson'),
                              ('Tom Hardy'),
                              ('Idris Elba');


-- Insert all directors into the table
INSERT INTO director (director) VALUES
                                    ('Quentin Tarantino'),
                                    ('Christopher Nolan'),
                                    ('Steven Spielberg'),
                                    ('Martin Scorsese'),
                                    ('Woody Allen'),
                                    ('Stanley Kubrick'),
                                    ('Alfred Hitchcock'),
                                    ('Ingmar Bergman'),
                                    ('Francis Ford Coppola'),
                                    ('Akira Kurosawa'),
                                    ('David Lynch'),
                                    ('Paul Thomas Anderson'),
                                    ('Coen Brothers'),
                                    ('Spike Lee'),
                                    ('Charlie Kaufman'),
                                    ('Wes Anderson'),
                                    ('Pedro Almodóvar'),
                                    ('David Fincher'),
                                    ('Hayao Miyazaki'),
                                    ('James Cameron'),
                                    ('Darren Aronofsky'),
                                    ('Guillermo del Toro'),
                                    ('Michael Haneke'),
                                    ('Clint Eastwood'),
                                    ('Aaron Sorkin'),
                                    ('Greta Gerwig'),
                                    ('Jordan Peele'),
                                    ('Ava DuVernay'),
                                    ('Barry Jenkins'),
                                    ('Chloé Zhao'),
                                    ('Bong Joon-ho'),
                                    ('Taika Waititi'),
                                    ('Lulu Wang'),
                                    ('Bo Burnham'),
                                    ('Sofia Coppola'),
                                    ('Jane Campion'),
                                    ('Yorgos Lanthimos'),
                                    ('Lynne Ramsay'),
                                    ('Safdie Brothers'),
                                    ('Denis Villeneuve'),
                                    ('Damien Chazelle'),
                                    ('Ryan Coogler'),
                                    ('Shonda Rhimes'),
                                    ('Noah Baumbach'),
                                    ('Rebecca Hall'),
                                    ('Rian Johnson'),
                                    ('Ryan Murphy'),
                                    ('Céline Sciamma'),
                                    ('Patty Jenkins'),
                                    ('Judd Apatow'),
                                    ('David O. Russell'),
                                    ('Alejandro González Iñárritu');

INSERT INTO cinema  (room,row,"column") VALUES
                                            ('A001',20,15),
                                            ('A002',20,15),
                                            ('A003',20,15),
                                            ('B001',30,15),
                                            ('B002',30,15),
                                            ('C001',35,15),
                                            ('D001',40,15)
