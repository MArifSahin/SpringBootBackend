create table if not exists "cloud_users"
(
    id           serial primary key,
    username     varchar(50) unique,
    email        varchar(100) not null unique,
    password     varchar(255),
    enabled      boolean      not null,
    name         varchar(25),
    lastname     varchar(25),
    age          varchar(3),
    phone_number varchar(10)
);

create table if not exists "cloud_roles"
(
    id               serial primary key,
    role             varchar(50) not null unique,
    is_account_admin boolean     not null default false
);

create table if not exists "user_roles"
(
    user_id int not null references cloud_users (id),
    role_id int not null references cloud_roles (id)
);

create table if not exists "token_blacklist"
(
    token text primary key not null unique,
    type  varchar(50)      not null
);

create table if not exists "email_templates"
(
    id            serial primary key,
    template_name text not null unique,
    content       text not null
);

create table if not exists "user_attempt"
(
    ip                 varchar(255) primary key,
    attempt_counter    int                         not null,
    first_attempt_date timestamp without time zone NOT NULL
);

create table if not exists "active_sessions"
(
    refresh_token varchar(255) primary key,
    access_token  varchar(255),
    user_agent    text,
    issue_date    timestamp without time zone NOT NULL,
    expire_date   timestamp without time zone NOT NULL,
    user_id       int                         NOT NULL references cloud_users (id)
);

create table if not exists "content_modes"
(
    id        int primary key,
    dram      int,
    comedy    int,
    action    int,
    adventure int,
    romance   int,
    thriller  int,
    horror    int
);

create table if not exists "content_book"
(
    id             int primary key,
    name           varchar,
    published_year int,
    author         varchar,
    editor_score   int,
    user_score     int,
    mode_id        int not null references content_modes (id),
    review_number  int
);

create table if not exists "content_movie"
(
    id            int primary key,
    name          varchar,
    release_date  int,
    director      varchar,
    editor_score  int,
    user_score    int,
    mode_id       int not null references content_modes (id),
    review_number int
);

create table if not exists "movie_review"
(
    id               int primary key,
    review_text      text,
    is_editor_review boolean,
    review_date      timestamp without time zone NOT NULL,
    movie_id         int                         NOT NULL references content_movie (id),
    user_id          int                         NOT NULL references cloud_users (id)
);

create table if not exists "book_review"
(
    id               int primary key,
    review_text      text,
    is_editor_review boolean,
    review_date      timestamp without time zone NOT NULL,
    book_id          int                         NOT NULL references content_book (id),
    user_id          int                         NOT NULL references cloud_users (id)
);