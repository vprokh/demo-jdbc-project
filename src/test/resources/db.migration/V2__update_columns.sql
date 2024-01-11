-- when we add new column with the NOT NULL constraint
-- to the table which already has some data
-- we must specify DEFAULT value because DB should know what value should be added
-- to the newly added column (since null is not allowed due to constraint)
ALTER TABLE orders
    ADD COLUMN total_price numeric NOT NULL DEFAULT 0.00;

-- if there is no constraint, we can add new column without DEFAULT value
-- all rows will have null value in this case
-- ALTER TABLE orders
--     ADD COLUMN status VARCHAR(50);

-- this is how we can update type of the existing column
ALTER TABLE orders
    ALTER COLUMN total_price SET DATA TYPE varchar(10);

-- delete test column
ALTER TABLE orders
    DROP COLUMN IF EXISTS status;