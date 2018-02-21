--USERS
insert into user(id, address, city, email, user_type, name, password, username, zip_code, date_round_robin) values (1, 'Ulica 6/13', 'Grad 1', 'email1@email.com', 'FIZICKO', 'Naziv 1', 'password1', 'username1', '21000', '2000-01-01');
insert into user(id, address, city, email, user_type, name, password, username, zip_code, date_round_robin) values (2, 'Ulica 4', 'Grad 2', 'mmikac27@gmail.com', 'PRAVNO', 'Naziv 2', 'password2', 'username2', '21001', '2000-01-01');
insert into user(id, address, city, email, user_type, name, password, username, zip_code, date_round_robin) values (3, 'Ulica 15/2', 'Grad 3', 'email3@email.com', 'PRAVNO', 'Naziv 3', 'password3', 'username3', '21002', '2000-01-01');
insert into user(id, address, city, email, user_type, name, password, username, zip_code, date_round_robin) values (4, 'Ulica 3/14', 'Grad 4', 'email4@email.com', 'PRAVNO', 'Naziv 4', 'password4', 'username4', '21003', '2000-01-01');

insert into job_category(id, name) values(1, 'Zdravstvo');
insert into job_category(id, name) values(2, 'IT');
insert into job_category(id, name) values(3, 'Ugostiteljstvo');
insert into job_category(id, name) values(4, 'Poljoprivreda');
insert into job_category(id, name) values(5, 'Rudarstvo');

insert into user_job_categories(users_id, job_categories_id) values(2, 1);
insert into user_job_categories(users_id, job_categories_id) values(2, 2);
insert into user_job_categories(users_id, job_categories_id) values(2, 4);
insert into user_job_categories(users_id, job_categories_id) values(3, 2);
insert into user_job_categories(users_id, job_categories_id) values(3, 3);
insert into user_job_categories(users_id, job_categories_id) values(3, 5);
insert into user_job_categories(users_id, job_categories_id) values(4, 1);
insert into user_job_categories(users_id, job_categories_id) values(4, 2);

insert into purchase_request(id, category, job_description, job_due_date, max_value, min_offers_num, offer_due_date, initiator_id) values(1, 'IT', 'Razvoj softvera za turisticku agenciju.', '2018-09-21', 120751, 1, '2018-03-03', 1);

insert into offer(id, cancel_offer, expenses, job_due_date, initiator_id, purchase_request_id) values(1, false, 110000, '2018-09-01', 2, 1);
insert into offer(id, cancel_offer, expenses, job_due_date, initiator_id, purchase_request_id) values(2, false, 100000, '2018-09-02', 3, 1);
insert into offer(id, cancel_offer, expenses, job_due_date, initiator_id, purchase_request_id) values(3, false, 99999, '2018-08-31', 4, 1);