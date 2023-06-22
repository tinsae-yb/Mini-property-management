


insert into public.users (id, account_status, email, first_name, last_name, password, role)
values (2, 1, 'john@admin.com', 'John', 'Stones', '$2a$10$ADP96eZhyzaaq6W0AndU9.BPNhugumsAxK7NyaYtDLVKVP59znA0q', 0),
       (1, 1, 'james@owner.com', 'James', 'Madison', '$2a$10$RkzqjGbDe.gjhKHAwdgEAu9N2mNtu3Y9AEoQEvaiGBVkkp7K1gaBu', 2),
       (52, 1, 'mia@user.com', 'Mia', 'Swass', '$2a$10$he.8I/kzT3OMHRYKyeQJKeZhaznbT1A2i/1/iuImvNcd2JBc0wUAy', 1);


insert into public.property (id, city, state, street, zip, area, created_date, description, image, last_modified_date,
                             number_of_bathrooms, number_of_bedrooms, number_of_floors, price, property_status,
                             property_type, year_built, seller_id)
values (54, 'Anytown', 'California', '123 Main St', '12345', 4000, '2023-06-22 16:42:46.095174 +00:00', '42732',
        '5ca87691-c47c-49f0-9a14-a8de82f562b7', '2023-06-22 16:45:34.529857 +00:00', 2, 3, 2, 350000.00, 1, 0, 2022, 1),
       (52, 'Fairfield', 'IOWA', '4th street', '52557', 50, '2023-06-22 16:36:13.840404 +00:00', '42735',
        '7deafc95-a8a1-457e-b4a4-975402c10924', '2023-06-22 16:46:44.289253 +00:00', 1, 2, 2, 200000.00, 2, 0, 2022, 1),
       (53, 'Burlington', 'IOWA', '4th street', '52557', 50, '2023-06-22 16:38:37.911215 +00:00', '42736',
        '10a978b6-9c09-4af9-b6f6-86a25e374c81', '2023-06-22 16:46:45.911967 +00:00', 2, 3, 2, 350000.00, 0, 4, 2022, 1);

--
--
-- insert into public.offer (id, accepted_by_customer, accepted_by_owner, created_date, last_modified_date, offer_price,
--                           offer_status, customer_id, property_id)
-- values (1, false, false, '2023-06-22 16:45:34.527103 +00:00', '2023-06-22 16:45:34.527117 +00:00', 250000.00, 0, 52,
--         54),
--        (3, false, false, '2023-06-22 16:45:48.868732 +00:00', '2023-06-22 16:46:44.291681 +00:00', 250000.00, 1, 52,
--         52),
--        (2, false, false, '2023-06-22 16:45:41.377652 +00:00', '2023-06-22 16:46:45.918028 +00:00', 180000.00, 3, 52,
--         53);
--
-- insert into public.favorite (id, property_id, user_id)
-- values (3, 54, 52);