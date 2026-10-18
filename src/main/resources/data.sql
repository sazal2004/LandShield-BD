-- NOTE: IDs are intentionally NOT inserted. Auto-generated IDs start at 1 and follow
-- insertion order, so property_id values below (1..5) match. This also avoids
-- primary-key collisions when new rows are saved later (H2 and PostgreSQL).

-- PROPERTY 1 : OVER-SELLING DEMO
INSERT INTO property_records (district, upazila, mouza, khatian_no, plot_no, total_area)
VALUES ('Dhaka', 'Savar', 'Demo Mouza A', 'KH-101', 'PL-501', 20.0);
INSERT INTO base_ownerships (property_id, owner_name, base_area) VALUES (1, 'Rahim Ahmed', 15.0);
INSERT INTO deed_records (property_id, deed_no, owner_name, khatian_no, plot_no, land_area)
VALUES (1, 'DEED-1001', 'Rahim Ahmed', 'KH-101', 'PL-501', 15.0);
INSERT INTO mutation_records (property_id, mutation_no, owner_name, khatian_no, plot_no, land_area)
VALUES (1, 'MUT-1001', 'Rahim Ahmed', 'KH-101', 'PL-501', 15.0);
INSERT INTO transfer_records (property_id, seller_name, buyer_name, transferred_area, share_ref, transaction_date, status, demo_generated)
VALUES (1, 'Rahim Ahmed', 'Karim Hasan', 8.0, 'SEG-A', '2024-03-11', 'COMPLETED', FALSE);
INSERT INTO transfer_records (property_id, seller_name, buyer_name, transferred_area, share_ref, transaction_date, status, demo_generated)
VALUES (1, 'Rahim Ahmed', 'Nabila Khan', 4.0, 'SEG-B', '2025-06-20', 'COMPLETED', FALSE);

-- PROPERTY 2 : DEED + MUTATION MISMATCH
INSERT INTO property_records (district, upazila, mouza, khatian_no, plot_no, total_area)
VALUES ('Dhaka', 'Savar', 'Demo Mouza B', 'KH-102', 'PL-502', 12.0);
INSERT INTO base_ownerships (property_id, owner_name, base_area) VALUES (2, 'Nusrat Jahan', 10.0);
-- intentional wrong plot in deed
INSERT INTO deed_records (property_id, deed_no, owner_name, khatian_no, plot_no, land_area)
VALUES (2, 'DEED-1002', 'Nusrat Jahan', 'KH-102', 'PL-WRONG', 10.0);
-- intentional area mismatch in mutation
INSERT INTO mutation_records (property_id, mutation_no, owner_name, khatian_no, plot_no, land_area)
VALUES (2, 'MUT-1002', 'Nusrat Jahan', 'KH-102', 'PL-502', 8.0);

-- PROPERTY 3 : CLEAN / LOW RISK
INSERT INTO property_records (district, upazila, mouza, khatian_no, plot_no, total_area)
VALUES ('Dhaka', 'Dhamrai', 'Demo Mouza C', 'KH-103', 'PL-503', 20.0);
INSERT INTO base_ownerships (property_id, owner_name, base_area) VALUES (3, 'Karim Hasan', 12.0);
INSERT INTO deed_records (property_id, deed_no, owner_name, khatian_no, plot_no, land_area)
VALUES (3, 'DEED-1003', 'Karim Hasan', 'KH-103', 'PL-503', 12.0);
INSERT INTO mutation_records (property_id, mutation_no, owner_name, khatian_no, plot_no, land_area)
VALUES (3, 'MUT-1003', 'Karim Hasan', 'KH-103', 'PL-503', 12.0);
INSERT INTO transfer_records (property_id, seller_name, buyer_name, transferred_area, share_ref, transaction_date, status, demo_generated)
VALUES (3, 'Karim Hasan', 'Demo Buyer', 2.0, 'SEG-A', '2025-01-10', 'COMPLETED', FALSE);

-- PROPERTY 4 : DOUBLE SELLING
INSERT INTO property_records (district, upazila, mouza, khatian_no, plot_no, total_area)
VALUES ('Gazipur', 'Kaliakair', 'Demo Mouza D', 'KH-104', 'PL-504', 15.0);
INSERT INTO base_ownerships (property_id, owner_name, base_area) VALUES (4, 'Mahmud Hasan', 10.0);
INSERT INTO deed_records (property_id, deed_no, owner_name, khatian_no, plot_no, land_area)
VALUES (4, 'DEED-1004', 'Mahmud Hasan', 'KH-104', 'PL-504', 10.0);
INSERT INTO mutation_records (property_id, mutation_no, owner_name, khatian_no, plot_no, land_area)
VALUES (4, 'MUT-1004', 'Mahmud Hasan', 'KH-104', 'PL-504', 10.0);
INSERT INTO transfer_records (property_id, seller_name, buyer_name, transferred_area, share_ref, transaction_date, status, demo_generated)
VALUES (4, 'Mahmud Hasan', 'Buyer One', 4.0, 'SEG-X', '2025-04-15', 'COMPLETED', FALSE);

-- PROPERTY 5 : PENDING / OVERLAPPING TRANSACTION
INSERT INTO property_records (district, upazila, mouza, khatian_no, plot_no, total_area)
VALUES ('Narayanganj', 'Rupganj', 'Demo Mouza E', 'KH-105', 'PL-505', 15.0);
INSERT INTO base_ownerships (property_id, owner_name, base_area) VALUES (5, 'Salma Akter', 10.0);
INSERT INTO deed_records (property_id, deed_no, owner_name, khatian_no, plot_no, land_area)
VALUES (5, 'DEED-1005', 'Salma Akter', 'KH-105', 'PL-505', 10.0);
INSERT INTO mutation_records (property_id, mutation_no, owner_name, khatian_no, plot_no, land_area)
VALUES (5, 'MUT-1005', 'Salma Akter', 'KH-105', 'PL-505', 10.0);
INSERT INTO transfer_records (property_id, seller_name, buyer_name, transferred_area, share_ref, transaction_date, status, demo_generated)
VALUES (5, 'Salma Akter', 'Pending Buyer', 4.0, 'SEG-P', '2026-09-01', 'PENDING', FALSE);
