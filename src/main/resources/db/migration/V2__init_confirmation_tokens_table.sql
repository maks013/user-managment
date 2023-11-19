create table confirmation_tokens
(
    id int primary key auto_increment,
    token varchar (255) not null,
    created_at datetime,
    expires_at datetime,
    confirmed_at datetime null,
    user_id int,
    foreign key (user_id) references users (id)
)
