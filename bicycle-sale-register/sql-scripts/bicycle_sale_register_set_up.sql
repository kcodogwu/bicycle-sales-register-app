drop database if exists bicycle_sale_register;
create database if not exists bicycle_sale_register;
use bicycle_sale_register;
drop table if exists bicycle_brands;

create table bicycle_brands(
	bicycle_brand_id integer auto_increment not null primary key,
    bicycle_brand_name varchar(20) not null
);

drop table if exists bicycles;

create table bicycles(
	bicycle_id integer auto_increment not null primary key,
    bicycle_brand_id int not null,
    bicycle_model varchar(30) not null,
    cost_price decimal(9,2) not null,
    quantity_in_stock integer not null
);

drop table if exists sale_orders;

create table sale_orders(
	sale_order_number integer auto_increment not null primary key,
    name_of_customer varchar(50) not null,
    phone_number_of_customer varchar(20) not null,
    address_of_customer varchar(160) not null,
    total_amount_of_sale decimal(9,2) not null,
    amount_paid decimal(9,2) not null,
    change_for_customer decimal(9,2) not null
);

drop table if exists sale_order_details;

create table sale_order_details(
	sale_order_detail_id integer auto_increment not null primary key,
	sale_order_number integer not null,
    bicycle_id integer not null,
    quantity_ordered integer not null,
    line_cost decimal(9,2) not null
);

insert into bicycle_brands(
	bicycle_brand_name
) values (
	'Boardman'
);

insert into bicycle_brands(
	bicycle_brand_name
) values (
	'Brompton'
);

insert into bicycle_brands(
	bicycle_brand_name
) values (
	'Falcon'
);

insert into bicycles(
	bicycle_brand_id,
    bicycle_model,
    cost_price,
    quantity_in_stock
) values (
	1,
    'M1R',
    400.00,
    200
);

insert into bicycles(
	bicycle_brand_id,
    bicycle_model,
    cost_price,
    quantity_in_stock
) values (
	1,
    'M2R',
    500.00,
    200
);

insert into bicycles(
	bicycle_brand_id,
    bicycle_model,
    cost_price,
    quantity_in_stock
) values (
	1,
    'M6R',
    700.00,
    200
);

insert into bicycles(
	bicycle_brand_id,
    bicycle_model,
    cost_price,
    quantity_in_stock
) values (
	1,
    'M1R-X',
    450.00,
    200
);

insert into bicycles(
	bicycle_brand_id,
    bicycle_model,
    cost_price,
    quantity_in_stock
) values (
	1,
    'M2R-X',
    550.00,
    200
);

insert into bicycles(
	bicycle_brand_id,
    bicycle_model,
    cost_price,
    quantity_in_stock
) values (
	1,
    'M6R-X',
    750.00,
    200
);

insert into bicycles(
	bicycle_brand_id,
    bicycle_model,
    cost_price,
    quantity_in_stock
) values (
	2,
    'AIR 9.8',
    600.00,
    180
);

insert into bicycles(
	bicycle_brand_id,
    bicycle_model,
    cost_price,
    quantity_in_stock
) values (
	2,
    'AIR 9.9',
    750.00,
    220
);

insert into bicycles(
	bicycle_brand_id,
    bicycle_model,
    cost_price,
    quantity_in_stock
) values (
	3,
    'Go-To 20" Wheel Alloy',
    600.00,
    300
);

insert into bicycles(
	bicycle_brand_id,
    bicycle_model,
    cost_price,
    quantity_in_stock
) values (
	3,
    'Foldaway 20" Wheel Alloy',
    750.00,
    300
);

drop function if exists bicycle_brand_exists;

delimiter //
create function bicycle_brand_exists(
	bicycle_brand_name_param varchar(20)
)
returns boolean
begin
	declare brand_count int;
    
    select 
		count(bicycle_brand_id) into brand_count
	from bicycle_brands
    where bicycle_brand_name = bicycle_brand_name_param;
    
    if brand_count > 0 then
		return true;
	else
		return false;
	end if;
end //

drop function if exists bicycle_exists;

delimiter //
create function bicycle_exists(
	bicycle_id_param varchar(20)
)
returns boolean
begin
	declare bicycle_count int;
    
    select 
		count(bicycle_id) into bicycle_count
	from bicycles
    where bicycle_id = bicycle_id_param;
    
    if bicycle_count > 0 then
		return true;
	else
		return false;
	end if;
end //

drop function if exists get_bicycle_brand_name;

delimiter //
create function get_bicycle_brand_name(
	bicycle_brand_id_param int
)
returns varchar(30)
begin
	declare bicycle_brand_var varchar(30);
    
    set bicycle_brand_var = '';
    
    select 
		bicycle_brand_name into bicycle_brand_var
	from bicycle_brands
    where bicycle_brand_id = bicycle_brand_id_param;
    
    return bicycle_brand_var;
end //

drop function if exists get_bicycle_brand_id;

delimiter //
create function get_bicycle_brand_id(
	bicycle_brand_name_param varchar(30)
)
returns int
begin
	declare bicycle_brand_var int;
    
    set bicycle_brand_var = 0;
    
    select 
		bicycle_brand_id into bicycle_brand_var
	from bicycle_brands
    where bicycle_brand_name = bicycle_brand_name_param;
    
    return bicycle_brand_var;
end //

drop function if exists get_a_bicycle;

delimiter //
create function get_a_bicycle(
	bicycle_id_param int
)
returns varchar(100)
begin
	declare bicycle_brand_var varchar(30);
    declare bicycle_model_var varchar(30);
    declare bicycle_brand_id_var int;
    
    set bicycle_brand_id_var = (
		select 
			bicycle_brand_id
		from bicycles
		where bicycle_id = bicycle_id_param
    );
    
    set bicycle_model_var = (
		select 
			bicycle_model
		from bicycles
		where bicycle_id = bicycle_id_param
    );
    
    set bicycle_brand_var = (
		select 
			bicycle_brand_name
		from bicycle_brands
		where bicycle_brand_id = bicycle_brand_id_var
    );
    
    return concat(bicycle_brand_var, ' ', bicycle_model_var);
end //

drop function if exists calculate_line_cost;

delimiter //
create function calculate_line_cost(
	bicycle_id_param int,
    quantity_param int
)
returns decimal(9,2)
begin
	declare cost decimal(9,2);
    
    set cost = (
		select 
			cost_price
		from bicycles
		where bicycle_id = bicycle_id_param
    );
    
    return cost * quantity_param;
end //

drop function if exists is_enough_quantity_in_stock;

delimiter //
create function is_enough_quantity_in_stock(
	bicycle_id_param int,
    quantity_param int
)
returns boolean
begin
	declare qty int;
    
    set qty = (
		select 
			quantity_in_stock
		from bicycles
		where bicycle_id = bicycle_id_param
    );
    
    if quantity_param > qty then
		return false;
	else
		return true;
	end if;
end //

drop procedure if exists get_bicycles;

delimiter //
create procedure get_bicycles()
begin
	declare sql_error tinyint default false;
    
    declare continue handler for sqlexception
		set sql_error = true;

    start transaction;
    
    select 
		bicycles.bicycle_id as 'Bicycle ID',
		bicycle_brands.bicycle_brand_name as 'Bicycle Brand Name',
        bicycles.bicycle_model as 'Bicycle Model',
        bicycles.cost_price as 'Cost Price (€)',
        bicycles.quantity_in_stock as 'Quantity in Stock'
    from bicycle_brands
    join bicycles
    on bicycle_brands.bicycle_brand_id = bicycles.bicycle_brand_id;
    
	if sql_error = false then
		commit;
	else
		rollback;
    end if;
end //

drop procedure if exists add_bicycle;

delimiter //
create procedure add_bicycle(
	in bicycle_brand_name_param varchar(20),
    in bicycle_model_param varchar(30),
    in cost_price_param decimal(9, 2),
    in quantity_in_stock_param integer,
    out update_count integer
)
begin
	declare sql_error tinyint default false;
    declare new_id integer;
    
    declare continue handler for sqlexception
		set sql_error = true;

    start transaction;
    
    if bicycle_brand_exists(bicycle_brand_name_param) then
		select 
			bicycle_brand_id into new_id
		from bicycle_brands
		where bicycle_brand_name = bicycle_brand_name_param;
    else
		insert into bicycle_brands (
			bicycle_brand_name
        ) values (
			bicycle_brand_name_param
        );
        
        set new_id = last_insert_id();
    end if;
    
    insert into bicycles(
		bicycle_brand_id,
		bicycle_model,
		cost_price,
		quantity_in_stock
	) values (
		new_id,
		bicycle_model_param,
		cost_price_param,
		quantity_in_stock_param
	);
    
	if sql_error = false then
		set update_count = 1; 
		commit;
	else
		set update_count = 0;
		rollback;
    end if;
end //

drop procedure if exists edit_bicycle;

delimiter //
create procedure edit_bicycle(
	in bicycle_id_param int,
	in bicycle_brand_name_param varchar(20),
    in bicycle_model_param varchar(30),
    in cost_price_param decimal(9, 2),
    in quantity_in_stock_param integer,
    out update_count integer
)
begin
	declare sql_error tinyint default false;
    declare brand_count integer;
    declare new_id integer;
    
    declare continue handler for sqlexception
		set sql_error = true;

    start transaction;
    
    if bicycle_brand_exists(bicycle_brand_name_param) then
		select 
			bicycle_brand_id into new_id
		from bicycle_brands
		where bicycle_brand_name = bicycle_brand_name_param;
    else
		insert into bicycle_brands (
			bicycle_brand_name
        ) values (
			bicycle_brand_name_param
        );
        
        set new_id = last_insert_id();
    end if;

    update bicycles set
		bicycle_brand_id = new_id
        , bicycle_model = bicycle_model_param
        , cost_price = cost_price_param
        , quantity_in_stock = quantity_in_stock_param
	where bicycle_id = bicycle_id_param;
    
	if sql_error = false then
		set update_count = 1; 
		commit;
	else
		set update_count = 0;
		rollback;
    end if;
end //

drop procedure if exists delete_bicycle;

delimiter //
create procedure delete_bicycle(
	in bicycle_id_param int,
    out update_count integer
)
begin
	declare sql_error tinyint default false;
    declare brand_count integer;
    declare new_id integer;
    
    declare continue handler for sqlexception
		set sql_error = true;

    start transaction;
    
    delete from bicycles
	where bicycle_id = bicycle_id_param;
    
	if sql_error = false then
		set update_count = 1; 
		commit;
	else
		set update_count = 0;
		rollback;
    end if;
end //

drop procedure if exists get_all_active_bicycle_brands;

delimiter //
create procedure get_all_active_bicycle_brands()
begin
	declare sql_error tinyint default false;
    
    declare continue handler for sqlexception
		set sql_error = true;

    start transaction;
    
    select 
		distinct get_bicycle_brand_name(bicycle_brand_id) as 'Bicycle Brands'
    from bicycles;
    
	if sql_error = false then
		commit;
	else
		rollback;
    end if;
end //

drop procedure if exists get_all_bicycle_models;

delimiter //
create procedure get_all_bicycle_models()
begin
	declare sql_error tinyint default false;
    
    declare continue handler for sqlexception
		set sql_error = true;

    start transaction;
    
    select 
		distinct bicycle_model as 'Bicycle Models'
    from bicycles;
    
	if sql_error = false then
		commit;
	else
		rollback;
    end if;
end //

drop procedure if exists get_number_of_bicycles_for_brand;

delimiter //
create procedure get_number_of_bicycles_for_brand(
	in bicycle_brand_name_param varchar(30)
)
begin
	declare sql_error tinyint default false;
    declare _id int;
    
    declare continue handler for sqlexception
		set sql_error = true;
	
    start transaction;
    
    set _id = get_bicycle_brand_id(bicycle_brand_name_param);
    
    select 
		bicycle_brand_name_param as 'Bicycle Brand',
		count(bicycle_brand_id) as 'Bicycle Count'
    from bicycles
    where bicycle_brand_id = _id;
    
	if sql_error = false then
		commit;
	else
		rollback;
    end if;
end //

drop procedure if exists get_average_cost_of_bicycles_for_brand;

delimiter //
create procedure get_average_cost_of_bicycles_for_brand(
	in bicycle_brand_name_param varchar(30)
)
begin
	declare sql_error tinyint default false;
    declare _id int;
    
    declare continue handler for sqlexception
		set sql_error = true;

    start transaction;
    
    set _id = get_bicycle_brand_id(bicycle_brand_name_param);
    
    select 
		bicycle_brand_name_param as 'Bicycle Brand',
		avg(cost_price) as 'Average Cost Price (€)'
    from bicycles
    where bicycle_brand_id = _id;
    
	if sql_error = false then
		commit;
	else
		rollback;
    end if;
end //

drop procedure if exists get_bicycles_and_stock_quantity;

delimiter //
create procedure get_bicycles_and_stock_quantity()
begin
	declare sql_error tinyint default false;
    declare _id int;
    
    declare continue handler for sqlexception
		set sql_error = true;

    start transaction;

    select 
		bicycle_id as 'Bicycle IDs',
        get_a_bicycle(bicycle_id) as 'Bicycles',
		cost_price as 'Cost',
        quantity_in_stock as 'Quantity'
    from bicycles;
    
	if sql_error = false then
		commit;
	else
		rollback;
    end if;
end //

drop procedure if exists get_sales;

delimiter //
create procedure get_sales()
begin
	declare sql_error tinyint default false;
    
    declare continue handler for sqlexception
		set sql_error = true;

    start transaction;
    
    select 
		sale_order_details.sale_order_number as 'Sale No.'
        , sale_orders.total_amount_of_sale as 'Total Cost'
        , sale_orders.amount_paid as 'Amount Paid'
        , sale_orders.name_of_customer as 'Customer'
        , get_a_bicycle(bicycle_id) as 'Bicycle Ordered'
        , sale_order_details.quantity_ordered as 'Qty.'
        , sale_order_details.line_cost as 'Line Cost'
    from sale_orders
    join sale_order_details
    on sale_orders.sale_order_number = sale_order_details.sale_order_number
    order by sale_orders.sale_order_number;
    
	if sql_error = false then
		commit;
	else
		rollback;
    end if;
end //

drop procedure if exists add_sales;

delimiter //
create procedure add_sale(
	in customer_param varchar(50),
    in phone_number_param varchar(20),
    in address_param varchar(150),
    in ordered_bicycles_param varchar(150),
    in ordered_quantities_param varchar(150),
    in total_param decimal(9, 2),
    in amount_paid_param decimal(9, 2),
    in change_param decimal(9, 2),
    out update_count int
)
begin
	declare sql_error tinyint default false;
    declare new_id integer;
    declare str_len integer;
    declare old_str varchar(150);
    declare an_id varchar(3);
	declare idx integer;
    declare str_len1 integer;
    declare old_str1 varchar(150);
    declare an_id1 varchar(3);
	declare idx1 integer;
    
    declare continue handler for sqlexception
		set sql_error = true;

    start transaction;
    
    insert into sale_orders(
		name_of_customer,
        phone_number_of_customer,
        address_of_customer,
        total_amount_of_sale,
        amount_paid,
        change_for_customer
	) values (
		customer_param,
		phone_number_param,
		address_param,
		total_param,
        amount_paid_param,
        change_param
	);
    
    set new_id = last_insert_id();
    set str_len = length(ordered_bicycles_param);
    set str_len1 = length(ordered_quantities_param);
    
    while str_len > 0 do
		set old_str = ordered_bicycles_param;
        set old_str1 = ordered_quantities_param;
        set idx = instr(old_str, ',');
        set idx1 = instr(old_str1, ',');
        
        if idx = 0 then
			set an_id = trim(old_str);
            set an_id1 = trim(old_str1);
            set ordered_bicycles_param = '';
            set ordered_quantities_param = '';
        else
			set an_id = trim(substring(old_str, 1, idx-1));
            set ordered_bicycles_param = trim(substring(old_str, idx - str_len));
            set an_id1 = trim(substring(old_str1, 1, idx1-1));
            set ordered_quantities_param = trim(substring(old_str1, idx1 - str_len1));
        end if;
        
		set str_len = length(ordered_bicycles_param);
        set str_len1 = length(ordered_quantities_param);
        
        if is_enough_quantity_in_stock(an_id, an_id1) then
			insert into sale_order_details (
				sale_order_number,
				bicycle_id,
				quantity_ordered,
				line_cost
			) values (
				new_id,
				an_id,
				an_id1,
				calculate_line_cost(an_id, an_id1)
			);
		else
			set sql_error = false;
		end if;
    end while;
    
	if sql_error = false then
		set update_count = 1; 
		commit;
	else
		set update_count = 0;
		rollback;
    end if;
end //

drop procedure if exists edit_sale;

delimiter //
create procedure edit_sale(
	in sale_order_number_param int,
	in customer_param varchar(50),
    in phone_number_param varchar(20),
    in address_param varchar(150),
    in ordered_bicycles_param varchar(150),
    in ordered_quantities_param varchar(150),
    in total_param decimal(9, 2),
    in amount_paid_param decimal(9, 2),
    in change_param decimal(9, 2),
    out update_count int
)
begin
	declare sql_error tinyint default false;
    declare str_len integer;
    declare old_str varchar(150);
    declare an_id varchar(3);
	declare idx integer;
    declare str_len1 integer;
    declare old_str1 varchar(150);
    declare an_id1 varchar(3);
	declare idx1 integer;
    
    declare continue handler for sqlexception
		set sql_error = true;

    start transaction;
    
    update sale_orders
	set name_of_customer = customer_param
		, phone_number_of_customer = phone_number_param
        , address_of_customer = address_param
        , total_amount_of_sale = total_param
        , amount_paid = amount_paid_param
        , change_for_customer = change_param
	where sale_order_number = sale_order_number_param;

    delete from sale_order_details
    where sale_order_number = sale_order_number_param;
    
    set str_len = length(ordered_bicycles_param);
    set str_len1 = length(ordered_quantities_param);
    
    while str_len > 0 do
		set old_str = ordered_bicycles_param;
        set old_str1 = ordered_quantities_param;
        set idx = instr(old_str, ',');
        set idx1 = instr(old_str1, ',');
        
        if idx = 0 then
			set an_id = trim(old_str);
            set an_id1 = trim(old_str1);
            set ordered_bicycles_param = '';
            set ordered_quantities_param = '';
        else
			set an_id = trim(substring(old_str, 1, idx-1));
            set ordered_bicycles_param = trim(substring(old_str, idx - str_len));
            set an_id1 = trim(substring(old_str1, 1, idx1-1));
            set ordered_quantities_param = trim(substring(old_str1, idx1 - str_len1));
        end if;
        
		set str_len = length(ordered_bicycles_param);
        set str_len1 = length(ordered_quantities_param);
        
        if is_enough_quantity_in_stock(an_id, an_id1) then
			insert into sale_order_details (
				sale_order_number,
				bicycle_id,
				quantity_ordered,
				line_cost
			) values (
				sale_order_number_param,
				an_id,
				an_id1,
				calculate_line_cost(an_id, an_id1)
			);
		else
			set sql_error = false;
		end if;
    end while;
    
	if sql_error = false then
		set update_count = 1; 
		commit;
	else
		set update_count = 0;
		rollback;
    end if;
end //

drop procedure if exists delete_sale;

delimiter //
create procedure delete_sale(
	in sale_order_number_param int,
    out update_count integer
)
begin
	declare sql_error tinyint default false;
    declare brand_count integer;
    declare new_id integer;
    
    declare continue handler for sqlexception
		set sql_error = true;

    start transaction;
    
    delete from sale_order_details
    where sale_order_number = sale_order_number_param;
    
    delete from sale_orders
	where sale_order_number = sale_order_number_param;
    
	if sql_error = false then
		set update_count = 1; 
		commit;
	else
		set update_count = 0;
		rollback;
    end if;
end //

drop procedure if exists get_num_of_customer_transactions;

delimiter //
create procedure get_num_of_customer_transactions(
	in customer_param varchar(50)
)
begin
	declare sql_error tinyint default false;
    
    declare continue handler for sqlexception
		set sql_error = true;

    start transaction;
    
    select
		name_of_customer as 'Customer',
        count(sale_order_number) as 'No. of Transactions'
	from sale_orders
    where name_of_customer = customer_param;
    
	if sql_error = false then
		commit;
	else
		rollback;
    end if;
end //

drop procedure if exists get_total_amount_paid_by_customer;

delimiter //
create procedure get_total_amount_paid_by_customer(
	in customer_param varchar(50)
)
begin
	declare sql_error tinyint default false;
    
    declare continue handler for sqlexception
		set sql_error = true;

    start transaction;
    
    select
		name_of_customer as 'Customer',
        sum(amount_paid) as 'Total Amount Paid'
	from sale_orders
    where name_of_customer = customer_param;
    
	if sql_error = false then
		commit;
	else
		rollback;
    end if;
end //

drop procedure if exists get_total_bicycles_sold;

delimiter //
create procedure get_total_bicycles_sold()
begin
	declare sql_error tinyint default false;
    
    declare continue handler for sqlexception
		set sql_error = true;

    start transaction;
    
    select 
		get_a_bicycle(bicycle_id) as 'Bicycle'
        , sum(quantity_ordered) as 'Qty. Sold'
    from sale_order_details
    group by bicycle_id;
    
	if sql_error = false then
		commit;
	else
		rollback;
    end if;
end //

drop procedure if exists get_sale_orders;

delimiter //
create procedure get_sale_orders()
begin
	declare sql_error tinyint default false;
    
    declare continue handler for sqlexception
		set sql_error = true;

    start transaction;
    
    select 
		sale_order_number as 'Sale No.'
        , name_of_customer as 'Customer'
        , total_amount_of_sale as 'Sale Amount (€)'
        , amount_paid as 'Amount Paid (€)'
        , change_for_customer 'Change (€)'
    from sale_orders;
    
	if sql_error = false then
		commit;
	else
		rollback;
    end if;
end //

drop trigger if exists bicycles_before_sale_order_details_delete;

delimiter //
create trigger bicycles_before_sale_order_details_delete
	before delete on sale_order_details
    for each row
begin
	update bicycles
     set quantity_in_stock = (quantity_in_stock + old.quantity_ordered)
	where bicycle_id = old.bicycle_id;
end//

drop trigger if exists bicycles_after_sale_order_details_insert;

delimiter //
create trigger bicycles_after_sale_order_details_insert
	after insert on sale_order_details
    for each row
begin
	update bicycles
     set quantity_in_stock = (quantity_in_stock - new.quantity_ordered)
	where bicycle_id = new.bicycle_id;
end//
